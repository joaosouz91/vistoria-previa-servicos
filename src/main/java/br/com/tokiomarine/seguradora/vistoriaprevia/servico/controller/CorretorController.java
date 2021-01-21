package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Corretor;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.CorretorService;

@RestController
@RequestMapping("/v1/corretores")
@ResponseStatus(HttpStatus.OK)
public class CorretorController {

	@Autowired
	private CorretorService corretorService;
	
	@CrossOrigin
	@PostMapping("/pesquisa")
	public ResponseEntity<List<Corretor>> buscarListaCorretores(@RequestBody Corretor corretor) {
		
		final List<Corretor> corretores  = corretorService.buscarListaCorretores(corretor);
		
		return ResponseEntity.ok(corretores);
	}
	
	@GetMapping("/pesquisa/{tipo}/{valor}")
	public ResponseEntity<List<Corretor>> buscarListaCorretores(@PathVariable String tipo, @PathVariable String valor) {
		
		final List<Corretor> corretores  = corretorService.buscarListaCorretores(tipo, valor);
		
		return ResponseEntity.ok(corretores);
	}
}
