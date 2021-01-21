package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.aceitacao.crud.model.Restricao;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.AjusteRestClient;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.util.RestClientException;
import br.com.tokiomarine.seguradora.ext.act.service.PropostaService;
import br.com.tokiomarine.seguradora.ext.act.service.RestricaoService;
import br.com.tokiomarine.seguradora.ext.ssv.service.ItemSeguradoService;
import br.com.tokiomarine.seguradora.ext.ssv.service.ParametroControleDataService;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ParametroControleData;
import br.com.tokiomarine.seguradora.ssv.transacional.model.ItemSegurado;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPreviaGeral;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParecerTecnicoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VeiculoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ComboNovoStatus;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ListaReclassificacaoAlterarStatus;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.NegocioComponentDto;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Reclassificacao;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ReclassificacaoAlterarStatus;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.StatusLaudoEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoRestricao;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LaudoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ParametroVistoriaPreviaGeralRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ParecerTecnicoVistoriaPreviaRepository;

@Component
public class ReclassificacaoService {

	@Autowired
	private UsuarioService usuarioLogado;
	
	@Autowired
	private PropostaService propostaService;
	
	@Autowired
	private ParecerTecnicoVistoriaPreviaRepository parecerTecnicoVistoriaPreviaRepository;
	
	@Autowired
	private LaudoVistoriaPreviaRepository laudoVistoriaPreviaRepository;
	
	@Autowired
	private ItemSeguradoService itemSeguradoService;
	
	@Autowired
	private RestricaoService restricaoService;
	
	@Autowired
	private ParametroControleDataService parametroControleDataService;
	
	@Autowired
	private HistoricoLaudoVistoriaPreviaService historicoLaudoVistoriaPreviaBusiness;
	
	@Autowired
	private ParametroVistoriaPreviaGeralRepository parametroVistoriaPreviaGeralRepository;
	
	@Autowired
	private AjusteRestClient ajusteRestClient;
	
	private static final Logger LOGGER = LogManager.getLogger(ReclassificacaoService.class);
	
	public Reclassificacao getReclassificacao(final NegocioComponentDto negocioComponentDto){
		List<Object[]> list = propostaService.buscarPropostaReclassificacao(negocioComponentDto.getCodigoProposta(), negocioComponentDto.getNumeroItemSegurado());
		Iterator<Object[]> iterator = list.listIterator();
		
		while (iterator.hasNext()) {        	
            final Object[] itemList = iterator.next();
            final ItemSegurado itemSegurado = (ItemSegurado) itemList[0];
            final LaudoVistoriaPrevia laudoVistoriaPrevia = (LaudoVistoriaPrevia) itemList[1];
            final VeiculoVistoriaPrevia veiculoVistoriaPrevia = (VeiculoVistoriaPrevia) itemList[2];
            
            Reclassificacao reclassificacao = new Reclassificacao();
            
            if(itemSegurado != null){
            	reclassificacao.setChassi(itemSegurado.getCodChassiVeiculo());
            	reclassificacao.setPlaca(itemSegurado.getCodPlacaVeiculo());
            	reclassificacao.setTipoHistorico(itemSegurado.getTipoHistorico());
            }
            
            if(laudoVistoriaPrevia != null){
            	reclassificacao.setVistoria(laudoVistoriaPrevia.getCdLvpre());
            	reclassificacao.setEmpresaVistoriadora(laudoVistoriaPrevia.getCdAgrmtVspre());
            	reclassificacao.setDataTransmissao(laudoVistoriaPrevia.getDtTrnsmVspre());
            	reclassificacao.setDataVistoria(laudoVistoriaPrevia.getDtVspre());
            	            	            	
            	final StatusLaudoEnum statusLaudo = StatusLaudoEnum.obterPorCodigo(laudoVistoriaPrevia.getCdSitucVspre());
            	
            	reclassificacao.setStatusLaudo(statusLaudo.getDescricao());
            	reclassificacao.setComboNovoStatusList(comboNovoStatus(statusLaudo));            	            	
            	reclassificacao.setParecerTecnicoVistoriaPrevias(buscarInformacaoTecnica(laudoVistoriaPrevia.getCdLvpre(), laudoVistoriaPrevia.getNrVrsaoLvpre()));
            	
            	reclassificacao.setVersaoLaudo(laudoVistoriaPrevia.getNrVrsaoLvpre());
            }
            
            if(veiculoVistoriaPrevia != null){
            	reclassificacao.setCrlv(veiculoVistoriaPrevia.getCdDutVeicu());
            	reclassificacao.setExpedido(veiculoVistoriaPrevia.getDtExpdcDut());
            	reclassificacao.setAnoModelo(veiculoVistoriaPrevia.getNrAnoModelVeicu());
            	reclassificacao.setNomeCrlv(veiculoVistoriaPrevia.getNmDutVeicu());
            	
            	if(veiculoVistoriaPrevia.getNrCpfDut() != null){
            		reclassificacao.setCprfCnpj(veiculoVistoriaPrevia.getNrCpfDut());
            	} else {
            		reclassificacao.setCprfCnpj(veiculoVistoriaPrevia.getNrCnpjDut());
            	}            	
            }
                        
            return reclassificacao;
        }
		
		return null;
	}
	
