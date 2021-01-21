package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.relatorio;

import java.text.ParseException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.core.util.StringUtil;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoCusto;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.LoteImprodutivoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.LaudoImprodutivo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LaudoCustoRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LoteLaudoImprodutivoDetalheRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LoteLaudoImprodutivoRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.EmailService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.LaudoService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.ParametroVPService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilJava;

@Service
public class GeraLoteVPImprodutivaService {

	@Autowired
	private LoteLaudoImprodutivoRepository laudoImprodutivoRepository;

	@Autowired
	private LaudoCustoRepository laudoCustoRepository;

	@Autowired
	private LoteLaudoImprodutivoDetalheRepository laudoImprodutivoDetalheRepository;

	@Autowired
	private ParametroVPService parametroVPService;

	@Autowired
	private LaudoService laudoService;

	@Autowired
	private EmailService emailService;

	@Async
	@Transactional
	public void gerarLote() {
		try {

			YearMonth anoMesReferencia = parametroVPService.obterDataReferLaudosImprodutivos();
			Long mesReferencia = Long.valueOf(anoMesReferencia.getMonthValue());
			Long anoReferencia = Long.valueOf(anoMesReferencia.getYear());
			Date dataInicioReferencia = obterDataInicioLote(mesReferencia, anoReferencia);
			Date dataFimReferencia = obterDataFimLote(mesReferencia, anoReferencia);

			// Lotes já gravados na base para o mes/ano de referencia;
			List<LoteImprodutivoDTO> lotesImprodutivosAtuais = laudoImprodutivoRepository.obterLotes(mesReferencia,
					anoReferencia);

			// Lotes obtidos através das regras de laudo improdutivo
			List<LoteImprodutivoDTO> lotesImprodutivosNovos = obterLotesImprodutivos(mesReferencia, anoReferencia,
					dataInicioReferencia, dataFimReferencia);

			/** Confrontar a lista nova e a antiga para atualizar a base. */

			// Itera a lista de lotes improdutivos que já está gravada na base.
			for (LoteImprodutivoDTO loteAtual : lotesImprodutivosAtuais) {

				// Verifica se o lote ainda existe (pelo novo levantamento - regras)
				if (lotesImprodutivosNovos.contains(loteAtual)) {

					// Obtém o lote correspondente no lote novo.
					LoteImprodutivoDTO loteNovo = lotesImprodutivosNovos.get(lotesImprodutivosNovos.indexOf(loteAtual));

					// Itera a lista de laudos improdutivos que já está gravada na base.
					for (LaudoImprodutivo laudoAtual : loteAtual.getLaudosImprodutivos()) {

						// Se o laudo improdutivo ainda existe na nova lista (ainda é improdutivo)
						if (loteNovo.getLaudosImprodutivos().contains(laudoAtual)) {

							// Obtém o laudo improdutivo correspondente no lote novo.
							LaudoImprodutivo laudoNovo = loteNovo.getLaudosImprodutivos()
									.get(loteNovo.getLaudosImprodutivos().indexOf(laudoAtual));
							// Atualiza o motivo.
							laudoAtual.getDetalhe().setCdMotvImpdv(laudoNovo.getDetalhe().getCdMotvImpdv());
							laudoImprodutivoDetalheRepository.save(laudoAtual.getDetalhe());

							// Se o laudo improdutivo não existe na nova lista (não é mais improdutivo) e;
							// Não foi incluído e;
							// Não foi estornado.
						} else if (!laudoAtual.getDetalhe().getCdTipoLaudo().equals("C")
								&& !laudoAtual.getDetalhe().getCdTipoLaudo().equals("E")) {

							// Exclui o laudo deste lote.
							laudoImprodutivoDetalheRepository.delete(laudoAtual.getDetalhe());

						}
					}

					// Itera a lista de laudos improdutivos do novo levantamento
					for (LaudoImprodutivo laudoNovo : loteNovo.getLaudosImprodutivos()) {

						// Se o laudo improdutivo novo não existia na base, insere;
						if (!loteAtual.getLaudosImprodutivos().contains(laudoNovo)) {
							laudoNovo.getDetalhe().setIdLoteLaudoImpdv(loteAtual.getLote().getIdLoteLaudoImpdv());
							laudoImprodutivoDetalheRepository.save(laudoNovo.getDetalhe());
						}
					}
					// Lote atual não existe mais (pelo novo levantamento não possui mais laudos
					// improdutivos)
				} else {

					// Exclui todos os laudos improdutivos que não foram incluídos ou estornados.
					for (LaudoImprodutivo laudoAtual : loteAtual.getLaudosImprodutivos()) {
						if (!"C".equals(laudoAtual.getDetalhe().getCdTipoLaudo())
								&& !"E".equals(laudoAtual.getDetalhe().getCdTipoLaudo())) {
							laudoImprodutivoDetalheRepository.delete(laudoAtual.getDetalhe());
						}
					}

					// Se neste lote atual não existe laudos Incluídos ("C") ou estornados ("E")
					if (!possuiLaudoIncluidoOuEstornado(loteAtual.getLaudosImprodutivos())) {
						// Exclui o lote
						laudoImprodutivoRepository.delete(loteAtual.getLote());
					}
				}
			}

			// Itera a lista de lotes improdutivos novo (levantamento regra)
			for (LoteImprodutivoDTO loteNovo : lotesImprodutivosNovos) {

				// Se o lote ainda não existe
				if (!lotesImprodutivosAtuais.contains(loteNovo)) {

					// Cria
					loteNovo.setLote(laudoImprodutivoRepository.save(loteNovo.getLote()));

					// Itera a lista de laudos improdutivos novos para criar na base
					for (LaudoImprodutivo laudoNovo : loteNovo.getLaudosImprodutivos()) {
						laudoNovo.getDetalhe().setIdLoteLaudoImpdv(loteNovo.getLote().getIdLoteLaudoImpdv());
						laudoImprodutivoDetalheRepository.save(laudoNovo.getDetalhe());
					}
				}
			}

			atualizaValoresEQuantidadesLotes(null, mesReferencia, anoReferencia, dataInicioReferencia,
					dataFimReferencia);

			if (viraMesGeracaoLote(mesReferencia, anoReferencia)) {
				YearMonth proximoMesReferencia = obterProximoMesReferencia(mesReferencia, anoReferencia);
				parametroVPService.salvarDataReferLaudosImprodutivos(proximoMesReferencia);
			}
		} catch (Exception e) {
			emailService.enviarException("Erro ao gerar lote", e);
			throw new InternalServerException("[geraLote]", e);
		}
	}

