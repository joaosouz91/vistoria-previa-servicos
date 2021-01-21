package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PrestadoraVistoriaPrevia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class PrestadoraStatus implements Serializable {

    private PrestadoraVistoriaPrevia prestadora;

    private Long contStatusAGD = 0l;
    private Long contStatusRCB = 0l;
    private Long contStatusPEN = 0l;
    private Long contStatusVFR = 0l;
    private Long contStatusRLZ = 0l;
    private Long contStatusRGD = 0l;

    // * REAGENDAR
    private Long contStatusCAN = 0l;

    // MOBILE
    private Long contStatusFTR = 0l;
    private Long contStatusPEF = 0l;
    private Long contStatusLKX = 0l;
    private Long contStatusFTT = 0l;

    private Long totalAgtoPrestadora = 0l;


}
