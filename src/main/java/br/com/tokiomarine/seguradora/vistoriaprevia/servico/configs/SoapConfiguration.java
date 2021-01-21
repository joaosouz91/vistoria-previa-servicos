package br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import br.com.tokiomarine.seguradora.ext.soap.MapServiceClient;

@Configuration
public class SoapConfiguration {
	
	@Bean
	public Jaxb2Marshaller mapserviceMarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("br.com.tokiomarine.seguradora.ext.soap.mapservice");
		return marshaller;
	}

	@Bean
	public MapServiceClient mapServiceClient(Jaxb2Marshaller mapserviceMarshaller) {
		MapServiceClient client = new MapServiceClient();
		client.setMarshaller(mapserviceMarshaller);
		client.setUnmarshaller(mapserviceMarshaller);
		return client;
	}
}
