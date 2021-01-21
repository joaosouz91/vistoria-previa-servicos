package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParecerTecnicoLaudoVistoriaPrevia;

@Repository
@Transactional(readOnly = true)
public interface ParecerTecnicoLaudoVistoriaPreviaRepository extends JpaRepository<ParecerTecnicoLaudoVistoriaPrevia, Long>{

	@Query(value =  "select "+
					"	lift.cdPatecVspre "+
					"from "+					 
					"   ParecerTecnicoLaudoVistoriaPrevia lift "+ 
					"where "+
					"	lift.cdLvpre = :cdLvpre "+
					 "  and lift.nrVrsaoLvpre = :nrVrsaoLvpre "+
					"	order by lift.cdPatecVspre")
	public List<Long> findByParecerTecnico(@Param("cdLvpre") String cdLvpre, @Param("nrVrsaoLvpre") Long nrVrsaoLvpre);
	
	@Modifying(flushAutomatically = true)
	@Transactional
	@Query("delete  from ParecerTecnicoLaudoVistoriaPrevia p where p.cdLvpre = :cdLvpre")
	void deleteParecerPorLaudo(@Param("cdLvpre") String cdLvpre);

	boolean existsParecerTecnicoLaudoVistoriaPreviaByCdLvpreAndCdPatecVspreIn(String cdLvpre, Collection<Long> cdPatecVspre);


	public boolean existsParecerTecnicoLaudoVistoriaPreviaByCdLvpreAndCdPatecVspreIn(String cdLvpre,
			long[] codigosParecer);

	
}
