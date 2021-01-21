package br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BusinessVPException extends ResponseStatusException {

	private static final long serialVersionUID = -8597603182881801327L;
	
	private static final Logger LOGGER = LogManager.getLogger(BusinessVPException.class);

	public BusinessVPException(String message) {
		super(HttpStatus.PRECONDITION_FAILED, message);
		LOGGER.error(message);
	}

	public BusinessVPException(Throwable cause) {
		super(HttpStatus.PRECONDITION_FAILED);
		LOGGER.error(cause);
	}

	public BusinessVPException(String message, Throwable cause) {
		super(HttpStatus.PRECONDITION_FAILED, message, cause);
		LOGGER.error(message, cause);
	}
	
	@Override
	public String getMessage() {
		return super.getReason();
	}
}
