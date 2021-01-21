package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoCusto;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilJava;

public class LaudoCustoRepositoryImpl implements LaudoCustoRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Carrega Lista de Valores para Laudo Improdutivo
	 * 
	 * @param mesReferencia
	 * @param anoReferencia
	 * 
	 * @return List<LaudoCusto>
	 * 
	 * @see br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoCusto
	 */
	@SuppressWarnings("unchecked")
	public List<LaudoCusto> carregarCustoVPImprodutiva(Long mesReferencia, Long anoReferencia) {

		StringBuffer sql = new StringBuffer();

		sql.append("   SELECT LC ");
		sql.append("     FROM LaudoCusto LC ");
		sql.append("    WHERE LC.idLaudoCusto = LC.idLaudoCusto  ");

		if (UtilJava.trueVar(mesReferencia)) {
			sql.append("      AND LC.mmInicoRefer=:mesReferencia ");
		}
		if (UtilJava.trueVar(anoReferencia)) {
			sql.append("      AND LC.aaInicoRefer=:anoReferencia ");
		}

		sql.append(" ORDER BY LC.aaInicoRefer DESC, ");
		sql.append("  		  LC.mmInicoRefer DESC ");

		Query query = em.createQuery(sql.toString());
		if (UtilJava.trueVar(mesReferencia)) {
			query.setParameter("mesReferencia", mesReferencia);
		}
		if (UtilJava.trueVar(anoReferencia)) {
			query.setParameter("anoReferencia", anoReferencia);
		}

		return query.getResultList();
	}
}
