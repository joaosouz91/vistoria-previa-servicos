package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.aceitacao.crud.model.Proposta;
import br.com.tokiomarine.seguradora.aceitacao.crud.model.Restricao;
import br.com.tokiomarine.seguradora.ext.act.service.PropostaService;
import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.ValorCaracteristicaItemSegurado;
import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.Veiculo;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.ssv.transacional.model.DescricaoItemSegurado;
import br.com.tokiomarine.seguradora.ssv.transacional.model.ItemSegurado;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PrestadoraVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VeiculoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.DetalheDoItemController;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.NegocioComponentDto;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.DetalheDoItem;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.StatusLaudoEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.restclient.ClientePessoaRestClient;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesVistoriaPrevia;

@Component
public class DetalheDoItemService {

	@Autowired
	private PropostaService propostaService;
					
	@Autowired
	private AlcadaUsuarioService alcadaUsuarioBusiness;
	
	@Autowired
	private MotivoVistoriaPreviaObrigatoriaService motivoVistoriaPreviaObrigatoriaBusiness;
	
	@Value("${url.ambiente}")
	private String urlAmbiente;
	
	@Value("${url.servidor.laudo}")
	private String urlLinkDetalhe;
	
	@Autowired
	private UsuarioService usuarioLogado;
	
	@Autowired
	private ClientePessoaRestClient clientePessoaRestClient;
	
	private static final Logger LOGGER = LogManager.getLogger(DetalheDoItemController.class);
	
	public DetalheDoItem consultaDetalheDoItem(final NegocioComponentDto negocioComponent){				
		String codigoUsuario = usuarioLogado.getUsuarioId();
		
		LOGGER.info("Usuario: " + codigoUsuario);
		
		List<Object[]> list = propostaService.buscarPropostaDetalheDoItem(negocioComponent.getCodigoProposta(), negocioComponent.getNumeroItemSegurado());
		Iterator<Object[]> iterator = list.listIterator();
		
		while (iterator.hasNext()) {        	
            final Object[] itemList = iterator.next();
            
            return getDetalheDoItem(itemList, negocioComponent);
        }
		
		return null;
	}

