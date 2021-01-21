package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonPluralRootName;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.StatusParecerEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldNameConstants
@JsonRootName("ParecerTecnicoVistoriaPrevia")
@JsonPluralRootName("ParecerTecnicoVistoriaPrevia")
@JsonInclude(Include.NON_NULL)
public class ParecerTecnicoDTO implements Serializable {

	private static final long serialVersionUID = 7810760348379455099L;

	@EqualsAndHashCode.Include
	@JsonProperty("CD_PATEC_VSPRE")
	private Long cdPatecVspre;

	@JsonProperty("CD_SITUC_PATEC")
	private String cdSitucPatec;

	@JsonProperty("CD_CLASF_PATEC")
	private StatusParecerEnum cdClasfPatec;

	@JsonProperty("DS_INFOR_RECUS_ANALIS")
	private String dsInforRecusAnalis;

	@JsonProperty("DS_PATEC_VSPRE")
	private String dsPatecVspre;

	@JsonProperty("DT_FIM_SELEC")
	private Date dtFimSelec;

	@JsonProperty("DT_INICO_SELEC")
	private Date dtInicoSelec;

}
