package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.PrestadoraStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrestadoraStatusDTO {

    private List<PrestadoraStatus> prestadoraStatusList;
    private PrestadoraStatus linhaComTotal;

}
