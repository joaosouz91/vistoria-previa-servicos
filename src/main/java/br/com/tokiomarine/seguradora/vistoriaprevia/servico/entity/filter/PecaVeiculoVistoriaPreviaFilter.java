package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PecaVeiculoVistoriaPreviaFilter {

    private String codigo;
    private String situacao;
    private String descricao;

}
