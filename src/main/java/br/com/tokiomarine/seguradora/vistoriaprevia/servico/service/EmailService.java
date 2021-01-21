package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import br.com.tokiomarine.infra.componente.email.dto.EmailHTML;
import br.com.tokiomarine.infra.componente.email.dto.EmailTexto;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.ParametroRegrasVP;
import br.com.tokiomarine.seguradora.core.exception.ExceptionUtil;
import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.ext.ssv.repository.DescricaoItemSeguradoRepository;
import br.com.tokiomarine.seguradora.ext.ssv.service.ConteudoColunaTipoService;
import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.ValorCaracteristicaItemSegurado;
import br.com.tokiomarine.seguradora.ssv.transacional.model.DescricaoItemSegurado;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPreviaGeral;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PrestadoraVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto.RastreiaEnvioGNT;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto.TipoEnvioGNT;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ConteudoColunaTipoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Corretor;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.DestinatarioDetalhes;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.EmailGNTRequest;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.EmailGNTResponse;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ParametroGNT;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ParametroGNTBuilder;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.AmbienteEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BadRequestException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.AgendamentoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ParametroVistoriaPreviaGeralRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.PrestadoraVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ValorCaracteristicaItemSeguradoRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.restclient.EmailRestClient;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilJava;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilNegocio;

@Service
public class EmailService {
	
	private static final Logger LOGGER = LogManager.getLogger(EmailService.class);
	
	@Value("${tokiomarine.infra.AMBIENTE}")
	private String ambiente;

	@Autowired
	private AgendamentoVistoriaPreviaRepository agendamentoVistoriaPreviaRepository;
	
	@Autowired
	private PrestadoraVistoriaPreviaRepository prestadoraVistoriaPreviaRepository;
	
	@Autowired
	private ParametroVistoriaPreviaGeralRepository parametroVistoriaPreviaGeralRepository;
	
	@Autowired
	private DescricaoItemSeguradoRepository descricaoItemSeguradoRepository;
	
	@Autowired 
	private ValorCaracteristicaItemSeguradoRepository valorCaracteristicaItemSeguradoRepository;	
	
	@Autowired
	private EmailRestClient emailRestClient;
	
	@Autowired
	private CorretorService corretorService;
	
	@Autowired
	private VistoriaPreviaObrigatoriaService vistoriaPreviaObrigatoriaService;
	
	@Autowired
	private ConteudoColunaTipoService conteudoColunaTipoService;
	
	@Autowired
	private AgendamentoContatoEmailService agendamentoContatoEmailService;
	
	@Autowired
	private AgendamentoContatoTelefoneService agendamentoContatoTelefoneService;
	
	public static final String CORPO_EMAIL_AGTO_RECUSADO =
		    " <html>" +
		    " 		<body>" +
		    "		    <br>" +
		    "			<table style='font-family: Arial;font-size: 10pt;'>" +
		    "		    	<tr><td>" +
		    "		    		<b>São Paulo, #DATA_ATUAL </b>" +
		    "		    	</td></tr>" +
		    "		    </table>" +
		    "			<br>" +
		    "			<table style='font-family: Arial;font-size: 10pt;'>" +
		    "		    	<tr><td>" +
		    "			 		<b>Prezado Corretor</b> #NOME_CORRETOR," +
		    "		    	</td></tr>" +
		    "		    </table>" +
		    "		    <br>" +
		    "		    <table style='font-family: Arial;font-size: 10pt;'>" +
		    "				<tr><td><b>Proponente:</b> #NOME_CLIENTE </td></tr>" +
		    "				<tr><td><b>Item Segurado:</b> #DESC_VEICULO </td></tr>" +
		    "				<tr><td><b>Chassi:</b> #NUMERO_CHASSI </td></tr>" +
		    "				<tr><td><b>Placa:</b> #NUMERO_PLACA </td></tr>" +
		    "				<tr><td><b>Voucher:</b> #NUMERO_VOUCHER </td></tr>" +
		    "				<tr><td><b>Motivo:</b> #MOTIVO_RECUSA </td></tr>" +
		    "			</table>" +
		    "		    <br>" +
		    "		    <table style='font-family: Arial;font-size: 10pt;'>" +
		    "		    	<tr><td>" +
		    "		     		O agendamento de vistoria prévia foi recusado pelo prestador ou sua realização frustrada." +
		    "		    		<br><br>" +
		    "		     		Favor reagendar esta vistoria no portal do corretor pelo caminho (Produtos - Individual - Agendamento/Vistoria – Agendar Vistoria Prévia)." +
		    "		    		<br><br>" +
		    "		     		<b>NOTA:</b> A cobertura iniciar-se-á após a realização da vistoria prévia." +
		    "		    		<br><br>" +
		    "		     		Em caso da não aceitação a seguradora se manifestará por escrito." +
		    "		    		<br><br>" +
		    "		     		Atenciosamente," +
		    "		    		<br><br>" +
		    "		     		TOKIO MARINE SEGURADORA S.A." +
		    "		    	</td></tr>" +
		    "		    </table>" +
		    " 		</body>" +
		    " </html>";
	
