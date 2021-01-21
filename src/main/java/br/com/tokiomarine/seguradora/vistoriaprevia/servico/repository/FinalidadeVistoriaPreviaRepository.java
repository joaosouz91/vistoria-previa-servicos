package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.FinalidadeVistoriaPrevia;

@Repository
@Transactional(readOnly = true)
public interface FinalidadeVistoriaPreviaRepository extends JpaRepository<FinalidadeVistoriaPrevia, Long>{

	@Query(value =  "select "+
			 		"	f " +
					"from "+
					"	FinalidadeVistoriaPrevia f " +
					"where " +
					"	f.cdFnaldVspre = :cdFnaldVspre ")
	public FinalidadeVistoriaPrevia findByFinalidadeVistoriaPrevia(@Param("cdFnaldVspre") Long cdFnaldVspre);
	
	@Query(value =  "select "+
			 		"	f " +
					"from "+
					"	FinalidadeVistoriaPrevia f " +			
					"	order by dsFnaldVspre")
	public List<FinalidadeVistoriaPrevia> findAllFinalidadeVistoriaPrevia();
}
