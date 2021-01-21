package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

@Entity
@SqlResultSetMappings ({
        @SqlResultSetMapping(name="EstatisticaFaturamentoPrestadoras",
                entities = {
                        @EntityResult(entityClass=EstatisticaFaturamento.class,
                                fields={
                                        @FieldResult(name="QUANTIDADE_KM",column="quantidadeKM"),
                                        @FieldResult(name="CD_LVPRE",column="cdLaudo"),
                                        @FieldResult(name="CD_EMPRE_VSTRA",column="codigoEmpresa"),
                                        @FieldResult(name="NM_EMPRE_VSTRA",column="nomeEmpresa"),
                                        @FieldResult(name="TP_LOCAL_VSPRE",column="tipoLocalVistoria"),
                                        @FieldResult(name="QT_KM_RALZO",column="quantidadeKmRealizado")
                                }
                        )
                }
        ),
        @SqlResultSetMapping(name="EstatisticaFaturamentoFranquias",
                entities = {
                        @EntityResult(entityClass=EstatisticaFaturamento.class,
                                fields={
                                        @FieldResult(name="QUANTIDADE_KM",column="quantidadeKM"),
                                        @FieldResult(name="CD_LVPRE",column="cdLaudo"),
                                        @FieldResult(name="CD_EMPRE_VSTRA",column="codigoEmpresa"),
                                        @FieldResult(name="NM_EMPRE_VSTRA",column="nomeEmpresa"),
                                        @FieldResult(name="TP_LOCAL_VSPRE",column="tipoLocalVistoria"),
                                        @FieldResult(name="QT_KM_RALZO",column="quantidadeKmRealizado")
                                }
                        )
                }
        )
})

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class EstatisticaFaturamento implements Serializable {

    @Column(name="QUANTIDADE_KM")
    private Long quantidadeKM;

    @Id
    @Column(name="CD_LVPRE")
    private String cdLaudo;

    @Id
    @Column(name="CD_EMPRE_VSTRA")
    private Long codigoEmpresa;

    @Column(name="NM_EMPRE_VSTRA")
    private String nomeEmpresa;

    @Column(name="TP_LOCAL_VSPRE")
    private String tipoLocalVistoria;

    @Column(name="QT_KM_RALZO")
    private Long quantidadeKmRealizado;

    private Long quantidadeDistKmPrimeiro;
    private Long quantidadeDistKmSegundo;
    private Long quantidadeDistKmTerceiro;
    private Long quantidadeDistKmQuarto;

    private Long quantidadeDomicilio;
    private Long quantidadePosto;
    private Long quantidadeMobile;

    private Long totalQuantidadeDistKmPrimeiro;
    private Long totalQuantidadeDistKmSegundo;
    private Long totalQuantidadeDistKmTerceiro;
    private Long totalQuantidadeDistKmQuarto;

    private Long totalQuantidadeDomicilio;
    private Long totalQuantidadePosto;
    private Long totalQuantidadeMobile;

    private Long totalGeral;

    public EstatisticaFaturamento(Long quantidadeKM, String cdLaudo, Long codigoEmpresa,
                                  String nomeEmpresa, String tipoLocalVistoria, Long quantidadeKmRealizado){
        this.quantidadeKM = quantidadeKM;
        this.cdLaudo = cdLaudo;
        this.codigoEmpresa = codigoEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.tipoLocalVistoria = tipoLocalVistoria;
        this.quantidadeKmRealizado = quantidadeKmRealizado;
    }


}
