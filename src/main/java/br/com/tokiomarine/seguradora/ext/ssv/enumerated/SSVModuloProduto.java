package br.com.tokiomarine.seguradora.ext.ssv.enumerated;

/**
 * Enum de Modulo Produto do SSV, utilizado pelos sistemas da Grade de Aceita��o
 * @author E105892
 *
 */
public enum SSVModuloProduto {

	AUTOPASSEIO(7L),
	AUTOCARGA(9L),
	AUTOCLASSICO(20L),
	CAMINHAO(24L),
	UTILITARIOCARGA(25L),
	PASSEIOPOPULAR(21L),
	CAMINHAOPOPULAR(22L),
	UTILITARIOPOPULAR(23L);

	private Long codigoModuloProduto;

	private SSVModuloProduto(Long moduloProduto) {
		codigoModuloProduto = moduloProduto;
	}

	public static SSVModuloProduto getModuloProduto(Long codigoModuloProduto){

		for(SSVModuloProduto modulo : values()){

			if(modulo.getCodigoModuloProduto().equals(codigoModuloProduto)){
				return modulo;
			}
		}
		return null;
	}

	public Long getCodigoModuloProduto() {
		return codigoModuloProduto;
	}


}
