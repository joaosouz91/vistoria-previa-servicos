package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.aceitacao.crud.model.Restricao;
import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.ext.act.repository.RestricaoRepository;
import br.com.tokiomarine.seguradora.ext.ssv.repository.ItemSeguradoRepository;
import br.com.tokiomarine.seguradora.ext.ssv.service.UsuarioAlcadaService;
import br.com.tokiomarine.seguradora.ssv.transacional.model.ItemSegurado;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AvariaLaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.HistoricoLaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParecerTecnicoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PrestadoraVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ProponenteVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VeiculoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.AvariaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.HistoricoVistoriaPreviaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ParecerVistoriaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseAvariaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseDadosLaudoEmissaoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseHistoricoVistoriaPreviaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseLaudoVPDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseParecerAvariaChassiDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseParecerVistoriaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseVeiculoVPDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoAvarias;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoPecas;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.LaudoRegrasDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.LaudoVistoriaPreviaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RegraAcessoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RegraAcessoDTO.RegraAcessoDTOBuilder;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RetornoServico;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.FiltroConsultaVistoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.LaudoVistoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ResponseLaudoEditar;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.StatusLaudoEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoDeVinculo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.fila.VinculoLaudoProducer;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.AcessorioLaudoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ArquivoAvariasRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ArquivoPecasRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.AvariaLaudoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.HistoricoLaudoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ItemSeguroRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LaudoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LaudoVistoriaPreviaRepositoryHql;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LogVinculoRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ParecerTecnicoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ProponenteVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.RegrasVPDAO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.VeiculoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesVistoriaPrevia;

@Component
public class LaudoService {
	
	@Autowired
	private LaudoVistoriaPreviaRepositoryHql laudoVistoriaPreviaRepositoryHql;
			
	@Autowired
	private LaudoVistoriaPreviaRepository laudoVistoriaPreviaRepository;
	
	@Autowired
	private ProponenteVistoriaPreviaRepository proponenteVistoriaPreviarepository;
	
	@Autowired
	private VeiculoVistoriaPreviaRepository veiculoVistoriaPrevia;
	
	@Autowired
	private RestricaoRepository restricaoRespository;
	
	@Autowired
	private HistoricoLaudoVistoriaPreviaRepository historicoLaudoVPERepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private VeiculoVistoriaPreviaService veiculoService;
	
	@Autowired
	private ProponenteVistoriaPreviaService proponenteService;
	
	@Autowired
	AcessorioLaudoVistoriaPreviaRepository dadosComplementares;
	
	@Autowired
	private ItemSeguradoRepository itemSeguradoRepository;
	
	@Autowired
	private ItemSeguroRepository itensSeg;
	
	@Autowired
	private ParecerTecnicoVistoriaPreviaRepository parecertecnico;
	
	@Autowired
	private ParecerTecnicoLaudoVPEService parecerLaudos;
	
	@Autowired
	private UsuarioService usuarioLogado;
	
	@Autowired
	private UsuarioAlcadaService usuarioAlcadaService;
	
	@Autowired
	private ParametroVPService parametroVPService;
	
	@Autowired
	private VinculoLaudoProducer vinculoLaudoProducer;
	
	@Autowired
	private LogVinculoRepository log;
	
	@Autowired
	private ArquivoAvariasRepository arquivoAvariasRepository;
	
	@Autowired
	private ArquivoPecasRepository arquivoPecasRepository;
	
	@Autowired
	private AvariaLaudoVistoriaPreviaRepository avariaLaudoVistoriaPreviaRepository;
	
	@Autowired
	private PrestadoraVistoriaPreviaService prestadoraVistoriaPreviaService;
	
	@Autowired
	private RegrasVPDAO regrasVPDAO;
	
