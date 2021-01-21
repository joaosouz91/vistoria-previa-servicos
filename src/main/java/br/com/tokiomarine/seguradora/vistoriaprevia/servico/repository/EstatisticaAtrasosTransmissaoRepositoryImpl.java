package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.AtrasoTransmissaoAgrupamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.DetalhamentoAtrasoTransmissao;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.DetalhamentoAtrasoTransmissaoTotal;
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
public class EstatisticaAtrasosTransmissaoRepositoryImpl implements EstatisticaAtrasosTransmissaoRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<AtrasoTransmissaoAgrupamento> findAtrasoTransmissaoAgrupamentoList(String periodo, Long cdPrestadora, ParametroVistoriaPrevia paramVP) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("   L.CD_LVPRE, ");
        sql.append("   L.CD_AGRMT_VSPRE, ");
        sql.append("   P.NM_RAZAO_SOCAL, ");
        sql.append("   L.DT_TRNSM_VSPRE, ");
        sql.append("   L.DT_VSPRE, ");
        sql.append("   L.QT_DIAS_ATRSO_TRNSM ");
        sql.append(" FROM VPE0077_LVPRE L, VPE0230_PRTRA_VSPRE P ");
        sql.append(" WHERE TO_CHAR(L.DT_TRNSM_VSPRE, 'MMYYYY') = ?1 ");

        if(cdPrestadora != null && cdPrestadora != 0L) {
            sql.append(" AND  P.CD_AGRMT_VSPRE = ?2");
        }

        sql.append(" AND L.CD_AGRMT_VSPRE = P.CD_AGRMT_VSPRE ");
        sql.append(" ORDER BY L.CD_AGRMT_VSPRE ");

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);

        query.setParameter(1, periodo);

        if(cdPrestadora != null && cdPrestadora != 0L) {
            query.setParameter(2,cdPrestadora);
        }

        List<Tuple> tupleList = query.getResultList();

        if(tupleList.isEmpty()) return new ArrayList<>();

        return tupleList.stream().map(tuple ->
            new AtrasoTransmissaoAgrupamento(
                    (String)tuple.get(0),
                    ((BigDecimal)tuple.get(1)).longValue(),
                    (String)tuple.get(2),
                    (Date)tuple.get(3),
                    (Date)tuple.get(4),
                    ((BigDecimal)tuple.get(5)).longValue()
        )).collect(Collectors.toList());
    }

    public List<DetalhamentoAtrasoTransmissao> findDetalhamentoLaudoAtrasoTransmissaoList(String periodo, Long cdPrestadora) {

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT " );
        sql.append("   L.CD_LVPRE CODIGO_LAUDO, ");
        sql.append("   VC.CD_PLACA_VEICU, ");
        sql.append("   VC.CD_CHASSI_VEICU, ");
        sql.append("   PP.NM_PRPNT, ");
        sql.append("   L.DT_TRNSM_VSPRE , ");
        sql.append("   L.DT_VSPRE, ");
        sql.append("   L.DT_RCLSF_VSPRE, ");
        sql.append("   DECODE(L.CD_SITUC_VSPRE,'A','Aceitável','R','Recusado','L','Liberado','AF','Aceitação Forçada','S','Sujeito Análise') SITUACAO_LAUDO, ");
        sql.append("   L.QT_DIAS_ATRSO_TRNSM ");
        sql.append(" FROM VPE0077_LVPRE L, VPE0036_EMPRE_VSTRA E,VPE0119_VEICU_VSPRE VC,VPE0102_PRPNT_VSPRE PP");
        sql.append(" WHERE E.CD_AGRMT_VSPRE = ?1 ");
        sql.append(" AND TO_CHAR(L.DT_TRNSM_VSPRE, 'MMYYYY') = ?2 ");
        sql.append(" AND substr( L.CD_LVPRE,1,3) = E.CD_EMPRE_VSTRA  ");
        sql.append(" AND VC.CD_LVPRE = L.CD_LVPRE");
        sql.append(" AND VC.NR_VRSAO_LVPRE = l.NR_VRSAO_LVPRE ");
        sql.append(" AND PP.CD_LVPRE = L.CD_LVPRE");
        sql.append(" AND PP.NR_VRSAO_LVPRE = l.NR_VRSAO_LVPRE ");

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);

        query.setParameter(1,cdPrestadora);
        query.setParameter(2,periodo);

        List<Tuple> tupleList = query.getResultList();

        if(tupleList.isEmpty()) return new ArrayList<>();

        return tupleList.stream().map(tuple ->
                new DetalhamentoAtrasoTransmissao(
                        (String)tuple.get(0),
                        (String)tuple.get(1),
                        (String)tuple.get(2),
                        (String)tuple.get(3),
                        (Date)tuple.get(4),
                        (Date)tuple.get(5),
                        (Date)tuple.get(6),
                        (String)tuple.get(7),
                        ((BigDecimal)tuple.get(8)).longValue()
                )).collect(Collectors.toList());
    }

    public List<DetalhamentoAtrasoTransmissaoTotal> findDetalhamentoAtrasoTransmissaoTotal(Long cdPrestadora, String periodo) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT " );
        sql.append(     "L.CD_LVPRE, " );
        sql.append(     "E.CD_AGRMT_VSPRE, " );
        sql.append(     "E.NM_EMPRE_VSTRA, " );
        sql.append(     "L.DT_TRNSM_VSPRE, " );
        sql.append(     "L.DT_VSPRE, " );
        sql.append(     "L.QT_DIAS_ATRSO_TRNSM ");
        sql.append(" FROM VPE0077_LVPRE L, VPE0036_EMPRE_VSTRA E ");
        sql.append(" WHERE E.CD_AGRMT_VSPRE = ?1 ");
        sql.append("    AND TO_CHAR(L.DT_TRNSM_VSPRE, 'MMYYYY') = ?2 ");
        sql.append("    AND (TRUNC(L.DT_TRNSM_VSPRE) - TRUNC(L.DT_VSPRE)) >=  0  ");
        sql.append("    AND substr( L.CD_LVPRE,1,3) = E.CD_EMPRE_VSTRA  ");
        sql.append(" ORDER BY E.CD_EMPRE_VSTRA  ");

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);

        query.setParameter(1, cdPrestadora);
        query.setParameter(2, periodo);

        List<Tuple> tupleList = query.getResultList();

        if(tupleList.isEmpty()) return null;

        return tupleList.stream().map(tuple ->
                new DetalhamentoAtrasoTransmissaoTotal(
                        (String)tuple.get(0),
                        ((BigDecimal)tuple.get(1)).longValue(),
                        (String) tuple.get(2),
                        (Date) tuple.get(3),
                        (Date) tuple.get(4),
                        ((BigDecimal)tuple.get(5)).longValue()
                )).collect(Collectors.toList());
    }

}