	private DetalheDoItem getDetalheDoItem(Object[] itemList, NegocioComponentDto negocioComponent) {
		DetalheDoItem detalheDoItem = new DetalheDoItem();

		final ItemSegurado itemSegurado = (ItemSegurado) itemList[0];
        final DescricaoItemSegurado descricaoItemSeguradoDescricaoFabricante = (DescricaoItemSegurado) itemList[1];
        final DescricaoItemSegurado descricaoItemSeguradoModeloDescricaoModelo = (DescricaoItemSegurado) itemList[2];
        final DescricaoItemSegurado descricaoItemSeguradoModeloDescricaoAnoModelo = (DescricaoItemSegurado) itemList[3];
        final DescricaoItemSegurado descricaoItemSeguradoModeloDescricaoCombustivel = (DescricaoItemSegurado) itemList[4];            
        final VeiculoVistoriaPrevia veiculoVistoriaPrevia = (VeiculoVistoriaPrevia) itemList[5];            
        final ValorCaracteristicaItemSegurado valorCaracFabricante = (ValorCaracteristicaItemSegurado)  itemList[6];            
        final ValorCaracteristicaItemSegurado valorCaracModelo = (ValorCaracteristicaItemSegurado)  itemList[7];
        final Veiculo veiculoCadastro = (Veiculo)  itemList[8];
        final ConteudoColunaTipo colunaTipoCombustivel = (ConteudoColunaTipo)  itemList[9];
        final ConteudoColunaTipo colunaStatusLaudo = (ConteudoColunaTipo)  itemList[10];
        final LaudoVistoriaPrevia laudoVistoriaPrevia = (LaudoVistoriaPrevia) itemList[11];
        final Restricao restricao = (Restricao) itemList[12];
        final ConteudoColunaTipo conteudoColunaTipo = (ConteudoColunaTipo) itemList[13];
        final Proposta proposta = (Proposta) itemList[14];
        final PrestadoraVistoriaPrevia prestadora = (PrestadoraVistoriaPrevia) itemList[15];            
        
        setItemSegurado(itemSegurado, detalheDoItem);
                    
        try{
        	detalheDoItem.setUrlAmbiente(urlAmbiente);
        	detalheDoItem.setPropostaFabricante(descricaoItemSeguradoDescricaoFabricante.getDsVlcarInfdo());
            detalheDoItem.setPropostaCodFabricante(descricaoItemSeguradoDescricaoFabricante.getCodValorCaracteristicaItemSegurado());
            
            detalheDoItem.setPropostaModelo(descricaoItemSeguradoModeloDescricaoModelo.getDsVlcarInfdo());
            detalheDoItem.setPropostaCodModelo(descricaoItemSeguradoModeloDescricaoModelo.getCodValorCaracteristicaItemSegurado());
            
            detalheDoItem.setPropostaAnoModelo(descricaoItemSeguradoModeloDescricaoAnoModelo.getDsVlcarInfdo());
        }catch (Exception e) {
			LOGGER.error(e);
		}
        
        detalheDoItem.setPropostaCombustivel(descricaoItemSeguradoModeloDescricaoCombustivel.getDsVlcarInfdo());            
        detalheDoItem.setPropostaCodCombustivel(descricaoItemSeguradoModeloDescricaoCombustivel.getCodValorCaracteristicaItemSegurado());
        
        detalheDoItem.setLiberaDetalhe(Boolean.FALSE);

        if (restricao != null && !ConstantesVistoriaPrevia.SITUACAO_NEGOCIO_GRADE.equals(restricao.getCdSitucRestr())) {

        	setVeiculoVistoriaPrevia(veiculoVistoriaPrevia, detalheDoItem); 
        
	        if(valorCaracFabricante != null){
	        	detalheDoItem.setLaudoFabricante(valorCaracFabricante.getDsVaricInico());
	        	detalheDoItem.setLaudoCodFabricante(valorCaracFabricante.getCdVlcarItseg());
	        }
	
	        if(valorCaracModelo != null){
	        	detalheDoItem.setLaudoModelo(valorCaracModelo.getDsVaricInico());
	        	detalheDoItem.setLaudoCodModelo(valorCaracModelo.getCdVlcarItseg());
	        }
	        
	        if(veiculoCadastro != null){
	        	detalheDoItem.setLaudoCodCombustivel(veiculoCadastro.getTpCmbst());
	        }
	        
	        if(colunaTipoCombustivel != null){
	        	detalheDoItem.setLaudoCombustivel(colunaTipoCombustivel.getDsCoptaColunTipo());
	        }
	        
	        if(colunaStatusLaudo != null){
	        	detalheDoItem.setLaudoStatus(colunaStatusLaudo.getDsCoptaColunTipo());
	        }

	        setLaudoVistoriaPrevia(laudoVistoriaPrevia, detalheDoItem);
        }
        
        if(restricao != null){	            
            detalheDoItem.setCodigoSituacaoGrade(restricao.getCdRestrGrade());
            detalheDoItem.setCodigoSituacaoRestricao(restricao.getCdSitucRestr());
        }
        
        if(conteudoColunaTipo != null){
        	detalheDoItem.setStatusRestricao(conteudoColunaTipo.getDsRmidaColunTipo());
        }
                    
        if(proposta != null && proposta.getTpPpota().equals("E")){
        	detalheDoItem.setTipoFechamentoRestricao(3L);            	
        	detalheDoItem.setTituloDaTela( motivoVistoriaPreviaObrigatoriaBusiness.buscarMotivoVistoriaPreviaCodigoEndosso(negocioComponent.getNumeroItemSegurado(), negocioComponent.getCodigoProposta()) );
        	
        }else if(proposta != null && proposta.getTpPpota().equals("N")){
        	detalheDoItem.setTipoFechamentoRestricao(1L);
        	detalheDoItem.setTituloDaTela( motivoVistoriaPreviaObrigatoriaBusiness.buscarMotivoVistoriaPreviaItemSegurado(negocioComponent.getNumeroItemSegurado()) );
        	
        }else{
        	detalheDoItem.setTipoFechamentoRestricao(0L);
        	detalheDoItem.setTituloDaTela("");
        }
        
        detalheDoItem.setLiberarPrestadora(Boolean.FALSE);
        if(prestadora != null){
        	detalheDoItem.setSitePrestadora(prestadora.getDsSite());
        	detalheDoItem.setNomePrestadora(prestadora.getNmRazaoSocal());
        	detalheDoItem.setLiberarPrestadora(Boolean.TRUE);
        }
        
        return detalheDoItem;
	}

