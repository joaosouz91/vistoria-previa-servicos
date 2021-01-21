package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(content = Include.NON_NULL)
public class ResponseAgendamentoDTO {

	private Long idVistoria;
	private String chassi;
	private String voucher;
	private String error;

	public ResponseAgendamentoDTO(Long idVistoria, String chassi, String voucher) {
		this.idVistoria = idVistoria;
		this.chassi = chassi;
		this.voucher = voucher;
	}

	public ResponseAgendamentoDTO(Long idVistoria, String chassi, String voucher, Throwable error) {
		this(idVistoria, chassi, voucher);
		this.error = ExceptionUtils.getRootCause(error).getMessage();
	}
}
