package br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions;

public class VistoriaPreviaServicoException extends RuntimeException {
    	
	private static final long serialVersionUID = -3372875459819094127L;
	
	private final Long codigo;
    private final String mensagem;
     
    public VistoriaPreviaServicoException(final Long codigoErro, final String mensagemErro) {
    	codigo = codigoErro;
        mensagem = mensagemErro;
    }

	public Long getCodigo() {
		return codigo;
	}

	public String getMensagem() {
		return mensagem;
	} 
}