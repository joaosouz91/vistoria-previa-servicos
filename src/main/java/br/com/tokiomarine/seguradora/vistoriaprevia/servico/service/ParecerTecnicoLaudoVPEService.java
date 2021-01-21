package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParecerTecnicoLaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParecerTecnicoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ParecerTecnicoLaudoVistoriaPreviaRepository;



@Service
public class ParecerTecnicoLaudoVPEService {
	
	
	@Autowired
	ParecerTecnicoLaudoVistoriaPreviaRepository repository;
	
	
	public List<Long> findByParecerTecnico(String cdLvpre,Long nrVrsaoLvpre){
		return repository.findByParecerTecnico(cdLvpre, nrVrsaoLvpre);
	}
	
	
	@Transactional
	public void salvarSelecionados(List<ParecerTecnicoVistoriaPrevia> lista, LaudoVistoriaPrevia laudo ) {
		
		repository.deleteParecerPorLaudo(laudo.getCdLvpre());
		List<ParecerTecnicoLaudoVistoriaPrevia> retorno = this.convert(lista, laudo);
		repository.saveAll(retorno);
	}
	
	
	public List<ParecerTecnicoLaudoVistoriaPrevia> convert(List<ParecerTecnicoVistoriaPrevia> lista, LaudoVistoriaPrevia laudo ){
		
		List<ParecerTecnicoLaudoVistoriaPrevia> retorno = new  ArrayList<>();
		
		for(ParecerTecnicoVistoriaPrevia obj : lista) {
			ParecerTecnicoLaudoVistoriaPrevia ob =  new ParecerTecnicoLaudoVistoriaPrevia();
			ob.setCdLvpre(laudo.getCdLvpre());
			ob.setCdPatecVspre(obj.getCdPatecVspre());
			ob.setNrVrsaoLvpre(laudo.getNrVrsaoLvpre());
			retorno.add(ob);
		}
		
		return retorno;
	}
	
	public boolean verificarLaudoComParecerTecnico(String cdLvpre, Collection<Long> cdPatecVspre) {
		return repository.existsParecerTecnicoLaudoVistoriaPreviaByCdLvpreAndCdPatecVspreIn(cdLvpre, cdPatecVspre);
	}

}
