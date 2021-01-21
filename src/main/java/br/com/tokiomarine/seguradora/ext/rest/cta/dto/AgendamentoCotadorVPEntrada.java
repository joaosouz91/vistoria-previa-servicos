package br.com.tokiomarine.seguradora.ext.rest.cta.dto;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class AgendamentoCotadorVPEntrada implements Serializable {

	private static final long serialVersionUID = 4641916911945428185L;

	/** Campo obrigatorio. */
	@EqualsAndHashCode.Include
	private long cdCrtor;

	/** Campo obrigatorio. */
	private Long dtInclsFim;

	/** Campo obrigatorio. */
	private Long dtInclsInicio;

	private Long cdNgocoRsvdo;

	@EqualsAndHashCode.Include
	private String chassi;

	@EqualsAndHashCode.Include
	private String placa;
}