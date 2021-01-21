package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VeiculoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VeiculoVistoriaPreviaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BadRequestException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.VeiculoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.VeiculoVistoriaPreviaService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.hateoas.ResourceUtil;

@RestController
@RequestMapping("/v1/laudo-vistoria-veiculo")
//@Api(value = "Veículo Vistoria Prévia", description = "Métodos Veículo Vitoria Préviaa (Veículo)")
public class VeiculoVistoriaPreviaController  extends ResourceUtil<VeiculoVistoriaPrevia, VeiculoVistoriaPreviaDTO> {

	@Autowired
	private VeiculoVistoriaPreviaRepository veiculoRepository;
	
	@Autowired
	private VeiculoVistoriaPreviaService veiculoVistoriaPreviaservice;
	
	@CrossOrigin 
	@PostMapping("/pesquisa")
//	@ApiOperation(value = "Pesquisa por Veiculo")
	public ResponseEntity<Page<VeiculoVistoriaPreviaDTO>> pesquisar(@RequestBody VeiculoVistoriaPreviaDTO dto, Pageable page) {
		VeiculoVistoriaPrevia filtro = toEntity(dto).orElseThrow(BadRequestException::new);
		Page<VeiculoVistoriaPrevia> veiculosPage = veiculoVistoriaPreviaservice.listaVeiculos(filtro, page);
		return ok(veiculosPage);
	}

	@CrossOrigin 
	@GetMapping("/{codigoVeiculo}")
	public ResponseEntity<VeiculoVistoriaPreviaDTO> obterVeiculoPorCodigo(@PathVariable String codigoVeiculo){
		VeiculoVistoriaPrevia veiculo = veiculoRepository.findLaudoBycdLvpre(codigoVeiculo) ;
		return ok(veiculo) ;
	}
	
	@Override
	public Link linkTo(VeiculoVistoriaPreviaDTO dto) {
		return ControllerLinkBuilder
				.linkTo(methodOn(VeiculoVistoriaPreviaController.class).obterVeiculoPorCodigo(dto.getCdLvpre()))
				.withSelfRel(); 
	}
}