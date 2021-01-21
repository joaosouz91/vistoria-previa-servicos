package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public enum AmbienteEnum {

	LOCAL("LOCAL"),
	DESENVOLVIMENTO("DESENVOLVIMENTO"),
	ACEITE("ACEITE"),
	PRODUCAO("PRODUCAO");
	
	private final String valor;
    
	AmbienteEnum(String valorOpcao){
        valor = valorOpcao;
    }
    
	public String getValue(){
        return valor;
    }
}
