package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;

public class DistribuicaoVistoriasDTO implements Serializable {

	private static final long serialVersionUID = -5686851514950215554L;

	private Long cdAgrmtVspre;
	private Long qtdVistoria;
	private Double pcDistr;
	private Double pcVistAgendada;
	private String indCapital;

	public DistribuicaoVistoriasDTO(Long cdAgrmtVspre, Double pcDistr, String indCapit) {
		this.cdAgrmtVspre = cdAgrmtVspre;
		this.pcDistr = pcDistr;
		this.indCapital = indCapit;
	}

	public Long getCdAgrmtVspre() {
		return cdAgrmtVspre;
	}

	public void setCdAgrmtVspre(Long cdAgrmtVspre) {
		this.cdAgrmtVspre = cdAgrmtVspre;
	}

	public Long getQtdVistoria() {
		return qtdVistoria;
	}

	public void setQtdVistoria(Long qtdVistoria) {
		this.qtdVistoria = qtdVistoria;
	}

	public Double getPcDistr() {
		return pcDistr;
	}

	public void setPcDistr(Double pcDistr) {
		this.pcDistr = pcDistr;
	}

	public Double getPcVistAgendada() {
		return pcVistAgendada;
	}

	public void setPcVistAgendada(Double pcVistAgendada) {
		this.pcVistAgendada = pcVistAgendada;
	}

	public String getIndCapital() {
		return indCapital;
	}

	public void setIndCapital(String indCapital) {
		this.indCapital = indCapital;
	}
}
