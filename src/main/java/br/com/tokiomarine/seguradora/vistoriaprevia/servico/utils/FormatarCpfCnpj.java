package br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class FormatarCpfCnpj {
	

	public static String formatarCpf(final String cpf) {
		try{
			MaskFormatter mask = new MaskFormatter("###.###.###-##");
	        mask.setValueContainsLiteralCharacters(false);
	        return mask.valueToString(cpf);
		}catch (ParseException e) {
			return "";
		}    
	}	
	
	public static String formatarCnpj(final String cnpj) {
		try{
	        MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
	        mask.setValueContainsLiteralCharacters(false);
	        return mask.valueToString(cnpj);
		}catch (ParseException e) {
			return "";
		}
	}
	
	public static boolean isCpf(String cpf) {
		return cpf.length() <= 11;
	}
	
	 private FormatarCpfCnpj() {
		    throw new IllegalStateException("Utility class");
		  }
}