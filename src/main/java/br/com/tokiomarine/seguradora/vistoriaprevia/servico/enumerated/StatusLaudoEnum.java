package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public enum StatusLaudoEnum {
	
	RECUSADA("R","RECUSADA"),
	ACEITACAO_FRUSTRADA("F","ACEITAÇÃO FRUSTRADA"),
	ACEITACAO_FORCADA("AF","ACEITAÇÃO FORÇADA"),
	REGULARIZACAO_VISTORIA("RR","REGULARIZAÇÃO VISTORIA"),
	SUJEITO_A_ANALISE("S","SUJEITO À ANALISE"),
	LIBERADA("L","LIBERADA"),
	ACEITAVEL("A","ACEITÁVEL"),
	REGULARIZADO("RV","REGULARIZADO"),
	ACEITAVEL_LIBERADA("AL","ACEITÁVEL LIBERADA");

	private String codigo;
	private String descricao;
	
	private StatusLaudoEnum(String codigo, String descricao) {
		
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	

	public static String obterDescricao(String codigo) {
		
		for(StatusLaudoEnum s : StatusLaudoEnum.values()) {
			if(s.getCodigo().equals(codigo))
				return s.getDescricao();
		}
		return "";
	}
	
	public static StatusLaudoEnum obterPorCodigo(String codigo) {
		
		for(StatusLaudoEnum s : StatusLaudoEnum.values()) {
			if(s.getCodigo().equals(codigo))
				return s;
		}
		
		return null;
	}
}
