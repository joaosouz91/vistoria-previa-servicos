package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.relatorio;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.Acxcdpa0020001RecLocaisUser;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.ConsultaCorretorRequest;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.ConsultaCorretorResponse;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.LocaisServiceUser_pGetLocais_Out;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.LocaisServiceUser_pGetLochieraq_Out;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.ejb.AcselXRestEJBClient;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.service.ParametrosService;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivo;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivoDetalhe;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPreviaGeral;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.SuperintendenciaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.LoteLaudoImprodutivoAux;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.LoteLaudoImprodutivoDetalheAux;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.RelatorioImprodutividadeCorretorDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.RelatorioImprodutividadeDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.RelatorioImprodutivoFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Corretor;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.Sucursal;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.VisaoRelatorioImprodutivoEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.ExternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NoContentException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.VistoriaBindException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.CorretorService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.LaudoImprodutivoPercentualService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.LaudoService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.LoteLaudoImprodutivoDetalheService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.LoteLaudoImprodutivoService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.MessageUtil;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.ParametroVPService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilJava;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilNegocio;

@Service
public class RelatorioVistoriaImprodutivaService {
	
	private static final Logger LOGGER = LogManager.getLogger(RelatorioVistoriaImprodutivaService.class);

	@Autowired
	private GeraLoteVPImprodutivaService geraLoteVPImprodutivaService;

	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private LoteLaudoImprodutivoService loteLaudoImprodutivoService;

	@Autowired
	private LoteLaudoImprodutivoDetalheService loteLaudoImprodutivoDetalheService;

	@Autowired
	private LaudoImprodutivoPercentualService laudoImprodutivoPercentualService;

	@Autowired
	private LaudoService laudoService;

	@Autowired
	private AcselXRestEJBClient acselXRestEJBClient;

	@Autowired
	private CorretorService corretorService;

	@Autowired
	private ParametroVPService parametroVPService;
	
	@Autowired
	private ParametrosService parametrosService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RestTemplate restTemplate;

	public List<SuperintendenciaDTO> obterSuperintendencias() {
		try {
			List<SuperintendenciaDTO> superintendenciaDTOs = new ArrayList<>();

			LocaisServiceUser_pGetLocais_Out locais = acselXRestEJBClient
					.consultaLocais("RPSSA          24000000000000001", 3L, 1L, 99L);

			if (locais != null && locais.getPolocaisOut() != null) {
				for (int i = 0; i < locais.getPolocaisOut().length; i++) {

					Acxcdpa0020001RecLocaisUser tipo = new Acxcdpa0020001RecLocaisUser();
					tipo = locais.getPolocaisOut()[i];

					if (tipo.getDcEnceLoc() == null) {
						superintendenciaDTOs
								.add(new SuperintendenciaDTO(Long.valueOf(tipo.getCdLocadm()), tipo.getNmLoc().trim()));
					}
				}
			}

			Collections.sort(superintendenciaDTOs, new Comparator<SuperintendenciaDTO>() {

				public int compare(SuperintendenciaDTO s1, SuperintendenciaDTO s2) {

					return s1.getCodigo().compareTo(s2.getCodigo());
				}
			});

			return superintendenciaDTOs;
		} catch (Exception e) {
			throw new ExternalServerException("Erro ao consultar Superintendências", e);
		}
	}

	public List<Sucursal> obterSucursais() {
		List<SuperintendenciaDTO> listaSuperintendencia = obterSuperintendencias();

		List<Sucursal> listaSucursais = new ArrayList<Sucursal>();

		for (SuperintendenciaDTO superintendenciaDTO : listaSuperintendencia) {
			List<Sucursal> listaSucursaisSuperintendenciaAtual = obterSucursaisSuperintendencia(
					superintendenciaDTO.getCodigo());
			listaSucursais.addAll(listaSucursaisSuperintendenciaAtual);
		}

		// ordena pelo código da sucursal
		Collections.sort(listaSucursais, new Comparator<Sucursal>() {
			public int compare(Sucursal s1, Sucursal s2) {
				return s1.getCdSucursal().compareTo(s2.getCdSucursal());
			}
		});

		return listaSucursais;
	}

	private List<Sucursal> obterSucursaisSuperintendencia(final Long codSuperintendencia) {

		final String chaveBusca = "RPSSA          2400000000000" + codSuperintendencia + "23";
		final Long parametro2 = 2L;
		final Long parametro1 = 1L;

		List<Sucursal> sucursais = new ArrayList<Sucursal>();

		try {
			final LocaisServiceUser_pGetLochieraq_Out locais = acselXRestEJBClient
					.consultaLocaisHierarquicos(chaveBusca, parametro2, parametro1);

			if (locais != null && locais.getPolochierarqOut() != null) {

				for (int i = 0; i < locais.getPolochierarqOut().length; i++) {
					if (locais.getPolochierarqOut()[i].getDataencerra() == null) {
						Sucursal sucursal = new Sucursal();
						sucursal.setCdSuperintendencia(codSuperintendencia);
						sucursal.setCdSucursal(Long.valueOf(locais.getPolochierarqOut()[i].getCodlocadm()));
						sucursal.setDsSucursal(locais.getPolochierarqOut()[i].getDesclocal().trim());
						sucursais.add(sucursal);
					}
				}

				TreeSet<Sucursal> treeSet = new TreeSet<Sucursal>(new Comparator<Sucursal>() {
					public int compare(Sucursal s1, Sucursal s2) {
						return s1.getCdSucursal().compareTo(s2.getCdSucursal());
					}
				});

				treeSet.addAll(sucursais);
				sucursais = new ArrayList<Sucursal>(treeSet);
			}
		} catch (Exception e) {
			throw new ExternalServerException("Erro ao obter as sucursais das superintendencias.", e);
		}

		return sucursais;
	}

