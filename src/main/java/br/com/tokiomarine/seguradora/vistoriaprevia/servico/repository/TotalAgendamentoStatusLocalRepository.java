package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.core.exception.DAOException;
import br.com.tokiomarine.seguradora.core.exception.ExceptionUtil;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.TotalAgendamentoStatusLocal;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.TotalStatusPrestadora;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TotalAgendamentoStatusLocalRepository {

    @PersistenceContext
    private EntityManager em;

    public List<TotalAgendamentoStatusLocal> recuperarTotalStatusLocal(Long codCorretor, Date dataDe, Date dataAte) {

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT SA1.CD_SITUC_AGMTO, ");
        sql.append("        AV.TP_VSPRE, ");
        sql.append("   	    COUNT(*) QTD_SITUC_STATS ");
        sql.append(" FROM VPE0437_STATU_AGMTO SA1, VPE0425_AGEND_VSPRE AV ");
        sql.append(" WHERE SA1.ID_STATU_AGMTO IN( ");
        sql.append("  							SELECT MAX(SA.ID_STATU_AGMTO) " );
        sql.append("   							FROM VPE0424_VSPRE_OBGTA VO,  ");
        sql.append("   								 VPE0425_AGEND_VSPRE AG,  ");
        sql.append("	    						 VPE0437_STATU_AGMTO SA   ");
        sql.append("                            WHERE VO.CD_CRTOR_CIA = :codigoCorretor ");
        sql.append("                            AND AG.CD_VOUCH = VO.CD_VOUCH ");
        sql.append("                            AND AG.DT_ULTMA_ALTER > :dataInicio ");
        sql.append("                            AND AG.DT_ULTMA_ALTER < :dataFim ");
        sql.append("                            AND SA.CD_VOUCH = VO.CD_VOUCH ");
        sql.append("                            GROUP BY SA.CD_VOUCH ) ");
        sql.append(" AND AV.CD_VOUCH = SA1.CD_VOUCH ");
        sql.append(" GROUP BY SA1.CD_SITUC_AGMTO, AV.TP_VSPRE  ");
        sql.append(" UNION ALL ");
        sql.append(" SELECT Sa.CD_SITUC_AGMTO, ");
        sql.append("        ag.TP_VSPRE, ");
        sql.append("        COUNT(*) QTD_SITUC_STATS ");
        sql.append(" FROM VPE0424_VSPRE_OBGTA VO, ");
        sql.append("      VPE0425_AGEND_VSPRE AG, ");
        sql.append("      VPE0437_STATU_AGMTO SA ");
        sql.append(" WHERE VO.CD_CRTOR_CIA = :codigoCorretor ");
        sql.append(" AND AG.CD_VOUCH = VO.CD_VOUCH ");
        sql.append(" AND AG.DT_ULTMA_ALTER > :dataInicio AND AG.DT_ULTMA_ALTER < :dataFim ");
        sql.append(" AND SA.CD_VOUCH = VO.CD_VOUCH ");
        sql.append(" AND Sa.Cd_Situc_Agmto = 'RGD' ");
        sql.append(" GROUP BY Sa.Cd_Situc_Agmto, ag.TP_VSPRE ");

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);

        query.setParameter("codigoCorretor", codCorretor);
        query.setParameter("dataInicio", dataDe);
        query.setParameter("dataFim", dataAte);

        List<Tuple> tupleList = query.getResultList();

        if(tupleList.isEmpty()) return new ArrayList<>();

        return tupleList.stream().map(tuple ->
                new TotalAgendamentoStatusLocal(
                        (String)tuple.get(0),
                        (String)tuple.get(1),
                        tuple.get(2) != null ? ((BigDecimal)tuple.get(2)).longValue() : null
                )).collect(Collectors.toList());
    }

}
