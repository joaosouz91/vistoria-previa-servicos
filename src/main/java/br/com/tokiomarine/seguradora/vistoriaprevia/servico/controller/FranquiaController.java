package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URISyntaxException;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.EmpresaVistoriadora;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.FranquiaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BadRequestException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.FranquiaRespository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.FranquiaService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.hateoas.ResourceUtil;

@RestController
@RequestMapping("/v1/franquia")
//@Api(value = "EmpresaVistoriadora", description = "Métodos Empresa Vistoriadora (Franquias)")
public class FranquiaController extends ResourceUtil<EmpresaVistoriadora, FranquiaDTO>{
	
	@Autowired
	private FranquiaService franquiaService;
	
	@Autowired
	private FranquiaRespository franquiaRepository;
	
	@CrossOrigin 
	@PostMapping("/pesquisa")
	//@ApiOperation(value = "Pesquisa por franquia")
	public ResponseEntity<Page<FranquiaDTO>> pesquisar(@RequestBody FranquiaDTO dto, Pageable page) {
		EmpresaVistoriadora filtro = toEntity(dto).orElseThrow(BadRequestException::new);
		Page<EmpresaVistoriadora> franquiasPage = franquiaService.listaFranquia(filtro, page);
		return ok(franquiasPage);
	}
	
	@CrossOrigin 
	@GetMapping("/{codigoFranquia}")
//	@ApiOperation(value = "Obtem uma franquia")
	public ResponseEntity<FranquiaDTO> buscarFranquia(@PathVariable String codigoFranquia) {
		EmpresaVistoriadora franquia = franquiaRepository.findByCdEmpreVstra(codigoFranquia);
		return ok(franquia);
	}
	
	@CrossOrigin
	@PostMapping
//	@ApiOperation(value = "Salva uma Franquia")
	public ResponseEntity<FranquiaDTO> salvar(@Validated  @RequestBody  FranquiaDTO dto) throws URISyntaxException {
		EmpresaVistoriadora franquia = toEntity(dto).orElseThrow(BadRequestException::new);
		franquia = franquiaService.salvar(franquia);
		return created(franquia);
	}
	
	@CrossOrigin
	@PutMapping("/{codigoFranquia}")
//	@ApiOperation(value = "Atualiza uma franquia")
	public ResponseEntity<FranquiaDTO> atualizar(@PathVariable String codigoFranquia, @RequestBody FranquiaDTO dto) throws URISyntaxException {
		EmpresaVistoriadora franquia = toEntity(dto).orElseThrow(BadRequestException::new);
		franquia = franquiaService.atualizar(codigoFranquia, franquia);
		return ok(franquia);
	}
	
	@Override
	public Link linkTo(FranquiaDTO dto) {
		return ControllerLinkBuilder
				.linkTo(methodOn(FranquiaController.class).buscarFranquia(dto.getCdEmpreVstra()))
				.withSelfRel();
	}
}