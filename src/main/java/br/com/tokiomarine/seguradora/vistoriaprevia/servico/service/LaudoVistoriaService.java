package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.tokiomarine.seguradora.ext.ssv.repository.ItemSeguradoRepository;
import br.com.tokiomarine.seguradora.ssv.transacional.model.ItemSegurado;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.HistoricoLaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.HistoricoMotivoInconsistenciaLaudo;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPreviaGeral;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.HistoricoLaudoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.HistoricoMotivoInconsistenciaLaudoRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LaudoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ParametroVistoriaPreviaGeralRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ParecerTecnicoLaudoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.VistoriaPreviaObrigatoriaRepository;

@Service
public class LaudoVistoriaService {

	@Autowired
	private ItemSeguradoRepository itemSeguradoRepository;
	
	@Autowired
	private LaudoVistoriaPreviaRepository laudoVistoriaPreviaRepository;
	
	@Autowired
	private HistoricoLaudoVistoriaPreviaRepository historicoLaudoVistoriaPreviaRepository;
	
	@Autowired
	private HistoricoMotivoInconsistenciaLaudoRepository historicoMotivoInconsistenciaLaudoRepository;	
	
	@Autowired
	private VistoriaPreviaObrigatoriaRepository vistoriaPreviaObrigatoriaRepository;
	
	@Autowired
	private ParecerTecnicoLaudoVistoriaPreviaRepository parecerTecnicoRepository;	
	
	@Autowired
	private ParametroVistoriaPreviaGeralRepository parametroVPRespository;
	
	private static final Logger LOGGER = LogManager.getLogger(LaudoVistoriaService.class);
	
	
	/**
	 * Desvincula o laudo marcando a flag para não vinculado e deletando o histórico
	 * de motivos de inconsistência e o histórico do laudo.
	 * * Método utilizado pela Recusa e VSP de item/endosso da grade
	 *
	 * @param numeroItemSegurado número do item segurado ao qual o laudo está vinculado
	 * @param codigoEndosso código do endosso ao qual o laudo está vinculado
	 * @param codigoUsuario código do usuário que está realizando a operação
	 */
	public void desvincularLaudo(Long numeroItemSegurado,Long codigoEndosso,String codigoUsuario) {
		
		StringBuilder mensagemLog = new StringBuilder();

		adicionarMensagemLog(mensagemLog,"Usuário: " + codigoUsuario);

		ItemSegurado itemSeguradoPropostaEndosso = null;
		
		try {
			
			// se for um endosso
			if (codigoEndosso !=null && codigoEndosso > 0L) {

				adicionarMensagemLog(mensagemLog,"Verificando endosso com restrição de VP");

				// busca o item segurado do endosso (apenas se o endosso tiver restrição de VP)
				itemSeguradoPropostaEndosso = itemSeguradoRepository.findItemSeguradoPorEndossoItemRestricaoVP(codigoEndosso, numeroItemSegurado);
				
				if (itemSeguradoPropostaEndosso == null) {

					adicionarMensagemLog(mensagemLog,"Endosso sem restrição de VP");
				}			
			}
			// se for uma proposta
			else if (numeroItemSegurado !=null) {

				adicionarMensagemLog(mensagemLog,"Obtendo item segurado proposta");

				// busca o item segurado da proposta
				itemSeguradoPropostaEndosso = itemSeguradoRepository.findByItemSegurado(numeroItemSegurado, "0");
			}

			// se encontrou o item segurado da proposta ou endosso
			if (itemSeguradoPropostaEndosso != null) {

				adicionarMensagemLog(mensagemLog,"Item segurado encontrado");

				// se o item possui código de laudo
				if (!StringUtils.isEmpty(itemSeguradoPropostaEndosso.getCodLaudoVistoriaPrevia())) {

					adicionarMensagemLog(mensagemLog,"Item segurado com laudo vinculado: " + itemSeguradoPropostaEndosso.getCodLaudoVistoriaPrevia());

					// se o item não precisa de VP
					if (!StringUtils.isEmpty(itemSeguradoPropostaEndosso.getIcVspre()) && "N".equals(itemSeguradoPropostaEndosso.getIcVspre())) {

						adicionarMensagemLog(mensagemLog,"Item sem necessidade de VP. Não desvincula o laudo.");

						gravaLog(mensagemLog);

						// encerra o processamento
						return;
					}

					adicionarMensagemLog(mensagemLog,"Obtendo laudo");
					// busca o laudo e faz o desvínculo do laudo				
					LaudoVistoriaPrevia laudoVP = laudoVistoriaPreviaRepository.findLaudoByCdLvpreNrVrsaoLvpreCdSituc(itemSeguradoPropostaEndosso.getCodLaudoVistoriaPrevia(), 0L);
					saveLaudoVp(codigoUsuario, mensagemLog, itemSeguradoPropostaEndosso, laudoVP);					
				} else {

					adicionarMensagemLog(mensagemLog,"Item não possui laudo para desvínculo");
				}
			}

			gravaLog(mensagemLog);
			
		} catch (Exception e) {
			adicionarMensagemLog(mensagemLog,"Erro ao desvincular laudo para o item:" + numeroItemSegurado + "erro:"  +  e.getMessage());
			gravaLog(mensagemLog);
		}
		
	}	
	
