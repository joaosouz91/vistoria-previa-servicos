package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.util.List;

public class ItemSegur {
	
	private Long nrItseg;
	private Long cdMdupr;
	private Long ordemItem;
	private List<Caracteristica> caracteristicas;
	private String icMobile;
	
	public Long getNrItseg() {
		return nrItseg;
	}
	public void setNrItseg(Long nrItseg) {
		this.nrItseg = nrItseg;
	}
	public Long getCdMdupr() {
		return cdMdupr;
	}
	public void setCdMdupr(Long cdMdupr) {
		this.cdMdupr = cdMdupr;
	}
	public Long getOrdemItem() {
		return ordemItem;
	}
	public void setOrdemItem(Long ordemItem) {
		this.ordemItem = ordemItem;
	}
	public List<Caracteristica> getCaracteristicas() {
		return caracteristicas;
	}
	public void setCaracteristicas(List<Caracteristica> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	public String getIcMobile() {
		return icMobile;
	}
	public void setIcMobile(String icMobile) {
		this.icMobile = icMobile;
	}	

}
