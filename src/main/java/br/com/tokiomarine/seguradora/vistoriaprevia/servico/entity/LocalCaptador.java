package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LocalCaptador implements Serializable {

	private static final long serialVersionUID = 3967464237426584396L;
	
	@EqualsAndHashCode.Include
	private Long cdLocalCaptador;
    private String dsLocalCaptador;
    private String cdCentroCusto;
}
