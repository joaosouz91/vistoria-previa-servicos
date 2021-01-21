package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.EstatisticaVistoriasRealizadas;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.EstatisticaVistoriasRealizadasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estatistica/vistorias-realizadas")
public class EstatisticaVistoriasRealizadasController {

    @Autowired
    private EstatisticaVistoriasRealizadasService estatisticaVistoriasRealizadasService;

    @GetMapping
    public Page<EstatisticaVistoriasRealizadas> listarVistoriasRealizadas(
            @RequestParam(value = "idPrestadora") Long idPrestadora,
            @RequestParam(value = "periodo") String periodo,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "page", required = false) Integer pageIndex) {
        Page<EstatisticaVistoriasRealizadas> retorno = estatisticaVistoriasRealizadasService.getEstatisticaVistoriasRealizadas(periodo, idPrestadora, size, pageIndex);
        return retorno != null && !retorno.isEmpty() ? retorno: Page.empty();
    }

    @GetMapping("/detalhe")
    public Page<EstatisticaVistoriasRealizadas> listarVistoriasRealizadasDetalhe(
            @RequestParam(value = "idPrestadora") Long idPrestadora,
            @RequestParam(value = "periodo") String periodo,
            @RequestParam(value = "situacao") String situacao,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "page", required = false) Integer pageIndex) {
        Page<EstatisticaVistoriasRealizadas> retorno = estatisticaVistoriasRealizadasService.getDetalheEstatisticaVistoriasRealizadas(periodo, idPrestadora, situacao, size, pageIndex);
        return retorno != null && !retorno.isEmpty() ? retorno: Page.empty();
    }

}
