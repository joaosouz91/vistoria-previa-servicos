package br.com.tokiomarine.seguradora.ext.rest.cta.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Caracteristicas implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 9065345029521635554L;

	@JsonProperty("DS_VLCAR_INFDO")
	public String dsVlcatInfdo;
	@JsonProperty("CD_CARAC_ITSEG")
	public Long cdCaracItSeg;
	@JsonProperty("CD_VLCAR_ITSEG")
	public Long cdVlcarIiseg;

	public String getDsVlcatInfdo() {
		return dsVlcatInfdo;
	}

	public void setDsVlcatInfdo(String dsVlcatInfdo) {
		this.dsVlcatInfdo = dsVlcatInfdo;
	}

	public Long getCdCaracItSeg() {
		return cdCaracItSeg;
	}

	public void setCdCaracItSeg(Long cdCaracItSeg) {
		this.cdCaracItSeg = cdCaracItSeg;
	}

	public Long getCdVlcarIiseg() {
		return cdVlcarIiseg;
	}

	public void setCdVlcarIiseg(Long cdVlcarIiseg) {
		this.cdVlcarIiseg = cdVlcarIiseg;
	}
}
