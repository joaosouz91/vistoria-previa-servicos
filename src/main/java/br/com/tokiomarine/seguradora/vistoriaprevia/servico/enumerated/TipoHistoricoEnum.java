package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import lombok.Getter;

@Getter
public enum TipoHistoricoEnum {

	TIPO_HISTORICO_NEGOCIO("0"), 
	TIPO_HISTORICO_ITEM("0");

	private String codigo;

	private TipoHistoricoEnum(String codigo) {
		this.codigo = codigo;
	}
}
