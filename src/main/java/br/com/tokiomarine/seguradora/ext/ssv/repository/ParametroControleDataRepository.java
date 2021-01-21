package br.com.tokiomarine.seguradora.ext.ssv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.corporativa.model.ParametroControleData;

@Repository
@Transactional(readOnly = true)
public interface ParametroControleDataRepository extends JpaRepository<ParametroControleData, Long>{

	@Query(value =  "from ParametroControleData p where p.cdIdetcProcm = 0")
	public Optional<ParametroControleData> buscarDataDeControle();

	public Optional<ParametroControleData> findByCdIdetcProcm(Long cdIdetcProcm);
}
