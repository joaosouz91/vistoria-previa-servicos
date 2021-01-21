package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;


public enum TipoCondutorEnum {
	
	CONJUGUE(01,"CONJUGUE/SEGURADO"),
	PARENTE(02,"PARENTE"),
	EMPREGADO(03,"EMPREGADO"),
	OUTROS(99,"OUTROS");
	
	
	
	private TipoCondutorEnum(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	
	private Integer codigo;
	private String descricao;
	
	
	
	
	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}


	public static String obterDescricao(Integer codigo) {
			
			for(TipoCondutorEnum s : TipoCondutorEnum.values()) {
				if(s.getCodigo() == codigo)
					return s.getDescricao();
			}
			return "";
		}
		
	

}
