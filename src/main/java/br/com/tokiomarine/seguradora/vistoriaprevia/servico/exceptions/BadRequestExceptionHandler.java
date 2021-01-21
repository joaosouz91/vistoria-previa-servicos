package br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonProperty;

@ControllerAdvice
public class BadRequestExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private CustomErrorAttributes customErrorAttributes;
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		return buildResponse(ex.getBindingResult(), headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponse(ex.getBindingResult(), headers, status, request);
	}

	private ResponseEntity<Object> buildResponse(BindingResult bindingResult, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		Map<String, Object> body = customErrorAttributes.getErrorAttributes(request, false);

		body.put("status", status.value());
		body.put("error", status.getReasonPhrase());
		body.put("message", "Validation Failed");
		body.put("path", request.getDescription(false).replaceFirst("uri=", ""));

		Function<FieldError, CustomFieldError> f = e -> {

			String field = e.getField();

			try {
				Field declaredField = bindingResult.getTarget().getClass().getDeclaredField(e.getField());
				if (declaredField.isAnnotationPresent(JsonProperty.class)) {
					field = declaredField.getAnnotation(JsonProperty.class).value();
				}
			} catch (NoSuchFieldException | SecurityException e1) {
				logger.error(e1.getMessage());
			}

			return new CustomFieldError(field, e.getDefaultMessage());
		};
		
		List<CustomFieldError> errors = bindingResult.getFieldErrors().stream().map(f)
				.collect(Collectors.toList());

		body.put("violations", errors);
		
		body.remove("errors");

		return new ResponseEntity<>(body, headers, status);
	}
}
