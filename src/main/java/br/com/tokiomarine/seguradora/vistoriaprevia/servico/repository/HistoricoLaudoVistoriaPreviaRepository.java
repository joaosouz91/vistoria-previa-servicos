package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.HistoricoLaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.pk.HistoricoLaudoVistoriaPreviaPK;

@Repository
@Transactional(readOnly = true)
public interface HistoricoLaudoVistoriaPreviaRepository extends JpaRepository<HistoricoLaudoVistoriaPrevia, HistoricoLaudoVistoriaPreviaPK> {

	@Query(value = "select hl from HistoricoLaudoVistoriaPrevia hl where hl.cdLvpre= :cdLvpre and hl.nrVrsaoLvpre= :nrVrsaoLvpre")
	public List<HistoricoLaudoVistoriaPrevia> findHistoricoLaudoVistoriaPreviaPorLaudoEVersao(@Param("cdLvpre") String codigoLaudo, @Param("nrVrsaoLvpre") Long numeroVersao);
	
}
