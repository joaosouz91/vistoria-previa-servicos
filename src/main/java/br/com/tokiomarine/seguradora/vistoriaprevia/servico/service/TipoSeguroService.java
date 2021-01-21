package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import org.springframework.stereotype.Component;

@Component
public class TipoSeguroService {

	public String verificarTipoSeguro(final String tipoSeguro) {
		
		if (tipoSeguro == null) { 
			return null; 
		}
		
		String descricaoTipoSeguro = null;
		int tipoSeguroInt = new Long(tipoSeguro).intValue();
		switch (tipoSeguroInt) {
			case 1:
				descricaoTipoSeguro = "SEGURO NOVO";
				break;
			case 2:
				descricaoTipoSeguro = "RENOV. CONGENERE COM SINISTRO";
				break;
			case 3:
				descricaoTipoSeguro = "RENOV. CONGENERE SEM SINISTRO";
				break;
			case 4:
				descricaoTipoSeguro = "RENOV. TOKIO COM SINISTRO";
				break;
			case 5:
				descricaoTipoSeguro = "RENOV. TOKIO SEM SINISTRO";
				break;
			default:
				break;
		}

		return descricaoTipoSeguro;
	}
}
