package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.ALWAYS)
public class HistoricoVistoriaPreviaDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1918495394343613004L;
	private String cdLvpre;
	private Long nrVrsao;
	private Long cdSitucHistoVspre;
	private Long nrItseg;
	private String tpHistoItseg;
	private Long cdLocalCaptc;
	private Long cdEndos;
	private String icSbrpoDado;
	private Long tpExcecVspre;
	private String dsMotvSbrpoDado;
	private Long cdSucslComrl;
	private String cdUsuroIncls;
	@JsonFormat(shape = Shape.NUMBER)
	private Date dtUltmaAlter;

	public String getCdLvpre() {
		return cdLvpre;
	}

	public void setCdLvpre(String cdLvpre) {
		this.cdLvpre = cdLvpre;
	}

	public Long getNrVrsao() {
		return nrVrsao;
	}

	public void setNrVrsao(Long nrVrsao) {
		this.nrVrsao = nrVrsao;
	}

	public Long getCdSitucHistoVspre() {
		return cdSitucHistoVspre;
	}

	public void setCdSitucHistoVspre(Long cdSitucHistoVspre) {
		this.cdSitucHistoVspre = cdSitucHistoVspre;
	}

	public Long getNrItseg() {
		return nrItseg;
	}

	public void setNrItseg(Long nrItseg) {
		this.nrItseg = nrItseg;
	}

	public String getTpHistoItseg() {
		return tpHistoItseg;
	}

	public void setTpHistoItseg(String tpHistoItseg) {
		this.tpHistoItseg = tpHistoItseg;
	}

	public Long getCdLocalCaptc() {
		return cdLocalCaptc;
	}

	public void setCdLocalCaptc(Long cdLocalCaptc) {
		this.cdLocalCaptc = cdLocalCaptc;
	}

	public Long getCdEndos() {
		return cdEndos;
	}

	public void setCdEndos(Long cdEndos) {
		this.cdEndos = cdEndos;
	}

	public String getIcSbrpoDado() {
		return icSbrpoDado;
	}

	public void setIcSbrpoDado(String icSbrpoDado) {
		this.icSbrpoDado = icSbrpoDado;
	}

	public Long getTpExcecVspre() {
		return tpExcecVspre;
	}

	public void setTpExcecVspre(Long tpExcecVspre) {
		this.tpExcecVspre = tpExcecVspre;
	}

	public String getDsMotvSbrpoDado() {
		return dsMotvSbrpoDado;
	}

	public void setDsMotvSbrpoDado(String dsMotvSbrpoDado) {
		this.dsMotvSbrpoDado = dsMotvSbrpoDado;
	}

	public Long getCdSucslComrl() {
		return cdSucslComrl;
	}

	public void setCdSucslComrl(Long cdSucslComrl) {
		this.cdSucslComrl = cdSucslComrl;
	}

	public String getCdUsuroIncls() {
		return cdUsuroIncls;
	}

	public void setCdUsuroIncls(String cdUsuroIncls) {
		this.cdUsuroIncls = cdUsuroIncls;
	}

	public Date getDtUltmaAlter() {
		return dtUltmaAlter;
	}

	public void setDtUltmaAlter(Date dtUltmaAlter) {
		this.dtUltmaAlter = dtUltmaAlter;
	}
}
