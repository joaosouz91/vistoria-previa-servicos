package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.Municipio;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.MunicipioService;

@RestController
@RequestMapping(value ="/api/municipio")
public class MunicipioController {

	@Autowired
	private MunicipioService municipioService;
	
	@GetMapping("/uf/{uf}")
	public ResponseEntity<List<Municipio>> findMunicipiosByUf(@PathVariable String uf){
		return ResponseEntity.ok(municipioService.findMunicipiosByUf(uf));
	}
	
	@GetMapping("/codigo/{codMunicipio}")
	public ResponseEntity<Municipio> findMunicipiosByUf(@PathVariable Long codMunicipio){
		return ResponseEntity.ok(municipioService.findMunicipiosByCodigo(codMunicipio));
	}
	
}