	@Transactional
	public RelatorioImprodutividadeDTO obterLotesVistoriasImprodutivas(RelatorioImprodutivoFiltro filtro)
			throws VistoriaBindException {
		filtro = prepararfiltro(filtro);

		List<LoteLaudoImprodutivoAux> lotes = loteLaudoImprodutivoService.carregarLotesImprodutivos(filtro);

		boolean permitirEdicao = loteLaudoImprodutivoService.permitirEdicao(filtro.getDataReferencia());

		if (lotes.isEmpty()) {

			// * Para Visao_Pesquisa = CORRETOR e Mes/Ano NÃO TRANSMITIDO.
			// * Cria Lote para corretor poder incluir e estornar laudos.
			if (VisaoRelatorioImprodutivoEnum.COD_VISAO_CORRETOR == filtro.getVisao() && permitirEdicao) {

				LoteLaudoImprodutivoAux loteAux = this.criarNovoLote(filtro.getCorretor(), filtro.getDataReferencia());
				lotes.add(loteAux);
			} else {
				throw new NoContentException(messageUtil.get("alerta.relatorio.improdutiva.registro.nao.encontrado"));
			}
		}

		// * Recupera Corretor.
		addNomeCorretor(lotes);

		return calcularRelatorioImprodutividadeDTO(lotes, filtro.getDataReferencia(), permitirEdicao);
	}

	private RelatorioImprodutividadeDTO calcularRelatorioImprodutividadeDTO(
			List<LoteLaudoImprodutivoAux> lotes, YearMonth dataReferencia, boolean permitirEdicao) {
		Double valorTotalImprodutivoFinal = 0d;
		Double valorTotalImprodutivoOriginal = 0d;
		Double valorTotalLaudoCalculado = 0d;
		Double valorTotalLaudoIncluido = 0d;
		Double valorTotalLaudoEstornado = 0d;

		Long qtdTotalImprodutivoOriginal = 0l;
		Long qtdTotalLaudoCalculado = 0l;
		Long qtdTotalLaudoIncluido = 0l;
		Long qtdTotalLaudoEstornado = 0l;

		// * Somatorio dos valores/quantidades.
		for (LoteLaudoImprodutivoAux lote : lotes) {

			// * Recupera Quantidades e Valores originais(objeto da base) do Lote.
			this.carregarQtdValoresLote(lote);
			// * Recupera Somatorio Final de Improdutivas.(Calculados + Incluidos -
			// Estornados)
			this.calcularTotalFinalImprodutiva(lote);

			// * Somar Total Calculado senão estiver dentro da franquia
			if (ConstantesVistoriaPrevia.COD_IND_NAO.equals(lote.getLoteLaudoImprodutivo().getIcFranq())) {
				qtdTotalLaudoCalculado += lote.getQtdTotalLaudoCalculado();
				valorTotalLaudoCalculado += lote.getValorTotalLaudoCalculado();
			}

			qtdTotalImprodutivoOriginal += lote.getQtdTotalImprodutivoOriginal();
			qtdTotalLaudoIncluido += lote.getQtdTotalLaudoIncluido();
			qtdTotalLaudoEstornado += lote.getQtdTotalLaudoEstornado();

			valorTotalImprodutivoOriginal += lote.getValorTotalImprodutivoOriginal();
			valorTotalLaudoIncluido += lote.getValorTotalLaudoIncluido();
			valorTotalLaudoEstornado += lote.getValorTotalLaudoEstornado();
		}

		valorTotalImprodutivoFinal += valorTotalLaudoCalculado + valorTotalLaudoIncluido - valorTotalLaudoEstornado;

		RelatorioImprodutividadeDTO relatorioImprodutividade = new RelatorioImprodutividadeDTO();

		relatorioImprodutividade.setListaLoteImprodutivoAux(lotes);
		relatorioImprodutividade.setDataReferencia(dataReferencia);
		relatorioImprodutividade
				.setDescricaoDataReferencia(UtilNegocio.getNomeMes(dataReferencia.getMonthValue()));

		relatorioImprodutividade.setQtdTotalImprodutivoOriginal(qtdTotalImprodutivoOriginal);
		relatorioImprodutividade.setQtdTotalLaudoCalculado(qtdTotalLaudoCalculado);
		relatorioImprodutividade.setQtdTotalLaudoIncluido(qtdTotalLaudoIncluido);
		relatorioImprodutividade.setQtdTotalLaudoEstornado(qtdTotalLaudoEstornado);

		relatorioImprodutividade.setValorTotalImprodutivoOriginal(valorTotalImprodutivoOriginal);
		relatorioImprodutividade.setValorTotalLaudoCalculado(valorTotalLaudoCalculado);
		relatorioImprodutividade.setValorTotalLaudoIncluido(valorTotalLaudoIncluido);
		relatorioImprodutividade.setValorTotalLaudoEstornado(valorTotalLaudoEstornado);

		relatorioImprodutividade.setValorTotalImprodutivoFinal(valorTotalImprodutivoFinal);

		// * Permite Edição de Lotes desde que mes/ano NÃO tenha sido transmitido.
		relatorioImprodutividade.setPermiteEdicao(permitirEdicao);

		if (permitirEdicao) {
			// * Habilita Botão transmitir
			relatorioImprodutividade.setPermiteTransmissao(this.permitirTransmissao(dataReferencia));
		}

		return relatorioImprodutividade;
	}

