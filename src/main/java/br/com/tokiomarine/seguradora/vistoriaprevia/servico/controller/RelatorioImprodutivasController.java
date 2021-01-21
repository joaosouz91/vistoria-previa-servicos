package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.SuperintendenciaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ValorDescricaoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.RelatorioImprodutividadeCorretorDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.RelatorioImprodutividadeDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.RelatorioImprodutivoFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.Sucursal;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.VisaoRelatorioImprodutivoEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.VistoriaBindException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.LoteLaudoImprodutivoService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.relatorio.RelatorioVistoriaImprodutivaService;

@RestController
@RequestMapping("/api/relatorio/vistoria/improdutiva")
public class RelatorioImprodutivasController {

	@Autowired
	private RelatorioVistoriaImprodutivaService relatorioVistoriaImprodutivaService;

	@Autowired
	private LoteLaudoImprodutivoService loteLaudoImprodutivoService;

	@PostMapping("/pesquisa")
	public ResponseEntity<?> obterLotesVistoriasImprodutivas(@RequestBody RelatorioImprodutivoFiltro filtro)
			throws VistoriaBindException {
		return ResponseEntity.ok(relatorioVistoriaImprodutivaService.obterLotesVistoriasImprodutivas(filtro));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obterLotesVistoriasImprodutivas(@PathVariable Long id) {
		return ResponseEntity.ok(relatorioVistoriaImprodutivaService.obterLoteVistoriasImprodutivas(id));
	}

	@PostMapping("/laudos/adicionais")
	public ResponseEntity<?> obterLaudosAdicionais(@RequestBody RelatorioImprodutivoFiltro filtro)
			throws VistoriaBindException {
		return ResponseEntity.ok(relatorioVistoriaImprodutivaService.obterLaudosAdicionais(filtro));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> salvarDetalheLote(@PathVariable Long id,
			@RequestBody RelatorioImprodutividadeCorretorDTO req) throws VistoriaBindException {
		relatorioVistoriaImprodutivaService.salvarDetalheLote(id, req);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<?> salvarLotes(@RequestBody RelatorioImprodutividadeDTO req) {
		relatorioVistoriaImprodutivaService.salvarLotes(req);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/transmissao")
	public ResponseEntity<?> transmitirLotes(@RequestBody RelatorioImprodutividadeDTO req) {
		relatorioVistoriaImprodutivaService.transmitirLotes(req);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/visoes")
	public ResponseEntity<VisaoRelatorioImprodutivoEnum[]> obterVisoes() {
		return ResponseEntity.ok(VisaoRelatorioImprodutivoEnum.values());
	}

	@GetMapping("/superintendencias")
	public ResponseEntity<List<SuperintendenciaDTO>> obterSuperintendencias() {
		return ResponseEntity.ok(relatorioVistoriaImprodutivaService.obterSuperintendencias());
	}

	@GetMapping("/sucursais")
	public ResponseEntity<List<Sucursal>> obterSucursais() {
		return ResponseEntity.ok(relatorioVistoriaImprodutivaService.obterSucursais());
	}

	@GetMapping("/datas")
	public ResponseEntity<List<ValorDescricaoDTO>> obterListaReferenciaLotesLaudosImprodutivos() {
		return ResponseEntity.ok(loteLaudoImprodutivoService.obterListaReferenciaLotesLaudosImprodutivos());
	}
}