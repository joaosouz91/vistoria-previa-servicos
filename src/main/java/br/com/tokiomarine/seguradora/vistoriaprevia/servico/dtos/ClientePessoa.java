package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.sql.Timestamp;

public class ClientePessoa {
    private Long cdClien;
    private String dsEmail;
    private Timestamp dtNascm;
    private String icBlque;
    private String nmClien;
    private String nrCelul;
    private String nrCnpjCpf;
    private String tpPesoa;
	public Long getCdClien() {
		return cdClien;
	}
	public void setCdClien(Long cdClien) {
		this.cdClien = cdClien;
	}
	public String getDsEmail() {
		return dsEmail;
	}
	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}
	public Timestamp getDtNascm() {
		return dtNascm;
	}
	public void setDtNascm(Timestamp dtNascm) {
		this.dtNascm = dtNascm;
	}
	public String getIcBlque() {
		return icBlque;
	}
	public void setIcBlque(String icBlque) {
		this.icBlque = icBlque;
	}
	public String getNmClien() {
		return nmClien;
	}
	public void setNmClien(String nmClien) {
		this.nmClien = nmClien;
	}
	public String getNrCelul() {
		return nrCelul;
	}
	public void setNrCelul(String nrCelul) {
		this.nrCelul = nrCelul;
	}
	public String getNrCnpjCpf() {
		return nrCnpjCpf;
	}
	public void setNrCnpjCpf(String nrCnpjCpf) {
		this.nrCnpjCpf = nrCnpjCpf;
	}
	public String getTpPesoa() {
		return tpPesoa;
	}
	public void setTpPesoa(String tpPesoa) {
		this.tpPesoa = tpPesoa;
	}
	
    

}
