package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoDTO;

@Repository
@Transactional(readOnly = true)
public class AgendamentoVistoriaPreviaRepositoryImpl implements AgendamentoVistoriaPreviaRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<AgendamentoDTO> findAgendamentosDomicilioSicrediAGD(Date dataDe, Date dataAte){
		StringBuilder sql = new StringBuilder();
		sql.append("select");
		sql.append(" agd.CD_VOUCH,"); 
		sql.append(" sts.CD_SITUC_AGMTO,"); 
		sql.append(" vis.CD_CRTOR_CIA,"); 
		sql.append(" vis.NM_CLIEN,"); 
		sql.append(" vis.nr_cpf_cnpj_clien,"); 
		sql.append(" vis.TP_VEICU,"); 
		sql.append(" vis.NM_FABRT,"); 
		sql.append(" vis.DS_MODEL_VEICU,"); 
		sql.append(" vis.CD_PLACA_VEICU,"); 
		sql.append(" vis.CD_CHASSI_VEICU,"); 
		sql.append(" vis.AA_FABRC,"); 
		sql.append(" vis.AA_MODEL,"); 
		sql.append(" vis.IC_VEICU_ZERO_KM,"); 
		sql.append(" vis.cd_ngoco,"); 
		sql.append(" vis.cd_endos,"); 
		sql.append(" vis.cd_fipe,"); 
		sql.append(" visDom.dt_vspre,"); 
		sql.append(" visDom.nm_logra,"); 
		sql.append(" visDom.nr_logra,"); 
		sql.append(" visDom.nm_bairr,"); 
		sql.append(" visDom.nm_cidad,"); 
		sql.append(" visDom.sg_unidd_fedrc"); 
		sql.append(" from VPE0425_AGEND_VSPRE agd, VPE0426_AGEND_DOMCL visDom, VPE0424_VSPRE_OBGTA vis, VPE0437_STATU_AGMTO sts"); 
		sql.append(" where agd.CD_VOUCH = vis.CD_VOUCH"); 
		sql.append(" and agd.CD_VOUCH = sts.CD_VOUCH"); 
		sql.append(" and agd.CD_VOUCH = visDom.CD_VOUCH"); 
		sql.append(" and (sts.ID_STATU_AGMTO in"); 
		sql.append(	" (select max(maxSts.ID_STATU_AGMTO)"); 
		sql.append(	" from VPE0437_STATU_AGMTO maxSts"); 
		sql.append(	" where maxSts.CD_VOUCH = sts.CD_VOUCH"); 
		sql.append(	" group by maxSts.CD_VOUCH))"); 
		sql.append(" and vis.ID_VSPRE_OBGTA ="); 
		sql.append(	" (select max(maxVis.ID_VSPRE_OBGTA)"); 
		sql.append(	" from VPE0424_VSPRE_OBGTA maxVis"); 
		sql.append(	" where maxVis.CD_CRTOR_CIA = vis.CD_CRTOR_CIA"); 
		sql.append(	" and maxVis.CD_PLACA_VEICU = vis.CD_PLACA_VEICU"); 
		sql.append(	" and maxVis.CD_CHASSI_VEICU = vis.CD_CHASSI_VEICU"); 
		sql.append(	" and (maxVis.CD_SISTM_ORIGM not in (14, 15) or"); 
		sql.append(	" maxVis.CD_VOUCH is not null))"); 
		
		if (dataAte != null) {
			sql.append(" and visDom.dt_vspre >= :dataDe"); 
			sql.append(" and visDom.dt_vspre < :dataAte"); 
		} else {
			sql.append(" and visDom.dt_vspre = :dataDe"); 
		}
		
		sql.append(" and sts.CD_SITUC_AGMTO = 'AGD'"); 
		sql.append(" and agd.tp_vspre = 'D'"); 
		sql.append(" and vis.CD_CRTOR_CIA = 402676");
		
		Query query = em.createNativeQuery(sql.toString());

