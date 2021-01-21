package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;


import java.util.Map;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
@JsonFormat(shape = JsonFormat.Shape.OBJECT) 
public enum StatusParecerEnum {

	
	A("A","ACEITÁVEL"),
	R("R","RECUSADA"),
	AF("AF","ACEITACAO_FORCADA"),
	S("S","SUJEITO_A_ANALISE"),
	L("L","LIBERADA"),
	F("F","FRUSTRADA"),
	RG("RG","REGULARIZACAO"),
	AL("AL","ACEITAVEL_LIBERADA");
	
	
	
	private String codigo;
	private String descricao;
	
	private StatusParecerEnum(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return this.codigo ;
	}
	
	@JsonCreator
	public static StatusParecerEnum fromObject( final Map<String, Object> obj) {
		
		return Stream.of(StatusParecerEnum.values()).filter(
				t -> t.codigo.equals(obj.get("codigo"))).findFirst().orElseThrow(IllegalArgumentException::new); 
	}
	
	
}
