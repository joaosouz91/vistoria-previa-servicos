package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.EstatisticaAtrasosTransmissaoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.DetalhamentoAtrasoTransmissao;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.DetalhamentoAtrasoTransmissaoTotal;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.EstatisticaAtrasosTransmissaoService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.ParametroVistoriaPreviaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estatistica/atrasos-transmissao")
public class EstatisticaAtrasosTransmissaoController {

    @Autowired
    private EstatisticaAtrasosTransmissaoService estatisticaAtrasosTransmissaoService;

    @Autowired
    ParametroVistoriaPreviaService parametroVistoriaPreviaService;

    @GetMapping
    public ResponseEntity<EstatisticaAtrasosTransmissaoDTO> listarAtrasosTransmissaoAgrupamento(
            @RequestParam(value = "idPrestadora") Long idPrestadora,
            @RequestParam(value = "periodo") String periodo,
            @RequestParam(value = "modulo", required = false) Long modulo,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "page", required = false) Integer pageIndex) {
        EstatisticaAtrasosTransmissaoDTO retorno = estatisticaAtrasosTransmissaoService.getAtrasoTransmissaoAgrupamentoDTO(periodo, idPrestadora, modulo, size, pageIndex);
        return retorno != null && !retorno.getAtrasoTransmissaoAgrupamentoPageableList().isEmpty() ? ResponseEntity.ok(retorno): ResponseEntity.noContent().build();
    }

    @GetMapping("/detalhe-laudo")
    public ResponseEntity<Page<DetalhamentoAtrasoTransmissao>> listarDetalheLaudo(
            @RequestParam(value = "idPrestadora") Long idPrestadora,
            @RequestParam(value = "periodo") String periodo,
            @RequestParam(value = "diaInicio") Integer diaInicio,
            @RequestParam(value = "diaFim") Integer diaFim,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "page", required = false) Integer pageIndex) {
        Page<DetalhamentoAtrasoTransmissao> retorno = estatisticaAtrasosTransmissaoService.getDetalhamentoLaudoAtrasoTransmissao(periodo, idPrestadora, diaInicio, diaFim, size, pageIndex);
        return retorno != null && !retorno.isEmpty() ? ResponseEntity.ok(retorno): ResponseEntity.noContent().build();
    }

    @GetMapping("/detalhe-total")
    public ResponseEntity<List<DetalhamentoAtrasoTransmissaoTotal>> buscarDetalheTotal(
            @RequestParam(value = "idPrestadora") Long idPrestadora,
            @RequestParam(value = "periodo") String periodo) {
        List<DetalhamentoAtrasoTransmissaoTotal> retorno = estatisticaAtrasosTransmissaoService.getDetalhamentoAtrasoTransmissaoTotal(idPrestadora, periodo);
        return retorno != null ? ResponseEntity.ok(retorno) : ResponseEntity.notFound().build();
    }

    @GetMapping("/param-vp")
    public ResponseEntity<ParametroVistoriaPrevia> buscarParamVistoriaPrevia(
            @RequestParam(value = "modulo", required = false) Long modulo) {
        ParametroVistoriaPrevia retorno = parametroVistoriaPreviaService.getParametroVistoriaPreviaPorModuloSelecionado(modulo);
        return retorno != null ? ResponseEntity.ok(retorno) : ResponseEntity.notFound().build();
    }

}
