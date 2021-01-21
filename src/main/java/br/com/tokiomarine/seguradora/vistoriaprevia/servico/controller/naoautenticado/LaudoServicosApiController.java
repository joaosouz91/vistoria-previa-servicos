package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.RequestConsultaHistoricoLaudoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseAvariaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseDadosLaudoEmissaoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseHistoricoVistoriaPreviaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseLaudoVPDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseParecerAvariaChassiDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseParecerVistoriaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.ResponseVeiculoVPDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RetornoServico;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.LaudoService;

@RestController
@RequestMapping("/api/servicos/laudo")
public class LaudoServicosApiController {

	@Autowired
	private LaudoService laudoService;

	@GetMapping("/obterLaudoVistoriaPrevia/{codigoLaudo}")
	public ResponseEntity<ResponseLaudoVPDTO> obterLaudoVistoriaPrevia(@PathVariable String codigoLaudo) {
		try {
			return ResponseEntity.ok(laudoService.obterLaudoVistoriaPrevia(codigoLaudo));
		} catch (Exception e) {
			ResponseLaudoVPDTO dto = new ResponseLaudoVPDTO();
			dto.setRetorno(new RetornoServico());
			dto.getRetorno().setCodigoRetorno(99L);
			dto.getRetorno().setMensagem("Erro:" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
		}
	}

	@GetMapping("/obterVeiculoVistoriaPrevia/{codigoLaudo}")
	public ResponseEntity<ResponseVeiculoVPDTO> obterVeiculoVistoriaPrevia(@PathVariable String codigoLaudo) {
		try {
			return ResponseEntity.ok(laudoService.obterDadosVeiculo(codigoLaudo));
		} catch (Exception e) {
			ResponseVeiculoVPDTO dto = new ResponseVeiculoVPDTO();
			dto.setRetorno(new RetornoServico());
			dto.getRetorno().setCodigoRetorno(99L);
			dto.getRetorno().setMensagem("Erro:" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
		}
	}

	@GetMapping("/obterDadosLaudoEmissao/{codigoLaudo}/{numeroItem}/{tipoHistorico}")
	public ResponseEntity<ResponseDadosLaudoEmissaoDTO> obterDadosLaudoEmissao(@PathVariable String codigoLaudo,
			@PathVariable Long numeroItem, @PathVariable String tipoHistorico) {
		try {
			return ResponseEntity.ok(laudoService.obterDadosLaudoEmissao(codigoLaudo, numeroItem, tipoHistorico));

		} catch (Exception e) {
			ResponseDadosLaudoEmissaoDTO dto = new ResponseDadosLaudoEmissaoDTO();
			dto.setRetorno(new RetornoServico());
			dto.getRetorno().setCodigoRetorno(99L);
			dto.getRetorno().setMensagem("Erro:" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
		}
	}

	@GetMapping("/obterListaParecerVistoria/{codigoLaudo}")
	public ResponseEntity<ResponseParecerVistoriaDTO> obterListaParecerVistoria(@PathVariable String codigoLaudo) {
		try {
			return ResponseEntity.ok(laudoService.obterListaParecerVistoria(codigoLaudo));

		} catch (Exception e) {
			ResponseParecerVistoriaDTO dto = new ResponseParecerVistoriaDTO();
			dto.setRetorno(new RetornoServico());
			dto.getRetorno().setCodigoRetorno(99L);
			dto.getRetorno().setMensagem("Erro:" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
		}
	}

	@PostMapping("/obterListaHistoricoLaudo")
	public ResponseEntity<ResponseHistoricoVistoriaPreviaDTO> obterListaHistoricoLaudo(
			@RequestBody RequestConsultaHistoricoLaudoDTO req) {

		try {
			return ResponseEntity
					.ok(laudoService.obterListaHistoricoLaudo(req.getCodigoLaudo(), req.getCodigoSituacao()));

		} catch (Exception e) {
			ResponseHistoricoVistoriaPreviaDTO dto = new ResponseHistoricoVistoriaPreviaDTO();
			dto.setRetorno(new RetornoServico());
			dto.getRetorno().setCodigoRetorno(99L);
			dto.getRetorno().setMensagem("Erro:" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
		}
	}

	@GetMapping("/obterListaAvariaLaudo/{codigoLaudo}")
	public ResponseEntity<ResponseAvariaDTO> obterListaAvariaLaudo(@PathVariable String codigoLaudo) {

		try {
			return ResponseEntity.ok(laudoService.obterListaAvariaLaudo(codigoLaudo));

		} catch (Exception e) {
			ResponseAvariaDTO dto = new ResponseAvariaDTO();
			dto.setRetorno(new RetornoServico());
			dto.getRetorno().setCodigoRetorno(99L);
			dto.getRetorno().setMensagem("Erro:" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
		}
	}
	
	@GetMapping("/verificaParecerAvariaChassi/{codigoLaudo}")
	public ResponseEntity<ResponseParecerAvariaChassiDTO> verificaParecerAvariaChassi(@PathVariable String codigoLaudo) {

		try {
			return ResponseEntity.ok(laudoService.verificaParecerAvariaChassi(codigoLaudo));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}