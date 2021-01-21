package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;
import java.util.List;

import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ListaDeDispensa implements Serializable {
	
	private static final long serialVersionUID = -1856239597266670216L;
	
	@EqualsAndHashCode.Include
	private Long codigoProposta;
	private List<Long> numeroItemSegurado;		
	private ConteudoColunaTipo conteudoColunaTipo;	
	private String justificativaDispensa;	
	private Long tipoFechamentoRestricao; 
	private String idUsuarioLogado;
}