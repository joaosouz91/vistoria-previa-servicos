package br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomFieldError implements Serializable {

	private static final long serialVersionUID = -5774780250898356735L;

	private String field;
	private String message;

}
