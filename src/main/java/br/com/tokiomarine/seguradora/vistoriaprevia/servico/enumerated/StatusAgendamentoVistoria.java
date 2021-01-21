package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public enum StatusAgendamentoVistoria {
	
	/**
	 * AGD
	 */
	AGENDADO("AGD"),
	/**
	 * RCB
	 */
	RECEBIDO("RCB"),
	/**
	 * PEN
	 */
	PENDENTE("PEN"),
	/**
	 * NAP
	 */
	NAOREALIZADO("NAP"),
	/**
	 * VFR
	 */
	FRUSTRADO("VFR"),
	/**
	 * RLZ
	 */
	REALIZADO("RLZ"),
	/**
	 * FINALIZADO
	 */
	FINALIZADO("FINALIZADO"),
	/**
	 * CANCELADO
	 */
	CANCELADO("CANCELADO"),
	/**
	 * NAG
	 */
	NAOAGENDADO("NAG"),
	/**
	 * RGD
	 */
	REAGENDADO("RGD");
	
	private final String valor;
	
	StatusAgendamentoVistoria(final String valorOpcao){
        valor = valorOpcao;
    }
    
    public String getValor(){
        return valor;
    }
}