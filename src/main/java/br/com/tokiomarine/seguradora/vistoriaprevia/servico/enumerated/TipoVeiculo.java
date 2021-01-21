package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoVeiculo {

	/**
	 * Auto Passeio Clássico
	 */
	B("B", "Auto Passeio Clássico"),
	/**
	 * Auto Passeio Tradicional
	 */
	P("P", "Auto Passeio Tradicional"),
	/**
	 * Auto Carga
	 */
	C("C", "Auto Carga"),
	/**
	 * Auto Frota
	 */
	F("F", "Auto Frota"),
	/**
	 * Auto Carga Utilitário
	 */
	U("U", "Auto Carga Utilitário"),
	/**
	 * Auto Passeio Popular
	 */
	O("O", "Auto Passeio Popular"),
	/**
	 * Auto Carga Popular
	 */
	Z("Z", "Auto Carga Popular"),
	/**
	 * Auto Roubo
	 */
	R("R", "Auto Roubo"), 
	/**
	 * Auto Roubo + Rastreador
	 */
	A("A", "Auto Roubo + Rastreador"),
	/**
	 * Não Identificado
	 */
	N("N", ""),
	/**
	 * Utilitario Carga Popular/Caminhao Popular
	 */
	T("T", "Utilitario Carga Popular/Caminhao Popular"),
	/**
	 * Utilitário Popular
	 */
	L("L", "Utilitário Popular");
	
	private String valor;
	private String descricao;

	private TipoVeiculo(String valor, String descricao){
		this.valor = valor;
		this.descricao = descricao;
	}
	TipoVeiculo(String valor) {
		this.valor = valor;
	    }
	
	public String getDescricao() {
		return descricao;
	}

	public String getValor() {
		return valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return valor;
	}
	
}
