package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PostoDTO;

@Repository
@Transactional(readOnly = true)
public class PostoVistoriaPreviaRepositoryImpl implements PostoVistoriaPreviaRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<String> findBairrosPorCidade(String uf, String cidade, boolean isAtivo, boolean isCaminhao) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT distinct pv.nmBairr ");
		sql.append("FROM PostoVistoriaPrevia  pv, ");
		sql.append("RegiaoAtendimentoVistoriador regiao ");
		sql.append("WHERE pv.idRegiaoAtnmtVstro = regiao.idRegiaoAtnmtVstro ");
		sql.append("AND regiao.sgUf = :sgUf ");
		sql.append("AND regiao.nmMunic = :nmMunic ");

		if (isCaminhao) {
			sql.append("AND pv.icAtndeCmhao = 'S' ");
		}

		if (isAtivo) {
			sql.append("AND pv.cdSitucPosto = 1 ");
		} else {
			sql.append("AND pv.cdSitucPosto = 0 ");
		}

		sql.append("ORDER BY pv.nmBairr");

		TypedQuery<String> query = em.createQuery(sql.toString(), String.class);

		query.setParameter("sgUf", uf);
		query.setParameter("nmMunic", cidade);

		return query.getResultList();
	}
	
	@Override
	public List<String> findBairrosPorCidade(Long idRegiaoAtnmtVstro, boolean isAtivo, boolean isCaminhao) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT distinct pv.nmBairr ");
		sql.append("FROM PostoVistoriaPrevia  pv ");
		sql.append("WHERE pv.idRegiaoAtnmtVstro =  :idRegiaoAtnmtVstro ");

		if (isCaminhao) {
			sql.append("AND pv.icAtndeCmhao = 'S' ");
		}

		if (isAtivo) {
			sql.append("AND pv.cdSitucPosto = 1 ");
		} else {
			sql.append("AND pv.cdSitucPosto = 0 ");
		}

		sql.append("ORDER BY pv.nmBairr");

		TypedQuery<String> query = em.createQuery(sql.toString(), String.class);

		query.setParameter("idRegiaoAtnmtVstro", idRegiaoAtnmtVstro);

		return query.getResultList();
	}
	
	@Override
	public List<PostoDTO> obterPostosPorLocalizacao(Long idRegiaoAtnmtVstro, String bairro, boolean isAtivo,
			boolean isCaminhao) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT new ");
		sql.append(PostoDTO.class.getCanonicalName());
		sql.append("(posto, prestadora.nmRazaoSocal) ");
		sql.append("FROM PostoVistoriaPrevia  posto, ");
		sql.append("PrestadoraVistoriaPrevia  prestadora ");
		sql.append("WHERE posto.cdAgrmtVspre = prestadora.cdAgrmtVspre ");
		sql.append("AND posto.idRegiaoAtnmtVstro = :idRegiaoAtnmtVstro ");

		boolean bairroNotBlank = StringUtils.isNotBlank(bairro);

		if (bairroNotBlank) {
			sql.append("AND UPPER(posto.nmBairr) = :bairro ");
		}

		if (isCaminhao) {
			sql.append("AND posto.icAtndeCmhao = 'S' ");
		}

		if (isAtivo) {
			sql.append("AND posto.cdSitucPosto = 1 ");
		} else {
			sql.append("AND posto.cdSitucPosto = 0 ");
		}

		sql.append("ORDER BY posto.nmBairr");

		TypedQuery<PostoDTO> query = em.createQuery(sql.toString(), PostoDTO.class);

		query.setParameter("idRegiaoAtnmtVstro", idRegiaoAtnmtVstro);
		
		if (bairroNotBlank) {
			query.setParameter("bairro", bairro.toUpperCase());
		}
		
		return query.getResultList();
	}
	
	@Override
	public List<PostoDTO> obterPostos(String sgUf, String nmMunic, String nmBairr) {
		return obterPostos(null, sgUf, nmMunic, nmBairr);
	}

	@Override
	public List<PostoDTO> obterPostos(Long cdAgrmtVspre, String sgUf, String nmMunic, String nmBairr) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT new ");
		sql.append(PostoDTO.class.getCanonicalName());
		sql.append("(posto, prestadora.nmRazaoSocal) ");
		sql.append("FROM PostoVistoriaPrevia posto, ");
		sql.append("PrestadoraVistoriaPrevia prestadora, ");
		sql.append("RegiaoAtendimentoVistoriador regiao ");
		sql.append("WHERE posto.cdAgrmtVspre = prestadora.cdAgrmtVspre ");
		sql.append("AND posto.idRegiaoAtnmtVstro = regiao.idRegiaoAtnmtVstro ");
		sql.append("AND posto.cdSitucPosto = 1 ");
		sql.append("AND regiao.sgUf = :sgUf ");

		boolean prestadoraNotNull = cdAgrmtVspre != null;

		if (prestadoraNotNull) {
			sql.append("AND posto.cdAgrmtVspre = :cdAgrmtVspre ");
		}

		boolean cidadeNotBlank = StringUtils.isNotBlank(nmMunic);

		if (cidadeNotBlank) {
			sql.append("AND UPPER(regiao.nmMunic) = :nmMunic ");
		}

		boolean bairroNotBlank = StringUtils.isNotBlank(nmBairr);

		if (bairroNotBlank) {
			sql.append("AND UPPER(posto.nmBairr) = :nmBairr ");
		}

		sql.append("ORDER BY regiao.nmMunic, posto.nmBairr");

		TypedQuery<PostoDTO> query = em.createQuery(sql.toString(), PostoDTO.class);

		query.setParameter("sgUf", sgUf.toUpperCase());
		
		if (prestadoraNotNull) {
			query.setParameter("cdAgrmtVspre", cdAgrmtVspre);
		}
		
		if (cidadeNotBlank) {
			query.setParameter("nmMunic", nmMunic.toUpperCase());
		}
		
		if (bairroNotBlank) {
			query.setParameter("nmBairr", nmBairr.toUpperCase());
		}
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<PostoDTO> obterPostos(PostoDTO filtro, Pageable pageable) {

		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append("SELECT count(posto.cdPostoVspre) ");
		buildFromWhere(filtro, sqlCount);
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT new ");
		sql.append(PostoDTO.class.getCanonicalName());
		sql.append("(posto, prestadora.nmRazaoSocal) ");
		buildFromWhere(filtro, sql);
		sql.append("ORDER BY posto.nmPostoVspre");

		Query query = em.createQuery(sqlCount.toString());
		buildWhere(filtro, query);
		
		long count = (long) query.getSingleResult();

		List<PostoDTO> content = new ArrayList<>();
		
		if (count > 0) {
			query = em.createQuery(sql.toString()).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize());
			buildWhere(filtro, query);
			content = query.getResultList();
		}
		
		return new PageImpl<>(content, pageable, count);
	}

	private void buildFromWhere(PostoDTO filtro, StringBuilder sql) {
		sql.append("FROM PostoVistoriaPrevia posto, ");
		sql.append("PrestadoraVistoriaPrevia prestadora, ");
		sql.append("RegiaoAtendimentoVistoriador regiao ");
		sql.append("WHERE posto.cdAgrmtVspre = prestadora.cdAgrmtVspre ");
		sql.append("AND posto.idRegiaoAtnmtVstro = regiao.idRegiaoAtnmtVstro ");

		if (filtro.getCdSitucPosto() != null) {
			sql.append("AND posto.cdSitucPosto = " + filtro.getCdSitucPosto());
		}
		
		boolean prestadoraNotNull = filtro.getCdAgrmtVspre() != null;

		if (prestadoraNotNull) {
			sql.append("AND posto.cdAgrmtVspre = :cdAgrmtVspre ");
		}

		boolean ufNotBlank = StringUtils.isNotBlank(filtro.getSgUniddFedrc());

		if (ufNotBlank) {
			sql.append("AND regiao.sgUf = :sgUf ");
		}

		boolean cidadeNotBlank = StringUtils.isNotBlank(filtro.getNmCidad());

		if (cidadeNotBlank) {
			sql.append("AND UPPER(regiao.nmMunic) = :nmMunic ");
		}

		boolean bairroNotBlank = StringUtils.isNotBlank(filtro.getNmBairr());

		if (bairroNotBlank) {
			sql.append("AND UPPER(posto.nmBairr) = :nmBairr ");
		}
	}

	private void buildWhere(PostoDTO filtro, Query query) {
		boolean prestadoraNotNull = filtro.getCdAgrmtVspre() != null;
		boolean ufNotBlank = StringUtils.isNotBlank(filtro.getSgUniddFedrc());
		boolean cidadeNotBlank = StringUtils.isNotBlank(filtro.getNmCidad());
		boolean bairroNotBlank = StringUtils.isNotBlank(filtro.getNmBairr());

		if (prestadoraNotNull) {
			query.setParameter("cdAgrmtVspre", filtro.getCdAgrmtVspre());
		}
		
		if (ufNotBlank) {
			query.setParameter("sgUf", filtro.getSgUniddFedrc().toUpperCase());
		}

		if (cidadeNotBlank) {
			query.setParameter("nmMunic", filtro.getNmCidad().toUpperCase());
		}
		
		if (bairroNotBlank) {
			query.setParameter("nmBairr", filtro.getNmBairr().toUpperCase());
		}
	}

}