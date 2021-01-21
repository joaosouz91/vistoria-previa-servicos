package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.lang3.StringUtils;

import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.LoteImprodutivoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.LoteLaudoImprodutivoAux;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.RelatorioImprodutivoFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.LaudoImprodutivo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.VisaoRelatorioImprodutivoEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilJava;

public class LoteLaudoImprodutivoRepositoryImpl implements LoteLaudoImprodutivoRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	public List<LoteImprodutivoDTO> obterLotes(Long mesReferencia, Long anoReferencia) {
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT new " + LoteImprodutivoDTO.class.getCanonicalName() + "( L )");
		sql.append("   FROM LoteLaudoImprodutivo L ");
		sql.append("  WHERE L.mmRefer = :mesReferencia ");
		sql.append("    AND L.aaRefer = :anoReferencia  ");

		Query query = em.createQuery(sql.toString());

		query.setParameter("mesReferencia", mesReferencia);
		query.setParameter("anoReferencia", anoReferencia);

		List<LoteImprodutivoDTO> lotes = query.getResultList();

		sql = new StringBuffer();

		sql.append(" SELECT new " + LaudoImprodutivo.class.getCanonicalName() + "( D )");
		sql.append("   FROM LoteLaudoImprodutivoDetalhe D ");
		sql.append("  WHERE D.idLoteLaudoImpdv in ( ");
		sql.append(" 							   SELECT L.idLoteLaudoImpdv ");
		sql.append("   								 FROM LoteLaudoImprodutivo L ");
		sql.append("  								WHERE L.mmRefer = :mesReferencia ");
		sql.append("    							  AND L.aaRefer = :anoReferencia) ");

		query = em.createQuery(sql.toString());
		query.setParameter("mesReferencia", mesReferencia);
		query.setParameter("anoReferencia", anoReferencia);

		List<LaudoImprodutivo> laudosImprodutivos = query.getResultList();

		for (LoteImprodutivoDTO lote : lotes) {
			for (LaudoImprodutivo laudoImprodutivo : laudosImprodutivos) {
				if (lote.getLote().getIdLoteLaudoImpdv().equals(laudoImprodutivo.getDetalhe().getIdLoteLaudoImpdv())) {
					lote.getLaudosImprodutivos().add(laudoImprodutivo);
				}
			}
		}

