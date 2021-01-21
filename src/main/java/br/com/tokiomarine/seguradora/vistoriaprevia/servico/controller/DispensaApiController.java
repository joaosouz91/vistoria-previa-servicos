package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ListaDeDispensa;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Response;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.DispensaService;

@RestController
@RequestMapping("/api/motivo-dispensas")
@ResponseStatus(HttpStatus.OK)
public class DispensaApiController {

	@Autowired
	private DispensaService conteudoColunaTipoBusiness;
	
	@CrossOrigin
	@GetMapping
	public Response<List<ConteudoColunaTipo>> getConteudoColunaTipos() {		
				
		final List<ConteudoColunaTipo> conteudoColunaTipos = conteudoColunaTipoBusiness.motivoDispensas();
		
		return new Response<>(conteudoColunaTipos);
	}
			
	@CrossOrigin
	@PostMapping
	public Response<Boolean> salvarMotivoDispensaLista(@RequestBody ListaDeDispensa listaDeDispensa) {
										
		final Boolean resultado = conteudoColunaTipoBusiness.salvarMotivoDispensaLista(listaDeDispensa);
				
		return new Response<>(resultado);
	}
}
