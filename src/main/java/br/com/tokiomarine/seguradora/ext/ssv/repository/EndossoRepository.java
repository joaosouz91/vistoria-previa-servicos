package br.com.tokiomarine.seguradora.ext.ssv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.ssv.transacional.model.Endosso;

@Repository
public interface EndossoRepository extends JpaRepository<Endosso, Long>{

	@Query(value =  "select e " +
					"from Endosso e " +
					"where " +
					"e.codEndosso = :codigoEndosso ")
	public Endosso findObterEndossoPorCodigo(@Param("codigoEndosso") Long codigoEndosso);
	
}
