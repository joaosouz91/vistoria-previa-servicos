package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import lombok.Getter;

@Getter
public enum StatusVistoriaEnum {
	
	ACEITAVEL("A","ACEITÁVEL"),           
	RECUSADO("R","RECUSADO"),            
	ACEITACAO_FORCADA("AF","ACEITAÇÃO FORÇADA"),
	SUJEIRO_A_ANALISE("S","SUJEITO À ANALISE"),
	FRUSTRADA("F","FRUSTRADA"),
	ACEITACAO_LIBERADA("L","ACEITAÇÃO LIBERADA");
	
	private StatusVistoriaEnum(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	private String codigo;
	private String descricao;
	
	public StatusVistoriaEnum[] listar() {
		
		return StatusVistoriaEnum.values();
	}
	
	
}
