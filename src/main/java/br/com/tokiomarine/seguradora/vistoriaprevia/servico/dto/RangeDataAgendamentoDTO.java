package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RangeDataAgendamentoDTO implements Serializable {

	private static final long serialVersionUID = 1208648827692990562L;

	private String periodoVistoria;
	private LocalDate maxDate;
	private LocalDate minDate;
}
