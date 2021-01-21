package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.EstatisticaRelatorioFaturamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.DetalhamentoEstatisticaFaturamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.EstatisticaRelatorioFaturamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estatistica/relatorio-faturamento")
public class EstatisticaRelatorioFaturamentoController {

    @Autowired
    private EstatisticaRelatorioFaturamentoService estatisticaRelatorioFaturamentoService;

    @GetMapping
    public ResponseEntity<EstatisticaRelatorioFaturamentoDTO> buscarEstatisticaFaturamento(
            @RequestParam(value = "idPrestadora") Long idPrestadora,
            @RequestParam(value = "periodo") String periodo,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "page", required = false) Integer pageIndex) {
        EstatisticaRelatorioFaturamentoDTO retorno = estatisticaRelatorioFaturamentoService.getEstatisticaFaturamentoDTO(idPrestadora, periodo, size, pageIndex);
        return retorno != null ? ResponseEntity.ok(retorno): ResponseEntity.noContent().build();
    }

    @GetMapping("/detalhe")
    public ResponseEntity<Page<DetalhamentoEstatisticaFaturamento>> buscarDetalheEstatisticaFaturamento(
            @RequestParam(value = "idPrestadora") Long idPrestadora,
            @RequestParam(value = "periodo") String periodo,
            @RequestParam(value = "tipoLocalVistoria") String tipoLocalVistoria,
            @RequestParam(value = "distanciaKilometragem") Long distanciaKilometragem,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "page", required = false) Integer pageIndex) {
        Page<DetalhamentoEstatisticaFaturamento> retorno = estatisticaRelatorioFaturamentoService.getDetalheEstatisticaFaturamentoList(idPrestadora, periodo, tipoLocalVistoria, distanciaKilometragem, size, pageIndex);
        return retorno != null && !retorno.isEmpty() ? ResponseEntity.ok(retorno): ResponseEntity.noContent().build();
    }

}