	private void saveLaudoVp(String codigoUsuario, StringBuilder mensagemLog, ItemSegurado itemSeguradoPropostaEndosso, LaudoVistoriaPrevia laudoVP) {

		if (laudoVP != null && !isCodigoParecerRecusaAutomatica(laudoVP.getCdLvpre(), laudoVP.getNrVrsaoLvpre())) {

			adicionarMensagemLog(mensagemLog,"Laudo encontrado. Atualizando laudo...");
			
			// marca o laudo como não vinculado e atualiza na base
			laudoVP.setIcLaudoVicdo("N");
			laudoVP.setCdUsuroUltmaAlter(codigoUsuario);
			laudoVP.setDtUltmaAlter(new Date());
			
			laudoVistoriaPreviaRepository.save(laudoVP);
			
			adicionarMensagemLog(mensagemLog,"Laudo atualizado.");

			deletarPreAgendamentoVinculo(mensagemLog,laudoVP);
			// deleta os motivos de inconsistência do laudo
			deletarHistoricoMotivoInconsistenciaLaudo(mensagemLog,itemSeguradoPropostaEndosso.getCodLaudoVistoriaPrevia());

			// deleta o histórico do laudo
			deletarHistoricoLaudoVistoriaPrevia(mensagemLog,itemSeguradoPropostaEndosso.getCodLaudoVistoriaPrevia());

			adicionarMensagemLog(mensagemLog,"Laudo desvinculado!");
		
		} else {						
			adicionarMensagemLog(mensagemLog,"Laudo não desvinculado, nulo ou possui codigo de parecer recusavel automatico!");						
		}  
	}
	
	
	private boolean isCodigoParecerRecusaAutomatica(String codigoLaudo, Long numeroVersao){
		
		List<Long> listaCodigoParacer = parecerTecnicoRepository.findByParecerTecnico(codigoLaudo,numeroVersao);		
		List<ParametroVistoriaPreviaGeral> listaParecerRecusa = parametroVPRespository.findByParametroVistoriaPreviaGeralPorNome("LISTA_COD_PARECER_RECUSA_PROPOSTA");
		
		List<Long> listParecerLong = Stream.of(listaParecerRecusa.get(0).getVlParamVspre().split("[,]"))
		        .map(Long::parseLong)
		        .collect(Collectors.toList());
				
		Long containsCodigo = listParecerLong.stream().filter(line -> (listaCodigoParacer.contains(line))).findAny().orElse(null);
					
		return containsCodigo !=null;
		
	}
	