		return lotes;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LaudoImprodutivo> obterAceitaveisNaoVinculadosProposta(Date dataInicial, Date dataFinal) {
		// TODO: remover os laudo de supervisao...
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT NEW " + LaudoImprodutivo.class.getCanonicalName() + "( L )");
		sql.append(" FROM LaudoVistoriaPrevia L, VeiculoVistoriaPrevia V, ItemSegurado I, Proposta N, Restricao r ");
		sql.append(" WHERE L.dtVspre > :dataInicial AND L.dtVspre < :dataFinal  ");
		sql.append(" AND L.icLaudoVicdo = 'N' ");
		sql.append(" AND L.cdSitucBlqueVspre = 1 ");
		sql.append(" AND L.cdSitucVspre in ('A', 'L', 'AF') ");
		sql.append(" AND V.cdLvpre = L.cdLvpre ");
		sql.append(" AND V.nrVrsaoLvpre = L.nrVrsaoLvpre ");
		sql.append(" AND L.cdVouch is not null ");
		sql.append(" AND L.cdVouch not like 'TB%' "); // não considera os voucher da TMB.
		sql.append(" AND I.codLaudoVistoriaPrevia = L.cdLvpre ");
		sql.append(" AND I.numVersaoLaudoVistoriaPrevia = L.nrVrsaoLvpre ");
		sql.append(" AND I.tipoHistorico = '0' ");
		sql.append(" AND N.cdPpota = I.codNegocio ");
		sql.append(" AND N.tpPpota = 'N' ");
		sql.append(" AND N.cdStatuPpota = 'REC' ");
		sql.append(" AND r.idPpota = N.idPpota ");
		sql.append(" AND r.tpRestr = 'CEN' ");
		sql.append(" AND r.cdUsuroUltmaAlter = 'CANINADI' "); // usuário de cancelamento por inadimplencia.

		sql.append(" AND L.cdLvpre not in (");

		sql.append(" SELECT Laudo.cdLvpre ");
		sql.append(" FROM LaudoVistoriaPrevia Laudo, VistoriaPreviaObrigatoria vo ");
		sql.append(" WHERE Laudo.dtVspre > :dataInicial AND Laudo.dtVspre < :dataFinal  ");
		sql.append(" AND Laudo.cdVouch like 'TT%' "); // recupera somente os laudos "TT" agendamento sem proposta.
		sql.append(" AND vo.cdVouch = Laudo.cdVouch ");
		sql.append(" AND LENGTH(vo.nrCpfCnpjClien) = 14 ) "); // não considera PJ TT.

		sql.append(" ORDER BY L.cdCrtorSegur");

		Query query = em.createQuery(sql.toString());
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);

		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LaudoImprodutivo> obterAceitaveisNaoVinculadosEndosso(Date dataInicial, Date dataFinal) {
		// TODO: remover os laudo de supervisao...
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT NEW " + LaudoImprodutivo.class.getCanonicalName() + "( L )");
		sql.append(
				" FROM LaudoVistoriaPrevia L, VeiculoVistoriaPrevia V, ItemSegurado I, ControleEndosso C, Restricao R, Proposta P ");
		sql.append(" WHERE L.dtVspre > :dataInicial AND L.dtVspre < :dataFinal  ");
		sql.append(" AND L.icLaudoVicdo = 'N' ");
		sql.append(" AND L.cdSitucBlqueVspre = 1 ");
		sql.append(" AND L.cdSitucVspre in ('A', 'L', 'AF') ");
		sql.append(" AND V.cdLvpre = L.cdLvpre ");
		sql.append(" AND V.nrVrsaoLvpre = L.nrVrsaoLvpre ");
		sql.append(" AND L.cdVouch is not null ");
		sql.append(" AND L.cdVouch not like 'TB%' ");
		sql.append(" AND I.codLaudoVistoriaPrevia = L.cdLvpre ");
		sql.append(" AND I.numVersaoLaudoVistoriaPrevia = L.nrVrsaoLvpre ");
		sql.append(" AND C.codEndosso = I.codEndosso ");
		sql.append(" AND C.cdSitucEndos = 'REC' ");
		sql.append(" AND P.cdPpota = C.codEndosso ");
		sql.append(" AND R.idPpota = P.idPpota ");
		sql.append(" AND R.tpRestr = 'CEN' ");
		sql.append(" AND R.nrItseg = I.numItemSegurado ");
		sql.append(" AND R.cdUsuroUltmaAlter = 'CANINADI' "); // usuário de cancelamento por inadimplencia.

		sql.append(" AND L.cdLvpre not in (");
		sql.append(" SELECT Laudo.cdLvpre ");
		sql.append(" FROM LaudoVistoriaPrevia Laudo, VistoriaPreviaObrigatoria vo ");
		sql.append(" WHERE Laudo.dtVspre > :dataInicial AND Laudo.dtVspre < :dataFinal  ");
		sql.append(" AND Laudo.cdVouch like 'TT%' ");
		sql.append(" AND vo.cdVouch = Laudo.cdVouch ");
		sql.append(" AND LENGTH(vo.nrCpfCnpjClien) = 14 ) "); // não considera PJ TT.

		sql.append(" ORDER BY L.cdCrtorSegur");

		Query query = em.createQuery(sql.toString());
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);

		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LaudoImprodutivo> obterAceitaveisNaoVinculadosEndossoInadimplencia(Date dataInicial, Date dataFinal) {
		// TODO: remover os laudo de supervisao...
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT NEW " + LaudoImprodutivo.class.getCanonicalName() + "( L )");
		sql.append(
				" FROM LaudoVistoriaPrevia L, VeiculoVistoriaPrevia V, ItemSegurado I, ControleEndosso C, ParcelaItemSegurado P ");
		sql.append(" WHERE L.dtVspre > :dataInicial AND L.dtVspre < :dataFinal  ");
		sql.append(" AND L.icLaudoVicdo = 'S' ");
		sql.append(" AND L.cdSitucBlqueVspre = 0 ");
		sql.append(" AND L.cdSitucVspre in ('A', 'L', 'AF') ");
		sql.append(" AND V.cdLvpre = L.cdLvpre ");
		sql.append(" AND V.nrVrsaoLvpre = L.nrVrsaoLvpre ");
		sql.append(" AND L.cdVouch is not null ");
		sql.append(" AND L.cdVouch not like 'TB%' ");
		sql.append(" AND I.codLaudoVistoriaPrevia = L.cdLvpre ");
		sql.append(" AND I.numVersaoLaudoVistoriaPrevia = L.nrVrsaoLvpre ");
		sql.append(" AND C.codEndosso = I.codEndosso ");
		sql.append(" AND C.codUsuarioInclusao = 'CANCCOB' ");
		sql.append(" AND C.cdSitucEndos = 'END' ");
		sql.append(" AND P.numItemSegurado = I.numItemSegurado ");
		sql.append(" AND P.tipoHistorico = I.tipoHistorico ");
		sql.append(" AND P.codEndossoCancelamento = I.codEndosso  ");
		sql.append(" AND P.numParcela = 1 ");
		sql.append(" AND P.dataCancelamentoParcela is not null ");
		sql.append(" AND P.dataPagamentoParcela is null  ");

		sql.append(" AND L.cdLvpre not in ( ");

		sql.append(" SELECT Laudo.cdLvpre ");
		sql.append(" FROM LaudoVistoriaPrevia Laudo, VistoriaPreviaObrigatoria vo ");
		sql.append(" WHERE Laudo.dtVspre > :dataInicial AND Laudo.dtVspre < :dataFinal  ");
		sql.append(" AND Laudo.cdVouch like 'TT%' ");
		sql.append(" AND vo.cdVouch = Laudo.cdVouch ");
		sql.append(" AND LENGTH(vo.nrCpfCnpjClien) = 14 ) "); // não considera PJ TT.

		sql.append(" ORDER BY L.cdCrtorSegur");

		Query query = em.createQuery(sql.toString());
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);

		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LaudoImprodutivo> obterLaudoRepetido(Date dataInicial, Date dataFinal) {
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT DISTINCT L.Cd_crtor_segur as CD_CRTOR_SEGUR, ");
		sql.append("                 L.Cd_sucsl_comrl as CD_SUCSL_COMRL, ");
		sql.append("                 L.Cd_supin as CD_SUPIN, ");
		sql.append("                 L.Cd_lvpre as CD_LVPRE, ");
		sql.append("                 L.Nr_vrsao_lvpre as NR_VRSAO_LVPRE, ");
		sql.append("                 L.Cd_situc_vspre as CD_SITUC_VSPRE ");
		sql.append("            FROM Vpe0077_lvpre L, ");
		sql.append("                 Vpe0119_veicu_vspre V, ");
		sql.append("                 Vpe0095_patec_lvpre P, ");
		sql.append("                 VPE0096_PATEC_VSPRE PV, ");
		sql.append("                 VPE0424_VSPRE_OBGTA VPO, ");
		sql.append("                 Vpe0077_lvpre Lant, ");
		sql.append("                 Vpe0119_veicu_vspre Vant, ");
		sql.append("                 Vpe0095_patec_lvpre Pant, ");
		sql.append("                 VPE0096_PATEC_VSPRE PVant, ");
		sql.append("                 VPE0424_VSPRE_OBGTA VPOant, ");
		sql.append("                 VPE0435_RCPAO_LAUDO Rc ");
		// MES DE REFERENCIA RECUSAVEL/SUJEITO ANALISE ATUAL
		sql.append("           WHERE L.Dt_vspre > :dataInicial AND L.Dt_vspre < :dataFinal ");
		// PROCURAR LAUDO ANTERIOR EM ATE X DIAS ATRAS
		sql.append("             AND Lant.Dt_vspre >= L.Dt_vspre - :qtdDias ");
		sql.append("             AND Lant.Dt_vspre < L.Dt_vspre ");
		// STATUS DO ATUAL (RECUSAVEL OU SUJEITO A ANÁLISE)
		sql.append("             AND L.Cd_situc_vspre in ('R', 'S') ");
		sql.append("             AND PV.CD_CLASF_PATEC  = L.Cd_situc_vspre ");
		// STATUS DO ANTERIOR (RECUSAVEL OU SUJEITO A ANÁLISE - MESMO DO ATUAL)
		sql.append("             AND Lant.Cd_situc_vspre = L.Cd_situc_vspre ");
		sql.append("             AND PVant.CD_CLASF_PATEC  = L.Cd_situc_vspre ");
		// JOINS TABELAS LAUDO ATUAL
		sql.append("             AND V.Cd_lvpre = L.Cd_lvpre ");
		sql.append("             AND V.Nr_vrsao_lvpre = L.Nr_vrsao_lvpre ");
		sql.append("             AND P.Cd_lvpre = L.Cd_lvpre ");
		sql.append("             AND P.Nr_vrsao_lvpre = L.Nr_vrsao_lvpre ");
		sql.append("             AND PV.CD_PATEC_VSPRE = P.CD_PATEC_VSPRE ");
		sql.append("             AND L.Cd_vouch IS NOT NULL ");
		sql.append("             AND L.Cd_vouch NOT LIKE 'TB%' ");
		sql.append("             AND VPO.Cd_vouch = L.Cd_vouch ");
		// JOINS TABELAS LAUDO ANTERIOR
		sql.append("             AND Vant.Cd_lvpre = Lant.Cd_lvpre ");
		sql.append("             AND Vant.Nr_vrsao_lvpre = Lant.Nr_vrsao_lvpre ");
		sql.append("             AND Pant.Cd_lvpre = Lant.Cd_lvpre ");
		sql.append("             AND Pant.Nr_vrsao_lvpre = Lant.Nr_vrsao_lvpre ");
		sql.append("             AND PVant.CD_PATEC_VSPRE = Pant.CD_PATEC_VSPRE ");
		sql.append("             AND Lant.Cd_vouch IS NOT NULL ");
		sql.append("             AND Lant.Cd_vouch NOT LIKE 'TB%' ");
		sql.append("             AND VPOant.Cd_vouch = Lant.Cd_vouch ");
		// VERIFICANDO SE MESMO VEICULO/CORRETOR ENTRE LAUDO ATUAL E ANTERIOR
		sql.append("             AND V.Cd_placa_veicu = Vant.Cd_placa_veicu ");
		sql.append("             AND V.Cd_chassi_veicu = Vant.Cd_chassi_veicu ");
		sql.append("             AND L.Cd_crtor_segur = Lant.Cd_crtor_segur ");
		// VERIFICANDO SE REPETIU O CODIGO DE PARECER TECNICO
		sql.append("             AND P.Cd_patec_vspre = Pant.Cd_patec_vspre ");
		// GARANTINDO QUE SAO LAUDOS DIFERENTES
		sql.append("             AND L.Cd_lvpre <> Lant.Cd_lvpre ");
		// COMPARAR PROPOSTA COM PROPOSTA E ENDOSSO COM ENDOSSO.
		sql.append(
				"             AND ((VPO.CD_SISTM_ORIGM IN (5, 8, 9, 10, 14, 16, 17, 20, 21, 24) AND VPOant.CD_SISTM_ORIGM IN (5, 8, 9, 10, 14, 16, 17, 20, 21, 24)) ");
		sql.append(
				"             	   OR (VPO.CD_SISTM_ORIGM IN (6, 7, 11, 12) AND VPOant.CD_SISTM_ORIGM IN (6, 7, 11, 12))) ");
		sql.append("			 AND Rc.Cd_Lvpre = L.Cd_Lvpre");
		sql.append("			 AND Rc.Ic_Empre <> 'B' "); // elimina os laudos "TT" que pertencem ao plataforma

		// retira os laudos originados de agendamento sem proposta dos clientes PJ.
		sql.append("AND L.CD_LVPRE NOT IN( ");
		sql.append("SELECT DISTINCT L.Cd_Lvpre ");
//			sql.append("FROM VPE0077_LVPRE L, VPE0435_RCPAO_LAUDO R, VPE0424_VSPRE_OBGTA VO ");
		sql.append("FROM VPE0077_LVPRE L, VPE0424_VSPRE_OBGTA VO ");
		sql.append("WHERE L.dt_Vspre > :dataInicial AND L.dt_Vspre < :dataFinal ");
//			sql.append("AND L.ic_Laudo_Vicdo = 'N' ");
//			sql.append("AND L.cd_Situc_Blque_Vspre = 1 ");
//			sql.append("AND L.cd_Vouch is not null ");
		sql.append("AND L.cd_Vouch like 'TT%' ");
//			sql.append("AND R.cd_Lvpre = L.cd_Lvpre ");
		// sql.append("AND R.ic_Empre <> 'P' ");
		sql.append("AND VO.CD_VOUCH = L.CD_VOUCH ");
		sql.append("AND LENGTH(VO.NR_CPF_CNPJ_CLIEN) = 14 ) ");

		Query query = em.createNativeQuery(sql.toString(), LaudoImprodutivo.class);

		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);
		query.setParameter("qtdDias", 90L);

