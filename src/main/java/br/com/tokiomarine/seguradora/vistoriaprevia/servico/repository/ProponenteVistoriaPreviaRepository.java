package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ProponenteVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.pk.ProponenteVistoriaPreviaPK;

@Repository
@Transactional(readOnly = true)
public interface ProponenteVistoriaPreviaRepository extends JpaRepository<ProponenteVistoriaPrevia, ProponenteVistoriaPreviaPK>{
	
	@Query(value =  "SELECT " +
			 		" 	p " +
					"FROM " +
					"	ProponenteVistoriaPrevia p " +
					"WHERE " +
					"	p.cdLvpre = :cdLvpre")
	public ProponenteVistoriaPrevia findByProponenteVistoria(@Param("cdLvpre") String cdLvpre);
	
	
	public ProponenteVistoriaPrevia findProponenteBycdLvpre(String cdLvpre);
}