	private List<LoteImprodutivoDTO> obterLotesImprodutivos(Long mesReferencia, Long anoReferencia, Date dataInicial,
			Date dataFinal) {
		// Double valorLaudo = vpImprodutivaDAO.obterCustoLaudo(mesReferencia,
		// anoReferencia);
		Double valorLaudo = obterCustoLaudo(mesReferencia, anoReferencia);

		List<LaudoImprodutivo> laudosImprodutivos = new ArrayList<LaudoImprodutivo>();

		// Obtém os laudos Aceitáveis, Liberados e com Aceitação Forçada não vinculados
		// (e bloqueados) de Proposta.
		List<LaudoImprodutivo> laudosImprodutivosProsta = laudoImprodutivoRepository
				.obterAceitaveisNaoVinculadosProposta(dataInicial, dataFinal);

		// Obtém os laudos Aceitáveis, Liberados e com Aceitação Forçada não vinculados
		// (e bloqueados) de Endosso.
		List<LaudoImprodutivo> laudosImprodutivosEndosso = laudoImprodutivoRepository
				.obterAceitaveisNaoVinculadosEndosso(dataInicial, dataFinal);

		// Obtém os laudos Aceitáveis, Liberados e com Aceitação Forçada não vinculados
		// (e bloqueados) de Endosso de Inadimplencia.
		List<LaudoImprodutivo> laudosImprodutivosEndossoInadimplencia = laudoImprodutivoRepository
				.obterAceitaveisNaoVinculadosEndossoInadimplencia(dataInicial, dataFinal);

		// Obtém os laudos repetidos com o mesmo codigo de parecer tecnico (Recusavis e
		// Sujeitos a Analise).
		List<LaudoImprodutivo> laudosRepetidos = laudoImprodutivoRepository.obterLaudoRepetido(dataInicial, dataFinal);

		// Obtém os laudos desnecessário por já ter Laudo anterior aceitável.
		List<LaudoImprodutivo> laudosDesnecessarios = laudoImprodutivoRepository.obter2LaudoDesnecessario(dataInicial,
				dataFinal);

		// Obtém os laudos nunca vinculados
		List<LaudoImprodutivo> laudosNuncaVinculados = laudoImprodutivoRepository.obterLaudosNuncaVinculados(
				dataInicial, dataFinal, obterDataFinalFechamento(mesReferencia, anoReferencia));

		// Cria uma lista única com todos os improdutivos (todos motivos)
		laudosImprodutivos.addAll(laudosImprodutivosProsta);
		laudosImprodutivos.addAll(laudosImprodutivosEndosso);
		laudosImprodutivos.addAll(laudosImprodutivosEndossoInadimplencia);
		laudosImprodutivos.addAll(laudosRepetidos);
		laudosImprodutivos.addAll(laudosDesnecessarios);
		laudosImprodutivos.addAll(laudosNuncaVinculados);

		// Ordena a lista por corretor, codLaudo e NumVersaoLaudo
		Collections.sort(laudosImprodutivos);

		// Cria a lista de lotes (por corretor e mes/ano referencia)
		List<LoteImprodutivoDTO> lotes = new ArrayList<LoteImprodutivoDTO>();
		LoteImprodutivoDTO lote = new LoteImprodutivoDTO(new LoteLaudoImprodutivo());
		Long codCorretor = null;

		String codLaudo = null;
		Long versaoLaudo = null;

		// Itera a lista de laudos improdutivos
		for (LaudoImprodutivo laudoImprodutivo : laudosImprodutivos) {
			// Para cada corretor cria um lote novo.
			if (!laudoImprodutivo.getCorretor().equals(codCorretor)) {

				codCorretor = laudoImprodutivo.getCorretor();

				lote = new LoteImprodutivoDTO(new LoteLaudoImprodutivo());

				lote.getLote().setMmRefer(mesReferencia);
				lote.getLote().setAaRefer(anoReferencia);
				lote.getLote().setCdCrtorSegur(laudoImprodutivo.getCorretor());
				lote.getLote().setCdSucsl(laudoImprodutivo.getSucursal());
				lote.getLote().setCdSupin(laudoImprodutivo.getSuperintendencia());
				lote.getLote().setQtLaudoSelec(0L);
				lote.getLote().setQtLaudoCaldo(0L);
				lote.getLote().setVlTotalOrignLote(0D);
				lote.getLote().setVlTotalLoteCaldo(0D);
				lote.getLote().setIcExclu("N");
				lote.getLote().setIcFranq("S");
				lote.getLote().setDtUltmaAlter(new Date());

				lotes.add(lote);
			}

			// Caso um laudo seja selecionado como improdutivo em mais de uma regra só
			// manterá um no lote.
			if (!laudoImprodutivo.getDetalhe().getCdLvpre().equals(codLaudo)
					|| !laudoImprodutivo.getDetalhe().getNrVrsaoLvpre().equals(versaoLaudo)) {

				codLaudo = laudoImprodutivo.getDetalhe().getCdLvpre();
				versaoLaudo = laudoImprodutivo.getDetalhe().getNrVrsaoLvpre();

				laudoImprodutivo.getDetalhe().setVlLaudo(valorLaudo);
				laudoImprodutivo.getDetalhe().setAaReferLaudo(anoReferencia);
				laudoImprodutivo.getDetalhe().setMmReferLaudo(mesReferencia);

				// Adiciona o laudo improdutivo ao lote.
				lote.getLaudosImprodutivos().add(laudoImprodutivo);
			}
		}

		return lotes;
	}

