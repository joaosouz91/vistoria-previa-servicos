package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Dispensa;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.DispensaService;

@RestController
@RequestMapping("${api.path}/motivo-dispensas")
@ResponseStatus(HttpStatus.OK)
public class DispensaController {

	@Autowired
	private UsuarioService usuarioLogado;
	
	@Autowired
	private DispensaService conteudoColunaTipoBusiness;
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<ConteudoColunaTipo>> getConteudoColunaTipos() {		
				
		final List<ConteudoColunaTipo> conteudoColunaTipos = conteudoColunaTipoBusiness.motivoDispensas();
		
		return  ResponseEntity.ok(conteudoColunaTipos);
	}
	
	@CrossOrigin
	@PostMapping
	public ResponseEntity<Boolean> salvarMotivoDispensa(@RequestBody Dispensa dispensa) {
						
		final String idUsuarioLogado = usuarioLogado.getUsuarioId();
		dispensa.setIdUsuarioLogado(idUsuarioLogado);
				
		final Boolean resultado = conteudoColunaTipoBusiness.salvarMotivoDispensa(dispensa);
				
		return  ResponseEntity.ok(resultado);
	}		
}