package br.com.tokiomarine.seguradora.ext.ssv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.ext.ssv.entity.ControleAlcadaDominio;

@Repository
public interface ControleAlcadaDominioRepository extends JpaRepository<ControleAlcadaDominio, Long>{

	@Query(value =  "SELECT "+
			 		"	o "+					
					"FROM "+
					"	ControleAlcadaDominio o "+					
					"WHERE "+
					"	o.cdMdupr = :cdMdupr "+
					"	AND o.tpOperc = :tpOperc "+
					"	AND o.cdRestrGrade = 'VP0'")
	public ControleAlcadaDominio buscarControleAlcadaDominio(@Param("cdMdupr") Long cdMdupr, @Param("tpOperc") Long tpOperc);
}