		List<LaudoImprodutivo> laudos = query.getResultList();

		for (LaudoImprodutivo laudo : laudos) {
			laudo.getDetalhe().setCdLvpre(laudo.getCdLvpre());
			laudo.getDetalhe().setNrVrsaoLvpre(laudo.getNrVrsaoLvpre());
			laudo.getDetalhe().setCdMotvImpdv(laudo.getCdSitucVspre());
			laudo.getDetalhe().setIcExclu("N");
			laudo.getDetalhe().setCdTipoLaudo("I");
			laudo.getDetalhe().setCdUsuroUltmaAtulz("Batch");
			laudo.getDetalhe().setDtUltmaAtulz(new Date());
		}

		return laudos;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LaudoImprodutivo> obter2LaudoDesnecessario(Date dataInicial, Date dataFinal) {
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT DISTINCT L.Cd_crtor_segur as CD_CRTOR_SEGUR, ");
		sql.append("                 L.Cd_sucsl_comrl as CD_SUCSL_COMRL, ");
		sql.append("                 L.Cd_supin as CD_SUPIN, ");
		sql.append("                 L.Cd_lvpre as CD_LVPRE, ");
		sql.append("                 L.Nr_vrsao_lvpre as NR_VRSAO_LVPRE, ");
		sql.append("                 L.Cd_situc_vspre as CD_SITUC_VSPRE ");
		sql.append("            FROM Vpe0077_lvpre L, ");
		sql.append("                 Vpe0119_veicu_vspre V, ");
		sql.append("                 VPE0424_VSPRE_OBGTA VPO, ");
		sql.append("                 Vpe0077_lvpre Lant, ");
		sql.append("                 Vpe0119_veicu_vspre Vant, ");
		sql.append("                 VPE0424_VSPRE_OBGTA VPOant, ");
		sql.append("                 VPE0435_RCPAO_LAUDO Rc ");
		// MES DE REFERENCIA SUJEITO ANALISE ATUAL
		sql.append("           WHERE L.Dt_vspre > :dataInicial AND L.Dt_vspre < :dataFinal ");
		// PROCURAR LAUDO ANTERIOR EM ATE X DIAS ATRAS
		sql.append("             AND Lant.Dt_vspre >= L.Dt_vspre - :qtdDias ");
		sql.append("             AND Lant.Dt_vspre < L.Dt_vspre ");
		// STATUS DO ATUAL (SUJEITO A ANÁLISE)
		sql.append("             AND ( L.Cd_situc_vspre = 'S' ) ");
		// STATUS DO ANTERIOR (ACEITAVEL, LIBERADO OU ACEITACAO FORCADA)
		sql.append("             AND Lant.Cd_situc_vspre in ('A', 'L', 'AF') ");
		// JOINS TABELAS LAUDO ATUAL ");
		sql.append("             AND V.Cd_lvpre = L.Cd_lvpre ");
		sql.append("             AND V.Nr_vrsao_lvpre = L.Nr_vrsao_lvpre ");
		sql.append("             AND L.Cd_vouch IS NOT NULL ");
		sql.append("             AND L.Cd_vouch NOT LIKE 'TB%' ");
		sql.append("             AND VPO.Cd_vouch = L.Cd_vouch ");
		// JOINS TABELAS LAUDO ANTERIOR
		sql.append("             AND Vant.Cd_lvpre = Lant.Cd_lvpre ");
		sql.append("             AND Vant.Nr_vrsao_lvpre = Lant.Nr_vrsao_lvpre ");
		sql.append("             AND Lant.Cd_vouch IS NOT NULL ");
		sql.append("             AND Lant.Cd_vouch NOT LIKE 'TB%' ");
		sql.append("             AND VPOant.Cd_vouch = Lant.Cd_vouch ");
		// VERIFICANDO SE MESMO VEICULO/CORRETOR ENTRE LAUDO ATUAL E ANTERIOR E LAUDO
		// DIFERENTE
		sql.append("             AND V.Cd_placa_veicu = Vant.Cd_placa_veicu ");
		sql.append("             AND V.Cd_chassi_veicu = Vant.Cd_chassi_veicu ");
		sql.append("             AND L.Cd_crtor_segur = Lant.Cd_crtor_segur ");
		// VERIFICANDO SE O PRIMEIRO LAUDO ESTA DESBLOQUEADO E VINCULADO
		sql.append("             AND Lant.IC_LAUDO_VICDO = 'S' ");
		sql.append("             AND Lant.CD_SITUC_BLQUE_VSPRE = '0' ");
		// GARANTINDO QUE SAO LAUDOS DIFERENTES
		sql.append("             AND L.Cd_lvpre <> Lant.Cd_lvpre ");
		// COMPARAR PROPOSTA COM PROPOSTA E ENDOSSO COM ENDOSSO.
		sql.append(
				"             AND ((VPO.CD_SISTM_ORIGM IN (5, 8, 9, 10, 14, 16, 17, 20, 21, 24) AND VPOant.CD_SISTM_ORIGM IN (5, 8, 9, 10, 14, 16, 17, 20, 21, 24)) ");
		sql.append(
				"             	   OR (VPO.CD_SISTM_ORIGM IN (6, 7, 11, 12) AND VPOant.CD_SISTM_ORIGM IN (6, 7, 11, 12))) ");
		sql.append("			 AND Rc.Cd_Lvpre = L.Cd_Lvpre");
		sql.append("			 AND Rc.Ic_Empre <> 'B' "); // elimina os laudos "TT" que pertencem ao plataforma

		// retira os laudos originados de agendamento sem proposta dos clientes PJ.
		sql.append("AND L.CD_LVPRE NOT IN( ");
		sql.append("SELECT DISTINCT L.Cd_Lvpre ");
//			sql.append("FROM VPE0077_LVPRE L, VPE0435_RCPAO_LAUDO R, VPE0424_VSPRE_OBGTA VO ");
		sql.append("FROM VPE0077_LVPRE L, VPE0424_VSPRE_OBGTA VO ");
		sql.append("WHERE L.dt_Vspre > :dataInicial AND L.dt_Vspre < :dataFinal ");
//			sql.append("AND L.ic_Laudo_Vicdo = 'N' ");
//			sql.append("AND L.cd_Situc_Blque_Vspre = 1 ");
//			sql.append("AND L.cd_Vouch is not null ");
		sql.append("AND L.cd_Vouch like 'TT%' ");
//			sql.append("AND R.cd_Lvpre = L.cd_Lvpre ");
		// sql.append("AND R.ic_Empre <> 'P' ");
		sql.append("AND VO.CD_VOUCH = L.CD_VOUCH ");
		sql.append("AND LENGTH(VO.NR_CPF_CNPJ_CLIEN) = 14 ) ");

		Query query = em.createNativeQuery(sql.toString(), LaudoImprodutivo.class);

		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);
		query.setParameter("qtdDias", 90L);

