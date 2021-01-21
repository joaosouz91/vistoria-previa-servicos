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
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Proponente;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Response;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.ProponenteVistoriaPreviaService;

@RestController
@RequestMapping("${api.path}/proponentes")
@ResponseStatus(HttpStatus.OK)
public class ProponenteVistoriaPreviaController {

	@Autowired
	private ProponenteVistoriaPreviaService proponenteVistoriaPreviaService;
	
	@CrossOrigin
	@PostMapping
	public Response<Proponente> buscarProponente(@RequestBody String  cdLvpre) {
						
		final Proponente proponente  = proponenteVistoriaPreviaService.buscarProponenteVistoria(cdLvpre);
		
		return new Response<>(proponente);
	}
	
	@CrossOrigin
	@GetMapping("/estado-civil")
	public Response<List<ConteudoColunaTipo>> listaEstadoCivil() {
						
		final List<ConteudoColunaTipo> conteudoColunaTipos  = proponenteVistoriaPreviaService.listaEstadoCivil();
		
		return new Response<>(conteudoColunaTipos);
	}
	
	@CrossOrigin
	@GetMapping("/tipo-condutores")
	public Response<List<ConteudoColunaTipo>> listaTipoDeCondutor() {
						
		final List<ConteudoColunaTipo> conteudoColunaTipos  = proponenteVistoriaPreviaService.listaTipoDeCondutor();
		
		return new Response<>(conteudoColunaTipos);
	}	
}