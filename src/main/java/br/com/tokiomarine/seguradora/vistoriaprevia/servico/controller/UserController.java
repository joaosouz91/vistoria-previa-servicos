package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioLogado;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<UsuarioLogado> obterUsuario(){
		return ResponseEntity.ok(usuarioService.getUsuario());
	}
	
	@GetMapping("/username")
	public String principal(Principal principal) {
		return principal.getName();
	}
}