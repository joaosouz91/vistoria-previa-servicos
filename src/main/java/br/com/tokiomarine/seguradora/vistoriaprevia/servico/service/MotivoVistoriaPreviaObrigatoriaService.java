package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.MotivoVistoriaPreviaObrigatoriaRepository;

@Component
public class MotivoVistoriaPreviaObrigatoriaService {

	@Autowired
	private MotivoVistoriaPreviaObrigatoriaRepository motivoVistoriaPreviaObrigatoriaRepository;
	
	public String buscarMotivoVistoriaPreviaCodigoEndosso(Long item, Long endosso) {
		return buscarMotivoVistoriaPrevia(item, endosso);
	}
	
	public String buscarMotivoVistoriaPreviaItemSegurado(Long item) {
		return buscarMotivoVistoriaPrevia(item, null);
	}
	
	private String buscarMotivoVistoriaPrevia(Long item, Long endosso){
		
		List<String> motivos;
		if (endosso != null) {
			motivos = motivoVistoriaPreviaObrigatoriaRepository.buscarMotivoVistoriaPreviaPorCodigoEndosso(item, endosso);
			if(CollectionUtils.isEmpty(motivos)){
				motivos = motivoVistoriaPreviaObrigatoriaRepository.buscarMotivoVistoriaPreviaTabelaAntigaPorCodigoEndosso(item, endosso);
				
			}
		} else {
			motivos = motivoVistoriaPreviaObrigatoriaRepository.buscarMotivoVistoriaPreviaPorNumeroItemSegurado(item);
			if(CollectionUtils.isEmpty(motivos)){
				motivos = motivoVistoriaPreviaObrigatoriaRepository.buscarMotivoVistoriaPreviaTabelaAntigaPorNumeroItemSegurado(item);
				
			}
		}
		
		String motivo = motivos.stream().collect(Collectors.joining(" / "));
			
		if(StringUtils.isEmpty(motivo)){
			return "";
		}
		
		return motivo;
	}
}