	private void addNomeCorretor(LoteLaudoImprodutivoAux lote) {
		lote.setNomeCorretor("CORRETOR NÃO ENCONTRADO");
		try {
			String nomeCorretor = corretorService.obterNomeCorretor(lote.getLoteLaudoImprodutivo().getCdCrtorSegur());

			if (nomeCorretor != null) {
				lote.setNomeCorretor(nomeCorretor);
			}
		} catch (Exception e) {
			LOGGER.error("[Relatorio Vistoria Improdutiva] Erro ao consultar nome do corretor: ", ExceptionUtils.getRootCause(e).getMessage());
		}
	}
	
	private void addNomeCorretor(List<LoteLaudoImprodutivoAux> lotes) {
		try {
			final String urlWSConsultaCorretor = parametrosService
					.buscarParametroAceitacao("REST.ACSEL.CONSULTA.CORRETOR");

			lotes.parallelStream().forEach(lote -> {
				try {
					lote.setNomeCorretor("CORRETOR NÃO ENCONTRADO");
					
					final String chaveCorretor = StringUtils
							.leftPad(lote.getLoteLaudoImprodutivo().getCdCrtorSegur().toString(), 6, "0");
					
					ConsultaCorretorRequest consultaCorretorRequest = new ConsultaCorretorRequest();
					consultaCorretorRequest.setP_chave(chaveCorretor);
					consultaCorretorRequest.setP_op_consulta(2L);
					consultaCorretorRequest.setP_tp_acesso(1L);
					consultaCorretorRequest.setP_qtd_reg(1L);
					
					HttpHeaders headers = new HttpHeaders();
					headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
					headers.setContentType(MediaType.APPLICATION_JSON);
					
					final HttpEntity<ConsultaCorretorRequest> requestBody = new HttpEntity<>(consultaCorretorRequest,
							headers);

					final ConsultaCorretorResponse consultaCorretorResponse = restTemplate
							.postForObject(urlWSConsultaCorretor, requestBody, ConsultaCorretorResponse.class);

					if (consultaCorretorResponse != null && !consultaCorretorResponse.getPo_reg().isEmpty()) {
						lote.setNomeCorretor(consultaCorretorResponse.getPo_reg().get(0).getNomter());
					}
					
				} catch (Exception e) {
					LOGGER.error("[Relatorio Vistoria Improdutiva] Erro ao consultar nome do corretor: ",
							ExceptionUtils.getRootCause(e).getMessage());
				}
			});
		} catch (Exception e) {
			LOGGER.error("[Relatorio Vistoria Improdutiva] Erro ao consultar nome do corretor: ",
					ExceptionUtils.getRootCause(e).getMessage());
		}
	}

	private RelatorioImprodutivoFiltro prepararfiltro(RelatorioImprodutivoFiltro filtro) throws VistoriaBindException {
		RelatorioImprodutivoFiltro novoFiltro = new RelatorioImprodutivoFiltro();

		VistoriaBindException bindException = new VistoriaBindException(filtro, "filtro");

		if (filtro.getVisao() == null) {
			bindException.rejectValue(RelatorioImprodutivoFiltro.Fields.visao, null,
					messageUtil.get("erro.relatorio.improdutiva.visao.nao.informado"));
		} else {
			novoFiltro.setVisao(filtro.getVisao());
		}

		if (VisaoRelatorioImprodutivoEnum.COD_VISAO_SUPERINTENDENCIA == filtro.getVisao()) {
			if (!UtilJava.trueVar(filtro.getSuperintendencia())) {
				bindException.rejectValue(RelatorioImprodutivoFiltro.Fields.superintendencia, null,
						messageUtil.get("erro.relatorio.improdutiva.superintendencia.nao.informado"));
			} else {
				novoFiltro.setSuperintendencia(filtro.getSuperintendencia());
			}
		}

		if (VisaoRelatorioImprodutivoEnum.COD_VISAO_SUCURSAL == filtro.getVisao()) {
			if (!UtilJava.trueVar(filtro.getSucursal())) {
				bindException.rejectValue(RelatorioImprodutivoFiltro.Fields.sucursal, null,
						messageUtil.get("erro.relatorio.improdutiva.sucursal.nao.informado"));
			} else {
				novoFiltro.setSucursal(filtro.getSucursal());
			}
		}

		if (VisaoRelatorioImprodutivoEnum.COD_VISAO_CORRETOR == filtro.getVisao()) {
			if (!UtilJava.trueVar(filtro.getCorretor())) {
				bindException.rejectValue(RelatorioImprodutivoFiltro.Fields.corretor, null,
						messageUtil.get("erro.relatorio.improdutiva.corretor.nao.informado"));
			} else {
				novoFiltro.setCorretor(filtro.getCorretor());
			}
		}

		if (VisaoRelatorioImprodutivoEnum.COD_VISAO_VISTORIA_PREVIA == filtro.getVisao()) {
			if (StringUtils.isBlank(filtro.getLaudo()) && StringUtils.isBlank(filtro.getVoucher())
					&& filtro.getDataVistoria() == null && filtro.getParecerTecnico() == null) {
				bindException.reject(null, messageUtil.get("erro.relatorio.improdutiva.vistoria.previa.nao.informado"));
			} else {
				novoFiltro.setLaudo(filtro.getLaudo());
				novoFiltro.setVoucher(filtro.getVoucher());
				novoFiltro.setDataVistoria(filtro.getDataVistoria());
				novoFiltro.setParecerTecnico(filtro.getParecerTecnico());
			}
		}

		if (VisaoRelatorioImprodutivoEnum.COD_VISAO_VEICULO == filtro.getVisao()) {
			if (StringUtils.isBlank(filtro.getPlaca()) && StringUtils.isBlank(filtro.getChassi())) {
				bindException.reject(null, messageUtil.get("erro.relatorio.improdutiva.veiculo.nao.informado"));
			} else {
				novoFiltro.setPlaca(filtro.getPlaca());
				novoFiltro.setChassi(filtro.getChassi());
			}
		}

		if (filtro.getDataReferencia() == null) {
			bindException.reject(null, messageUtil.get("erro.relatorio.improdutiva.mes.ano.refencia.nao.informado"));
		} else {
			novoFiltro.setDataReferencia(filtro.getDataReferencia());
		}

		if (bindException.hasErrors()) {
			throw bindException;
		}

		return novoFiltro;
	}

