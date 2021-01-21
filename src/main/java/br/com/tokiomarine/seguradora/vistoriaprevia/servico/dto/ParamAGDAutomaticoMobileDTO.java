package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Set;

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
public class ParamAGDAutomaticoMobileDTO implements Serializable {

	private static final long serialVersionUID = 7242585737768398522L;

	@EqualsAndHashCode.Include
	private Long prestadoraMobile;
	
	private Set<Long> corretores;
	
}