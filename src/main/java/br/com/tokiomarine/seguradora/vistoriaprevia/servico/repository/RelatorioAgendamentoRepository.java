package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.core.exception.DAOException;
import br.com.tokiomarine.seguradora.core.exception.ExceptionUtil;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.RelatorioAgendamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.VistoriaPreviaCorretor;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.RelatorioAgendamentoFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RelatorioAgendamentoRepository {

    @PersistenceContext
    private EntityManager em;

    public List<RelatorioAgendamento> findRelatorioAgendamentoByFilter(
            RelatorioAgendamentoFilter filter,
            boolean consultaHistorico,
            boolean agendamentosCorrentes,
            boolean agendamentosRejeitados) {

        StringBuilder sql = new StringBuilder();

        sql.append("     SELECT ag.CD_VOUCH             AS CD_VOUCH,              ");
        sql.append("	        ag.CD_VOUCH_ANTER       AS CD_VOUCH_ANTER,        ");
        sql.append("	        ag.DT_ULTMA_ALTER       AS DT_ULTMA_ALTER_AGTO,   ");
        sql.append("	        ag.TP_VSPRE             AS TP_VSPRE,   			  ");
        sql.append("	        s.ID_STATU_AGMTO        AS ID_STATU_AGMTO,        ");
        sql.append("	        s.CD_SITUC_AGMTO        AS CD_SITUC_AGMTO,        ");
        sql.append("	        s.CD_MOTV_SITUC_AGMTO   AS CD_MOTV_SITUC_AGMTO,   ");
        sql.append("	        s.DS_MOTV_VSTRI_FRUDA   AS DS_MOTV_VSTRI_FRUDA,   ");
        sql.append("	        s.DT_ULTMA_ALTER        AS DT_ULTMA_ALTER_STATUS, ");
        sql.append("	        mot.DS_COPTA_COLUN_TIPO AS DS_MOTV_SITUC_AGMTO,   ");
        sql.append("	        vp.ID_VSPRE_OBGTA       AS ID_VSPRE_OBGTA,        ");
        sql.append("	        vp.CD_CRTOR_CIA         AS CD_CRTOR_CIA,          ");
        sql.append("	        co.NM_CONVO             AS NM_CRTOR_CIA,          ");
        sql.append("	        vp.NR_CPF_CNPJ_CLIEN    AS NR_CPF_CNPJ_CLIEN,     ");
        sql.append("	        vp.NM_CLIEN             AS NM_CLIEN,              ");
        sql.append("	        vp.CD_PLACA_VEICU       AS CD_PLACA_VEICU,        ");
        sql.append("	        vp.CD_CHASSI_VEICU      AS CD_CHASSI_VEICU,       ");
        sql.append("	        p.CD_AGRMT_VSPRE        AS CD_AGRMT_VSPRE,        ");
        sql.append("	        p.NM_RAZAO_SOCAL        AS NM_RAZAO_SOCIAL_PRTA,  ");
        sql.append("            d.DT_VSPRE 				AS DT_VSPRE, 			  ");
        sql.append("            d.IC_PERIO_VSPRE 		AS IC_PERIO_VSPRE,		  ");
        sql.append("            s.CD_USURO_ULTMA_ALTER	AS CD_USURO_ULTMA_ALTER	  ");

        boolean agtosFrustrados = true;
        if ("RJD".equals(filter.getCodSitVistoria())) {
            agtosFrustrados = false;
            filter.setCodSitVistoria(null);
        }

        sql.append(this.montarSqlComunRelAgto(filter, consultaHistorico, agendamentosCorrentes, agendamentosRejeitados, agtosFrustrados));

        if (consultaHistorico) {
            sql.append("	ORDER BY Ag.Dt_ultma_alter, ");
            sql.append("	         Ag.Cd_vouch, ");
            sql.append("	         S.Id_statu_agmto DESC");
        } else {
            if ("P".equals(filter.getFormaAgrupamento())) { //Agrupado por Prestadora(P)
                sql.append(" ORDER BY ag.CD_AGRMT_VSPRE, s.CD_SITUC_AGMTO ");
            } else if ("C".equals(filter.getFormaAgrupamento())) { //Agrupado por Corretor(C)
                sql.append(" ORDER BY vp.CD_CRTOR_CIA, ag.DT_ULTMA_ALTER, Ag.Cd_vouch ");
            } else {
                sql.append(" ORDER BY ag.CD_AGRMT_VSPRE, ag.DT_ULTMA_ALTER ");//Agrupado por Voucher(V)
            }
        }

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);

        List<Tuple> tupleList = query.getResultList();

        if(tupleList.isEmpty()) return new ArrayList<>();

        return tupleList.stream().map(tuple ->
                new RelatorioAgendamento(
                        (String)tuple.get(0),
                        (String)tuple.get(1),
                        null,
                        (Date)tuple.get(2),
                        (String)tuple.get(3),
                        tuple.get(4) != null ? ((BigDecimal)tuple.get(4)).longValue() : null,
                        (String)tuple.get(5),
                        null,
                        tuple.get(6) != null ? ((BigDecimal)tuple.get(6)).longValue() : null,
                        (String)tuple.get(7),
                        (Date)tuple.get(8),
                        (String)tuple.get(9),
                        tuple.get(10) != null ? ((BigDecimal)tuple.get(10)).longValue() : null,
                        tuple.get(11) != null ? ((BigDecimal)tuple.get(11)).longValue() : null,
                        null,
                        (String)tuple.get(12),
                        (String)tuple.get(13),
                        (String)tuple.get(14),
                        (String)tuple.get(15),
                        (String)tuple.get(16),
                        tuple.get(17) != null ? ((BigDecimal)tuple.get(17)).longValue() : null,
                        (String)tuple.get(18),
                        (Date)tuple.get(19),
                        (String)tuple.get(20),
                        (String)tuple.get(21)
                )
        ).collect(Collectors.toList());

    }

    protected StringBuilder montarSqlComunRelAgto(
            RelatorioAgendamentoFilter filter,
            boolean consultaHistorico,
            boolean agtosCorrentes,
            boolean agtosRejeitados,
            boolean agtosFrustrados) {

        StringBuilder sql = new StringBuilder();

        sql.append("	   FROM Vpe0425_agend_vspre AG	");

        //* JOIN Vpe0425 e Vpe0437 (Status Agto)
        sql.append("	   JOIN 																					 ");
        sql.append("	        ( Vpe0437_statu_agmto S 															 ");
        //* JOIN Vpe0437 (Status Agto) e SSV4001 (desc motivos cancelamento)
        sql.append("	          LEFT JOIN SSV4001_CNTDO_COLUN_TIPO mot 											 ");
        sql.append("	                 ON (     TO_CHAR(S.CD_MOTV_SITUC_AGMTO) = mot.VL_CNTDO_COLUN_TIPO 			 ");
        sql.append("	                      AND mot.NM_COLUN_TIPO = 'CD_MOTV_SITUC_AGMTO' 						 ");
        sql.append("	                     AND S.DT_ULTMA_ALTER >= MOT.DT_INICO_VIGEN                              ");
        sql.append("                         AND S.DT_ULTMA_ALTER <= MOT.DT_FIM_VIGEN                                ");
        sql.append("	                    ) 																         ");
        sql.append("	         )																					 ");

        //* ON Vpe0425 e Vpe0437 (Status Agto)
        sql.append("	     ON ( S.Cd_vouch = AG.Cd_vouch ) 					");

        //* JOIN Vpe0425 e Vpe0230 (Prestadora)
        sql.append("	   JOIN Vpe0230_prtra_vspre P 							");
        sql.append("	     ON ( P.Cd_agrmt_vspre = AG.Cd_agrmt_vspre ) 		");

        //* Se agtosCorrentes = true, pega só agendamentos que tem relacionamento ativo com a 424
        if (agtosCorrentes) {
            sql.append("       JOIN ( Vpe0424_vspre_obgta VP ");
        } else {
            sql.append("  LEFT JOIN ( Vpe0424_vspre_obgta VP ");
        }

        //* JOIN Vpe0424 e Ssv5046 (Dados do Corretor)
        sql.append("              JOIN Ssv5046_convo CO 							");
        sql.append("                ON (     VP.Cd_crtor_cia = CO.Cd_itrno_convo 	");
        sql.append("                     AND CO.Tp_convo = 'C'						");
        sql.append("                   )                                         	");
        sql.append("             )                                         			");

        //* ON Vpe0425 e Vpe0424
        sql.append("	     ON ( Vp.Cd_vouch = AG.Cd_vouch ) ");

        //* JOIN Vpe0425 e VPE0426 (Domicilio)
        sql.append("  LEFT JOIN VPE0426_AGEND_DOMCL D 		 ");
        sql.append("	     ON ( D.CD_VOUCH = AG.Cd_vouch ) ");

        sql.append("      WHERE AG.CD_VOUCH = AG.CD_VOUCH");

        //* Recupera o ultimo registro da Vpe0424_vspre_obgta caso tenha
        sql.append("        AND ( Vp.ID_VSPRE_OBGTA IS NULL 									");
        sql.append("              OR Vp.ID_VSPRE_OBGTA = ( SELECT MAX(Vp2.ID_VSPRE_OBGTA) 		");
        sql.append("                                         FROM VPE0424_VSPRE_OBGTA Vp2 		");
        sql.append("                                        WHERE Vp2.CD_VOUCH = Vp.CD_VOUCH	");
        sql.append("                                      )										");
        sql.append("             )																");

        //* Se consultaHistorico = true, pega todos os status de um agendamento
        //* Se consultaHistorico = false, pega o ultimo status de um agendamento
        if (!consultaHistorico) {

            sql.append("    AND S.Id_statu_agmto = ( 									");
            sql.append("   							SELECT MAX ( S2.Id_statu_agmto ) 	");
            sql.append("   							  FROM Vpe0437_statu_agmto S2,   	");
            sql.append("   								   Vpe0425_agend_vspre A2    	");
            sql.append("   							 WHERE S2.Cd_vouch = A2.Cd_vouch 	");
            sql.append("   						       AND AG.CD_VOUCH = a2.CD_VOUCH 	");
            sql.append("						    ) 									");
        }

        if (filter.getNumVoucher() != null) {
            sql.append("    AND AG.CD_VOUCH = '").append(filter.getNumVoucher()).append("'");
        }

        if (filter.getNumPlaca() != null) {
            sql.append("    AND vp.CD_PLACA_VEICU = '").append(filter.getNumPlaca()).append("'");
        }

        if (filter.getCodCorretor() != null) {
            sql.append("    AND vp.CD_CRTOR_CIA = '").append(filter.getCodCorretor()).append("'");
        }

        if (filter.getIdPrestadora() != null) {
            sql.append("    AND p.CD_AGRMT_VSPRE = '").append(filter.getIdPrestadora()).append("'");
        }

        if (filter.getCodSitVistoria() != null && !"RJD".equals(filter.getCodSitVistoria())) {
            sql.append("    AND S.CD_SITUC_AGMTO = '").append(filter.getCodSitVistoria()).append("'");
        } else {
            if (agtosRejeitados) {
                if (agtosFrustrados) {
                    sql.append("   AND S.CD_SITUC_AGMTO IN ('NAP', 'CAN','NAG', 'VFR') ");
                } else {
                    sql.append("   AND S.CD_SITUC_AGMTO IN ('NAP', 'CAN','NAG') ");
                }
            } else {
                //* Situações fixas no código para evitar erros no getMethod do component
                sql.append("   AND S.CD_SITUC_AGMTO IN ('AGD','RCB','PEN','VFR','RLZ','RGD','NAP', 'CAN','NAG', 'FIM','FTR','PEF','LKX','FTT') ");
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if (filter.getDataPesquisaDe() != null) {
            sql.append("    AND AG.DT_ULTMA_ALTER >= ").append("TO_DATE('").append(sdf.format(filter.getDataPesquisaDe())).append("', 'dd-MM-yyyy HH24:MI:SS')");
        }

        if (filter.getDataPesquisaAte() != null) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(filter.getDataPesquisaAte());
            cal.set(Calendar.HOUR_OF_DAY,23);
            cal.set(Calendar.MINUTE,59);
            cal.set(Calendar.SECOND,59);

            sql.append("    AND AG.DT_ULTMA_ALTER <= ").append("TO_DATE('").append(sdf.format(cal.getTime())).append("', 'dd-MM-yyyy HH24:MI:SS')");
        }

        return sql;
    }

    public String recuperAgtoFilho(String codVoucher) {

        StringBuilder sql = new StringBuilder();

        try {
            sql.append("  SELECT a.cdVouch ");
            sql.append("    FROM AgendamentoVistoriaPrevia a ");
            sql.append("   WHERE a.cdVouchAnter =:codVoucher ");

            Query query = em.createQuery(sql.toString());
            query.setParameter("codVoucher", codVoucher);

            List<String> listaVoucher = query.getResultList();

            if (listaVoucher == null || listaVoucher.isEmpty()) {
                return null;
            }

            return listaVoucher.get(0);

        } catch (NoResultException e) {
            return null;
        } catch (RuntimeException e) {
            throw new DAOException("Erro ao executar query recuperAgtoFilho: " + ExceptionUtil.getRootMessage(e));
        }
    }

    public VistoriaPreviaCorretor obterPreAgtoCorretorVoucher(String codigoVoucher) {

        StringBuilder sql = new StringBuilder();

        try {
            sql.append(" SELECT new ");
            sql.append( VistoriaPreviaCorretor.class.getCanonicalName() );
            sql.append("         ( vpo, c )");
            sql.append("     FROM VistoriaPreviaObrigatoria vpo ");
            sql.append("          ,Conveniado c               ");
            sql.append("    WHERE vpo.cdCrtorCia = c.cdItrnoConvo ");
            sql.append("      AND c.tpConvo = 'C'               ");
            sql.append("      AND vpo.cdVouch = :codigoVoucher    " );
            sql.append(" ORDER BY vpo.idVspreObgta desc ");

            Query query = em.createQuery(sql.toString());
            query.setParameter("codigoVoucher", codigoVoucher);

            List<VistoriaPreviaCorretor> vpo = query.getResultList();

            if (vpo.size() > 0) {
                return (vpo.get(0));
            }

            return null;

        } catch (RuntimeException e) {
            throw new DAOException("Erro ao executar query obterPreAgtoCorretorVoucher: " + ExceptionUtil.getRootMessage(e));
        }
    }

}
