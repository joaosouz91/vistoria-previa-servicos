package br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils;

public class RemoverCaracteres {
	
	public String removerCaracteres(final String texto){
		return texto.replaceAll(".", "").replaceAll("-", "").replaceAll("/", "");
	}	
}