		if (dataAte != null) {
			query.setParameter("dataDe", dataDe);
			query.setParameter("dataAte", dataAte);
		} else {
			query.setParameter("dataDe", dataDe);
		}
		
		return ((List<Object[]>)query.getResultList()).stream().map(AgendamentoDTO::new).collect(Collectors.toList());
	}

	public Optional<AgendamentoDTO> findAgendamentoDomicilioSicredi(String voucher){
		StringBuilder  sql = new StringBuilder();
		sql.append("select");
		sql.append(" agd.CD_VOUCH,"); 
		sql.append(" sts.CD_SITUC_AGMTO,"); 
		sql.append(" vis.CD_CRTOR_CIA,"); 
		sql.append(" vis.NM_CLIEN,"); 
		sql.append(" vis.nr_cpf_cnpj_clien,"); 
		sql.append(" vis.TP_VEICU,"); 
		sql.append(" vis.NM_FABRT,"); 
		sql.append(" vis.DS_MODEL_VEICU,"); 
		sql.append(" vis.CD_PLACA_VEICU,"); 
		sql.append(" vis.CD_CHASSI_VEICU,"); 
		sql.append(" vis.AA_FABRC,"); 
		sql.append(" vis.AA_MODEL,"); 
		sql.append(" vis.IC_VEICU_ZERO_KM,"); 
		sql.append(" vis.cd_ngoco,"); 
		sql.append(" vis.cd_endos,"); 
		sql.append(" vis.cd_fipe,"); 
		sql.append(" visDom.dt_vspre,"); 
		sql.append(" visDom.nm_logra,"); 
		sql.append(" visDom.nr_logra,"); 
		sql.append(" visDom.nm_bairr,"); 
		sql.append(" visDom.nm_cidad,"); 
		sql.append(" visDom.sg_unidd_fedrc"); 
		sql.append(" from VPE0425_AGEND_VSPRE agd, VPE0426_AGEND_DOMCL visDom, VPE0424_VSPRE_OBGTA vis, VPE0437_STATU_AGMTO sts"); 
		sql.append(" where agd.CD_VOUCH = vis.CD_VOUCH"); 
		sql.append(" and agd.CD_VOUCH = sts.CD_VOUCH"); 
		sql.append(" and agd.CD_VOUCH = visDom.CD_VOUCH"); 
		sql.append(" and (sts.ID_STATU_AGMTO in"); 
		sql.append(	" (select max(maxSts.ID_STATU_AGMTO)"); 
		sql.append(	" from VPE0437_STATU_AGMTO maxSts"); 
		sql.append(	" where maxSts.CD_VOUCH = sts.CD_VOUCH"); 
		sql.append(	" group by maxSts.CD_VOUCH))"); 
		sql.append(" and vis.ID_VSPRE_OBGTA ="); 
		sql.append(	" (select max(maxVis.ID_VSPRE_OBGTA)"); 
		sql.append(	" from VPE0424_VSPRE_OBGTA maxVis"); 
		sql.append(	" where maxVis.CD_CRTOR_CIA = vis.CD_CRTOR_CIA"); 
		sql.append(	" and maxVis.CD_PLACA_VEICU = vis.CD_PLACA_VEICU"); 
		sql.append(	" and maxVis.CD_CHASSI_VEICU = vis.CD_CHASSI_VEICU"); 
		sql.append(	" and (maxVis.CD_SISTM_ORIGM not in (14, 15) or"); 
		sql.append(	" maxVis.CD_VOUCH is not null))"); 
		sql.append(" and agd.tp_vspre = 'D'"); 
		sql.append(" and vis.CD_CRTOR_CIA = 402676");
		sql.append(" and agd.CD_VOUCH = :voucher");
		
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("voucher", voucher);
		
		Object[] obj = (Object[]) query.getSingleResult();
		
		if (obj != null) {
			return Optional.of(new AgendamentoDTO(obj));
		}
		
		return Optional.empty();
	}
}
