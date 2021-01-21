package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

/**
 * Enum com tipos básicos de veículo. Utilizado na regra do serviço
 * AgendamentoController.obterTiposAgendamento para determinar, em caso de
 * exceção, a liberação da opção de agendamento mobile.
 * 
 * @author Filipe
 *
 */
public enum TipoVeiculoLocalAgendamentoEnum {

	/**
	 * Passeio
	 */
	P,
	/**
	 * Caminhão
	 */
	C,
	/**
	 * Moto
	 */
	M;
}