	private void executarAjuste(final Long codigoProposta, final Long numeroItemSegurado){
		try{
			final Restricao restricao = restricaoService.buscarRestricao(codigoProposta, numeroItemSegurado, TipoRestricao.AJU.getValue());
			if(restricao != null){					
				ajusteRestClient.executarAjusteNovo(restricao.getIdRestr());
			}
		}catch (RestClientException e) {
			LOGGER.error("erro ao executar AjusteRestClient executarAjuste, numero item: " + numeroItemSegurado + " , detalhe: " + ExceptionUtils.getRootCause(e));
		}
	}
	
	private List<ComboNovoStatus> comboNovoStatus(final StatusLaudoEnum statusLaudo){
		List<ComboNovoStatus> comboNovoStatusList = new ArrayList<>(); 
		
		if(statusLaudo == StatusLaudoEnum.RECUSADA){
			comboNovoStatusList.add(new ComboNovoStatus(StatusLaudoEnum.ACEITACAO_FORCADA, StatusLaudoEnum.RECUSADA));
			comboNovoStatusList.add(new ComboNovoStatus(StatusLaudoEnum.REGULARIZADO, StatusLaudoEnum.RECUSADA));
			
		}else if(statusLaudo == StatusLaudoEnum.SUJEITO_A_ANALISE){
			comboNovoStatusList.add(new ComboNovoStatus(StatusLaudoEnum.LIBERADA, StatusLaudoEnum.SUJEITO_A_ANALISE));
			comboNovoStatusList.add(new ComboNovoStatus(StatusLaudoEnum.REGULARIZADO, StatusLaudoEnum.SUJEITO_A_ANALISE));			
			
		}else if(statusLaudo == StatusLaudoEnum.ACEITAVEL){
			comboNovoStatusList.add(new ComboNovoStatus(StatusLaudoEnum.ACEITAVEL_LIBERADA, StatusLaudoEnum.ACEITAVEL));			
		}
		
		return comboNovoStatusList;
	}
	
	private List<ParecerTecnicoVistoriaPrevia> buscarInformacaoTecnica(final String codigoLaudo, final Long versaoLaudo){
		return parecerTecnicoVistoriaPreviaRepository.buscarInformacaoTecnica(codigoLaudo, versaoLaudo);
	}
	
