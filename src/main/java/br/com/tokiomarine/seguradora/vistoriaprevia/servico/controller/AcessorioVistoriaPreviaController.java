package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AcessorioVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.AcessorioVistoriaPreviaFilter;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.event.CreatedResourceEvent;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.AcessorioVistoriaPreviaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/acessorios")
public class AcessorioVistoriaPreviaController {

    @Autowired
    private AcessorioVistoriaPreviaService service;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping
    public ResponseEntity<List<AcessorioVistoriaPrevia>> listarAcessoriosVistoriaPrevia(AcessorioVistoriaPreviaFilter filter){
        List<AcessorioVistoriaPrevia> retorno = service.getAcessorioVistoriaPreviaListByFilter(filter);
        return !retorno.isEmpty() ? ResponseEntity.ok(retorno) : ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody AcessorioVistoriaPrevia model, HttpServletResponse response){
        AcessorioVistoriaPrevia created = service.criar(model);
        eventPublisher.publishEvent(new CreatedResourceEvent(this, response, created.getCdAcsroVspre().toString()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable(value = "id") Long id, @RequestBody AcessorioVistoriaPrevia model){
        service.atualizar(id, model);
        return ResponseEntity.ok().build();
    }

}
