package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.ext.ssv.service.ConteudoColunaTipoService;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AvariaLaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.FinalidadeVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoAvarias;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoPecas;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.LaudoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.LaudoRegrasDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.FiltroConsultaVistoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ResponseLaudoEditar;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BadRequestException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.FinalidadeVistoriaPreviaService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.LaudoService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.hateoas.ResourceUtil;

@RestController
@RequestMapping("/v1/laudo-vistoria")
//@Api(value = "LaudoVistoriaPrevia", description = "Métodos Laudo Vistoria prévia (laudos)")
public class LaudoVistoriaController extends ResourceUtil<ResponseLaudoEditar, LaudoDTO> {

	@Autowired
	private ConteudoColunaTipoService conteudoColunaTipoService;
	
	@Autowired
	private FinalidadeVistoriaPreviaService finalidadeVistoriaPreviaService;
	
	@Autowired
	private LaudoService laudoService;
	
	@CrossOrigin
	@PostMapping
	public ResponseEntity<List<LaudoRegrasDTO>> consultaLaudos(@RequestBody FiltroConsultaVistoria  filtroConsultaVistoria) {
		
		if (filtroConsultaVistoria.isFiltroInvalido()) {
			throw new BusinessVPException("Preencha ao menos um dos campos para a pesquisa.");
		}
		
		final List<LaudoRegrasDTO> laudoVistorias = laudoService.consultaLaudos(filtroConsultaVistoria);
		return ResponseEntity.ok(laudoVistorias);
	}
	
	
	@CrossOrigin 
	@GetMapping("/laudo/{codigoLaudo}")
	public ResponseEntity<LaudoDTO> obterLaudoPorCodigo(@PathVariable String codigoLaudo){
		ResponseLaudoEditar laudoCompleto = laudoService.obterLaudoCompletoPorCodigo(codigoLaudo);
		return ok(laudoCompleto) ;
	}
	
	
	@CrossOrigin
	@PutMapping("/laudo/save")
	public ResponseEntity<LaudoDTO> salvarLaudoCompleto(@RequestBody LaudoDTO laudo){
		ResponseLaudoEditar laudoCompleto = toEntity(laudo).orElseThrow(BadRequestException::new);
		laudoService.salvarLaudoCompleto(laudoCompleto);
		return ok(laudoCompleto);
	}
	
	
	@CrossOrigin
	@PutMapping("/laudo/bloqueio")
	public ResponseEntity<LaudoDTO> permitirBloqueioLaudo( @RequestBody  LaudoDTO laudo){
		ResponseLaudoEditar response = toEntity(laudo).orElseThrow(BadRequestException::new);
		ResponseLaudoEditar laudoCompleto = laudoService.permitirBloqueio(response.getLaudo().getCdLvpre(), response.getLaudo().getDsMotvRclsfVspre());
		return ok(laudoCompleto);
	}
	
	
	@CrossOrigin
	@GetMapping("/laudo/desvincular-laudo/{codigoLaudo}")
	public ResponseEntity<LaudoDTO> desvincularLaudo( @PathVariable String codigoLaudo){
		LaudoVistoriaPrevia laudo = laudoService.desvincularLaudo(codigoLaudo);
		ResponseLaudoEditar laudoResponse = new ResponseLaudoEditar();
		laudoResponse.setLaudo(laudo);
		return ok(laudoResponse);
	}
	
	
	@CrossOrigin
	@GetMapping("/laudo/bloquear-por-supervisao/{codigoLaudo}")
	public ResponseEntity<LaudoDTO> bloquearPorSupervisao( @PathVariable String codigoLaudo){
		LaudoVistoriaPrevia laudo = laudoService.bloqueioPorSupervisao(codigoLaudo, 0L);
		ResponseLaudoEditar response = new ResponseLaudoEditar();
		response.setLaudo(laudo);
		return ok(response);
	}
		

	@CrossOrigin
	@GetMapping("/laudo/{codigoLaudo}/log-detalhe")
	public ResponseEntity<List<String>> logDetalheVinculo(@PathVariable String codigoLaudo){
		LaudoVistoriaPrevia laudo =  laudoService.obterLaudoPorCodigoVistoria(codigoLaudo);
	    List<String> logs = laudoService.obterLogVinculoLaudo(laudo.getCdLvpre(), laudo.getDtVspre());
		return new ResponseEntity<>(logs, HttpStatus.OK);
	}
	

