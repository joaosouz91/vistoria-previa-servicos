package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.util.List;

public class NegocioDTO {

	private Long cdNgoco;
	private Long nrCallo;
	private Long cdEndos;
	private Long cdSistmOrigm;
	private String nmClien;
	private String nrCpfCnpjClien;
	private Long cdCrtorCia;
	private String loginUsuarioPortal;
	private Boolean web;
	private List<ItemSegur> itens;
	
	public Long getCdNgoco() {
		return cdNgoco;
	}
	public void setCdNgoco(Long cdNgoco) {
		this.cdNgoco = cdNgoco;
	}
	public Long getNrCallo() {
		return nrCallo;
	}
	public void setNrCallo(Long nrCallo) {
		this.nrCallo = nrCallo;
	}
	public Long getCdEndos() {
		return cdEndos;
	}
	public void setCdEndos(Long cdEndos) {
		this.cdEndos = cdEndos;
	}
	public Long getCdSistmOrigm() {
		return cdSistmOrigm;
	}
	public void setCdSistmOrigm(Long cdSistmOrigm) {
		this.cdSistmOrigm = cdSistmOrigm;
	}
	public String getNmClien() {
		return nmClien;
	}
	public void setNmClien(String nmClien) {
		this.nmClien = nmClien;
	}
	public String getNrCpfCnpjClien() {
		return nrCpfCnpjClien;
	}
	public void setNrCpfCnpjClien(String nrCpfCnpjClien) {
		this.nrCpfCnpjClien = nrCpfCnpjClien;
	}
	public Long getCdCrtorCia() {
		return cdCrtorCia;
	}
	public void setCdCrtorCia(Long cdCrtorCia) {
		this.cdCrtorCia = cdCrtorCia;
	}
	public String getLoginUsuarioPortal() {
		return loginUsuarioPortal;
	}
	public void setLoginUsuarioPortal(String loginUsuarioPortal) {
		this.loginUsuarioPortal = loginUsuarioPortal;
	}
	public Boolean getWeb() {
		return web;
	}
	public void setWeb(Boolean web) {
		this.web = web;
	}
	public List<ItemSegur> getItens() {
		return itens;
	}
	public void setItens(List<ItemSegur> itens) {
		this.itens = itens;
	}

	
	
}
