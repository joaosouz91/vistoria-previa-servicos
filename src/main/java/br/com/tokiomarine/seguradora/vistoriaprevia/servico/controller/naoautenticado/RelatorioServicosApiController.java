package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.relatorio.GeraLoteVPImprodutivaService;

@RestController
@RequestMapping("/api/servicos/relatorio")
public class RelatorioServicosApiController {

	@Autowired
	private GeraLoteVPImprodutivaService geraLoteVPImprodutivaService;

	@GetMapping("/vistoria/improdutiva")
	public ResponseEntity<String> gerarLote() {
		try {
			geraLoteVPImprodutivaService.gerarLote();

			return ResponseEntity.ok("0");
		} catch (Exception e) {
			return ResponseEntity.ok("1");
		}
	}
}