	public void enviarEmailPrestadora(String numeroVoucher, String tituloEmail, String corpoEmail) {
		
		//* Carrega e-mail de contato da pretadora
		String emailPrestadora = null;

		AgendamentoVistoriaPrevia agendamento = agendamentoVistoriaPreviaRepository.findAgendamentoPorVoucher(numeroVoucher).orElse(new AgendamentoVistoriaPrevia());		
		PrestadoraVistoriaPrevia prestadora = prestadoraVistoriaPreviaRepository.findByIdPrestadoraVistoriaPrevia(agendamento.getCdAgrmtVspre());

		if (!StringUtils.isEmpty(prestadora.getCdEmail())) {
			emailPrestadora = prestadora.getCdEmail();
		}

		List<String> listEmailDestino = null;

		if (emailPrestadora != null) {
			listEmailDestino = new ArrayList<>();
			listEmailDestino.add(emailPrestadora);
		}

		try {
			
			if(AmbienteEnum.PRODUCAO.getValue().equals(ambiente)){
				EmailHTML email = new EmailHTML(tituloEmail, corpoEmail, "sistemas@tokiomarine.com.br", listEmailDestino);
				email.enviar();				
			}

		} catch (Exception e) {
			LOGGER.error("Erro metodo enviarEmailPrestadora: voucher - " + numeroVoucher + " <br>\n" + e.getMessage());
			throw new InternalServerException(e);
		}

	}
	
	public void enviarEmailCorretorAgendamento(ParametroRegrasVP parametroRegrasVP) {
		StringBuilder message = new StringBuilder().append("Inicio do processo deenvio de email. /r/n");
		message.append(parametroRegrasVP.toString());
		String titulo;
		String assunto = "ASSUNTO";

		titulo = "Notificação de obrigatoriedade de VP ";
		
		Corretor corretor = corretorService.obterCorretor(parametroRegrasVP.getCodCorretor()).orElse(new Corretor());
		corretor.setEmails(corretorService.obterListEmail(parametroRegrasVP.getCodCorretor()));
		ParametroVistoriaPreviaGeral parametroVistoriaPreviaGeral = parametroVistoriaPreviaGeralRepository.findOneByNmParamVspre(ConstantesVistoriaPrevia.PARAM_TEMPLATE_EMAIL_PENDENCIA_VISTORIA_PREVIA_GNT).orElse(null);
		String codModeloTemplate = parametroVistoriaPreviaGeral != null ? parametroVistoriaPreviaGeral.getVlParamVspre() : "";

		EmailGNTRequest emailGntRequest = new EmailGNTRequest(codModeloTemplate,
																null,
																TipoEnvioGNT.CORRETOR,
																getParametros(parametroRegrasVP),
																RastreiaEnvioGNT.N,
																null,
																null);

		if(AmbienteEnum.PRODUCAO.getValue().equals(ambiente)) {
			if (UtilJava.trueVar(corretor)) {
				emailGntRequest.setDestinatariosDetalhes(addListaDestinatario(emailGntRequest, corretor));
			} else {
				titulo += "Corretor " + parametroRegrasVP.getCodCorretor() + " sem e-mail de contato cadastrado. ";
				emailGntRequest.addDestinatario("operacoes.vp@tokiomarine.com.br", null);
			}
			emailGntRequest.addDestinatario("notificacao.vp@tokiomarine.com.br", parametroRegrasVP.getCpfCnpj())
							.addOrReplaceParam("Tokio Marine - "+titulo, assunto)
							.addOrReplaceParam(String.valueOf(parametroRegrasVP.getCodCorretor()), "CD_CORRETOR");

			try {	
				message.append("/r/n enviando email para: " + emailGntRequest.toString());
				LOGGER.info(message.toString());
				EmailGNTResponse emailGNTResponse = emailRestClient.sendEmail(emailGntRequest);
			    if(emailGNTResponse.getCodigoRetorno() == EmailGNTResponse.RETORNO_SUCESSO) {
		           String mensagemEnvioEmail = "Email enviado com sucesso pelo serviço do GNT! SeqAgendamento: " + emailGNTResponse.getSeqAgendamento() + ".Destinatários: " + emailGntRequest.getDestinatariosDetalhes() + ". CopiaOculta: " + emailGntRequest.getComCopias();
		           LOGGER.info(mensagemEnvioEmail, parametroRegrasVP.getNumItem().toString(), "EmailVP", null, null);
			   }
			} catch (Exception e) {

				LOGGER.error(
						"Erro ao enviar email de agendamento do retorno de bônus. Email corretor: " + 
									getDestinatarios(emailGntRequest.getDestinatariosDetalhes()) + " <br>\n" + ExceptionUtil.stackToString(e),
						parametroRegrasVP.getNumItem().toString(),
						ConstantesVistoriaPrevia.LOG_ERRO_GPA,
						null,
						null);
			}
		}

		 else {
			try {
				emailGntRequest.addOrReplaceParam("Tokio Marine - " + titulo, assunto)
							.addOrReplaceParam(String.valueOf(parametroRegrasVP.getCodCorretor()), "CD_CORRETOR");
				EmailGNTResponse emailGNTResponse = enviarEmailGNTMonitoracao(emailGntRequest,parametroRegrasVP.getCpfCnpj());
			    if(emailGNTResponse.getCodigoRetorno() == EmailGNTResponse.RETORNO_SUCESSO) {
		            String mensagemEnvioEmail = "Email enviado com sucesso pelo serviço do GNT! SeqAgendamento: " + emailGNTResponse.getSeqAgendamento() + ".Destinatários: " + emailGntRequest.getDestinatariosDetalhes() + ". CopiaOculta: " + emailGntRequest.getComCopias();
		            LOGGER.info(mensagemEnvioEmail, parametroRegrasVP.getNumItem().toString(), "EmailVP", null, null);
			    }
			} catch (Exception e) {
				LOGGER.error(
						"Erro ao enviar email GNT" + ExceptionUtil.getRootCause(e).getMessage(),
						parametroRegrasVP.getNumItem().toString(),
						ConstantesVistoriaPrevia.LOG_ERRO_GPA,
						null,
						null);
			}
		}
		
		LOGGER.info(message.toString());
	}
	
