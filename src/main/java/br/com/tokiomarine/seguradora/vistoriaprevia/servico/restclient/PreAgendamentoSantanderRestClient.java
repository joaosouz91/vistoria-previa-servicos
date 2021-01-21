package br.com.tokiomarine.seguradora.vistoriaprevia.servico.restclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name="santanderCliente", url="${url.servico.cotador}", path="/ConsultasCotadorService")
public interface PreAgendamentoSantanderRestClient {
	    
	    @GetMapping(value = "/consulta/cotacao/consultaPreAgendamentoSantander/{codigoCotacao}", produces = "application/json", consumes = "application/json")
	    ResponseEntity<String> consultaPreAgendamentoSantander(@PathVariable Long codigoCotacao);
	    ///ConsultasCotadorService/consulta/cotacao/consultaPreAgendamentoSantander/301135201
	   
	    @GetMapping(value = "/consulta/cotacao/consultaPreAgendamentoSantanderAutoCompara/{codigoCotacao}", produces = "application/json", consumes = "application/json")
	    ResponseEntity<String> consultaPreAgendamentoSantanderAutoCompara(@PathVariable String codigoCotacao);
	    ///ConsultasCotadorService/consulta/cotacao/consultaPreAgendamentoSantanderAutoCompara/301135201
	    
}
