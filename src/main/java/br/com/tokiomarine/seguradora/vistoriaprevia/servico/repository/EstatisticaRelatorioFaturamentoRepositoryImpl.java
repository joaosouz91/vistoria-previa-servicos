package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.DetalhamentoEstatisticaFaturamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.EstatisticaFaturamento;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EstatisticaRelatorioFaturamentoRepositoryImpl implements EstatisticaRelatorioFaturamentoRepository {

    @PersistenceContext
    private EntityManager em;

    public List<EstatisticaFaturamento> findEstatisticaFranquias(String periodo, Long cdPrestadora) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("    COUNT(LV.CD_LVPRE) QUANTIDADE_KM, ");
        sql.append("    LV.CD_LVPRE, " );
        sql.append("    PV.CD_AGRMT_VSPRE CD_EMPRE_VSTRA, ");
        sql.append("    PV.NM_RAZAO_SOCAL NM_EMPRE_VSTRA, ");
        sql.append("    LV.TP_LOCAL_VSPRE, ");
        sql.append("    LV.QT_KM_RALZO ");
        sql.append(" FROM VPE0077_LVPRE LV, VPE0230_PRTRA_VSPRE PV ");
        sql.append(" WHERE PV.CD_AGRMT_VSPRE = LV.CD_AGRMT_VSPRE ");
        sql.append(" AND TO_CHAR(LV.DT_TRNSM_VSPRE,'MMYYYY') = ?1 ");
        sql.append(" AND LV.CD_AGRMT_VSPRE = ?2 ");
        sql.append(" AND LV.NR_VRSAO_LVPRE = 0 ");
        sql.append(" GROUP BY LV.CD_LVPRE, PV.CD_AGRMT_VSPRE ,PV.NM_RAZAO_SOCAL,LV.TP_LOCAL_VSPRE,QT_KM_RALZO ");
        sql.append(" ORDER BY PV.CD_AGRMT_VSPRE,LV.TP_LOCAL_VSPRE,QT_KM_RALZO ");

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);

        query.setParameter(1, periodo);
        query.setParameter(2, cdPrestadora);

        List<Tuple> tupleList = query.getResultList();

        if (tupleList.isEmpty()) return new ArrayList<>();

        return tupleList.stream().map(tuple ->
                new EstatisticaFaturamento(
                        ((BigDecimal)tuple.get(0)).longValue(),
                        (String)tuple.get(1),
                        ((BigDecimal)tuple.get(2)).longValue(),
                        (String)tuple.get(3),
                        (String)tuple.get(4),
                        ((BigDecimal)tuple.get(5)).longValue()
                )
        ).collect(Collectors.toList());
    }

    public List<EstatisticaFaturamento> findEstatisticaPrestadoras(String periodo) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("   COUNT(LV.CD_LVPRE) QUANTIDADE_KM, ");
        sql.append("   LV.CD_LVPRE, ");
        sql.append("   PV.CD_AGRMT_VSPRE CD_EMPRE_VSTRA, ");
        sql.append("   PV.NM_RAZAO_SOCAL NM_EMPRE_VSTRA,");
        sql.append("   LV.TP_LOCAL_VSPRE,");
        sql.append("   LV.QT_KM_RALZO ");
        sql.append(" FROM VPE0077_LVPRE LV, VPE0230_PRTRA_VSPRE PV ");
        sql.append(" WHERE PV.CD_AGRMT_VSPRE = LV.CD_AGRMT_VSPRE ");
        sql.append(" AND TO_CHAR(LV.DT_TRNSM_VSPRE,'MMYYYY') = ?1 ");
        sql.append(" AND QT_KM_RALZO >= 0 ");
        sql.append(" AND LV.NR_VRSAO_LVPRE = 0 ");
        sql.append(" GROUP BY LV.CD_LVPRE, PV.CD_AGRMT_VSPRE ,PV.NM_RAZAO_SOCAL,LV.TP_LOCAL_VSPRE,QT_KM_RALZO ");
        sql.append(" ORDER BY PV.CD_AGRMT_VSPRE,LV.TP_LOCAL_VSPRE,QT_KM_RALZO ");

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);

        query.setParameter(1, periodo);

        List<Tuple> tupleList = query.getResultList();

        if (tupleList.isEmpty()) return new ArrayList<>();

        return tupleList.stream().map(tuple ->
                new EstatisticaFaturamento(
                        ((BigDecimal)tuple.get(0)).longValue(),
                        (String)tuple.get(1),
                        ((BigDecimal)tuple.get(2)).longValue(),
                        (String)tuple.get(3),
                        (String)tuple.get(4),
                        ((BigDecimal)tuple.get(5)).longValue()
                )
        ).collect(Collectors.toList());
    }

    public List<DetalhamentoEstatisticaFaturamento> findDetalheEstatisticaPrestadora(Long cdPrestadora, String periodo, String tipoLocalVistoria, Long distanciaKilometragem) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT DISTINCT ");
        sql.append("    LV.CD_LVPRE, ");
        sql.append("    LV.DT_VSPRE, ");
        sql.append("    LV.DT_INCLS_RGIST, ");
        sql.append("    VV.CD_PLACA_VEICU, ");
        sql.append("    VV.CD_CHASSI_VEICU, ");
        sql.append("    LV.CD_CRTOR_SEGUR, ");
        sql.append("    LV.CD_LOCAL_CAPTC, ");
        sql.append("    LV.TP_LOCAL_VSPRE, ");
        sql.append("    LV.QT_KM_RALZO, ");
        sql.append("    LV.NM_CIDAD_ORIGM_VSPRE, ");
        sql.append("    LV.NM_CIDAD_DESTN_VSPRE, ");
        sql.append("    LV.CD_SUCSL_COMRL, ");
        sql.append("    LV.CD_VOUCH, ");
        sql.append("    DECODE(LV.CD_SITUC_VSPRE,'A','Aceitável','S','Sujeito Análise','R','Recusada','L','Liberada','AF','Aceitação Forçada','RV','Regularização') CD_SITUC_VSPRE, ");
        sql.append("    LV.SG_UNIDD_FEDRC_VSPRE,  ");

        sql.append("DECODE(VO.TP_VEICU,'P','Auto Passeio',");
        sql.append("DECODE(VO.TP_VEICU,'O','Auto Classico',");
        sql.append("DECODE(VO.TP_VEICU,'U','Utilitario Carga',");
        sql.append("DECODE(VO.TP_VEICU,'C','Caminhao',");
        sql.append("DECODE(VO.TP_VEICU,'B','Auto Classico',");
        sql.append("DECODE(VO.TP_VEICU,'L','Utilitario Popular',");
        sql.append("DECODE(VO.TP_VEICU,'R','Auto Roubo',");
        sql.append("DECODE(VO.TP_VEICU,'F','Auto Frota','NAO IDENTIFICADO')))))))) DS_MDUPR,");

        sql.append(" LV.CD_VSTRO ");

        sql.append(" FROM VPE0077_LVPRE LV, VPE0119_VEICU_VSPRE VV, VPE0424_VSPRE_OBGTA VO ");

        sql.append(" WHERE TO_CHAR(LV.DT_TRNSM_VSPRE,'MMYYYY') = ?1 ");
        sql.append(" AND LV.CD_AGRMT_VSPRE IN (SELECT CD_AGRMT_VSPRE FROM VPE0036_EMPRE_VSTRA WHERE CD_AGRMT_VSPRE = ?2) ");


        if (distanciaKilometragem == 1L) {
            sql.append(" AND QT_KM_RALZO BETWEEN 0  AND 50");
        } else if (distanciaKilometragem == 2L) {
            sql.append(" AND QT_KM_RALZO BETWEEN 51  AND 100");
        } else if (distanciaKilometragem == 3L) {
            sql.append(" AND QT_KM_RALZO BETWEEN 101  AND 200");
        } else if (distanciaKilometragem == 4L) {
            sql.append(" AND QT_KM_RALZO >= 201");
        }

        if ("D".equals(tipoLocalVistoria)) {
            sql.append(" AND LV.TP_LOCAL_VSPRE = 'D' ");
        } else if ("P".equals(tipoLocalVistoria)) {
            sql.append(" AND LV.TP_LOCAL_VSPRE = 'P' ");
        } else if ("M".equals(tipoLocalVistoria)) {
            sql.append(" AND LV.TP_LOCAL_VSPRE = 'M' ");
        } else {
            sql.append(" AND LV.TP_LOCAL_VSPRE in ('P','D','M') ");
        }

        sql.append(" AND VV.CD_LVPRE = LV.CD_LVPRE ");
        sql.append(" AND VV.NR_VRSAO_LVPRE = LV.NR_VRSAO_LVPRE ");
        sql.append(" AND VO.CD_VOUCH = LV.CD_VOUCH ");
        sql.append(" AND VO.ID_VSPRE_OBGTA =  (SELECT MAX(VP.ID_VSPRE_OBGTA) ");
        sql.append("   FROM VPE0424_VSPRE_OBGTA VP ");
        sql.append("   WHERE VP.CD_VOUCH = LV.CD_VOUCH) ");

        sql.append(" ORDER BY LV.DT_VSPRE asc");

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);

        query.setParameter(1, periodo);
        query.setParameter(2, cdPrestadora);

        List<Tuple> tupleList = query.getResultList();

        if (tupleList.isEmpty()) return new ArrayList<>();

        return tupleList.stream().map(tuple ->
                new DetalhamentoEstatisticaFaturamento(
                        (String)tuple.get(0),
                        (Date)tuple.get(1),
                        (Date)tuple.get(2),
                        (String)tuple.get(3),
                        (String)tuple.get(4),
                        ((BigDecimal)tuple.get(5)).longValue(),
                        tuple.get(6) != null? ((BigDecimal)tuple.get(6)).longValue() : null,
                        (String)tuple.get(7),
                        null,
                        ((BigDecimal)tuple.get(8)).longValue(),
                        (String)tuple.get(9),
                        (String)tuple.get(10),
                        ((BigDecimal)tuple.get(11)).toString(),
                        (String)tuple.get(12),
                        (String)tuple.get(13),
                        (String)tuple.get(14),
                        (String)tuple.get(15),
                        null,
                        (String)tuple.get(16)
                )
        ).collect(Collectors.toList());
    }

}


