package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.NegocioComponentDto;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Reclassificacao;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ReclassificacaoAlterarStatus;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.ReclassificacaoService;

@RestController
@RequestMapping("${api.path}/negocio-reclassificacoes")
@ResponseStatus(HttpStatus.OK)
public class ReclassificacaoController {

	@Autowired
	private ReclassificacaoService reclassificacaoBusiness;
		
	@CrossOrigin
	@PostMapping
	public ResponseEntity<Reclassificacao> getReclassificacao(@Valid @RequestBody NegocioComponentDto negocioComponentDto) {
				
		final Reclassificacao reclassificacao = reclassificacaoBusiness.getReclassificacao(negocioComponentDto);
				
		return ResponseEntity.ok(reclassificacao);
	}
		
	@CrossOrigin
	@PostMapping("/salvar")
	public ResponseEntity<Boolean> salvarNovoStatusReclassificacao(@RequestBody ReclassificacaoAlterarStatus reclassificacaoAlterarStatus) {
		
		final Boolean resultado = reclassificacaoBusiness.salvarNovoStatusReclassificacao(reclassificacaoAlterarStatus);
				
		return ResponseEntity.ok(resultado);
	}
}
