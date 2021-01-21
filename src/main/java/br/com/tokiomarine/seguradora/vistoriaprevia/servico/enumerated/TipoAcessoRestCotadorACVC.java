package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum para tipo de acesso ao resto do cotador.
 * @author E105892
 *
 */
@Getter
@AllArgsConstructor
public enum TipoAcessoRestCotadorACVC {

	REST_COTADOR_AC_VC("STR_AC_VC"), REST_COTADOR_AC("STR_AC");

	private String tipoAcessoRest;
}
