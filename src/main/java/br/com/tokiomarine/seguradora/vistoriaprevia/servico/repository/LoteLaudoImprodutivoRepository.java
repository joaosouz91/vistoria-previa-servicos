package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivo;

@Repository
@Transactional(readOnly = true)
public interface LoteLaudoImprodutivoRepository
		extends CrudRepository<LoteLaudoImprodutivo, Long>, LoteLaudoImprodutivoRepositoryCustom {

}
