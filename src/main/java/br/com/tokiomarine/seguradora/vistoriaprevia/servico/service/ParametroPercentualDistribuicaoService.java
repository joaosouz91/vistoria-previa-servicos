package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import static java.text.MessageFormat.format;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroPercentualDistribuicao;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.DistribuicaoMunicipioDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.DistribuicaoPrestadoraDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ParametroPercentualDistribuicaoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ParametroPercentualDistribuicaoRepository;

@Service
public class ParametroPercentualDistribuicaoService {

	@Autowired
	private ParametroPercentualDistribuicaoRepository repository;

	@Autowired
	private AgendamentoService agendamentoService;
	
	@Autowired
	private UsuarioService usuarioLogado;
	
	@Autowired
	private ParametroVPService parametroVPService;
	
	private static final Logger LOGGER = LogManager.getLogger(ParametroPercentualDistribuicaoService.class);

	public List<ParametroPercentualDistribuicaoDTO> findVigenteByIdRegiao(Long idRegiao,
			Date dataReferencia) {
		return repository.findAllDistrPercentual(idRegiao, DateUtil.truncaData(dataReferencia));
	}

	public List<ParametroPercentualDistribuicaoDTO> carregarParametrosDistribuicao(Long idVspreObgta, Long idRegiao,
			Date dataReferencia) {

		// *Recupera Lista de Prestadoras para região tarifaria e suas percentagens de
		// distribuição em ordem decrescente.
		List<ParametroPercentualDistribuicaoDTO> parametrosDistribuicao = repository.recuperarDistrPercentual(idRegiao, dataReferencia);

		// * Sem prestadora para região tarifaria
		if (parametrosDistribuicao == null || parametrosDistribuicao.isEmpty()) {
			throw new BusinessVPException(format(
					"Não há Prestadoras cadastradas para a Cidade: {0}. Por favor comunique a área responsável de sistemas.",
					idRegiao));
		}

		// * Recupera o somatorio do Percentual relativo e Valida flag Capital
		parametrosDistribuicao.forEach(distrVistorias -> {
			if (distrVistorias.getPcDistr() == null) {
				throw new BusinessVPException(format(
						"Não há Percentual cadastrado para a Prestadora: {0} / Cidade: {1}. Por favor comunique a área responsável de sistemas.",
						distrVistorias.getCdAgrmtVspre(), idRegiao));
			}

			if (distrVistorias.getIcCapit() == null) {
				throw new BusinessVPException(format(
						"Não há Indicador de Capital cadastrado para a Prestadora: {0} / Cidade: {1}. Por favor comunique a área responsável de sistemas.",
						distrVistorias.getCdAgrmtVspre(), idRegiao));
			}
		});

		// * região tarifaria cadastrada para capital e interior na base
		long qtdRegiaoTarifaria = parametrosDistribuicao.stream().map(ParametroPercentualDistribuicaoDTO::getIcCapit)
				.distinct().count();
		if (qtdRegiaoTarifaria > 1l) {
			throw new BusinessVPException(format(
					"Região Tarifaria {0} cadastrada para capital e interior. Por favor comunique a área responsável de sistemas.",
					idRegiao));
		}

		// * Por coerência o somatório teria que ser 100%
		double somatoria = parametrosDistribuicao.stream().mapToDouble(ParametroPercentualDistribuicaoDTO::getPcDistr)
				.sum();
		if (somatoria != 100d) {
			throw new BusinessVPException(format(
					"Erro no somatório de percentual de distrubuição para região: {0}. Por favor comunique a área responsável de sistemas.",
					idRegiao));
		}

		parametrosDistribuicao = calcularPercentualDistribuicao(idVspreObgta, dataReferencia, parametrosDistribuicao);

		return parametrosDistribuicao;
	}

