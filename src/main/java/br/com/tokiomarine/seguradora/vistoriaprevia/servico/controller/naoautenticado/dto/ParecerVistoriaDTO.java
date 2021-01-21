package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.ALWAYS)
public class ParecerVistoriaDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 716995804135359741L;
	private Long cdPatecVspre;
	private String dsPatecVspre;

	public Long getCdPatecVspre() {
		return cdPatecVspre;
	}

	public void setCdPatecVspre(Long cdPatecVspre) {
		this.cdPatecVspre = cdPatecVspre;
	}

	public String getDsPatecVspre() {
		return dsPatecVspre;
	}

	public void setDsPatecVspre(String dsPatecVspre) {
		this.dsPatecVspre = dsPatecVspre;
	}
}
