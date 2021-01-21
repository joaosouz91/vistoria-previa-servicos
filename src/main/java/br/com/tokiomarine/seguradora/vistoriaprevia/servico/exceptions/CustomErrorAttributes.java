package br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {

		
		Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, false);

		Throwable rootCause = ExceptionUtils.getRootCause(getError(webRequest));

		if (rootCause != null) {
			String message = rootCause.getMessage();
			errorAttributes.put("exception", rootCause.getClass().getName());
			errorAttributes.put("message", message);
		}
		
		Date date;

		// format & update timestamp
        Object timestamp = errorAttributes.get("timestamp");
        
        if (timestamp == null) {
        	date = new Date();
        } else {
            date = (Date) timestamp;
        }

		errorAttributes.put("timestamp", date.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_INSTANT));

		return errorAttributes;
	}

}
