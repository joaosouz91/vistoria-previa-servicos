package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.LaudoImprodutivoPercentualAux;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilJava;

public class LaudoImprodutivoPercentualRepositoryImpl implements LaudoImprodutivoPercentualRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Carrega Percentual permitido de Laudos Improdutivos por corretor de acordo
	 * com data de referencia.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LaudoImprodutivoPercentualAux carregarPercentualVPImprodutivaAtual(Long codCorretor, Long codSucursal,
			Date dataReferencia) {
		StringBuffer sql = new StringBuffer();

		try {

			sql.append(" SELECT new ");
			sql.append(LaudoImprodutivoPercentualAux.class.getCanonicalName());
			sql.append("       (				   ");
			sql.append("   		  lip ");
			sql.append("       )				   ");
			sql.append("     FROM LaudoImprodutivoPercentual lip ");
			sql.append("    WHERE lip.idLaudoImpdvPerct = lip.idLaudoImpdvPerct ");
			sql.append("      AND lip.cdCrtorSegur =:codCorretor ");
			sql.append("      AND :dataReferencia BETWEEN lip.dtInicoVigen AND lip.dtFimVigen ");

			if (UtilJava.trueVar(codSucursal)) {
				sql.append("      AND lip.cdSucsl =:codSucursal ");
			}

			Query query = em.createQuery(sql.toString());
			query.setParameter("codCorretor", codCorretor);
			query.setParameter("dataReferencia", dataReferencia);

			if (UtilJava.trueVar(codSucursal)) {
				query.setParameter("codSucursal", codSucursal);
			}

			List<LaudoImprodutivoPercentualAux> listPercentual = query.getResultList();
			if (listPercentual == null || listPercentual.isEmpty()) {
				return null;
			}

			return listPercentual.get(0);

		} catch (NoResultException e) {
			return null;
		}
	}

}
