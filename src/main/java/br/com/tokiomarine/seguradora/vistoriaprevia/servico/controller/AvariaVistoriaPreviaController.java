package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AvariaVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PecaVeiculoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.AvariaVistoriaPreviaFilter;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.event.CreatedResourceEvent;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.AvariaVistoriaPreviaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/avarias")
public class AvariaVistoriaPreviaController {

    @Autowired
    private AvariaVistoriaPreviaService service;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping
    public ResponseEntity<List<AvariaVistoriaPrevia>> listarPecasVistoriaPrevia(AvariaVistoriaPreviaFilter filter){
        List<AvariaVistoriaPrevia> retorno =  service.getAvariaVistoriaPreviaByFilter(filter);
        return retorno != null && !retorno.isEmpty() ? ResponseEntity.ok(retorno): ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AvariaVistoriaPrevia> criar(@RequestBody AvariaVistoriaPrevia model, HttpServletResponse response){
        AvariaVistoriaPrevia created = service.criar(model);
        eventPublisher.publishEvent(new CreatedResourceEvent(this, response, created.getCdTipoAvari()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PecaVeiculoVistoriaPrevia> atualizar(@PathVariable(value = "id") String id, @RequestBody AvariaVistoriaPrevia model){
        service.atualizar(id, model);
        return ResponseEntity.ok().build();
    }

}
