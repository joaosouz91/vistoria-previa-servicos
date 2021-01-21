package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.fila.VinculoLaudoProducer;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LaudoVistoriaPreviaServicoRepository;

@Component
public class ServicoService {

	@Autowired
	private LaudoVistoriaPreviaServicoRepository laudoVistoriaPreviaServicoRepository;
	
	@Autowired
	private VinculoLaudoProducer vinculoLaudoProducer;
	
	private Logger logger = LogManager.getLogger(ServicoService.class);
	
//	public Boolean verificaSeExisteVistoriaNegocioOuEndosso(final VitoriaPreviaServicoDto vitoriaPreviaServicoDto){				
//		try {
//			final String numeroPlaca = vitoriaPreviaServicoDto.getNumeroPlaca();
//			final String codigoChassi = vitoriaPreviaServicoDto.getCodigoChassi();
//			final Long codigoCorretor = vitoriaPreviaServicoDto.getCodigoCorretor();
//			
//			final LaudoVistoriaPrevia laudoVistoriaPreviaNegocio = laudoVistoriaPreviaServicoRepository.findLaudoVistoriaPreviaNegocioNative(numeroPlaca, codigoChassi, codigoCorretor);
//			if(laudoVistoriaPreviaNegocio != null){
//				vinculoLaudoProducer.sendVinculado(laudoVistoriaPreviaNegocio.getCdLvpre());
//				return Boolean.TRUE;
//			}
//			
//			final LaudoVistoriaPrevia laudoVistoriaPreviaEndosso = laudoVistoriaPreviaServicoRepository.findLaudoVistoriaPreviaEndossoNative(numeroPlaca, codigoChassi, codigoCorretor);
//			if(laudoVistoriaPreviaEndosso != null){
//				vinculoLaudoProducer.sendVinculado(laudoVistoriaPreviaEndosso.getCdLvpre());
//				return Boolean.TRUE;
//			}		
//		} catch (Exception e) {
//			logger.error("Erro: verificaSeExisteVistoriaNegocioOuEndosso " + e.getStackTrace());
//		}
//		
//		return Boolean.FALSE;
//	}
//	
}
