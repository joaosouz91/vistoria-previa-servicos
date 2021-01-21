package br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

	private static final long serialVersionUID = -5763764941053144372L;
	private static final String MENSSAGEM = "Recurso não encontrado";


	public NotFoundException() {
		super(HttpStatus.NOT_FOUND, MENSSAGEM);
	}

	public NotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message == null ? MENSSAGEM : message);
	}

	public NotFoundException(Throwable cause) {
		super(HttpStatus.NOT_FOUND);
	}

	public NotFoundException(String message, Throwable cause) {
		super(HttpStatus.NOT_FOUND, message == null ? MENSSAGEM : message, cause);
	}
	
	@Override
	public String getMessage() {
		return super.getReason();
	}
}