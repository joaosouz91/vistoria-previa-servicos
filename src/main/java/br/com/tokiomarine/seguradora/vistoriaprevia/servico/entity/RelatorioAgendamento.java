package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.Corretor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
@SqlResultSetMapping(
        name = "HistoricoAgendamento",
        entities = 	{
                @EntityResult( entityClass = RelatorioAgendamento.class, fields = {
                        @FieldResult( name = "cdVouch", column = "CD_VOUCH" ),
                        @FieldResult( name = "cdVouchAnter", column = "CD_VOUCH_ANTER" ),
                        @FieldResult( name = "dtUltmaAlterAgto", column = "DT_ULTMA_ALTER_AGTO" ),
                        @FieldResult( name = "tpVspre", column = "TP_VSPRE" ),

                        @FieldResult( name = "idStatuAgmto", column = "ID_STATU_AGMTO" ),
                        @FieldResult( name = "cdSitucAgmto", column = "CD_SITUC_AGMTO" ),
                        @FieldResult( name = "cdMotvSitucAgmto", column = "CD_MOTV_SITUC_AGMTO" ),
                        @FieldResult( name = "dsMotvSitucAgmto", column = "DS_MOTV_SITUC_AGMTO" ),
                        @FieldResult( name = "dsMotvVstriFruda", column = "DS_MOTV_VSTRI_FRUDA" ),
                        @FieldResult( name = "dtUltmaAlterStatus", column = "DT_ULTMA_ALTER_STATUS" ),

                        @FieldResult( name = "idVspreObgta", column = "ID_VSPRE_OBGTA" ),
                        @FieldResult( name = "cdCrtorCia", column = "CD_CRTOR_CIA" ),
                        @FieldResult( name = "nmCrtorCia", column = "NM_CRTOR_CIA" ),
                        @FieldResult( name = "cdPlacaVeicu", column = "CD_PLACA_VEICU" ),
                        @FieldResult( name = "cdChassiVeicu", column = "CD_CHASSI_VEICU" ),
                        @FieldResult( name = "nmClien", column = "NM_CLIEN" ),
                        @FieldResult( name = "nrCpfCnpjClien", column = "NR_CPF_CNPJ_CLIEN" ),

                        @FieldResult( name = "cdAgrmtVspre", column = "CD_AGRMT_VSPRE" ),
                        @FieldResult( name = "nmRazaoSocialPrta", column = "NM_RAZAO_SOCIAL_PRTA" ),

                        @FieldResult( name = "dtVspre", column = "DT_VSPRE" ),
                        @FieldResult( name = "icPerioVspre", column = "IC_PERIO_VSPRE"),

                        @FieldResult( name = "cdUsuroUltmaAlter", column = "CD_USURO_ULTMA_ALTER")
                })
        })
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioAgendamento implements Serializable {

    //* Vpe0425_agend_vspre
    @Column(name = "CD_VOUCH")
    @Id
    private String cdVouch;

    @Column(name = "CD_VOUCH_ANTER")
    private String cdVouchAnter;

    @Transient
    private String cdVouchPosterior;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_ULTMA_ALTER_AGTO")
    private Date dtUltmaAlterAgto;

    @Column(name="TP_VSPRE", nullable=false)
    private String tpVspre;

    //* Vpe0437_statu_agmto
    @Column(name = "ID_STATU_AGMTO")
    @Id
    private Long idStatuAgmto;

    @Column(name = "CD_SITUC_AGMTO")
    private String cdSitucAgmto;

    @Transient
    private String dsSitucAgend;

    @Column(name = "CD_MOTV_SITUC_AGMTO")
    private Long cdMotvSitucAgmto;

    @Column(name = "DS_MOTV_VSTRI_FRUDA")
    private String dsMotvVstriFruda;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_ULTMA_ALTER_STATUS")
    private Date dtUltmaAlterStatus;

    //* Ssv4001_cntdo_colun_tipo(Desc.Motivo)
    @Column(name = "DS_MOTV_SITUC_AGMTO")
    private String dsMotvSitucAgmto;

    //* Vpe0424_vspre_obgta
    @Column(name = "ID_VSPRE_OBGTA")
    private Long idVspreObgta;

    @Column(name = "CD_CRTOR_CIA")
    private Long cdCrtorCia;

    @Transient
    private Corretor corretor;

    @Column(name = "NM_CRTOR_CIA")
    private String nmCrtorCia;

    @Column(name = "NR_CPF_CNPJ_CLIEN")
    private String nrCpfCnpjClien;

    @Column(name = "NM_CLIEN")
    private String nmClien;

    @Column(name = "CD_PLACA_VEICU")
    private String cdPlacaVeicu;

    @Column(name = "CD_CHASSI_VEICU")
    private String cdChassiVeicu;

    //* Vpe0230_prtra_vspre
    @Column(name = "CD_AGRMT_VSPRE")
    private Long cdAgrmtVspre;

    @Column(name = "NM_RAZAO_SOCIAL_PRTA")
    private String nmRazaoSocialPrta;

    //* VPE0426_AGEND_DOMCL
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DT_VSPRE", nullable=false)
    private Date dtVspre;

    @Column(name="IC_PERIO_VSPRE", nullable=false)
    private String icPerioVspre;

    @Column(name="CD_USURO_ULTMA_ALTER", nullable=false)
    private String cdUsuroUltmaAlter;

    public String getCodNomeCorretor() {

        if (this.getCdCrtorCia() != null && !this.getCdCrtorCia().equals(0L)) {
            return this.getCdCrtorCia().toString().concat(" - ").concat(this.getNmCrtorCia());
        } else {
            return "NÃO ENCONTRADO";
        }
    }


}
