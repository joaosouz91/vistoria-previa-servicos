package br.com.tokiomarine.seguradora.vistoriaprevia.servico.regra.agendamento;

import org.apache.commons.chain.impl.ChainBase;
import org.springframework.stereotype.Component;

@Component
public class PermiteAgendamentoChain extends ChainBase {

	public PermiteAgendamentoChain(
			CorretorParticipantePilotoCommand corretorParticipantePilotoCommand,
			SistemaOrigemNaoPLTCommand sistemaOrigemNaoPLTCommand,
			ContemVoucherCommand contemVoucherCommand,
			ProcuraAgendamentoVeiculoCommand procuraAgendamentoVeiculoCommand,
			PermiteAgendamentoFilter permiteAgendamentoFilter
			) {
		frozen=false;
		addCommand(corretorParticipantePilotoCommand);
		addCommand(sistemaOrigemNaoPLTCommand);
		addCommand(contemVoucherCommand);
		addCommand(procuraAgendamentoVeiculoCommand);
		addCommand(permiteAgendamentoFilter);
	}
}