	/**
	 * Verifica se Lote pode ser transmitido.
	 */
	private boolean permitirTransmissao(YearMonth ref) {

		if (!loteLaudoImprodutivoService.permitirEdicao(ref)) {
			return false;
		}

		try {
			ParametroVistoriaPreviaGeral parametro = parametroVPService
					.obterParametroVistoriaPreviaGeral("DATA_REF_TRANSMITIR_LAUDOS_IMPRODUTIVOS").get(0);
			String dataMesAno = parametro.getVlParamVspre();

			Long mesTransmissao = Long.parseLong(dataMesAno.split("/")[0]);
			Long anoTransmissao = Long.parseLong(dataMesAno.split("/")[1]);

			Date dataInicLote = geraLoteVPImprodutivaService.obterDataInicioLote(new Long(ref.getMonthValue()),
					new Long(ref.getYear()));
			Date dataInicTransmissao = geraLoteVPImprodutivaService.obterDataInicioLote(mesTransmissao, anoTransmissao);

			if (dataInicLote.compareTo(dataInicTransmissao) >= 0) {
				return true;
			}

		} catch (Exception e) {
			throw new NullPointerException(
					messageUtil.get("erro.relatorio.improdutividade.data.referencia.transmissao"));
		}

		return false;
	}

	/**
	 * Cria novo Lote, para o caso de corretor sem improdutivas para o mês e ano.
	 */
	private LoteLaudoImprodutivoAux criarNovoLote(Long codCorretor, YearMonth referencia) {
		Corretor corretor = corretorService.obterCorretor(codCorretor)
				.orElseThrow(() -> new BusinessVPException("Código de corretor " + codCorretor + " inválido"));

		LoteLaudoImprodutivo loteLaudoImprodutivo = new LoteLaudoImprodutivo();

		loteLaudoImprodutivo.setCdCrtorSegur(codCorretor);

		Long mesReferencia = new Long(referencia.getMonthValue());
		Long anoReferencia = new Long(referencia.getYear());
		loteLaudoImprodutivo.setMmRefer(mesReferencia);
		loteLaudoImprodutivo.setAaRefer(anoReferencia);

		if (StringUtils.isNotEmpty(corretor.getCodSucursal())) {
			Long codSucursal = Long.valueOf(corretor.getCodSucursal());
			loteLaudoImprodutivo.setCdSucsl(codSucursal);

			Sucursal sucursal = obterSucursais().stream().filter(s -> s.getCdSuperintendencia().equals(codSucursal))
					.findFirst()
					.orElseThrow(() -> new BusinessVPException("Código de sucursal " + codSucursal + " inválido"));

			loteLaudoImprodutivo.setCdSupin(sucursal.getCdSuperintendencia());
		} else {
			throw new BusinessVPException(messageUtil.get("erro.relatorio.improdutiva.sucursal.nao.informado"));
		}

		loteLaudoImprodutivo.setQtLaudoSelec(0l);
		loteLaudoImprodutivo.setQtLaudoCaldo(0l);
		loteLaudoImprodutivo.setVlTotalOrignLote(0d);
		loteLaudoImprodutivo.setIcExclu("N");
		loteLaudoImprodutivo.setIcFranq("S");
		loteLaudoImprodutivo.setDtUltmaAlter(new Date());

		loteLaudoImprodutivo = loteLaudoImprodutivoService.save(loteLaudoImprodutivo);

		geraLoteVPImprodutivaService.atualizaValoresEQuantidadesLotes(loteLaudoImprodutivo.getIdLoteLaudoImpdv(),
				mesReferencia, anoReferencia);

		LoteLaudoImprodutivoAux loteAux = new LoteLaudoImprodutivoAux(loteLaudoImprodutivo,
				loteLaudoImprodutivo.getIcFranq());

		return loteAux;
	}

	/**
	 * Recupera Quantidades e Valores originais(objeto da base) do Lote.
	 */
	private void carregarQtdValoresLote(LoteLaudoImprodutivoAux loteImprodutivoAux) {

		LoteLaudoImprodutivo loteImprodutivo = loteImprodutivoAux.getLoteLaudoImprodutivo();

		loteImprodutivoAux.setQtdTotalImprodutivoOriginal(
				loteImprodutivo.getQtLaudoSelec() != null ? loteImprodutivo.getQtLaudoSelec() : 0l);
		loteImprodutivoAux.setQtdTotalLaudoCalculado(
				loteImprodutivo.getQtLaudoCaldo() != null ? loteImprodutivo.getQtLaudoCaldo() : 0l);
		loteImprodutivoAux.setQtdTotalLaudoIncluido(
				loteImprodutivo.getQtTotalLaudoInclo() != null ? loteImprodutivo.getQtTotalLaudoInclo() : 0l);
		loteImprodutivoAux.setQtdTotalLaudoEstornado(
				loteImprodutivo.getQtTotalLaudoEstor() != null ? loteImprodutivo.getQtTotalLaudoEstor() : 0l);

		loteImprodutivoAux.setValorTotalImprodutivoOriginal(
				loteImprodutivo.getVlTotalOrignLote() != null ? loteImprodutivo.getVlTotalOrignLote() : 0d);
		loteImprodutivoAux.setValorTotalLaudoCalculado(
				loteImprodutivo.getVlTotalLoteCaldo() != null ? loteImprodutivo.getVlTotalLoteCaldo() : 0d);
		loteImprodutivoAux.setValorTotalLaudoIncluido(
				loteImprodutivo.getVlTotalLaudoInclo() != null ? loteImprodutivo.getVlTotalLaudoInclo() : 0d);
		loteImprodutivoAux.setValorTotalLaudoEstornado(
				loteImprodutivo.getVlTotalLaudoEstor() != null ? loteImprodutivo.getVlTotalLaudoEstor() : 0d);
	}

