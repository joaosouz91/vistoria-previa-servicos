package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.Municipio;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NoContentException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.MunicipioRepository;

@Service
public class MunicipioService {

	@Autowired
	private MunicipioRepository municipioRepository;

	public List<Municipio> findMunicipiosByUf(String uf){
		List<Municipio> municipios = municipioRepository.findByUf(uf);
		return municipios;
	}

	public Municipio findMunicipiosByCodigo(Long codMunicipio){
		return municipioRepository.findFirstByCodMunicipio(codMunicipio).orElseThrow(() -> new NoContentException());
	}
}
