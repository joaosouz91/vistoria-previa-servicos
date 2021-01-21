package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FiltroConsultaVistoria implements Serializable {
	
	private static final long serialVersionUID = 3134722144820153208L;

	private String placa;
	private String chassi;
	private String numeroVistoria;
	private String numeroVoucher;
	private String cpf;
	private String cnpj;
	
	@JsonIgnore
	public boolean isFiltroInvalido() {
		return isBlank(placa)
				&& isBlank(chassi)
				&& isBlank(numeroVistoria)
				&& isBlank(numeroVoucher)
				&& isBlank(cpf)
				&& isBlank(cnpj);
	}
}