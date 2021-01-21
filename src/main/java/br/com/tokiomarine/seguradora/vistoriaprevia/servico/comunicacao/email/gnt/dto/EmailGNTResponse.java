package br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto;

public class EmailGNTResponse  {
	
	public static final int RETORNO_SUCESSO = 0;

    private Long seqAgendamento;
    private int codigoRetorno;
    private String mensagemRetorno;
    
	public Long getSeqAgendamento() {
		return seqAgendamento;
	}

	public int getCodigoRetorno() {
		return codigoRetorno;
	}

	public String getMensagemRetorno() {
		return mensagemRetorno;
	}

}