	@CrossOrigin
	@GetMapping("/tipo-local-vistoria")
	public ResponseEntity<List<ConteudoColunaTipo>> listaTipoLocalVistoria() {		
		final List<ConteudoColunaTipo> conteudoColunaTipos = conteudoColunaTipoService.listaTipoLocalVistoria();
		return new ResponseEntity<>(conteudoColunaTipos, HttpStatus.OK); 
	}
	
	
	@CrossOrigin
	@GetMapping("/finalidade-vistorias-all")
	public ResponseEntity<List<FinalidadeVistoriaPrevia>> listaFinalidadeVistoriaPreviaService() {			
		final List<FinalidadeVistoriaPrevia> finalidadeVistoriaPrevias = finalidadeVistoriaPreviaService.findAllFinalidadeVistoriaPrevia();
		return new ResponseEntity<>(finalidadeVistoriaPrevias, HttpStatus.OK);  
	}
	
	@GetMapping("/finalidade-vistorias/{cod}")
	public ResponseEntity<FinalidadeVistoriaPrevia> obterFinalidadeVistoriaPreviaService(@PathVariable Long cod) {			
		return ResponseEntity.ok(finalidadeVistoriaPreviaService.buscarFinalidadeVistoriaPrevia(cod));  
	}
	
	@CrossOrigin
	@GetMapping("/arquivoavarias")
	public ResponseEntity<List<ArquivoAvarias>> getDadosArquivoAvarias() {			
		final List<ArquivoAvarias> arquivoAvarias = laudoService.getDadosArquivoAvarias();
		return new ResponseEntity<>(arquivoAvarias, HttpStatus.OK);  
	}
	
	
	@CrossOrigin
	@GetMapping("/arquivopecas")
	public ResponseEntity<List<ArquivoPecas>> getDadosArquivoPecas() {			
		final List<ArquivoPecas> arquivoPecas = laudoService.getDadosArquivoPecas();
		return new ResponseEntity<>(arquivoPecas, HttpStatus.OK);  
	}
	
	@CrossOrigin
	@GetMapping("/avarias/{cdLvpre}")
	public ResponseEntity<List<Object>> getAvarias(@PathVariable String cdLvpre) {			
		final List<Object> avarias = laudoService.getAvarias(cdLvpre);
		return new ResponseEntity<>(avarias, HttpStatus.OK);  
	}
	
	@CrossOrigin
	@GetMapping("/avaria/{cdLvpre}/{codPeca}/{tpAvaria}")
	public ResponseEntity<AvariaLaudoVistoriaPrevia> getAvaria(@PathVariable String cdLvpre, @PathVariable Long codPeca,  @PathVariable String tpAvaria) {			
		return new ResponseEntity<>(laudoService.getAvaria(cdLvpre, codPeca,tpAvaria), HttpStatus.OK);  
	}
	
	@CrossOrigin
	@PostMapping("/avarias")
	public ResponseEntity<AvariaLaudoVistoriaPrevia> saveAvariaLaudoVistoriaPrevia(@RequestBody AvariaLaudoVistoriaPrevia avariaLaudoVistoriaPrevia) {			
		return new ResponseEntity<>(laudoService.saveAvariaLaudoVistoriaPrevia(avariaLaudoVistoriaPrevia), HttpStatus.OK);  
	}
	
	@CrossOrigin
	@DeleteMapping("/avaria/{cdLvpre}/{codPeca}/{tpAvaria}")
	public ResponseEntity<Object> deleteAvariaLaudoVistoriaPrevia(@PathVariable String cdLvpre, @PathVariable Long codPeca,  @PathVariable String tpAvaria) {			
		return new ResponseEntity<>(laudoService.deleteAvaria(cdLvpre, codPeca,tpAvaria), HttpStatus.OK);  
	}
	
	@Override
	public Link linkTo(LaudoDTO dto) {
		return ControllerLinkBuilder
				.linkTo(methodOn(LaudoVistoriaController.class).obterLaudoPorCodigo(dto.getLaudo().getCdLvpre()))
				.withSelfRel();
	}

}
