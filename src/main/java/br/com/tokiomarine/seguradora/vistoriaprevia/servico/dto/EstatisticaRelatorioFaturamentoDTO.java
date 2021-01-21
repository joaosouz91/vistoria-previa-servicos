package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.EstatisticaFaturamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstatisticaRelatorioFaturamentoDTO {

    private Page<EstatisticaFaturamento> estatisticaFaturamentoList;
    private EstatisticaFaturamento linhaComTotal;

}
