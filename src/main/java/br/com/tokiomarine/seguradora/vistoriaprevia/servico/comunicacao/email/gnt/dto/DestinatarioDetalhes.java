package br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class DestinatarioDetalhes {

	@EqualsAndHashCode.Include
	private String destino;
	
	private String cpfCnpj;
}
