package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RetornoServico;

@JsonInclude(value = Include.ALWAYS)
public class ResponseParecerVistoriaDTO implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 4351045550242118150L;
	/**
	 *
	 */
	private List<ParecerVistoriaDTO> parecerVistoriaDTO;
	private RetornoServico retorno;


	public List<ParecerVistoriaDTO> getParecerVistoriaDTO() {
		return parecerVistoriaDTO;
	}

	public void setParecerVistoriaDTO(List<ParecerVistoriaDTO> parecerVistoriaDTO) {
		this.parecerVistoriaDTO = parecerVistoriaDTO;
	}

	public RetornoServico getRetorno() {
		return retorno;
	}

	public void setRetorno(RetornoServico retorno) {
		this.retorno = retorno;
	}
}
