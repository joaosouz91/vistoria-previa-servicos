package br.com.tokiomarine.seguradora.vistoriaprevia.servico.restclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ClientePessoa;

@FeignClient(name="clientePessoa", url="${url.servico.cotador}", path="ClienteService/rest")
public interface ClientePessoaRestClient {
	
	    @GetMapping(value = "/clientePessoa/pesquisarClientePessoaPorCpfCnpjAtivo/{nrCnpj}/{tipoPessoa}", produces = "application/json")
	    List<ClientePessoa> findClientByCnpjCpf(@PathVariable Long nrCnpj, @PathVariable String tipoPessoa);
	    
	    
	    @GetMapping(value = "/clientePessoa/pesquisarClientePessoaPorApolice/{cdClien}", produces = "application/json")
	    List<ClientePessoa> findClientByCodCliente(@PathVariable Long cdClien);
	    
}
