package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import br.com.tokiomarine.seguradora.aceitacao.crud.model.Restricao;
import br.com.tokiomarine.seguradora.ext.act.service.RestricaoService;
import br.com.tokiomarine.seguradora.ext.ssv.service.ItemSeguradoService;
import br.com.tokiomarine.seguradora.ssv.transacional.model.ItemSegurado;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PrestadoraVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ProponenteVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VeiculoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.AjusteParametros;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.TipoAjusteVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.VerificaDispensaLaudo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class AjusteVistoriaPreviaService {

	@Autowired
	private LaudoVistoriaPreviaRepository laudoVistoriaPreviaRepository;
	
	@Autowired
	private ParecerTecnicoLaudoVistoriaPreviaRepository parecerTecnicoLaudoVistoriaPreviaRepository;
	
	@Autowired
	private AcessorioLaudoVistoriaPreviaRepository acessorioLaudoVistoriaPreviaRepository;
	
	@Autowired
	private AvariaLaudoVistoriaPreviaRepository avariaLaudoVistoriaPreviaRepository;
	
	@Autowired
	private PrestadoraVistoriaPreviaRepository prestadoraVistoriaPreviaRepository;
	
	@Autowired
	private ItemSeguradoService itemSeguradoService;
	
	@Autowired
	private RestricaoService restricaoService;
	
	private static final Logger LOGGER = LogManager.getLogger(AjusteVistoriaPreviaService.class);
	
	public AjusteParametros getControleVistoriaPrevia(final String cdLvpre) {
		AjusteParametros ajusteParametros = new AjusteParametros();
		
        try {
            if(cdLvpre == null || cdLvpre.trim().length() == 0 ) {
            	throw new BusinessVPException("Codigo do Laudo da Vistoria Previa esta nulo");
            }            
            
            final List<Object[]> list = laudoVistoriaPreviaRepository.findLaudoBy(cdLvpre);
            final Iterator<Object[]> iterator = list.listIterator();
            
            while (iterator.hasNext()) {        	
                final Object[] itemList = iterator.next();                
                final LaudoVistoriaPrevia laudoVistoriaPrevia = (LaudoVistoriaPrevia) itemList[0];
                final VeiculoVistoriaPrevia veiculoVistoriaPrevia = (VeiculoVistoriaPrevia) itemList[1];
                final ProponenteVistoriaPrevia proponenteVistoriaPrevia = (ProponenteVistoriaPrevia) itemList[2];
                
                if(laudoVistoriaPrevia != null){
                	ajusteParametros.setiCdVistoriadoraVp(laudoVistoriaPrevia.getCdAgrmtVspre());
                	ajusteParametros.setiNoLaudoVp(laudoVistoriaPrevia.getCdLvpre());
                	ajusteParametros.setNumVersaoLaudoVistoriaPrevia(laudoVistoriaPrevia.getNrVrsaoLvpre());
                	ajusteParametros.setiStLaudoVp(laudoVistoriaPrevia.getCdSitucVspre());
                	ajusteParametros.setiTpVistoriadorVp(laudoVistoriaPrevia.getTpLocalVspre());
                	ajusteParametros.setiCdFinalidadeVp(laudoVistoriaPrevia.getCdFnaldVspre());
                	ajusteParametros.setiDtRealizacaoVp(laudoVistoriaPrevia.getDtVspre());                	
                }
                
                if(veiculoVistoriaPrevia != null){
                	ajusteParametros.setiNoAnoFabLaudoVp(veiculoVistoriaPrevia.getNrAnoFabrcVeicu());
                	ajusteParametros.setiNoAnoModLaudoVp(veiculoVistoriaPrevia.getNrAnoModelVeicu());
                	ajusteParametros.setiNmAlienadoVp(veiculoVistoriaPrevia.getNmAlienVeicu());
                	ajusteParametros.setiCdCambioVp(veiculoVistoriaPrevia.getCdCamboVeicu());
                	ajusteParametros.setiTpCambioVp(veiculoVistoriaPrevia.getTpCamboVeicu());
                	ajusteParametros.setiIdRodoar(veiculoVistoriaPrevia.getIcRodoar());
                	ajusteParametros.setiTpUtilizacaoVp(veiculoVistoriaPrevia.getTpUtlzcVeicu());
                	ajusteParametros.setiIdVeiculoCargaVp(veiculoVistoriaPrevia.getIcVeicuCarga());
                	ajusteParametros.setiCdCarroceriaVp(veiculoVistoriaPrevia.getCdCarroVeicu());
                	ajusteParametros.setiNoCpfCrlvVp(veiculoVistoriaPrevia.getNrCpfDut());
                	ajusteParametros.setiTpCarroceriaVp(veiculoVistoriaPrevia.getTpCarroVeicu());
                	ajusteParametros.setiNoCnpjCrlvVp(veiculoVistoriaPrevia.getNrCnpjDut());
                	ajusteParametros.setiIdTransformadoVp(veiculoVistoriaPrevia.getIcVeicuTrafm());
                	ajusteParametros.setiNoPlacaLaudoVp(veiculoVistoriaPrevia.getCdPlacaVeicu());
                	ajusteParametros.setiNoChassiLaudoVp(veiculoVistoriaPrevia.getCdChassiVeicu());
                	ajusteParametros.setiCdModeloLaudoVp(veiculoVistoriaPrevia.getCdModelVeicu());
                	ajusteParametros.setiCdOrigemChassiVp(veiculoVistoriaPrevia.getCdOrigmChassi());
                	ajusteParametros.setiCdFabricanteLaudoVp(veiculoVistoriaPrevia.getCdFabrt());
                	ajusteParametros.setiTpCombust(veiculoVistoriaPrevia.getTpCmbst());
                	ajusteParametros.setQtLotacVeicuVp(veiculoVistoriaPrevia.getQtLotacVeicu());
                	ajusteParametros.setQtdKmRdadoVeicu(veiculoVistoriaPrevia.getQtKmRdadoVeicu());
                	ajusteParametros.setStatuDecodChasis(veiculoVistoriaPrevia.getStatuDecodChasis());
                }
                
                if(proponenteVistoriaPrevia != null){
                	ajusteParametros.setiNoCepProponenteVp(proponenteVistoriaPrevia.getNrCep());                	
                }                
            }
            
            if(ajusteParametros != null) {            	
                ajusteParametros.setCodParecerTecnicoVPList( parecerTecnicoLaudoVistoriaPreviaRepository.findByParecerTecnico(cdLvpre, ajusteParametros.getNumVersaoLaudoVistoriaPrevia()) );                
                ajusteParametros.setCodAcessoriosLaudoVPList( acessorioLaudoVistoriaPreviaRepository.findByAcessorioLaudo(cdLvpre) );
                ajusteParametros.setAvariaLaudoVPList(avariaLaudoVistoriaPreviaRepository.findByAvariaLaudo(cdLvpre));                
                ajusteParametros.setiCabineSuplementarVp(isCabineSuplementar(ajusteParametros.getCodParecerTecnicoVPList()));                
                ajusteParametros.setiChassiRemarcadoVp(isChassiRemarcado(ajusteParametros.getCodParecerTecnicoVPList()));
                ajusteParametros.setiQuartoEixoVp(isQuartoEixo(ajusteParametros.getCodParecerTecnicoVPList()));
                
                final PrestadoraVistoriaPrevia prestadoraVistoriaPrevia = prestadoraVistoriaPreviaRepository.findById(ajusteParametros.getiCdVistoriadoraVp()).orElse(null);
                
                if(prestadoraVistoriaPrevia != null && prestadoraVistoriaPrevia.getDsSite().trim().length() > 0){
                	ajusteParametros.setDescricaoLinkPrestador(prestadoraVistoriaPrevia.getDsSite());
                	ajusteParametros.setNomePrestador(prestadoraVistoriaPrevia.getNmRazaoSocal());
                }
            }            
        } catch (Exception e) {
        	LOGGER.info("context", e);
        	}
                
        return ajusteParametros;
    }
	
	private String isCabineSuplementar(final List<Long> parecerTecnicoList) {
		if (parecerTecnicoList != null && !parecerTecnicoList.isEmpty()
				&& (parecerTecnicoList.contains(TipoAjusteVistoriaPrevia.COD_CABINE_SUPLEMENTAR_REGULARIZADO.getValue())
						|| parecerTecnicoList.contains(
								TipoAjusteVistoriaPrevia.COD_CABINE_SUPLEMENTAR_NAO_REGULARIZADO.getValue()))) {
			return "SIM";
		}
		return "NAO";
	}
	
	private String isChassiRemarcado(final List<Long> parecerTecnicoList){    	
    	if(parecerTecnicoList != null && !parecerTecnicoList.isEmpty() && 
    			(parecerTecnicoList.contains(TipoAjusteVistoriaPrevia.COD_CHASSI_REMARCADO.getValue())
				|| parecerTecnicoList.contains(TipoAjusteVistoriaPrevia.COD_CHASSI_REMARCADO_NAO_REGULARIZADO.getValue()))){
    			return "SIM";
    	}
    	return "NAO";
    }
	
	private String isQuartoEixo(final List<Long> parecerTecnicoList){    	
    	if(parecerTecnicoList != null && !parecerTecnicoList.isEmpty() && (parecerTecnicoList.contains(TipoAjusteVistoriaPrevia.COD_QUARTO_EIXO_REGULARIZADO.getValue()) 
				|| parecerTecnicoList.contains(TipoAjusteVistoriaPrevia.COD_QUARTO_EIXO_NAO_REGULARIZADO.getValue()))){
    			return "SIM";
    	}
    	return "NAO";
    }
	
	public Boolean verificaDispensaLaudo(final VerificaDispensaLaudo verificaDispensaLaudo) {
        try{        	        	
        	if(verificaDispensaLaudo == null || verificaDispensaLaudo.getNrItseg() == null || verificaDispensaLaudo.getTpHisto() == null 
        			|| verificaDispensaLaudo.getTpHisto().trim().length() <= 0){
        		throw new BusinessVPException("Número do item ou tipo historico inválido!");
        		
        	}else{
        		final ItemSegurado itemSegurado = itemSeguradoService.findByItemSegurado(verificaDispensaLaudo.getNrItseg(), verificaDispensaLaudo.getTpHisto());
        		return verificaItemSegurado(itemSegurado, verificaDispensaLaudo);        		        		
        	}
        }catch (Exception vpe) {
        	LOGGER.info("context", vpe);
        }

    	return Boolean.FALSE;
    }
	
	private Boolean verificaItemSegurado(final ItemSegurado itemSegurado, final VerificaDispensaLaudo verificaDispensaLaudo){
		if(itemSegurado != null){
			final Long codigoProposta =  itemSegurado.getCodEndosso() != null ?  itemSegurado.getCodEndosso() : itemSegurado.getCodNegocio();			
			final Restricao restricao = restricaoService.buscarRestricao(codigoProposta, verificaDispensaLaudo.getNrItseg(), "VIS");
    		
    		if(restricao != null && restricao.getTpLibec() != null && restricao.getTpLibec().trim().length() > 0){        			
    			return Boolean.TRUE;
    		}
		}
		
		return Boolean.FALSE;
	}
}
