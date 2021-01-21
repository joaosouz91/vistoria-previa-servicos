package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public enum TipoRestricao {
		
	LIB("LIB"),
	AJU("AJU"),
	VIS("VIS");
	
	private final String valor;
    
	TipoRestricao(String valorOpcao){
        valor = valorOpcao;
    }
    
	public String getValue(){
        return valor;
    }
}
