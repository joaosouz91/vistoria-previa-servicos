package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public enum EstadoCivilEnum {
	
	SOLTEIRO("S","SOLTEIRO"),
	CASADO("C","CASADO"),
	VIUVO("V","VIUVO"),
	DESQUITADO("D","DESQUITADO"),
	DIVORCIADO("I","DIVORCIADO");
	
	
	
	private EstadoCivilEnum(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	private String codigo;
	private String descricao;
	

	public String getCodigo() {
		return codigo;
	}
	
	
	public String getDescricao() {
		return descricao;
	}
	
	
	public static String obterDescricao(String codigo) {
		
		for(EstadoCivilEnum s : EstadoCivilEnum.values()) {
			if(s.getCodigo().equals(codigo))
				return s.getDescricao();
		}
		return "";
	}
	

}
