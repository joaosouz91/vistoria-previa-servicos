package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public enum TipoSeguroEnum {

	
	SEGURO_NOVO("1","SEGURO_NOVO"),
	RENOV_CONGENERE_COM_SINISTRO("2","RENOV. CONGENERE COM SINISTRO"),
	RENOV_CONGENERE_SEM_SINISTRO("3","RENOV. CONGENERE SEM SINISTRO"),
	RENOV_TOKIO_COM_SINISTRO("4","RENOV. TOKIO COM SINISTRO"),
	RENOV_TOKIO_SEM_SINISTRO("5","RENOV. TOKIO SEM SINISTRO");
	
	
	
	private String codigo;
	private String descricao;
	
	
	private TipoSeguroEnum(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
		
	}


	public String getCodigo() {
		return codigo;
	}


	public String getDescricao() {
		return descricao;
	}

	
	
	
}
