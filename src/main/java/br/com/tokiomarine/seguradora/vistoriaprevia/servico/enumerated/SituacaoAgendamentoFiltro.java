package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SituacaoAgendamentoFiltro  {

	COD_SITUC_A_AGENDAR("A_AGENDAR", "Agendar"), 
	COD_SITUC_PENDENTE("PEN", "Aguardando Confirmação"), 
	COD_SITUC_AGENDADA("AGD", "Agendada"), 
	COD_SITUC_REAGENDADA("RGD", "Reagendada"),
	COD_SITUC_A_REAGENDAR("A_REAGENDAR", "Reagendar"),
	COD_SITUC_CANCELADA("CAN", "Cancelada"),
	COD_SITUC_FRUSTRADA("VFR", "Frustrada"), 
	COD_SITUC_REALIZADA("RLZ", "Realizada"),
	COD_SITUC_FOTOS_RECEPCIONADAS("FTR", "Fotos Recepcionadas"),
	COD_SITUC_LINK_EXPIRADO("LKX", "Link Expirado"),
	COD_SITUC_PENDENTE_FOTOS("PEF", "Pendente de Novas Fotos");
	
	private String value;
	private String descricao;
	
	private SituacaoAgendamentoFiltro(String value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
