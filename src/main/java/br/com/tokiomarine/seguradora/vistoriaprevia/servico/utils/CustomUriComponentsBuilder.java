package br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils;

import org.springframework.web.util.UriComponentsBuilder;

public class CustomUriComponentsBuilder extends UriComponentsBuilder {

	@Override
	public UriComponentsBuilder path(String path) {
		return super.path(path);
	}
	
}
