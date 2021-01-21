package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoCusto;

public interface LaudoCustoRepositoryCustom {

	List<LaudoCusto> carregarCustoVPImprodutiva(Long mesReferencia, Long anoReferencia);

}
