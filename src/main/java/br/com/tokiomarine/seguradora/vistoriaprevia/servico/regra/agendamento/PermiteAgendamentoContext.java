package br.com.tokiomarine.seguradora.vistoriaprevia.servico.regra.agendamento;

import java.io.Serializable;

import org.apache.commons.chain.impl.ContextBase;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoAgendamentoNaoPermitidoEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class PermiteAgendamentoContext extends ContextBase implements Serializable {

	private static final long serialVersionUID = 2916002831713194964L;

	VistoriaPreviaObrigatoria vistoriaPrevia;
	@EqualsAndHashCode.Include
	private String voucher;
	private Boolean permiteAgendamento;
	private MotivoAgendamentoNaoPermitidoEnum motivoAgendamentoNaoPermitido;
	private StringBuilder log;

	public PermiteAgendamentoContext(VistoriaPreviaObrigatoria vistoriaPrevia) {
		this.vistoriaPrevia = vistoriaPrevia;
		this.log = new StringBuilder();
	}
	
	public void addLog(String linhaLog) {
		log.append(linhaLog + "\r\n");
	}
}