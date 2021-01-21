package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonPluralRootName;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.StatusLaudoEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoFrustacaoEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVistoria;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;





@Data
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
@JsonRootName("laudoVp")
@JsonPluralRootName("laudoVp")
@JsonInclude(Include.NON_NULL)
public class LaudoVistoriaPreviaDTO extends ResourceSupport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	@JsonProperty("CODIGO_EMPRESA_VISTORIA")
	private Long cdAgrmtVspre;

	@JsonProperty("CODIGO_CLIENTE")
	private Long cdClien;

	@JsonProperty("CODIGO_CORRETOR_SEGURADO")
	private Long cdCrtorSegur;
	
	
	
	@JsonProperty("CODIGO_EMPRE_FTUMT_LAUDO")
	private Long cdEmpreFtumtLaudo;

	@JsonProperty("CODIGO_ENDOSSO")
	private Long cdEndos;

	@JsonProperty("CODIGO_FINALIDADE_VSPRE")
	private Long cdFnaldVspre;

	@JsonProperty("CODIGO_LOCAL_CAPTC")
	private Long cdLocalCaptc;


	@JsonProperty("CODIGO_LVPRE")
	private String cdLvpre;

	@JsonProperty("CODIGO_MOTV_FRUDO")
	private Long cdMotvFrudo;

	@JsonProperty("CODIGO_NGOCO")
	private Long cdNgoco;

	@JsonProperty("CODIGO_POSTO_VSPRE")
	private Long cdPostoVspre;

	@JsonProperty("CODIGO_SITUCAO_ANTER_VSPRE")
	private String cdSitucAnterVspre;

	@JsonProperty("CODIGO_SITUCAO_BLQUE_VSPRE")
	private Long cdSitucBlqueVspre;

	@JsonProperty("CODIGO_SITUCAO_VSPRE")
	private String cdSitucVspre;

	@JsonProperty("CODIGO_SUCSL_COMRL")
	private Long cdSucslComrl;

	@JsonProperty("CODIGO_SUPIN")
	private Long cdSupin;

	@JsonProperty("CODIGO_USURO_ULTMA_ALTER")
	private String cdUsuroUltmaAlter;

	@JsonProperty("CODIGO_VOUCH")
	private String cdVouch;

	@JsonProperty("CODIGO_VSTRI_QUALD_BLNDO")
	private String cdVstriQualdBlndo;

	@JsonProperty("CODIGO_VSTRO")
	private String cdVstro;

	@JsonProperty("DS_MOTV_RCLSF_VSPRE")
	private String dsMotvRclsfVspre;

	@JsonProperty("DS_OBSER_VSPRE")
	private String dsObserVspre;
	
	@JsonProperty("DT_FIM_SELEC")
	private Date dtFimSelec;
	
	@JsonProperty("DT_GERAC_ARQUV_FTUMT")
	private Date dtGeracArquvFtumt;
	
	@JsonProperty("DT_INCLS_RGIST")
	private Date dtInclsRgist;
	
	@JsonProperty("DT_INICO_SELEC")
	private Date dtInicoSelec;
	
	@JsonProperty("DT_LIMIT_BLQUE_VSPRE")
	private Date dtLimitBlqueVspre;
	
	@JsonProperty("DT_RCLSF_VSPRE")
	private Date dtRclsfVspre;
	
	@JsonProperty("DT_TRNSM_VSPRE")
	private Date dtTrnsmVspre;
	
	@JsonProperty("DT_ULTMA_ALTER")
	private Date dtUltmaAlter;
	
	@JsonProperty("DT_VSPRE")
	private Date dtVspre;
	
	@JsonProperty("DT_VSTRI_QUALD_BLNDO")
	private Date dtVstriQualdBlndo;

	@JsonProperty("IC_ALTER_BASCA")
	private String icAlterBasca;

	@JsonProperty("IC_ALTER_CARGA_VEICU")
	private String icAlterCargaVeicu;

	@JsonProperty("IC_ALTER_PRPNT")
	private String icAlterPrpnt;
	
	@JsonProperty("IC_ALTER_VEICU_PASEO")
	private String icAlterVeicuPaseo;

	@JsonProperty("IC_LAUDO_FTUDO")
	private String icLaudoFtudo;

	@JsonProperty("IC_LAUDO_VICDO")
	private String icLaudoVicdo;
	
	@JsonProperty("IC_VSPRE_DSNSA")
	private String icVspreDsnsa;

	@JsonProperty("IC_VSPRE_DUPLI")
	private String icVspreDupli;

	@JsonProperty("IC_VSPRE_NAO_APRVE")
	private String icVspreNaoAprve;
	
	@JsonProperty("NM_CIDADE_DESTNO_VSPRE")
	private String nmCidadDestnVspre;

	@JsonProperty("NM_CIDADE_ORIGEM_VSPRE")
	private String nmCidadOrigmVspre;

	@JsonProperty("NM_SOLTT_VSPRE")
	private String nmSolttVspre;

	@JsonProperty("NR_CEP_LOCAL_VSPRE")
	private Long nrCepLocalVspre;

	@JsonProperty("NR_ITSEG")
	private Long nrItseg;
	
	@JsonProperty("NR_VRSAO_LVPRE")
	private Long nrVrsaoLvpre;

	@JsonProperty("QT_DIAS_ATRSO_TRNSM")
	private Long qtDiasAtrsoTrnsm;

	@JsonProperty("QT_KM_RALZO")
	private Long qtKmRalzo;
	
	@JsonProperty("SG_UNIDD_FEDRC_VSPRE")
	private String sgUniddFedrcVspre;
	
	@JsonProperty("TP_BLQUE")
	private Long tpBlque;
	
	@JsonProperty("TP_GUARD_VEICU")
	private Long tpGuardVeicu;
	
	@JsonProperty("TP_HISTO_ITSEG")
	private String tpHistoItseg;

	@JsonProperty("TP_HISTO_NGOCO")
	private String tpHistoNgoco;
	
	@JsonProperty("TP_LOCAL_VSPRE")
	private String tpLocalVspre;

	@JsonProperty("TP_MOTV_VSTRI_FRUDA")
	private Long tpMotvVstriFruda;

	@JsonProperty("DESCRICAO_SITUCAO_VSPRE")
	private String getDescricaoSitucVspre() {
		return StatusLaudoEnum.obterDescricao(cdSitucVspre);
	}
	
	@JsonProperty("DESCRICAO_LOCAL_VSPRE")
	private String getDescricaoLocalVspre() {
		return TipoVistoria.obterDescricao(tpLocalVspre);
	}
	
	@JsonProperty("DESCRICAO_MOTV_VSTRI_FRUDA")
	private String getDescricaoMotvVstriFruda() {
		return TipoFrustacaoEnum.obterDescricao(tpMotvVstriFruda);
	}
}
