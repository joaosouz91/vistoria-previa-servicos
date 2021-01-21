package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.ConteudoColunaTipoFilter;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.event.CreatedResourceEvent;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.MotivoDispensaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/motivos-dispensa")
public class MotivoDispensaController {

    @Autowired
    private MotivoDispensaService service;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping
    public ResponseEntity<List<ConteudoColunaTipo>> findByFilter(ConteudoColunaTipoFilter filter) {
        List<ConteudoColunaTipo> retorno = service.getMotivoDispensaByFilter(filter);
        return !retorno.isEmpty() ? ResponseEntity.ok(retorno) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConteudoColunaTipo> findById(@PathVariable Long id) {
        ConteudoColunaTipo retorno = service.getMotivoDispensaById(id);
        return ResponseEntity.ok(retorno);
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody ConteudoColunaTipo model, HttpServletResponse response){
        ConteudoColunaTipo created = service.criar(model);
        eventPublisher.publishEvent(new CreatedResourceEvent(this, response, created.getVlCntdoColunTipo()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable(value = "id") String id, @RequestBody ConteudoColunaTipo model){
        service.atualizar(id, model);
        return ResponseEntity.ok().build();
    }

}
