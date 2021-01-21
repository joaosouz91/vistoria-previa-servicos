package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;

@Getter
@JsonFormat(shape = Shape.OBJECT)
public enum PeriodoAgendamentoEnum {

	/**
	 * PERIODO_MANHA
	 */
	M("M", "MANHÃ"),

	/**
	 * PERIODO_TARDE
	 */
	T("T", "TARDE"),

	/**
	 * PERIODO_COMERCIAL
	 */
	C("C", "COMERCIAL");
	
	private String valor;
	private String descricao;
	
	private PeriodoAgendamentoEnum(String valor, String descricao) {
		this.valor=valor;
		this.descricao=descricao;
	}

}
