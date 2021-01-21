package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;

public class ParametroGNT implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5763764941053144372L;
	private String valorParametro;
	private String nomeParametro;
	
	public ParametroGNT(String valorParametro, String nomeParametro) {
		this.valorParametro = valorParametro;
		this.nomeParametro = nomeParametro;
	}
	
	public String getNomeParametro() {
		return nomeParametro;
	}
	public void setNomeParametro(String nomeParametro) {
		this.nomeParametro = nomeParametro;
	}
	public String getValorParametro() {
		return valorParametro;
	}
	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
	}

	@Override
	public String toString() {
		return "ParametroGNT [valorParametro=" + valorParametro + ", nomeParametro=" + nomeParametro + "]";
	}
	
	
}
