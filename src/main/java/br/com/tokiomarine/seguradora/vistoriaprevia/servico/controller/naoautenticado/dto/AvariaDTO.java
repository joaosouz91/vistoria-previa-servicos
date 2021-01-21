package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AvariaLaudoVistoriaPrevia;

@JsonInclude(value = Include.ALWAYS)
public class AvariaDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3348692843473187538L;
	private Long sqAvariLvpre;
	private String cdLvpre;
	private Long cdPecaAvada;
	private Long qtHoraEletr;
	private Long qtHoraFunil;
	private Long qtHoraPntur;
	private Long qtHoraTapec;
	private String tpAvariVeicu;
	private Long nrVrsaoLvpre;
	private String dsTipoAvari;
	private String dsPecaAvada;

	public AvariaDTO(AvariaLaudoVistoriaPrevia avaria, String dsPecaAvada) {
		setSqAvariLvpre(avaria.getSqAvariLvpre());
		setCdLvpre(avaria.getCdLvpre());
		setCdPecaAvada(avaria.getCdPecaAvada());
		setQtHoraEletr(avaria.getQtHoraEletr());
		setQtHoraFunil(avaria.getQtHoraFunil());
		setQtHoraPntur(avaria.getQtHoraPntur());
		setQtHoraTapec(avaria.getQtHoraTapec());
		setTpAvariVeicu(avaria.getTpAvariVeicu());
		setDsPecaAvada(dsPecaAvada);
		setNrVrsaoLvpre(avaria.getNrVrsaoLvpre());
		setDsTipoAvari(avaria.getTpAvariVeicu());
	}

	public Long getSqAvariLvpre() {
		return sqAvariLvpre;
	}

	public void setSqAvariLvpre(Long sqAvariLvpre) {
		this.sqAvariLvpre = sqAvariLvpre;
	}

	public String getCdLvpre() {
		return cdLvpre;
	}

	public void setCdLvpre(String cdLvpre) {
		this.cdLvpre = cdLvpre;
	}

	public Long getCdPecaAvada() {
		return cdPecaAvada;
	}

	public void setCdPecaAvada(Long cdPecaAvada) {
		this.cdPecaAvada = cdPecaAvada;
	}

	public Long getQtHoraEletr() {
		return qtHoraEletr;
	}

	public void setQtHoraEletr(Long qtHoraEletr) {
		this.qtHoraEletr = qtHoraEletr;
	}

	public Long getQtHoraFunil() {
		return qtHoraFunil;
	}

	public void setQtHoraFunil(Long qtHoraFunil) {
		this.qtHoraFunil = qtHoraFunil;
	}

	public Long getQtHoraPntur() {
		return qtHoraPntur;
	}

	public void setQtHoraPntur(Long qtHoraPntur) {
		this.qtHoraPntur = qtHoraPntur;
	}

	public Long getQtHoraTapec() {
		return qtHoraTapec;
	}

	public void setQtHoraTapec(Long qtHoraTapec) {
		this.qtHoraTapec = qtHoraTapec;
	}

	public String getTpAvariVeicu() {
		return tpAvariVeicu;
	}

	public void setTpAvariVeicu(String tpAvariVeicu) {
		this.tpAvariVeicu = tpAvariVeicu;
	}

	public Long getNrVrsaoLvpre() {
		return nrVrsaoLvpre;
	}

	public void setNrVrsaoLvpre(Long nrVrsaoLvpre) {
		this.nrVrsaoLvpre = nrVrsaoLvpre;
	}

	public String getDsTipoAvari() {
		return dsTipoAvari;
	}

	public void setDsTipoAvari(String dsTipoAvari) {
		this.dsTipoAvari = dsTipoAvari;
	}

	public String getDsPecaAvada() {
		return dsPecaAvada;
	}

	public void setDsPecaAvada(String dsPecaAvada) {
		this.dsPecaAvada = dsPecaAvada;
	}
}
