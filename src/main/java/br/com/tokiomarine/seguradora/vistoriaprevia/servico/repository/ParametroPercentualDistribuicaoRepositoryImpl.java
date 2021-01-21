package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections4.MapUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ParametroPercentualDistribuicaoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RegiaoAtendimentoVistoriadorDTO;

@Repository
@Transactional(readOnly = true)
public class ParametroPercentualDistribuicaoRepositoryImpl implements ParametroPercentualDistribuicaoRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public Page<ParametroPercentualDistribuicaoDTO> findAll(Map<String, Object> filtro, Pageable pageable) {
		StringBuilder sqlCount = new StringBuilder("SELECT count(pd.idParamPerctDistr) ");
		StringBuilder sqlData = new StringBuilder("SELECT new br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ParametroPercentualDistribuicaoDTO(pd, p, r) ");
		
		StringBuilder sqlFrom = new StringBuilder();
		sqlFrom.append("FROM ParametroPercentualDistribuicao pd, PrestadoraVistoriaPrevia p, RegiaoAtendimentoVistoriador r ");
		sqlFrom.append("WHERE pd.cdAgrmtVspre = p.cdAgrmtVspre ");
		sqlFrom.append("AND pd.idRegiaoAtnmtVstro = r.idRegiaoAtnmtVstro ");
		sqlFrom.append("AND :dataReferencia BETWEEN pd.dtInicoVigen AND pd.dtFimVigen ");
		
		if (filtro.containsKey(ParametroPercentualDistribuicaoDTO.Fields.prestadora)) {
			sqlFrom.append("AND p.nmRazaoSocal like :nmRazaoSocal ");
		}
		
		if (filtro.containsKey(RegiaoAtendimentoVistoriadorDTO.Fields.nmMunic)) {
			sqlFrom.append("AND r.nmMunic like :nmMunic ");
		}

		if (filtro.containsKey(RegiaoAtendimentoVistoriadorDTO.Fields.cdMunic)) {
			sqlFrom.append("AND r.cdMunic = :cdMunic ");
		}
		
		Query query = em.createQuery(sqlCount.append(sqlFrom).toString());
		buildWhere(filtro, query);
		
		long count = (long) query.getSingleResult();

		List<ParametroPercentualDistribuicaoDTO> content = new ArrayList<>();
		
		if (count > 0) {
			query = em.createQuery(sqlData.append(sqlFrom).toString()).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize());
			buildWhere(filtro, query);
			content = query.getResultList();
		}
		
		return new PageImpl<>(content, pageable, count);
	}

	private void buildWhere(Map<String, Object> filtro, Query query) {
		query.setParameter("dataReferencia", new Date());

		if (filtro.containsKey(ParametroPercentualDistribuicaoDTO.Fields.prestadora)) {
			query.setParameter("nmRazaoSocal", "%" + MapUtils.getString(filtro, ParametroPercentualDistribuicaoDTO.Fields.prestadora).toUpperCase() + "%");
		}
		
		if (filtro.containsKey(RegiaoAtendimentoVistoriadorDTO.Fields.nmMunic)) {
			query.setParameter("nmMunic", "%" + MapUtils.getString(filtro, RegiaoAtendimentoVistoriadorDTO.Fields.nmMunic).toUpperCase() + "%");
		}

		if (filtro.containsKey(RegiaoAtendimentoVistoriadorDTO.Fields.cdMunic)) {
			query.setParameter("cdMunic", MapUtils.getLong(filtro, RegiaoAtendimentoVistoriadorDTO.Fields.cdMunic));
		}
	}

}
