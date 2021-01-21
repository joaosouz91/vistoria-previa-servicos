package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaudoTransmitido implements Serializable {

    private Long idRcpaoLaudo;
    private String cdLvpre;
    private Long cdAgrmtVspre;
    private String cdChassiVeicu;
    private String cdPlacaVeicu;
    private Date dtRcpaoLaudo;
    private String icEmpre;
    private String icLaudoAceto;
    private boolean possuiInconsistencias;

    @Transient
    private String nmPrestadora;

    public LaudoTransmitido(Long idRcpaoLaudo, String cdLvpre, Long cdAgrmtVspre, String cdChassiVeicu, String cdPlacaVeicu,
                            Date dtRcpaoLaudo, String icEmpre, String icLaudoAceto, byte[] inconsistencias) {

        this.idRcpaoLaudo = idRcpaoLaudo;
        this.cdLvpre = cdLvpre;
        this.cdAgrmtVspre = cdAgrmtVspre;
        this.cdChassiVeicu = cdChassiVeicu;
        this.cdPlacaVeicu = cdPlacaVeicu;
        this.dtRcpaoLaudo = dtRcpaoLaudo;
        this.icEmpre = icEmpre;
        this.icLaudoAceto = icLaudoAceto;

        if (inconsistencias != null && inconsistencias.length > 0) {
            this.possuiInconsistencias = true;
        }
    }

}
