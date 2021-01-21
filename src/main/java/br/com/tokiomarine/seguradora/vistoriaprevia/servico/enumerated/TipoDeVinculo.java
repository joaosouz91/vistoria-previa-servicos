package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public enum TipoDeVinculo {
		
	S("Vinculado"),
	N("Não Vinculado"),
	A("Não Vinculado"),
	B("Bloqueado");
	
	private final String valor;
    
	TipoDeVinculo(String valorOpcao){
        valor = valorOpcao;
    }
    
	public String getValue(){
        return valor;
    }
}
