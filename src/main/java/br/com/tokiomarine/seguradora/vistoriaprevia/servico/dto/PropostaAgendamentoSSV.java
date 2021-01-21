package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

public interface PropostaAgendamentoSSV {

	Long getNumItemSegurado();
	String getTipoHistorico();
	String getCodChassiVeiculo();
	String getCodPlacaVeiculo();
	Long getCodNegocio();
	Long getCodEndosso();
	Long getCodModuloProduto();
	Long getCodCorretor();
	Long getCodCliente();
}