	public Boolean salvarNovoStatusReclassificacao(final ReclassificacaoAlterarStatus reclassificacaoAlterarStatus){
		try{									
	
			String idUsuarioLogado = usuarioLogado.getUsuarioId();
		    validaReclassificacaoAlterarStatus(reclassificacaoAlterarStatus);

			
			final ParametroControleData parametroControleData = parametroControleDataService.obterParametroControleData();
			final ParametroVistoriaPreviaGeral parametroVistoriaPreviaGeral = parametroVistoriaPreviaGeralRepository.findByParametroVistoriaPreviaGeral();
			
			LaudoVistoriaPrevia laudoVistoriaPrevia = laudoVistoriaPreviaRepository.findLaudoByCdLvpreNrVrsaoLvpreCdSituc(reclassificacaoAlterarStatus.getNumeroLaudo(), reclassificacaoAlterarStatus.getVersaoLaudo());
			laudoVistoriaPrevia.setCdSitucAnterVspre(reclassificacaoAlterarStatus.getComboNovoStatus().getStatusAnterior());
			laudoVistoriaPrevia.setCdSitucVspre(reclassificacaoAlterarStatus.getComboNovoStatus().getCodigo());
			laudoVistoriaPrevia.setDtRclsfVspre(parametroControleData.getDtReferOnlin());			
			laudoVistoriaPrevia.setDtUltmaAlter(parametroControleData.getDtReferOnlin());			
			laudoVistoriaPrevia.setDsMotvRclsfVspre(reclassificacaoAlterarStatus.getJustificativa());
			laudoVistoriaPrevia.setCdUsuroUltmaAlter(idUsuarioLogado);
			
			int quantidadeDias = 0;
			if(parametroVistoriaPreviaGeral.getVlParamVspre() != null){
				quantidadeDias = Integer.parseInt(parametroVistoriaPreviaGeral.getVlParamVspre());
			}
									
			Calendar c = Calendar.getInstance();
			c.setTime(laudoVistoriaPrevia.getDtVspre());
			c.add(Calendar.DATE, +quantidadeDias);
						
			laudoVistoriaPrevia.setDtLimitBlqueVspre(c.getTime());
						
			ItemSegurado itemSegurado = itemSeguradoService.findByItemSegurado(reclassificacaoAlterarStatus.getNumeroItem(), reclassificacaoAlterarStatus.getTipoHistorico());			
			itemSegurado.setCodSituacaoVistoriaPrevia(reclassificacaoAlterarStatus.getComboNovoStatus().getCodigo());
			
			Restricao restricao = restricaoService.buscarRestricao(reclassificacaoAlterarStatus.getCodigoProposta(), reclassificacaoAlterarStatus.getNumeroItem(), TipoRestricao.VIS.getValue());
			restricao.setDtFechmRestr(parametroControleData.getDtReferOnlin());
			restricao.setDtUltmaAlter(parametroControleData.getDtReferOnlin());			
			restricao.setDsJustfLibecRestr(reclassificacaoAlterarStatus.getJustificativa());
			restricao.setCdSitucRestr(TipoRestricao.LIB.getValue());
			restricao.setTpFechmRestr(reclassificacaoAlterarStatus.getTipoFechamentoRestricao());			
			restricao.setCdUsuroUltmaAlter(idUsuarioLogado);	
						
			salvarReclassificao(laudoVistoriaPrevia, itemSegurado, restricao);			
			historicoLaudoVistoriaPreviaBusiness.salvarNovoHistoricoLaudoVistoriaPrevia(laudoVistoriaPrevia, reclassificacaoAlterarStatus, idUsuarioLogado);			
		}catch (Exception e) {
			LOGGER.error("erro ao executar salvarNovoStatusReclassificacao detalhe: " + e.getMessage());
			return Boolean.FALSE;
		}
		
		executarAjuste(reclassificacaoAlterarStatus.getCodigoProposta(), reclassificacaoAlterarStatus.getNumeroItem());		
		return Boolean.TRUE;		
	}
	
	public void salvarReclassificao(final LaudoVistoriaPrevia laudoVistoriaPrevia, final ItemSegurado itemSegurado, final Restricao restricao){
		laudoVistoriaPreviaRepository.save(laudoVistoriaPrevia);
		itemSeguradoService.save(itemSegurado);
		restricaoService.save(restricao);
	}
	
