package br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "Recurso já existe")
public class DuplicateResourceException extends RuntimeException {

	private static final long serialVersionUID = 7442784055964452257L;

}