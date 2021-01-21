package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AgendamentoMobileCancelamentoDTO implements Serializable {

	private static final long serialVersionUID = -6835375986256382193L;

	private String cdVouch;
	private String situacaoNegocio;
	
}
