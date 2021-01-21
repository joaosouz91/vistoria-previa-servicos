package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PrestadoraStatusDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.PrestadoraStatus;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.RelatorioAgendamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.TotalAgendamentoStatusLocal;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.TotalStatusPrestadora;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.RelatorioAgendamentoFilter;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.relatorio.RelatorioAgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios/agendamentos")
public class RelatorioAgendamentoController {

    @Autowired
    private RelatorioAgendamentoService relatorioAgendamentoService;

    @GetMapping("/rejeitados-frustrados")
    public ResponseEntity<Page<RelatorioAgendamento>> getRelatorioRejeitadosFrustrados(
           RelatorioAgendamentoFilter agendamentoFilter,
           @RequestParam(value = "page", required = false) Integer page,
           @RequestParam(value = "size", required = false) Integer size) {
        Page<RelatorioAgendamento> relatorioAgendamentos = relatorioAgendamentoService.recuperarRelatorioRejeitadosFrustradosByFilter(agendamentoFilter, page, size);
        return relatorioAgendamentos != null && !relatorioAgendamentos.isEmpty() ? ResponseEntity.ok(relatorioAgendamentos) : ResponseEntity.noContent().build();
    }

    @GetMapping("/historico-agendamentos")
    public ResponseEntity<Page<RelatorioAgendamento>> getRelatorioHistoricoAgendamentos(
            RelatorioAgendamentoFilter agendamentoFilter,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        Page<RelatorioAgendamento> relatorioAgendamentos = relatorioAgendamentoService.recuperarRelatorioHistoricoAgendamentosByFilter(agendamentoFilter, page, size);
        return relatorioAgendamentos != null && !relatorioAgendamentos.isEmpty() ? ResponseEntity.ok(relatorioAgendamentos) : ResponseEntity.noContent().build();
    }

    @GetMapping("/situacao-agendamentos")
    public ResponseEntity<Page<RelatorioAgendamento>> getSituacaoAgendamentos(
            RelatorioAgendamentoFilter agendamentoFilter,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        Page<RelatorioAgendamento> relatorioAgendamentos = relatorioAgendamentoService.recuperarRelatorioSituacaoAgendamentoByFilter(agendamentoFilter, page, size);
        return relatorioAgendamentos != null && !relatorioAgendamentos.isEmpty() ? ResponseEntity.ok(relatorioAgendamentos) : ResponseEntity.noContent().build();
    }

    @GetMapping("/status-prestadora")
    public ResponseEntity<PrestadoraStatusDTO> getTotalStatusPrestadora(
            RelatorioAgendamentoFilter agendamentoFilter,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        PrestadoraStatusDTO dto = relatorioAgendamentoService.recuperarTotalStatusPrestadoraByFilter(agendamentoFilter, page, size);
        return dto.getPrestadoraStatusList() != null && !dto.getPrestadoraStatusList().isEmpty() ? ResponseEntity.ok(dto) : ResponseEntity.noContent().build();
    }

    @GetMapping("/agendamentos-status-local")
    public ResponseEntity<List<TotalAgendamentoStatusLocal>> getTotalAgendamentoStatusLocal(Long codCorretor, String periodo) {
        List<TotalAgendamentoStatusLocal> relatorioAgendamentos = relatorioAgendamentoService.recuperarTotalAgendamentoStatusLocal(codCorretor, periodo);
        return relatorioAgendamentos != null && !relatorioAgendamentos.isEmpty() ? ResponseEntity.ok(relatorioAgendamentos) : ResponseEntity.noContent().build();
    }

}
