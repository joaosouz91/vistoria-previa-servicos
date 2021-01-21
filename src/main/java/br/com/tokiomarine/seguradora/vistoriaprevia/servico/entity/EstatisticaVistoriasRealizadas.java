package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@SqlResultSetMappings ({
        @SqlResultSetMapping(name="EstatisticaVistoriasRealizadas",
            classes = {
                @ConstructorResult(targetClass = EstatisticaVistoriasRealizadas.class,
                    columns = {
                        @ColumnResult(name = "CD_AGRMT_VSPRE"),
                        @ColumnResult(name = "CD_LVPRE"),
                        @ColumnResult(name = "IC_LAUDO_VICDO"),
                        @ColumnResult(name = "CD_SITUC_BLQUE_VSPRE"),
                        @ColumnResult(name = "CD_SITUC_VSPRE")
                    }
                )
            }
        ),
        @SqlResultSetMapping(name="DetalhamentoEstatisticasVistoriasRealizadas",
                entities = {
                        @EntityResult(entityClass=EstatisticaVistoriasRealizadas.class,
                                fields={
                                        @FieldResult(name="CD_LVPRE ",column="codigoLaudo"),
                                        @FieldResult(name="CD_PLACA_VEICU ",column="numeroPlaca"),
                                        @FieldResult(name="CD_CHASSI_VEICU",column="numeroChassi"),
                                        @FieldResult(name="DT_VSPRE",column="dataRealizacao"),
                                        @FieldResult(name="DT_TRNSM_VSPRE ",column="dataTransmissao"),
                                        @FieldResult(name="CD_SITUC_BLQUE_VSPRE",column="situacaoBloqueio"),
                                        @FieldResult(name="IC_LAUDO_VICDO",column="indicadorLaudoVinculado"),
                                        @FieldResult(name="CD_SITUC_VSPRE",column="situacaoLaudo"),
                                        @FieldResult(name="NM_CIDAD_ORIGM_VSPRE",column="cidadeOrigem"),
                                        @FieldResult(name="NM_CIDAD_DESTN_VSPRE",column="cidadeDestino"),
                                        @FieldResult(name="CD_VOUCH",column="numVoucher"),
                                        @FieldResult(name="SG_UNIDD_FEDRC_VSPRE",column="uf")
                                }
                        )
                }
        )
})
@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticaVistoriasRealizadas implements Serializable {

    @Column(name="CD_AGRMT_VSPRE")
    private Long codigoPrestadora;

    private String nomePrestadora;

    @Column(name="CD_EMPRE_VSTRA")
    private Long codigoEmpresa;

    @Id
    @Column(name="CD_LVPRE")
    private String codigoLaudo;

    @Column(name="NR_ITSEG")
    private Long numeroItem;

    @Column(name="CD_SITUC_BLQUE_VSPRE")
    private Long situacaoBloqueio;

    @Column(name="CD_SITUC_VSPRE")
    private String situacaoLaudo;

    @Column(name="IC_LAUDO_VICDO")
    private String indicadorLaudoVinculado;

    private Long quantLaudosBloqueados    = 0L;
    private Long quantLaudosNaoVinculados = 0L;
    private Long quantLaudosVinculados    = 0L;

    private Long totalLaudosBloqueados;
    private Long totalLaudosNaoVinculados;
    private Long totalLaudosVinculados;

    private Long totalSituacaoLaudo;

    private Long quantAceitavel        = 0L;
    private Long quantSujeitoAnalise   = 0L;
    private Long quantRecusados        = 0L;
    private Long quantLiberado         = 0L;
    private Long quantAceitacaoForcada = 0L;
    private Long quantRegularizado = 0L;
    private Long totalAceitavel;
    private Long totalSujeitoAnalise;
    private Long totalRecusados;
    private Long totalLiberado;
    private Long totalAceitacaoForcada;
    private Long totalRegularizado;

    private Long totalStatusLaudo;

    @Column(name="CD_PLACA_VEICU")
    private String numeroPlaca;

    @Column(name="CD_CHASSI_VEICU")
    private String numeroChassi;

    @Column(name="DT_VSPRE")
    @Temporal(TemporalType.DATE)
    private Date dataRealizacao;

    @Column(name="DT_TRNSM_VSPRE")
    @Temporal(TemporalType.DATE)
    private Date dataTransmissao;

    private String statusVistoria;

    @Column(name="NM_CIDAD_ORIGM_VSPRE")
    private String cidadeOrigem;

    @Column(name="NM_CIDAD_DESTN_VSPRE")
    private String cidadeDestino;

    @Column(name="CD_VOUCH")
    private String  numVoucher;

    @Column(name="SG_UNIDD_FEDRC_VSPRE")
    private String  uf;

    public EstatisticaVistoriasRealizadas (Long codigoPrestadora, String codigoLaudo,
                                           String indicadorLaudoVinculado, Long situacaoBloqueio, String situacaoLaudo) {
        this.codigoPrestadora = codigoPrestadora;
        this.codigoLaudo = codigoLaudo;
        this.indicadorLaudoVinculado = indicadorLaudoVinculado;
        this.situacaoBloqueio = situacaoBloqueio;
        this.situacaoLaudo = situacaoLaudo;
    }

    public EstatisticaVistoriasRealizadas (String codigoLaudo, String numeroPlaca,
                                           String numeroChassi, Date dataRealizacao, Date dataTransmissao, Long situacaoBloqueio, String indicadorLaudoVinculado,
                                           String situacaoLaudo, String cidadeOrigem, String cidadeDestino, String numVoucher, String uf) {
        this.codigoLaudo = codigoLaudo;
        this.numeroPlaca = numeroPlaca;
        this.numeroChassi = numeroChassi;
        this.dataRealizacao = dataRealizacao;
        this.dataTransmissao = dataTransmissao;
        this.situacaoBloqueio = situacaoBloqueio;
        this.indicadorLaudoVinculado = indicadorLaudoVinculado;
        this.situacaoLaudo = situacaoLaudo;
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
        this.numVoucher = numVoucher;
        this.uf = uf;
    }

}

