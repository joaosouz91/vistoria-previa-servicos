package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PostoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.pk.PostoVistoriaPreviaPK;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.Update;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PostoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BadRequestException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NotFoundException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.PostoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.PostoVistoriaPreviaService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.hateoas.ResourceUtil;

@RestController
@RequestMapping("/api")
public class PostoVistoriaPreviaController extends ResourceUtil<PostoVistoriaPrevia, PostoDTO> {

	@Autowired
	private PostoVistoriaPreviaRepository postoRepository;

	@Autowired
	private PostoVistoriaPreviaService postoService;

	@CrossOrigin
	@PutMapping("/prestadora/{codigoPrestadora}/postos/{codigoPosto}")
	public ResponseEntity<PostoDTO> alterar(@PathVariable Long codigoPosto, @PathVariable Long codigoPrestadora, @Validated(Update.class) @RequestBody PostoDTO dto) {
		PostoVistoriaPrevia posto = toEntity(dto).orElseThrow(BadRequestException::new);
		PostoVistoriaPreviaPK pk = new PostoVistoriaPreviaPK(codigoPrestadora, codigoPosto);
		posto = postoService.atualizar(pk, posto);
		return ok(posto);
	}

	@CrossOrigin
	@PostMapping("/postos/pesquisa")
	public ResponseEntity<Page<PostoDTO>> listaPostos(@RequestBody PostoDTO filtro, Pageable page) {
		return ResponseEntity.ok(postoService.obterPostos(filtro, page));
	}

	@GetMapping("/prestadora/{codigoPrestadora}/postos/{codigoPosto}")
	public ResponseEntity<PostoDTO> buscarPosto(@PathVariable Long codigoPosto, @PathVariable Long codigoPrestadora) {
		PostoVistoriaPreviaPK pk = new PostoVistoriaPreviaPK(codigoPrestadora, codigoPosto);
		PostoVistoriaPrevia posto = postoRepository.findById(pk).orElseThrow(NotFoundException::new);
		return ok(posto);
	}
	
	@GetMapping("/prestadora/{codigoPrestadora}/postos/{uf}/cidades")
	public ResponseEntity<List<String>> listaCidadesPorPrestadoraEstado(@PathVariable Long codigoPrestadora, @PathVariable String uf) {
		return ResponseEntity.ok(postoService.obterCidadesPorPrestadoraEstado(codigoPrestadora, uf));
	}
	
	@GetMapping("/postos/{uf}/cidades")
	public ResponseEntity<List<String>> listaCidadesPorEstado(
			@PathVariable String uf, 
			@RequestParam(name = "isCaminhao", required = false) boolean isCaminhao, 
			@RequestParam(name = "isAtivo", required = false, defaultValue = "true") boolean isAtivo) {
		return ResponseEntity.ok(postoService.obterCidadesPorEstado(uf, isCaminhao, isAtivo));
	}

	@GetMapping("/postos/{cdMunic}/bairros")
	public ResponseEntity<List<String>> listaBairrosPorCidade(@PathVariable Long cdMunic,
			@RequestParam(name = "isCaminhao", required = false) boolean isCaminhao,
			@RequestParam(name = "isAtivo", required = false, defaultValue = "true") boolean isAtivo) {
		return ResponseEntity.ok(postoService.recuperarListaBairroPorCidade(cdMunic, isCaminhao, isAtivo));
	}
	
	@GetMapping("/postos/{uf}/{cidade}/bairros")
	public ResponseEntity<List<String>> listaBairrosPorCidade(@PathVariable String uf,
			@PathVariable String cidade, @RequestParam(name = "isCaminhao", required = false) boolean isCaminhao,
			@RequestParam(name = "isAtivo", required = false, defaultValue = "true") boolean isAtivo) {
		return ResponseEntity.ok(postoService.recuperarListaBairroPorCidade(uf, cidade, isCaminhao, isAtivo));
	}

	@GetMapping("/vistoria/{idVistoria}/postos/{cdMunic}")
	public ResponseEntity<List<PostoDTO>> listaPostosPorLocalizacao(@PathVariable Long idVistoria, @PathVariable Long cdMunic,
			@RequestParam(name = "bairro", required = false) String bairro,
			@RequestParam(name = "isCaminhao", required = false) boolean isCaminhao,
			@RequestParam(name = "isAtivo", required = false, defaultValue = "true") boolean isAtivo) {

		return ResponseEntity.ok(postoService.obterPostos(idVistoria, cdMunic, bairro, isCaminhao, isAtivo));
	}

	public Link linkTo(PostoDTO dto) {
		return ControllerLinkBuilder.linkTo(
				methodOn(PostoVistoriaPreviaController.class).buscarPosto(dto.getCdPostoVspre(), dto.getCdAgrmtVspre()))
				.withSelfRel();
	}

}
