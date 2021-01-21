package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaObrigatoriaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.VistoriaPreviaObrigatoriaService;

@RestController
@RequestMapping(value = "/api/vistoria-previa-obrigatoria")
public class VistoriaPreviaObrigatoriaController {

	@Autowired
	private VistoriaPreviaObrigatoriaService vistoriaService;
	
	
	@GetMapping("/{idVistoria}")
	public ResponseEntity<VistoriaObrigatoriaDTO> buscarVistoria(@PathVariable Long idVistoria) {
		VistoriaObrigatoriaDTO vistoria = vistoriaService.buscarVistoria(idVistoria);
		return ResponseEntity.ok(vistoria);
	}

	@CrossOrigin
	@PostMapping("/pesquisa") 
	public ResponseEntity<List<VistoriaObrigatoriaDTO>> pesquisar(@RequestBody VistoriaFiltro filtro) throws BindException {
		List<VistoriaObrigatoriaDTO> vistoriasPage = vistoriaService.pesquisarVistoriasObrigatorias(filtro);
		
		return ResponseEntity.ok(vistoriasPage);
	}

	@CrossOrigin
	@PostMapping("/frota/pesquisa")
	public ResponseEntity<List<VistoriaObrigatoriaDTO>> pesquisarFrota(@RequestBody VistoriaFiltro filtro) throws BindException {
		List<VistoriaObrigatoriaDTO> vistoriasPage = vistoriaService.obterVistoriasFrota(filtro.getCpfCnpj(), filtro.getCorretor(), filtro.getEndosso(), filtro.getNegocio());
		return ResponseEntity.ok(vistoriasPage);
	}
	
	@GetMapping("/enviar-email-1-contato")
	public String enviaEmailContato1() throws Exception {
		
		try {
			vistoriaService.emailContato1();
			 return "0";
		} catch (Exception e) {
			 return "1";
		}
	}
	
	@GetMapping("/enviar-email-2-contato")
	public String enviaEmailContato2() throws Exception {
		
		try {
			vistoriaService.emailContato2();
			 return "0";
		} catch (Exception e) {
			 return "1";
		}
	}
	
	@CrossOrigin
	@PostMapping("/salvar") 
	public ResponseEntity<VistoriaObrigatoriaDTO> pesquisar(@RequestBody VistoriaObrigatoriaDTO vistoriaDTO) throws BindException {
		vistoriaDTO  = vistoriaService.save(vistoriaDTO);
		
		return ResponseEntity.ok(vistoriaDTO);
	}
}
