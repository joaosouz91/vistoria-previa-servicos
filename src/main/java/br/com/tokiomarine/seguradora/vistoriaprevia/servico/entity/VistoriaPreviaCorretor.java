package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import br.com.tokiomarine.seguradora.ssv.transacional.model.Conveniado;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VistoriaPreviaCorretor {

    private VistoriaPreviaObrigatoria vistoriaPrevia;
    private Conveniado corretor;

}
