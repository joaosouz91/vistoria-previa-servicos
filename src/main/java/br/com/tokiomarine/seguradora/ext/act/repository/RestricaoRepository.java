package br.com.tokiomarine.seguradora.ext.act.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.aceitacao.crud.model.Restricao;

@Repository
public interface RestricaoRepository extends JpaRepository<Restricao, Long>{

	@Query(value =  "select "+
					"	r "+
			 		"from "+
			 		"	Proposta p, "+
			 		"	Restricao r "+
					"where "+
					"	r.idPpota = p.idPpota"+
					"	and p.cdPpota = :codigoProposta"+					
					"	and r.nrItseg = :numeroItemSegurado"+
					"	and r.tpRestr = :tipoRestricao")
	public Restricao buscarRestricao(@Param("codigoProposta") Long codigoProposta, @Param("numeroItemSegurado") Long numeroItemSegurado, @Param("tipoRestricao") String tipoRestricao);

	@Query("SELECT case when count(Rit.nrItseg) > 0 then true else false end "
			+ "FROM Proposta proposta, Restricao Rit "
			+ "WHERE proposta.cdPpota = :codigoProposta "
			+ "AND proposta.idPpota = Rit.idPpota "
			+ "AND Rit.nrItseg = :nrItseg "
			+ "AND Rit.tpRestr = 'VIS' "
			+ "AND Rit.cdSitucRestr = 'LIB' "
			+ "AND Rit.tpLibec is not null")
	public boolean verificaVpDispensada(@Param("codigoProposta") Long codigoProposta, @Param("nrItseg") Long nrItseg);
}
