package br.com.tokiomarine.seguradora.vistoriaprevia.servico.regra.agendamento;

import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.StatusAgendamentoDTO.isMotivoCanclReagendar;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoAgendamentoNaoPermitidoEnum.MOTIVO_AGENDAMENTO_JA_REALIZADO;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.CAN;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.NAG;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.NAP;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.VFR;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.StatusAgendamentoAgrupamento;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.StatusAgendamentoService;

@Component
public class ContemVoucherCommand implements Command {

	private StatusAgendamentoService statusAgendamentoService;
	
	public ContemVoucherCommand(StatusAgendamentoService statusAgendamentoService) {
		this.statusAgendamentoService = statusAgendamentoService;
	}
	
	@Override
	public boolean execute(Context context) throws Exception {
		PermiteAgendamentoContext permite = (PermiteAgendamentoContext) context;
		VistoriaPreviaObrigatoria vp = permite.getVistoriaPrevia();
		
		if (vp.getCdVouch() != null) {

			permite.addLog("Pré agendamento com voucher. Obtendo status do agendamento...");
			StatusAgendamentoAgrupamento status = statusAgendamentoService.obterStatusAgendamento(vp.getCdVouch())
					.orElseThrow(() -> new BusinessVPException("Pré agendamento com voucher. Status do agendamento não localizado: "+vp.getCdVouch()));

			if (VFR.getValor().equals(status.getCdSitucAgmto()) || NAG.getValor().equals(status.getCdSitucAgmto())
					|| NAP.getValor().equals(status.getCdSitucAgmto())
					|| CAN.getValor().equals(status.getCdSitucAgmto())
							&& isMotivoCanclReagendar(status.getCdMotvSitucAgmto())) {

				permite.addLog("Status do agendamento: " + status.getCdSitucAgmto() + ". Permite agendamento.");
				permite.setPermiteAgendamento(true);

				return true;
			}

			permite.addLog("Status do agendamento: " + status.getCdSitucAgmto() + ". Não permite agendamento.");
			permite.setPermiteAgendamento(false);
			permite.setMotivoAgendamentoNaoPermitido(MOTIVO_AGENDAMENTO_JA_REALIZADO);
			permite.setVoucher(vp.getCdVouch());
			
			return true;
		}

		return false;
	}
}
