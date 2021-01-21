package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.pk.LaudoVistoriaPreviaPK;

@Repository
@Transactional(readOnly = true)
public interface LaudoVistoriaPreviaRepository extends JpaRepository<LaudoVistoriaPrevia, LaudoVistoriaPreviaPK>{

	@Query(value =  "select l from LaudoVistoriaPrevia l where l.cdLvpre = :cdLvpre and l.nrVrsaoLvpre = :nrVrsaoLvpre")
	public LaudoVistoriaPrevia findLaudoByCdLvpreNrVrsaoLvpreCdSituc(@Param("cdLvpre") String cdLvpre, @Param("nrVrsaoLvpre") Long nrVrsaoLvpre);
			
	
	
	@Query(value =  "SELECT " +
			 		" 	l, v, c " +
					"FROM " +
					"	LaudoVistoriaPrevia l, VeiculoVistoriaPrevia v, ProponenteVistoriaPrevia c " +
					"WHERE " +
					"	l.cdLvpre = :cdLvpre " +
					" 	AND l.cdLvpre = v.cdLvpre " +
					"	AND c.cdLvpre = v.cdLvpre " +
					"	AND c.nrVrsaoLvpre = v.nrVrsaoLvpre")
	public List<Object[]> findLaudoBy(@Param("cdLvpre") String cdLvpre);
					
	public Optional<LaudoVistoriaPrevia> findFirstByCdVouchOrderByDtInclsRgistDesc(String cdVouch);
	
	public LaudoVistoriaPrevia findLaudoBycdLvpre(String codigo);
	
	
	@Query(value =  "SELECT " +
			 		" 	laudoVistoriaPrevia, veiculoVistoriaPrevia, itemSegurado " +
					"FROM " +
					"	LaudoVistoriaPrevia laudoVistoriaPrevia "+
					"	inner join VeiculoVistoriaPrevia veiculoVistoriaPrevia on (veiculoVistoriaPrevia.cdLvpre = laudoVistoriaPrevia.cdLvpre) "+
					"	left outer join ItemSegurado itemSegurado on (itemSegurado.codLaudoVistoriaPrevia = laudoVistoriaPrevia.cdLvpre) " +					
					"WHERE " +
					"	laudoVistoriaPrevia.cdLvpre = :cdLvpre ")
	public List<Object[]> findLaudoByDadosBasicos(@Param("cdLvpre") String cdLvpre);
	
	
	@Query(value =  "SELECT " +
	 		" 	laudoVistoriaPrevia, veiculoVistoriaPrevia, itemSegurado " +
			"FROM " +
			"	LaudoVistoriaPrevia laudoVistoriaPrevia "+
			"	inner join VeiculoVistoriaPrevia veiculoVistoriaPrevia on (veiculoVistoriaPrevia.cdLvpre = laudoVistoriaPrevia.cdLvpre) "+
			"	left outer join ItemSegurado itemSegurado on (itemSegurado.codLaudoVistoriaPrevia = laudoVistoriaPrevia.cdLvpre) " +					
			"WHERE " +
			"	laudoVistoriaPrevia.cdLvpre = :cdLvpre ")
	public LaudoVistoriaPrevia findLaudoByDadosBasico(@Param("cdLvpre") String cdLvpre);

	public List<LaudoVistoriaPrevia> findByCdVouch(String voucher);
	
	@Query("SELECT COUNT(l) FROM LaudoVistoriaPrevia l WHERE l.cdCrtorSegur = :codCorretor AND l.dtVspre > :dataReferenciaDe and l.dtVspre < :dataReferenciaAte")
	Long recuperarQtdTotalVistoria(Long codCorretor, Date dataReferenciaDe, Date dataReferenciaAte);
}