	private List<ParametroPercentualDistribuicaoDTO> calcularPercentualDistribuicao(Long idVspreObgta, Date dataReferencia,
			List<ParametroPercentualDistribuicaoDTO> parametrosDistribuicao) {
		// * Quantidade Total de agendamentos para data de vistoria informada.
		long qtdTotalVist = agendamentoService.countAgendamentos(dataReferencia);

		// * Verifica regras de REAGENDAMENTO para CANCELADOS, NAP e NAG
		Set<Long> listaCodPrtraAnterior = agendamentoService.verificarRegrasReagendamento(idVspreObgta,
				parametrosDistribuicao);

		Double qtdVistPrestadora;
		Double pctVistAgendada = 0d;

		// * Recupera as quantidade agendadas por prestadora e o seu percentual
		// relativo.
		// * Exemplo: para o dia 10/10/2012 temos 10 agtos - Prestadora 1 tem 2
		// agtos(QtdVistoria) - 20%(PcVistAgendada)
		for (ParametroPercentualDistribuicaoDTO distrVistorias : parametrosDistribuicao) {

			qtdVistPrestadora = (double) agendamentoService.countAgendamentos(distrVistorias.getCdAgrmtVspre(), dataReferencia);

			if (qtdTotalVist > 0l) {
				pctVistAgendada =  ((qtdVistPrestadora * 100) / qtdTotalVist);
			}

			distrVistorias.setPcVistAgendada(pctVistAgendada);
		}

		// * Realiza redistribuição dos percentuais. (Desconsidera Prestadoras que não
		// podem receber re-agendamento)
		if (listaCodPrtraAnterior != null && listaCodPrtraAnterior.size() != parametrosDistribuicao.size()) {
				for (Long codPrestadoraAnt : listaCodPrtraAnterior) {
					parametrosDistribuicao = redistribuirPercDistribuicao(codPrestadoraAnt, parametrosDistribuicao);
				}
		}
		return parametrosDistribuicao;
	}

	/**
	 * Redistribiu as percentagens de distribuição entre as demais prestadoras da
	 * região. Para o caso de NAG o agendamento não pode ser direcionada para a
	 * mesma prestadora.
	 *
	 * @param codPrtraAnterior
	 * @param listDistrVistorias
	 * @return List<DistribuicaoVistorias>
	 *
	 * @throws VistoriaPreviaException
	 */
	public List<ParametroPercentualDistribuicaoDTO> redistribuirPercDistribuicao(Long codPrtraAnterior,
			List<ParametroPercentualDistribuicaoDTO> listDistrVistorias) {

		Double pcDistr =  Double.valueOf(0);
		
		ParametroPercentualDistribuicaoDTO distrPrtraAnterior = null;
		for (ParametroPercentualDistribuicaoDTO distrVistorias : listDistrVistorias) {
			if (codPrtraAnterior.equals(distrVistorias.getCdAgrmtVspre())) {
				distrPrtraAnterior = distrVistorias;
				break;
			}
		}

		if (distrPrtraAnterior == null || pcDistr.equals(distrPrtraAnterior.getPcDistr())) {
			return listDistrVistorias;
		}

		// * PrestadoraAnterior = prestadora que não pode receber o agendamento
		// * PrestadoraCorrente = prestadora que ainda pode receber o agendamento
		// * Re-Distribuição = %PrestadoraCorrente / (100 - %PrestadoraAnterior) * 100
		List<ParametroPercentualDistribuicaoDTO> listDistrAux = new ArrayList<>();
		for (ParametroPercentualDistribuicaoDTO distrVistorias : listDistrVistorias) {
			if (!codPrtraAnterior.equals(distrVistorias.getCdAgrmtVspre())) {
				BigDecimal novaPctDistr = BigDecimal
						.valueOf((distrVistorias.getPcDistr() / ( 100- distrPrtraAnterior.getPcDistr())) * 100);
				novaPctDistr = novaPctDistr.setScale(2, RoundingMode.HALF_UP);
				distrVistorias.setPcDistr(novaPctDistr.doubleValue());
				listDistrAux.add(distrVistorias);
			}
		}

		return listDistrAux;
	}
	
	public boolean contemVistoriador(Long idRegiao) {
		ParametroPercentualDistribuicao filtro = new ParametroPercentualDistribuicao();
		filtro.setIdRegiaoAtnmtVstro(idRegiao);
		filtro.setDtFimVigen(DateUtil.DATA_REGISTRO_VIGENTE);
		
		return repository.findAll(Example.of(filtro)).stream().filter(p -> p.getPcDistr().doubleValue() > 0d).findAny().isPresent();
	}

	public boolean isCapital(String cidade) {
		return repository.recuperarIndicadorCapital(cidade, new Date());
	}

