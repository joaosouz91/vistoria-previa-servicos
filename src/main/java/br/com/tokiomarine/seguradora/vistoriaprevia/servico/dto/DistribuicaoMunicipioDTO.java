package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString
@Valid
public class DistribuicaoMunicipioDTO implements Serializable {

	private static final long serialVersionUID = 7306610291813521396L;

	@EqualsAndHashCode.Include
	@NotBlank(message = "Informe a UF")
	private String uf;

	@NotNull(message = "Informe uma ou mais prestadoras")
	private Set<DistribuicaoPrestadoraDTO> prestadoras;
}
