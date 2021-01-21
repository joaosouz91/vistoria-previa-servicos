package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.HistoricoMotivoInconsistenciaLaudo;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.pk.HistoricoMotivoInconsistenciaLaudoPK;

@Repository
@Transactional(readOnly = true)
public interface HistoricoMotivoInconsistenciaLaudoRepository extends JpaRepository<HistoricoMotivoInconsistenciaLaudo, HistoricoMotivoInconsistenciaLaudoPK>{
	
	@Query (value = "select hm from HistoricoMotivoInconsistenciaLaudo hm where hm.cdLvpre= :codLvpre and hm.nrVrsaoLvpre= :numVrsaoLvpre")
	public List<HistoricoMotivoInconsistenciaLaudo> findHistoricoMotivoInconsistenciaLaudoPorLaudoEVersao(@Param("codLvpre") String codigoLaudo, @Param("numVrsaoLvpre") Long numeroVersaoLaudo);
	
}
