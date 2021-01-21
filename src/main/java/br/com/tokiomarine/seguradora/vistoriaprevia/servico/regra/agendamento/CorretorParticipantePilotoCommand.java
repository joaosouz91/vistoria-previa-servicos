package br.com.tokiomarine.seguradora.vistoriaprevia.servico.regra.agendamento;

import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoAgendamentoNaoPermitidoEnum.MOTIVO_CORRETOR_NAO_PARTICIPA;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.ParametroVPService;

@Component
public class CorretorParticipantePilotoCommand implements Command {

	private ParametroVPService parametroVPService;
	
	public CorretorParticipantePilotoCommand(ParametroVPService parametroVPService) {
		this.parametroVPService=parametroVPService;
	}
	
	@Override
	public boolean execute(Context context) throws Exception {
		PermiteAgendamentoContext permite = (PermiteAgendamentoContext) context;
		VistoriaPreviaObrigatoria vp = permite.getVistoriaPrevia();
		
		if (!parametroVPService.isCorretorParticipantePiloto(vp.getCdCrtorCia())) {

			permite.addLog("Corretor do pré-agendamento não participa do piloto. Não permite agendamento.");
			permite.setPermiteAgendamento(false);
			permite.setMotivoAgendamentoNaoPermitido(MOTIVO_CORRETOR_NAO_PARTICIPA);
			
			return true;
		}
		
		return false;
	}

}
