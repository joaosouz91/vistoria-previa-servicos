package br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExternalServerException extends ResponseStatusException {

	private static final long serialVersionUID = -8597603182881801327L;
	
	private static final Logger LOGGER = LogManager.getLogger(ExternalServerException.class);

	public ExternalServerException(String message) {
		super(HttpStatus.SERVICE_UNAVAILABLE, message);
		LOGGER.error(message);
	}

	public ExternalServerException(String message, Throwable cause) {
		super(HttpStatus.SERVICE_UNAVAILABLE, message);
		LOGGER.error(message, cause);
	}
	
	@Override
	public String getMessage() {
		return super.getReason();
	}
}
