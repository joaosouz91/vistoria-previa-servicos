package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public enum StatusVistoriaPrevia {
	
	PENDENTE("PENDENTE"),
	BACKOUT("BACKOUT"),
	HOLD("HOLD");
	
	private final String valor;
	
	StatusVistoriaPrevia(final String valorOpcao){
        valor = valorOpcao;
    }
    
    public String getValor(){
        return valor;
    }
}