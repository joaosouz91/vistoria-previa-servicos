package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class ReclassificacaoAlterarStatus implements Serializable {
	
	private static final long serialVersionUID = 726518087668462225L;

	@EqualsAndHashCode.Include
	private String numeroLaudo;
	@EqualsAndHashCode.Include
    private Long versaoLaudo;
	@EqualsAndHashCode.Include
    private Long numeroItem;
	@EqualsAndHashCode.Include
    private String tipoHistorico;
    private ComboNovoStatus comboNovoStatus;
    private String justificativa;
    @EqualsAndHashCode.Include
    private Long codigoProposta;
    private Long tipoFechamentoRestricao;
}
