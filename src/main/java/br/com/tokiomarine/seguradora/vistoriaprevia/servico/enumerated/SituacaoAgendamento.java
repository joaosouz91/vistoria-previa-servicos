package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)

public enum SituacaoAgendamento  {
	
	/**
	 * Aguardando Confirmação
	 * COD_SITUC_PENDENTE
	 */
	PEN("PEN", "Aguardando Confirmação"), 

	/**
	 * Agendada
	 * COD_SITUC_AGENDADA
	 */
	AGD("AGD", "Agendada"), 
	
	/**
	 * Reagendada
	 * COD_SITUC_REAGENDADA
	 */
	RGD("RGD", "Reagendada"),
	
	/**
	 * Cancelada
	 * COD_SITUC_CANCELADA
	 */
	CAN("CAN", "Cancelada"),
	
	/**
	 * Frustrada
	 * COD_SITUC_FRUSTRADA
	 */
	VFR("VFR", "Frustrada"), 
	
	/**
	 * Realizada
	 * COD_SITUC_REALIZADA
	 */
	RLZ("RLZ", "Realizada"),
	
	/**
	 * Aguardando Confirmação
	 * COD_SITUC_RECEBIDA
	 */
	RCB("RCB", "Aguardando Confirmação"),
	
	/**
	 * Fim
	 * COD_SITUC_FIM
	 */
	FIM("FLZ", "Finalizada"),
	
	/**
	 * Não Agendada
	 * COD_SITUC_NAO_AGENDADA
	 */
	NAG("NAG", "Não Agendada"),
	
	/**
	 * Não Aprovada
	 * COD_SITUC_NAO_APROVADA
	 */
	NAP("NAP", "Não Aprovada"),
	
	
	FTR("FTR", "Fotos Recepcionadas"),
	
	FTT("FTT", "Realizada"),
	
	PEF("PEF", "Pendência de Fotos"),
	
	LKX("LKX", "Link Expirado");

	
	
	private String valor;
	private String descricao;
	
	private SituacaoAgendamento(String valor, String descricao) {
		this.valor = valor;
		this.descricao = descricao;
	}

	public String getValor() {
		return valor;
	}

	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return valor;
	}
}
