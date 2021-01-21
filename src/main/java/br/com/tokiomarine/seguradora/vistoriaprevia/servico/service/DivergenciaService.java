package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.aceitacao.crud.model.Restricao;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.AjusteRestClient;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.util.RestClientException;
import br.com.tokiomarine.seguradora.ext.act.service.RestricaoService;
import br.com.tokiomarine.seguradora.ext.ssv.service.ConteudoColunaTipoService;
import br.com.tokiomarine.seguradora.ext.ssv.service.ParametroControleDataService;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ParametroControleData;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Dispensa;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ListaDeDispensa;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoRestricao;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;

@Component
public class DivergenciaService {
	
	@Autowired
	private UsuarioService usuarioLogado;
	
	@Autowired
	private ConteudoColunaTipoService conteudoColunaTipoService;
	
	@Autowired
	private RestricaoService restricaoService;
	
	@Autowired
	private ParametroControleDataService parametroControleDataService;
	
	@Autowired
	private AjusteRestClient ajusteRestClient;
	
	private static final Logger LOGGER = LogManager.getLogger(DivergenciaService.class);
	
	public List<ConteudoColunaTipo> motivoDivergencias(){
		return conteudoColunaTipoService.motivoDivergencia();
	}
	
	@Transactional
	public Boolean salvarMotivoDivergencia(final Dispensa dispensa) {
		try{									
			if(dispensa.getConteudoColunaTipo() == null){
				throw new BusinessVPException("Motivo dispensa inválido!");
			}
			
			if(dispensa.getJustificativaDispensa() == null || dispensa.getJustificativaDispensa().length() <= 0 || dispensa.getJustificativaDispensa().length() > 400){
				throw new BusinessVPException("Justificativa da dispensa inválida!");
			}
			
			if(dispensa.getCodigoProposta() == null || dispensa.getCodigoProposta() <= 0){
				throw new BusinessVPException("Código endosso inválido!");
			}
			
			if(dispensa.getNumeroItemSegurado() == null || dispensa.getNumeroItemSegurado() <= 0){
				throw new BusinessVPException("Item seguro  inválido!");
			}				

			if(!((dispensa.getTipoFechamentoRestricao() != null && dispensa.getTipoFechamentoRestricao() == 1) || 
					(dispensa.getTipoFechamentoRestricao() != null && dispensa.getTipoFechamentoRestricao() == 3))){
				throw new BusinessVPException("Tipo do fechamento restrição inválido!");
			}
			salvarRestricao(dispensa);						
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Boolean.FALSE;
		}
		
		execuraAjuste(dispensa.getCodigoProposta(), dispensa.getNumeroItemSegurado());
		
		return Boolean.TRUE;
	}
	
	@Transactional
	public void salvarRestricao(final Dispensa dispensa){
		final ParametroControleData parametroControleData = parametroControleDataService.obterParametroControleData();
		
		Restricao restricao = restricaoService.buscarRestricao(dispensa.getCodigoProposta(), dispensa.getNumeroItemSegurado(), TipoRestricao.VIS.getValue());
		
		restricao.setDsJustfLibecRestr(dispensa.getJustificativaDispensa());						
		restricao.setCdSitucRestr(TipoRestricao.LIB.getValue());
		restricao.setTpLibec(dispensa.getConteudoColunaTipo().getVlCntdoColunTipo());
		restricao.setDtFechmRestr(parametroControleData.getDtReferOnlin());
		restricao.setDtUltmaAlter(parametroControleData.getDtReferOnlin());
		restricao.setTpFechmRestr(dispensa.getTipoFechamentoRestricao());
		restricao.setCdUsuroUltmaAlter(dispensa.getIdUsuarioLogado());
		
		restricaoService.save(restricao);
	}
	
	@Transactional
	public Boolean salvarMotivoDivergenciaLista(final ListaDeDispensa listaDeDispensa) {
		
		String idUsuarioLogado = usuarioLogado.getUsuarioId();
		validaListaDeDispensa(listaDeDispensa);
		for(Long numeroItemSegurado : listaDeDispensa.getNumeroItemSegurado()){
			try{
				listaDeDispensa.setIdUsuarioLogado(idUsuarioLogado);
				
				
				
				salvarListaRestricao(listaDeDispensa, numeroItemSegurado);
				
			}catch (Exception e) {
				LOGGER.error(e.getMessage());
				return Boolean.FALSE;
			}
			
			execuraAjuste(listaDeDispensa.getCodigoProposta(), numeroItemSegurado);
		}
						
		return Boolean.TRUE;
	}
	
	private void execuraAjuste(final Long codigoProposta, final Long numeroItemSegurado){
		try{
			final Restricao restricao = restricaoService.buscarRestricao(codigoProposta, numeroItemSegurado, TipoRestricao.AJU.getValue());
			if(restricao != null){					
				ajusteRestClient.executarAjusteNovo(restricao.getIdRestr());
			}
		}catch (RestClientException e) {
			LOGGER.error("erro ao executar AjusteRestClient executarAjuste, numero item: " + numeroItemSegurado + " , detalhe: " + e.getMessage());
		}
	}
	
	@Transactional
	public void salvarListaRestricao(final ListaDeDispensa listaDeDispensa, final Long numeroItemSegurado){
		final ParametroControleData parametroControleData = parametroControleDataService.obterParametroControleData();
		
		Restricao restricao = restricaoService.buscarRestricao(listaDeDispensa.getCodigoProposta(), numeroItemSegurado, TipoRestricao.VIS.getValue());
		
		restricao.setDsJustfLibecRestr(listaDeDispensa.getJustificativaDispensa());						
		restricao.setCdSitucRestr(TipoRestricao.LIB.getValue());
		restricao.setTpLibec(listaDeDispensa.getConteudoColunaTipo().getVlCntdoColunTipo());
		restricao.setDtFechmRestr(parametroControleData.getDtReferOnlin());
		restricao.setDtUltmaAlter(parametroControleData.getDtReferOnlin());
		restricao.setTpFechmRestr(listaDeDispensa.getTipoFechamentoRestricao());
		restricao.setCdUsuroUltmaAlter(listaDeDispensa.getIdUsuarioLogado());
		
		restricaoService.save(restricao);
	}
	
	private void validaListaDeDispensa(ListaDeDispensa listaDeDispensa) {
		if(listaDeDispensa.getConteudoColunaTipo() == null){
			throw new BusinessVPException("Motivo dispensa inválido!");
		}
		
		if(listaDeDispensa.getJustificativaDispensa() == null || listaDeDispensa.getJustificativaDispensa().length() <= 0 || listaDeDispensa.getJustificativaDispensa().length() > 400){
			throw new BusinessVPException("Justificativa da dispensa inválida!");
		}
		
		if(listaDeDispensa.getCodigoProposta() == null || listaDeDispensa.getCodigoProposta() <= 0){
			throw new BusinessVPException("Código endosso inválido!");
		}
		
		if(listaDeDispensa.getNumeroItemSegurado() == null || listaDeDispensa.getNumeroItemSegurado().isEmpty()){
			throw new BusinessVPException("Item seguro  inválido!");
		}				

		if(!((listaDeDispensa.getTipoFechamentoRestricao() != null && listaDeDispensa.getTipoFechamentoRestricao() == 1) 
				|| (listaDeDispensa.getTipoFechamentoRestricao() != null && listaDeDispensa.getTipoFechamentoRestricao() == 3))){
			throw new BusinessVPException("Tipo do fechamento restrição inválido!");
		}
	}
}