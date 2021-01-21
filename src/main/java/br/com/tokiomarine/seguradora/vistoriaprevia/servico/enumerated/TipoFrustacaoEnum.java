package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import lombok.Getter;

@Getter
public enum TipoFrustacaoEnum {
	
	VEICULO_AUSENTE(1L,"VEÍCULO AUSENTE"),
	ENDERECO_NAO_LOCALIZADO(2L, "ENDEREÇO NÃO LOCALIZADO"),
	SEGURADO_AUSENTE(3L, "SEGURADO AUSENTE"),
	VEICULO_EM_REPARO(4L,"VEÍCULO EM REPARO");
	
	private Long codigo;
	private String descricao;
	
	private TipoFrustacaoEnum(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static String obterDescricao(Long codigo) {
		
		for(TipoFrustacaoEnum s : TipoFrustacaoEnum.values()) {
			if(s.getCodigo().equals(codigo))
				return s.getDescricao();
		}
		return "";
	}
}