		List<LaudoImprodutivo> laudos = query.getResultList();

		for (LaudoImprodutivo laudo : laudos) {
			laudo.getDetalhe().setCdLvpre(laudo.getCdLvpre());
			laudo.getDetalhe().setNrVrsaoLvpre(laudo.getNrVrsaoLvpre());
			laudo.getDetalhe().setCdMotvImpdv("E");
			laudo.getDetalhe().setIcExclu("N");
			laudo.getDetalhe().setCdTipoLaudo("I");
			laudo.getDetalhe().setCdUsuroUltmaAtulz("Batch");
			laudo.getDetalhe().setDtUltmaAtulz(new Date());
		}

		return laudos;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LaudoImprodutivo> obterLaudosNuncaVinculados(Date dataInicial, Date dataFinal,
			Date dataFinalFechamento) {
		// TODO: remover os laudo de supervisao...
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT DISTINCT L.Cd_crtor_segur as CD_CRTOR_SEGUR, ");
		sql.append("        L.Cd_sucsl_comrl as CD_SUCSL_COMRL, ");
		sql.append("        L.Cd_supin as CD_SUPIN, ");
		sql.append("        L.Cd_lvpre as CD_LVPRE, ");
		sql.append("        L.Nr_vrsao_lvpre as NR_VRSAO_LVPRE, ");
		sql.append("        L.Cd_situc_vspre as CD_SITUC_VSPRE ");
		sql.append("FROM VPE0077_LVPRE L, VPE0435_RCPAO_LAUDO R  ");
		sql.append("WHERE L.dt_Vspre > :dataInicial AND L.dt_Vspre < :dataFinal  ");
		sql.append("AND L.ic_Laudo_Vicdo = 'N' ");
		sql.append("AND L.cd_Situc_Blque_Vspre = 1 ");
		sql.append("AND L.cd_Vouch is not null ");
		sql.append("AND L.Cd_situc_vspre <> 'R' ");
		sql.append("AND L.cd_Vouch not like 'TB%' ");
		sql.append("AND R.cd_Lvpre = L.cd_Lvpre ");
		sql.append("AND R.Ic_Empre <> 'B' ");
		sql.append("AND not exists (SELECT 1 FROM SSV0076_ITSEG i ");
		sql.append("WHERE I.cd_Lvpre = L.cd_Lvpre ");
		sql.append("AND i.nr_vrsao_lvpre = L.nr_vrsao_lvpre) ");

		// retira laudos de propostas emitidas.
		sql.append("AND L.cd_Lvpre NOT IN(SELECT LAUDOS.cd_Lvpre ");
		sql.append("FROM SSV0076_ITSEG ITEM, ");
		sql.append("(SELECT veic.cd_Chassi_Veicu, veic.cd_Placa_Veicu, veic.cd_Lvpre, L.dt_Vspre ");
		sql.append("FROM VPE0077_LVPRE L, VPE0435_RCPAO_LAUDO R, VPE0119_VEICU_VSPRE veic ");
		sql.append("WHERE L.DT_VSPRE > :dataInicial AND L.dt_Vspre < :dataFinal ");
		sql.append("AND L.ic_Laudo_Vicdo = 'N' ");
		sql.append("AND L.cd_Situc_Blque_Vspre = 1 ");
		sql.append("AND L.cd_Vouch is not null ");
		sql.append("AND L.cd_Vouch not like 'TB%' ");
		sql.append("AND R.cd_Lvpre = L.cd_Lvpre ");
		sql.append("AND R.Ic_Empre <> 'B' ");
		sql.append("AND R.ic_Laudo_Aceto = 'S' ");
		sql.append("AND veic.cd_Lvpre = l.cd_Lvpre  ");
		sql.append("AND veic.nr_Vrsao_Lvpre = l.nr_Vrsao_Lvpre) LAUDOS, ");
		// sql.append("SSV0250_RESTR_ITSEG RESTRI, SSV0254_RESTR_ITSEG_VSPRE DISP ");
		sql.append("ACT0250_RESTR RESTRI, ACT0260_PPOTA PRO ");
		sql.append("WHERE ITEM.cd_Situc_Ngoco = 'APO' ");
		sql.append("AND   (ITEM.cd_Chassi_Veicu = LAUDOS.cd_Chassi_Veicu ");
		sql.append("or (ITEM.cd_Placa_Veicu = LAUDOS.cd_Placa_Veicu and LAUDOS.cd_Placa_Veicu <> 'AVI0000' ");
		sql.append("AND LAUDOS.cd_Placa_Veicu <> 'AAVISAR') ) ");
		sql.append("AND PRO.CD_PPOTA = item.cd_ngoco ");
		sql.append("and PRO.tp_ppota = 'N' ");
		sql.append("AND RESTRI.ID_PPOTA = PRO.ID_PPOTA ");
		sql.append("AND RESTRI.nr_Itseg = ITEM.nr_Itseg ");
//			sql.append("AND RESTRI.tp_Histo_Itseg = ITEM.tp_Histo_Itseg ");
		sql.append("AND RESTRI.tp_Restr = 'VIS' ");
		sql.append("AND RESTRI.CD_SITUC_RESTR = 'LIB' ");
		sql.append("AND RESTRI.dt_Fechm_Restr > :dataInicial AND RESTRI.dt_Fechm_Restr <= :dataFinalFechamento ) ");
//			sql.append("AND DISP.nr_Itseg = RESTRI.nr_Itseg ");
//			sql.append("AND DISP.tp_Histo_Itseg = RESTRI.tp_Histo_Itseg   ) ");

		// retira laudos de endossos emitidos.
		sql.append("  AND L.CD_LVPRE NOT IN(  ");
		sql.append("SELECT LAUDOS.cd_Lvpre ");
		sql.append("FROM SSV0076_ITSEG ITEM, ");
		sql.append("(SELECT veic.cd_Chassi_Veicu, veic.cd_Placa_Veicu, veic.cd_Lvpre, L.dt_Vspre ");
		sql.append(" FROM VPE0077_LVPRE L, VPE0435_RCPAO_LAUDO R, VPE0119_VEICU_VSPRE veic ");
		sql.append(" WHERE L.DT_VSPRE > :dataInicial AND L.dt_Vspre < :dataFinal ");
		sql.append(" AND L.ic_Laudo_Vicdo = 'N' ");
		sql.append(" AND L.cd_Situc_Blque_Vspre = 1 ");
		sql.append(" AND L.cd_Vouch is not null  ");
		sql.append(" AND L.cd_Vouch not like 'TB%' ");
		sql.append(" AND R.cd_Lvpre = L.cd_Lvpre ");
		sql.append(" AND R.Ic_Empre <> 'B' ");
		sql.append(" AND R.ic_Laudo_Aceto = 'S' ");
		sql.append(" AND veic.cd_Lvpre = l.cd_Lvpre  ");
		sql.append(
				" AND veic.nr_Vrsao_Lvpre = l.nr_Vrsao_Lvpre) LAUDOS, ACT0250_RESTR RESTRI, SSV0025_CONTR_ENDOS ENDOS, ACT0260_PPOTA PROPOS ");
		sql.append(" WHERE ITEM.cd_Situc_Ngoco = 'APO' ");
		sql.append(" AND   (ITEM.cd_Chassi_Veicu = LAUDOS.cd_Chassi_Veicu ");
		sql.append(
				" or (ITEM.cd_Placa_Veicu = LAUDOS.cd_Placa_Veicu and LAUDOS.cd_Placa_Veicu <> 'AVI0000' and LAUDOS.cd_Placa_Veicu <> 'AAVISAR') ) ");
		sql.append("AND ITEM.TP_HISTO_ITSEG = '0' ");
		sql.append("AND ENDOS.CD_ENDOS = ITEM.CD_ENDOS ");
		sql.append("AND ENDOS.CD_SITUC_ENDOS = 'END' ");
		sql.append("AND PROPOS.TP_PPOTA = 'E' ");
		sql.append("AND PROPOS.CD_PPOTA = ITEM.CD_ENDOS ");
		sql.append("AND RESTRI.ID_PPOTA = PROPOS.ID_PPOTA ");
		sql.append("AND RESTRI.tp_Restr = 'VIS'  ");
		sql.append("AND RESTRI.cd_Situc_Restr = 'LIB' ");
		sql.append("AND RESTRI.dt_Fechm_Restr > :dataInicial AND RESTRI.dt_Fechm_Restr <= :dataFinalFechamento ");
		sql.append("AND RESTRI.TP_LIBEC IS NOT NULL  )"); // IDENTIFICA QUE HOUVE DISPENSA DE VP

		// retira os laudos nunca vinculados utilizado para reclassficação.
		sql.append("  AND L.CD_LVPRE NOT IN(  ");
		sql.append("           SELECT DISTINCT   L.Cd_lvpre as CD_LVPRE ");
		sql.append("           FROM Vpe0077_lvpre L,  ");
		sql.append("                Vpe0119_veicu_vspre V, ");
		sql.append("                VPE0424_VSPRE_OBGTA VPO, ");
		sql.append("                Vpe0077_lvpre Lant, ");
		sql.append("                Vpe0119_veicu_vspre Vant, ");
		sql.append("                VPE0424_VSPRE_OBGTA VPOant, ");
		sql.append("                VPE0435_RCPAO_LAUDO Rc       ");
		sql.append("            WHERE L.Dt_vspre > :dataInicial AND L.Dt_vspre < :dataFinal ");
		sql.append("               AND Lant.Dt_vspre >= L.Dt_vspre - :qtdDias ");
		sql.append("               AND Lant.Dt_vspre < L.Dt_vspre       ");
		sql.append("               AND ( L.Cd_situc_vspre = 'A' )       ");
		sql.append("               AND L.CD_SITUC_BLQUE_VSPRE = 1");
		sql.append("               AND L.IC_LAUDO_VICDO = 'N'");
		sql.append("               AND Lant.Cd_situc_vspre in ('L', 'AF')");
		sql.append("               AND V.Cd_lvpre = L.Cd_lvpre ");
		sql.append("               AND V.Nr_vrsao_lvpre = L.Nr_vrsao_lvpre");
		sql.append("               AND L.Cd_vouch IS NOT NULL ");
		sql.append("               AND L.Cd_vouch NOT LIKE 'TB%' ");
		sql.append("               AND VPO.Cd_vouch = L.Cd_vouch    ");
		sql.append("               AND Vant.Cd_lvpre = Lant.Cd_lvpre ");
		sql.append("               AND Vant.Nr_vrsao_lvpre = Lant.Nr_vrsao_lvpre");
		sql.append("               AND Lant.Cd_vouch IS NOT NULL ");
		sql.append("               AND Lant.Cd_vouch NOT LIKE 'TB%' ");
		sql.append("               AND VPOant.Cd_vouch = Lant.Cd_vouch ");
		sql.append("               AND V.Cd_placa_veicu = Vant.Cd_placa_veicu ");
		sql.append("               AND V.Cd_chassi_veicu = Vant.Cd_chassi_veicu ");
		sql.append("               AND L.Cd_crtor_segur = Lant.Cd_crtor_segur     ");
		sql.append("               AND Lant.IC_LAUDO_VICDO = 'S' ");
		sql.append("               AND Lant.CD_SITUC_BLQUE_VSPRE = '0'");
		sql.append("               AND L.Cd_lvpre <> Lant.Cd_lvpre       ");
		sql.append(
				"               AND ((VPO.CD_SISTM_ORIGM IN (5, 8, 9, 10, 14, 16, 17, 20, 21, 24) AND VPOant.CD_SISTM_ORIGM IN (5, 8, 9, 10, 14, 16, 17, 20, 21, 24)) ");
		sql.append(
				"             	   OR (VPO.CD_SISTM_ORIGM IN (6, 7, 11, 12) AND VPOant.CD_SISTM_ORIGM IN (6, 7, 11, 12))) ");
		sql.append("               AND Rc.Cd_Lvpre = L.Cd_Lvpre");
		sql.append("               AND Rc.Ic_Empre <> 'B' ) ");

		// retira os laudos originados de agendamento sem proposta dos clientes PJ.
		sql.append("AND L.CD_LVPRE NOT IN( ");
		sql.append("SELECT DISTINCT L.Cd_Lvpre ");
		sql.append("FROM VPE0077_LVPRE L, VPE0424_VSPRE_OBGTA VO ");
		sql.append("WHERE L.dt_Vspre > :dataInicial AND L.dt_Vspre < :dataFinal ");
		sql.append("AND L.cd_Vouch like 'TT%' ");
		sql.append("AND VO.CD_VOUCH = L.CD_VOUCH ");
		sql.append("AND LENGTH(VO.NR_CPF_CNPJ_CLIEN) = 14 ) ");

		sql.append("ORDER BY L.cd_Crtor_Segur ");

		Query query = em.createNativeQuery(sql.toString(), LaudoImprodutivo.class);

		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);
		query.setParameter("qtdDias", 90L);
		query.setParameter("dataFinalFechamento", dataFinalFechamento);
		List<LaudoImprodutivo> laudos = query.getResultList();

