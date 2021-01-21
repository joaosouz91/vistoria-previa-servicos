package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;

public class DadosEmailSegurado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String nomeCliente;
	private String itemSegurado;
	private String placaVeiculo;
	private String codChassi;
	private String codCorretor;
	private String proposta;
	private String endosso;
	private String dddTelCliente;
	private String telefoneCliente;
	private String tipo;
	private String emailCliente;
	private String cpfCnpj;



	public String getItemSegurado() {
		return itemSegurado;
	}

	public void setItemSegurado(String itemSegurado) {
		this.itemSegurado = itemSegurado;
	}

	public String getPlacaVeiculo() {
		return placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}

	public String getCodChassi() {
		return codChassi;
	}

	public void setCodChassi(String codChassi) {
		this.codChassi = codChassi;
	}

	public String getCodCorretor() {
		return codCorretor;
	}

	public void setCodCorretor(String codCorretor) {
		this.codCorretor = codCorretor;
	}

	public String getProposta() {
		return proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}

	public String getEndosso() {
		return endosso;
	}

	public void setEndosso(String endosso) {
		this.endosso = endosso;
	}

	public String getDddTelCliente() {
		return dddTelCliente;
	}

	public void setDddTelCliente(String dddTelCliente) {
		this.dddTelCliente = dddTelCliente;
	}

	public String getTelefoneCliente() {
		return telefoneCliente;
	}

	public void setTelefoneCliente(String telefoneCliente) {
		this.telefoneCliente = telefoneCliente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}




}
