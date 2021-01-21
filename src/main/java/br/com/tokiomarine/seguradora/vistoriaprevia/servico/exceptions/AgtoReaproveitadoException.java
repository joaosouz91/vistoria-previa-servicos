package br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions;

public class AgtoReaproveitadoException extends BusinessVPException {

	private static final long serialVersionUID = -8597603182881801327L;
	
	public AgtoReaproveitadoException(String message) {
		super(message);
	}

	public AgtoReaproveitadoException(Throwable cause) {
		super(cause);
	}

	public AgtoReaproveitadoException(String message, Throwable cause) {
		super(message, cause);
	}
}
