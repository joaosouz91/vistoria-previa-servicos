package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RetornoServico;

@JsonInclude(value = Include.ALWAYS)
public class ResponseHistoricoVistoriaPreviaDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -736892428774522295L;
	private List<HistoricoVistoriaPreviaDTO> historicoAvarias;
	private RetornoServico retorno;

	public List<HistoricoVistoriaPreviaDTO> getHistoricoAvarias() {
		return historicoAvarias;
	}

	public void setHistoricoAvarias(List<HistoricoVistoriaPreviaDTO> historicoAvarias) {
		this.historicoAvarias = historicoAvarias;
	}

	public RetornoServico getRetorno() {
		return retorno;
	}

	public void setRetorno(RetornoServico retorno) {
		this.retorno = retorno;
	}

}