		for (LaudoImprodutivo laudo : laudos) {
			laudo.getDetalhe().setCdLvpre(laudo.getCdLvpre());
			laudo.getDetalhe().setNrVrsaoLvpre(laudo.getNrVrsaoLvpre());
			laudo.getDetalhe().setCdMotvImpdv("N");
			laudo.getDetalhe().setIcExclu("N");
			laudo.getDetalhe().setCdTipoLaudo("I");
			laudo.getDetalhe().setCdUsuroUltmaAtulz("Batch");
			laudo.getDetalhe().setDtUltmaAtulz(new Date());
		}

		return laudos;
	}

	@Override
	public void atualizaValoresEQuantidadesLotes(Long idLote, Long mesReferencia, Long anoReferencia) {
		StringBuffer sql = new StringBuffer();

		sql.append(" UPDATE VPE0439_LOTE_LAUDO_IMPDV l ");
		sql.append(" SET l.QT_LAUDO_SELEC =     NVL( (SELECT count ( d1.Vl_laudo ) ");
		sql.append("                                 FROM Vpe0438_lote_laudo_impdv_detal d1 ");
		sql.append("                                 WHERE d1.ID_LOTE_LAUDO_IMPDV = l.ID_LOTE_LAUDO_IMPDV ");
		sql.append("                                   AND d1.CD_TIPO_LAUDO NOT IN ('C','E')),0), ");
		sql.append("     l.QT_LAUDO_CALDO =     NVL( (SELECT count ( d2.Vl_laudo ) ");
		sql.append("                                 FROM Vpe0438_lote_laudo_impdv_detal d2 ");
		sql.append("                                 WHERE d2.ID_LOTE_LAUDO_IMPDV = l.ID_LOTE_LAUDO_IMPDV  ");
		sql.append("                                   AND d2.IC_EXCLU <> 'S' ");
		sql.append("                                   AND d2.CD_TIPO_LAUDO NOT IN ('C','E') ),0) ,  ");
		sql.append("     l.VL_TOTAL_ORIGN_LOTE = NVL( (SELECT sum ( d3.Vl_laudo ) ");
		sql.append("                                 FROM Vpe0438_lote_laudo_impdv_detal d3 ");
		sql.append("                                 WHERE d3.ID_LOTE_LAUDO_IMPDV = l.ID_LOTE_LAUDO_IMPDV ");
		sql.append("                                   AND d3.CD_TIPO_LAUDO NOT IN ('C','E')),0), ");
		sql.append("     l.VL_TOTAL_LOTE_CALDO = NVL ((SELECT sum ( d4.Vl_laudo ) ");
		sql.append("                                  FROM Vpe0438_lote_laudo_impdv_detal d4 ");
		sql.append("                                 WHERE d4.ID_LOTE_LAUDO_IMPDV = l.ID_LOTE_LAUDO_IMPDV ");
		sql.append("                                   AND d4.IC_EXCLU <> 'S' ");
		sql.append("                                   AND d4.CD_TIPO_LAUDO NOT IN ('C','E') ),0) ,  ");
		sql.append("     l.VL_TOTAL_LAUDO_INCLO = NVL ((SELECT sum ( d5.Vl_laudo ) ");
		sql.append("                                      FROM Vpe0438_lote_laudo_impdv_detal d5 ");
		sql.append("                                     WHERE d5.ID_LOTE_LAUDO_IMPDV = l.ID_LOTE_LAUDO_IMPDV ");
		sql.append("                                       AND d5.CD_TIPO_LAUDO = 'C'),0),   ");
		sql.append("     l.VL_TOTAL_LAUDO_ESTOR = NVL ((SELECT sum ( d6.Vl_laudo ) ");
		sql.append("                                      FROM Vpe0438_lote_laudo_impdv_detal d6 ");
		sql.append("                                     WHERE d6.ID_LOTE_LAUDO_IMPDV = l.ID_LOTE_LAUDO_IMPDV ");
		sql.append("                                       AND d6.CD_TIPO_LAUDO = 'E'),0),   ");
		sql.append("     l.QT_TOTAL_LAUDO_INCLO = NVL( (SELECT count ( d7.Vl_laudo ) ");
		sql.append("                                      FROM Vpe0438_lote_laudo_impdv_detal d7 ");
		sql.append("                                     WHERE d7.ID_LOTE_LAUDO_IMPDV = l.ID_LOTE_LAUDO_IMPDV  ");
		sql.append("                                       AND d7.CD_TIPO_LAUDO = 'C'),0), ");
		sql.append("     l.QT_TOTAL_LAUDO_ESTOR = NVL( (SELECT count ( d8.Vl_laudo ) ");
		sql.append("                                      FROM Vpe0438_lote_laudo_impdv_detal d8 ");
		sql.append("                                     WHERE d8.ID_LOTE_LAUDO_IMPDV = l.ID_LOTE_LAUDO_IMPDV  ");
		sql.append("                                       AND d8.CD_TIPO_LAUDO = 'E'),0), ");
		sql.append("     l.IC_FRANQ = 'S' ");
		sql.append(" WHERE l.MM_REFER = :mesReferencia ");
		sql.append("   AND l.AA_REFER = :anoReferencia ");
		if (idLote != null) {
			sql.append(" AND l.ID_LOTE_LAUDO_IMPDV = :idLote ");
		}

		Query query = em.createNativeQuery(sql.toString());

		query.setParameter("mesReferencia", mesReferencia);
		query.setParameter("anoReferencia", anoReferencia);

		if (idLote != null) {
			query.setParameter("idLote", idLote);
		}

		query.executeUpdate();
	}

	@Override
	public void atualizaFlagDeFranquia(Long idLote, Long mesReferencia, Long anoReferencia, Date dataInicio,
			Date dataFim) {

		StringBuffer sql = new StringBuffer();
		sql = new StringBuffer();
		Query query = null;

		sql = new StringBuffer();

		sql.append(" UPDATE Vpe0439_lote_laudo_impdv Lote ");
		sql.append("    SET Lote.Ic_franq = 'N' ");
		sql.append("  WHERE Lote.MM_REFER = :mesReferencia ");
		sql.append("    AND Lote.AA_REFER = :anoReferencia ");

		if (idLote != null) {
			sql.append(" AND Lote.ID_LOTE_LAUDO_IMPDV = :idLote ");
		}

		// AND (QtdCalculada / QtdTotal de Laudos) > (pctPermitida/100)
		sql.append("    AND   Lote.Qt_laudo_caldo ");
		sql.append(
				"        / ( SELECT CASE NVL(COUNT ( Laudo.Cd_lvpre ),0) WHEN 0 THEN 1 ELSE COUNT ( Laudo.Cd_lvpre ) END ");
		sql.append("             FROM Vpe0077_lvpre Laudo ");
		sql.append("            WHERE Laudo.Cd_crtor_segur = Lote.Cd_crtor_segur ");
		sql.append("              AND Laudo.Dt_vspre BETWEEN :dataInicio AND :dataFim ) > ");
		sql.append("            NVL((SELECT Perct.Pc_laudo_impdv / 100 ");
		sql.append("                   FROM Vpe0440_laudo_impdv_perct Perct ");
		sql.append("                  WHERE Perct.Cd_crtor_segur = Lote.Cd_crtor_segur ");
		sql.append("                    AND SYSDATE BETWEEN Perct.Dt_inico_vigen AND Perct.Dt_fim_vigen),   ");
		sql.append("                (SELECT Perct.Pc_laudo_impdv / 100 ");
		sql.append("                   FROM Vpe0440_laudo_impdv_perct Perct ");
		sql.append("                  WHERE Perct.Cd_crtor_segur = :codCorretorComum ");
		sql.append("                    AND SYSDATE BETWEEN Perct.Dt_inico_vigen AND Perct.Dt_fim_vigen)  ) ");

		query = em.createNativeQuery(sql.toString());
		query.setParameter("mesReferencia", mesReferencia);
		query.setParameter("anoReferencia", anoReferencia);

		if (idLote != null) {
			query.setParameter("idLote", idLote);
		}

		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);
		query.setParameter("codCorretorComum", ConstantesVistoriaPrevia.COD_CORRETOR_COMUM_IMPRODUTIVO);

		query.executeUpdate();

		// atualiza valores que estão fora da franquia.
		sql = new StringBuffer();

		sql.append(" UPDATE Vpe0439_lote_laudo_impdv Lote ");
		sql.append("    SET Lote.Vl_total_lote_caldo = 0, Lote.Qt_Laudo_Caldo = 0 ");
		sql.append("  WHERE Lote.MM_REFER = :mesReferencia ");
		sql.append("    AND Lote.AA_REFER = :anoReferencia ");
		sql.append("    AND Lote.Ic_franq = 'S' ");

		if (idLote != null) {
			sql.append(" AND Lote.ID_LOTE_LAUDO_IMPDV = :idLote ");
		}

		query = em.createNativeQuery(sql.toString());
		query.setParameter("mesReferencia", mesReferencia);
		query.setParameter("anoReferencia", anoReferencia);

		if (idLote != null) {
			query.setParameter("idLote", idLote);
		}

		query.executeUpdate();
	}

	/**
	 * Retorna os lotes de laudos improdutivos que estão fora da franquia.
	 *
	 * @param idLote
	 * @param mesReferencia
	 * @param anoReferencia
	 * @return List<LoteLaudoImprodutivo>
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<LoteLaudoImprodutivo> obterLotesForaFranquia(Long idLote, Long mesReferencia, Long anoReferencia) {
		Query query;
		StringBuffer sql = new StringBuffer();

		sql = new StringBuffer();
		sql.append(" select * from Vpe0439_lote_laudo_impdv lote ");
		sql.append(" WHERE lote.MM_REFER = :mesReferencia ");
		sql.append(" AND lote.AA_REFER = :anoReferencia ");
		sql.append(" AND lote.ic_Franq = 'N' ");

		if (idLote != null) {
			sql.append(" AND lote.Id_Lote_Laudo_Impdv = :idLoteImprodutivo ");
		}

		// query = em.createNativeQuery(sql.toString(), LoteLaudoImprodutivo.class);
		query = em.createNativeQuery(sql.toString());
		query.setParameter("mesReferencia", mesReferencia);
		query.setParameter("anoReferencia", anoReferencia);

		if (idLote != null) {
			query.setParameter("idLoteImprodutivo", idLote);
		}

		List<Object[]> result = query.getResultList();

		List<LoteLaudoImprodutivo> listaRetorno = new ArrayList<LoteLaudoImprodutivo>();

		for (Object[] obj : result) {

			LoteLaudoImprodutivo laudo = new LoteLaudoImprodutivo();
			listaRetorno.add(laudo);
			laudo.setIdLoteLaudoImpdv(obj[0] != null ? ((BigDecimal) obj[0]).longValue() : null);
			laudo.setAaRefer(obj[1] != null ? ((BigDecimal) obj[1]).longValue() : null);
			laudo.setMmRefer(obj[2] != null ? ((BigDecimal) obj[2]).longValue() : null);
			laudo.setCdCrtorSegur(obj[3] != null ? ((BigDecimal) obj[3]).longValue() : null);
			laudo.setCdSucsl(obj[4] != null ? (Long) ((BigDecimal) obj[4]).longValue() : null);
			laudo.setCdSupin(obj[5] != null ? (Long) ((BigDecimal) obj[5]).longValue() : null);
			laudo.setQtLaudoSelec(obj[6] != null ? ((BigDecimal) obj[6]).longValue() : null);
			laudo.setQtLaudoCaldo(obj[7] != null ? ((BigDecimal) obj[7]).longValue() : null);
			laudo.setVlTotalOrignLote(obj[8] != null ? ((BigDecimal) obj[8]).doubleValue() : null);
			laudo.setVlTotalLoteCaldo(obj[8] != null ? ((BigDecimal) obj[9]).doubleValue() : null);
			laudo.setDtEnvioLote(obj[10] != null ? new Date(((Timestamp) obj[10]).getTime()) : null);
			laudo.setCdUsuroEnvioLote(obj[11] != null ? (String) obj[11] : null);
			laudo.setDtUltmaAlter(obj[12] != null ? new Date(((Timestamp) obj[12]).getTime()) : null);
			laudo.setDsMotvLaudoImpdv(obj[13] != null ? (String) obj[13] : null);
			laudo.setQtTotalLaudoInclo(obj[14] != null ? ((BigDecimal) obj[14]).longValue() : null);
			laudo.setQtTotalLaudoEstor(obj[15] != null ? ((BigDecimal) obj[15]).longValue() : null);
			laudo.setVlTotalLaudoInclo(obj[16] != null ? ((BigDecimal) obj[16]).doubleValue() : null);
			laudo.setVlTotalLaudoEstor(obj[17] != null ? ((BigDecimal) obj[17]).doubleValue() : null);
			laudo.setIcFranq(obj[18] != null ? (String) obj[18] : null);
			laudo.setIcExclu(obj[19] != null ? (String) obj[19] : null);

		}

		return listaRetorno;
	}

	/**
	 * Otem percentual de franqui do corretor.
	 *
	 * @param codigoCorretor
	 * @return Double
	 */

	@Override
	public Double obterPercentualFranquiaCorretor(Long codigoCorretor) {
		StringBuffer sql = new StringBuffer();
		Double valorPercentual;
		Query query;

		try {
			sql.append(" select l.pcLaudoImpdv ");
			sql.append(" from LaudoImprodutivoPercentual l ");
			sql.append(" where  l.cdCrtorSegur = :codigoCorretor ");
			sql.append(" and  l.dtFimVigen = :dataFim ");

			query = em.createQuery(sql.toString());
			query.setParameter("codigoCorretor", codigoCorretor);
			query.setParameter("dataFim", DateUtil.DATA_REGISTRO_VIGENTE);

			valorPercentual = (Double) query.getSingleResult();

			return valorPercentual;

		} catch (NoResultException nr) {

			sql = new StringBuffer();

			sql.append(" select l.pcLaudoImpdv ");
			sql.append(" from LaudoImprodutivoPercentual l ");
			sql.append(" where  l.cdCrtorSegur = :codigoCorretor ");
			sql.append(" and  l.dtFimVigen = :dataFim ");

			query = em.createQuery(sql.toString());
			query.setParameter("codigoCorretor", ConstantesVistoriaPrevia.COD_CORRETOR_COMUM_IMPRODUTIVO);
			query.setParameter("dataFim", DateUtil.DATA_REGISTRO_VIGENTE);

			valorPercentual = (Double) query.getSingleResult();

			return valorPercentual;
		}
	}

	@Override
	public void atualizaValorCalculado(Long idLote, Double valorTotalLoteCalculado, Long qtCalculado,
			String icFranquia) {
		StringBuffer sql = new StringBuffer();

		sql.append(
				" UPDATE VPE0439_LOTE_LAUDO_IMPDV l SET l.VL_TOTAL_LOTE_CALDO = :valorTotalLoteCalculado, l.QT_LAUDO_CALDO = :qtCalculado, l.IC_FRANQ = :icFranquia ");
		sql.append(" WHERE l.ID_LOTE_LAUDO_IMPDV = :idLote ");

		Query query = em.createNativeQuery(sql.toString());

		query.setParameter("valorTotalLoteCalculado", valorTotalLoteCalculado);
		query.setParameter("qtCalculado", qtCalculado);
		query.setParameter("idLote", idLote);
		query.setParameter("icFranquia", icFranquia);

		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<YearMonth> recuperarListaMesAnoReferencia() {

		StringBuilder sql = new StringBuilder();

		sql.append("   SELECT ");
		sql.append("          Lote.mmRefer, ");
		sql.append("          Lote.aaRefer ");
		sql.append("     FROM LoteLaudoImprodutivo Lote ");
		sql.append(" GROUP BY Lote.mmRefer, ");
		sql.append("          Lote.aaRefer  ");
		sql.append(" ORDER BY Lote.aaRefer DESC, ");
		sql.append("          Lote.mmRefer DESC  ");

		List<Object[]> result = em.createQuery(sql.toString()).getResultList();

		List<YearMonth> listaRetorno = new ArrayList<YearMonth>();

		for (Object[] obj : result) {

			int month = ((Long) obj[0]).intValue();
			int year = ((Long) obj[1]).intValue();

			listaRetorno.add(YearMonth.of(year, month));
		}

		return listaRetorno;
	}

	@Override
	public LoteLaudoImprodutivoAux carregarLoteImprodutivo(Long id) {
		String sql = "SELECT new " + LoteLaudoImprodutivoAux.class.getCanonicalName() + "( L, L.icFranq )"
				+ " FROM LoteLaudoImprodutivo L"
				+ " WHERE L.idLoteLaudoImpdv = :idLoteLaudoImpdv";

		Query query = em.createQuery(sql);
		query.setParameter("idLoteLaudoImpdv", id);

		return (LoteLaudoImprodutivoAux) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoteLaudoImprodutivoAux> carregarLotesImprodutivos(RelatorioImprodutivoFiltro filtro) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT new " + LoteLaudoImprodutivoAux.class.getCanonicalName() + "( L, L.icFranq )");
		sql.append("   FROM LoteLaudoImprodutivo L ");
		sql.append("  WHERE L.mmRefer = :mesReferencia ");
		sql.append("    AND L.aaRefer = :anoReferencia  ");

//		if (filtro.getStatusLaudo() != null) {
//			sql.append("    AND L.stProcm = :status  ");
//		}

		if (UtilJava.trueVar(filtro.getSuperintendencia())) {
			sql.append("    AND L.cdSupin =:codSuperintendencia");
		}

		if (UtilJava.trueVar(filtro.getSucursal())) {
			sql.append("    AND L.cdSucsl =:codSucursal");
		}

		if (UtilJava.trueVar(filtro.getCorretor())) {
			sql.append("    AND L.cdCrtorSegur =:codCorretor");
		}

		if (VisaoRelatorioImprodutivoEnum.COD_VISAO_VISTORIA_PREVIA == filtro.getVisao()
				|| VisaoRelatorioImprodutivoEnum.COD_VISAO_VEICULO == filtro.getVisao()) {

			StringBuffer sqlOutrasVisoes = new StringBuffer();
			this.appendQueryVisaoDetalhe(filtro, sqlOutrasVisoes, null);

			sql.append(" AND L.idLoteLaudoImpdv IN ( ").append(sqlOutrasVisoes).append(" )");
		}

		sql.append("  ORDER BY L.cdCrtorSegur");

		Query query = em.createQuery(sql.toString());
		query.setParameter("mesReferencia", new Long(filtro.getDataReferencia().getMonthValue()));
		query.setParameter("anoReferencia", new Long(filtro.getDataReferencia().getYear()));

//		if (filtro.getStatusLaudo() != null) {
//			query.setParameter("status", filtro.getStatusLaudo());
//		}

		if (UtilJava.trueVar(filtro.getSuperintendencia())) {
			query.setParameter("codSuperintendencia", filtro.getSuperintendencia());
		}

		if (UtilJava.trueVar(filtro.getSucursal())) {
			query.setParameter("codSucursal", filtro.getSucursal());
		}

		if (UtilJava.trueVar(filtro.getCorretor())) {
			query.setParameter("codCorretor", filtro.getCorretor());
		}

		if (VisaoRelatorioImprodutivoEnum.COD_VISAO_VISTORIA_PREVIA == filtro.getVisao()
				|| VisaoRelatorioImprodutivoEnum.COD_VISAO_VEICULO == filtro.getVisao()) {

			this.appendQueryVisaoDetalhe(filtro, null, query);
		}

		return query.getResultList();
	}

	/**
	 * Append no sql para Visao de consulta diferente de Geral.
	 * 
	 * @param filtro - FiltroRelatorioImprodutivo
	 * @param sql    - StringBuffer
	 * @param query  - Query
	 */
	private void appendQueryVisaoDetalhe(RelatorioImprodutivoFiltro filtro, StringBuffer sql, Query query) {

		if (sql != null) {
			sql.append("   SELECT ListaAux.idLoteLaudoImpdv	 ");
			sql.append("	 FROM LoteLaudoImprodutivoDetalhe ListaAux, ");
			sql.append("	      LaudoVistoriaPrevia LaudoAux, 		 ");
			sql.append("	      VeiculoVistoriaPrevia VeiculoAux		 ");
			sql.append("    WHERE ListaAux.cdLvpre = LaudoAux.cdLvpre    ");
			sql.append("      AND LaudoAux.cdLvpre = VeiculoAux.cdLvpre  ");
			sql.append("      AND ListaAux.mmReferLaudo =:mesReferencia");
			sql.append("      AND ListaAux.aaReferLaudo =:anoReferencia");

			if (StringUtils.isNotBlank(filtro.getLaudo())) {
				sql.append("  AND UPPER(LaudoAux.cdLvpre) =:numLaudo ");
			}

			if (filtro.getDataVistoria() != null) {
				sql.append("  AND LaudoAux.dtVspre >=:dataVistoria ");
				sql.append("  AND LaudoAux.dtVspre <=:dataVistoriaAte ");
			}

			if (StringUtils.isNotBlank(filtro.getVoucher())) {
				sql.append("  AND UPPER(LaudoAux.cdVouch) =:numVoucher ");
			}

			if (filtro.getParecerTecnico() != null) {
				sql.append("  AND LaudoAux.cdSitucVspre =:codParecerTec ");
			}

			if (StringUtils.isNotBlank(filtro.getPlaca())) {
				sql.append("  AND UPPER(VeiculoAux.cdPlacaVeicu) =:numPlaca ");
			}

			if (StringUtils.isNotBlank(filtro.getChassi())) {
				sql.append("  AND UPPER(VeiculoAux.cdChassiVeicu) =:numChassi ");
			}

			sql.append(" GROUP BY ListaAux.idLoteLaudoImpdv ");

		} else {

			query.setParameter("mesReferencia", new Long(filtro.getDataReferencia().getMonthValue()));
			query.setParameter("anoReferencia", new Long(filtro.getDataReferencia().getYear()));

			if (StringUtils.isNotBlank(filtro.getLaudo())) {
				query.setParameter("numLaudo", filtro.getLaudo().toUpperCase());
			}

			if (filtro.getDataVistoria() != null) {
				query.setParameter("dataVistoria", filtro.getDataVistoria());
				query.setParameter("dataVistoriaAte",
						filtro.getDataVistoria().plusDays(1).atStartOfDay().minusNanos(1l));
			}

			if (StringUtils.isNotBlank(filtro.getVoucher())) {
				query.setParameter("numVoucher", filtro.getVoucher().toUpperCase());
			}

			if (filtro.getParecerTecnico() != null) {
				query.setParameter("codParecerTec", filtro.getParecerTecnico().getCodigo());
			}

			if (StringUtils.isNotBlank(filtro.getPlaca())) {
				query.setParameter("numPlaca", filtro.getPlaca().toUpperCase());
			}

			if (StringUtils.isNotBlank(filtro.getChassi())) {
				query.setParameter("numChassi", filtro.getChassi().toUpperCase());
			}
		}
	}

	/**
	 * Carrega quantidade Lotes Transmitidos
	 */
	@Override
	public Long recuperarQtdLotesTransmitidos(YearMonth ref) {
		try {

			StringBuilder sql = new StringBuilder();

			sql.append(" SELECT count(L) ");
			sql.append("   FROM LoteLaudoImprodutivo L ");
			sql.append("  WHERE L.mmRefer =:mesReferencia ");
			sql.append("    AND L.aaRefer =:anoReferencia ");
			sql.append("    AND L.dtEnvioLote IS NOT NULL ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("mesReferencia", new Long(ref.getMonthValue()));
			query.setParameter("anoReferencia", new Long(ref.getYear()));

			return (Long) query.getSingleResult();

		} catch (NoResultException nre) {
			return 0L;
		}
	}
	
	/**
	 * Carrega Valor Total Calculado (Somente Laudos Improdutivos e não Retirados)
	 * 
	 * @param idLoteLaudoImpdv - Id do Lote
	 * 
	 * @return Double
	 * 
	 */
	public Double carregarVlTotalLoteCaldo(Long idLoteLaudoImpdv) {
		try {

			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT sum (LD.vlLaudo) ");
			sql.append("   FROM LoteLaudoImprodutivo L, ");
			sql.append("        LoteLaudoImprodutivoDetalhe LD  ");
			sql.append("  WHERE LD.idLoteLaudoImpdv =:idLoteLaudoImpdv ");
			sql.append("    AND LD.idLoteLaudoImpdv = L.idLoteLaudoImpdv ");
			sql.append("    AND LD.icExclu = 'N' ");
			sql.append("    AND LD.cdTipoLaudo = 'I' ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("idLoteLaudoImpdv", idLoteLaudoImpdv);
			
			return (Double) query.getSingleResult();
		} catch (NoResultException nre) {
			return 0D;
		}
	}

	@Override
	public void transmitirLotes(YearMonth dataReferencia) {
		StoredProcedureQuery query = em.createStoredProcedureQuery("ADMVPE.VPEVPPA0001_002.laudo_improdutivos_acselx")
		.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
		.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
		.registerStoredProcedureParameter(3, String.class, ParameterMode.OUT)
		.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)
		.setParameter(1, dataReferencia.getYear())
		.setParameter(2, dataReferencia.getMonthValue());
		
		query.execute();
		
		String cod = (String) query.getOutputParameterValue(3);
		
		if (!"0".equals(cod)) {
			String msg = (String) query.getOutputParameterValue(4);
			throw new BusinessVPException(msg);
		}
	}
}
