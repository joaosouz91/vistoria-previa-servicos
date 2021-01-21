package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.ext.ssv.service.ConteudoColunaTipoService;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.FinalidadeVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.FinalidadeVistoriaPreviaService;

@RestController
@RequestMapping("${api.path}/laudo-vistorias")
@ResponseStatus(HttpStatus.OK)
public class LaudoController {
	
	@Autowired
	private ConteudoColunaTipoService conteudoColunaTipoService;
	
	@Autowired
	private FinalidadeVistoriaPreviaService finalidadeVistoriaPreviaService;
	
	@CrossOrigin
	@GetMapping("/tipo-local-vistoria")
	public ResponseEntity<List<ConteudoColunaTipo>> listaTipoLocalVistoria() {
						
		final List<ConteudoColunaTipo> conteudoColunaTipos = conteudoColunaTipoService.listaTipoLocalVistoria();
		
		return  ResponseEntity.ok(conteudoColunaTipos);
	}
	
	
	@CrossOrigin
	@GetMapping("/finalidade-vistorias-all")
	public ResponseEntity<List<FinalidadeVistoriaPrevia>> listaFinalidadeVistoriaPreviaService() {
						
		final List<FinalidadeVistoriaPrevia> finalidadeVistoriaPrevias = finalidadeVistoriaPreviaService.findAllFinalidadeVistoriaPrevia();
		
		return  ResponseEntity.ok(finalidadeVistoriaPrevias);
	}
}