package br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto.DestinatarioDetalhes;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto.EmailGNT;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto.EmailGNTResponse;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.AmbienteEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.ParametroVistoriaPreviaGeralService;

@Service
public class GNTService {

	private static Logger logger = LogManager.getLogger(GNTService.class);

	private static final String URL_SERVICO_GNT = "URL_SERVICO_GNT";
	private static final String EMAILS_MONITORACAO_ERROS_INESPERADOS = "EMAILS_MONITORACAO_ERROS_INESPERADOS";

	@Value("${tokiomarine.infra.AMBIENTE}")
	private String ambiente;

	@Autowired
	private ParametroVistoriaPreviaGeralService parametroService;

	@Autowired
	private RestTemplate restTemplate;

	public void enviar(EmailGNT emailGNT) {

		try {

			if (!ambiente.equalsIgnoreCase(AmbienteEnum.PRODUCAO.getValue())) {
				emailGNT.setDestinatariosDetalhes(null);

				Set<DestinatarioDetalhes> emailsMonitoracao = Arrays
						.stream(parametroService.findByPorNome(EMAILS_MONITORACAO_ERROS_INESPERADOS).split(","))
						.map(e -> new DestinatarioDetalhes(e, null)).collect(Collectors.toSet());

				emailGNT.setDestinatariosDetalhes(emailsMonitoracao);

			}

			validar(emailGNT);

			String urlWServico = parametroService.findByPorNome(URL_SERVICO_GNT);
			urlWServico = StringUtils.appendIfMissingIgnoreCase(urlWServico, "?envia=S");

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<EmailGNT> requestEntity = new HttpEntity<>(emailGNT, headers);

			ResponseEntity<EmailGNTResponse> emailGNTResponse = restTemplate.exchange(urlWServico, HttpMethod.POST,
					requestEntity, EmailGNTResponse.class);

			if (emailGNTResponse.getBody() != null
					&& emailGNTResponse.getBody().getCodigoRetorno() != EmailGNTResponse.RETORNO_SUCESSO) {
				throw new InternalServerException(emailGNTResponse.getBody().getMensagemRetorno());
			}
			
		} catch (Exception e) {
			throw new InternalServerException("[GNT] request: " + new Gson().toJson(emailGNT), e);
		}
	}

	private void validar(EmailGNT emailGNT) {
		StringBuilder camposInvalidos = new StringBuilder();
		if (null == emailGNT.getCodigo()) {
			camposInvalidos.append(" Codigo;");
		}

		if (null == emailGNT.getDestinatariosDetalhes()
				|| (null != emailGNT.getDestinatariosDetalhes() && emailGNT.getDestinatariosDetalhes().isEmpty())) {
			camposInvalidos.append(" Destinatario;");
		} else {
			for (DestinatarioDetalhes destinatarioDetalhes : emailGNT.getDestinatariosDetalhes()) {
				if (null == destinatarioDetalhes.getDestino())
					camposInvalidos.append(" Destinatario;");
			}
		}

		if (null == emailGNT.getTipoEnvio()) {
			camposInvalidos.append(" Tipo Envio;");
		}

		if (null == emailGNT.getRastreiaEnvio()) {
			camposInvalidos.append(" Rastreia Envio;");
		}

		if (!"".equals(camposInvalidos.toString())) {
			logger.error("Problemas ao enviar email, campos inválidos: " + camposInvalidos);
			throw new InternalServerException("Problemas ao enviar email, campos inválidos: " + camposInvalidos);
		}
	}
}
