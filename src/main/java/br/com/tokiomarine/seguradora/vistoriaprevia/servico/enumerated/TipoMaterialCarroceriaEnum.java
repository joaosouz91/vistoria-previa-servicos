package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import lombok.Getter;

@Getter
public enum TipoMaterialCarroceriaEnum {

	MADEIRA(1L,"MADEIRA"),
	CHAPA_DE_ALUMINIO(2L,"CHAPA DE ALUMINIO"),
	CHAPA_DE_ACO(3L,"CHAPA DE AÇO"),
	FIBRA(4L,"FIBRA"),
	OUTROS(99L,"OUTROS");
	
	private Long codigo;
	private String descricao;
	
	private TipoMaterialCarroceriaEnum(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static String obterDescricao(Long codigo) {
		for(TipoMaterialCarroceriaEnum s : TipoMaterialCarroceriaEnum.values()) {
			if(s.getCodigo().equals(codigo))
				return s.getDescricao();
		}
		return "";
	}
}