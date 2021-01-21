package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.LogradouroDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.AjusteParametros;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Response;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.VerificaDispensaLaudo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NoContentException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.AjusteVistoriaPreviaService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.DispensaService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.LaudoVistoriaService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.MapService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.ServicoService;

@RestController
@RequestMapping("/v1/api/servicos-aceitacao")
@ResponseStatus(HttpStatus.OK)
public class ServicosAceitacaoApiController {

	@Autowired
	private DispensaService dispensaBusiness;
	
	@Autowired
	private LaudoVistoriaService laudoVistoriaService;
	
	@Autowired
	private ServicoService servicoBusiness;
	
	@Autowired
	private AjusteVistoriaPreviaService ajusteVistoriaPreviaBusiness;

	@Autowired
	private MapService mapService;
	
	@CrossOrigin
	@GetMapping("/dispensa-vistoria/{numeroItem}/{codigoEndosso}/{tipoOrigem}/{codigoUsuario}")
	public ResponseEntity<Object> dispensarVistoria(@PathVariable("numeroItem") Long numeroItem, 
											   @PathVariable("codigoEndosso") Long codigoEndosso, 
											   @PathVariable("tipoOrigem") Long tipoOrigem,
											   @PathVariable("codigoUsuario") String codigoUsuario) {		
				
		try{
			
			dispensaBusiness.dispensarVistoriaProposta(numeroItem, codigoEndosso, tipoOrigem, codigoUsuario);
			
			return new ResponseEntity<>(null, HttpStatus.OK);
		
		} catch(Exception ex){
			
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}

	@CrossOrigin
	@GetMapping("/desvincula-laudo/{numeroItem}/{codigoEndosso}/{codigoUsuario}")
	public ResponseEntity<Object> desvincularLaudo(@PathVariable("numeroItem") Long numeroItemSegurado, @PathVariable("codigoEndosso") Long codigoEndosso, @PathVariable("codigoUsuario") String codigoUsuario) {		
				
		try{
			
			laudoVistoriaService.desvincularLaudo(numeroItemSegurado, codigoEndosso, codigoUsuario);
			
			return new ResponseEntity<>(null, HttpStatus.OK);
		
		} catch(Exception ex){
			
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}	
		
//	@CrossOrigin
//	@PostMapping("/verificar-vinculos")
//	public Response<Boolean> servico(@Valid @RequestBody VitoriaPreviaServicoDto vitoriaPreviaServicoDto) {		
//		
//		final Boolean resposta = servicoBusiness.verificaSeExisteVistoriaNegocioOuEndosso(vitoriaPreviaServicoDto);
//						
//		return new Response<>(resposta);
//	}
	
	@CrossOrigin
	@GetMapping("/dados-laudo/{cdLvpre}")
	public ResponseEntity<Object> controleVistoriaPrevia(@PathVariable("cdLvpre") String  cdLvpre) {				
		try{			
			final AjusteParametros ajusteParametros = ajusteVistoriaPreviaBusiness.getControleVistoriaPrevia(cdLvpre);			
			return new ResponseEntity<>(ajusteParametros, HttpStatus.OK);
		
		} catch(Exception ex){
			throw new InternalServerException("");			
		}		
	}
	
	@CrossOrigin
	@PostMapping("/verificar-dispensa")
	public Response<Boolean> servico(@RequestBody VerificaDispensaLaudo verificaDispensaLaudo) {		
		
		final Boolean resposta = ajusteVistoriaPreviaBusiness.verificaDispensaLaudo(verificaDispensaLaudo);
						
		return new Response<>(resposta);
	}
	
	@GetMapping("/logradouro/{cep}")
	public ResponseEntity<LogradouroDTO> obterLogradouro(@PathVariable String cep) {
		return ResponseEntity.ok(mapService.obterLogradouro(cep).orElseThrow(NoContentException::new));
	}
}