package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;

public class DestinatarioDetalhes implements  Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5763764941053144372L;
	private String destino;
	private String cpfCnpj;
		
	public DestinatarioDetalhes(String destino, String cpfCnpj) {
		this.destino = destino;
		this.cpfCnpj = cpfCnpj;
	}
	
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	@Override
	public String toString() {
		return "DestinatarioDetalhes [destino=" + destino + ", cpfCnpj=" + cpfCnpj + "]";
	}
	

}
