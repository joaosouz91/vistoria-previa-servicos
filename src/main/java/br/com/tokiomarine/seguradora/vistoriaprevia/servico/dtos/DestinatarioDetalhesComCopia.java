package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;

public class DestinatarioDetalhesComCopia  implements  Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ENVIAR_COMO_COPIA_OCULTA = "S";
	public static final String ENVIAR_COMO_COPIA = "N";
	
	private String destino;
	private String cpfCnpj;
	private String copiaOculta;
		
	public DestinatarioDetalhesComCopia(String destino, String cpfCnpj, String copiaOculta) {
		this.destino = destino;
		this.cpfCnpj = cpfCnpj;
		this.copiaOculta = copiaOculta;
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

	public String getCopiaOculta() {
		return copiaOculta;
	}

	public void setCopiaOculta(String copiaOculta) {
		this.copiaOculta = copiaOculta;
	}
	
	@Override
	public String toString() {
		return "comCopia [destino = " + destino + ", cpfCnpj = " + cpfCnpj + ", copiaOculta = " + copiaOculta + "]";
	}
}
