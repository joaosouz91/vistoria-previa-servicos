package br.com.tokiomarine.seguradora.vistoriaprevia.servico.regra.agendamento;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class PermiteAgendamentoFilter implements Filter {
	
	private static final Logger LOGGER = LogManager.getLogger(PermiteAgendamentoFilter.class);

	@Override
	public boolean execute(Context context) throws Exception {
		PermiteAgendamentoContext permite = (PermiteAgendamentoContext) context;
		
		LOGGER.info("PermiteAgendamentoFilter - execute");
		permite.setPermiteAgendamento(true);
//		adicionarLinhaLog("Não Encontrado voucher para esse veículo. Permite agendamento.", logPermiteAgendamento);
//		permite.setPermiteAgendamento(true);
//		gravaLogDebug(logPermiteAgendamento, vp);
		return false;
	}

	@Override
	public boolean postprocess(Context context, Exception exception) {
		LOGGER.info("PermiteAgendamentoFilter - postprocess");
		return false;
	}

}
