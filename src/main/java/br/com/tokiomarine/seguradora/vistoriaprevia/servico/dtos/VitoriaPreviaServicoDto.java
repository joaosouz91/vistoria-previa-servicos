package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

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
@EqualsAndHashCode
public class VitoriaPreviaServicoDto implements Serializable {
		
	private static final long serialVersionUID = -5638131569771515539L;
		
	@NotNull(message="Número da placa não pode ser nulo!")
	private String numeroPlaca;
		
	@NotNull(message="Código do corretor não pode ser nulo!")
	private Long codigoCorretor;
		
	@NotNull(message="Código do chassi não pode ser nulo!")
	private String codigoChassi;
}
