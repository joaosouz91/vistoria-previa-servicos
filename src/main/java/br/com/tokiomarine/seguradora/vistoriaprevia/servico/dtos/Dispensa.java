package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;

import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
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
public class Dispensa implements Serializable {
	
	private static final long serialVersionUID = -2760673862319546183L;
		
	@EqualsAndHashCode.Include
	private Long numeroItemSegurado;	
	
	@EqualsAndHashCode.Include
	private Long codigoProposta;	
	
	private ConteudoColunaTipo conteudoColunaTipo;	
	
	private String justificativaDispensa;	
	
	private Long tipoFechamentoRestricao; 
	
	private String idUsuarioLogado;
}