	public Boolean salvarNovoStatusReclassificacaoLista(final ListaReclassificacaoAlterarStatus listaReclassificacaoAlterarStatus, final String idUsuarioLogado){
		for(long numeroItem : listaReclassificacaoAlterarStatus.getNumeroItem()){	
			try{									
			    validaReclassificacaoStatus(listaReclassificacaoAlterarStatus);
				final ParametroControleData parametroControleData = parametroControleDataService.obterParametroControleData();
				final ParametroVistoriaPreviaGeral parametroVistoriaPreviaGeral = parametroVistoriaPreviaGeralRepository.findByParametroVistoriaPreviaGeral();
				
				LaudoVistoriaPrevia laudoVistoriaPrevia = laudoVistoriaPreviaRepository.findLaudoByCdLvpreNrVrsaoLvpreCdSituc(listaReclassificacaoAlterarStatus.getNumeroLaudo(), listaReclassificacaoAlterarStatus.getVersaoLaudo());
				laudoVistoriaPrevia.setCdSitucAnterVspre(listaReclassificacaoAlterarStatus.getComboNovoStatus().getStatusAnterior());
				laudoVistoriaPrevia.setCdSitucVspre(listaReclassificacaoAlterarStatus.getComboNovoStatus().getCodigo());
				laudoVistoriaPrevia.setDtRclsfVspre(parametroControleData.getDtReferOnlin());			
				laudoVistoriaPrevia.setDtUltmaAlter(parametroControleData.getDtReferOnlin());			
				laudoVistoriaPrevia.setDsMotvRclsfVspre(listaReclassificacaoAlterarStatus.getJustificativa());
				laudoVistoriaPrevia.setCdUsuroUltmaAlter(idUsuarioLogado);
				
				int quantidadeDias = 0;
				if(parametroVistoriaPreviaGeral.getVlParamVspre() != null){
					quantidadeDias = Integer.parseInt(parametroVistoriaPreviaGeral.getVlParamVspre());
				}
				
				final Date dataLaudoVistoria = laudoVistoriaPrevia.getDtVspre();
							
				Calendar c = Calendar.getInstance();
				c.setTime(dataLaudoVistoria);
				c.add(Calendar.DATE, +quantidadeDias);
							
				laudoVistoriaPrevia.setDtLimitBlqueVspre(c.getTime());
							
				ItemSegurado itemSegurado = itemSeguradoService.findByItemSegurado(numeroItem, listaReclassificacaoAlterarStatus.getTipoHistorico());			
				itemSegurado.setCodSituacaoVistoriaPrevia(listaReclassificacaoAlterarStatus.getComboNovoStatus().getCodigo());
				
				Restricao restricao = restricaoService.buscarRestricao(listaReclassificacaoAlterarStatus.getCodigoProposta(), numeroItem, TipoRestricao.VIS.getValue());
				restricao.setDtFechmRestr(parametroControleData.getDtReferOnlin());
				restricao.setDtUltmaAlter(parametroControleData.getDtReferOnlin());			
				restricao.setDsJustfLibecRestr(listaReclassificacaoAlterarStatus.getJustificativa());
				restricao.setCdSitucRestr(TipoRestricao.LIB.getValue());
				restricao.setTpFechmRestr(listaReclassificacaoAlterarStatus.getTipoFechamentoRestricao());			
				restricao.setCdUsuroUltmaAlter(idUsuarioLogado);	
							
				salvarReclassificao(laudoVistoriaPrevia, itemSegurado, restricao);			
				historicoLaudoVistoriaPreviaBusiness.salvarListaNovoHistoricoLaudoVistoriaPrevia(laudoVistoriaPrevia, listaReclassificacaoAlterarStatus, idUsuarioLogado);
				
			}catch (Exception e) {
				LOGGER.error(e.getStackTrace());
				return Boolean.FALSE;
			}
			
			executarAjuste(listaReclassificacaoAlterarStatus.getCodigoProposta(), numeroItem);
		}
		
		return Boolean.TRUE;		
	}
	
