package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamentoFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVeiculo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
@JsonInclude(Include.NON_NULL)
public class VistoriaFiltro implements Serializable {

	private static final long serialVersionUID = -6040228274737235045L;

	private Long id;
	
	@Digits(integer = 6, fraction = 0)
	private Long corretor;
	
	private SituacaoAgendamentoFiltro situacao;

	@Size(max = 14)
	private String cpfCnpj;

	@Size(max = 20)
	private String voucher;
	
	@Size(max = 7)
	private String placa;
	
	@Size(max = 20)
	private String chassi;

	@Digits(integer = 38, fraction = 0)
	private Long negocio;

	@Digits(integer = 38, fraction = 0)
	private Long item;
	
	@Digits(integer = 38, fraction = 0)
	private Long endosso;
	
	@Digits(integer = 9, fraction = 0)
	private Long calculo;
	
	private Date dataDe;

	private Date dataAte;
	
	@JsonIgnore
	private TipoVeiculo tipo;
	
	@JsonIgnore
	public boolean isFrota() {
		return tipo == TipoVeiculo.F;
	}
	
}
