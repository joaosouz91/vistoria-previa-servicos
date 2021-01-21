package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

import br.com.tokiomarine.seguradora.aceitacao.rest.client.AcselXRestClient;
import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto.DestinatarioDetalhes;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto.EmailGNT;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto.ParametroGNT;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto.RastreiaEnvioGNT;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto.TipoEnvioGNT;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.service.GNTService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoDomicilioDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoMobileCancelamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaObrigatoriaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.AmbienteEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.AgendamentoVistoriaPreviaRepository;

@Service
public class CancelamentoAutomaticoService {

	private static final String EMAIL_CANCELAMENTO_SICREDI = "EMAIL_CANCELAMENTO_SICREDI";

	private static final String DATA_SIMULACAO_TESTE_EMAIL = "DATA_SIMULACAO_TESTE_EMAIL";

	private static final String PROC_VIS = "PROC_VIS";

	@Autowired
	private AgendamentoVistoriaPreviaRepository repository;
	
	@Autowired
	private StatusAgendamentoService statusAgendamentoService;
	
	@Autowired
	private AgendamentoContatoTelefoneService agendamentoContatoTelefoneService;
	
	@Autowired
	private GNTService gnt;
	
	@Autowired
	private ParametroVistoriaPreviaGeralService parametroService;
	
	@Autowired
	private AcselXRestClient acselXRestClient;
	
	@Value("${tokiomarine.infra.AMBIENTE}")
	private String ambiente;
	
	@Transactional(rollbackFor = Exception.class)
	public void cancelarSicrediMobile() {
		cancelarMobile();
		cancelarDomicilioSicredi();
	}

	private void cancelarDomicilioSicredi() {
		
		Date dataDe = obterDataCancelamentoSicredi();
		
		Date dataAte = null;
		
		// Se data de consulta for sexta-feira, inclui sábado e domingo
		if (DateUtil.getDiaDaSemana(dataDe) == 6) {
			dataAte = DateUtils.addDays(dataDe, 3);
		}
		
		repository.findAgendamentosDomicilioSicrediAGD(dataDe, dataAte)
		.stream()
		.forEach(a -> {
			statusAgendamentoService.cancelarAgendamento(a.getCdVouch(), PROC_VIS);
			EmailGNT emailGNT = buildEmailSicredi(a);
			gnt.enviar(emailGNT);
		});
	}
	
	public void cancelarDomicilioSicredi(String voucher, boolean cancelar) {
		repository.findAgendamentoDomicilioSicredi(voucher)
		.ifPresent(a -> {
			
			if (cancelar) {
				statusAgendamentoService.cancelarAgendamento(a.getCdVouch(), PROC_VIS);
			}
			
			EmailGNT emailGNT = buildEmailSicredi(a);
			gnt.enviar(emailGNT);
		});
	}

	private Date obterDataCancelamentoSicredi() {
		try {
			Date dataProcessamento;
			
			if (!ambiente.equalsIgnoreCase(AmbienteEnum.PRODUCAO.getValue())) {
				String dataParametro = parametroService.findByPorNome(DATA_SIMULACAO_TESTE_EMAIL);
				dataProcessamento = DateUtil.parseData(dataParametro);
			
			} else {
				dataProcessamento = new Date();
			}
			
			dataProcessamento = acselXRestClient.calcularDiasUteis(dataProcessamento, 1L, -1L);
			
			return dataProcessamento;
		
		} catch (Exception e) {
			throw new InternalServerException("Erro obterDataCancelamentoSicredi", e);
		}
	}

	private void cancelarMobile() {
		repository.findAgendamentosMobile()
		.stream()
		.map(o -> new AgendamentoMobileCancelamentoDTO(Objects.toString(o[0], null), Objects.toString(o[1], null)))
		.forEach(a -> {
			
			if ("LIB".equals(a.getSituacaoNegocio())) {
				statusAgendamentoService.salvar(a.getCdVouch(), SituacaoAgendamento.RLZ, PROC_VIS);
				
			} else if ("REC".equals(a.getSituacaoNegocio())) {
				statusAgendamentoService.salvar(a.getCdVouch(), SituacaoAgendamento.CAN, PROC_VIS, 2L);
			}
		});
	}

