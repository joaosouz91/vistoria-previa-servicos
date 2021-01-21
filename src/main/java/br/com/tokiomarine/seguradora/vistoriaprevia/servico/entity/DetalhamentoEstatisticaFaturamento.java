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
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @author CTIS Tecnologia S/A
 * @version 1.0
 *
 */
@Entity
@SqlResultSetMappings ({
        @SqlResultSetMapping(name="DetalhamentoEstatisticaFaturamento",
                entities = {
                        @EntityResult(entityClass=DetalhamentoEstatisticaFaturamento.class,
                                fields={
                                        @FieldResult(name="CD_LVPRE",column="codigoLaudo"),
                                        @FieldResult(name="DT_VSPRE",column="dataVistoria"),
                                        @FieldResult(name="DT_INCLS_RGIST",column="dataTransmissao"),
                                        @FieldResult(name="CD_PLACA_VEICU",column="numeroPlaca"),
                                        @FieldResult(name="CD_CHASSI_VEICU",column="numeroChassi"),
                                        @FieldResult(name="CD_CRTOR_SEGUR",column="codigoCorretor"),
                                        @FieldResult(name="CD_LOCAL_CAPTC",column="localCaptador"),
                                        @FieldResult(name="TP_LOCAL_VSPRE",column="tipoLocalVistoria"),
                                        @FieldResult(name="QT_KM_RALZO",column="kilometragem"),
                                        @FieldResult(name="NM_CIDAD_ORIGM_VSPRE",column="cidadeOrigem"),
                                        @FieldResult(name="NM_CIDAD_DESTN_VSPRE",column="cidadeDestino"),
                                        @FieldResult(name="CD_VOUCH",column="numVoucher"),
                                        @FieldResult(name="CD_SITUC_VSPRE",column="statusVoucher"),
                                        @FieldResult(name="SG_UNIDD_FEDRC_VSPRE",column="uf"),
                                        @FieldResult(name="DS_MDUPR",column="dsProduto"),
                                        @FieldResult(name="CD_MDUPR",column="cdProduto"),
                                        @FieldResult(name="CD_VSTRO",column="cdVistoriador")
                                }
                        )
                }
        )
})

@SuppressWarnings("serial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalhamentoEstatisticaFaturamento implements Serializable {

    @Id
    @Column(name="CD_LVPRE")
    private String codigoLaudo;

    @Column(name="DT_VSPRE")
    @Temporal(TemporalType.DATE)
    private Date dataVistoria;

    @Column(name="DT_INCLS_RGIST")
    @Temporal(TemporalType.DATE)
    private Date dataTransmissao;

    @Id
    @Column(name="CD_PLACA_VEICU")
    private String numeroPlaca;

    @Id
    @Column(name="CD_CHASSI_VEICU")
    private String numeroChassi;

    @Column(name="CD_CRTOR_SEGUR")
    private Long codigoCorretor;

    @Column(name="CD_LOCAL_CAPTC")
    private Long localCaptador;

    @Column(name="TP_LOCAL_VSPRE")
    private String tipoLocalVistoria;

    private String descricaoSucursal;

    @Column(name="QT_KM_RALZO")
    private Long kilometragem;

    @Column(name="NM_CIDAD_ORIGM_VSPRE")
    private String cidadeOrigem;

    @Column(name="NM_CIDAD_DESTN_VSPRE")
    private String cidadeDestino;

    @Column(name="CD_SUCSL_COMRL")
    private String codigoSucursal;

    @Column(name="CD_VOUCH")
    private String  numVoucher;

    @Column(name="CD_SITUC_VSPRE")
    private String  statusVoucher;

    @Column(name="SG_UNIDD_FEDRC_VSPRE")
    private String  uf;

    @Column(name="DS_MDUPR")
    private String  dsProduto;

    @Column(name="CD_MDUPR")
    private String  cdProduto;

    @Column(name="CD_VSTRO")
    private String  cdVistoriador;

}
