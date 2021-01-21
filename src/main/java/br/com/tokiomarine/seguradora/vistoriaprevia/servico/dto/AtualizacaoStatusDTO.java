package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoDomicilio;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.StatusAgendamentoAgrupamento;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class AtualizacaoStatusDTO implements Serializable {
	
	private static final long serialVersionUID = 4442796852209393919L;

	@EqualsAndHashCode.Include
	private String cdVoucher;
	
	private StatusAgendamentoAgrupamento statusAtual;
	
	private StatusAgendamentoAgrupamento novoStatus;
	
	private AgendamentoDomicilio agendamentoDomicilio;
}