	private EmailGNT buildEmailSicredi(AgendamentoDTO a) {
		String emailCancelamento = parametroService.findByPorNome(EMAIL_CANCELAMENTO_SICREDI);
		
		VistoriaObrigatoriaDTO vistoria = a.getVistoria();
		AgendamentoDomicilioDTO domicilio = a.getAgendamentoDomicilio();

		EmailGNT emailGNT = new EmailGNT();
		
		emailGNT.setCodigo("EMACTPROP0013");
		emailGNT.setDestinatariosDetalhes(Sets.newHashSet(new DestinatarioDetalhes(emailCancelamento, vistoria.getNrCpfCnpjClien())));
		emailGNT.setRastreiaEnvio(RastreiaEnvioGNT.N);
		emailGNT.setTipoEnvio(TipoEnvioGNT.FORNECEDOR);

		emailGNT.setParametros(
			Sets.newHashSet(
				new ParametroGNT("NOME_SEGURADO", vistoria.getNmClien()),
				new ParametroGNT("CPF_CNPJ_SEGURADO", vistoria.getNrCpfCnpjClien()),
				new ParametroGNT("TIPO_VISTORIA", "MOBILE"),
				new ParametroGNT("POSTO_VISTORIA", "MOBILE APP AUTO"),
				new ParametroGNT("DT_SOLICITACAO", DateUtil.formataData(domicilio.getDtVspre())),
				new ParametroGNT("ENDERECO", getEndereco(domicilio)),
				new ParametroGNT("BAIRRO", domicilio.getNmBairr()),
				new ParametroGNT("CIDADE", domicilio.getNmCidad()),
				new ParametroGNT("TELEFONE", getTelefone(a.getCdVouch())),
				new ParametroGNT("UF", domicilio.getSgUniddFedrc()),
				new ParametroGNT("NOME_PRODUTO", vistoria.getTpVeicu().getDescricao()),
				new ParametroGNT("FABRICANTE", vistoria.getNmFabrt()),
				new ParametroGNT("MODELO_VEICULO", vistoria.getDsModelVeicu()),
				new ParametroGNT("COD_FIPE", vistoria.getCdFipe()),
				new ParametroGNT("PLACA", vistoria.getCdPlacaVeicu()),
				new ParametroGNT("CHASSI", vistoria.getCdChassiVeicu()),
				new ParametroGNT("ANO_FABRICACAO", vistoria.getAaFabrc()),
				new ParametroGNT("ANO_VEICULO", vistoria.getAaModel()),
				new ParametroGNT("ZERO_KM", vistoria.getIcVeicuZeroKm().equals("S")? "Sim" : "Não"),
				new ParametroGNT("COD_NEGOCIO", Objects.toString(vistoria.getCdNgoco(), "")),
				new ParametroGNT("CD_ENDOSSO", Objects.toString(vistoria.getCdEndos(), "0")),
				new ParametroGNT("NOME_PARCEIRO", "CONTROL EXPERT")
			)
		);
		
		return emailGNT;
	}

	private String getTelefone(String cdVouch) {
		return agendamentoContatoTelefoneService.findUltimoTelefoneSegurado(cdVouch)
				.map(t -> new StringBuilder()
						.append("(")
						.append(t.getCdDddTelef())
						.append(") ")
						.append(t.getNrTelef())
						.toString())
				.orElse(null);
	}

	private String getEndereco(AgendamentoDomicilioDTO domicilio) {
		StringBuilder str = new StringBuilder();
		str.append(domicilio.getNmLogra());
		
		if (StringUtils.isNotBlank(domicilio.getNrLogra())) {
			str.append(", ");
			str.append(domicilio.getNrLogra());
		}

		if (StringUtils.isNotBlank(domicilio.getNrCep())) {
			str.append(" - ");
			
			String cep = StringUtils.leftPad(domicilio.getNrCep(), 8, '0');
			str.append(StringUtils.left(cep, 5));
			str.append("-");
			str.append(StringUtils.right(cep, 3));
		}
		
		if (StringUtils.isNotBlank(domicilio.getDsCmploLogra())) {
			str.append(" (");
			str.append(domicilio.getDsCmploLogra());
			str.append(")");
		}
		
		return  str.toString();
	}
	
}
