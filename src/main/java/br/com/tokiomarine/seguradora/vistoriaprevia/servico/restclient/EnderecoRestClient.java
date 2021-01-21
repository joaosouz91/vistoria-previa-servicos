package br.com.tokiomarine.seguradora.vistoriaprevia.servico.restclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.tokiomarine.seguradora.ssv.corporativa.model.EnderecoCliente;

@FeignClient(name="enderecoCliente", url="${url.servico.cotador}", path="ClienteService/rest")
public interface EnderecoRestClient {
	    
	    @GetMapping(value = "/enderecoCliente/consultarListaEndereco/{cdClien}", produces = "application/json")
	    List<EnderecoCliente> findEnderecoByCdClient(@PathVariable Long cdClien);
	    
	   
	    
}
