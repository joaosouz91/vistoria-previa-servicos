package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.tokiomarine.seguradora.aceitacao.crud.model.Restricao;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.AjusteRestClient;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.util.DateUtil;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.util.RestClientException;
import br.com.tokiomarine.seguradora.ext.act.service.RestricaoService;
import br.com.tokiomarine.seguradora.ext.ssv.service.ConteudoColunaTipoService;
import br.com.tokiomarine.seguradora.ext.ssv.service.ParametroControleDataService;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ParametroControleData;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoDomicilio;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.StatusAgendamentoAgrupamento;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Dispensa;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ListaDeDispensa;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoCancelamentoEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.OrigemDispensaVistoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.StatusAgendamentoVistoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoRestricao;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.AgendamentoDomicilioRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.StatusAgendamentoAgrupamentoRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.VistoriaPreviaObrigatoriaRepository;

@Component
public class DispensaService {

	@Autowired
	private ConteudoColunaTipoService conteudoColunaTipoService;
	
	@Autowired
	private RestricaoService restricaoService;
	
	@Autowired
	private ParametroControleDataService parametroControleDataService;
			
	@Autowired
	private AjusteRestClient ajusteRestClient;
	
	@Autowired
	private VistoriaPreviaObrigatoriaRepository vistoriaPreviaObrigatoriaRepository;
	
	@Autowired
	private StatusAgendamentoAgrupamentoRepository statusAgendamentoAgrupamentoRepository;
	
	@Autowired
	private AgendamentoDomicilioRepository agendamentoDomicilioRepository;	
	
	@Autowired
	private EmailService emailService;
	
	private static final Logger LOGGER = LogManager.getLogger(DispensaService.class);
	
	private String voucher =  " Voucher \"";
	
	public List<ConteudoColunaTipo> motivoDispensas(){
		return conteudoColunaTipoService.motivoDispensas();
	}
		
	@Transactional
	public Boolean salvarMotivoDispensa(final Dispensa dispensa) {
		try{									
			if(dispensa.getConteudoColunaTipo() == null){
				throw new BusinessVPException("Motivo dispensa inválido!");
			}
			
			if(dispensa.getJustificativaDispensa() == null || dispensa.getJustificativaDispensa().length() <= 0 || dispensa.getJustificativaDispensa().length() > 400){
				throw new BusinessVPException("Justificativa da dispensa inválida!");
			}
			
			if(dispensa.getCodigoProposta() == null || dispensa.getCodigoProposta() <= 0){
				throw new BusinessVPException("Código endosso inválido!");
			}
			
			if(dispensa.getNumeroItemSegurado() == null || dispensa.getNumeroItemSegurado() <= 0){
				throw new BusinessVPException("Item seguro  inválido!");
			}				

			if(!(dispensa.getTipoFechamentoRestricao() == 1 || dispensa.getTipoFechamentoRestricao() == 3)){
				throw new BusinessVPException("Tipo do fechamento restrição inválido!");
			}
			
			salvarRestricao(dispensa);						
		}catch (Exception e) {
			LOGGER.error(e);
			return Boolean.FALSE;
		}
				
		execuraAjuste(dispensa.getCodigoProposta(), dispensa.getNumeroItemSegurado());
		
		return Boolean.TRUE;
	}
	
	@Transactional
	public void salvarRestricao(final Dispensa dispensa){
		final ParametroControleData parametroControleData = parametroControleDataService.obterParametroControleData();
		
		Restricao restricao = restricaoService.buscarRestricao(dispensa.getCodigoProposta(), dispensa.getNumeroItemSegurado(), TipoRestricao.VIS.getValue());
		
		restricao.setDsJustfLibecRestr(dispensa.getJustificativaDispensa());						
		restricao.setCdSitucRestr(TipoRestricao.LIB.getValue());
		restricao.setTpLibec(dispensa.getConteudoColunaTipo().getVlCntdoColunTipo());
		restricao.setDtFechmRestr(parametroControleData.getDtReferOnlin());
		restricao.setDtUltmaAlter(parametroControleData.getDtReferOnlin());
		restricao.setTpFechmRestr(dispensa.getTipoFechamentoRestricao());
		restricao.setCdUsuroUltmaAlter(dispensa.getIdUsuarioLogado());
		
		restricaoService.save(restricao);
	}
	