	/**
	 * Recupera Somatorio Final de Improdutivas.(Calculados + Incluidos -
	 * Estornados)
	 */
	private void calcularTotalFinalImprodutiva(LoteLaudoImprodutivoAux loteLaudoImprodutivoAux) {

		Double valorTotalLaudoCalculado = 0d;
		Long qtdTotalLaudoCalculado = 0l;

		// * Somar Total Calculado senão estiver dentro da franquia
		if (ConstantesVistoriaPrevia.COD_IND_NAO.equals(loteLaudoImprodutivoAux.getIcFranqAux())) {
			valorTotalLaudoCalculado = loteLaudoImprodutivoAux.getValorTotalLaudoCalculado();
			qtdTotalLaudoCalculado = loteLaudoImprodutivoAux.getQtdTotalLaudoCalculado();
		}

		Double valorTotalIncluido = loteLaudoImprodutivoAux.getValorTotalLaudoIncluido();
		Double valorTotalEstornado = loteLaudoImprodutivoAux.getValorTotalLaudoEstornado();

		Double valorTotalFinal = valorTotalLaudoCalculado + valorTotalIncluido - valorTotalEstornado;

		loteLaudoImprodutivoAux.setValorTotalImprodutivoFinal(valorTotalFinal);

		loteLaudoImprodutivoAux.setQtdTotalLaudoCalculado(qtdTotalLaudoCalculado);
		loteLaudoImprodutivoAux.setValorTotalLaudoCalculado(valorTotalLaudoCalculado);
	}

	public RelatorioImprodutividadeCorretorDTO obterLoteVistoriasImprodutivas(Long id) {
		RelatorioImprodutividadeCorretorDTO relatorioImprodutivoTO = new RelatorioImprodutividadeCorretorDTO();
		LoteLaudoImprodutivoAux loteImprodutivoAux = new LoteLaudoImprodutivoAux();

		LoteLaudoImprodutivo loteImprodutivo = loteLaudoImprodutivoService.findById(id)
				.orElseThrow(() -> new NoContentException("Código de lote " + id + " inválido"));
		loteImprodutivoAux.setLoteLaudoImprodutivo(loteImprodutivo);

		addNomeCorretor(loteImprodutivoAux);

		Date dataReferencia = loteImprodutivo.getDtEnvioLote();
		if (!UtilJava.trueVar(dataReferencia)) {
			dataReferencia = new Date();
		}
		// * Recupera a Percentagem Permitida de Improdutivas para Corretor.
		Double pctPermitidoImprodutiva = laudoImprodutivoPercentualService.recuperarPercentualImprodutivaReferencia(
				loteImprodutivo.getCdCrtorSegur(), loteImprodutivo.getCdSucsl(), dataReferencia);
		loteImprodutivoAux.setPctPermitidoImprodutiva(pctPermitidoImprodutiva);

		Date dataInicioLote = geraLoteVPImprodutivaService.obterDataInicioLote(loteImprodutivo.getMmRefer(),
				loteImprodutivo.getAaRefer());
		Date dataFimLote = geraLoteVPImprodutivaService.obterDataFimLote(loteImprodutivo.getMmRefer(),
				loteImprodutivo.getAaRefer());

		// * Recupera Quantidade Total de Vistorias por Corretor.
		Long qtdTotalVistoria = laudoService.recuperarQtdTotalVistoria(loteImprodutivo.getCdCrtorSegur(),
				dataInicioLote, dataFimLote);
		loteImprodutivoAux.setQtdTotalVistoria(qtdTotalVistoria);

		// * Recupera Quantidades e Valores Originais(objeto da base) do Lote.
		this.carregarQtdValoresLote(loteImprodutivoAux);

		// * Carrega Map com a descrição dos motivos de improdutivas.
		Map<String, String> mapMensagemImprodutiva = parametroVPService.carregarMensagemImprodutiva();

		// * Recupera Lista de Detalhes do Lote.
		recuperarDetalheLote(loteImprodutivoAux, mapMensagemImprodutiva);
		// * Recupera Lista de Laudos Adicionais (Incluidos/Estornados)
		this.recuperarDetalheLoteAdicional(loteImprodutivoAux, mapMensagemImprodutiva);
		// * Recupera Somatorio Final de Improdutivas.(Calculados + Incluidos -
		// Estornados)
		this.calcularTotalFinalImprodutiva(loteImprodutivoAux);

		relatorioImprodutivoTO.setExibirPesquisaAdicional(false);
		loteImprodutivoAux.setListaPesquisaLaudoAdicional(new ArrayList<LoteLaudoImprodutivoDetalheAux>());

		relatorioImprodutivoTO.setLoteImprodutivoAux(loteImprodutivoAux);

		int mesRef = loteImprodutivoAux.getLoteLaudoImprodutivo().getMmRefer().intValue();
		YearMonth dtReferencia = YearMonth.of(loteImprodutivoAux.getLoteLaudoImprodutivo().getAaRefer().intValue(),
				mesRef);

		relatorioImprodutivoTO.setDescricaoDataReferencia(UtilNegocio.getNomeMes(mesRef));
		// * Monta lista com meses/anos antetiores para select que irá aparecerá na
		// <DIV> para pesquisa de Laudos que podem ser Incluidos/Estornados.
		// * Remove Mes Corrente da lista para pesquisa de Laudos Adicionais.
		relatorioImprodutivoTO
				.setListaMesAnoAdicional(loteLaudoImprodutivoService.recuperarListaMesAnoAdicional(dtReferencia));

		// * Verifica se mês selecionado é o mês corrente - Bloqueia teclas para edição
		// dos lotes dos meses anteriores.
		relatorioImprodutivoTO.setPermiteEdicao(loteLaudoImprodutivoService.permitirEdicao(dtReferencia));

		return relatorioImprodutivoTO;
	}

