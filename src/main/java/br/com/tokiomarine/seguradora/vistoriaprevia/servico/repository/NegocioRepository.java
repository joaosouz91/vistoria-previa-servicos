package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.transacional.model.Negocio;

@Repository
@Transactional(readOnly = true)
public interface NegocioRepository extends JpaRepository<Negocio, Long>{

	Negocio findByCodNegocioAndTipoHistorico(Long cdNgoco, String tipoHistoricoNegocio);

	
}
