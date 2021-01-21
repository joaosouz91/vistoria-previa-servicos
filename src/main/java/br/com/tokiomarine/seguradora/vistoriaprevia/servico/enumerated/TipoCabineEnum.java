package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public enum TipoCabineEnum {
	
	SIMPLES(1,"SIMPLES"),
	DUPLA(2,"DUPLA"),
	SUPER_CABINE(3,"SUPER CABINE"),
	LEITO(4,"LEITO");
	
	private Integer codigo;
	private String descricao;
	
	
	private TipoCabineEnum(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}


	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	
}
