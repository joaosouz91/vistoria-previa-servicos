package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import lombok.Getter;

public enum TipoContatoEmailEnum {

	C("C"), S("S"), T_3("3");

	@Getter
	private String codigo;

	private TipoContatoEmailEnum(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return codigo;
	}
}