	/**
	 * Recupera Lista de Detalhes do Lote.
	 */
	private void recuperarDetalheLote(LoteLaudoImprodutivoAux loteImprodutivoAux,
			Map<String, String> mapMensagemImprodutiva) {

		Long idLoteSelecionado = loteImprodutivoAux.getLoteLaudoImprodutivo().getIdLoteLaudoImpdv();

		// Apenas Laudos com cdTipoLaudo = I
		List<LoteLaudoImprodutivoDetalheAux> listaLoteDetalhe = loteLaudoImprodutivoDetalheService
				.carregarDetalheLoteImprodutivo(idLoteSelecionado, "I", null);
		/*
		 * if (!UtilJava.trueVar(listaLoteDetalhe)) { throw new VistoriaPreviaException(
		 * "erro.relatorio.improdutividade.detalhe.lote.nao.encontrado",
		 * ConstantesVistoriaPrevia.TP_MSG_ERRO); }
		 */

		Double valorTotalLaudoRetirado = 0d;

		for (LoteLaudoImprodutivoDetalheAux detalhe : listaLoteDetalhe) {
			detalhe.setDescMotivoImprodutiva(mapMensagemImprodutiva.get(detalhe.getCodMotivoImprodutiva()));
			detalhe.setDescStatusLaudo(UtilNegocio.retornaDescSitucVsPreLaudo(detalhe.getCodStatusLaudo()));

			// Somatorio dos Laudos com Situação RETIRADO.
			if (ConstantesVistoriaPrevia.COD_IND_SIM.equals(detalhe.getLoteLaudoImprodutivoDetalhe().getIcExclu())) {
				valorTotalLaudoRetirado += detalhe.getLoteLaudoImprodutivoDetalhe().getVlLaudo();
			}
		}

		loteImprodutivoAux.setListaLoteDetalhe(listaLoteDetalhe);
		loteImprodutivoAux.setValorTotalLaudoRetirado(valorTotalLaudoRetirado);
	}

	/**
	 * Recupera Lista de Laudos Adicionais (Incluidos/Estornados)
	 */
	private void recuperarDetalheLoteAdicional(LoteLaudoImprodutivoAux loteImprodutivoAux,
			Map<String, String> mapMensagemImprodutiva) {
		Long idLoteSelecionado = loteImprodutivoAux.getLoteLaudoImprodutivo().getIdLoteLaudoImpdv();

		Long qtdTotalLaudoIncluido = 0l;
		Long qtdTotalLaudoEstornado = 0l;

		Double valorTotalLaudoIncluido = 0d;
		Double valorTotalLaudoEstornado = 0d;

		// * Apenas Laudos com cdTipoLaudo IN (C,E)
		List<LoteLaudoImprodutivoDetalheAux> listaLoteDetalheAdicional = loteLaudoImprodutivoDetalheService
				.carregarDetalheLoteImprodutivo(idLoteSelecionado, "C&E", null);

		for (LoteLaudoImprodutivoDetalheAux detalhe : listaLoteDetalheAdicional) {

			detalhe.setDescMotivoImprodutiva(mapMensagemImprodutiva.get(detalhe.getCodMotivoImprodutiva()));
			detalhe.setDescStatusLaudo(UtilNegocio.retornaDescSitucVsPreLaudo(detalhe.getCodStatusLaudo()));

			if (ConstantesVistoriaPrevia.COD_TIPO_LAUDO_IMPRODUTIVO_INCLUIDO
					.equals(detalhe.getLoteLaudoImprodutivoDetalhe().getCdTipoLaudo())) {
				detalhe.setDescTipoLaudo("Inclusão");
				// * Somatorio Valores/Qtd dos Laudos com Situação INCLUIDO.
				qtdTotalLaudoIncluido += 1;
				valorTotalLaudoIncluido += detalhe.getLoteLaudoImprodutivoDetalhe().getVlLaudo();
			} else {
				detalhe.setDescTipoLaudo("Estorno");
				// * Somatorio Valores/Qtd dos Laudos com Situação ESTORNAD.
				qtdTotalLaudoEstornado += 1;
				valorTotalLaudoEstornado += detalhe.getLoteLaudoImprodutivoDetalhe().getVlLaudo();
			}
		}

		loteImprodutivoAux.setListaLoteDetalheAdicional(listaLoteDetalheAdicional);

		loteImprodutivoAux.setQtdTotalLaudoIncluido(qtdTotalLaudoIncluido);
		loteImprodutivoAux.setQtdTotalLaudoEstornado(qtdTotalLaudoEstornado);
		loteImprodutivoAux.setValorTotalLaudoIncluido(valorTotalLaudoIncluido);
		loteImprodutivoAux.setValorTotalLaudoEstornado(valorTotalLaudoEstornado);
	}