	@Transactional
	public Boolean salvarMotivoDispensaLista(final ListaDeDispensa listaDeDispensa) {		
		validarListaDispensa(listaDeDispensa);

		for(Long numeroItemSegurado : listaDeDispensa.getNumeroItemSegurado()){
			try{								
				listaDeDispensa.setIdUsuarioLogado(listaDeDispensa.getIdUsuarioLogado());
				salvarListaRestricao(listaDeDispensa, numeroItemSegurado);				
			}catch (Exception e) {
				LOGGER.error("erro ao liberar toda lista de dispensa, numero item: " + numeroItemSegurado + " , detalhe: " + e.getMessage());
				return Boolean.FALSE;
			}
			
			execuraAjuste(listaDeDispensa.getCodigoProposta(), numeroItemSegurado);	
		}
		
		return Boolean.TRUE;
	}

	private void validarListaDispensa(final ListaDeDispensa listaDeDispensa) {
		if(listaDeDispensa.getIdUsuarioLogado() == null || listaDeDispensa.getIdUsuarioLogado().length() <= 0){
			throw new BusinessVPException("Id usuário inválido!");
		}
		
		if(listaDeDispensa.getConteudoColunaTipo() == null){
			throw new BusinessVPException("Motivo dispensa inválido!");
		}
		
		if(listaDeDispensa.getJustificativaDispensa() == null || listaDeDispensa.getJustificativaDispensa().length() <= 0 
				|| listaDeDispensa.getJustificativaDispensa().length() > 400){
			throw new BusinessVPException("Justificativa da dispensa inválida!");
		}
		
		if(listaDeDispensa.getCodigoProposta() == null || listaDeDispensa.getCodigoProposta() <= 0){
			throw new BusinessVPException("Código endosso inválido!");
		}
		
		if(listaDeDispensa.getNumeroItemSegurado() == null || listaDeDispensa.getNumeroItemSegurado().isEmpty()){
			throw new BusinessVPException("Item seguro inválido!");
		}				

		if (listaDeDispensa.getTipoFechamentoRestricao() != null && listaDeDispensa.getTipoFechamentoRestricao() != 1
				&& listaDeDispensa.getTipoFechamentoRestricao() != 3) {
			throw new BusinessVPException("Tipo do fechamento restrição inválido!");
		}
	}
	
	private void execuraAjuste(final Long codigoProposta, final Long numeroItemSegurado){
		try{
			final Restricao restricao = restricaoService.buscarRestricao(codigoProposta, numeroItemSegurado, TipoRestricao.AJU.getValue());
			if(restricao != null){					
				ajusteRestClient.executarAjusteNovo(restricao.getIdRestr());
			}
		}catch (RestClientException e) {
			LOGGER.error("erro ao executar AjusteRestClient executarAjuste, numero item: " + numeroItemSegurado + " , detalhe: " + e.getMessage());
		}
	}
	
	@Transactional
	public void salvarListaRestricao(final ListaDeDispensa listaDeDispensa, final Long numeroItemSegurado){
		final ParametroControleData parametroControleData = parametroControleDataService.obterParametroControleData();
		
		Restricao restricao = restricaoService.buscarRestricao(listaDeDispensa.getCodigoProposta(), numeroItemSegurado, TipoRestricao.VIS.getValue());
		
		restricao.setDsJustfLibecRestr(listaDeDispensa.getJustificativaDispensa());						
		restricao.setCdSitucRestr(TipoRestricao.LIB.getValue());
		restricao.setTpLibec(listaDeDispensa.getConteudoColunaTipo().getVlCntdoColunTipo());
		restricao.setDtFechmRestr(parametroControleData.getDtReferOnlin());
		restricao.setDtUltmaAlter(new Date());
		restricao.setTpFechmRestr(listaDeDispensa.getTipoFechamentoRestricao());
		restricao.setCdUsuroUltmaAlter(listaDeDispensa.getIdUsuarioLogado());
		
		restricaoService.save(restricao);
	}
	
