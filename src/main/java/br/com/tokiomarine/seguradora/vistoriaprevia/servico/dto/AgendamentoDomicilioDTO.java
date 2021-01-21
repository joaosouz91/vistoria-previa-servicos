package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.PeriodoAgendamentoEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class AgendamentoDomicilioDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1040240524177038058L;

	@EqualsAndHashCode.Include
	@JsonProperty("id")
	private Long idAgendDomcl;

	@EqualsAndHashCode.Include
	@JsonProperty("voucher")
	@Size(max = 20)
	private String cdVouch;

	@JsonProperty("complemento")
	@Size(max = 50)
	private String dsCmploLogra;

	@JsonProperty("logradouroReferencia")
	@Size(max = 200)
	private String dsReferEnder;

	@JsonProperty("dataUltimaAlteracao")
	private Date dtUltmaAlter;

	@JsonProperty("dataVistoria")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-3")
	private Date dtVspre;

	@JsonProperty("dataVistoriaAnterior")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-3")
	private Date dtVspreAnter;

	@JsonProperty("periodoVistoria")
	private PeriodoAgendamentoEnum icPerioVspre;

	@JsonProperty("periodoVistoriaAnterior")
	private PeriodoAgendamentoEnum icPerioVspreAnter;

	@JsonProperty("bairro")
	@Size(max = 40)
	private String nmBairr;

	@JsonProperty("cidade")
	@Size(max = 40)
	private String nmCidad;

	@JsonProperty("logradouro")
	@Size(max = 60)
	private String nmLogra;

	@JsonProperty("cep")
	@Size(max = 8)
	private String nrCep;

	@JsonProperty("numeroLogradouro")
	@Size(max = 10)
	private String nrLogra;

	@JsonProperty("uf")
	@Size(max = 2)
	private String sgUniddFedrc;
}
