package br.com.tokiomarine.seguradora.vistoriaprevia.servico.regra.agendamento;

import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.StatusAgendamentoDTO.isMotivoCanclReagendar;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoAgendamentoNaoPermitidoEnum.MOTIVO_AGENDAMENTO_REAPROVEITADO;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SistemaChamador.isAgtoSemProposta;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SistemaChamador.isEndossoSSV;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.AGD;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.CAN;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.FIM;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.NAG;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.NAP;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.PEN;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.RCB;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.RGD;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.RLZ;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.VFR;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.ext.ssv.service.ItemSeguradoService;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoDomicilio;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.StatusAgendamentoAgrupamento;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.AgendamentoDomicilioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.LaudoService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.ParametroVPService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.StatusAgendamentoService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.VistoriaPreviaObrigatoriaService;

@Component
public class ProcuraAgendamentoVeiculoCommand implements Command {

	private ParametroVPService parametroVPService;
	private StatusAgendamentoService statusAgendamentoService;
	private VistoriaPreviaObrigatoriaService vistoriaPreviaObrigatoriaService;
	private ItemSeguradoService itemSeguradoService;
	private AgendamentoDomicilioService agendamentoDomicilioService;
	private LaudoService laudoService;
	
	public ProcuraAgendamentoVeiculoCommand(ParametroVPService parametroVPService,
			StatusAgendamentoService statusAgendamentoService,
			@Lazy VistoriaPreviaObrigatoriaService vistoriaPreviaObrigatoriaService,
			ItemSeguradoService itemSeguradoService, 
			AgendamentoDomicilioService agendamentoDomicilioService, 
			LaudoService laudoService) {
		this.parametroVPService = parametroVPService;
		this.statusAgendamentoService = statusAgendamentoService;
		this.vistoriaPreviaObrigatoriaService = vistoriaPreviaObrigatoriaService;
		this.itemSeguradoService = itemSeguradoService;
		this.agendamentoDomicilioService = agendamentoDomicilioService;
		this.laudoService = laudoService;
	}
	
	@Override
	public boolean execute(Context context) throws Exception {
		PermiteAgendamentoContext permite = (PermiteAgendamentoContext) context;
		VistoriaPreviaObrigatoria vp = permite.getVistoriaPrevia();
		
		permite.addLog("Pré-agendamento sem voucher. Procura outro agendamento para o mesmo veículo...");

		String voucher = obterVoucher(permite);

		if (voucher != null) {

			permite.addLog("Encontrado voucher " + voucher + " para esse veículo. Obtém status do agendamento...");
			StatusAgendamentoAgrupamento status = obterStatus(voucher);

			if (VFR.getValor().equals(status.getCdSitucAgmto()) || NAG.getValor().equals(status.getCdSitucAgmto())
					|| NAP.getValor().equals(status.getCdSitucAgmto())
					|| CAN.getValor().equals(status.getCdSitucAgmto())
							&& isMotivoCanclReagendar(status.getCdMotvSitucAgmto())) {

				permite.addLog("Status do agendamento: " + status.getCdSitucAgmto() + ". Permite agendamento.");
				permite.setPermiteAgendamento(true);
				vp.setCdVouch(voucher);
				vistoriaPreviaObrigatoriaService.salvar(vp);

				return true;
			}

			permite.addLog("Status do agendamento: " + status.getCdSitucAgmto() + ". Não permite agendamento.");
			permite.setPermiteAgendamento(false);
			permite.setMotivoAgendamentoNaoPermitido(MOTIVO_AGENDAMENTO_REAPROVEITADO);
			permite.setVoucher(voucher);
			vp.setCdVouch(voucher);
			vistoriaPreviaObrigatoriaService.salvar(vp);

			return true;
		}
		
		return false;
	}

	private StatusAgendamentoAgrupamento obterStatus(String voucher) {
		return statusAgendamentoService.obterStatusAgendamento(voucher)
				.orElseThrow(() -> new BusinessVPException("Status do agendamento não localizado: "+voucher));
	}
	