	public void dispensarVistoriaProposta(Long numeroItem, Long codigoEndosso, Long tipoOrigem, String codigoUsuario){
		
		List<VistoriaPreviaObrigatoria> listaPreAgendamento = null;		
		String tipoProposta = codigoEndosso !=null && codigoEndosso > 0 ? "E":"P";	
		
		if("E".equals(tipoProposta)){
			listaPreAgendamento = vistoriaPreviaObrigatoriaRepository.findObterPreAgendamentoPorItemSeguradoEndosso(numeroItem, codigoEndosso);	
		} else {
			listaPreAgendamento = vistoriaPreviaObrigatoriaRepository.findObterPreAgendamentoPorItemSegurado(numeroItem);
		}
				
		if (CollectionUtils.isNotEmpty(listaPreAgendamento)) {
			listaPreAgendamento.forEach(vp -> dispensar(tipoOrigem, codigoUsuario, tipoProposta, vp));
		}
	}

	private void dispensar(Long tipoOrigem, String codigoUsuario, String tipoProposta, VistoriaPreviaObrigatoria vp) {
		if (vp.getCdVouch() == null || StringUtils.isEmpty(vp.getCdVouch())) {
			vistoriaPreviaObrigatoriaRepository.delete(vp);
		} else {
			StatusAgendamentoAgrupamento status = statusAgendamentoAgrupamentoRepository.findStatusAtualPorVoucher(vp.getCdVouch())
					.orElseThrow(() -> new BusinessVPException("Status do agendamento não localizado: "+vp.getCdVouch()));
			
			// caso o voucher esteja cancelado e confirmado, não invoca o precesso de dispensa.
			if(!"CAN".equals(status.getCdSitucAgmto()) || ("CAN".equals(status.getCdSitucAgmto()) && !MotivoCancelamentoEnum.CANCELAMENTO_CONFIRMADO.getValor().equals(status.getCdMotvSitucAgmto()))){ 
				this.tratamentoDispensa(tipoProposta, vp, tipoOrigem, codigoUsuario);	
			}
		}
	}
	
	private String mVistoriaDispensada(Long origemDispensa, String tipoProposta) {
		if (OrigemDispensaVistoria.ORIGEM_REGRA.getValor().equals(origemDispensa)) {
			return "Devido as alterações, a \"Vistoria Prévia\" não é mais nescessária.";
		} else if (OrigemDispensaVistoria.ORIGEM_RECUSA.getValor().equals(origemDispensa)) {
			if ("E".equals(tipoProposta)) {
				return "Endosso";
			} else {
				return "Negócio";
			}
		}
		return " recusado, a \"Vistoria Prévia\" não é mais nescessária.";

	}
	
	private String titulo(Long origemDispensa, String tipoProposta) {
		if (OrigemDispensaVistoria.ORIGEM_REGRA.getValor().equals(origemDispensa)) {
			if ("E".equals(tipoProposta) ) {
				return "Endosso alterado. ";
			} else {
				return "Cotação/Proposta alterada. ";
			}
		} else if (OrigemDispensaVistoria.ORIGEM_DISPENSA.getValor().equals(origemDispensa)) {
			return "Vistoria Prévia Dispensada. ";
		} else if (OrigemDispensaVistoria.ORIGEM_RECUSA.getValor().equals(origemDispensa)) {
			if ("E".equals(tipoProposta)) {
				return "Endosso Recusado. ";
			} else {
				return "Negócio Recusado. ";
			}
		}
		return "";
	}
	
	private StatusAgendamentoAgrupamento gerarNovoStatus(Long valor ,String codUsuroUltmaAlter, StatusAgendamentoAgrupamento status, ParametroControleData parametroControleData) {
		StatusAgendamentoAgrupamento novoStatus = new StatusAgendamentoAgrupamento();

		novoStatus.setCdSitucAgmto("CAN");
		novoStatus.setCdMotvSitucAgmto(valor);
		novoStatus.setCdUsuroUltmaAlter(codUsuroUltmaAlter);
		novoStatus.setCdVouch(status.getCdVouch());
		novoStatus.setDtUltmaAlter(parametroControleData.getDtReferOnlin());
		
		return novoStatus;
	}

