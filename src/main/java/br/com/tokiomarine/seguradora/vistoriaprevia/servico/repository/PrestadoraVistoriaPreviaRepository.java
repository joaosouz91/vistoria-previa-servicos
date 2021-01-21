package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PrestadoraVistoriaPrevia;

@Repository
@Transactional(readOnly = true)
public interface PrestadoraVistoriaPreviaRepository extends JpaRepository<PrestadoraVistoriaPrevia, Long> {

	 /**
	    * @deprecated (when, why, refactoring advice...)
	    */
	@Deprecated
	@Query(value =  "select p from PrestadoraVistoriaPrevia p " +
			 		"where " +
			 		"p.cdAgrmtVspre = :cdAgrmtVspre ")	 		
	public PrestadoraVistoriaPrevia findByIdPrestadoraVistoriaPrevia(@Param("cdAgrmtVspre") Long cdAgrmtVspre);
	
	 /**
	    * @deprecated (when, why, refactoring advice...)
	    */
	@Deprecated
	@Query(value =  "select "+
			 		"	p "+
			 		"from "+
			 		"	PrestadoraVistoriaPrevia p " +
	 				"order by p.nmRazaoSocal")	 		
	public Stream<PrestadoraVistoriaPrevia> findAllPrestadoraVistoriaPrevia();

	@Query(value = "select p from PrestadoraVistoriaPrevia p, AgendamentoVistoriaPrevia a where a.cdVouch = :cdVoucher and a.cdAgrmtVspre = p.cdAgrmtVspre ")
	public Optional<PrestadoraVistoriaPrevia> findPrestadoraPorAgendamento(@Param("cdVoucher") String codigoVoucher);
}
