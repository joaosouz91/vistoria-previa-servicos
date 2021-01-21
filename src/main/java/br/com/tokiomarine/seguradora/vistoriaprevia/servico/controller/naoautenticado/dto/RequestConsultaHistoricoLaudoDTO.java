package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto;

import java.util.List;

public class RequestConsultaHistoricoLaudoDTO {

	private String codigoLaudo;
	private List<Long> codigoSituacao;

	public String getCodigoLaudo() {
		return codigoLaudo;
	}
	public void setCodigoLaudo(String codigoLaudo) {
		this.codigoLaudo = codigoLaudo;
	}
	public List<Long> getCodigoSituacao() {
		return codigoSituacao;
	}
	public void setCodigoSituacao(List<Long> codigoSituacao) {
		this.codigoSituacao = codigoSituacao;
	}

}