	public List<LoteLaudoImprodutivoDetalheAux> obterLaudosAdicionais(RelatorioImprodutivoFiltro filtro)
			throws VistoriaBindException {
		VistoriaBindException bindException = new VistoriaBindException(filtro, "filtro");

		if (!UtilJava.trueVar(filtro.getCorretor())) {
			bindException.rejectValue(RelatorioImprodutivoFiltro.Fields.corretor, null,
					messageUtil.get("erro.relatorio.improdutiva.corretor.nao.informado"));
		}

		if (filtro.getDataReferencia() == null) {
			bindException.rejectValue(RelatorioImprodutivoFiltro.Fields.dataReferencia, null,
					messageUtil.get("erro.relatorio.improdutiva.mes.ano.refencia.nao.informado"));
		}

		if (bindException.hasErrors()) {
			throw bindException;
		}

		List<LoteLaudoImprodutivoDetalheAux> listaPesquisaAdicional = loteLaudoImprodutivoDetalheService
				.carregarListaLaudoAdicional(filtro);

		Map<String, String> mapMensagemImprodutiva = parametroVPService.carregarMensagemImprodutiva();
		List<LoteLaudoImprodutivoDetalheAux> listaPesquisaAdicionalAux = new ArrayList<LoteLaudoImprodutivoDetalheAux>();

		for (LoteLaudoImprodutivoDetalheAux pesquisaAdicional : listaPesquisaAdicional) {
			pesquisaAdicional
					.setDescMotivoImprodutiva(mapMensagemImprodutiva.get(pesquisaAdicional.getCodMotivoImprodutiva()));
			pesquisaAdicional
					.setDescStatusLaudo(UtilNegocio.retornaDescSitucVsPreLaudo(pesquisaAdicional.getCodStatusLaudo()));

			// * Inclusão
			// * Laudo Retirado(IC_EXCLU = S) e Laudo Improdutivo(CD_TIPO_LAUDO = I)
			// * OU Laudo Estornado(CD_TIPO_LAUDO = E)
			if (pesquisaAdicional.getRetirado()
					&& ConstantesVistoriaPrevia.COD_TIPO_LAUDO_IMPRODUTIVO
							.equals(pesquisaAdicional.getLoteLaudoImprodutivoDetalhe().getCdTipoLaudo())
					|| ConstantesVistoriaPrevia.COD_TIPO_LAUDO_IMPRODUTIVO_ESTORNADO
							.equals(pesquisaAdicional.getLoteLaudoImprodutivoDetalhe().getCdTipoLaudo())) {

				pesquisaAdicional.getLoteLaudoImprodutivoDetalhe()
						.setCdTipoLaudo(ConstantesVistoriaPrevia.COD_TIPO_LAUDO_IMPRODUTIVO_INCLUIDO);
				pesquisaAdicional.setDescTipoLaudo("Inclusão");

				listaPesquisaAdicionalAux.add(pesquisaAdicional);
				// * Estorno
				// * Lote Fora da Franquia E
				// * (Laudo Improdutivo(CD_TIPO_LAUDO = I)
				// * OU Laudo Incluido(CD_TIPO_LAUDO = C))
			} else if (!pesquisaAdicional.getLoteDentroFranquia()
					&& (ConstantesVistoriaPrevia.COD_TIPO_LAUDO_IMPRODUTIVO
							.equals(pesquisaAdicional.getLoteLaudoImprodutivoDetalhe().getCdTipoLaudo())
							|| ConstantesVistoriaPrevia.COD_TIPO_LAUDO_IMPRODUTIVO_INCLUIDO
									.equals(pesquisaAdicional.getLoteLaudoImprodutivoDetalhe().getCdTipoLaudo()))) {

				pesquisaAdicional.getLoteLaudoImprodutivoDetalhe()
						.setCdTipoLaudo(ConstantesVistoriaPrevia.COD_TIPO_LAUDO_IMPRODUTIVO_ESTORNADO);
				pesquisaAdicional.setDescTipoLaudo("Estorno");

				listaPesquisaAdicionalAux.add(pesquisaAdicional);
			}
		}

		return listaPesquisaAdicionalAux;
	}

