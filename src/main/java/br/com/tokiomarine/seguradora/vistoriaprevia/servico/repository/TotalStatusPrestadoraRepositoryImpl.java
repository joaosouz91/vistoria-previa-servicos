package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.TotalStatusPrestadora;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.RelatorioAgendamentoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TotalStatusPrestadoraRepositoryImpl implements TotalStatusPrestadoraRepository{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RelatorioAgendamentoRepository relatorioAgendamentoRepository;

    public List<TotalStatusPrestadora> findTotalStatusPrestadoraByFilter(RelatorioAgendamentoFilter filter) {

        StringBuilder sql = new StringBuilder();

        sql.append("   SELECT S.Cd_situc_agmto AS CD_SITUC_AGMTO, ");
        sql.append("          COUNT ( * )      AS QTD_SITUC_AGTO, ");
        sql.append("          P.Cd_agrmt_vspre AS CD_AGRMT_VSPRE, ");
        sql.append("          COUNT ( * )      AS QTD_PRTRA_AGTO, ");
        sql.append("          ( SELECT P0.Nm_razao_socal          ");
        sql.append("             FROM Vpe0230_prtra_vspre P0      ");
        sql.append("            WHERE P0.Cd_agrmt_vspre = P.Cd_agrmt_vspre ) AS NM_RAZAO_SOCIAL_PRTA ");

        sql.append(relatorioAgendamentoRepository.montarSqlComunRelAgto(filter, false, true, false, false));

        sql.append(" GROUP BY P.Cd_agrmt_vspre, ");
        sql.append("          S.Cd_situc_agmto  ");
        sql.append(" ORDER BY P.Cd_agrmt_vspre  ");

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);

        List<Tuple> tupleList = query.getResultList();

        if(tupleList.isEmpty()) return new ArrayList<>();

        return tupleList.stream().map(tuple ->
                new TotalStatusPrestadora(
                        (String)tuple.get(0),
                        tuple.get(1) != null ? ((BigDecimal)tuple.get(1)).longValue() : null,
                        tuple.get(2) != null ? ((BigDecimal)tuple.get(2)).longValue() : null,
                        (String)tuple.get(4),
                        tuple.get(3) != null ? ((BigDecimal)tuple.get(3)).longValue() : null
                )).collect(Collectors.toList());
    }


}
