package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SistemaChamador.AGTOSEMPROPOSTATMS;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.CAN;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.FIM;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.PEN;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.RCB;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.RLZ;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.FTT;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.VFR;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaObrigatoriaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamentoFiltro;

@Repository
@Transactional(readOnly = true)
public class VistoriaPreviaObrigatoriaRepositoryImpl implements VistoriaPreviaObrigatoriaRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<VistoriaObrigatoriaDTO> recuperarListaItemAgendar(VistoriaFiltro filtro) {
		StringBuilder sqlFrom = getFromRecuperarListaItemAgendar(filtro);

		String sqlDados = sqlSelectAgendar(sqlFrom)
		.toString();

		TypedQuery<VistoriaObrigatoriaDTO> queryDados = em.createQuery(sqlDados, VistoriaObrigatoriaDTO.class);
		addParametrosRecuperarListaItemAgendar(filtro, queryDados);
		
		return queryDados.getResultList();
	}
	
	@Override
	public Page<VistoriaObrigatoriaDTO> recuperarListaItemAgendar(VistoriaFiltro filtro, Pageable page) {
		StringBuilder sqlFrom = getFromRecuperarListaItemAgendar(filtro);

		String sqlCount = new StringBuffer()
		.append("SELECT COUNT(Vpo.idVspreObgta) ")
		.append(sqlFrom)
		.toString();
		
		TypedQuery<Long> queryCount = em.createQuery(sqlCount, Long.class);
		addParametrosRecuperarListaItemAgendar(filtro, queryCount);
		Long count = queryCount.getSingleResult();
		
		if (count > 0L) {
			
			String sqlDados = sqlSelectAgendar(sqlFrom)
			.toString();
	
			TypedQuery<VistoriaObrigatoriaDTO> queryDados = em.createQuery(sqlDados, VistoriaObrigatoriaDTO.class);
			addParametrosRecuperarListaItemAgendar(filtro, queryDados);
			
			List<VistoriaObrigatoriaDTO> resultList = queryDados
				.setFirstResult(page.getPageNumber() * page.getPageSize())
				.setMaxResults(page.getPageSize())
				.getResultList();
		
			return new PageImpl<>(resultList, page, count);
		}
		
		return Page.empty();
	}

	private StringBuilder sqlSelectAgendar(StringBuilder sqlFrom) {
		return new StringBuilder()
		.append("SELECT new ")
		.append(VistoriaObrigatoriaDTO.class.getCanonicalName())
		.append("(")
		.append("Vpo.idVspreObgta,")
		.append("Vpo.cdCrtorCia,")
		.append("Vpo.tpVeicu,")
		.append("Vpo.nmClien,")
		.append("Vpo.dsModelVeicu,")
		.append("Vpo.cdPlacaVeicu,")
		.append("Vpo.cdChassiVeicu,")
		.append("Vpo.nmFabrt,")
		.append("Vpo.aaFabrc,")
		.append("Vpo.aaModel,")
		.append("Vpo.icVeicuZeroKm")
		.append(") ")
		.append(sqlFrom)
		.append("ORDER BY Vpo.dtUltmaAlter");
	}

	private void addParametrosRecuperarListaItemAgendar(VistoriaFiltro filtro, Query query) {
		/* PARAMETROS: */
		if (filtro.getId() != null) {
			query.setParameter("idVspreObgta", filtro.getId());
		}

		if (filtro.getDataDe() != null) {
			query.setParameter("dataDe", filtro.getDataDe(), TemporalType.DATE);
		}

		if (filtro.getDataAte() != null) {
			query.setParameter("dataAte", filtro.getDataAte(), TemporalType.DATE);
		}

		this.carregarFiltroAgendar(filtro, query);
	}

	private StringBuilder getFromRecuperarListaItemAgendar(VistoriaFiltro filtro) {
		StringBuilder sql = new StringBuilder();
		sql.append("FROM VistoriaPreviaObrigatoria Vpo ");
		sql.append("WHERE (Vpo.cdVouch IS NULL) ");
		
		if (filtro.getId() != null) {
			sql.append("AND Vpo.idVspreObgta = :idVspreObgta ");
		}

		if (filtro.getDataDe() != null) {
			sql.append("AND Vpo.dtUltmaAlter >= :dataDe ");
		}

		if (filtro.getDataAte() != null) {
			sql.append("AND Vpo.dtUltmaAlter <= :dataAte ");
		}

		this.carregarFiltroAgendar(filtro, sql, "Vpo");

		if (filtro.getEndosso() != null || filtro.getNegocio() != null
				|| filtro.getCalculo() != null || filtro.getItem() != null || filtro.isFrota()) {

			sql.append("AND Vpo.idVspreObgta = ( SELECT MAX ( MAIORPRE.idVspreObgta ) ");
			sql.append("FROM VistoriaPreviaObrigatoria MAIORPRE ");
			sql.append("WHERE MAIORPRE.cdCrtorCia = Vpo.cdCrtorCia  ");
			sql.append("AND MAIORPRE.cdPlacaVeicu = Vpo.cdPlacaVeicu ");
			sql.append("AND MAIORPRE.cdChassiVeicu = Vpo.cdChassiVeicu  ");
			sql.append("AND MAIORPRE.cdVouch IS NULL  ");
			sql.append("AND MAIORPRE.cdSistmOrigm NOT IN (");
			sql.append(AGTOSEMPROPOSTATMS);
			sql.append(")) ");
		}
		
		return sql;
	}

	@Override
	public List<VistoriaObrigatoriaDTO> recuperarListaItemAgendada(VistoriaFiltro filtro) {
		StringBuilder sqlFrom = getFromRecuperarListaItemAgendada(filtro);

		String sqlDados = sqlSelectAgendada(sqlFrom)
		.toString();

		TypedQuery<VistoriaObrigatoriaDTO> queryDados = em.createQuery(sqlDados, VistoriaObrigatoriaDTO.class);
		addParametrosRecuperarListaItemAgendada(filtro, queryDados);

		return queryDados.getResultList();
	}
	
	@Override
	public Page<VistoriaObrigatoriaDTO> recuperarListaItemAgendada(VistoriaFiltro filtro, Pageable page) {
		StringBuilder sqlFrom = getFromRecuperarListaItemAgendada(filtro);

		Long count = countListaItemAgendada(filtro, sqlFrom);
		
		if (count > 0L) {
			String sqlDados = sqlSelectAgendada(sqlFrom)
			.toString();
	
			TypedQuery<VistoriaObrigatoriaDTO> queryDados = em.createQuery(sqlDados, VistoriaObrigatoriaDTO.class);
			addParametrosRecuperarListaItemAgendada(filtro, queryDados);
	
			List<VistoriaObrigatoriaDTO> resultList = queryDados
			.setFirstResult(page.getPageNumber() * page.getPageSize())
			.setMaxResults(page.getPageSize())
			.getResultList();
			
			return new PageImpl<>(resultList, page, count);
		}
		
		return Page.empty();
	}

	private StringBuilder sqlSelectAgendada(StringBuilder sqlFrom) {
		return new StringBuilder()
		.append("SELECT new ")
		.append(VistoriaObrigatoriaDTO.class.getCanonicalName())
		.append("(")
		.append("Vpo.idVspreObgta,")
		.append("Vpo.cdCrtorCia,")
		.append("Vpo.tpVeicu,")
		.append("Vpo.nmClien,")
		.append("Vpo.dsModelVeicu,")
		.append("Vpo.cdPlacaVeicu,")
		.append("Vpo.cdChassiVeicu,")
		.append("Vpo.nmFabrt,")
		.append("Vpo.aaFabrc,")
		.append("Vpo.aaModel,")
		.append("Vpo.icVeicuZeroKm,")
		.append("Avp.cdVouch,")
		.append("Avp.tpVspre,")
		.append("S.cdSitucAgmto,")
		.append("S.cdMotvSitucAgmto")
		.append(") ")
		.append(sqlFrom)
		.append("ORDER BY Avp.dtUltmaAlter ");
	}

	@Override
	public Long countListaItemAgendada(VistoriaFiltro filtro) {
		StringBuilder sqlFrom = getFromRecuperarListaItemAgendada(filtro);
		return countListaItemAgendada(filtro, sqlFrom);
	}

	private Long countListaItemAgendada(VistoriaFiltro filtro, StringBuilder sqlFrom) {
		String sqlCount = new StringBuilder()
		.append("SELECT COUNT(Vpo.idVspreObgta) ")
		.append(sqlFrom)
		.toString();

		TypedQuery<Long> queryCount = em.createQuery(sqlCount, Long.class);
		addParametrosRecuperarListaItemAgendada(filtro, queryCount);
		return queryCount.getSingleResult();
	}

	private void addParametrosRecuperarListaItemAgendada(VistoriaFiltro filtro, Query query) {
		if (filtro.getSituacao() != null
			&& SituacaoAgendamentoFiltro.COD_SITUC_A_REAGENDAR != filtro.getSituacao() 
			&& SituacaoAgendamentoFiltro.COD_SITUC_PENDENTE != filtro.getSituacao() 
			&& SituacaoAgendamentoFiltro.COD_SITUC_REALIZADA != filtro.getSituacao()) {
				
			query.setParameter("cdSitucAgend", filtro.getSituacao().toString());
		}

		if (isNotBlank(filtro.getVoucher())) {
			query.setParameter("cdVouch", filtro.getVoucher().trim().toUpperCase());
		}

		if (filtro.getDataDe() != null) {
			query.setParameter("dataDe", filtro.getDataDe(), TemporalType.DATE);
		}

		if (filtro.getDataAte() != null) {
			query.setParameter("dataAte", filtro.getDataAte(), TemporalType.DATE);
		}

		this.carregarFiltroAgendar(filtro, query);
	}

	private StringBuilder getFromRecuperarListaItemAgendada(VistoriaFiltro filtro) {
		String andInSql = "AND S.cdSitucAgmto IN ('";
		StringBuilder sqlStatus = new StringBuilder()
				.append("SELECT MAX (S2.idStatuAgmto) ")
				.append("FROM StatusAgendamentoAgrupamento S2 ")
				.append("WHERE S2.cdVouch = S.cdVouch ");
		
		StringBuilder sqlFrom = new StringBuilder();
		
		sqlFrom.append("FROM AgendamentoVistoriaPrevia Avp, VistoriaPreviaObrigatoria Vpo, StatusAgendamentoAgrupamento S ")
		.append("WHERE Avp.cdVouch = Vpo.cdVouch AND Avp.cdVouch = S.cdVouch ");

		if (filtro.getSituacao() != null) {

			if (SituacaoAgendamentoFiltro.COD_SITUC_A_REAGENDAR == filtro.getSituacao()) {
				sqlFrom.append(andInSql+ VFR + "', '" + CAN + "') ");
			
			} else if (SituacaoAgendamentoFiltro.COD_SITUC_PENDENTE == filtro.getSituacao()) {
				sqlFrom.append(andInSql + PEN + "', '" + RCB + "') ");
			
			} else if (SituacaoAgendamentoFiltro.COD_SITUC_REALIZADA == filtro.getSituacao()) {
				sqlFrom.append(andInSql + RLZ + "', '" + FTT + "', '" + FIM + "') ");
			
			} else {
				sqlFrom.append("AND S.cdSitucAgmto =:cdSitucAgend ");
			}
		}

		if (isNotBlank(filtro.getVoucher())) {
			sqlFrom.append("AND UPPER(Avp.cdVouch) = :cdVouch ");
			sqlStatus.append("AND UPPER(S2.cdVouch) = :cdVouch ");
		}

		if (filtro.getDataDe() != null) {
			sqlFrom.append("AND Avp.dtUltmaAlter >= :dataDe ");
		}

		if (filtro.getDataAte() != null) {
			sqlFrom.append("AND Avp.dtUltmaAlter <= :dataAte ");
		}

		sqlStatus.append("GROUP BY S2.cdVouch ");

		this.carregarFiltroAgendar(filtro, sqlFrom, "Vpo");

		sqlFrom.append("AND S.idStatuAgmto in (" + sqlStatus + ") ");

		if (filtro.getEndosso() != null || filtro.getNegocio() != null || isNotBlank(filtro.getVoucher())
				|| filtro.getCalculo() != null || filtro.getItem() != null || filtro.isFrota()) {

			sqlFrom.append("AND Vpo.idVspreObgta = ( SELECT MAX ( MaiorPre.idVspreObgta ) ")
			.append("FROM VistoriaPreviaObrigatoria MaiorPre ")
			.append("WHERE MaiorPre.cdCrtorCia = Vpo.cdCrtorCia ")
			.append("AND MaiorPre.cdPlacaVeicu = Vpo.cdPlacaVeicu ")
			.append("AND MaiorPre.cdChassiVeicu = Vpo.cdChassiVeicu ");

			if (isNotBlank(filtro.getVoucher())) {
				sqlFrom.append("AND MaiorPre.cdVouch = Vpo.cdVouch ");
			} else {
				// * Caso encontre um pre agendamento sem proposta e sem voucher desconsidera o
				// mesmo para agendamentos
				sqlFrom.append("AND NOT (MaiorPre.cdSistmOrigm IN ( 14, 15 ) AND MaiorPre.cdVouch IS NULL) ");
			}

			sqlFrom.append(") ");
		}
		
		return sqlFrom;
	}

	/* TUNNING NA CONSULTA DE VOUCHER A AGENDAR! */
	private void carregarFiltroAgendar(VistoriaFiltro filtro, final StringBuilder sql, String aliasTabela) {
         String and = " AND ";
		if (sql != null) {
			if (filtro.getCorretor() != null) {
				sql.append(and).append(aliasTabela).append(".cdCrtorCia = :codCorretor ");
			}

			if (filtro.isFrota()) {
				sql.append(and + " (").append(aliasTabela).append(".tpVeicu = :tpVeicu OR ").append(aliasTabela)
						.append(".icFrota = 'S' )");
			} else if (filtro.getTipo() != null) {
				sql.append(and).append(aliasTabela).append(".tpVeicu = :tpVeicu ");
			}

			if (isNotBlank(filtro.getCpfCnpj())) {
				sql.append(and).append(aliasTabela).append(".nrCpfCnpjClien = :nrCpfCnpjClien ");
			}

			if (isNotBlank(filtro.getPlaca())) {
				sql.append(and + " UPPER(").append(aliasTabela).append(".cdPlacaVeicu) = :cdPlacaVeicu ");
			}

			if (isNotBlank(filtro.getChassi())) {
				sql.append(and+ " UPPER(").append(aliasTabela).append(".cdChassiVeicu) = :cdChassiVeicu ");
			}

			if (filtro.getEndosso() != null) {
				sql.append(and).append(aliasTabela).append(".cdEndos = :cdEndos ");
			}

			if (filtro.getNegocio() != null) {
				sql.append(and).append(aliasTabela).append(".cdNgoco = :cdNgoco ");
			}

			if (filtro.getCalculo() != null) {
				sql.append(and).append(aliasTabela).append(".nrCallo = :nrCallo ");
			}

			if (filtro.getItem() != null) {
				sql.append(and).append(aliasTabela).append(".nrItseg = :nrItseg ");
			}
		}
	}

	/* TUNNING NA CONSULTA DE VOUCHER A AGENDAR! */
	private void carregarFiltroAgendar(VistoriaFiltro filtro, Query query) {
		if (query != null) {
			if (filtro.getCorretor() != null) {
				query.setParameter("codCorretor", filtro.getCorretor());
			}

			if (filtro.getTipo() != null) {
				query.setParameter("tpVeicu", filtro.getTipo().toString());
			}

			if (isNotBlank(filtro.getCpfCnpj())) {
				query.setParameter("nrCpfCnpjClien", filtro.getCpfCnpj().trim());
			}

			if (isNotBlank(filtro.getPlaca())) {
				query.setParameter("cdPlacaVeicu", filtro.getPlaca().trim().toUpperCase());
			}

			if (isNotBlank(filtro.getChassi())) {
				query.setParameter("cdChassiVeicu", filtro.getChassi().trim().toUpperCase());
			}

			if (filtro.getEndosso() != null) {
				query.setParameter("cdEndos", filtro.getEndosso());
			}

			if (filtro.getNegocio() != null) {
				query.setParameter("cdNgoco", filtro.getNegocio());
			}

			if (filtro.getCalculo() != null) {
				query.setParameter("nrCallo", filtro.getCalculo());
			}

			if (filtro.getItem() != null) {
				query.setParameter("nrItseg", filtro.getItem());
			}
		}
	}

	@Override
	public List<String> obterVoucherMesmoVeiculo(String cdPlacaVeicu, String cdChassiVeicu, Long cdCrtorCia, Boolean placaImpeditiva, int qtidadeDiasRetrocessoAproveitaPreAg) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct vpo.cdVouch, vpo.dtUltmaAlter ");
		sql.append("from VistoriaPreviaObrigatoria vpo, AgendamentoVistoriaPrevia ag ");
		sql.append("where vpo.cdCrtorCia = :cdCrtorCia ");
		sql.append("and vpo.cdVouch = ag.cdVouch ");
		sql.append("and ag.dtUltmaAlter >= :dataInicialLimite ");

		if (isNotBlank(cdChassiVeicu)) {
			sql.append("and vpo.cdChassiVeicu = :cdChassiVeicu ");
		}

		if (isNotBlank(cdPlacaVeicu)) {

			if (!placaImpeditiva) {
				sql.append("and vpo.cdPlacaVeicu = :cdPlacaVeicu ");
			} else {
				// caso a placa seja impeditiva não pesquisa.
				return new ArrayList<>();
			}
		}

		sql.append("order by vpo.dtUltmaAlter desc");

		TypedQuery<Tuple> query = em.createQuery(sql.toString(), Tuple.class);

		if (isNotBlank(cdChassiVeicu)) {
			query.setParameter("cdChassiVeicu",cdChassiVeicu);
		}

		if (!placaImpeditiva && isNotBlank(cdPlacaVeicu)) {
			query.setParameter("cdPlacaVeicu",cdPlacaVeicu);
		}
		query.setParameter("cdCrtorCia",cdCrtorCia);

		query.setParameter("dataInicialLimite",DateUtil.calculaNovaData(new Date(),0,0,-qtidadeDiasRetrocessoAproveitaPreAg));

		return query.getResultList().stream().map(t -> t.get(0, String.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void detach(VistoriaPreviaObrigatoria entity) {
		em.detach(entity);
	}
}
