package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaudoTransmitidoFilter {

    private Long idPrestadora;

    private String codLaudo;

    private String chassi;

    private String numPlaca;

    private String laudoAceito;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataPesquisaDe;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataPesquisaAte;

}
