package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@JsonInclude(Include.NON_NULL)
public class AgendamentoTelefoneDTO implements Serializable {

	private static final long serialVersionUID = -317507693587422342L;

	@JsonProperty("voucher")
	@Size(max = 20)
	private String cdVouch;
	
	@JsonProperty("ddd")
	@Size(max = 4)
	private String cdDddTelef;

	@JsonProperty("contato")
	@Size(max = 100)
	private String nmConttVspre;

	@JsonProperty("ramal")
	@Size(max = 5)
	private String nrRamal;

	@JsonProperty("telefone")
	@Size(max = 12)
	private String nrTelef;

	@JsonProperty("tipo")
	@Size(max = 1)
	private String tpTelef;

	@JsonProperty("tipoContato")
	@Size(max = 1)
	private String tpConttTelef;
	
	public boolean isTelefoneAtendimento() {

		return (isNotBlank(nrTelef)
				&& (nrTelef.startsWith("0300") || nrTelef.startsWith("0500") || nrTelef.startsWith("0800") || nrTelef.startsWith("0900")));
			 		
	}
}
