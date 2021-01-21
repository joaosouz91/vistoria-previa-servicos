package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import lombok.Getter;

@Getter
public enum TipoGaragemEnum {
	
	EM_QUALQUER_CIRCUNSTANCIA(1,"EM QUALQUER CIRCUNSTÂNCIA"),
	RESIDENCIA_E_TRABALHO(2,"RESIDENCIA E TRABALHO"),
	RESIDENCIA_OU_TRABALHO(3,"RESIDENCIA OU TRABALHO"),
	RARAMENTE(4,"RARAMENTE");
	
	private Integer codigo;
	private String descricao;
	
	private TipoGaragemEnum(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
}