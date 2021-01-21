package br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Ocorreu um erro no servidor")
public class InternalServerException extends RuntimeException {

	private static final long serialVersionUID = -8597603182881801327L;
	
	private static final Logger LOGGER = LogManager.getLogger(InternalServerException.class);

	public InternalServerException(String message) {
		super(message);
		LOGGER.error(message);
	}

	public InternalServerException(Throwable cause) {
		super(ExceptionUtils.getRootCause(cause));
		LOGGER.error(cause);
	}

	public InternalServerException(String message, Throwable cause) {
		super(message, ExceptionUtils.getRootCause(cause));
		LOGGER.error(message, cause);
	}
	
	public InternalServerException(StringBuilder message, Throwable cause) {
		super(message.toString(), ExceptionUtils.getRootCause(cause));
		LOGGER.error(super.getMessage(), super.getCause());
	}
}
