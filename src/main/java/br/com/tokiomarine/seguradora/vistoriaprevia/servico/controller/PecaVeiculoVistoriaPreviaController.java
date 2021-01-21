package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PecaVeiculoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.PecaVeiculoVistoriaPreviaFilter;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.event.CreatedResourceEvent;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.PecaVeiculoVistoriaPreviaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pecas")
public class PecaVeiculoVistoriaPreviaController {

    @Autowired
    private PecaVeiculoVistoriaPreviaService service;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping
    public ResponseEntity<List<PecaVeiculoVistoriaPrevia>> listarPecasVistoriaPrevia(PecaVeiculoVistoriaPreviaFilter filter){
        List<PecaVeiculoVistoriaPrevia> retorno = service.getVistoriaPreviaByFilter(filter);
        return retorno != null && !retorno.isEmpty() ? ResponseEntity.ok(retorno): ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PecaVeiculoVistoriaPrevia> criar(@RequestBody PecaVeiculoVistoriaPrevia model, HttpServletResponse response){
        PecaVeiculoVistoriaPrevia created = service.criar(model);
        eventPublisher.publishEvent(new CreatedResourceEvent(this, response, created.getCdPecaAvada().toString()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PecaVeiculoVistoriaPrevia> atualizar(@PathVariable(value = "id") Long id, @RequestBody PecaVeiculoVistoriaPrevia model){
        service.atualizar(id, model);
        return ResponseEntity.ok().build();
    }

}
