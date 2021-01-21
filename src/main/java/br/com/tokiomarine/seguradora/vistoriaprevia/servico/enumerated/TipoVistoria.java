package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;

@JsonFormat(shape = Shape.OBJECT)
@Getter
public enum TipoVistoria {
	
	//mobile	
	M("M", "Digital", "MOBILE"), 

	/**
	 * Posto
	 */
	P("P", "Posto", "POSTO"), 
	
	/**
	 * Domicílio
	 */
	D("D", "Domicílio", "DOCLO");

	private String codigo;
	private String descricao;
	private String codMotivo;

	private TipoVistoria(String codigo, String descricao, String codMotivo) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.codMotivo = codMotivo;
	}
	
	public static String obterDescricao(String codigo) {
		for(TipoVistoria s : TipoVistoria.values()) {
			if(s.getCodigo().equals(codigo))
				return s.getDescricao();
		}
		
		return "";
	}
}