	/*
	 * Verifica se tem algum pré-agendamento criado pelo vínculo e se houver deleta
	 */
	private void deletarPreAgendamentoVinculo(StringBuilder mensagemLog,LaudoVistoriaPrevia laudoVP) {

		// se o laudo possui voucher
		if (!StringUtils.isEmpty(laudoVP.getCdVouch())) {

			adicionarMensagemLog(mensagemLog,"Laudo com voucher: " + laudoVP.getCdVouch() + ". Obtendo pré-agendamentos por voucher");

			List<VistoriaPreviaObrigatoria> preAgendamentos = vistoriaPreviaObrigatoriaRepository.findObterPreAgendamentoPorVoucher(laudoVP.getCdVouch());
			if (preAgendamentos != null && !preAgendamentos.isEmpty()) {

				adicionarMensagemLog(mensagemLog,"Pré-agendamento(s) encontrado(s)");
				for (VistoriaPreviaObrigatoria preAgendamento : preAgendamentos) {

					// se o pré-agendamento é do vínculo
					if (preAgendamento.getCdSistmOrigm().compareTo(16L) == 0) { // 16 Sistema de Vinculo.

						adicionarMensagemLog(mensagemLog,"Deletando pré-agendamento sistema \"vinculo\". Id: " + preAgendamento.getIdVspreObgta());

						// deleta o pré
						vistoriaPreviaObrigatoriaRepository.delete(preAgendamento);
					}
				}
			}
		}
	}	
	
	
	/*
	 * Deleta o histórico de motivos de inconsistência do laudo
	 */
	private void deletarHistoricoMotivoInconsistenciaLaudo(StringBuilder mensagemLogGPA,String codigoLaudo) {

		adicionarMensagemLog(mensagemLogGPA,"Procurando HistoricoMotivoInconsistenciaLaudo...");

		List<HistoricoMotivoInconsistenciaLaudo> historicoMotivoInconsistenciaList = historicoMotivoInconsistenciaLaudoRepository.findHistoricoMotivoInconsistenciaLaudoPorLaudoEVersao(codigoLaudo, 0L);

		if ( (historicoMotivoInconsistenciaList == null) || (historicoMotivoInconsistenciaList.isEmpty())) {

			adicionarMensagemLog(mensagemLogGPA,"HistoricoMotivoInconsistenciaLaudo não encontrados para o laudo");
			return;
		}

		for (HistoricoMotivoInconsistenciaLaudo bean : historicoMotivoInconsistenciaList) {

			adicionarMensagemLog(mensagemLogGPA,"Deletando HistoricoMotivoInconsistenciaLaudo. Motivo: " + bean.getCdMotvInconVspre() + ", situc: " + bean.getCdSitucHistoVspre());
			
			historicoMotivoInconsistenciaLaudoRepository.delete(bean);
		}
	}

	/*
	 * Deleta o histórico do laudo
	 */
	private void deletarHistoricoLaudoVistoriaPrevia(StringBuilder mensagemLogGPA,String codigoLaudo) {

		adicionarMensagemLog(mensagemLogGPA,"Procurando HistoricoLaudoVistoriaPrevia...");
		
		List<HistoricoLaudoVistoriaPrevia> listaHistoricoLaudoVistoriaPrevia = historicoLaudoVistoriaPreviaRepository.findHistoricoLaudoVistoriaPreviaPorLaudoEVersao(codigoLaudo, 0L) ;

		if ( (listaHistoricoLaudoVistoriaPrevia == null) || (listaHistoricoLaudoVistoriaPrevia.isEmpty())) {

			adicionarMensagemLog(mensagemLogGPA,"HistoricoLaudoVistoriaPrevia não encontrados para o laudo");
			return;
		}

		for (HistoricoLaudoVistoriaPrevia bean : listaHistoricoLaudoVistoriaPrevia) {

			adicionarMensagemLog(mensagemLogGPA,"Deletando HistoricoLaudoVistoriaPrevia. Situc: " + bean.getCdSitucHistoVspre());
			
			historicoLaudoVistoriaPreviaRepository.delete(bean);
		}
	}
	
	private void adicionarMensagemLog(StringBuilder mensagemLogGPA,String mensagemAtual) {

		mensagemLogGPA.append(mensagemAtual + " <br/>\n");
	}	
	
	private void gravaLog(StringBuilder mensagemLog){
		
		LOGGER.info(mensagemLog);
		
	}

}
