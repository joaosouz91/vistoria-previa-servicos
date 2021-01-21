package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import java.io.Serializable;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VistoriaObrigatoria implements Serializable {

	private static final long serialVersionUID = -2404853341413396472L;

	private VistoriaPreviaObrigatoria vistoria;
	private AgendamentoVistoriaPrevia agendamento;
	
	
}
