package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil implements Serializable {

	private static final long serialVersionUID = 9095170030322722841L;

	@Autowired
    private MessageSource messageSource;
	
	public String get(String code) {
		return messageSource.getMessage(code, null, Locale.getDefault());
	}

	public String get(String code, Object... args) {
		return messageSource.getMessage(code, args, Locale.getDefault());
	}
}
