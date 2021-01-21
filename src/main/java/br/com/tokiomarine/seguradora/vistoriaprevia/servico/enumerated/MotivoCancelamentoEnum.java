package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public enum MotivoCancelamentoEnum {
	
	CANCELAMENTO_A_CONFIRMAR(1L),
	CANCELAMENTO_CONFIRMADO(2L),
	CANCELAMENTO_VISTORIADORA(3L),
	CANCELAMENTO_DE_NAG(4L),
	CANCELAMENTO_FORA_SISTEMA_CONFIRMADO(5L),
	CANCELAMENTO_TELA_CONSULTA_AGTO(6L);
	
	private final Long valor;
	
	MotivoCancelamentoEnum(final Long valorOpcao){
        valor = valorOpcao;
    }
    
    public Long getValor(){
        return valor;
    }
}