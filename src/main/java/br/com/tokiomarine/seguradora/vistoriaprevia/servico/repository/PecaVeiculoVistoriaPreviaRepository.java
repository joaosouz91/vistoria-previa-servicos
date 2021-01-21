package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PecaVeiculoVistoriaPrevia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PecaVeiculoVistoriaPreviaRepository extends JpaRepository<PecaVeiculoVistoriaPrevia, Long> {

}
