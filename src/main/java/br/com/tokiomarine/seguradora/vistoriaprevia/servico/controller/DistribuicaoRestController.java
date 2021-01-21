package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.DistribuicaoMunicipioDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ParametroPercentualDistribuicaoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.ParametroPercentualDistribuicaoService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.RegiaoAtendimentoVistoriadorService;

@RestController
@RequestMapping(value = "/api/distribuicao")
public class DistribuicaoRestController {

	@Autowired
	private ParametroPercentualDistribuicaoService percentualDistribuicaoService;
	
	@Autowired
	private RegiaoAtendimentoVistoriadorService regiaoAtendimentoVistoriadorService;

	@PostMapping("/ufs")
	public ResponseEntity<Set<DistribuicaoMunicipioDTO>> salvarParamDistribuicaoUfs(
			@RequestBody Set<DistribuicaoMunicipioDTO> distribuicao) {
		percentualDistribuicaoService.redistribuirPercentuais(distribuicao);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/uf")
	public ResponseEntity<DistribuicaoMunicipioDTO> salvarParamDistribuicao(
			@Valid @RequestBody DistribuicaoMunicipioDTO distribuicao) {
		percentualDistribuicaoService.redistribuirPercentuais(SetUtils.hashSet(distribuicao));
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/uf")
	public ResponseEntity<List<DistribuicaoMunicipioDTO>> obterParamDistribuicao() {
		return ResponseEntity.ok(percentualDistribuicaoService.obterParamDistribuicaoUf());
	}

	@GetMapping
	public ResponseEntity<Page<ParametroPercentualDistribuicaoDTO>> findAll(@RequestParam Map<String, Object> filtro, Pageable pageable) {
		return ResponseEntity.ok(percentualDistribuicaoService.findAll(filtro, pageable));
	}

	@GetMapping("/{idRegiao}")
	public ResponseEntity<List<ParametroPercentualDistribuicaoDTO>> findAllByIdRegiao(@PathVariable Long idRegiao) {
		return ResponseEntity.ok(regiaoAtendimentoVistoriadorService.findVigenteByIdRegiao(idRegiao));
	}

	@PostMapping("/{idRegiao}")
	public ResponseEntity<Object> updatePercentualDistribuicao(@PathVariable Long idRegiao,
			@RequestBody List<ParametroPercentualDistribuicaoDTO> distribuicao) {
		percentualDistribuicaoService.salvarNovaDistribuicao(idRegiao, distribuicao);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
