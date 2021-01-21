package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class ArquivoTipoCarroceria  {
	
	private Long codigoTipoCarroceria;
	private String descricaoTipoCarroceria;
	
	
	public ArquivoTipoCarroceria(Long cdTipoCarroceria, String dsTipoCarroceria){
		
		this.codigoTipoCarroceria = cdTipoCarroceria;
		this.descricaoTipoCarroceria = dsTipoCarroceria;
				
	}
}
	