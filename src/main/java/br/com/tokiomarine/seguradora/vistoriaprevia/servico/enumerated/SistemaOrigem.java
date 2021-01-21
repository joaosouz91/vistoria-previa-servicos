package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import lombok.Getter;

public enum SistemaOrigem {

	ORIGEM_SSV(1L, "TS"), 
	ORIGEM_KCW(2L, null), 
	ORIGEM_PLATAFORMA(3L, "TB"), 
	ORIGEM_SEM_PROPOSTA(4L, "TT");

	@Getter
	private Long codigo;

	@Getter
	private String letra;

	private SistemaOrigem(Long codigo, String letra) {
		this.codigo = codigo;
		this.letra = letra;
	}
	
	public static SistemaOrigem retornaSistemaOrigem(Long cdSistemaChamador) {

		if (SistemaChamador.ENDOSSOSSV.equals(cdSistemaChamador) 
				|| SistemaChamador.ENDOSSOWEB.equals(cdSistemaChamador)
				|| SistemaChamador.EMISSAOSSV.equals(cdSistemaChamador) 
				|| SistemaChamador.RECEPSSV.equals(cdSistemaChamador)
				|| SistemaChamador.RESTRBONUS.equals(cdSistemaChamador)
				|| SistemaChamador.LIBERACAOPROPOSTA.equals(cdSistemaChamador)
				|| SistemaChamador.LIBERACAOENDOSSOSSV.equals(cdSistemaChamador)
				|| SistemaChamador.LIBERACAOENDOSSOWEB.equals(cdSistemaChamador)) {

			return ORIGEM_SSV;

		} else if (SistemaChamador.KCW.equals(cdSistemaChamador) || SistemaChamador.MULTICALCULO.equals(cdSistemaChamador)) {

			return ORIGEM_KCW;

		} else if (SistemaChamador.EMISSAOPLAT.equals(cdSistemaChamador)
				|| SistemaChamador.ENDOSSOPLAT.equals(cdSistemaChamador)) {

			return ORIGEM_PLATAFORMA;

		} else if (SistemaChamador.AGTOSEMPROPOSTATMS.equals(cdSistemaChamador)
				|| SistemaChamador.AGTOSEMPROPOSTATMB.equals(cdSistemaChamador)) {

			return ORIGEM_SEM_PROPOSTA;
		}

		return null;
	}
}