	private void setLaudoVistoriaPrevia(final LaudoVistoriaPrevia laudoVistoriaPrevia, DetalheDoItem detalheDoItem) {
		if(laudoVistoriaPrevia != null){ // caso a restrição seja GRD não exibe o laudo, laudo pode ser do endosso.
			detalheDoItem.setCodigoLaudo(laudoVistoriaPrevia.getCdLvpre());
			detalheDoItem.setJustificativa(laudoVistoriaPrevia.getDsMotvRclsfVspre());
		    detalheDoItem.setDataDaAcao(laudoVistoriaPrevia.getDtRclsfVspre());            
		    detalheDoItem.setResponsavel(laudoVistoriaPrevia.getCdUsuroUltmaAlter());
		    
		    detalheDoItem.setNrVrsaoLvpre(laudoVistoriaPrevia.getNrVrsaoLvpre());
		    detalheDoItem.setLiberaDetalhe(Boolean.TRUE);
		    detalheDoItem.setUrlLinkDetalhe(
		    		MessageFormat.format(urlLinkDetalhe,
		    				laudoVistoriaPrevia.getCdLvpre(), laudoVistoriaPrevia.getNrVrsaoLvpre(), detalheDoItem.getLaudoChassi()));
		    
		    if(laudoVistoriaPrevia.getCdSitucVspre() == null){
		    	detalheDoItem.setLiberarTabelaReclassificacao(Boolean.FALSE);
		    	
		    }else if(StatusLaudoEnum.ACEITACAO_FORCADA.getCodigo().equals(laudoVistoriaPrevia.getCdSitucVspre()) 
		    		|| StatusLaudoEnum.ACEITAVEL_LIBERADA.getCodigo().equals(laudoVistoriaPrevia.getCdSitucVspre())
		    		|| StatusLaudoEnum.LIBERADA.getCodigo().equals(laudoVistoriaPrevia.getCdSitucVspre())
		    		|| StatusLaudoEnum.REGULARIZADO.getCodigo().equals(laudoVistoriaPrevia.getCdSitucVspre())){
		    	
		    	detalheDoItem.setLiberarTabelaReclassificacao(Boolean.TRUE);
		    }else{
		    	detalheDoItem.setLiberarTabelaReclassificacao(Boolean.FALSE);
		    }	            
		}
	}

	private void setVeiculoVistoriaPrevia(final VeiculoVistoriaPrevia veiculoVistoriaPrevia, DetalheDoItem detalheDoItem) {
		if(veiculoVistoriaPrevia != null){
		    detalheDoItem.setLaudoPlaca(veiculoVistoriaPrevia.getCdPlacaVeicu());
		    detalheDoItem.setLaudoChassi(veiculoVistoriaPrevia.getCdChassiVeicu());
		    detalheDoItem.setLaudoNomeCrlv(veiculoVistoriaPrevia.getNmDutVeicu());
		    detalheDoItem.setLaudoAnoModelo(veiculoVistoriaPrevia.getNrAnoModelVeicu());
		    
		    if(veiculoVistoriaPrevia.getNrCnpjDut() == null || veiculoVistoriaPrevia.getNrCnpjDut() == 0){
		    	detalheDoItem.setLaudoCpfCnpjCrlv(veiculoVistoriaPrevia.getNrCpfDut());
		    }else{
		    	detalheDoItem.setLaudoCpfCnpjCrlv(veiculoVistoriaPrevia.getNrCnpjDut());
		    }
		}
	}

	private void setItemSegurado(final ItemSegurado itemSegurado, DetalheDoItem detalheDoItem) {
		if(itemSegurado != null){            	
		    detalheDoItem.setNrItseg(itemSegurado.getNumItemSegurado());
		    detalheDoItem.setTpHistoItseg(itemSegurado.getTipoHistorico());
		    detalheDoItem.setCdEndos(itemSegurado.getCodEndosso());
		    detalheDoItem.setPropostaPlaca(itemSegurado.getCodPlacaVeiculo());
		    detalheDoItem.setPropostaChassi(itemSegurado.getCodChassiVeiculo());
		    detalheDoItem.setLiberarDispensaAlcada( alcadaUsuarioBusiness.verificarAlcadaUsuario(itemSegurado.getCodModuloProduto(), 1L) );
		    
		    try {
		    	Long codCliente = itemSegurado.getCodCliente();
		    	
		    	LOGGER.info("[setItemSegurado] início consulta de cliente: " + codCliente);
				
		    	clientePessoaRestClient.findClientByCodCliente(codCliente).stream().findFirst().ifPresent(cliente -> {
					detalheDoItem.setCdClien(itemSegurado.getCodCliente());
			        detalheDoItem.setPropostaNomeCliente(cliente.getNmClien());
			        detalheDoItem.setPropsotaClienteCpfCnpj(new Long(cliente.getNrCnpjCpf()));
				});
				
		        LOGGER.info("[setItemSegurado] fim consulta de cliente: " + codCliente);
			} catch (Exception e) {				
				LOGGER.error(e);
			}    
		}
	}	
}