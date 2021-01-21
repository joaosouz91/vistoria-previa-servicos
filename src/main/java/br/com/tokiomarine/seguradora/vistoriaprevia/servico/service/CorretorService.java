package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.AmbienteEnum.PRODUCAO;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.aceitacao.rest.client.AcselXRestClient;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.ConsultaCorretorResponse;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.ConsultaCorretoresResponse;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.ConsultaEmailCorretorResponse;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.PO_Reg;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoTelefoneDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Corretor;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.ExternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;

@Component
public class CorretorService {

	@Autowired
	private AcselXRestClient acselXRestClient;
	
	@Value("${tokiomarine.infra.AMBIENTE}")
	private String ambiente;
	
	private static final Logger LOGGER = LogManager.getLogger(VistoriaPreviaObrigatoriaService.class);
	
	public List<Corretor> buscarListaCorretores(final Corretor corretor){
		if(corretor != null){
			try{
				
				Long idCorretor = corretor.getIdCorretor();
				
				if (idCorretor != null) {
					Optional<Corretor> opCorretor = obterCorretor(idCorretor);
					if (opCorretor.isPresent()) {
						return Arrays.asList(opCorretor.get());
					}
				} else {
				
					ConsultaCorretoresResponse consultaCorretores = acselXRestClient.consultaCorretores(null, corretor.getNomeCorretor());
					
					if (consultaCorretores != null && consultaCorretores.getTab_cor() != null) {
						
						return consultaCorretores.getTab_cor().stream().map(c -> {
							Corretor cc = new Corretor();
							cc.setIdCorretor(Long.valueOf(c.getCodinterno()));
							cc.setNomeCorretor(c.getNomter());	
							return cc;
						}).collect(Collectors.toList());
					}
				}
			} catch (Exception e) {			
				LOGGER.info("Erro buscar corretor pelo codigo e/ou nome, serviço acselXRestClient.consultaCorretores /n", e.getMessage());
			}
		}
		
		return Collections.emptyList();
	}

	/**
	 * Consulta os dados do corretor através do serviço acselXRestClient.
	 * @param idCorretor
	 * @throws InternalServerException
	 * @return Optional{@code<Corretor>} com id e nome do corretor
	 */
	public Optional<Corretor> obterCorretor(Long idCorretor) {
		try {
			ConsultaCorretorResponse consultaCorretorResponse = acselXRestClient.consultaCorretor(idCorretor);

			if (consultaCorretorResponse != null && !consultaCorretorResponse.getPo_reg().isEmpty()) {

				PO_Reg response = consultaCorretorResponse.getPo_reg().get(0);
				
				Corretor novoCorretor = new Corretor();
				novoCorretor.setIdCorretor(idCorretor);
				novoCorretor.setNomeCorretor(response.getNomter());
				novoCorretor.setCodSucursal(response.getCodsucursal());

				String email = response.getEmail();

				if (isNotBlank(email) && PRODUCAO.getValue().equals(ambiente)) {
					novoCorretor.setEmails(Arrays.asList(email));
				}

				BigDecimal ddd = response.getDddtelef1();
				BigDecimal telefone = response.getNumtelef1();
				
				if (ddd != null || telefone != null) {
					AgendamentoTelefoneDTO agendamentoTelefone = new AgendamentoTelefoneDTO();
					agendamentoTelefone.setCdDddTelef(ddd != null ? ddd.toPlainString(): "");
					agendamentoTelefone.setNrTelef(telefone != null ? telefone.toPlainString() : "");
					novoCorretor.setTelefones(Arrays.asList(agendamentoTelefone));
				}
				
				return Optional.of(novoCorretor);
			}

			return Optional.empty();
		} catch (Exception e) {
			throw new ExternalServerException("Erro ao consultar dados cadastrais do Corretor: " + idCorretor , e);
		}
	}
	
	public String obterNomeCorretor(Long idCorretor) {
		try {
			ConsultaCorretorResponse consultaCorretorResponse = acselXRestClient.consultaCorretor(idCorretor);

			if (consultaCorretorResponse != null && !consultaCorretorResponse.getPo_reg().isEmpty()) {

				return consultaCorretorResponse.getPo_reg().get(0).getNomter();
			}

			return null;
		} catch (Exception e) {
			throw new ExternalServerException("Erro ao consultar dados cadastrais do Corretor: " + idCorretor , e);
		}
	}
	
	public List<String> obterListEmail(Long idCorretor) {
		
		ConsultaEmailCorretorResponse consultaEmailCorretorResponse = acselXRestClient.consultaEmailVistoriaPrevia(idCorretor);
		List<String>  emails = new ArrayList<>();
		
		for (PO_Reg pO_Reg : consultaEmailCorretorResponse.getPo_reg()) {
			emails.add(pO_Reg.getEmail());

		}
				
		return emails;	
		
	}
	
	public List<Corretor> buscarListaCorretores(String tipo, String valor){
		Corretor c = new Corretor();
		
		if ("C".equalsIgnoreCase(tipo)) {
			if (NumberUtils.isCreatable(valor)) {
				c.setIdCorretor(Long.valueOf(valor));
			} else {
				throw new BusinessVPException("Informe um valor numérico.");
			}
		} else {
			c.setNomeCorretor(valor);
		}
		
		return buscarListaCorretores(c);
	}
}