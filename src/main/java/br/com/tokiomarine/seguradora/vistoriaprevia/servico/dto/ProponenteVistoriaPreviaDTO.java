package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonPluralRootName;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.EstadoCivilEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
@JsonRootName("proponente")
@JsonPluralRootName("proponente")
@JsonInclude(Include.NON_NULL)
public class ProponenteVistoriaPreviaDTO  extends ResourceSupport implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	@JsonProperty("CD_CNH_CODUT")
	private String cdCnhCodut;

	@JsonProperty("CD_CNH_PRPNT")
	private String cdCnhPrpnt;


	@JsonProperty("CD_LVPRE")
	private String cdLvpre;

	@JsonProperty("DS_ATIVD_PROFL_PRPNT")
	private String dsAtivdProflPrpnt;

	@JsonProperty("DS_ENDER")
	private String dsEnder;

	@JsonProperty("DT_FIM_SELEC")
	private Date dtFimSelec;

	@JsonProperty("DT_INICO_SELEC")
	private Date dtInicoSelec;

	@JsonProperty("DT_NASCM_CODUT")
	private Date dtNascmCodut;

	@JsonProperty("DT_NASCM_PRPNT")
	private Date dtNascmPrpnt;

	@JsonProperty("DT_PRMRA_HBLTC_CODUT")
	private Date dtPrmraHbltcCodut;

	@JsonProperty("DT_PRMRA_HBLTC_PRPNT")
	private Date dtPrmraHbltcPrpnt;

	@JsonProperty("NM_BAIRR")
	private String nmBairr;

	@JsonProperty("NM_CIDAD")
	private String nmCidad;

	@JsonProperty("NM_CODUT")
	private String nmCodut;

	@JsonProperty("NM_FONTC")
	private String nmFontc;

	@JsonProperty("NM_PRPNT")
	private String nmPrpnt;

	@JsonProperty("NR_CEP")
	private Long nrCep;

	@JsonProperty("NR_CNPJ_PRPNT")
	private Long nrCnpjPrpnt;

	@JsonProperty("NR_CPF_CODUT")
	private Long nrCpfCodut;

	@JsonProperty("NR_CPF_PRPNT")
	private Long nrCpfPrpnt;

	@JsonProperty("NR_TELEF_PRPNT")
	private String nrTelefPrpnt;

	@JsonProperty("NR_VRSAO_LVPRE")
	private Long nrVrsaoLvpre;

	@JsonProperty("SG_UNIDD_FEDRC")
	private String sgUniddFedrc;

	@JsonProperty("SG_UNIDD_FEDRC_CNH_PRPNT")
	private String sgUniddFedrcCnhPrpnt;

	@JsonProperty("TP_CODUT_VEICU")
	private Long tpCodutVeicu;

	@JsonProperty("TP_ESTAD_CIVIL_CODUT") 
	private String tpEstadCivilCodut;

	@JsonProperty("TP_ESTAD_CIVIL_PRPNT")
	private String tpEstadCivilPrpnt;

	@JsonProperty("TP_SEXO_PRPNT")
	private String tpSexoPrpnt;
	
	@JsonProperty("DESCRICAO_ESTAD_CIVIL_PRPNT")
	private String getDescricaoEstadCivilPrpnt() {
		return EstadoCivilEnum.obterDescricao(tpEstadCivilPrpnt);
	}
	
	@JsonProperty("DESCRICAO_ESTAD_CIVIL_CODUT")
	private String getDescricaoEstadCivilCodut() {
		return EstadoCivilEnum.obterDescricao(tpEstadCivilCodut);
	}
}
