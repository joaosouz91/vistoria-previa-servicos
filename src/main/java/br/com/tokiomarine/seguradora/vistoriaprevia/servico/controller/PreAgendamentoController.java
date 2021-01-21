package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RetornoServico;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.NegocioDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.PreAgendamentoService;

@RestController
@RequestMapping("/api/pre-agendamentos")
public class PreAgendamentoController {

	@Autowired
	PreAgendamentoService preAgendamentoService;
	
	@PostMapping
	public ResponseEntity<String> gerarPreAgendamento(@RequestBody NegocioDTO negocio){
		try {
			long idVpObrigatoria = preAgendamentoService.gravarPreAgendamento(negocio);
			
			return ResponseEntity.ok(preAgendamentoService.getURLAgendamento(negocio, idVpObrigatoria));
		}  catch (Exception e) {
			throw new InternalServerException(e);
		}
	}
	
	@GetMapping(value = "/restricao/item/{numeroItemSegurado}/endosso/{codigoEndosso}/corretor/{codigoCorretor}")
	public ResponseEntity<RetornoServico> gerarPreAgendamentoRestricao(@PathVariable Long numeroItemSegurado, @PathVariable Long codigoEndosso, @PathVariable Long codigoCorretor){
		Long idVpObrigatoria = preAgendamentoService.gerarPreAgendamentoRestricao(numeroItemSegurado, codigoEndosso, codigoCorretor);
		if (idVpObrigatoria != null) {
			RetornoServico retorno = new RetornoServico();
			retorno.setCodigoRetorno(0L);
			retorno.setMensagem("Pre-Agendamento Gerado com sucesso, ID:" + idVpObrigatoria);
			return ResponseEntity.ok(retorno);

		} else {
			RetornoServico retorno = new RetornoServico();
			retorno.setCodigoRetorno(99L);
			retorno.setMensagem("Erro ao gerar Pre-Agendamento ");
			return new ResponseEntity<RetornoServico>(retorno,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping(value = "/vistoria/{idVpObrigatoria}/voucher", produces = "application/json")
	public ResponseEntity<String> limpaVoucherPreAgendamento(@PathVariable Long idVpObrigatoria){
		try {
			return ResponseEntity.ok(preAgendamentoService.limpaVoucherPreAgendamento(idVpObrigatoria));
		}  catch (Exception e) {
			throw new InternalServerException(e);
		}
	}
	
	@DeleteMapping(value = "/vistoria/{idVpObrigatoria}/voucher", produces = "application/json")
	public ResponseEntity<String> deletaVoucherPreAgendamento(@PathVariable Long idVpObrigatoria){
		try {
			return ResponseEntity.ok(preAgendamentoService.deletaVoucherPreAgendamento(idVpObrigatoria));
		}  catch (Exception e) {
			throw new InternalServerException(e);
		}
	}
}
