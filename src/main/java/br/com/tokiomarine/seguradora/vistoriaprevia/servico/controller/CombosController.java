package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ParecerTecnicoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.GenericList;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.CombosServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/combos")
public class CombosController {

	@Autowired
	private CombosServices combosServices;
	
	@Autowired
	private ModelMapper mapper;

	@CrossOrigin
	@GetMapping("/estado-civil")
	public ResponseEntity<List<GenericList>> listaEstadoCivil() {
		return  ResponseEntity.ok(combosServices.estadoCivilList());
	}
	
	@CrossOrigin
	@GetMapping("/tipo-seguro")
	public ResponseEntity<List<GenericList>> listaTipoSeguro() {
		return  ResponseEntity.ok(combosServices.tipoSeguroList());
	}
	
	@CrossOrigin
	@GetMapping("/tipo-combustivel")
	public ResponseEntity<List<GenericList>> listaTipoCombustivel() {
		return  ResponseEntity.ok(combosServices.tipoCombustivelList());
	}
	
	@CrossOrigin
	@GetMapping("/tipo-status-laudo")
	public ResponseEntity<List<GenericList>> listaTipoStatusLaudo() {
		return  ResponseEntity.ok(combosServices.tipoStatusLaudoList());
	}
	
	@CrossOrigin
	@GetMapping("/tipo-condutor")
	public ResponseEntity<List<GenericList>> listaTipoCondutor() {
		return  ResponseEntity.ok(combosServices.tipoCondutorList());
	}
	
	@CrossOrigin
	@GetMapping("/tipo-frustracao")
	public ResponseEntity<List<GenericList>> listaTipoFrustracao() {
		return  ResponseEntity.ok(combosServices.tipoFrustracaoList());
	}
	
	@CrossOrigin
	@GetMapping("/tipo-material-carroceria")
	public ResponseEntity<List<GenericList>> listaMaterialCarroceria() {
		return  ResponseEntity.ok(combosServices.tipoMaterialCarroceriaList());
	}
	
	@CrossOrigin
	@GetMapping("/tipo-cabine")
	public ResponseEntity<List<GenericList>> listaTipoCabine() {
		return  ResponseEntity.ok(combosServices.tipoCabineList());
	}
	
	@CrossOrigin
	@GetMapping("/tipo-garagem")
	public ResponseEntity<List<GenericList>> listaGaragemList() {
		return  ResponseEntity.ok(combosServices.tipoGaragemList());
	}
	
	@CrossOrigin
	@GetMapping("/tipo-veiculo-utilizacao")
	public ResponseEntity<List<GenericList>> tipoVeiculoUtilList() {
		return  ResponseEntity.ok(combosServices.tipoGaragemList());
	}
	
	@CrossOrigin
	@GetMapping("/parecer-tecnico")
	public ResponseEntity<List<ParecerTecnicoDTO>> parecerTecnicolList() {
	 	List<ParecerTecnicoDTO> pareceres = combosServices.parecerTecnico().stream().map(p -> mapper.map( p ,ParecerTecnicoDTO.class )).collect(Collectors.toList());
		return  ResponseEntity.ok(pareceres);
	}
	
	@CrossOrigin
	@GetMapping("/fabricantes")
	public ResponseEntity<List<GenericList>> fabricanteListt() {
	 	List<GenericList> fabricantes = combosServices.listaFabricantes();
		return  ResponseEntity.ok(fabricantes);
	}
	
	@GetMapping("/fabricantes/{idFabricante}")
	public ResponseEntity<GenericList> obterfabricante(@PathVariable Long idFabricante) {
		return ResponseEntity.ok(new GenericList(idFabricante.toString(), null, combosServices.obterFabricante(idFabricante)));
	}
	
	@CrossOrigin
	@GetMapping("/fabricantes/{idFabricante}/modelos")
	public ResponseEntity<List<GenericList>> modeloList(@PathVariable String idFabricante) {
	 	List<GenericList> modelos = combosServices.listaModeloPorFabr(idFabricante);
		return  ResponseEntity.ok(modelos);
	}
	
	@GetMapping("/fabricantes/{idFabricante}/modelos/{idModelo}")
	public ResponseEntity<GenericList> getModelo(@PathVariable Long idFabricante, @PathVariable Long idModelo) {
		return ResponseEntity.ok(new GenericList(idModelo.toString(), null, combosServices.getModeloPorFabr(idFabricante, idModelo)));
	}

	@GetMapping("/situacoes-vistoria")
	public ResponseEntity<List<Map<String, String>>> getSituacaoVistoriaList(@RequestParam(required = false) String tela) {
		List<Map<String, String>> situacaoVistoriaEnumList = combosServices.listaSituacaoVistoriaEnum(tela);
		return !situacaoVistoriaEnumList.isEmpty() ? ResponseEntity.ok(situacaoVistoriaEnumList) : ResponseEntity.noContent().build();
	}

}
