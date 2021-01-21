package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.AtrasoTransmissaoAgrupamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstatisticaAtrasosTransmissaoDTO {

    Page<AtrasoTransmissaoAgrupamento> atrasoTransmissaoAgrupamentoPageableList;

    AtrasoTransmissaoAgrupamento linhaComTotal;
}
