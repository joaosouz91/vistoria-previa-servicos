package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RetornoServico;

@JsonInclude(value = Include.ALWAYS)
public class ResponseDadosLaudoEmissaoDTO implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 8721568284506444263L;

	private String dtVspre;

	private boolean dispensa;

	private RetornoServico retorno;


	public String getDtVspre() {
		return dtVspre;
	}


	public void setDtVspre(String dtVspre) {
		this.dtVspre = dtVspre;
	}


	public boolean isDispensa() {
		return dispensa;
	}


	public void setDispensa(boolean dispensa) {
		this.dispensa = dispensa;
	}



	public RetornoServico getRetorno() {
		return retorno;
	}



	public void setRetorno(RetornoServico retornoWS) {
		this.retorno = retornoWS;
	}





}