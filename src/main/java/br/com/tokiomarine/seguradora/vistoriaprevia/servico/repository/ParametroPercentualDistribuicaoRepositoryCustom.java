package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ParametroPercentualDistribuicaoDTO;

public interface ParametroPercentualDistribuicaoRepositoryCustom {

	Page<ParametroPercentualDistribuicaoDTO> findAll(Map<String, Object> filtro, Pageable pageable);
}
