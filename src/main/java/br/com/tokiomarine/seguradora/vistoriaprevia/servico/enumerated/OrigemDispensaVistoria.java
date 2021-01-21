package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public enum OrigemDispensaVistoria {
	
	ORIGEM_REGRA(1L),
	ORIGEM_DISPENSA(2L),
	ORIGEM_RECUSA(4L);
	
	private final Long valor;
	
	OrigemDispensaVistoria(final Long valorOpcao){
        valor = valorOpcao;
    }
    
    public Long getValor(){
        return valor;
    }
}