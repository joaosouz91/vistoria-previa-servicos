package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.FinalidadeVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.FinalidadeVistoriaPreviaRepository;

@Component
public class FinalidadeVistoriaPreviaService {

	@Autowired
	private FinalidadeVistoriaPreviaRepository finalidadeVistoriaPreviaRepository;
	
	public FinalidadeVistoriaPrevia buscarFinalidadeVistoriaPrevia(final Long cdFnaldVspre){
		return finalidadeVistoriaPreviaRepository.findByFinalidadeVistoriaPrevia(cdFnaldVspre);
	}
	
	public List<FinalidadeVistoriaPrevia> findAllFinalidadeVistoriaPrevia(){
		return finalidadeVistoriaPreviaRepository.findAllFinalidadeVistoriaPrevia();
	}
}
