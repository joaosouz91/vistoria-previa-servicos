package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.ALWAYS)
public class ResponseParecerAvariaChassiDTO implements Serializable {

	private static final long serialVersionUID = -7322395713864761533L;

	private String cdLvpre;
	private Long cdPatecVspre;

	public ResponseParecerAvariaChassiDTO() {
	}

	public ResponseParecerAvariaChassiDTO(String cdLvpre, Long cdPatecVspre) {
		super();
		this.cdLvpre = cdLvpre;
		this.cdPatecVspre = cdPatecVspre;
	}

	public String getCdLvpre() {
		return cdLvpre;
	}

	public void setCdLvpre(String cdLvpre) {
		this.cdLvpre = cdLvpre;
	}

	public Long getCdPatecVspre() {
		return cdPatecVspre;
	}

	public void setCdPatecVspre(Long cdPatecVspre) {
		this.cdPatecVspre = cdPatecVspre;
	}
}
