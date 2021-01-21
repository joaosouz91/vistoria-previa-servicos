package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonPluralRootName;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
@JsonRootName("itemSegurado")
@JsonPluralRootName("itemSegurado")
@JsonInclude(Include.NON_NULL)
public class ItemSeguradoDTO  extends ResourceSupport implements Serializable{/**

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	  @JsonProperty("CD_AGATV")
	  private java.lang.String codAgrupamentoAtividade;
	  

	  @JsonProperty("CD_ALCAD_LIBEC_VSPRE")
	  private java.lang.Long codAlcadaLiberacaoVistoriaPrevia;
	  

	  @JsonProperty("CD_ANALIS_RETRN_CRIVO")
	  private java.lang.String cdAnalisRetrnCrivo;
	  

	  @JsonProperty("CD_APOLI")
	  private java.lang.Long codApolice;
	  

	  @JsonProperty("CD_APOLI_SUSEP_RENOV")
	  private java.lang.Long codApoliceSusepRenovacao;
	  
	
	  @JsonProperty("CD_CHASSI_VEICU")
	  private java.lang.String codChassiVeiculo;
	  

	  @JsonProperty("CD_CIA_SGDRA_RENOV")
	  private java.lang.Long codCompanhiaSeguradoraRenovacao;
	  
	
	  @JsonProperty("CD_CLIEN")
	  private java.lang.Long codCliente;
	  

	  @JsonProperty("CD_COFMC_BONUS_TKMAR")
	  private java.lang.String cdCofmcBonusTkmar;
	  

	  @JsonProperty("CD_ENDOS")
	  private java.lang.Long codEndosso;

	  @JsonProperty("CD_IDETC_CONGR")
	  private java.lang.String cdIdetcCongr;
	  

	  @JsonProperty("CD_IMOVL")
	  private java.lang.String cdImovl;
	  

	  @JsonProperty("CD_INCON_VSPRE")
	  private java.lang.String codInconsistenciaVistoriaPrevia;
	  

	  @JsonProperty("CD_LOTE_EMISS")
	  private java.lang.String cdLoteEmiss;
	  

	  @JsonProperty("CD_LVPRE")
	  private java.lang.String codLaudoVistoriaPrevia;
	  

	  @JsonProperty("CD_MDUPR")
	  private java.lang.Long codModuloProduto;
	  

	  @JsonProperty("CD_MOTV_SCORE_CRIVO")
	  private java.lang.String cdMotvScoreCrivo;
	  

	  @JsonProperty("CD_MOTV_VSPRE_OBGTA")
	  private java.lang.Long cdMotvVspreObgta;
	  

	  @JsonProperty("CD_NGOCO")
	  private java.lang.Long codNegocio;
	  

	  @JsonProperty("CD_NGOCO_ANTER")
	  private java.lang.Long codNegocioAnterior;

	  @JsonProperty("CD_PLACA_VEICU")
	  private java.lang.String codPlacaVeiculo;
	  

	  @JsonProperty("CD_POTCL")
	  private java.lang.Long cdPotcl;
	  

	  @JsonProperty("CD_PROCS_SUSEP")
	  private java.lang.String cdProcsSusep;
	  

	  @JsonProperty("CD_RAMO_PRCPA_RENOV")
	  private java.lang.Long codRamoPrincipalRenovacao;
	  

	  @JsonProperty("CD_RETRN_CRIVO")
	  private java.lang.Long cdRetrnCrivo;
	  

	  @JsonProperty("CD_SITUC_NGOCO")
	  private java.lang.String codSituacaoNegocio;
	  

	  @JsonProperty("CD_SITUC_VSPRE")
	  private java.lang.String codSituacaoVistoriaPrevia;
	  

	  @JsonProperty("CD_TABEL_COTAC")
	  private java.lang.Long codTabelaCotacao;
	  

	  @JsonProperty("DS_MENSG_VSPRE")
	  private java.lang.String dsMensgVspre;
	  

	  @JsonProperty("DS_VARVL_POLIT_FONTE_EXTNA")
	  private java.lang.String dsVarvlPolitFonteExtna;
	  

	  @JsonProperty("DT_AVISO_SINST_PT_AUTO")
	  private java.util.Date dataAvisoSinistroPerdaTotalAuto;
	  

	  @JsonProperty("DT_BASE_CALLO_ITEM")
	  private java.util.Date dataBaseCalculoItem;
	  

	  @JsonProperty("DT_CALLO_PROPL")
	  private java.util.Date dataCalculoProporcional;

	  @JsonProperty("DT_CANCL_PARCL")
	  private java.util.Date dataCancelamentoParcela;
	  

	  @JsonProperty("DT_ENDOS_QBR")
	  private java.util.Date dtEndosQbr;
	  

	  @JsonProperty("DT_FIM_VIGEN_ITSEG")
	  private java.util.Date dtFimVigenItseg;
	  

	  @JsonProperty("DT_FIM_VIGEN_PROPL")
	  private java.util.Date dataFimVigenciaProporcional;

	  
	  @JsonProperty("DT_INICO_VIGEN_ITSEG")
	  private java.util.Date dtInicoVigenItseg;
	  

	  @JsonProperty("DT_INSUM")
	  private java.util.Date dtInsum;
	  

	  @JsonProperty("DT_LIMIT_CONCL_VSPRE")
	  private java.util.Date dataLimiteConciliacaoVistoriaPrevia;
	  

	  @JsonProperty("DT_MAXMA_CONDC_RENOV_ATEDA")
	  private java.util.Date dtMaxmaCondcRenovAteda;
	  

	  @JsonProperty("DT_PARTM")
	  private java.util.Date dtPartm;
	  

	  @JsonProperty("DT_PRMRO_CALLO")
	  private java.util.Date dtPrmroCallo;
	  

	  @JsonProperty("DT_ULTMA_ALTER")
	  private java.util.Date dataUltimaAlteracao;
	  

	  @JsonProperty("DT_ULTMO_CALLO")
	  private java.util.Date dtUltmoCallo;
	  

	  @JsonProperty("DT_VRSAO_COMPN_CALLO")
	  private java.util.Date dataVersaoComponenteCalculo;
	  

	  @JsonProperty("IC_APLCC_RAJUS_RENOV")
	  private java.lang.String icAplccRajusRenov;
	  

	  @JsonProperty("IC_ATZDA_MIGRC")
	  private java.lang.String icAtzdaMigrc;
	  

	  @JsonProperty("IC_CNTRC_CASCO")
	  private java.lang.String indContratacaoCasco;
	  

	  @JsonProperty("IC_DECOD_CHASSI")
	  private java.lang.String icDecodChassi;
	  

	  @JsonProperty("IC_DIVEG_CEP_LOCAL_RISCO")

	  private java.lang.String indDivergenciaCepLocalRisco;
	  

	  @JsonProperty("IC_PPOTA_OPORT")
	  private java.lang.String icPpotaOport;
	  

	  @JsonProperty("IC_PRMIO_BATEN")
	  private java.lang.String icPrmioBaten;
	  

	  @JsonProperty("IC_SINST_ANTER_CRIVO")
	  private java.lang.String icSinstAnterCrivo;
	  

	  @JsonProperty("IC_SUGST_TOKIO")
	  private java.lang.String icSugstTokio;
	  

	  @JsonProperty("IC_VSPRE")
	  private java.lang.String icVspre;
	  

	  @JsonProperty("ID_VEICU")
	  private java.lang.Long idVeicu;
	  

	  @JsonProperty("NM_COMPN_CALLO")
	  private java.lang.String nomeComponenteCalculo;
	  

	  @JsonProperty("NM_COMPN_SUBCR")
	  private java.lang.String nmCompnSubcr;
	  

	  @JsonProperty("NR_BONUS_ITRNO")
	  private java.lang.Long nrBonusItrno;
	  

	  @JsonProperty("NR_IDADE_SEGUR")
	  private java.lang.Long numIdadeSeguro;

}