	private static final Logger LOGGER = LogManager.getLogger(LaudoService.class);

	
	public List<LaudoRegrasDTO> consultaLaudos(final FiltroConsultaVistoria filtroConsultaVistoria) {
		List<LaudoRegrasDTO> laudoVistorias = new ArrayList<>();

		try {

			List<Object[]> list = laudoVistoriaPreviaRepositoryHql.findByLaudosByVeiculo(filtroConsultaVistoria);

			if (list.isEmpty()) {
				return laudoVistorias;
			}

			Long codigoAlcada = usuarioAlcadaService.buscarCodigoAlcada(usuarioLogado.getUsuarioId()).orElse(0L);

			list.forEach(itemList -> {
				VeiculoVistoriaPrevia veiculoVP = (VeiculoVistoriaPrevia) itemList[0];
				LaudoVistoriaPrevia laudoVistoriaPrevia = (LaudoVistoriaPrevia) itemList[1];
				LaudoVistoria laudo = new LaudoVistoria();
				LaudoRegrasDTO laudoVistoria = new LaudoRegrasDTO();

				laudo.setNumeroVistoria(laudoVistoriaPrevia.getCdLvpre());
				laudo.setPlaca(veiculoVP.getCdPlacaVeicu());
				laudo.setChassi(veiculoVP.getCdChassiVeicu());
				laudo.setData(laudoVistoriaPrevia.getDtVspre());
				laudo.setStatus(StatusLaudoEnum.obterDescricao(laudoVistoriaPrevia.getCdSitucVspre()));

				laudoVistoria.setLaudos(laudo);

				if (laudoVistoriaPrevia.getCdSitucBlqueVspre() == 1L
						&& laudoVistoriaPrevia.getIcLaudoVicdo().equals("N")) {
					laudoVistoria.getLaudos().setSituacao("Bloqueado");
				} else {
					laudoVistoria.getLaudos()
							.setSituacao(TipoDeVinculo.valueOf(laudoVistoriaPrevia.getIcLaudoVicdo()).getValue());
				}

				RegraAcessoDTO regras = verificaRegra(laudoVistoriaPrevia, codigoAlcada);
				laudoVistoria.setRegras(regras);

				laudoVistorias.add(laudoVistoria);
			});

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return laudoVistorias;
	}

	public LaudoVistoriaPrevia obterLaudoPorCodigoVistoria(String codigoVistoria) {
		return laudoVistoriaPreviaRepository.findLaudoBycdLvpre(codigoVistoria);
	}
	
	public Optional<LaudoVistoriaPrevia> obterUltimoLaudoPorVoucher(String voucher) {
		return laudoVistoriaPreviaRepository.findFirstByCdVouchOrderByDtInclsRgistDesc(voucher);
	}

	public Optional<LaudoVistoriaPreviaDTO> obterUltimoLaudoDtoPorVoucher(String voucher) {
		return obterUltimoLaudoPorVoucher(voucher).map(l -> mapper.map(l, LaudoVistoriaPreviaDTO.class));
	}
	
	public ResponseLaudoEditar obterLaudoCompletoPorCodigo(String codigoLaudo) {
		
		ResponseLaudoEditar laudoCompleto = new ResponseLaudoEditar();
		
		
		LaudoVistoriaPrevia laudo = this.obterLaudoPorCodigoVistoria(codigoLaudo) ;
		VeiculoVistoriaPrevia veiculo = veiculoService.findByVeiculoVistoriaPrevia(codigoLaudo) ;
		ProponenteVistoriaPrevia proponentes = proponenteService.findByProponenteVistoria(codigoLaudo);
		List<Object> acessorios = dadosComplementares.obterAcessorios(codigoLaudo);
		List<Object> equipSegur = dadosComplementares.obterEquipSegur(codigoLaudo);
		List<Object> avarias = dadosComplementares.obterAvarias(codigoLaudo);
		List<Object> infTech = dadosComplementares.obterInfTec(codigoLaudo);
		List<Object> equipamentos = dadosComplementares.obterEquipamentos(codigoLaudo);
		List<Object> propostasVinculadas = dadosComplementares.obterPropostasVinculadas(codigoLaudo);
		
		ItemSegurado item = itensSeg.findTopItemUnicoBycodLaudoVistoriaPreviaOrderByDataBaseCalculoItemDesc(codigoLaudo);

		List<ParecerTecnicoVistoriaPrevia> pareceres = parecertecnico.buscarInformacaoTecnica(codigoLaudo, 0l);
		
		ajustarDatas(veiculo, proponentes);
		
		if (item != null) {
			laudo.setCdEndos(item.getCodEndosso());
			laudoCompleto.setItens(Arrays.asList(item));
		}

		laudoCompleto.setLaudo(laudo);
		laudoCompleto.setVeiculo(veiculo);
		laudoCompleto.setProponente(proponentes);
		laudoCompleto.setAvarias(avarias);
		laudoCompleto.setAcessorios(acessorios);
		laudoCompleto.setEquipSegur(equipSegur);
		laudoCompleto.setInfTech(infTech);
		laudoCompleto.setEquipamentos(equipamentos);
		laudoCompleto.setPareceres(pareceres);
		laudoCompleto.setPropostasVinculadas(propostasVinculadas);

		this.validaRegrasLaudo(laudoCompleto);
	
		return laudoCompleto;
	}
	
	private void ajustarDatas(VeiculoVistoriaPrevia veiculo, ProponenteVistoriaPrevia proponentes) {
		if (veiculo != null && veiculo.getDtExpdcDut() != null) {
			veiculo.setDtExpdcDut(DateUtil.truncaData(veiculo.getDtExpdcDut()));
		}

		if (proponentes != null) {
			if (proponentes.getDtNascmCodut() != null) {
				proponentes.setDtNascmCodut(DateUtil.truncaData(proponentes.getDtNascmCodut()));
			}

			if (proponentes.getDtNascmPrpnt() != null) {
				proponentes.setDtNascmPrpnt(DateUtil.truncaData(proponentes.getDtNascmPrpnt()));
			}

			if (proponentes.getDtPrmraHbltcCodut() != null) {
				proponentes.setDtPrmraHbltcCodut(DateUtil.truncaData(proponentes.getDtPrmraHbltcCodut()));
			}

			if (proponentes.getDtPrmraHbltcPrpnt() != null) {
				proponentes.setDtPrmraHbltcPrpnt(DateUtil.truncaData(proponentes.getDtPrmraHbltcPrpnt()));
			}
		}
	}

	public void salvarLaudoCompleto(ResponseLaudoEditar laudo) {
		laudoVistoriaPreviaRepository.save(laudo.getLaudo());
		proponenteVistoriaPreviarepository.save(laudo.getProponente());
		veiculoVistoriaPrevia.save(laudo.getVeiculo());
		parecerLaudos.salvarSelecionados(laudo.getPareceres(), laudo.getLaudo());
		
		if (TipoDeVinculo.N.name().equals(laudo.getLaudo().getIcLaudoVicdo())) {
			vinculoLaudoProducer.sendVinculado(laudo.getLaudo().getCdLvpre());
		}
	}
	
	public ResponseLaudoEditar validaRegrasLaudo(ResponseLaudoEditar laudoCompleto) {

		//Verifica se tem proposta vinculada
		if(!laudoCompleto.getPropostasVinculadas().isEmpty()){
			Object[] row = (Object[]) laudoCompleto.getPropostasVinculadas().get(0);
			laudoCompleto.getLaudo().setCdClien(Long.parseLong(row[5].toString()));
			laudoCompleto.getLaudo().setCdNgoco(Long.parseLong(row[0].toString()));
			laudoCompleto.getLaudo().setNrItseg(Long.parseLong(row[1].toString()));
		}

		
		return laudoCompleto;
	}
	
	private RegraAcessoDTO verificaRegra(LaudoVistoriaPrevia laudo, Long codigoAlcada) {
		RegraAcessoDTOBuilder regra = RegraAcessoDTO.builder().visualizaLaudo(true).logLaudo(true);
		
		if (codigoAlcada == 10 || codigoAlcada == 5) {
			
			boolean isVinculado = "S".equals(laudo.getIcLaudoVicdo());
			boolean isBloqueado = laudo.getCdSitucBlqueVspre().equals(1L);
			boolean isNaoVinculadoBloqueado = !isVinculado && isBloqueado;
			
			if(codigoAlcada == 10) {
				boolean isVinculadoNaoBloqueado = isVinculado && !isBloqueado;
				boolean isProposta_LIB_REC_APO = false;
				
				if (isVinculado) {
					ItemSegurado itemSeg = itensSeg.findTopItemUnicoBycodLaudoVistoriaPreviaOrderByDataBaseCalculoItemDesc(laudo.getCdLvpre());
					isProposta_LIB_REC_APO = itemSeg != null && (StringUtils.equalsAny(itemSeg.getCodSituacaoNegocio(), "LIB", "REC", "APO"));
				}
				
				return regra
						.editarLaudo(!isProposta_LIB_REC_APO)
						.alteraDataLaudo(!isProposta_LIB_REC_APO)
			            .desbloqueiaLaudo(isNaoVinculadoBloqueado)
			            .desvincularLaudo(isVinculadoNaoBloqueado && !isProposta_LIB_REC_APO)
			            .build();
	
			} else if (codigoAlcada == 5) {
				
				boolean isNaoVinculadoNaoBloqueado = !isVinculado && !isBloqueado;
				
				return regra
						.editarLaudo(isNaoVinculadoNaoBloqueado)
						.alteraDataLaudo(isNaoVinculadoNaoBloqueado)
			            .desbloqueiaLaudo(isNaoVinculadoBloqueado)
			            .build();
			}
		}

		return regra.build();
	}
	
	public ResponseLaudoEditar permitirBloqueio(String codigoLaudo, String motivo) {
		
		Date dtProcessamento = new Date(System.currentTimeMillis());
		ResponseLaudoEditar laudoCompleto = obterLaudoCompletoPorCodigo(codigoLaudo);
		laudoCompleto.getLaudo().setDsMotvRclsfVspre(motivo);
		String idUsuario = usuarioLogado.getUsuarioId();
		
		if(laudoCompleto.getLaudo().getCdSitucBlqueVspre().equals(ConstantesVistoriaPrevia.LAUDO_STATUS_DESBLOQUEADO)) {
				laudoCompleto.setMenssagemBloqueado(messageUtil.get(ConstantesVistoriaPrevia.VISTORIA_DESBLOQUEADA));
				laudoCompleto.setIsBloqueado(true);
			return laudoCompleto;
			
		}else{
			    //obtém o parâmetro de quantidade de dias máximo para desbloqueio do laudo
				// obtém a quantidade de dias máxima de bloqueio do laudo
				Long qtdDiasMaximaBloqueio =	parametroVPService.obterQuantidadeDiasMaxDesbloqueioLaudo();
				if(qtdDiasMaximaBloqueio == null) {
						laudoCompleto.setMenssagemBloqueado(messageUtil.get(ConstantesVistoriaPrevia.PRAZO_PARAMETRO_NAO_CADASTRADO));
						laudoCompleto.setIsBloqueado(true);
					return laudoCompleto;
				}
			
				HistoricoLaudoVistoriaPrevia historicoLaudo = this.populaHistoricoLaudo(laudoCompleto.getLaudo().getDsMotvRclsfVspre(),laudoCompleto.getLaudo(),dtProcessamento,idUsuario);
				laudoCompleto.getLaudo().setCdSitucBlqueVspre(ConstantesVistoriaPrevia.LAUDO_STATUS_DESBLOQUEADO);
				laudoCompleto.getLaudo().setDtLimitBlqueVspre(this.calculaDataBloqueio(laudoCompleto.getLaudo(),qtdDiasMaximaBloqueio));
				this.saveLaudoAndHistorico(laudoCompleto.getLaudo(),historicoLaudo, null);
				laudoCompleto.setMenssagemBloqueado(messageUtil.get(ConstantesVistoriaPrevia.DESBLOQUEIO_EFETUADO));

//				vinculoLaudoProducer.sendVinculado(codigoLaudo);
		}
		
		return laudoCompleto;
		
	}



	private void saveLaudoAndHistorico(LaudoVistoriaPrevia laudo,HistoricoLaudoVistoriaPrevia historicoLaudo,ItemSegurado itemSegurado) {
		try {
			
				if (itemSegurado != null){
					itemSeguradoRepository.save(itemSegurado);
				}
				
				laudoVistoriaPreviaRepository.save(laudo);
				historicoLaudoVPERepository.save(historicoLaudo);
				
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	//pode sair
	private Date calculaDataBloqueio(LaudoVistoriaPrevia laudo,Long qtdDiasMaximaDesbloqueio) {
		
		return DateUtil.calculaNovaData(laudo.getDtVspre(), 0, 0, qtdDiasMaximaDesbloqueio.intValue());
		
	}

	//Pode Sair
	private HistoricoLaudoVistoriaPrevia populaHistoricoLaudo(String dsMotvRclsfVspre, LaudoVistoriaPrevia laudo, Date dtProcessamento, String idUsuario) {
		
		HistoricoLaudoVistoriaPrevia historicoLaudo = new HistoricoLaudoVistoriaPrevia();
        historicoLaudo.setNrVrsaoLvpre(laudo.getNrVrsaoLvpre());
        historicoLaudo.setCdLvpre(laudo.getCdLvpre());
        historicoLaudo.setCdEndos(laudo.getCdEndos());
        historicoLaudo.setCdSitucAnterVspre(laudo.getCdSitucAnterVspre());
        historicoLaudo.setCdLocalCaptc(laudo.getCdLocalCaptc());
        historicoLaudo.setCdSitucHistoVspre(2L);
        historicoLaudo.setDtUltmaAlter(dtProcessamento);
        historicoLaudo.setCdUsuroIncls(idUsuario);
        historicoLaudo.setDsMotvSbrpoDado(dsMotvRclsfVspre != null && dsMotvRclsfVspre.length() > 30 ? dsMotvRclsfVspre.substring(0,30) : dsMotvRclsfVspre);
        
		return historicoLaudo;
	}

	public LaudoVistoriaPrevia desvincularLaudo( String codigoLaudo) {
		
		LaudoVistoriaPrevia laudo = this.obterLaudoPorCodigoVistoria(codigoLaudo);
		
		if(laudo != null) {
			laudo.setIcLaudoVicdo("N");
			laudo.setCdSitucBlqueVspre(0L);
			laudoVistoriaPreviaRepository.save(laudo);
		}
		
		return laudo;
	}
	
	//Pode Sair
    public boolean permiteDesbloqueioLaudo( LaudoVistoriaPrevia laudo,  Long qtdDiasMaximaBloqueio) {

    	final Date dataLimiteBloqueioLaudo = DateUtil.calculaNovaData(laudo.getDtVspre(), 0, 0, qtdDiasMaximaBloqueio.intValue());
    	
        return new Date().after(dataLimiteBloqueioLaudo);
             	
    }
	
    public LaudoVistoriaPrevia bloqueioPorSupervisao(String codigoLaudo,Long numeroVersaoLaudo) {
    	
    	Restricao restricaoItemsegurado = null;
    	ItemSegurado itemSegurado = null;
    	Long codigoproposta = null;
    	
    	//MODULO_VEICULO_PASSEIO
    	Long codigoModuloProduto = ConstantesVistoriaPrevia.MODULO_VEICULO_PASSEIO;
    	
    	List<ItemSegurado> listaItensSegurados = itemSeguradoRepository.findItemSeguradoBycodLaudoVistoriaPrevia(codigoLaudo);
    	
    	//caso encontre um item com laudo vinculado.
    	if(listaItensSegurados != null && !listaItensSegurados.isEmpty()) {
    		
    		itemSegurado = listaItensSegurados.get(0);
    		codigoModuloProduto = itemSegurado.getCodModuloProduto();
    	}
		
    	//recupera alcada do usuario
    	//Verfica se usuario não possui alcada10
    	String idUsuario = usuarioLogado.getUsuarioId();
    	
		usuarioAlcadaService.getUsuarioAlcadaByCdMduprCdUsuarioDataVigencia(codigoModuloProduto, idUsuario)
    	.filter(u -> u.getCdAlcad().equals(10L))
    	.orElseThrow(() -> new BusinessVPException("Usuario não tem alçada 10"));
    	
    	// recupera laudo
    	LaudoVistoriaPrevia laudo = laudoVistoriaPreviaRepository.findLaudoBycdLvpre(codigoLaudo);
    	
    	//retira vinculo do laudo e bloqueio pior motivos de supervisao
    	
    	laudo.setIcLaudoVicdo("N");
    	laudo.setCdSitucBlqueVspre(ConstantesVistoriaPrevia.LAUDO_STATUS_BLOQUEADO);
    	laudo.setTpBlque(ConstantesVistoriaPrevia.LAUDO_TIPO_BLOQUEIO_SUPERVISAO);
    	laudo.setDsMotvRclsfVspre("LAUDO DE SUPERVISAO");
    	laudo.setDtUltmaAlter(new Date());
    	laudo.setCdUsuroUltmaAlter(idUsuario);
    	
    	laudoVistoriaPreviaRepository.save(laudo);
    	
    	//se o laudo não for do plataforma, entao procura  o item segurado para fazer o desvinculo
    	
		if (StringUtils.isEmpty(laudo.getCdVouch()) || !ConstantesVistoriaPrevia.LETRA_SYS_ORIGEM_PLATAFORMA.equals(laudo.getCdVouch().substring(0, 2))) {

			if (itemSegurado != null) {

				codigoproposta = itemSegurado.getCodEndosso() != null ? itemSegurado.getCodEndosso()
						: itemSegurado.getCodNegocio();

				// recupera restricao de vistoria previa
				restricaoItemsegurado = restricaoRespository.buscarRestricao(codigoproposta,
						itemSegurado.getNumItemSegurado(), "VIS");

				// caso ecista restricao de vistoria previa com status "PEN" permite o
				// cancelamento de restrito de desvinculo do laudo
				if (restricaoItemsegurado != null && restricaoItemsegurado.getCdSitucRestr().equals("PEN")) {

					// volta a restricao de VP para GRD para aguardar um novo laudi de supervisao
					restricaoItemsegurado.setCdSitucRestr("GRD");

					// desvincula o laudo do item segurado
					itemSegurado.setCodLaudoVistoriaPrevia(null);

					restricaoRespository.save(restricaoItemsegurado);

					itemSeguradoRepository.save(itemSegurado);

					// efetua log no processo de consulta de proposta de aceitacao inclui log de
					// aceitacao

				} else {
					   throw new BusinessVPException("Não encontrou Restrição item sgurado!");

				}

			} else {
				throw new BusinessVPException("Não encontrou um Item Segurado!");
			}

    	}
		
    	return laudo;
	}
    
    public List<String> obterLogVinculoLaudo(String numeroLaudo, Date dataLaudo){
    	
    	return log.obterLogVinculoLaudo(numeroLaudo, dataLaudo);
    	
    }

	/**
	 * Verifica se permite um novo agendamento para laudos com parecer sujeito a
	 * analise, com parecer de avaria de vidro.
	 *
	 * @param laudo - LaudoVistoriaPrevia
	 * @return boolean
	 */
	public boolean isLaudoComParecerLiberado(LaudoVistoriaPrevia laudo) {

		Set<Long> codigosParecer = parametroVPService.obterCodParecerSujeitoAnalisePermiteAgendamento();

		if (codigosParecer != null) {

			return parecerLaudos.verificarLaudoComParecerTecnico(laudo.getCdLvpre(), codigosParecer);
		}

		return false;
	}
	
	public List<ArquivoAvarias> getDadosArquivoAvarias(){
        return arquivoAvariasRepository.findAllByCdSitucAvariOrderByCodigoAvaria("A");
    }  
	
	public List<ArquivoPecas> getDadosArquivoPecas(){
        return arquivoPecasRepository.findAllByCdSitucPecaOrderByCodigoPeca("A");
    }  
	
	public AvariaLaudoVistoriaPrevia saveAvariaLaudoVistoriaPrevia(AvariaLaudoVistoriaPrevia avariaLaudoVistoriaPrevia){
        return avariaLaudoVistoriaPreviaRepository.save(avariaLaudoVistoriaPrevia);
    }  
	
	public List<Object> getAvarias(String cdLvpre){
        return dadosComplementares.obterAvarias(cdLvpre);
    }  
	
	public AvariaLaudoVistoriaPrevia getAvaria(String cdLvpre, Long codPeca,String tpAvaria){
        return avariaLaudoVistoriaPreviaRepository.findByCdLvpreAndCdPecaAvadaAndTpAvariVeicu(cdLvpre, codPeca, tpAvaria);
    } 
	
	public List<Object> deleteAvaria(String cdLvpre, Long codPeca,String tpAvaria){
		AvariaLaudoVistoriaPrevia avaria = avariaLaudoVistoriaPreviaRepository.findByCdLvpreAndCdPecaAvadaAndTpAvariVeicu(cdLvpre, codPeca, tpAvaria);
		avariaLaudoVistoriaPreviaRepository.delete(avaria);
		
		return getAvarias(cdLvpre);
    }	
	
	public ResponseLaudoVPDTO obterLaudoVistoriaPrevia(String cdLvpre) {
		ResponseLaudoVPDTO dto = new ResponseLaudoVPDTO();

		LaudoVistoriaPrevia laudo = laudoVistoriaPreviaRepository.findLaudoByCdLvpreNrVrsaoLvpreCdSituc(cdLvpre, 0L);

		if (laudo != null) {

			PrestadoraVistoriaPrevia prestadora = prestadoraVistoriaPreviaService.obterPrestadoraPorId(laudo.getCdAgrmtVspre());

			dto.setCdClien(laudo.getCdClien());
			dto.setCdCrtorSegur(laudo.getCdCrtorSegur());
			dto.setCdEndos(laudo.getCdEndos());
			dto.setCdLocalCaptc(laudo.getCdLocalCaptc());
			dto.setCdLvpre(laudo.getCdLvpre());
			dto.setCdSitucVspre(laudo.getCdSitucVspre());
			dto.setCdSucslComrl(laudo.getCdSucslComrl());
			dto.setCdUsuroUltmaAlter(laudo.getCdUsuroUltmaAlter());
			dto.setCdVouch(laudo.getCdVouch());
			dto.setDsMotvRclsfVspre(laudo.getDsMotvRclsfVspre());
			dto.setDsObserVspre(laudo.getDsObserVspre());
			dto.setDtInclsRgist(laudo.getDtInclsRgist());
			dto.setDtRclsfVspre(laudo.getDtRclsfVspre());
			dto.setDtTrnsmVspre(laudo.getDtTrnsmVspre());
			dto.setDtVspre(DateUtil.formata(laudo.getDtVspre(), "ddMMyyyy"));
			dto.setIcLaudoVicdo(laudo.getIcLaudoVicdo());
			dto.setNmCidadDestnVspre(laudo.getNmCidadDestnVspre());
			dto.setNmCidadOrigmVspre(laudo.getNmCidadOrigmVspre());
			dto.setNmSolttVspre(laudo.getNmSolttVspre());
			dto.setNrCepLocalVspre(laudo.getNrCepLocalVspre());
			dto.setNrVrsaoLvpre(laudo.getNrVrsaoLvpre());
			dto.setSgUniddFedrcVspre(laudo.getSgUniddFedrcVspre());
			dto.setTpLocalVspre(laudo.getTpLocalVspre());
			dto.setCdSitucAnterVspre(laudo.getCdSitucAnterVspre());
			dto.setCdPostoVspre(laudo.getCdPostoVspre());
			dto.setNomeEmpresaVistoriadora(prestadora.getNmRazaoSocal());
			dto.setNumeroCPFCNPJVistoriadora(
					prestadora.getNrCpfCnpj() != null ? prestadora.getNrCpfCnpj().toString() : null);
			dto.setRetorno(new RetornoServico());
			dto.getRetorno().setCodigoRetorno(0L);
			dto.getRetorno().setMensagem("sucesso");
		} else {
			dto.setRetorno(new RetornoServico());
			dto.getRetorno().setCodigoRetorno(99L);
			dto.getRetorno().setMensagem("Sem resultados encontrados");
		}
		return dto;
	}

	public ResponseVeiculoVPDTO obterDadosVeiculo(String cdLvpre) {
		ResponseVeiculoVPDTO dto = new ResponseVeiculoVPDTO();

		Optional<VeiculoVistoriaPrevia> veiculoOp = veiculoService.findByCdLvpre(cdLvpre);

		if (veiculoOp.isPresent()) {
			VeiculoVistoriaPrevia veiculo = veiculoOp.get();
			dto.setCdLvpre(veiculo.getCdLvpre());
			dto.setCdChassiVeicu(veiculo.getCdChassiVeicu());
			dto.setCdRenavam(veiculo.getCdRenavam());
			dto.setDtExpdcDut(veiculo.getDtExpdcDut());
			dto.setNmCidadExpdcDut(veiculo.getNmCidadExpdcDut());
			dto.setSgUniddFedrcDut(veiculo.getSgUniddFedrcDut());
			dto.setCdPlacaVeicu(veiculo.getCdPlacaVeicu());
			dto.setCdCarroVeicu(veiculo.getCdCarroVeicu());
			dto.setCdCamboVeicu(veiculo.getCdCamboVeicu());
			dto.setIcVeicuCarga(veiculo.getIcVeicuCarga());
			dto.setDsModelVeicu(veiculo.getDsModelVeicu());
			dto.setQtLotacVeicu(veiculo.getQtLotacVeicu());
			dto.setCdDutVeicu(veiculo.getCdDutVeicu());
			dto.setNmDutVeicu(veiculo.getNmDutVeicu());
			dto.setNmAlienVeicu(veiculo.getNmAlienVeicu());
			dto.setCdFormaCarroVeicu(veiculo.getCdFormaCarroVeicu());
			dto.setTpCmbst(veiculo.getTpCmbst());
			dto.setTpCamboVeicu(veiculo.getTpCamboVeicu());
			dto.setQtKmRdadoVeicu(veiculo.getQtKmRdadoVeicu());
			dto.setNrCnpjDut(veiculo.getNrCnpjDut());
			dto.setNrCpfDut(veiculo.getNrCpfDut());
			dto.setRetorno(new RetornoServico());
			dto.getRetorno().setCodigoRetorno(0L);
			dto.getRetorno().setMensagem("sucesso");
		} else {
			dto.setRetorno(new RetornoServico());
			dto.getRetorno().setCodigoRetorno(99L);
			dto.getRetorno().setMensagem("Sem resultados encontrados");
		}
		return dto;

	}

	public ResponseDadosLaudoEmissaoDTO obterDadosLaudoEmissao(String cdLvpre, Long nrItSeg, String tipoHistorico){

		ResponseDadosLaudoEmissaoDTO dadosLaudoDTO = new ResponseDadosLaudoEmissaoDTO();

		// caso o item segurado não possui laudo vinculado, verifica somente o processo de dispensa.
		if(!"0".equals(cdLvpre)){

			LaudoVistoriaPrevia laudo = laudoVistoriaPreviaRepository.findLaudoByCdLvpreNrVrsaoLvpreCdSituc(cdLvpre, 0L);

			if(laudo !=null){
				dadosLaudoDTO.setDtVspre(DateUtil.formata(laudo.getDtVspre(),"ddMMyyyy"));
			} else {
				// caso o laudo enviado não seja encontrado retorna erro.
				dadosLaudoDTO.setRetorno(new RetornoServico());
				dadosLaudoDTO.getRetorno().setCodigoRetorno(99L);
				dadosLaudoDTO.getRetorno().setMensagem("Laudo informado não encontrado!");

				return dadosLaudoDTO;
			}

		}

		// pesquisa o item segurado por numero e tipo historico
		ItemSegurado itemSegurado =  itemSeguradoRepository.findByItemSegurado(nrItSeg,tipoHistorico);

		// se o item segurado não for nulo

		if(itemSegurado !=null){
			// verifica se existe dispensa para a restrição de VP.
			dadosLaudoDTO.setDispensa(regrasVPDAO.verificaVpDispensada(itemSegurado));

			// caso o laudo seja nulo (0) e não exista dispesna retornar erro.
			if("0".equals(cdLvpre) && dadosLaudoDTO.isDispensa() == false){
				dadosLaudoDTO.setRetorno(new RetornoServico());
				dadosLaudoDTO.getRetorno().setCodigoRetorno(99L);
				dadosLaudoDTO.getRetorno().setMensagem("Item sem laudo vinculado e sem dispensa!");

				return dadosLaudoDTO;
			}

		} else {
			// caso não encontre o item segurado retorna erro.
			dadosLaudoDTO.setRetorno(new RetornoServico());
			dadosLaudoDTO.getRetorno().setCodigoRetorno(99L);
			dadosLaudoDTO.getRetorno().setMensagem("Item Segurado não encontrado ou laudo não pertence ao item!");

			return dadosLaudoDTO;
		}

		dadosLaudoDTO.setRetorno(new RetornoServico());
		dadosLaudoDTO.getRetorno().setCodigoRetorno(0L);
		dadosLaudoDTO.getRetorno().setMensagem("sucesso");

		return dadosLaudoDTO;
	}

	public ResponseParecerVistoriaDTO obterListaParecerVistoria(String cdLvpre) {
		ResponseParecerVistoriaDTO retornoParecerVistoriaDTO = null;
		List<ParecerVistoriaDTO> listaParecerDTO = new ArrayList<ParecerVistoriaDTO>();
		ParecerVistoriaDTO parecerTecnicoDTO = new ParecerVistoriaDTO();

		if (StringUtils.isEmpty(cdLvpre)) {
			retornoParecerVistoriaDTO = new ResponseParecerVistoriaDTO();
			retornoParecerVistoriaDTO.setRetorno(new RetornoServico());
			retornoParecerVistoriaDTO.getRetorno().setCodigoRetorno(99L);
			retornoParecerVistoriaDTO.getRetorno().setMensagem("Laudo deve ser informado");
			return retornoParecerVistoriaDTO;
		}

		List<ParecerTecnicoVistoriaPrevia> listaParecer = parecertecnico.buscarInformacaoTecnica(cdLvpre, 0L);

		if (listaParecerDTO != null) {

			retornoParecerVistoriaDTO = new ResponseParecerVistoriaDTO();

			for (ParecerTecnicoVistoriaPrevia parecerTecnico : listaParecer) {
				parecerTecnicoDTO = new ParecerVistoriaDTO();
				parecerTecnicoDTO.setCdPatecVspre(parecerTecnico.getCdPatecVspre());
				parecerTecnicoDTO.setDsPatecVspre(parecerTecnico.getDsPatecVspre());
				listaParecerDTO.add(parecerTecnicoDTO);
			}

			retornoParecerVistoriaDTO.setParecerVistoriaDTO(listaParecerDTO);
			retornoParecerVistoriaDTO.setRetorno(new RetornoServico());
			retornoParecerVistoriaDTO.getRetorno().setCodigoRetorno(0L);
			retornoParecerVistoriaDTO.getRetorno().setMensagem("sucesso");

		}

		return retornoParecerVistoriaDTO;
	}

	public ResponseHistoricoVistoriaPreviaDTO obterListaHistoricoLaudo(String cdLvpre, List<Long> codigoSituacao) {
		ResponseHistoricoVistoriaPreviaDTO retorno = new ResponseHistoricoVistoriaPreviaDTO();
		if (StringUtils.isEmpty(cdLvpre)) {
			retorno.setRetorno(new RetornoServico());
			retorno.getRetorno().setCodigoRetorno(99L);
			retorno.getRetorno().setMensagem("Laudo deve ser informado");
		}

		List<HistoricoLaudoVistoriaPrevia> listaHistorico = historicoLaudoVPERepository.findHistoricoLaudoVistoriaPreviaPorLaudoEVersao(cdLvpre,0L);

		if (listaHistorico != null) {
			retorno.setHistoricoAvarias(new ArrayList<HistoricoVistoriaPreviaDTO>());

			for (HistoricoLaudoVistoriaPrevia historico : listaHistorico) {
				// verifica se o laudo contem os codigos desejados e adiciona na lista de retorno.
				if(codigoSituacao.contains(historico.getCdSitucHistoVspre())){
					HistoricoVistoriaPreviaDTO historicoDTO = new HistoricoVistoriaPreviaDTO();
					historicoDTO.setCdEndos(historico.getCdEndos());
					historicoDTO.setCdLocalCaptc(historico.getCdLocalCaptc());
					historicoDTO.setCdLvpre(historico.getCdLvpre());
					historicoDTO.setCdSitucHistoVspre(historico.getCdSitucHistoVspre());
					historicoDTO.setCdSucslComrl(historico.getCdSucslComrl());
					historicoDTO.setCdUsuroIncls(historico.getCdUsuroIncls());
					historicoDTO.setDtUltmaAlter(historico.getDtUltmaAlter());
					retorno.getHistoricoAvarias().add(historicoDTO);
				}
			}

			retorno.setRetorno(new RetornoServico());
			retorno.getRetorno().setCodigoRetorno(0L);
			retorno.getRetorno().setMensagem("sucesso");
		} else {
			retorno.setRetorno(new RetornoServico());
			retorno.getRetorno().setCodigoRetorno(99L);
			retorno.getRetorno().setMensagem("Sem resultados encontrados");
		}

		return retorno;
	}

	public ResponseAvariaDTO obterListaAvariaLaudo(String cdLvpre) {
		ResponseAvariaDTO retorno = new ResponseAvariaDTO();

		List<AvariaDTO> listaAvaria = avariaLaudoVistoriaPreviaRepository.findByCdLvpreAndNrVrsaoLvpre(cdLvpre,0L);

		if (listaAvaria != null) {
			retorno.setAvarias(listaAvaria);
			retorno.setRetorno(new RetornoServico());
			retorno.getRetorno().setCodigoRetorno(0L);
			retorno.getRetorno().setMensagem("sucesso");
		} else {
			retorno.setRetorno(new RetornoServico());
			retorno.getRetorno().setCodigoRetorno(99L);
			retorno.getRetorno().setMensagem("Sem resultados encontrados");
		}
		return retorno;
	}

	public ResponseParecerAvariaChassiDTO verificaParecerAvariaChassi(String codigoLaudo) {
		final Long cdPatecVspre = parecertecnico.verificaParecerAvariaChassi(codigoLaudo);
		return new ResponseParecerAvariaChassiDTO(codigoLaudo, cdPatecVspre);
	}
	
	public Long recuperarQtdTotalVistoria(Long codCorretor, Date dataReferenciaDe, Date dataReferenciaAte) {
		return laudoVistoriaPreviaRepository.recuperarQtdTotalVistoria(codCorretor, dataReferenciaDe, dataReferenciaAte);
	}
}