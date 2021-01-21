package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.LoteLaudoImprodutivoDetalheAux;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.RelatorioImprodutivoFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilJava;

public class LoteLaudoImprodutivoDetalheRepositoryImpl implements LoteLaudoImprodutivoDetalheRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Carregar Lista de Detalhes do Lote Improdutivo
	 * 
	 * @param idLoteLaudoImpdv - Id do Lote
	 * @param codTipoLaudo     - Código Tipo Laudo : I - Improdutivo / C - Inclusao
	 *                         / E - Estorno
	 * @param filtro           - FiltroRelatorioImprodutivo
	 * 
	 * @return List<LoteLaudoImprodutivoDetalheAux>
	 * 
	 * @see br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivo
	 * @see br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivoDetalhe
	 * @see br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia
	 * @see br.com.tokiomarine.seguradora.ssv.vp.crud.model.VeiculoVistoriaPrevia
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoteLaudoImprodutivoDetalheAux> carregarDetalheLoteImprodutivo(Long idLoteLaudoImpdv,
			String codTipoLaudo, RelatorioImprodutivoFiltro filtro) {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT new ");
		sql.append(LoteLaudoImprodutivoDetalheAux.class.getCanonicalName());
		sql.append("       (");
		sql.append("          Detalhe, ");
		sql.append("          Lote.icExclu,          ");
		sql.append("          Lote.icFranq,          ");
		sql.append("          Lote.dtEnvioLote,      ");
		sql.append("		  Detalhe.cdLvpre, 		 ");
		sql.append("		  Laudo.dtVspre,	     ");
		sql.append("		  Laudo.cdSitucVspre,	 ");
		sql.append("		  Laudo.cdVouch, 		 ");
		sql.append("		  Laudo.cdNgoco, 		 ");
		sql.append("		  Veiculo.cdPlacaVeicu,	 ");
		sql.append("		  Veiculo.cdChassiVeicu, ");
		sql.append("	      Detalhe.cdMotvImpdv 	 ");
		sql.append("       )");
		sql.append("	 FROM LoteLaudoImprodutivo Lote,     	   ");
		sql.append("          LoteLaudoImprodutivoDetalhe Detalhe, ");
		sql.append("	      LaudoVistoriaPrevia Laudo, 		   ");
		sql.append("	      VeiculoVistoriaPrevia Veiculo		   ");
		sql.append("    WHERE Lote.idLoteLaudoImpdv = Detalhe.idLoteLaudoImpdv ");
		sql.append("      AND Detalhe.cdLvpre = Laudo.cdLvpre ");
		sql.append("      AND Laudo.cdLvpre = Veiculo.cdLvpre  ");

		if (UtilJava.trueVar(idLoteLaudoImpdv)) {
			sql.append("  AND Detalhe.idLoteLaudoImpdv =:idLoteLaudoImpdv");
		} else {
			StringBuffer sqlOutrasVisoes = new StringBuffer();
			this.appendQueryVisaoDetalhe(filtro, sqlOutrasVisoes, null);

			sql.append("  AND Detalhe.idLoteLaudoImpdv IN ( ").append(sqlOutrasVisoes).append(" )");
		}

		if (UtilJava.trueVar(codTipoLaudo)) {
			if ("C&E".equals(codTipoLaudo)) {
				sql.append("  AND Detalhe.cdTipoLaudo IN ('C','E') ");
			} else {
				sql.append("  AND Detalhe.cdTipoLaudo =:codTipoLaudo ");
			}
		}

		Query query = em.createQuery(sql.toString());
		if (UtilJava.trueVar(idLoteLaudoImpdv)) {
			query.setParameter("idLoteLaudoImpdv", idLoteLaudoImpdv);
		} else {
			this.appendQueryVisaoDetalhe(filtro, null, query);
		}

		if (UtilJava.trueVar(codTipoLaudo) && !"C&E".equals(codTipoLaudo)) {
			query.setParameter("codTipoLaudo", codTipoLaudo);
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
						filtro.getDataVistoria().plusDays(1l).atStartOfDay().minusNanos(1l));
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
	 * Carrega Lista de Laudos Improdutivos que podem ser Incluidos/Estornados
	 * Somente Lotes Enviados.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoteLaudoImprodutivoDetalheAux> carregarListaLaudoAdicional(RelatorioImprodutivoFiltro filtro) {
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT new ");
		sql.append(LoteLaudoImprodutivoDetalheAux.class.getCanonicalName());
		sql.append("       (");
		sql.append("          Detalhe, ");
		sql.append("          Lote.icExclu,          ");
		sql.append("          Lote.icFranq,          ");
		sql.append("          Lote.dtEnvioLote,      ");
		sql.append("		  Detalhe.cdLvpre, 		 ");
		sql.append("		  Laudo.dtVspre,	     ");
		sql.append("		  Laudo.cdSitucVspre,	 ");
		sql.append("		  Laudo.cdVouch, 		 ");
		sql.append("		  Laudo.cdNgoco, 		 ");
		sql.append("		  Veiculo.cdPlacaVeicu,	 ");
		sql.append("		  Veiculo.cdChassiVeicu, ");
		sql.append("	      Detalhe.cdMotvImpdv 	 ");
		sql.append("       )");
		sql.append("	 FROM LoteLaudoImprodutivo Lote,     	   ");
		sql.append("          LoteLaudoImprodutivoDetalhe Detalhe, ");
		sql.append("	      LaudoVistoriaPrevia Laudo, 		   ");
		sql.append("	      VeiculoVistoriaPrevia Veiculo		   ");
		sql.append("    WHERE Lote.idLoteLaudoImpdv = Detalhe.idLoteLaudoImpdv ");
		sql.append("      AND Detalhe.cdLvpre = Laudo.cdLvpre ");
		sql.append("      AND Laudo.cdLvpre = Veiculo.cdLvpre  ");
		sql.append("      AND Detalhe.mmReferLaudo =:mesReferencia");
		sql.append("      AND Detalhe.aaReferLaudo =:anoReferencia");
		sql.append("      AND Lote.cdCrtorSegur =:codCorretor ");
		sql.append("      AND Lote.dtEnvioLote IS NOT NULL ");
		// sql.append(" AND Detalhe.stCobrc IN ('R','E') ");
		sql.append("      AND Detalhe.idLoteLaudoImpdvDetal = ( SELECT MAX ( DetalheAux.idLoteLaudoImpdvDetal ) ");
		sql.append("                             			    FROM LoteLaudoImprodutivoDetalhe DetalheAux ");
		sql.append("                                           WHERE DetalheAux.cdLvpre = Detalhe.cdLvpre )");

		if (StringUtils.isNotBlank(filtro.getLaudo())) {
			sql.append("  AND Laudo.cdLvpre =:numLaudo ");
		}

		if (StringUtils.isNotBlank(filtro.getVoucher())) {
			sql.append("  AND Laudo.cdVouch =:numVoucher ");
		}

		Query query = em.createQuery(sql.toString());

		query.setParameter("mesReferencia", new Long(filtro.getDataReferencia().getMonthValue()));
		query.setParameter("anoReferencia", new Long(filtro.getDataReferencia().getYear()));
		query.setParameter("codCorretor", filtro.getCorretor());

		if (StringUtils.isNotBlank(filtro.getLaudo())) {
			query.setParameter("numLaudo", filtro.getLaudo());
		}

		if (StringUtils.isNotBlank(filtro.getVoucher())) {
			query.setParameter("numVoucher", filtro.getVoucher());
		}

		return query.getResultList();
	}
	
	/**
	 * Apaga Laudos Adicionais (incluidos/estornados)
	 * 
	 * @param idLoteLaudoImpdv - Id Lote
	 * 
	 */
	public void excluirListaLaudoAdicional(Long idLoteLaudoImpdv) {
		StringBuffer sql = new StringBuffer();

		sql.append(" DELETE ");
		sql.append("   FROM LoteLaudoImprodutivoDetalhe Detalhe ");
		sql.append("  WHERE Detalhe.idLoteLaudoImpdv =:idLoteLaudoImpdv ");
		sql.append("    AND Detalhe.cdTipoLaudo IN ('C','E') ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("idLoteLaudoImpdv", idLoteLaudoImpdv);

		query.executeUpdate();
	}
	
	/**
	 * Altera Situação de Cobrança dos Detalhes de um Lote
	 * 
	 * @param idLoteLaudoImpdv - Id Lote
	 * @param codSituacao - (S)sim/(N)não
	 * 
	 */
	@Override
	public void alterarSitucDetalheLote(Long idLoteLaudoImpdv, String codSituacao) {
		StringBuffer sql = new StringBuffer();

		sql.append(" UPDATE LoteLaudoImprodutivoDetalhe detalhe  ");
		sql.append("    SET detalhe.icExclu =:codSituacao ");
		sql.append("  WHERE detalhe.idLoteLaudoImpdv =:idLoteLaudoImpdv ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("codSituacao", codSituacao);
		query.setParameter("idLoteLaudoImpdv", idLoteLaudoImpdv);

		query.executeUpdate();
	}
}
