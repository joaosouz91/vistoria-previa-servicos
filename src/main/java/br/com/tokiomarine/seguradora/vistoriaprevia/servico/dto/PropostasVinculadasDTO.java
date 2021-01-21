package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class PropostasVinculadasDTO {

	@EqualsAndHashCode.Include
	private Long codApolice;
	
	@EqualsAndHashCode.Include
	private Long codNegocio;
	
	@EqualsAndHashCode.Include
	private Long numItemSegurado;
	
	private Date dtEmissao;
	
	@EqualsAndHashCode.Include
	private String codEndosso;
	
	private String codSituacaoVistoriaPrevia;
	
	private String tpSegur;
}
