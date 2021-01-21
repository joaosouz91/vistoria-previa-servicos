package br.com.tokiomarine.seguradora.ext.ssv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.ssv.corporativa.model.ParametroSSV;

@Repository
public interface ParametroSsvRepository extends JpaRepository<ParametroSSV, Long>{

	@Query(value =  "select p from ParametroSSV p where p.cdGrpParamSsv = :cdGrpParamSsv AND p.cdParamSsv = :cdParamSsv")
	public ParametroSSV findByParametroSsv(@Param("cdGrpParamSsv") Long cdGrpParamSsv, @Param("cdParamSsv") String cdParamSsv);
}