	/* Trecho comentado para obter agendamento somente por corretor + chassi.
	** (#Solicitado por Sergio Avena - OS284882 - 23/06/2015)
	** <code>String chave = placa + "_" + chassi + "_" + corretor + "_" + sistemaChamador;</code>
	*/
	public String obterVoucher(PermiteAgendamentoContext permite) {
		VistoriaPreviaObrigatoria vp = permite.getVistoriaPrevia();
		
		int qtDiasValidadeAgendamento = parametroVPService.obterQuantidadeDiasValidadeAgendamento();

		permite.addLog("Inicio da verificação para obter voucher");

		LinkedHashSet<String> vouchers = obterVouchersChassiCorretor(vp.getCdChassiVeicu(), vp.getCdCrtorCia(), permite);

		if (!vouchers.isEmpty()) {

			Date dataReferenciaOnline = new Date();
			
			for (String voucher : vouchers) {

				if(!isVoucherReaproveitavel(voucher, permite)) {
					continue;
				}

				permite.addLog("Recuperando status do agendamento do voucher: " + voucher);
				StatusAgendamentoAgrupamento statusAgendamento = obterStatus(voucher);

				permite.addLog("Verificando o status do agendamento....");

				if(isVoucherReaproveitavelStatusProcessando(voucher, statusAgendamento, dataReferenciaOnline, qtDiasValidadeAgendamento, permite)) {
					return voucher;
				}
				
				if(isVoucherReaproveitavelStatusNaoProcessado(voucher, statusAgendamento, dataReferenciaOnline, permite)) {
					return voucher;
				}
				
				if(isVoucherReaproveitavelStatusProcessado(voucher, statusAgendamento, dataReferenciaOnline, qtDiasValidadeAgendamento, vp.getCdSistmOrigm(), permite)) {
					return voucher;
				}
			}
		}
		// caso não encontrar permite um novo agendamento.
		permite.addLog("Não encontrado voucher para reaproveitamento. Permitindo um novo agendamento.");

		return null;
	}
	
	private boolean isVoucherReaproveitavelStatusProcessado(String voucher,
			StatusAgendamentoAgrupamento statusAgendamento, Date dataReferenciaOnline,
			int qtDiasValidadeAgendamento, Long sistemaChamador, PermiteAgendamentoContext permite) {
		
		/*
		 * Caso esteja realizado ou finalizado verifica as regras de aproveitamento
		 * referente ao laudo
		 */
		if (FIM.getValor().equals(statusAgendamento.getCdSitucAgmto())
				|| RLZ.getValor().equals(statusAgendamento.getCdSitucAgmto())) {

			permite.addLog("Agendamento com status igual a FIM ou RLZ");
			permite.addLog("Obtendo laudo pelo codigo de voucher: " + voucher);

			Optional<LaudoVistoriaPrevia> ultimoLaudo = laudoService.obterUltimoLaudoPorVoucher(voucher);

			/* Caso encontre laudos com o número de voucher... */
			if (ultimoLaudo.isPresent()) {
				LaudoVistoriaPrevia laudo = ultimoLaudo.get();
				permite.addLog("Laudo encontrado numero: " + laudo.getCdLvpre());
				isVoucherReaproveitavelUltimoLaudo(laudo, permite, dataReferenciaOnline, qtDiasValidadeAgendamento, sistemaChamador, voucher);
			} else {

				/*
				 * Caso não encontre verifica se o agendamento é valido (data de agendamento
				 * menor ou igual a 10 dias)
				 */
				permite.addLog("Laudo não encontrado, validando a data do agendamento...");
				if (isAgendamentoValido(dataReferenciaOnline, statusAgendamento.getDtUltmaAlter(), voucher,
						qtDiasValidadeAgendamento, permite)) {

					addLogAproveitando(voucher, permite);

					return true;
				}
			}
		}
		
		return false;
	}

	private void addLogAproveitando(String voucher, PermiteAgendamentoContext permite) {
		permite.addLog("Aproveitando o voucher: " + voucher);
	}

	private boolean isVoucherReaproveitavelUltimoLaudo(LaudoVistoriaPrevia laudo, PermiteAgendamentoContext permite, Date dataReferenciaOnline, long qtDiasValidadeAgendamento, Long sistemaChamador, String voucher) {
		/* Verifica se o laudo possui parecer recusado */
		if ("R".equals(laudo.getCdSitucVspre())) {

			permite.addLog("Laudo com status Recusável. Não aproveita essa vistoria.");

			// pula para o próximo voucher (se houver) pois esse não pode ser aproveitado
			return false;
		}
		// caso o laudo seja sujeito a analise E não estiver bloqueado
		if ("S".equals(laudo.getCdSitucVspre()) && !laudo.getCdSitucBlqueVspre().equals(1L)) {

			// permite um novo agendamento se o laudo possui avaria de vidros com os códigos
			// parametrizados
			// (ver parametro CD_PAREC_SUJ_ANALISE_VIDROS_PERMT_AGD na base de dados.)
			// OU dataLimiteBloqueioVspre for menor que a dataReferenciaOnline (Permite um
			// novo agendamento).
			return !(laudoService.isLaudoComParecerLiberado(laudo) || (laudo.getDtLimitBlqueVspre() != null && DateUtil
					.truncaData(laudo.getDtLimitBlqueVspre()).compareTo(DateUtil.truncaData(dataReferenciaOnline)) < 0));
		}

		/* Verifica se o laudo não está vinculado */
		if ("N".equals(laudo.getIcLaudoVicdo())) {

			permite.addLog("Laudo não vinculado");
			/* Verifica se o laudo não está bloqueado */
			if (!laudo.getCdSitucBlqueVspre().equals(1L)) {

				permite.addLog("Laudo não bloqueado");
				addLogAproveitando(voucher, permite);

				return true;
			}
		} else {

			// Verifica se o negocio ou endosso que tem este laudo está na grade.
			if ((isEndossoSSV(sistemaChamador)
					&& itemSeguradoService.endossoEstaNaGrade(laudo.getCdLvpre())
					|| itemSeguradoService.negocioEstaNaGrade(laudo.getCdLvpre()))
					&& DateUtil.getDiferencaEmDias(dataReferenciaOnline,
							laudo.getDtVspre()) <= qtDiasValidadeAgendamento) {

				permite.addLog(
						"Aproveitando voucher por já possuir uma outra proposta/endosso na grade e laudo igual ou inferior a 10 dias. Voucher: "
								+ voucher);

				return true;
			}
		}
		
		return false;
	}

