package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

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
@EqualsAndHashCode
public class NegocioComponentDto implements Serializable {
		
	private static final long serialVersionUID = -4784199097450015246L;
	
	private Long codigoProposta;	 
	private Long numeroItemSegurado;
}