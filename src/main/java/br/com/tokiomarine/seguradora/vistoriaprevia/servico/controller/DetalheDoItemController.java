package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.NegocioComponentDto;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.DetalheDoItem;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.DetalheDoItemService;

@RestController
@RequestMapping("${api.path}/negocio-componentes")
@ResponseStatus(HttpStatus.OK)
public class DetalheDoItemController {

	@Autowired
	private DetalheDoItemService detalheDoItemBusiness;
	
	@CrossOrigin
	@PostMapping
	public ResponseEntity<DetalheDoItem> negocio(@Valid @RequestBody NegocioComponentDto negocioComponentDto)  {		
		
		final DetalheDoItem detalheDoItem = detalheDoItemBusiness.consultaDetalheDoItem(negocioComponentDto);
		
		return ResponseEntity.ok(detalheDoItem);
	}
}