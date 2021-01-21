package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@SqlResultSetMapping(name="DetalheAtrasoTransmissaoAgrupamento",
        entities = {
                @EntityResult(entityClass=DetalhamentoAtrasoTransmissao.class,
                        fields={
                                @FieldResult(name="CODIGO_LAUDO",column="codigoLaudo"),
                                @FieldResult(name="CD_PLACA_VEICU",column="numeroPlaca"),
                                @FieldResult(name="CD_CHASSI_VEICU",column="numeroChassi"),
                                @FieldResult(name="NM_PRPNT",column="nomeProponente"),
                                @FieldResult(name="DT_TRNSM_VSPRE",column="dataTransmissao"),
                                @FieldResult(name="DT_VSPRE",column="dataVistoria"),
                                @FieldResult(name="DT_RCLSF_VSPRE",column="dataReclassificacao"),
                                @FieldResult(name="SITUACAO_LAUDO",column="situacaoLaudo"),
                                @FieldResult(name="QT_DIAS_ATRSO_TRNSM",column="atrasoDias")
                        }
                )
        }
)
@SuppressWarnings("serial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalhamentoAtrasoTransmissao implements Serializable {

    @Id
    @Column(name="CODIGO_LAUDO")
    private String codigoLaudo;

    @Column(name="CD_PLACA_VEICU")
    private String numeroPlaca;

    @Column(name="CD_CHASSI_VEICU")
    private String numeroChassi;

    @Column(name="NM_PRPNT")
    private String nomeProponente;

    @Column(name="DT_TRNSM_VSPRE")
    @Temporal(TemporalType.DATE)
    private Date dataTransmissao;

    @Column(name="DT_VSPRE")
    @Temporal(TemporalType.DATE)
    private Date dataVistoria;

    @Column(name="DT_RCLSF_VSPRE")
    @Temporal(TemporalType.DATE)
    private Date dataReclassificacao;

    @Column(name="SITUACAO_LAUDO")
    private String situacaoLaudo;

    @Column(name="QT_DIAS_ATRSO_TRNSM")
    private Long atrasoDias;

}