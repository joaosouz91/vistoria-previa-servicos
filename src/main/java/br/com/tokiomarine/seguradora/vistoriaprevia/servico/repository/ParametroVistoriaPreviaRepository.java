package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPrevia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ParametroVistoriaPreviaRepository extends JpaRepository<ParametroVistoriaPrevia, Long> {


    @Query(value = "SELECT P.* FROM VPE0090_PARAM_VSPRE P " +
        "WHERE (P.CD_MDUPR = ?1)" +
        "AND ((P.DT_INICO_VIGEN <= ?2 AND P.DT_FIM_VIGEN >= ?2) OR (P.DT_INICO_VIGEN <= ?2 AND P.DT_FIM_VIGEN IS NULL)) AND ROWNUM <= 1", nativeQuery = true)
    public Optional<ParametroVistoriaPrevia> findByCdMduprAndDtProcessamento(Long cdMdupr, Date dtProcessamento);

}
