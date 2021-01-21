package br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions;

import org.springframework.validation.BindException;

public class VistoriaBindException extends BindException {

	private static final long serialVersionUID = -5599750747117140041L;

	public VistoriaBindException(Object target, String objectName) {
		super(target, objectName);
	}
	
	public void required(String field) {
		super.rejectValue(field, null, "Campo obrigatório");
	}

}
