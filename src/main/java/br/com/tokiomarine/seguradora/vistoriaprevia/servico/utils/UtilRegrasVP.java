package br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils;



/**
 * @author Rafael Moreira
 */
public class UtilRegrasVP {

	public static boolean isEndossoSSV(Long sistemaChamador) {

		return (ConstantesRegrasVP.ENDOSSO_SSV.equals(sistemaChamador) || 
				ConstantesRegrasVP.ENDOSSO_WEB.equals(sistemaChamador) || 
				ConstantesRegrasVP.ENDOSSO_CTF.equals(sistemaChamador) || 
				ConstantesRegrasVP.LIBERACAO_ENDOSSO_SSV.equals(sistemaChamador) || 
				ConstantesRegrasVP.LIBERACAO_ENDOSSO_WEB.equals(sistemaChamador)); 
		
	}

	public static boolean isSSV(Long sistemaChamador) {

		return (ConstantesRegrasVP.EMISSAO_SSV.equals(sistemaChamador) || 
				ConstantesRegrasVP.LIBERACAO_PROPOSTA.equals(sistemaChamador) || 
				ConstantesRegrasVP.RECEP_SSV.equals(sistemaChamador) || 
				ConstantesRegrasVP.RESTR_BONUS.equals(sistemaChamador) || 
				ConstantesRegrasVP.ENDOSSO_SSV.equals(sistemaChamador) || 
				ConstantesRegrasVP.ENDOSSO_WEB.equals(sistemaChamador) || 
				ConstantesRegrasVP.LIBERACAO_ENDOSSO_SSV.equals(sistemaChamador) || 
				ConstantesRegrasVP.LIBERACAO_ENDOSSO_WEB.equals(sistemaChamador)); 
	}

	public static boolean isPlataforma(Long sistemaChamador) {

		return (ConstantesRegrasVP.EMISSAO_PLAT.equals(sistemaChamador) 
				|| ConstantesRegrasVP.ENDOSSO_PLAT.equals(sistemaChamador)); 
	}

	public static boolean isKCW(Long sistemaChamador) {
		return (ConstantesRegrasVP.KCW.equals(sistemaChamador) || 
				ConstantesRegrasVP.MULTICALCULO.equals(sistemaChamador) || 
				ConstantesRegrasVP.AUTOCOMPARA_SANTANDER.equals(sistemaChamador) || 
				ConstantesRegrasVP.TRASMISSAO_KCW.equals(sistemaChamador)); 
	}

	public static boolean isCotador(Long sistemaChamador) {

		return (ConstantesRegrasVP.EMISSAO_COTADOR.equals(sistemaChamador) ||
			ConstantesRegrasVP. TRANSMISSAO_COTADOR.equals(sistemaChamador) || 
			ConstantesRegrasVP.WS_COTADOR.equals(sistemaChamador)); 
	}

	public static boolean isLiberacao(Long sistemaChamador) {
		return (ConstantesRegrasVP.LIBERACAO_PROPOSTA.equals(sistemaChamador) || 
				ConstantesRegrasVP.LIBERACAO_ENDOSSO_SSV.equals(sistemaChamador) || 
				ConstantesRegrasVP.LIBERACAO_ENDOSSO_WEB.equals(sistemaChamador) || 
				ConstantesRegrasVP.TRASMISSAO_KCW.equals(sistemaChamador)); 
	}

	public static boolean isAgtoSemProposta(Long sistemaChamador) {

		return (ConstantesRegrasVP.AGTO_SEM_PROPOSTA_TMS.equals(sistemaChamador) || 
				ConstantesRegrasVP.AGTO_SEM_PROPOSTA_TMB.equals(sistemaChamador)); 
	}

	/**
	 * Obtm chave para o log do GPA das mensagens utilizadas na verificao de VP
	 *
	 * @param parametroRegrasVP
	 * @return
	 */
	public static String obterChaveLogVerificaVP(Long sistemaChamador,Long codigoEndosso,Long codigoNegocio,Long numeroItem,Long numeroCalculo) {

		String chave = null;

		if (ConstantesRegrasVP.ENDOSSO_SSV.equals(sistemaChamador) || ConstantesRegrasVP.ENDOSSO_WEB.equals(sistemaChamador) || ConstantesRegrasVP.LIBERACAO_ENDOSSO_SSV.equals(sistemaChamador) || ConstantesRegrasVP.LIBERACAO_ENDOSSO_WEB.equals(sistemaChamador)) {

			chave = codigoEndosso.toString();
		}

		if (ConstantesRegrasVP.EMISSAO_PLAT.equals(sistemaChamador) || ConstantesRegrasVP.ENDOSSO_PLAT.equals(sistemaChamador)) {

			chave = "" + codigoNegocio + "_" + numeroItem;
		}

		if (ConstantesRegrasVP.EMISSAO_SSV.equals(sistemaChamador) || ConstantesRegrasVP.LIBERACAO_PROPOSTA.equals(sistemaChamador) || ConstantesRegrasVP.RECEP_SSV.equals(sistemaChamador) || ConstantesRegrasVP.RESTR_BONUS.equals(sistemaChamador)) {

			chave = "" + numeroItem;
		}

		if (isKCW(sistemaChamador) || isCotador(sistemaChamador)) {

			chave = numeroCalculo.toString();
		}

		return chave;
	}

	public static boolean exibirMotivoCancelada(Long cdMotvSitucAgmto) {

		return !(ConstantesRegrasVP.CANCELAMENTO_A_CONFIRMAR.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_CONFIRMADO.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_DE_NAG.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_FORA_SISTEMA_CONFIRMADO.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_VISTORIADORA.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_AGTO_POR_ATRASO.equals(cdMotvSitucAgmto)); 
	}
	
	private UtilRegrasVP() {
	    throw new IllegalStateException("Utility class");
	  }
}
