package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public enum TipoVeiculoUtilEnum {

	PARTICULAR(1,"PARTICULAR"),
	PARTICULAR_TRABALHO(2,"PARTICULAR_TRABALHO"),
	FRETE(3,"FRETE"),
	TAXI(4,"TAXI"),
	OUTROS(99,"OUTROS");
	
	private Integer codigo;
	private String descricao;
	
	
	private TipoVeiculoUtilEnum(Integer codigo, String descricao) {
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
