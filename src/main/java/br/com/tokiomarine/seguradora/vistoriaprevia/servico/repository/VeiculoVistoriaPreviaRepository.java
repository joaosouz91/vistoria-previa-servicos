package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VeiculoVistoriaPrevia;

@Repository
@Transactional(readOnly = true)
public interface VeiculoVistoriaPreviaRepository extends JpaRepository<VeiculoVistoriaPrevia, Long> {

	@Query(value = "SELECT " + "	v " + "FROM " + "	VeiculoVistoriaPrevia v " + "WHERE "
			+ "	v.cdLvpre = :cdLvpre ")
	public VeiculoVistoriaPrevia findByVeiculoVistoriaPrevia(@Param("cdLvpre") String cdLvpre);

	public VeiculoVistoriaPrevia findLaudoBycdLvpre(String cdLvpre);

	Optional<VeiculoVistoriaPrevia> findByCdLvpreAndNrVrsaoLvpre(String cdLvpre, Long nrVrsaoLvpre);
}
