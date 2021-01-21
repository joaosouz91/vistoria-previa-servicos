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

@Entity
@SqlResultSetMappings({
        @SqlResultSetMapping(name="DetalhamentoAtrasoTransmissaoTotal",
                entities = {
                        @EntityResult(entityClass=DetalhamentoAtrasoTransmissaoTotal.class,
                                fields={
                                        @FieldResult(name="CD_LVPRE",column="codigoLaudo"),
                                        @FieldResult(name="CD_EMPRE_VSTRA",column="codigoEmpresa"),
                                        @FieldResult(name="NM_EMPRE_VSTRA",column="nomeEmpresa"),
                                        @FieldResult(name="DT_TRNSM_VSPRE",column="dataTransmissao"),
                                        @FieldResult(name="DT_VSPRE",column="dataVistoria"),
                                        @FieldResult(name="QT_DIAS_ATRSO_TRNSM",column="atrasoDias")
                                }
                        )
                }
        ),
        @SqlResultSetMapping(name="DetalhamentoAtrasoTransmissaoDashboard",
                entities = {
                        @EntityResult(entityClass=DetalhamentoAtrasoTransmissaoTotal.class,
                                fields={
                                        @FieldResult(name="CD_LVPRE",column="codigoLaudo"),
                                        @FieldResult(name="CD_AGRMT_VSPRE",column="codigoPrestadora"),
                                        @FieldResult(name="CD_EMPRE_VSTRA",column="codigoEmpresa"),
                                        @FieldResult(name="NM_EMPRE_VSTRA",column="nomeEmpresa"),
                                        @FieldResult(name="MES",column="mes"),
                                        @FieldResult(name="QUANT_DIAS",column="quantidadeDias"),
                                        @FieldResult(name="QT_DIAS_ATRSO_TRNSM",column="atrasoDias")
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
public class DetalhamentoAtrasoTransmissaoTotal implements Serializable, Comparable {

    @Id
    @Column(name="CD_LVPRE")
    private String codigoLaudo;

    @Column(name="CD_AGRMT_VSPRE")
    private Long codigoPrestadora;

    @Column(name="CD_EMPRE_VSTRA")
    private String codigoEmpresa;

    @Column(name="NM_EMPRE_VSTRA")
    private String nomeEmpresa;

    @Column(name="DT_TRNSM_VSPRE")
    @Temporal(TemporalType.DATE)
    private Date dataTransmissao;

    @Column(name="DT_VSPRE")
    @Temporal(TemporalType.DATE)
    private Date dataVistoria;

    @Column(name="MES")
    private Long mes;

    @Column(name="QT_DIAS_ATRSO_TRNSM")
    private Long atrasoDias;

    private Long quantidadeAteDois   = 0L;
    private Long quantidadeAteTres   = 0L;
    private Long quantidadeMaiorTres = 0L;
    private Long totalRegistros;

    private double porcentagemAteDois;
    private double porcentagemAteTres;
    private double porcentagemMaiorTres;

    private Long totalAteDoisDias   = 0L;
    private Long totalAteTresDias   = 0L;;
    private Long totalMaiorTresDias = 0L;;
    private Long totalGeral         = 0L;;
    private double totalPorcentagemAteDoisDias;
    private double totalPorcentagemAteTresDias;
    private double totalPorcentagemMaiorTresDias;

    public DetalhamentoAtrasoTransmissaoTotal(String codigoLaudo, Long codigoPrestadora,
                                              String nomeEmpresa, Date dataTransmissao, Date dataVistoria, Long atrasoDias) {
        this.codigoLaudo = codigoLaudo;
        this.codigoPrestadora = codigoPrestadora;
        this.nomeEmpresa = nomeEmpresa;
        this.dataTransmissao = dataTransmissao;
        this.dataVistoria = dataVistoria;
        this.atrasoDias = atrasoDias;
    }

    /*
    *                   "L.CD_LVPRE, " );
        sql.append(     "E.CD_AGRMT_VSPRE, " );
        sql.append(     "E.NM_EMPRE_VSTRA, " );
        sql.append(     "L.DT_TRNSM_VSPRE, " );
        sql.append(     "L.DT_VSPRE, " );
        sql.append(     "L.QT_DIAS_ATRSO_TRNSM ");
    * */

    public int compareTo(Object o) {
        DetalhamentoAtrasoTransmissaoTotal bb = (DetalhamentoAtrasoTransmissaoTotal ) o;
        // A < B  RETORNA NEGATIVO
        if (Integer.parseInt(this.getCodigoEmpresa()) <= Integer.parseInt( bb.getCodigoEmpresa()))
            return -1;
        else
            return 1;
    }
}