	private void tratamentoDispensa(String tipoProposta, VistoriaPreviaObrigatoria vp, Long origemDispensa, String codUsuroUltmaAlter) {
		
		
		String motivoVistoriaDispensada = "";
		String motivoVistoriaDispensadaPrtra = "";
		String textCancelado = "\" cancelado.";
		ParametroControleData parametroControleData = parametroControleDataService.obterParametroControleData();

		motivoVistoriaDispensada = mVistoriaDispensada(origemDispensa, tipoProposta);
	
		StatusAgendamentoAgrupamento status = statusAgendamentoAgrupamentoRepository.findStatusAtualPorVoucher(vp.getCdVouch())
				.orElseThrow(() -> new BusinessVPException("Status do agendamento não localizado: "+vp.getCdVouch()));
		
		if (!StringUtils.isEmpty(codUsuroUltmaAlter)) codUsuroUltmaAlter = status.getCdSitucAgmto();
		

		if (StatusAgendamentoVistoria.PENDENTE.getValor().equals(status.getCdSitucAgmto()) || StatusAgendamentoVistoria.RECEBIDO.getValor().equals(status.getCdSitucAgmto()) || StatusAgendamentoVistoria.FRUSTRADO.getValor().equals(status.getCdSitucAgmto())) {

			statusAgendamentoAgrupamentoRepository.save(gerarNovoStatus(MotivoCancelamentoEnum.CANCELAMENTO_A_CONFIRMAR.getValor(), 
																		codUsuroUltmaAlter, status, parametroControleData));

			motivoVistoriaDispensadaPrtra += " Agendamento cancelado devido ao cancelamento da proposta. A vistoria não é mais necessária. \n";
			motivoVistoriaDispensada += voucher + status.getCdVouch() + textCancelado;

		} else if (StatusAgendamentoVistoria.NAOAGENDADO.getValor().equals(status.getCdSitucAgmto())) {

			statusAgendamentoAgrupamentoRepository.save(gerarNovoStatus(MotivoCancelamentoEnum.CANCELAMENTO_DE_NAG.getValor(), 
																		codUsuroUltmaAlter, status, parametroControleData));
		    
			motivoVistoriaDispensadaPrtra += " Agendamento cancelado a pedido da Tokio Marine, favor desconsiderar a solicitação para realização dessa vistoria. \n";
			motivoVistoriaDispensada += voucher + status.getCdVouch() + textCancelado;

		} else if ((StatusAgendamentoVistoria.AGENDADO.getValor().equals(status.getCdSitucAgmto()) || StatusAgendamentoVistoria.REAGENDADO.getValor().equals(status.getCdSitucAgmto()))) {

			boolean icPermiteCancelamento = true;

			AgendamentoDomicilio agendamentoDomicilio =  agendamentoDomicilioRepository.findAgendamentoDomicilioPorVoucher(vp.getCdVouch()).orElse(null);
			
			if(agendamentoDomicilio !=null && DateUtil.trunc(agendamentoDomicilio.getDtVspre().getTime()).compareTo(DateUtil.trunc(parametroControleData.getDtReferOnlin().getTime())) == 0){
				icPermiteCancelamento = false;
				
			}

			// caso permitir o cancelamento inseri o status de "cancelado".
			if(icPermiteCancelamento || StatusAgendamentoVistoria.CANCELADO.getValor().equals(status.getCdSitucAgmto())){
				statusAgendamentoAgrupamentoRepository.save(gerarNovoStatus(MotivoCancelamentoEnum.CANCELAMENTO_CONFIRMADO.getValor(), 
																			codUsuroUltmaAlter, status, parametroControleData));
			}

			motivoVistoriaDispensadaPrtra += " Agendamento cancelado devido ao cancelamento da proposta. A vistoria não é mais necessária. \n";
			motivoVistoriaDispensada += " Atenção, Voucher \"" + status.getCdVouch() + "\" deve ser cancelado junto à prestadora.";

		} else if (StatusAgendamentoVistoria.REALIZADO.getValor().equals(status.getCdSitucAgmto())) {}

		String titulo = titulo(origemDispensa, tipoProposta);
		String tituloPrtra = "";

		
		tituloPrtra += voucher + status.getCdVouch() + textCancelado;
		tituloPrtra += titulo += "Vistoria Prévia não é mais nescessária.";

		vp.setDsObser("VP_NAO_NECESSARIA");
		vistoriaPreviaObrigatoriaRepository.save(vp);
		
		motivoVistoriaDispensadaPrtra = motivoVistoriaDispensada += "\n\n\n";
		
		if (StringUtils.isEmpty(status.getCdVouch())) {
			motivoVistoriaDispensadaPrtra = motivoVistoriaDispensada += "Voucher: " + status.getCdVouch() + "\n";
		}
		
		motivoVistoriaDispensada += gerarMVDispensada(vp);
		
// IMPLEMENTAR ENVIO DE EMAIL GNT QUANDO O PROCESSO DE MIGRACAO FOR CONCLUIDO
//					ParametroVistoriaPreviaGeral parametroVistoriaPreviaGeral = parametroVPGeralDAO.getParametroVistoriaPreviaGeral(ConstantesVistoriaPrevia.PARAM_TEMPLATE_EMAIL_DISPENSA_VISTORIA_PREVIA_GNT).get(0);
//					String codModeloTemplate = parametroVistoriaPreviaGeral.getVlParamVspre();
//
//					EmailGNTRequest emailGNTRequest =  new EmailGNTRequest(codModeloTemplate, null, TipoEnvioGNT.CORRETOR, getParametros(vp, status), RastreiaEnvioGNT.NAO, null,null);
		try {
			
			emailService.enviarEmailPrestadora(vp.getCdVouch(),tituloPrtra,motivoVistoriaDispensadaPrtra);
//						if (OrigemDispensaVistoria.ORIGEM_RECUSA.getValor().equals(origemDispensa)) {
//						} else {
//							emailAgendamentoComponent.enviarEmailGNTCorretor(vp.getCdVouch(),vp.getCdCrtorCia(),titulo,vp.getNrCpfCnpjClien(),emailGNTRequest);
//							emailAgendamentoComponent.enviarEmailPrestadora(vp.getCdVouch(),tituloPrtra,motivoVistoriaDispensadaPrtra);
//						}
		} catch (Exception e) {
			LOGGER.error("erro ao enviar e-mail dispensarVistoria | item:" + vp.getNrItseg() + "endosso:" + vp.getCdEndos());
		}
	}
	
