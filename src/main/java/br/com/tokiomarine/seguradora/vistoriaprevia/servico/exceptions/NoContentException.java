package br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoContentException extends ResponseStatusException {

	private static final long serialVersionUID = 7442784055964452257L;

	private static final Logger LOGGER = LogManager.getLogger(NoContentException.class);

	public NoContentException() {
		super(HttpStatus.NO_CONTENT, "Dados não encontrados");
	}

	public NoContentException(String message) {
		super(HttpStatus.NO_CONTENT, message);
		LOGGER.error(message);
	}

	public NoContentException(Throwable cause) {
		super(HttpStatus.NO_CONTENT, "Dados não encontrados");
		LOGGER.error(cause);
	}

	public NoContentException(String message, Throwable cause) {
		super(HttpStatus.NO_CONTENT, message, cause);
		LOGGER.error(message, cause);
	}
	
	@Override
	public String getMessage() {
		return super.getReason();
	}
}