	private void validaReclassificacaoStatus(ListaReclassificacaoAlterarStatus listaReclassificacaoAlterarStatus) {
		if(listaReclassificacaoAlterarStatus.getCodigoProposta() == null){
			throw new BusinessVPException("Código da proposta inválido!");
		}
		
		if(listaReclassificacaoAlterarStatus.getNumeroItem() == null || listaReclassificacaoAlterarStatus.getNumeroItem().isEmpty()){
			throw new BusinessVPException("Número do item segurado inválido!");
		}
		
		if(listaReclassificacaoAlterarStatus.getJustificativa() == null || listaReclassificacaoAlterarStatus.getJustificativa().trim().length() <= 0){
			throw new BusinessVPException("Justificativa da reclassificação inválida!");
		}
		
		if(listaReclassificacaoAlterarStatus.getComboNovoStatus() == null){
			throw new BusinessVPException("Novo status da reclassificação inválido!");
		}
		
		if(listaReclassificacaoAlterarStatus.getVersaoLaudo() == null){
			throw new BusinessVPException("Código versão laudo inválido!");
		}
		
		if(listaReclassificacaoAlterarStatus.getNumeroLaudo() == null || listaReclassificacaoAlterarStatus.getNumeroLaudo().trim().length() <= 0){
			throw new BusinessVPException("Número laudo inválido!");
		}
		
		if(listaReclassificacaoAlterarStatus.getTipoHistorico() == null || listaReclassificacaoAlterarStatus.getTipoHistorico().trim().length() <= 0){
			throw new BusinessVPException("Tipo historico inválido!");
		}
		
		if(!((listaReclassificacaoAlterarStatus.getTipoFechamentoRestricao() != null && 
				listaReclassificacaoAlterarStatus.getTipoFechamentoRestricao() == 1) 
				|| (listaReclassificacaoAlterarStatus.getTipoFechamentoRestricao() != null &&
				listaReclassificacaoAlterarStatus.getTipoFechamentoRestricao() == 3))) {
			throw new BusinessVPException("Tipo do fechamento restrição inválido!");
		}
	}
	
	private void validaReclassificacaoAlterarStatus(ReclassificacaoAlterarStatus reclassificacaoAlterarStatus) {

		if(reclassificacaoAlterarStatus.getCodigoProposta() == null){
			throw new BusinessVPException("Código da proposta inválido!");
		}
		
		if(reclassificacaoAlterarStatus.getNumeroItem() == null){
			throw new BusinessVPException("Número do item segurado inválido!");
		}
		
		if(reclassificacaoAlterarStatus.getJustificativa() == null || reclassificacaoAlterarStatus.getJustificativa().trim().length() <= 0){
			throw new BusinessVPException("Justificativa da reclassificação inválida!");
		}
		
		if(reclassificacaoAlterarStatus.getComboNovoStatus() == null){
			throw new BusinessVPException("Novo status da reclassificação inválido!");
		}
		
		if(reclassificacaoAlterarStatus.getVersaoLaudo() == null){
			throw new BusinessVPException("Código versão laudo inválido!");
		}
		
		if(reclassificacaoAlterarStatus.getNumeroLaudo() == null || reclassificacaoAlterarStatus.getNumeroLaudo().trim().length() <= 0){
			throw new BusinessVPException("Número laudo inválido!");
		}
		
		if(reclassificacaoAlterarStatus.getTipoHistorico() == null || reclassificacaoAlterarStatus.getTipoHistorico().trim().length() <= 0){
			throw new BusinessVPException("Tipo historico inválido!");
		}
		
		if(!((reclassificacaoAlterarStatus.getTipoFechamentoRestricao() != null &&
				reclassificacaoAlterarStatus.getTipoFechamentoRestricao() == 1) 
				|| (reclassificacaoAlterarStatus.getTipoFechamentoRestricao() != null 
				&& reclassificacaoAlterarStatus.getTipoFechamentoRestricao() == 3))){
			throw new BusinessVPException("Tipo do fechamento restrição inválido!");
		}
	}
}
