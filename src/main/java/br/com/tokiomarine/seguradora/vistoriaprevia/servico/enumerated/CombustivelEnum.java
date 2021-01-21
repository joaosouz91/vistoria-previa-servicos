package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import lombok.Getter;

@Getter
public enum CombustivelEnum {

	
	ALCOOL("A","ALCOOL"),
	DIESEL("D","DIESEL"),
	ELETRICO("E","ELETRICO"),
	FLEX("F","FLEX"),
	GASOLINA("G","GASOLINA"),
	HIBRIDO("H","HÍBRIDO"),
	MULTI_FUEL("M","MULTI_FUEL"),
	SEM_COMBUSTIVEL("S","SEM_COMBUSTÍVEL");
	
	private String codigo;

	private String descricao;
	
	private CombustivelEnum(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public static String obterDescricao(String codigo) {
		
		for(CombustivelEnum s : CombustivelEnum.values()) {
			if(s.getCodigo().equals(codigo))
				return s.getDescricao();
		}
		return "";
	}
}
