package br.com.tokiomarine.seguradora.ext.rest.cta;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.tokiomarine.seguradora.ext.rest.cta.dto.AgendamentoCotadorVP;
import br.com.tokiomarine.seguradora.ext.rest.cta.dto.AgendamentoCotadorVPEntrada;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.MessageUtil;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.ParametroVistoriaPreviaGeralService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesVistoriaPrevia;

@Service
public class CTAService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private ParametroVistoriaPreviaGeralService parametroService;

	public List<AgendamentoCotadorVP> pesquisaCotacaoNoCTA(VistoriaFiltro filtro) {
		try {
			String urlRestCotador = parametroService
					.findByPorNome(ConstantesVistoriaPrevia.PARAM_URL_CONSULTA_COTACAO_CTA);

			Date dataFimPesquisa = Date.from(LocalDate.now().minusDays(90).atStartOfDay(ZoneId.systemDefault()).toInstant());

			AgendamentoCotadorVPEntrada agendamentoCotadorVP = new AgendamentoCotadorVPEntrada();
			agendamentoCotadorVP.setCdCrtor(filtro.getCorretor());
			agendamentoCotadorVP.setChassi(filtro.getChassi());
			agendamentoCotadorVP.setPlaca(StringUtils.defaultIfBlank(filtro.getPlaca(), null));
			agendamentoCotadorVP.setDtInclsInicio(dataFimPesquisa.getTime());
			agendamentoCotadorVP.setDtInclsFim(new Date().getTime());

			RequestEntity<AgendamentoCotadorVPEntrada> request = RequestEntity.post(new URI(urlRestCotador)).accept(MediaType.APPLICATION_JSON)
					.body(agendamentoCotadorVP);
			
			ResponseEntity<AgendamentoCotadorVP[]> response = restTemplate.exchange(request,
					AgendamentoCotadorVP[].class);

			if (response.getStatusCode() != HttpStatus.OK) {
				throw new InternalServerException(
						"Erro ao tentar acessar o serviço de consultas no CTA " + response.getStatusCodeValue());
			}

			return Arrays.asList(response.getBody());

		} catch (Exception e) {

			throw new InternalServerException(messageUtil.get("erro.pre.consulta.cotacao.cta"));

		}
	}
}
