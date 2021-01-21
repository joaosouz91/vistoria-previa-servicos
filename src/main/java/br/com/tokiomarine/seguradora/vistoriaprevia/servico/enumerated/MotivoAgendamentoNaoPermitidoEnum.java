package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public enum MotivoAgendamentoNaoPermitidoEnum {
	
	MOTIVO_CORRETOR_NAO_PARTICIPA(1L),
	MOTIVO_VP_NAO_NECESSARIA(2L),
	MOTIVO_AGENDAMENTO_JA_REALIZADO(3L),
	MOTIVO_AGENDAMENTO_REAPROVEITADO(4L),
	MOTIVO_VP_DISPENSADA(5L);
	
	private final Long valor;
	
	private MotivoAgendamentoNaoPermitidoEnum(final Long valorOpcao){
        valor = valorOpcao;
    }
    
    public Long getValor(){
        return valor;
    }
}