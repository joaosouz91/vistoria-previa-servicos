package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RetornoServico;

@JsonInclude(value = Include.ALWAYS)
public class ResponseAvariaDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3590824156172301480L;
	private List<AvariaDTO> avarias;
	private RetornoServico retorno;

	public List<AvariaDTO> getAvarias() {
		return avarias;
	}

	public void setAvarias(List<AvariaDTO> avarias) {
		this.avarias = avarias;
	}

	public RetornoServico getRetorno() {
		return retorno;
	}

	public void setRetorno(RetornoServico retorno) {
		this.retorno = retorno;
	}
}
