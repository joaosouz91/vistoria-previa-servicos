package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import br.com.tokiomarine.seguradora.ssv.vistoriaprevia.ws.model.ErroVP;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.LaudoVPTransmitidoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.LaudoTransmitido;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.LaudoTransmitidoFilter;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.LaudoTransmitidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vistoria/laudos-transmitidos")
public class LaudoTransmitidoController {

    @Autowired
    private LaudoTransmitidoService laudoTransmitidoService;

    @GetMapping
    public ResponseEntity<Page<LaudoTransmitido>> recuperarLaudosTransmitidos(
            LaudoTransmitidoFilter filter,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page){
        Page<LaudoTransmitido> retorno = laudoTransmitidoService.getLaudoTransmitidoListByFilter(filter, page, size);
        return retorno != null && !retorno.isEmpty() ? ResponseEntity.ok(retorno): ResponseEntity.noContent().build();
    }

    @GetMapping("/{idRecepcaoLaudo}")
    public ResponseEntity<LaudoVPTransmitidoDTO> recuperarDetalheLaudo(@PathVariable("idRecepcaoLaudo") Long idRecepcaoLaudo){
        LaudoVPTransmitidoDTO retorno = laudoTransmitidoService.getLaudoDetalhe(idRecepcaoLaudo);
        return retorno != null ? ResponseEntity.ok(retorno): ResponseEntity.notFound().build();
    }

    @GetMapping("/{idRecepcaoLaudo}/inconsistencias")
    public ResponseEntity<List<ErroVP>> recuperarInconsistencias(@PathVariable("idRecepcaoLaudo") Long idRecepcaoLaudo){
        List<ErroVP> retorno =   laudoTransmitidoService.getInconsistencias(idRecepcaoLaudo);
        return retorno != null && !retorno.isEmpty() ? ResponseEntity.ok(retorno): ResponseEntity.noContent().build();
    }


}