	public Date obterDataInicioLote(Long mesReferencia, Long anoReferencia) {
		String dataLote = "01";

		dataLote += StringUtils.leftPad(mesReferencia.toString(), 2, '0');
		dataLote += anoReferencia;

		try {
			return DateUtil.parseDataDDMMYYYY(dataLote);
		} catch (ParseException e) {
			return null;
		}
	}

	public Date obterDataFimLote(Long mesReferencia, Long anoReferencia) {
		// caso o mês seja novembro 11, atribui como 12 o mes final de pesquisa
		int mesDataFimReferencia = mesReferencia == 11L ? 12 : (mesReferencia.intValue() + 1) % 12;
		Long anoDataFimReferencia = mesDataFimReferencia == 1 ? anoReferencia + 1 : anoReferencia;
		String dataLote = "01" + StringUtil.lpad(String.valueOf(mesDataFimReferencia), '0', 2) + anoDataFimReferencia;

		try {
			return DateUtil.parseDataDDMMYYYY(dataLote);
		} catch (ParseException e) {
			return null;
		}
	}

	private Date obterDataFinalFechamento(Long mesReferencia, Long anoReferencia) {
		// caso o mês seja novembro 11, atribui como 12 o mes final de pesquisa
		int mesDataFimReferencia = mesReferencia == 11L ? 12 : (mesReferencia.intValue() + 1) % 12;
		Long anoDataFimReferencia = mesDataFimReferencia == 1 ? anoReferencia.intValue() + 1 : anoReferencia;
		String dataLote = "20" + StringUtil.lpad(String.valueOf(mesDataFimReferencia), '0', 2) + anoDataFimReferencia;

		try {
			return DateUtil.parseDataDDMMYYYY(dataLote);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Recupera o valor custo do laudo para o mes/ano referencia
	 *
	 * @param mesReferencia
	 * @param anoReferencia
	 *
	 * @return LaudoCusto.VlLaudoCusto
	 */
	private Double obterCustoLaudo(Long mesReferencia, Long anoReferencia) {

		Double valorLaudo = null;

		List<LaudoCusto> listaLaudoCusto = laudoCustoRepository.carregarCustoVPImprodutiva(mesReferencia,
				anoReferencia);
		if (UtilJava.trueVar(listaLaudoCusto)) {
			valorLaudo = listaLaudoCusto.get(0).getVlLaudoCusto();
		} else {
			// * Recupera o valorLaudoCusto do ultimo mês cadastrado
			listaLaudoCusto = laudoCustoRepository.carregarCustoVPImprodutiva(null, null);
			valorLaudo = listaLaudoCusto.get(0).getVlLaudoCusto();
		}

		return valorLaudo;
	}

	private Boolean possuiLaudoIncluidoOuEstornado(List<LaudoImprodutivo> laudos) {
		for (LaudoImprodutivo laudo : laudos) {
			if ("C".equals(laudo.getDetalhe().getCdTipoLaudo()) || "E".equals(laudo.getDetalhe().getCdTipoLaudo())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Atualiza valores totais e de franquia dos lotes.
	 * 
	 * @param idLote
	 * @param mesReferencia
	 * @param anoReferencia
	 * @param dataInicio
	 * @param dataFim
	 * @param dataInicioReferencia
	 * @param dataFimReferencia
	 */
	@Transactional
	public void atualizaValoresEQuantidadesLotes(Long idLote, Long mesReferencia, Long anoReferencia) {
		Date dataInicioLote = obterDataInicioLote(mesReferencia, anoReferencia);
		Date dataFimLote = obterDataFimLote(mesReferencia, anoReferencia);
		atualizaValoresEQuantidadesLotes(idLote, mesReferencia, anoReferencia, dataInicioLote, dataFimLote);
	}

	/**
	 * Atualiza valores totais e de franquia dos lotes.
	 * 
	 * @param idLote
	 * @param mesReferencia
	 * @param anoReferencia
	 * @param dataInicio
	 * @param dataFim
	 * @param dataInicioReferencia
	 * @param dataFimReferencia
	 */
	private void atualizaValoresEQuantidadesLotes(Long idLote, Long mesReferencia, Long anoReferencia, Date dataInicio,
			Date dataFim) {
		// Atualiza valores de todos os l audos calculados
		laudoImprodutivoRepository.atualizaValoresEQuantidadesLotes(idLote, mesReferencia, anoReferencia);
		laudoImprodutivoRepository.atualizaFlagDeFranquia(idLote, mesReferencia, anoReferencia, dataInicio, dataFim);

		// Atualiza valores de franquia dos laudos que estão fora da franquia do
		// corretor.
		List<LoteLaudoImprodutivo> loteLaudoImprodutivo = laudoImprodutivoRepository.obterLotesForaFranquia(idLote,
				mesReferencia, anoReferencia);

		Double custoLadoCalculado = obterCustoLaudo(mesReferencia, anoReferencia);

		Double valorTotalLoteCalculado = 0D;
		String icFranquia = null;
		Double percentualFranquia = laudoImprodutivoRepository.obterPercentualFranquiaCorretor(99999L);

		for (LoteLaudoImprodutivo loteLaudoImprodutivoFranq : loteLaudoImprodutivo) {

			Long quatidadeLaudoCalculados = loteLaudoImprodutivoFranq.getQtLaudoCaldo();

			Long totalVistoriasCorretor = laudoService
					.recuperarQtdTotalVistoria(loteLaudoImprodutivoFranq.getCdCrtorSegur(), dataInicio, dataFim);

			icFranquia = loteLaudoImprodutivoFranq.getIcFranq();

			Long quantidadePermitida = new Double(Math.ceil((totalVistoriasCorretor * percentualFranquia / 100)))
					.longValue();

			Long quatidadeLaudoCalculadoRes = (quatidadeLaudoCalculados <= quantidadePermitida) ? 0L
					: (quatidadeLaudoCalculados - quantidadePermitida);

			valorTotalLoteCalculado = (custoLadoCalculado * quatidadeLaudoCalculadoRes);

			if (quatidadeLaudoCalculadoRes <= 0L) {
				icFranquia = "S";
			}

			laudoImprodutivoRepository.atualizaValorCalculado(loteLaudoImprodutivoFranq.getIdLoteLaudoImpdv(),
					valorTotalLoteCalculado, quatidadeLaudoCalculadoRes, icFranquia);

			valorTotalLoteCalculado = 0D;
		}
	}

	private Boolean viraMesGeracaoLote(Long mesReferencia, Long anoReferencia) {
		if (mesReferencia.equals(12L)) {
			mesReferencia = 1L;
			anoReferencia++;
		} else {
			mesReferencia++;
		}

		String mes = StringUtil.lpad(mesReferencia.toString(), '0', 2);

		Date dataSistema = new Date();
		Date dataVirada = null;

		try {
			dataVirada = DateUtil.parseDataDDMMYYYY("21" + mes + anoReferencia);
		} catch (ParseException e) {
		}

		if (dataSistema.after(dataVirada)) {
			return true;
		}

		return false;
	}

	private YearMonth obterProximoMesReferencia(Long mesReferencia, Long anoReferencia) {
		if (mesReferencia.equals(12L)) {
			mesReferencia = 1L;
			anoReferencia++;
		} else {
			mesReferencia++;
		}

		return YearMonth.of(anoReferencia.intValue(), mesReferencia.intValue());
	}
}