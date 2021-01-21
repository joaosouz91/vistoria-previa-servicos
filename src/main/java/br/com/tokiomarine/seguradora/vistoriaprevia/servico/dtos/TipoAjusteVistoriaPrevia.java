package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

public enum TipoAjusteVistoriaPrevia {

	COD_CHASSI_REMARCADO(306L),
	COD_CHASSI_REMARCADO_NAO_REGULARIZADO(502L),
	COD_QUARTO_EIXO_REGULARIZADO(119L),
	COD_QUARTO_EIXO_NAO_REGULARIZADO(555L),
	COD_CABINE_SUPLEMENTAR_REGULARIZADO(120L),
	COD_CABINE_SUPLEMENTAR_NAO_REGULARIZADO(561L);
	
	private final Long valor;
    
	TipoAjusteVistoriaPrevia(Long valorOpcao){
        valor = valorOpcao;
    }
    
	public Long getValue(){
        return valor;
    }
}