	private List<DestinatarioDetalhes> addListaDestinatario(EmailGNTRequest emailGntRequest, Corretor corretor) {
		for (String emailCorretorFinalidadeVP : corretor.getEmails()) {
			emailGntRequest.addDestinatario(emailCorretorFinalidadeVP.toLowerCase(), null);
		}
		return emailGntRequest.getDestinatariosDetalhes();
	}
	
	private List<ParametroGNT> getParametros(ParametroRegrasVP parametroRegrasVP)  {
		List<ParametroGNT> parametros = null;
		try {
			parametros = new ParametroGNTBuilder()
			.addOrReplaceParam(DateUtil.getDataExtenso(new Date()), "DATA_ATUAL")
			.addOrReplaceParam(corretorService.obterCorretor(parametroRegrasVP.getCodCorretor()).orElse(new Corretor()).getNomeCorretor(), "NOME_CORRETOR")
			.addOrReplaceParam(parametroRegrasVP.getNomeCliente(), "NOME_PROPONENTE")
			.addOrReplaceParam(getDescricaoVeiculo(parametroRegrasVP.getNumItem(), "0", parametroRegrasVP.getCodModuloProduto()), "ITEM")
			.addOrReplaceParam(parametroRegrasVP.getChassi(), "CHASSI")
			.addOrReplaceParam(parametroRegrasVP.getPlaca(), "PLACA")
			.addOrReplaceParam(parametroRegrasVP.getCodNegocio().toString(), "COD_NEGOCIO")
			.addOrReplaceParam("Por motivo de "+parametroRegrasVP.getMotivoVPBonus(), "MENSAGEM_GENERICA")
			.build();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return parametros;
	}
	
	public String getDescricaoVeiculo(Long numeroItem, String tipoHistorico, Long codigoProduto){

		String descricaoVeiculo= "";

		DescricaoItemSegurado descricaoItemSegurado = descricaoItemSeguradoRepository.findDescricaoItemSeguradoPorAgrupamento(numeroItem,tipoHistorico, "MARCA/MODELO",codigoProduto);

		if(descricaoItemSegurado !=null){
			ValorCaracteristicaItemSegurado valorCaracteristicaItemSegurado = valorCaracteristicaItemSeguradoRepository.findByCdVlcarItsegAndSqVlcarItsegAndDataConsulta(
							descricaoItemSegurado.getCodValorCaracteristicaItemSegurado(), DateUtil.truncaData(new Date()), DateUtil.DATA_REGISTRO_AGUARDANDO_EFETIVACAO).get(0);
			
			if(valorCaracteristicaItemSegurado !=null){
				descricaoVeiculo = valorCaracteristicaItemSegurado.getDsVaricInico();
			}
		}

		return descricaoVeiculo;
	}
	
	private String getDestinatarios(List<DestinatarioDetalhes> destinatarioDetalhes) {
		StringBuilder destinatarios = new StringBuilder();
		for (DestinatarioDetalhes destinatario : destinatarioDetalhes) {
			destinatarios.append(destinatario.getDestino() + "; ");
		}
		return destinatarios.toString();
	}
	
	public void enviarEmailGNT(EmailGNTRequest emailGntRequest) {
		EmailGNTResponse emailGNTResponse = emailRestClient.sendEmail(emailGntRequest);
	    if(emailGNTResponse.getCodigoRetorno() != EmailGNTResponse.RETORNO_SUCESSO) {
	    	throw new InternalServerException("[enviarEmailGNT] erro: " + emailGNTResponse.getMensagemRetorno() + " request: " + new Gson().toJson(emailGntRequest));
	    }
	}
	
	public EmailGNTResponse enviarEmailGNTMonitoracao(EmailGNTRequest emailGntRequest, String cpfCnpj, String assunto, List<String> listaDestinatarios) {
		StringBuilder log = new StringBuilder("[enviarEmailGNTMonitoracao] inicio -> [cpf]: " + cpfCnpj + " [request]: "
				+ emailGntRequest.toString());
		try {
			for (ParametroGNT parametroGNT : emailGntRequest.getParametros()) {
				if (parametroGNT.getNomeParametro().equals("ASSUNTO"))
					parametroGNT.setValorParametro(montaTituloAmbiente(StringUtils.defaultString(assunto, parametroGNT.getValorParametro())));
			}

			cpfCnpj = cpfCnpj == null ? "" : cpfCnpj;

			if (listaDestinatarios == null) {
				listaDestinatarios = obterEmailsMonitoracao();
			}

			emailGntRequest.setDestinatariosDetalhes(null);
			for (String string : listaDestinatarios) {
				String destino = string.toLowerCase();
				log.append("destinatario adicionado: " + destino + "/r/n");
				emailGntRequest.addDestinatario(destino, null);
			}

			if (!emailGntRequest.getDestinatariosDetalhes().isEmpty() && cpfCnpj != null || !cpfCnpj.trim().equals(""))
				emailGntRequest.getDestinatariosDetalhes().get(0).setCpfCnpj(cpfCnpj);

		} catch (Exception e) {
			LOGGER.error("ERRO enviarEmailGNTMonitoracao -> " + log);
			throw e;
		}
		try {
			return emailRestClient.sendEmail(emailGntRequest);
		} catch (Exception e) {
			LOGGER.error("ERRO enviarEmailGNTMonitoracao -> " + log);
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	private List<String> obterEmailsMonitoracao() {
		ParametroVistoriaPreviaGeral parametroDestinatarios = obterEmailsMonitoracaoErrosInesperados();
		return Arrays.asList(parametroDestinatarios.getVlParamVspre().split(","));
	}

	private ParametroVistoriaPreviaGeral obterEmailsMonitoracaoErrosInesperados() {
		return parametroVistoriaPreviaGeralRepository
				.findOneByNmParamVspre("EMAILS_MONITORACAO_ERROS_INESPERADOS").get();
	}

	public EmailGNTResponse enviarEmailGNTMonitoracao(EmailGNTRequest emailGntRequest, String cpfCnpj)  {
		 return enviarEmailGNTMonitoracao(emailGntRequest, cpfCnpj, null, null);
	 }
	 
	 private String montaTituloAmbiente(String titulo) {
		 return "[" + ambiente + "]" + " - " + titulo;
	 }
	 
	 @Transactional
	 public void enviarEmailConfirmacao(AgendamentoDTO agendamentoDTO) {

		List<String> listEmail = new ArrayList<String>();
    	List<String> listEmailCorretor = agendamentoDTO.getVistoria().getCorretor().getEmails();
    	List<String> listEmailCliente = new ArrayList<>();
    	listEmailCliente.add(agendamentoDTO.getVistoria().getEmailCliente());

		if (UtilJava.trueVar(listEmailCorretor)) {
			listEmail.addAll(listEmailCorretor);
		}

    	if (UtilJava.trueVar(listEmailCliente)) {
    		listEmail.addAll(listEmailCliente);
    	}

		try {
			EmailGNTResponse emailGNTResponse = enviarEmail(listEmail, agendamentoDTO);
			if(emailGNTResponse != null && emailGNTResponse.getCodigoRetorno() == EmailGNTResponse.RETORNO_SUCESSO){
	            String mensagemEnvioEmail = "Email enviado com sucesso pelo serviço do GNT! SeqAgendamento: " +
	            			emailGNTResponse.getSeqAgendamento() + ".Destinatários: " + new Gson().toJson(listEmail);
	            LOGGER.info(mensagemEnvioEmail, "agto.id."+agendamentoDTO.getVistoria().getIdVspreObgta()+
	            		".voucher."+agendamentoDTO.getVistoria().getCdVouch(), "EmailVP", null, null);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Erro metodo enviarEmail: <br>\n" + ExceptionUtil.stackToString(e),
					"agto.id."+agendamentoDTO.getVistoria().getIdVspreObgta()+".voucher."+agendamentoDTO.getVistoria().getCdVouch(),
					ConstantesVistoriaPrevia.LOG_ERRO_GPA,
					null,
					null);
			throw new BadRequestException();
		}
	}
	
	 private EmailGNTResponse enviarEmail(List<String> listEmail, AgendamentoDTO agendamentoDTO) throws Exception {

	    	String tituloEmail = "Tokio Marine - Dados do agendamento de Vistoria Prévia";

	    	List<DestinatarioDetalhes> destinatariosDetalhes = new ArrayList<>();

	    	if (!AmbienteEnum.PRODUCAO.getValue().equals(ambiente)) {
	    		tituloEmail = "[" + ambiente +"] ".concat(tituloEmail);
	    		
	    		obterEmailsTeste(listEmail).forEach(e -> destinatariosDetalhes.add(new DestinatarioDetalhes(e, null)));
	    	} else {
	    		listEmail.forEach(e -> destinatariosDetalhes.add(new DestinatarioDetalhes(e, null)));
	    	}
	    	
	    	if(!destinatariosDetalhes.isEmpty()) {
	    		destinatariosDetalhes.get(0).setCpfCnpj(agendamentoDTO.getVistoria().getNrCpfCnpjClien());
	    	}
	    	
			if (UtilJava.trueVar(agendamentoDTO.getCdVouch())) {

				ParametroVistoriaPreviaGeral parametroVistoriaPreviaGeral = parametroVistoriaPreviaGeralRepository.findOneByNmParamVspre(
								ConstantesVistoriaPrevia.PARAM_TEMPLATE_EMAIL_CONFIRMACAO_AGENDAMENTO_VISTORIA_PREVIA_GNT).orElse(null);
				String codModeloTemplate = parametroVistoriaPreviaGeral.getVlParamVspre();
				List<ParametroGNT> parametros = getParametrosPorItemAgendamento(agendamentoDTO);
				EmailGNTRequest emailGNTRequest = new EmailGNTRequest(codModeloTemplate,
																	destinatariosDetalhes,
																	TipoEnvioGNT.CORRETOR,
																	parametros,
																	RastreiaEnvioGNT.N,
																	null,
																	null);
				emailGNTRequest.addOrReplaceParam(tituloEmail, "ASSUNTO");
				return emailRestClient.sendEmail(emailGNTRequest);
			}
			return null;
		}
	 
	 private List<ParametroGNT> getParametrosPorItemAgendamento(AgendamentoDTO agendamentoDTO) throws Exception {

			ParametroGNTBuilder builder = new ParametroGNTBuilder()
			.addOrReplaceParam(agendamentoDTO.getVistoria().getNmClien(), "NOME_SEGURADO")
			.addOrReplaceParam(agendamentoDTO.getVistoria().getNrCpfCnpjClien(),"CPF_CNPJ")
			.addOrReplaceParam(agendamentoDTO.getCdVouch(),"COD_VOUCHER")
			.addOrReplaceParam(agendamentoDTO.getTpVspre().getDescricao(),"TIPO_VISTORIA")
			.addOrReplaceParam(agendamentoDTO.getPrestadora().getNmRazaoSocal(),"POSTO_VISTORIA")
			.addOrReplaceParam(DateUtil.formataData(new Date()),"DT_SOLICITACAO")
			.addOrReplaceParam(this.getTelefoneContato(agendamentoDTO ),"TELEFONE")
			.addOrReplaceParam(agendamentoDTO.getVistoria().getTpVeicu().getDescricao(),"NOME_PRODUTO")
			.addOrReplaceParam(agendamentoDTO.getVistoria().getNmFabrt(),"FABRICANTE")
			.addOrReplaceParam(agendamentoDTO.getVistoria().getDsModelVeicu(),"MODELO_VEICULO")
			.addOrReplaceParam(agendamentoDTO.getVistoria().getCdFipe(),"COD_FIPE")
			.addOrReplaceParam(agendamentoDTO.getVistoria().getCdPlacaVeicu(),"PLACA")
			.addOrReplaceParam(agendamentoDTO.getVistoria().getCdChassiVeicu(),"CHASSI")
			.addOrReplaceParam(agendamentoDTO.getVistoria().getAaFabrc(),"ANO_FABRICACAO")
			.addOrReplaceParam(agendamentoDTO.getVistoria().getAaModel(),"ANO_VEICULO")
			.addOrReplaceParam(String.valueOf(agendamentoDTO.getVistoria().getCdCrtorCia()),"CD_CORRETOR");
			
			if (agendamentoDTO.getAgendamentoDomicilio() != null) {
				builder.addOrReplaceParam(agendamentoDTO.getAgendamentoDomicilio().getNmLogra(),"ENDEREÇO")
				.addOrReplaceParam(agendamentoDTO.getAgendamentoDomicilio().getNmBairr(),"BAIRRO")
				.addOrReplaceParam(agendamentoDTO.getAgendamentoDomicilio().getNmCidad(),"CIDADE")
				.addOrReplaceParam(agendamentoDTO.getAgendamentoDomicilio().getSgUniddFedrc(),"UF");
			}
			
			List<ParametroGNT> parametros = builder.build();

			String indZeroKm = agendamentoDTO.getVistoria().getIcVeicuZeroKm();
			if (UtilJava.trueVar(indZeroKm) && ConstantesVistoriaPrevia.COD_IND_SIM.equals(indZeroKm)){
				parametros.add(new ParametroGNT("Sim","ZERO_KM"));
			} else {
				parametros.add(new ParametroGNT("Não","ZERO_KM"));
			}
			return parametros;
		}
	 
	private String getTelefoneContato(AgendamentoDTO agendamentoDTO) {
		String telefone = agendamentoDTO.getTelefones() != null ? StringUtils.join(agendamentoDTO.getTelefones().get(0).getCdDddTelef(), " ",
				agendamentoDTO.getTelefones().get(0).getNrTelef()) : "";
		return telefone;

	}

	public void enviarEmailCancelamento(String numeroVoucher, Long codMotvCanclto) {
		if (numeroVoucher != null) {
			try {
				// * Carregar Pré-Agendamento
				VistoriaPreviaObrigatoria vp = vistoriaPreviaObrigatoriaService
						.obterPreAgendamentoPorVoucher(numeroVoucher)
						.orElseThrow(() -> new BusinessVPException("Voucher " + numeroVoucher + " inválido."));

				if (vp != null) {

					String dataExtenso = DateUtil.formataData(DateUtil.truncaData(new Date()));
					
					try {
						dataExtenso = UtilNegocio.getNomeDiaSemana(new Date()) + ", " + DateUtil.getDiaDoMes(new Date())
								+ " de " + DateUtil.getNomeMes(new Date()) + " de " + DateUtil.getAno(new Date());
					} catch (Exception e) {
						LOGGER.error("[enviarEmailCancelamento] dataExtenso erro: " + ExceptionUtils.getRootCauseMessage(e));
					}

					String motivo = conteudoColunaTipoService.obterMotivoCancelamentoPorCodMotivo(codMotvCanclto)
							.map(ConteudoColunaTipoDTO::getDsCoptaColunTipo).orElse(null);
					
					Optional<String> telefones = agendamentoContatoTelefoneService.findContatosTelefonesSegurado(numeroVoucher);

					String codModeloTemplate = parametroVistoriaPreviaGeralRepository
					.findOneByNmParamVspre(ConstantesVistoriaPrevia.PARAM_TEMPLATE_EMAIL_CANCELAMENTO_AGENDAMENTO_VISTORIA_PREVIA_GNT)
					.map(ParametroVistoriaPreviaGeral::getVlParamVspre).orElseThrow(() -> new InternalServerException("Parâmetro 'TEMPLATE_EMAIL_CANCELAMENTO_AGENDAMENTO_VP_GNT' não encontrado."));

					List<ParametroGNT> params = new ParametroGNTBuilder()
							.addOrReplaceParam(numeroVoucher, "COD_VOUCHER")
							.addOrReplaceParam(dataExtenso, "DIA_MES_ANO")
							.addOrReplaceParam(motivo, "MOTIVO")
							.addOrReplaceParam(vp.getDsModelVeicu(), "ITEM")
							.addOrReplaceParam(vp.getCdChassiVeicu(), "CHASSI")
							.addOrReplaceParam(vp.getCdPlacaVeicu(), "PLACA")
							.addOrReplaceParam(telefones.orElse("----"), "TELEFONE")
							.build();
					
					EmailGNTRequest emailGntRequest = new EmailGNTRequest(codModeloTemplate,
																			null,
																			TipoEnvioGNT.CORRETOR,
																			params,
																			RastreiaEnvioGNT.N,
																			null,
																			null);
					
					enviarEmailPrestadoraCorretor(numeroVoucher, vp.getCdCrtorCia(), emailGntRequest);
				} else {
					// * Processo chamador não deve ser interrompido - gravar Log GPA
					LOGGER.error("Erro metodo enviarEmailAgtoCancelado: voucher - " + numeroVoucher
							+ " sem VistoriaPreviaObrigatoria");
				}
			} catch (Exception e) {
				// * Processo chamador não deve ser interrompido - gravar Log GPA
				LOGGER.error("Erro metodo enviarEmailAgtoCancelado: voucher - " + numeroVoucher
						+ ", falha no envio do e-mail getCause: " + ExceptionUtils.getRootCauseMessage(e));
			}
		}
	}

	public void enviarEmailPrestadoraCorretor(String numeroVoucher, Long codCorretor, EmailGNTRequest emailGnt) {
		try {
			enviarEmailCancelamentoPrestadora(numeroVoucher, emailGnt);
			enviarEmailCorretor(numeroVoucher, codCorretor, emailGnt);
		} catch (Exception e) {
			// * Processo chamador não deve ser interrompido - gravar Log GPA
			LOGGER.error("Erro metodo enviarEmailAgtoCancelado - voucher - " + numeroVoucher
					+ ", falha no envio do e-mail getCause: " + ExceptionUtils.getRootCauseMessage(e));
		}
	}

	/**
	 * Envia email informativo (cancelamento/recusa) para pretadora do agendamento
	 * informado.
	 *
	 * @param numeroVoucher
	 * @param tituloEmail
	 * @param corpoEmail
	 *
	 * @throws Exception
	 */
	public void enviarEmailCancelamentoPrestadora(String numeroVoucher, EmailGNTRequest emailGntRequest) {

		Optional<PrestadoraVistoriaPrevia> prestadora = prestadoraVistoriaPreviaRepository
				.findPrestadoraPorAgendamento(numeroVoucher);
		Optional<String> emailPrestadora = prestadora.map(PrestadoraVistoriaPrevia::getCdEmail);

		if (emailPrestadora.isPresent()) {
			try {
				emailGntRequest.setDestinatariosDetalhes(null);
				
				if (AmbienteEnum.PRODUCAO.getValue().equals(ambiente)) {
					emailGntRequest.addDestinatario(emailPrestadora.get(), null);
				} else {
					obterEmailsMonitoracao().forEach(e -> emailGntRequest.addDestinatario(e, null));
				}
				
				enviarEmailGNT(emailGntRequest);
			} catch (Exception e) {
				throw new InternalServerException(
						"Erro metodo enviarEmailPrestadora: voucher - " + numeroVoucher + ", falha no envio do e-mail",
						e);
			}
		} else {
			String tituloEmail = "Prestadora " + prestadora.get().getNmRazaoSocal()
					+ " sem e-mail de contato cadastrado";

			enviarEmailGNTMonitoracao(emailGntRequest, null, tituloEmail, null);
		}
	}

	/**
	 * Envia email informativo (cancelamento/recusa) para corretor do agendamento
	 * informado.
	 *
	 * @param numeroVoucher
	 * @param codCorretor
	 * @param tituloEmail
	 * @param corpoEmail
	 *
	 * @throws Exception
	 */
	public void enviarEmailCorretor(String numeroVoucher, Long codCorretor,
			EmailGNTRequest emailGntRequest) {

		// * Carrega e-mails do corretor informados no
		// agendamento.(VPE0428_AGEND_CONTT_EMAIL)
		List<String> listEmailCorretor = agendamentoContatoEmailService.findAgendamentoEmailCorretor(numeroVoucher);

		// * Corretor não informou nenhum e-mail de contato no agendamento.
		if (listEmailCorretor.isEmpty()) {
			if (AmbienteEnum.PRODUCAO.getValue().equals(ambiente)) {
				listEmailCorretor = corretorService.obterListEmail(codCorretor);
			} else {
				listEmailCorretor = obterEmailsMonitoracao();
			}
		} else if (!AmbienteEnum.PRODUCAO.getValue().equals(ambiente)) {
			listEmailCorretor = obterEmailsTeste(listEmailCorretor);
		}

		if (!listEmailCorretor.isEmpty()) {
			try {
				emailGntRequest.setDestinatariosDetalhes(null);
				listEmailCorretor.forEach(e -> emailGntRequest.addDestinatario(e, null));
				enviarEmailGNT(emailGntRequest);
			} catch (Exception e) {
				LOGGER.error(
						"Erro metodo enviarEmailCorretor: voucher - " + numeroVoucher + ", falha no envio do e-mail",
						e);
			}

			// * Caso corretor não tenha e-mail cadastrado
		} else if (AmbienteEnum.PRODUCAO.getValue().equals(ambiente)) {

			String tituloEmail = "Corretor " + codCorretor + " sem e-mail de contato cadastrado. ";

			try {
				enviarEmailGNTMonitoracao(emailGntRequest, null, tituloEmail,
						Arrays.asList("operacoes.vp@tokiomarine.com.br"));
			} catch (Exception e) {
				throw new InternalServerException("Erro metodo enviarEmailCorretor - Corretor sem e-mail: voucher - "
						+ numeroVoucher + ", falha no envio do e-mail", e);
			}
		}
	}

	private List<String> obterEmailsTeste(List<String> listEmail) {
		List<String> emailsMonitoracao = obterEmailsMonitoracao();
		List<String> emailsTokio = listEmail.stream().filter(emailsMonitoracao::contains).collect(Collectors.toList());
		
		if (emailsTokio.isEmpty()) {
			return emailsMonitoracao;
		}
		
		return emailsTokio;
	}

	/**
	 * Envia e-mail para o corretor nos casos de agendamento recusados/frustrados pela prestadora.
	 *
	 * @param numeroVoucher
	 * @param motivoRecusa
	 *
	 * @throws VistoriaPreviaException
	 */
	public void enviarEmailVistoriaRecusada(String numeroVoucher, String motivoRecusa) {
		if (numeroVoucher != null) {

			try {
				//* Carregar Pré-Agendamento
				VistoriaPreviaObrigatoria vp = vistoriaPreviaObrigatoriaService.obterPreAgendamentoPorVoucher(numeroVoucher).get();

				if (vp != null) {
					//* Carregar Corretor
					Optional<Corretor> corretor = null;
					try {
						corretor = corretorService.obterCorretor(vp.getCdCrtorCia());
					} catch (Exception e) {
						LOGGER.error("[enviarEmailVistoriaRecusada] Erro ao tentar recuperar contato do corretor, erro: " + ExceptionUtils.getRootCause(e).getMessage());
					}

					String nomeCorretor = "NÃO ENCONTRADO";

					if (corretor.isPresent()) {
						Corretor c = corretor.get();
						nomeCorretor = UtilJava.trueVar(c.getNomeCorretor()) ? c.getNomeCorretor() : nomeCorretor;
					}

					String codModeloTemplate = parametroVistoriaPreviaGeralRepository
							.findOneByNmParamVspre(ConstantesVistoriaPrevia.PARAM_TEMPLATE_EMAIL_AGENDAMENTO_RECUSADO)
							.map(ParametroVistoriaPreviaGeral::getVlParamVspre).orElseThrow(() -> new InternalServerException("Parâmetro 'PARAM_TEMPLATE_EMAIL_AGENDAMENTO_RECUSADO' não encontrado."));

					List<ParametroGNT> params = new ParametroGNTBuilder()
							.addOrReplaceParam(vp.getNmClien(), "NOME_PROPONENTE")
							.addOrReplaceParam(vp.getDsModelVeicu(), "ITEM")
							.addOrReplaceParam(vp.getCdChassiVeicu(), "CHASSI")
							.addOrReplaceParam(vp.getCdPlacaVeicu(), "PLACA")
							.addOrReplaceParam(vp.getCdVouch(), "COD_VOUCHER")
							.addOrReplaceParam(motivoRecusa, "MOTIVO_RECUSA")
							.addOrReplaceParam(DateUtil.getDataExtenso(new Date()), "DATA_ATUAL")
							.addOrReplaceParam(nomeCorretor, "NOME_CORRETOR")
							.build();
					
					EmailGNTRequest emailGntRequest = new EmailGNTRequest(codModeloTemplate,
																			null,
																			TipoEnvioGNT.CORRETOR,
																			params,
																			RastreiaEnvioGNT.N,
																			null,
																			null);
					
					enviarEmailCorretor(numeroVoucher, vp.getCdCrtorCia(), emailGntRequest);
				} else {
					//* Processo chamador não deve ser interrompido - gravar Log GPA
					LOGGER.error("Erro metodo enviarEmailVistoriaRecusada: voucher - " + numeroVoucher + " sem VistoriaPreviaObrigatoria");
				}
			} catch (Throwable e) {
				//* Processo chamador não deve ser interrompido - gravar Log GPA
				LOGGER.error(
						"Erro metodo enviarEmailVistoriaRecusada: voucher - " + numeroVoucher + ", falha no envio do e-mail getCause: " + ExceptionUtil.getRootMessage(e));
			}
		}
	}
	
	public void enviarException(String msg, Throwable e) {
		String titulo = "[ERRO] Vistoria Previa";
		String remente = "vp@tokiomarine.com.br";

		EmailTexto email = new EmailTexto(titulo, msg + "\r\n\r\n" + ExceptionUtils.getRootCause(e), remente,
				obterEmailsMonitoracaoErrosInesperados().getVlParamVspre());
		try {
			email.enviar();
		} catch (Exception ex) {
			LOGGER.error("[EmailUtils] erro: " + ExceptionUtils.getRootCause(ex).getMessage());
		}
	}
}