	@Transactional
	public void salvarNovaDistribuicao(Long idRegiao, List<ParametroPercentualDistribuicaoDTO> distribuicao) {
		validarNovaDistribuicao(distribuicao);
		
		Date dtHoje = new Date();
		Date dtHojeTruncate = DateUtil.truncaData(dtHoje);
		Date dtAmanha = DateUtil.truncaData(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)));
		String cdUsuroUltmaAlter = usuarioLogado.getUsuarioId();

		List<ParametroPercentualDistribuicao> percentuaisAtuais = repository
				.findByIdRegiaoAtnmtVstroAndDtFimVigenGreaterThanEqual(idRegiao, DateUtil.DATA_REGISTRO_AGUARDANDO_EFETIVACAO);
		if (!percentuaisAtuais.isEmpty()) {

			percentuaisAtuais.stream().forEach(p -> {
				if (p.getDtInicoVigen().after(dtHojeTruncate)) {
					repository.delete(p);
				} else {
					p.setDtFimVigen(dtHojeTruncate);
					p.setDtUltmaAlter(dtHoje);
					p.setCdUsuroUltmaAlter(cdUsuroUltmaAlter);
					repository.save(p);
				}
			});
		}
			
		distribuicao.forEach(p -> {
			ParametroPercentualDistribuicao perc = new ParametroPercentualDistribuicao();
			
			perc.setCdAgrmtVspre(p.getCdAgrmtVspre());
			perc.setCdRegiaoTrfra(0L);
			perc.setCdUsuroUltmaAlter(cdUsuroUltmaAlter);
			perc.setDtFimVigen(DateUtil.DATA_REGISTRO_VIGENTE);
			perc.setDtInicoVigen(dtAmanha);
			perc.setDtUltmaAlter(dtHoje);
			perc.setIcCapit("N");
			perc.setIdRegiaoAtnmtVstro(idRegiao);
			perc.setPcDistr(p.getPcDistr());
						
			repository.save(perc);
		});
	}
	
	@Transactional
	public void redistribuirPercentuais(Set<DistribuicaoMunicipioDTO> distribuicao) {
		validarNovaDistribuicao(distribuicao);
		
		distribuicao.stream().forEach(d -> {
			
			List<Long> ids = repository.obterIdsRegiaoAtendimento(d.getUf());
			
			ids.stream().forEach(r -> {
				List<ParametroPercentualDistribuicao> percentuaisAtuais = repository
						.findByIdRegiaoAtnmtVstroAndDtFimVigenGreaterThanEqual(r, DateUtil.DATA_REGISTRO_AGUARDANDO_EFETIVACAO);

				if (!percentuaisAtuais.isEmpty()) {
					List<ParametroPercentualDistribuicao> novosPercentuais = redistribuirPercentuais(d.getPrestadoras(), percentuaisAtuais);

					Date dtHoje = new Date();
					Date dtHojeTruncate = DateUtil.truncaData(dtHoje);
					Date dtAmanha = DateUtil.truncaData(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)));
	
					percentuaisAtuais.stream().forEach(p -> {
						if (p.getDtInicoVigen().after(dtHojeTruncate)) {
							repository.delete(p);
						} else {
							p.setDtFimVigen(dtHojeTruncate);
							p.setDtUltmaAlter(dtHoje);
							p.setCdUsuroUltmaAlter("ADMVPE");
							repository.save(p);
						}
					});
					
					novosPercentuais.forEach(p -> {
						p.setIdParamPerctDistr(null);
						p.setDtInicoVigen(dtAmanha);
						p.setDtFimVigen(DateUtil.DATA_REGISTRO_VIGENTE);
						p.setDtUltmaAlter(dtHoje);
						p.setCdUsuroUltmaAlter("ADMVPE");
						repository.save(p);
					});
				}
			});
		});
		
		parametroVPService.salvarParamDistribuicaoUf(distribuicao);
	}

	public List<DistribuicaoMunicipioDTO> obterParamDistribuicaoUf() {
		return parametroVPService.obterParamDistribuicaoUf();
	}
	
	private void validarNovaDistribuicao(Set<DistribuicaoMunicipioDTO> distribuicao) {
		Set<DistribuicaoMunicipioDTO> inconsistencias = distribuicao.stream()
				.filter(d -> CollectionUtils.isEmpty(d.getPrestadoras()) || d.getPrestadoras().stream()
						.mapToDouble(DistribuicaoPrestadoraDTO::getPercentual).sum() != 100d)
				.collect(Collectors.toSet());

		if (!inconsistencias.isEmpty()) {
			String msg = "[redistribuirPercentuais] Percentuais de distribuição inválidos para o(s) estado(s): "
					+ inconsistencias.stream().map(DistribuicaoMunicipioDTO::getUf).collect(Collectors.joining(", "));
			throw new BusinessVPException(msg);
		}
	}
	
	private void validarNovaDistribuicao(List<ParametroPercentualDistribuicaoDTO> distribuicao) {
		List<ParametroPercentualDistribuicaoDTO> inconsistencias = distribuicao.stream()
				.filter(d -> d.getCdAgrmtVspre() == null || d.getPcDistr() == null)
				.collect(Collectors.toList());

		if (!inconsistencias.isEmpty()) {
			String msg = "[salvarNovaDistribuicao] Percentuais de distribuição inválidos: "	+ new Gson().toJson(inconsistencias);
			throw new BusinessVPException(msg);
		}
		
		double sum = distribuicao.stream().mapToDouble(ParametroPercentualDistribuicaoDTO::getPcDistr).sum();
		
		if (sum != 0 && sum != 100) {
			String msg = "[salvarNovaDistribuicao] Soma dos percentuais de distribuição deve ser igual a 0 ou 100";
			throw new BusinessVPException(msg);
		}

	}

	public List<ParametroPercentualDistribuicao> redistribuirPercentuais(Set<DistribuicaoPrestadoraDTO> novaDistribuicao,
			List<ParametroPercentualDistribuicao> percentuaisAtuais) {
		StringBuilder log = new StringBuilder();
		String newLine = " \r\n";
		
		log.append("[redistribuirPercentuais] Início \r\n");
		
		Map<Long, Double> mapDistribuicao = novaDistribuicao.stream().collect(
				Collectors.toMap(DistribuicaoPrestadoraDTO::getCodigo, DistribuicaoPrestadoraDTO::getPercentual));

		log.append("* Novos percentuais: " + new Gson().toJson(mapDistribuicao) + newLine);

		List<ParametroPercentualDistribuicao> novosPercentuais = percentuaisAtuais.stream()
		.map(this::copiar)
		.peek(p -> {
			log.append("* R:" + p.getIdRegiaoAtnmtVstro() + " P:" + p.getCdAgrmtVspre() + " PA:" + p.getPcDistr());
			if (mapDistribuicao.containsKey(p.getCdAgrmtVspre())) {
				p.setPcDistr(mapDistribuicao.get(p.getCdAgrmtVspre()));
			} else {
				p.setPcDistr(0D);
			}
			log.append(" NP:" + p.getPcDistr() + newLine);
		})
		.collect(Collectors.toList());

		double baseCalculo = novosPercentuais.stream().mapToDouble(ParametroPercentualDistribuicao::getPcDistr).sum();
		
		if (baseCalculo == 0d) {
			log.append("* Não há prestadoras para os parametros informados. Atribuindo percentual 0.0 para as existentes." + newLine);
			novosPercentuais.stream().forEach(p -> {
				p.setPcDistr(0D);
				log.append("* R:" + p.getIdRegiaoAtnmtVstro() + " P:" + p.getCdAgrmtVspre() + " PC:" + 0D + newLine);
			});
		
		} else {
			log.append("* Redistribuir percentuais, base de cálculo: " + baseCalculo + newLine);
	
			novosPercentuais.stream().forEach(p -> {
				double perc = calcular(new BigDecimal(p.getPcDistr().toString()), BigDecimal.valueOf(baseCalculo)).doubleValue();
				p.setPcDistr(perc);
				log.append("* R:" + p.getIdRegiaoAtnmtVstro() + " P:" + p.getCdAgrmtVspre() + " PC:" + perc + newLine);
			});
		}

		log.append("[redistribuirPercentuais] Fim");
		LOGGER.info(log);

		return novosPercentuais;
	}
	
	private BigDecimal calcular(BigDecimal percentualAtual, BigDecimal baseCalculo) {
		return percentualAtual.divide(baseCalculo, 2, RoundingMode.HALF_EVEN)
				.multiply(BigDecimal.valueOf(100l)).setScale(0, RoundingMode.HALF_EVEN);
	}
	
	public ParametroPercentualDistribuicao copiar(ParametroPercentualDistribuicao p) {
		ParametroPercentualDistribuicao novo = new ParametroPercentualDistribuicao();
		novo.setCdAgrmtVspre(p.getCdAgrmtVspre());
		novo.setCdRegiaoTrfra(p.getCdRegiaoTrfra());
		novo.setCdUsuroUltmaAlter(p.getCdUsuroUltmaAlter());
		novo.setDtFimVigen(p.getDtFimVigen());
		novo.setDtInicoVigen(p.getDtInicoVigen());
		novo.setDtUltmaAlter(p.getDtUltmaAlter());
		novo.setIcCapit(p.getIcCapit());
		novo.setIdParamPerctDistr(p.getIdParamPerctDistr());
		novo.setIdRegiaoAtnmtVstro(p.getIdRegiaoAtnmtVstro());
		novo.setPcDistr(p.getPcDistr());
		return novo;
	}

	public Page<ParametroPercentualDistribuicaoDTO> findAll(Map<String, Object> filtro, Pageable pageable) {
		return repository.findAll(filtro, pageable);
	}
}