	private String gerarMVDispensada (VistoriaPreviaObrigatoria vp) {
		String motivoVistoriaDispensada = "";
		motivoVistoriaDispensada += "Corretor: " + vp.getCdCrtorCia() + "\n";
		motivoVistoriaDispensada += "Segurado: " + vp.getNmClien() + "\n";
		motivoVistoriaDispensada += "Veículo: " + vp.getDsModelVeicu() + "\n";
		motivoVistoriaDispensada += "Placa: " + vp.getCdPlacaVeicu() + "\n";
		motivoVistoriaDispensada += "Chassi: " + vp.getCdChassiVeicu() + "\n";
		
		if (vp.getNrCallo() != null) {
			motivoVistoriaDispensada += "Número Cálculo: " + vp.getNrCallo() + "\n";
		}
		
		if (vp.getCdNgoco() != null) {
			motivoVistoriaDispensada += "Negócio: " + vp.getCdNgoco() + "\n";
		}
		
		if (vp.getNrItseg() != null) {
			motivoVistoriaDispensada += "Item: " + vp.getNrItseg() + "\n";
		}
		
		if (vp.getCdEndos() != null) {
			motivoVistoriaDispensada += "Endosso: " + vp.getCdEndos() + "\n";
		}
		return motivoVistoriaDispensada;
	}
	
}