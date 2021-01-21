package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;
import java.util.List;

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
public class ListaReclassificacaoAlterarStatus implements Serializable {
	
	private static final long serialVersionUID = 726518087668462225L;

	@EqualsAndHashCode.Include
	private String numeroLaudo;
	@EqualsAndHashCode.Include
    private Long versaoLaudo;    
	@EqualsAndHashCode.Include
    private String tipoHistorico;
    private ComboNovoStatus comboNovoStatus;
    private String justificativa;
    @EqualsAndHashCode.Include
    private Long codigoProposta;
    private Long tipoFechamentoRestricao;
    private List<Long> numeroItem;
}