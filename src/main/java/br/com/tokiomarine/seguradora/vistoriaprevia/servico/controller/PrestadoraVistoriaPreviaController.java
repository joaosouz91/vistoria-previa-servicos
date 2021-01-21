package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PrestadoraVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.Create;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.Update;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PrestadoraDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BadRequestException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NotFoundException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.PrestadoraVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.PrestadoraVistoriaPreviaService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.hateoas.ResourceUtil;

@RestController
@RequestMapping("/v1/prestadora-vistorias")
public class PrestadoraVistoriaPreviaController extends ResourceUtil<PrestadoraVistoriaPrevia, PrestadoraDTO> {

	@Autowired
	private PrestadoraVistoriaPreviaRepository prestadoraRepository;

	@Autowired
	private PrestadoraVistoriaPreviaService prestadoraService;

	
	@CrossOrigin
	@PostMapping("/pesquisa")
	public ResponseEntity<Page<PrestadoraDTO>> pesquisar(@RequestBody PrestadoraDTO dto, Pageable page) {
		PrestadoraVistoriaPrevia filtro = toEntity(dto).orElseThrow(BadRequestException::new);
		Page<PrestadoraVistoriaPrevia> prestadorasPage = prestadoraService.listaPrestadoraVistoriaPrevia(filtro, page);
		return ok(prestadorasPage);
	}
	
	@CrossOrigin
	@GetMapping("/{codigoPrestadora}")
	public ResponseEntity<PrestadoraDTO> buscarPrestadora(@PathVariable Long codigoPrestadora) {
		PrestadoraVistoriaPrevia prestadora = prestadoraRepository.findById(codigoPrestadora).orElseThrow(NotFoundException::new);
		return ok(prestadora);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<PrestadoraDTO> salvar(@RequestBody @Validated(Create.class) PrestadoraDTO dto) throws URISyntaxException {
		PrestadoraVistoriaPrevia prestadora = toEntity(dto).orElseThrow(BadRequestException::new);
		prestadora = prestadoraService.salvar(prestadora);
		return created(prestadora);
	}

	@CrossOrigin
	@PutMapping("/{codigoPrestadora}")
	public ResponseEntity<PrestadoraDTO> atualizar(@PathVariable Long codigoPrestadora, @RequestBody @Validated(Update.class) PrestadoraDTO dto) {
		PrestadoraVistoriaPrevia prestadora = toEntity(dto).orElseThrow(BadRequestException::new);
		prestadora = prestadoraService.atualizar(codigoPrestadora, prestadora);
		return ok(prestadora);
	}

	@CrossOrigin
	@GetMapping("/todos")
	public List<PrestadoraVistoriaPrevia> obterTodas(@RequestParam(value = "ativo", required = false) Boolean ativo) {
		List<PrestadoraVistoriaPrevia> prestadoras = prestadoraService.obterTodas(ativo);
		if(prestadoras.isEmpty()) return null;
		return prestadoras;
	}

	public Link linkTo(PrestadoraDTO dto) {
		return ControllerLinkBuilder
				.linkTo(methodOn(PrestadoraVistoriaPreviaController.class).buscarPrestadora(dto.getCdAgrmtVspre()))
				.withSelfRel();
	}

}
