package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ext.soap.MapServiceClient;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.LogradouroDTO;

@Service
public class MapService {

	@Autowired
	private MapServiceClient client;
	
	public Optional<LogradouroDTO> obterLogradouro(String cep) {
		return client.obterLocalidadePorCEP(cep)
				.map(l -> LogradouroDTO.builder()
							.cep(cep)
							.logradouro(trimToNull(l.getOutlogradouro30POut()))
							.bairro(trimToNull(l.getOutbairropOut()))
							.cidade(obterCidade(l.getOutlocalidadepOut()))
							.uf(trimToNull(l.getOutufpOut()))
							.build());
	}
	
	private String obterCidade(String cidade) {
		if (StringUtils.contains(cidade, "(")) {
			return StringUtils.trimToNull(StringUtils.substringBetween(cidade, "(", ")"));
		}
		
		return StringUtils.trimToNull(cidade);
	}
}
