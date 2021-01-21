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

/**
 * Expõe as funcionalidades de negócio que envolvem a entidade AtrasoTransmissaoAgrupamento.
 *
 * @author rrogulski
 * @version 1.1
 */
@Entity
@SqlResultSetMapping(name="AtrasoTransmissaoAgrupamento",
        entities = {
                @EntityResult(entityClass=AtrasoTransmissaoAgrupamento.class,
                        fields={
                                @FieldResult(name="CD_LVPRE",column="codigoLaudo"),
                                @FieldResult(name="qt_dias_atrso_trnsm",column="codigoLaudo"),
                                @FieldResult(name="CD_AGRMT_VSPRE",column="codigoEmpresa"),
                                @FieldResult(name="NM_RAZAO_SOCAL",column="nomeEmpresa"),
                                @FieldResult(name="DT_TRNSM_VSPRE",column="dataTransmissao"),
                                @FieldResult(name="QT_DIAS_ATRSO_TRNSM",column="quantidadeDiasAtraso")

                        }
                )
        })
@SuppressWarnings("serial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtrasoTransmissaoAgrupamento implements Serializable {

    private Long quantDiaPrimeiroRankEmissaoRelatorio = 0L;
    private Long quantDiaSegundoRankEmissaoRelatorio  = 0L;
    private Long quantDiaTerceiroRankEmissaoRelatorio = 0L;
    private Long quantDiaQuartoRankEmissaoRelatorio   = 0L;
    private Long quantDiaQuintoRankEmissaoRelatorio   = 0L;
    private Long quantUltimoRankEmissaoRelatorio      = 0L;

    private Long quantTotalPrimeiroRankEmissaoRelatorio = 0L;
    private Long quantTotalSegundoRankEmissaoRelatorio  = 0L;
    private Long quantTotalTerceiroRankEmissaoRelatorio = 0L;
    private Long quantTotalQuartoRankEmissaoRelatorio   = 0L;
    private Long quantTotalQuintoRankEmissaoRelatorio   = 0L;
    private Long quantTotalUltimoRankEmissaoRelatorio   = 0L;

    private double porcentagemPrimeiroRankEmissaoRelatorio = 0.0d;
    private double porcentagemSegundoRankEmissaoRelatorio  = 0.0d;
    private double porcentagemTerceiroRankEmissaoRelatorio = 0.0d;
    private double porcentagemQuartoRankEmissaoRelatorio   = 0.0d;
    private double porcentagemQuintoRankEmissaoRelatorio   = 0.0d;
    private double porcentagemUltimoRankEmissaoRelatorio   = 0.0d;

    private Long totalGeral = 0L;

    @Id
    @Column(name="CD_LVPRE")
    private String codigoLaudo;

    @Id
    @Column(name="CD_AGRMT_VSPRE")
    private Long codigoEmpresa;

    @Column(name="NM_RAZAO_SOCAL")
    private String nomeEmpresa;

    @Column(name="DT_TRNSM_VSPRE")
    @Temporal(TemporalType.DATE)
    private Date dataTransmissao;

    @Column(name="DT_VSPRE")
    @Temporal(TemporalType.DATE)
    private Date dataVistoria;

    @Column(name="QUANTIDADE")
    private Long quantidade;

    @Column(name="QT_DIAS_ATRSO_TRNSM")
    private Long quantidadeDiasAtraso;


    public AtrasoTransmissaoAgrupamento(String codigoLaudo, Long codigoEmpresa, String nomeEmpresa, Date dataTransmissao, Date dataVistoria, Long quantidadeDiasAtraso) {
        this.codigoLaudo = codigoLaudo;
        this.codigoEmpresa = codigoEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.dataTransmissao = dataTransmissao;
        this.dataVistoria = dataVistoria;
        this.quantidadeDiasAtraso = quantidadeDiasAtraso;
    }

}