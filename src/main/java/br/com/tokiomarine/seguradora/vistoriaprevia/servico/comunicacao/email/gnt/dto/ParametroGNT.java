package br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ParametroGNT {

	@EqualsAndHashCode.Include
	private String nomeParametro;
	
	private String valorParametro;
	
}
