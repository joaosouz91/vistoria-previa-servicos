package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;
import java.io.Serializable;


public class RetornoServico implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5502748965361202555L;
	private Long codigoRetorno;
	private String mensagem;


	public void setMensagem(String retorno) {
		this.mensagem = retorno;
	}

	public String getMensagem() {
		return this.mensagem;
	}

	public Long getCodigoRetorno() {
		return codigoRetorno;
	}

	public void setCodigoRetorno(Long codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (codigoRetorno == null ? 0 : codigoRetorno.hashCode());
		result = prime * result + (mensagem == null ? 0 : mensagem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		RetornoServico other = (RetornoServico) obj;

		if (mensagem == null) {
			if (other.mensagem != null) { return false; }
		} else if (!mensagem.equals(other.mensagem)) { return false; }

		if (codigoRetorno == null) {
			if (other.mensagem != null) { return false; }
		} else if (!codigoRetorno.equals(other.codigoRetorno)) { return false; }

		return true;
	}

	@Override
	public String toString() {
		return "RetornoWS [codigoRetorno=" + codigoRetorno + ", retorno=" + mensagem + "]";
	}

}