	@Transactional
	public void salvarDetalheLote(Long id, RelatorioImprodutividadeCorretorDTO to) {
		LoteLaudoImprodutivoAux loteImprodutivoAux = to.getLoteImprodutivoAux();
		Long mesRefLote = loteImprodutivoAux.getLoteLaudoImprodutivo().getMmRefer();
		Long anoRefLote = loteImprodutivoAux.getLoteLaudoImprodutivo().getAaRefer();

		if (!loteLaudoImprodutivoService.permitirEdicao(YearMonth.of(anoRefLote.intValue(), mesRefLote.intValue()))) {
			throw new BusinessVPException(messageUtil.get("erro.relatorio.improdutividade.referencia.nao.atual",
					UtilNegocio.getNomeMes(mesRefLote.intValue()) + " / " + anoRefLote.toString()));
		}

		String usuario = usuarioService.getUsuarioId();

		List<LoteLaudoImprodutivoDetalheAux> listaDetalheLote = loteImprodutivoAux.getListaLoteDetalhe();

		boolean loteRetirado = true;

		// * Salvar Detalhes do Lote.
		for (LoteLaudoImprodutivoDetalheAux detalhe : listaDetalheLote) {

			if (loteRetirado && !detalhe.getRetirado()) {
				loteRetirado = false;
			}

			detalhe.getLoteLaudoImprodutivoDetalhe().setDtUltmaAtulz(new Date());
			detalhe.getLoteLaudoImprodutivoDetalhe().setCdUsuroUltmaAtulz(usuario);
			loteLaudoImprodutivoDetalheService.update(detalhe.getLoteLaudoImprodutivoDetalhe());
		}

		// * Excluir da base a lista de laudos adicionais(incluidos/estornados)
		loteLaudoImprodutivoDetalheService
				.excluirListaLaudoAdicional(loteImprodutivoAux.getLoteLaudoImprodutivo().getIdLoteLaudoImpdv());

		// * Reinsere a lista de laudos adicionais.
		List<LoteLaudoImprodutivoDetalheAux> listaDetalheLoteAdicional = loteImprodutivoAux
				.getListaLoteDetalheAdicional();
		for (LoteLaudoImprodutivoDetalheAux detalhe : listaDetalheLoteAdicional) {
			LoteLaudoImprodutivoDetalhe d = detalhe.getLoteLaudoImprodutivoDetalhe();
			d.setIdLoteLaudoImpdv(id);
			d.setIdLoteLaudoImpdvDetal(null);
			d.setIcExclu(ConstantesVistoriaPrevia.COD_IND_NAO);
			d.setDtUltmaAtulz(new Date());
			d.setCdUsuroUltmaAtulz(usuario);
			loteLaudoImprodutivoDetalheService.update(d);
		}

		if (UtilJava.trueVar(listaDetalheLoteAdicional)) {
			loteRetirado = false;
		}

		LoteLaudoImprodutivo loteImprodutivo = loteImprodutivoAux.getLoteLaudoImprodutivo();

		loteImprodutivo.setDtUltmaAlter(new Date());
		loteImprodutivo.setIcExclu(loteRetirado ? "S" : "N");
		loteLaudoImprodutivoService.save(loteImprodutivo);

		// * Atualiza Valores e Quantidade da VPE0439_LOTE_LAUDO_IMPDV
		// vpImprodutivaDAO.atualizaValoresEQuantidadesLotes(loteImprodutivo.getIdLoteLaudoImpdv(),
		// mesRefLote, anoRefLote);
		geraLoteVPImprodutivaService.atualizaValoresEQuantidadesLotes(loteImprodutivo.getIdLoteLaudoImpdv(), mesRefLote,
				anoRefLote);
	}
	
	@Transactional
	public void transmitirLotes(RelatorioImprodutividadeDTO req) {
//		salvarLotes(req);
		loteLaudoImprodutivoService.transmitirLotes(req.getDataReferencia());
	}

	@Transactional
	public void salvarLotes(RelatorioImprodutividadeDTO req) {
		if (!loteLaudoImprodutivoService.permitirEdicao(req.getDataReferencia())) {
			throw new BusinessVPException(messageUtil.get("erro.relatorio.improdutividade.referencia.nao.atual",
					UtilNegocio.getNomeMes(req.getDataReferencia().getMonthValue()) + " / " + req.getDataReferencia().getYear()));
		}
		
		req.getListaLoteImprodutivoAux().forEach(lote -> {
			Long idLoteLaudoImpdv = lote.getLoteLaudoImprodutivo().getIdLoteLaudoImpdv();
			
			LoteLaudoImprodutivo loteLaudoImprodutivo = loteLaudoImprodutivoService.findById(idLoteLaudoImpdv)
			.orElseThrow(() -> new BusinessVPException("Lote id: "+ idLoteLaudoImpdv + " inválido."));
			
			// Se marcado como RETIRADO
			if (ConstantesVistoriaPrevia.COD_IND_SIM.equals(lote.getLoteLaudoImprodutivo().getIcExclu())) {
				// Zera valor calculado.
				loteLaudoImprodutivo.setVlTotalLoteCaldo(0D);
				// Altera Situação dos Detalhes do Lote para RETIRADO
				loteLaudoImprodutivoDetalheService.alterarSitucDetalheLote(idLoteLaudoImpdv,ConstantesVistoriaPrevia.COD_IND_SIM);
				// Exclui os Laudos INCLUIDOS E ESTORNADOS.
				loteLaudoImprodutivoDetalheService.excluirListaLaudoAdicional(idLoteLaudoImpdv);
			} else {
				// Altera Situação dos Detalhes do Lote para NÃO ENVIADO
				loteLaudoImprodutivoDetalheService.alterarSitucDetalheLote(idLoteLaudoImpdv,ConstantesVistoriaPrevia.COD_IND_NAO);
				// Recupera total Calculado.
				Double valor = loteLaudoImprodutivoService.carregarVlTotalLoteCaldo(idLoteLaudoImpdv);
				loteLaudoImprodutivo.setVlTotalLoteCaldo(valor == null ? 0D : valor);
			}

			loteLaudoImprodutivo.setIcExclu(lote.getLoteLaudoImprodutivo().getIcExclu());
			loteLaudoImprodutivo.setDtUltmaAlter(new Date());

			loteLaudoImprodutivoService.save(loteLaudoImprodutivo);

			// Atualiza Valores e Quantidade da VPE0439_LOTE_LAUDO_IMPDV
			// vpImprodutivaDAO.atualizaValoresEQuantidadesLotes(lote.getLoteLaudoImprodutivo().getIdLoteLaudoImpdv(), mesRefLote, anoRefLote);
			Long mesRefLote = loteLaudoImprodutivo.getMmRefer();
			Long anoRefLote = loteLaudoImprodutivo.getAaRefer();
			geraLoteVPImprodutivaService.atualizaValoresEQuantidadesLotes(idLoteLaudoImpdv,mesRefLote,anoRefLote);
		});
	}
}