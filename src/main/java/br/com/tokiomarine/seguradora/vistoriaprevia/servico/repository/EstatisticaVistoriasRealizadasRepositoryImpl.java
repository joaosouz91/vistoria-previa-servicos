package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.EstatisticaVistoriasRealizadas;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(readOnly = true)
public class EstatisticaVistoriasRealizadasRepositoryImpl implements EstatisticaVistoriasRealizadasRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<EstatisticaVistoriasRealizadas> getEstatisticaVistoriasRealizadas(String periodo, Long cdPrestadora) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT DISTINCT ");
        sql.append(" E.CD_AGRMT_VSPRE CD_AGRMT_VSPRE, ");
        sql.append(" L.CD_LVPRE CD_LVPRE, ");
        sql.append(" L.IC_LAUDO_VICDO IC_LAUDO_VICDO, ");
        sql.append(" L.CD_SITUC_BLQUE_VSPRE CD_SITUC_BLQUE_VSPRE, ");
        sql.append(" L.CD_SITUC_VSPRE CD_SITUC_VSPRE ");
        sql.append(" FROM VPE0077_LVPRE L, VPE0036_EMPRE_VSTRA E ");
        sql.append(" WHERE TO_CHAR(L.DT_TRNSM_VSPRE,'MMYYYY') = ?1 ");

        if(cdPrestadora != null && cdPrestadora != 0L) {
            sql.append(" AND E.CD_AGRMT_VSPRE = ?2 ");
        }

        sql.append(" AND L.CD_AGRMT_VSPRE =  E.CD_AGRMT_VSPRE ");
        sql.append(" AND L.NR_VRSAO_LVPRE = 0 ");
        sql.append(" ORDER BY E.CD_AGRMT_VSPRE,L.CD_SITUC_BLQUE_VSPRE  ");

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);

        query.setParameter(1,periodo);

        if(cdPrestadora != null && cdPrestadora != 0L) {
            query.setParameter(2,cdPrestadora);
        }

        List<Tuple> tupleList = query.getResultList();

        if(tupleList.isEmpty()) return new ArrayList<>();

        return tupleList.stream().map(tuple ->
            new EstatisticaVistoriasRealizadas(
                    ((BigDecimal)tuple.get(0)).longValue(),
                    (String)tuple.get(1),
                    (String)tuple.get(2),
                    ((BigDecimal)tuple.get(3)).longValue(),
                    (String)tuple.get(4))
        ).collect(Collectors.toList());
    }

    @Override
    public List<EstatisticaVistoriasRealizadas> getDetalheEstatisticaVistoriasRealizadas(String periodo, Long cdPrestadora, String situacaoVistoria) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT DISTINCT ");
        sql.append(" L.CD_LVPRE CD_LVPRE, ");
        sql.append(" V.CD_PLACA_VEICU CD_PLACA_VEICU, ");
        sql.append(" V.CD_CHASSI_VEICU CD_CHASSI_VEICU, ");
        sql.append(" L.DT_VSPRE DT_VSPRE, ");
        sql.append(" L.DT_TRNSM_VSPRE DT_TRNSM_VSPRE, ");
        sql.append(" L.CD_SITUC_BLQUE_VSPRE CD_SITUC_BLQUE_VSPRE, ");
        sql.append(" L.IC_LAUDO_VICDO IC_LAUDO_VICDO, ");
        sql.append(" DECODE(L.CD_SITUC_VSPRE,'A','Aceitável','S','Sujeito Análise','R','Recusada','L','Liberada','AF','Aceitação Forçada','RV','Regularização') CD_SITUC_VSPRE, ");
        sql.append(" L.NM_CIDAD_ORIGM_VSPRE NM_CIDAD_ORIGM_VSPRE, ");
        sql.append(" L.NM_CIDAD_DESTN_VSPRE NM_CIDAD_DESTN_VSPRE, ");
        sql.append(" L.CD_VOUCH CD_VOUCH, ");
        sql.append(" L.SG_UNIDD_FEDRC_VSPRE SG_UNIDD_FEDRC_VSPRE ");
        sql.append(" FROM VPE0077_LVPRE L, VPE0119_VEICU_VSPRE V,VPE0036_EMPRE_VSTRA E ");
        sql.append(" WHERE TO_CHAR(L.DT_TRNSM_VSPRE,'MMYYYY') = ?1 ");

        if(cdPrestadora != null && cdPrestadora != 0L) {
            sql.append(" AND E.CD_AGRMT_VSPRE = ?2 ");
        }

        if("BL".equals(situacaoVistoria)) {
            sql.append(" AND L.CD_SITUC_BLQUE_VSPRE = 1 ");
        } else if ("NV".equals(situacaoVistoria)) {
            sql.append(" AND L.IC_LAUDO_VICDO <> 'S' AND L.CD_SITUC_BLQUE_VSPRE = 0 ");
        } else if ("VI".equals(situacaoVistoria)) {
            sql.append(" AND L.IC_LAUDO_VICDO = 'S' AND L.CD_SITUC_BLQUE_VSPRE = 0 ");
        } else if ("AC".equals(situacaoVistoria)) {
            sql.append(" AND L.CD_SITUC_VSPRE = 'A' ");
        } else if ("SA".equals(situacaoVistoria)) {
            sql.append(" AND L.CD_SITUC_VSPRE = 'S' ");
        } else if ("RE".equals(situacaoVistoria)) {
            sql.append(" AND L.CD_SITUC_VSPRE = 'R' ");
        } else if ("LI".equals(situacaoVistoria)) {
            sql.append(" AND L.CD_SITUC_VSPRE = 'L' ");
        } else if ("AF".equals(situacaoVistoria)) {
            sql.append(" AND L.CD_SITUC_VSPRE = 'AF' ");
        }
        else if ("RV".equals(situacaoVistoria)) {
            sql.append(" AND L.CD_SITUC_VSPRE = 'RV' ");
        }

        sql.append(" AND L.CD_AGRMT_VSPRE = E.CD_AGRMT_VSPRE ");
        sql.append(" AND L.CD_LVPRE = V.CD_LVPRE ");
        sql.append(" AND L.NR_VRSAO_LVPRE = V.NR_VRSAO_LVPRE ");
        sql.append(" AND L.NR_VRSAO_LVPRE = 0 ");
        sql.append(" ORDER BY L.CD_LVPRE ");

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);

        query.setParameter(1,periodo);

        if(cdPrestadora != null && cdPrestadora != 0L) {
            query.setParameter(2,cdPrestadora);
        }

        List<Tuple> tupleList = query.getResultList();

        if(tupleList.isEmpty()) return new ArrayList<>();

        return tupleList.stream().map(tuple ->
                new EstatisticaVistoriasRealizadas(
                        (String)tuple.get(0),
                        (String)tuple.get(1),
                        (String)tuple.get(2),
                        (Date)tuple.get(3),
                        (Date)tuple.get(4),
                        ((BigDecimal)tuple.get(5)).longValue(),
                        (String)tuple.get(6),
                        (String)tuple.get(7),
                        (String)tuple.get(8),
                        (String)tuple.get(9),
                        (String)tuple.get(10),
                        (String)tuple.get(11)
                )
        ).collect(Collectors.toList());
    }
}
