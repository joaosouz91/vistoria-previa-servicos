package br.com.tokiomarine.seguradora.ext.soap;

import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.tokiomarine.seguradora.ext.soap.mapservice.MapServiceUserPrcDbmRecLogradouroComplOut;
import br.com.tokiomarine.seguradora.ext.soap.mapservice.PrcDbmRecLogradouroComplElement;
import br.com.tokiomarine.seguradora.ext.soap.mapservice.PrcDbmRecLogradouroComplResponseElement;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.ParametroVPService;

public class MapServiceClient extends WebServiceGatewaySupport {

	@Autowired
	private ParametroVPService parametroVPService;

	private static final Logger LOG = LogManager.getLogger(MapServiceClient.class);

	public Optional<MapServiceUserPrcDbmRecLogradouroComplOut> obterLocalidadePorCEP(String cep) {
		StringBuilder logMsg = new StringBuilder("[MapService] Inicio da consulta \r\n CEP: " + cep);

		try {
			
			String servidorMap = parametroVPService.obterServidorMap();

			PrcDbmRecLogradouroComplElement request = new PrcDbmRecLogradouroComplElement();
			request.setInCepLogradourop(cep);

			PrcDbmRecLogradouroComplResponseElement response = (PrcDbmRecLogradouroComplResponseElement) getWebServiceTemplate()
					.marshalSendAndReceive(servidorMap + "/MapService/MapServiceSoapHttpPort", request);

			MapServiceUserPrcDbmRecLogradouroComplOut result = response.getResult();
			
			if (result.getOutretornopOut().equals("98")) {
				logMsg.append("Codigo de retorno 98 \r\n");
				logMsg.append("response -> " + new ObjectMapper().writeValueAsString(result) + " \r\n");
				LOG.info(logMsg);
				
				return Optional.empty();
			}
			
			return Optional.of(result);

		} catch (Exception e) {
			logMsg.append("Erro ao consultar MapService -> " + ExceptionUtils.getRootCause(e).getMessage());
			LOG.error(logMsg);
			
			throw new InternalServerException(e);
		}
	}
}
