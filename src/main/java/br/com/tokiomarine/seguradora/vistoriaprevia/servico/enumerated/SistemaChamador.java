package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public class SistemaChamador {

	public static final Long KCW = 1L;
	public static final Long EMISSAOPLAT = 2L;
	public static final Long ENDOSSOPLAT = 3L;
	public static final Long EMISSAOSSV = 4L;
	public static final Long RECEPSSV = 5L;
	public static final Long ENDOSSOSSV = 6L;
	public static final Long ENDOSSOWEB = 7L;
	public static final Long RESTRBONUS = 8L;
	public static final Long MULTICALCULO = 9L;
	public static final Long LIBERACAOPROPOSTA = 10L;
	public static final Long LIBERACAOENDOSSOSSV = 11L;
	public static final Long LIBERACAOENDOSSOWEB = 12L;
	public static final Long TRASMISSAOKCW = 13L;
	public static final Long AGTOSEMPROPOSTATMS = 14L;
	public static final Long AGTOSEMPROPOSTATMB = 15L;
	public static final Long SISTEMAVINCULO = 16L;
	public static final Long AUTOCOMPARASANTANDER = 17L;
	public static final Long EMISSAOCOTADOR = 20L;
	public static final Long TRANSMISSAOCOTADOR = 21L;
	public static final Long WSCOTADOR = 22L;
	public static final Long COTADORFROTA = 23L; // Validacao de regras via Blaze
	public static final Long GERACAORESTRICAO = 24L; // Validacao de regras via Blaze
	public static final Long ENDOSSOCTF = 25L; // Validacao de regras via Blaze

	private SistemaChamador() {
	}

	public static boolean isEndossoSSV(Long sistemaChamador) {

		return (ENDOSSOSSV.equals(sistemaChamador) || ENDOSSOWEB.equals(sistemaChamador)
				|| ENDOSSOCTF.equals(sistemaChamador) || LIBERACAOENDOSSOSSV.equals(sistemaChamador)
				|| LIBERACAOENDOSSOWEB.equals(sistemaChamador)); 
	}

	public static boolean isSSV(Long sistemaChamador) {

		return (EMISSAOSSV.equals(sistemaChamador) || LIBERACAOPROPOSTA.equals(sistemaChamador)
				|| RECEPSSV.equals(sistemaChamador) || RESTRBONUS.equals(sistemaChamador)
				|| ENDOSSOSSV.equals(sistemaChamador) || ENDOSSOWEB.equals(sistemaChamador)
				|| LIBERACAOENDOSSOSSV.equals(sistemaChamador) || LIBERACAOENDOSSOWEB.equals(sistemaChamador));
	}

	public static boolean isPlataforma(Long sistemaChamador) {
		return (EMISSAOPLAT.equals(sistemaChamador) || ENDOSSOPLAT.equals(sistemaChamador));
	}

	public static boolean isKCW(Long sistemaChamador) {
		return (KCW.equals(sistemaChamador) || MULTICALCULO.equals(sistemaChamador)
				|| AUTOCOMPARASANTANDER.equals(sistemaChamador) || TRASMISSAOKCW.equals(sistemaChamador)); 
	}

	public static boolean isCotador(Long sistemaChamador) {

		return (EMISSAOCOTADOR.equals(sistemaChamador) || TRANSMISSAOCOTADOR.equals(sistemaChamador)
				|| WSCOTADOR.equals(sistemaChamador)); 
	}

	public static boolean isLiberacao(Long sistemaChamador) {
		return (LIBERACAOPROPOSTA.equals(sistemaChamador) || LIBERACAOENDOSSOSSV.equals(sistemaChamador)
				|| LIBERACAOENDOSSOWEB.equals(sistemaChamador) || TRASMISSAOKCW.equals(sistemaChamador));
	}

	public static boolean isAgtoSemProposta(Long sistemaChamador) {

		return (AGTOSEMPROPOSTATMS.equals(sistemaChamador) || AGTOSEMPROPOSTATMB.equals(sistemaChamador));
	}
}
