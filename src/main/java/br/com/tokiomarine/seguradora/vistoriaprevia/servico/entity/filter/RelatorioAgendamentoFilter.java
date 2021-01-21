package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioAgendamentoFilter {

    private String numVoucher;

    private String numPlaca;

    private Long codCorretor;

    private Long idPrestadora;

    private String codSitVistoria;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataPesquisaDe;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataPesquisaAte;

    private String formaAgrupamento;

}
