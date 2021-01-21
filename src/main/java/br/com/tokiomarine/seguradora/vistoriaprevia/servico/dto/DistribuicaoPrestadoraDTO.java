package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;

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
public class DistribuicaoPrestadoraDTO implements Serializable {

	private static final long serialVersionUID = 7514644338936744183L;

	@EqualsAndHashCode.Include
	@NotNull(message = "Informe a prestadora")
	private Long codigo;

	@NotNull(message = "Informe o percentual de distribuição")
	private Double percentual;
}
