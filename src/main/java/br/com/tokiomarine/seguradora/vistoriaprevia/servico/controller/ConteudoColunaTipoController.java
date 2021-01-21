package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.ext.ssv.service.ConteudoColunaTipoService;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ConteudoColunaTipoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVistoria;

@RestController
@RequestMapping("${api.path}/utils")
@ResponseStatus(HttpStatus.OK)
public class ConteudoColunaTipoController {

	@Autowired
	private ConteudoColunaTipoService conteudoColunaTipoService;
	
	@CrossOrigin
	@GetMapping("/tipo-combustiveis")
	public ResponseEntity<List<ConteudoColunaTipo>> editarLaudos() {
									
		final List<ConteudoColunaTipo> conteudoColunaTipos = conteudoColunaTipoService.listaTipoCombustivel();
				
		return ResponseEntity.ok(conteudoColunaTipos);
	}
	
	@GetMapping("/motivos/cancelamento/{tipoVistoria}")
	public ResponseEntity<List<ConteudoColunaTipoDTO>> obterMotivosCancelamento(@PathVariable TipoVistoria tipoVistoria) {
		List<ConteudoColunaTipoDTO> motivos = conteudoColunaTipoService.listarMotivosCancelamentosPorTipoVistoria(tipoVistoria);
		return ResponseEntity.ok(motivos);
	}
}