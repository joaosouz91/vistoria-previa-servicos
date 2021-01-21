package br.com.tokiomarine.seguradora.ext.ssv.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.RegiaoTarifariaCep;

@Repository
public interface RegiaoTarifariaCepRepository extends JpaRepository<RegiaoTarifariaCep, Long>{

	@Query(value = "SELECT Rcep.cdRegiaoTrfraCep " + 
					"FROM RegiaoTarifariaCep Rcep " + 
					"WHERE Rcep.cdMdupr = :cdMdupr " + 
					"AND :nrCep BETWEEN Rcep.cdCepInicl AND Rcep.cdCepFinal " + 
					"AND :dtReferencia BETWEEN Rcep.dtInicoVigen AND Rcep.dtFimVigen")
	public List<Long> recuperarRegiaoTarifaria(@Param("cdMdupr") Long cdMdupr, @Param("nrCep") Long nrCep, @Param("dtReferencia") Date dtReferencia);
}
