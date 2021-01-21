package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;
import java.util.Date;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LaudoVistoria implements Serializable {
	
	private static final long serialVersionUID = 7233864680875846239L;

	@EqualsAndHashCode.Include
	private String numeroVistoria;
	private String placa;
	private String chassi;
	private String status;	
	private String situacao;
	private Date data;
}