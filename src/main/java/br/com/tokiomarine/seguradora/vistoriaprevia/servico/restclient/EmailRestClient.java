package br.com.tokiomarine.seguradora.vistoriaprevia.servico.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.EmailGNTRequest;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.EmailGNTResponse;

@FeignClient(name="emailRestClient", url="${url.servico.gnt}")
public interface EmailRestClient {

	@PostMapping("?envia=S")
	EmailGNTResponse sendEmail(@RequestBody EmailGNTRequest emailGntRequest);
}
