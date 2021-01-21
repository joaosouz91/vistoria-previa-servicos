package br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth;

public class UsuarioLogadoException extends Exception {
	
	private static final long serialVersionUID = -4074827374201391104L;

	public UsuarioLogadoException(String msg) {
		super(msg);
	}

	public UsuarioLogadoException(String msg, Exception exception) {
		super(msg, exception);
	}

	public UsuarioLogadoException(Exception exception) {
		super(exception);
	}
}