	private boolean isVoucherReaproveitavelStatusNaoProcessado(String voucher,
			StatusAgendamentoAgrupamento statusAgendamento, Date dataReferenciaOnline,
			PermiteAgendamentoContext permite) {
		/*
		 * Caso o voucher esteja cancelado (pela prestadora e area de produtos), não
		 * aprovado ou não agendado retorna o voucher para novo agendamento
		 */
		if (CAN.getValor().equals(statusAgendamento.getCdSitucAgmto())
				&& isMotivoCanclReagendar(statusAgendamento.getCdMotvSitucAgmto())
				|| NAP.getValor().equals(statusAgendamento.getCdSitucAgmto())
				|| NAG.getValor().equals(statusAgendamento.getCdSitucAgmto())
				|| VFR.getValor().equals(statusAgendamento.getCdSitucAgmto())) {

			permite.addLog("Validando o status do agendamento (CAN, NAP, NAG E VFR)...");
			permite.addLog("Satus igual a " + statusAgendamento.getCdSitucAgmto());

			permite.addLog("Validando a data do agendamento...");
			int qtdDiasVencimentoAgendamentoPendente = parametroVPService.obterQtdDiasRetrocessoAgendamento()
					.intValue();
			if (verificarAgendamentoValido(dataReferenciaOnline, statusAgendamento.getDtUltmaAlter(), voucher,
					qtdDiasVencimentoAgendamentoPendente, permite)) {

				addLogAproveitando(voucher, permite);

				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica o status do agendamento caso o ultimo status seja PEN,RCB,AGD OU
	 * RGD, verifica a data de agendamento para aproveitar ou não.
	 */
	private boolean isVoucherReaproveitavelStatusProcessando(String voucher, StatusAgendamentoAgrupamento statusAgendamento,
			Date dataReferenciaOnline, int qtDiasValidadeAgendamento, PermiteAgendamentoContext permite) {
		
		if (PEN.getValor().equals(statusAgendamento.getCdSitucAgmto())
				|| RCB.getValor().equals(statusAgendamento.getCdSitucAgmto())
				|| AGD.getValor().equals(statusAgendamento.getCdSitucAgmto())
				|| RGD.getValor().equals(statusAgendamento.getCdSitucAgmto())) {

			permite.addLog("Agendamento com status: " + statusAgendamento.getCdSitucAgmto());
			/*
			 * Caso a data de agendamento seja inferior a 10 dias da data de referencia
			 * on-line aproveita o voucher, caso contrário permite um novo agendamento.
			 */
			permite.addLog("Validando a data do agendamento...");

			if (isAgendamentoValido(dataReferenciaOnline, statusAgendamento.getDtUltmaAlter(), voucher,
					qtDiasValidadeAgendamento, permite)) {

				addLogAproveitando(voucher, permite);
				return true;
			}
		}
		
		return false;
	}

	private boolean isVoucherReaproveitavel(String voucher, PermiteAgendamentoContext context) {
		context.addLog("Recuperando pré-agendamento...");

		VistoriaPreviaObrigatoria preAgendamentoVoucher = vistoriaPreviaObrigatoriaService
				.obterPreAgendamentoPorVoucher(voucher).orElse(null);

		// se o sistema atual for endosso e o sistema do pré-agendamento não for endosso
		// e
		// não for agendamento sem proposta, então não aproveita o voucher
		if (isEndossoSSV(context.getVistoriaPrevia().getCdSistmOrigm()) && preAgendamentoVoucher != null
				&& !isEndossoSSV(preAgendamentoVoucher.getCdSistmOrigm())
				&& !isAgtoSemProposta(preAgendamentoVoucher.getCdSistmOrigm())) {

			context.addLog(
					"Não aproveita esse voucher porque ele não pode ser aproveitado para endosso. "
							+ "Sistema do voucher: " + preAgendamentoVoucher.getCdSistmOrigm());

			return false;
		}
		
		return true;
	}

	/*
	 * Valida a validade do agendamento para o parâmetro de quantidade de dias de
	 * informado e com a data do agendamento informada (se agendamento em Posto), ou
	 * com a data prevista da vistoria (se agendamento em domicílio)
	 */
	private boolean isAgendamentoValido(Date dataReferenciaOnline, Date dataAgendamento, String codigoVoucher,
			int quantidadeDiasVencimentoAgendamento, PermiteAgendamentoContext context) {

		// Se for agendamento a domicílio, utilizar a data deste.
		AgendamentoDomicilio agendamentoDomicilio = agendamentoDomicilioService.obterAgendamentoDomicilio(codigoVoucher)
				.orElse(null);
		if (agendamentoDomicilio != null) {
			dataAgendamento = agendamentoDomicilio.getDtVspre();
		}

		return verificarAgendamentoValido(dataReferenciaOnline, dataAgendamento, codigoVoucher,
				quantidadeDiasVencimentoAgendamento, context);
	}

	/*
	 * Valida a validade do agendamento para o parâmetro de quantidade de dias de
	 * informado e com a data do agendamento informada
	 */
	private boolean verificarAgendamentoValido(Date dataReferenciaOnline, Date dataAgendamento, String codigoVoucher,
			int quantidadeDiasVencimentoAgendamento, PermiteAgendamentoContext context) {

		boolean isAgendamentoValido = Boolean.FALSE;

		if (DateUtil.getDiferencaEmDias(dataReferenciaOnline,
				DateUtil.truncaData(dataAgendamento)) <= quantidadeDiasVencimentoAgendamento) {

			context.addLog(
					"Agendamento " + codigoVoucher + " com data inferior a " + quantidadeDiasVencimentoAgendamento
							+ " dias, será aproveitado, data: " + DateUtil.formataData(dataAgendamento));

			isAgendamentoValido = Boolean.TRUE;
		} else {

			context.addLog(
					"Agendamento " + codigoVoucher + " com data superior a " + quantidadeDiasVencimentoAgendamento
							+ " dias, não será aproveitado, data: " + DateUtil.formataData(dataAgendamento));

		}

		return isAgendamentoValido;
	}
	

	/** Trecho comentado para obter agendamento somente por corretor + chassi.
	* (#Solicitado por Sergio Avena - OS284882 - 23/06/2015)
	* Obtém vouchers por placa+chassi+corretor
	* adicionaLog("Obter voucher por placa: " + placa + " + chassi :" + chassi + corretor: " + corretor,logObterVoucher);
	* List<String> vouchers =
	* regrasVPDAO.obterVoucherMesmoVeiculo(placa,chassi,corretor);
	* adicionaLog("Vouchers encontrados por placa, chassi e corretor: " +
	* vouchers,logObterVoucher);
	* vouchersRetorno.addAll(vouchers);
	* 
	* Trecho comentado para obter agendamento somente por corretor + chassi.
	* (#Solicitado por Sergio Avena - OS284882 - 23/06/2015)
	* Obtém vouchers por Placa+Corretor
	* adicionaLog("Obter voucher por placa: " + placa + " + corretor " +
	* corretor,logObterVoucher);
	* vouchers = regrasVPDAO.obterVoucherMesmoVeiculo(placa,null,corretor);
	* adicionaLog("Vouchers encontrados por placa e corretor: " +
	* vouchers,logObterVoucher);
	* vouchersRetorno.addAll(vouchers);
	*/
	private LinkedHashSet<String> obterVouchersChassiCorretor(String chassi, Long corretor,
			PermiteAgendamentoContext context) {

		LinkedHashSet<String> vouchersRetorno = new LinkedHashSet<>();

		// Obtém vouchers por chassi+corretor
		context.addLog("Obter voucher por chassi: " + chassi + " + corretor: " + corretor);
		
		List<String> vouchers = vistoriaPreviaObrigatoriaService.obterVoucherMesmoVeiculo(null, chassi, corretor);
		
		context.addLog("Vouchers encontrados por chassi e corretor: " + vouchers);
		
		vouchersRetorno.addAll(vouchers);

		return vouchersRetorno;
	}
}
