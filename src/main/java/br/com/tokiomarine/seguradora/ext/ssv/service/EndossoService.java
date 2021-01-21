package br.com.tokiomarine.seguradora.ext.ssv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ext.ssv.repository.EndossoRepository;
import br.com.tokiomarine.seguradora.ssv.transacional.model.Endosso;

@Service
public class EndossoService {

	@Autowired
	private EndossoRepository endossoRepository;

	public Endosso findObterEndossoPorCodigo(Long codigoEndosso) {
		return endossoRepository.findObterEndossoPorCodigo(codigoEndosso);
	}

}
