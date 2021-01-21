package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.aceitacao.crud.model.AjusteItemSegurado;
import br.com.tokiomarine.seguradora.core.exception.DAOException;
import br.com.tokiomarine.seguradora.core.exception.ExceptionUtil;
import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.core.util.StringUtil;
import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.Veiculo;
import br.com.tokiomarine.seguradora.ssv.transacional.model.Apolice;
import br.com.tokiomarine.seguradora.ssv.transacional.model.DescricaoAcessorioItem;
import br.com.tokiomarine.seguradora.ssv.transacional.model.DescricaoItemSegurado;
import br.com.tokiomarine.seguradora.ssv.transacional.model.Endosso;
import br.com.tokiomarine.seguradora.ssv.transacional.model.ItemSegurado;
import br.com.tokiomarine.seguradora.ssv.transacional.model.PlacaImpeditiva;
import br.com.tokiomarine.seguradora.ssv.transacional.model.ValorTaxaCoberturaItem;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParecerTecnicoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.StatusAgendamentoAgrupamento;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesRegrasVP;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilJava;

@Repository
public class RegrasVPDAO {

	@PersistenceContext
	private EntityManager em;

	
	
	@SuppressWarnings("unchecked")
	public List<ValorTaxaCoberturaItem> obtemValorTaxaCobertura(Long numeroItem,String tipoHistoricoItem) {

		try {

			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT vt ");
			sql.append(" FROM ValorTaxaCoberturaItem vt ");
			sql.append(" WHERE vt.numItemSegurado = :numeroItem ");
			sql.append(" AND vt.tipoHistorico = :tipoHistoricoItem ORDER BY vt.codCobertura ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("numeroItem",numeroItem);
			query.setParameter("tipoHistoricoItem",tipoHistoricoItem);

			return query.getResultList();

		} catch (NoResultException nre) {
			return null;

		}
	}

	public DescricaoItemSegurado obtemDescricaoItemSegurado(Long codigoCaracteristicaItemSegurado,Long numeroItem,String tipoHistorico) {
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT dit FROM DescricaoItemSegurado dit ");
			sql.append(" WHERE dit.codCaracteristicaItemSegurado = :codigoCaracteristicaItemSegurado ");
			sql.append(" AND dit.numItemSegurado = :numeroItem");
			sql.append(" AND dit.tipoHistorico = :tipoHistorico");

			Query query = em.createQuery(sql.toString());

			query.setParameter("codigoCaracteristicaItemSegurado",codigoCaracteristicaItemSegurado);
			query.setParameter("numeroItem",numeroItem);
			query.setParameter("tipoHistorico",tipoHistorico);

			return (DescricaoItemSegurado) query.getSingleResult();

		} catch (NoResultException nre) {
			return null;

		}
	}

	/**
	 * Obtem a data de referencia on-line.
	 *
	 * @return Data de referencia on-line.
	 */	
	public Date obtemDataMovimentoEmissao() {
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT cd.dtReferOnlin ");
			sql.append(" FROM ParametroControleData cd ");
			sql.append(" WHERE cd.cdIdetcProcm = :historico ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("historico",0L);

			return (Date) query.getSingleResult();

		} catch (NoResultException nre) {
			return null;

		}
	}

	/**
	 * Obtem parâmetro de vistoria previa.
	 *
	 * @param dataOnline: Data referencia online.
	 * @param codModProd: Módulo do produto.
	 * @return ParametroVistoriaPrevia
	 */	
	public ParametroVistoriaPrevia obtemParametroVistoria(Date dataInicioVigencia,Long codModProd) {

		try {
			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT pv FROM ParametroVistoriaPrevia pv ");
			sql.append(" WHERE :dataInicioVigencia BETWEEN pv.dtInicoVigen AND pv.dtFimVigen ");
			sql.append(" AND   pv.cdMdupr = :modProd ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("dataInicioVigencia",dataInicioVigencia);
			query.setParameter("modProd",codModProd);

			return (ParametroVistoriaPrevia) query.getSingleResult();

		} catch (NoResultException nre) {
			return null;

		}
	}

	/**
	 * Obtem dados da apolice, com base nos parâmetros abaixo
	 *
	 * @param codigoSeguradora: Código seguradora.
	 * @param codigoRamo: Código ramo;
	 * @param descricaoValor: Código apolice susep.
	 * @param tipoHistoricoNegocio: Tipo histórico negócio.
	 * @return Apolice
	 */
	
	public Apolice consultarApolice(Long codigoSeguradora,Long[] codigoRamos,Long descricaoValor,String tipoHistoricoNegocio) {
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT a ");
			sql.append(" FROM Apolice a ");
			sql.append(" WHERE a.codApoliceSusep = :descricaoValor");
			sql.append(" AND a.codCompanhiaSeguradora = :codigoSeguradora ");
			sql.append(" AND a.codRamoSeguro in ( ");

			for (int i = 0 ; i < codigoRamos.length ; i++) {
				if (i == 0) {
					sql.append(codigoRamos[i]);
				} else {
					sql.append("," + codigoRamos[i]);
				}
			}

			sql.append(" ) ");
			sql.append(" AND a.tipoHistoricoNegocio = :tipoHistoricoNegocio ");
			Query query = em.createQuery(sql.toString());
			query.setParameter("descricaoValor",descricaoValor);
			query.setParameter("codigoSeguradora",codigoSeguradora);
			query.setParameter("tipoHistoricoNegocio",tipoHistoricoNegocio);

			return (Apolice) query.getSingleResult();

		} catch (NoResultException nre) {
			return null;

		}
	}

	/**
	 * Obtem dados da apolice, com base nos parâmetros abaixo
	 *
	 * @param codigoSeguradora: Código seguradora.
	 * @param codigoRamo: Código ramo;
	 * @param descricaoValor: Código apolice susep.
	 * @param tipoHistoricoNegocio: Tipo histórico negócio.
	 * @return Apolice
	 */
	
	public Apolice consultarApolice(Long codigoSeguradora,Long codigoRamo,Long descricaoValor,String tipoHistoricoNegocio) {
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT a ");
			sql.append(" FROM Apolice a ");
			sql.append(" WHERE a.codApoliceSusep = :descricaoValor");
			sql.append(" AND a.codCompanhiaSeguradora = :codigoSeguradora ");
			sql.append(" AND a.codRamoSeguro = :codigoRamo ");
			sql.append(" AND a.tipoHistoricoNegocio = :tipoHistoricoNegocio ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("descricaoValor",descricaoValor);
			query.setParameter("codigoSeguradora",codigoSeguradora);
			query.setParameter("codigoRamo",codigoRamo);
			query.setParameter("tipoHistoricoNegocio",tipoHistoricoNegocio);

			return (Apolice) query.getSingleResult();

		} catch (NoResultException nre) {
			return null;

		}
	}

	/**
	 * Obtem item segurado, com base no parâmetro abaixo.
	 *
	 * @param codigoApolice : Código apolice.
	 * @param tipoHistoricoNegocio : Tipo histórico negócio
	 * @param tipoHistoricoItem : Tipo histórico item
	 * @return List item segurado
	 */
	
	public ItemSegurado obtemItemSeguradoPorCodigoApolice(Long codigoApolice,String tipoHistoricoItem) {
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT it ");
			sql.append(" FROM ItemSegurado it ");
			sql.append(" where it.codApolice = :codigoApolice ");
			sql.append(" AND it.tipoHistorico = :tipoHistorico ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("codigoApolice",codigoApolice);
			query.setParameter("tipoHistorico",tipoHistoricoItem);

			return (ItemSegurado) query.getSingleResult();

		} catch (NoResultException nre) {
			return null;

		}
	}

	/**
	 * Obtem item segurado, com base no parâmetro abaixo.
	 * @param codigoApolice
	 * @param numeroItem
	 * @param tipoHistoricoItem
	 * @return ItemSegurado
	 */
	
	public ItemSegurado obtemItemSeguradoPorCodigoApoliceItem(Long codigoApolice, Long numeroItem, String tipoHistoricoItem) {
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT it ");
			sql.append(" FROM ItemSegurado it ");
			sql.append(" where it.codApolice = :codigoApolice ");
			sql.append(" AND it.tipoHistorico = :tipoHistorico ");

			if(numeroItem !=null){
				sql.append(" AND it.numItemSegurado = :item ");
			}

			Query query = em.createQuery(sql.toString());
			query.setParameter("codigoApolice",codigoApolice);
			query.setParameter("tipoHistorico",tipoHistoricoItem);

			if(numeroItem !=null){
				query.setParameter("item",numeroItem);
			}

			return (ItemSegurado) query.getSingleResult();

		} catch (NoResultException nre) {
			return null;

		}
	}

	
	@SuppressWarnings("unchecked")
	public List<DescricaoAcessorioItem> consultarContratacaoAcessorio(Long numItem,String tipoHistorico) {
		try {
			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT a ");
			sql.append(" FROM   DescricaoAcessorioItem a ");
			sql.append(" WHERE  a.numItemSegurado = :itemSegurado ");
			sql.append(" AND    a.tipoHistorico = :tpHistorico ");

			Query query = em.createQuery(sql.toString());

			query.setParameter("itemSegurado",numItem);
			query.setParameter("tpHistorico",tipoHistorico);

			return query.getResultList();

		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * Obtem informações do endosso, com base no parâmetro abaixo.
	 *
	 * @param codigoEndosso : Código endosso.
	 * @return Endosso.
	 */
	
	public Endosso getEndosso(Long codigoEndosso) {

		try {

			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT e ");
			sql.append(" FROM Endosso e ");
			sql.append(" WHERE e.codEndosso = :codEndosso ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("codEndosso",codigoEndosso);
			query.setMaxResults(1);

			return (Endosso) query.getSingleResult();

		} catch (NoResultException nre) {
			return null;

		}
	}

	
	public ItemSegurado obterItemPorEndosso(Long codEndosso) {

		StringBuffer sql = new StringBuffer();
		sql.append(" select i ");
		sql.append("   from ItemSegurado i ");
		sql.append("  where i.codEndosso = :codEndosso");

		Query query = em.createQuery(sql.toString());

		query.setParameter("codEndosso",codEndosso);

		try {
			return (ItemSegurado) query.getSingleResult();

		} catch (NoResultException e) {
			return null;

		}
	}

	
	public ItemSegurado obterItemPorEndossoItem(Long codEndosso, Long itemSegurado) {

		StringBuffer sql = new StringBuffer();
		sql.append(" select i ");
		sql.append("   from ItemSegurado i ");
		sql.append("  where i.codEndosso = :codEndosso");
		sql.append("  and i.numItemSegurado = :numItemSegurado");

		Query query = em.createQuery(sql.toString());

		query.setParameter("codEndosso",codEndosso);
		query.setParameter("numItemSegurado",itemSegurado);

		try {
			return (ItemSegurado) query.getSingleResult();

		} catch (NoResultException e) {
			return null;

		}
	}

	
	public Endosso obterEndosso(Long codEndosso) {

		StringBuffer sql = new StringBuffer();
		sql.append(" select e ");
		sql.append("   from Endosso e ");
		sql.append("  where e.codEndosso = :codEndosso");

		Query query = em.createQuery(sql.toString());

		query.setParameter("codEndosso",codEndosso);

		try {
			return (Endosso) query.getSingleResult();

		} catch (NoResultException e) {
			return null;

		}
	}

	
	public String obterFipe(Long codMarcaModelo,Date dataInicioVigencia,Long codFabricante,Long codCombustivel) {

		StringBuffer sql = new StringBuffer();

		sql.append(" Select distinct f.cdMarcaModelTabelVeicu ");
		sql.append("   From Veiculo v,  DeParaFipe f ");
		sql.append("  Where v.cdMarcaModel  = :cdMarcaModel ");
		sql.append("    and v.cdSitucCadtr  = 'FIN' ");
		sql.append("    and f.idVeicu = v.idVeicu ");
		sql.append("    and :dtInicoVigen between f.dtInicoVigen and f.dtFimVigen ");

		sql.append("    and v.cdFabrt = :cdFabricante ");
		sql.append("    and v.tpCmbst = :cdCombustivel ");

		Query query = em.createQuery(sql.toString());

		query.setParameter("cdMarcaModel",codMarcaModelo);
		query.setParameter("dtInicoVigen",dataInicioVigencia);

		query.setParameter("cdFabricante",codFabricante);
		query.setParameter("cdCombustivel",codCombustivel);

		try {
			return (String) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	@SuppressWarnings({"deprecation","unchecked"})
	public List<Long> obterItens() {

		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct i.numItemSegurado ");
		sql.append("   from ItemSegurado i, Negocio n, ModuloNegocio m ");
		sql.append("  where n.dataEmissaoNegocio > :dataEmissaoNegocio ");
		sql.append("    and i.codNegocio = n.codNegocio ");
		sql.append("    and m.codNegocio = n.codNegocio ");
		sql.append("    and m.codModuloProduto in (7, 9, 20, 21, 22) ");
		sql.append("    and n.codSituacaoNegocio <> 'COT' ");

		Query query = em.createQuery(sql.toString());

		query.setParameter("dataEmissaoNegocio",new Date("01/01/2012"));

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	@SuppressWarnings({"deprecation","unchecked"})
	public List<Long> obterEndossos() {

		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct c.codEndosso ");
		sql.append("   from ItemSegurado i, ControleEndosso c ");
		sql.append("  where c.cdSitucEndos = 'END' ");
		sql.append("    and c.cdCatgoEndos in ('V', 'S', 'G') ");
		sql.append("    and c.dataEmissaoEndosso >= :dataEmissaoEndosso ");
		sql.append("    and i.codEndosso = c.codEndosso");
		sql.append("    and i.codModuloProduto in (7, 9, 20, 21, 22) ");

		Query query = em.createQuery(sql.toString());

		query.setParameter("dataEmissaoEndosso",new Date("10/01/2011"));

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	@SuppressWarnings("unchecked")
	public VistoriaPreviaObrigatoria obterPreAgendamentoSemPropostaPorVeiculo(String cdPlacaVeicu,String cdChassiVeicu,Long cdCrtorCia) {

		Boolean placaImpeditiva = this.isPlacaImpeditiva(cdPlacaVeicu);

		StringBuffer sql = new StringBuffer();

		sql.append(" select distinct vpo ");
		sql.append("   from VistoriaPreviaObrigatoria vpo ");
		sql.append("  where vpo.cdCrtorCia = :cdCrtorCia ");
		sql.append("    and (vpo.cdSistmOrigm = :agtoSemPropostaTMS or vpo.cdSistmOrigm = :agtoSemPropostaTMB) ");

		if (UtilJava.trueVar(cdChassiVeicu)) {
			sql.append("and vpo.cdChassiVeicu = :cdChassiVeicu ");
		}

		if (UtilJava.trueVar(cdPlacaVeicu)) {

			if (!placaImpeditiva) {
				sql.append("and vpo.cdPlacaVeicu = :cdPlacaVeicu ");
			} else {
				// caso a placa seja impeditiva não pesquisa.
				return null;
			}
		}

		sql.append(" order by vpo.dtUltmaAlter desc");

		Query query = em.createQuery(sql.toString());

		if (UtilJava.trueVar(cdChassiVeicu)) {
			query.setParameter("cdChassiVeicu",cdChassiVeicu);
		}

		if (!placaImpeditiva && UtilJava.trueVar(cdPlacaVeicu)) {
			query.setParameter("cdPlacaVeicu",cdPlacaVeicu);
		}
		query.setParameter("cdCrtorCia",cdCrtorCia);
		query.setParameter("agtoSemPropostaTMS", ConstantesRegrasVP.AgtoSemPropostaTMS);
		query.setParameter("agtoSemPropostaTMB", ConstantesRegrasVP.AgtoSemPropostaTMB);

		try {
			List<VistoriaPreviaObrigatoria> listaVPO = query.getResultList();
			if (listaVPO == null || listaVPO.isEmpty()) { return null; }

			return listaVPO.get(0);

		} catch (RuntimeException e) {
			throw new DAOException("Erro ao executar query obterPreAgendamentoSemPropostaPorVeiculo: " + ExceptionUtil.getRootMessage(e));
		}
	}
		
	public VistoriaPreviaObrigatoria obterPreAgendamentoPorChave(Long codSistema,Long...codigo) {

		StringBuffer sql = new StringBuffer();
		sql.append(" select vpo ");
		sql.append("   from VistoriaPreviaObrigatoria vpo ");
		sql.append("  where vpo.idVspreObgta = vpo.idVspreObgta ");

		switch (codSistema.intValue()) {

		// --------------------------------------------------------------------------------------------------
			case 1: // KCW
			case 9: // Multicalculo
			case 13:/* TrasmissaoKCW */
			case 17:/* AutoCompara(Santander) */
			case 20:/* EmissaoCotador */
			case 21:/* TrasmissaoCotador */
			case 22:/* WSCotador */
				sql.append(" and vpo.nrCallo = :codigo0 ");
				break;
			// --------------------------------------------------------------------------------------------------
			case 5: /* RecepSSV */
				sql.append(" and (vpo.nrCallo = :codigo0 or vpo.nrItseg = :codigo1) ");
				break;
			// --------------------------------------------------------------------------------------------------
			case 4:  // EmissaoSSV
			case 8:  // RestrBonus
			case 24: // Gera Retrição
				sql.append(" and vpo.nrItseg = :codigo0 ");
				if (codigo.length > 1) {
					sql.append(" and vpo.cdEndos = :codigo1 ");
				}
				break;
			case 10: /* LiberacaoProposta */
				sql.append(" and vpo.nrItseg = :codigo0 ");
				break;
			// --------------------------------------------------------------------------------------------------
			case 6: // EndossoSSV
			case 7: // EndossoWEB
			case 11: // LiberacaoEndossoSSV
			case 12: /* LiberacaoEndossoWEB */
				sql.append(" and vpo.cdEndos = :codigo0 ");
				break;

			case 25: /* Endosso CTF */
				sql.append("and vpo.nrItseg = :codigo0 and vpo.cdEndos = :codigo1 ");
				break;
			// --------------------------------------------------------------------------------------------------
			case 3: /* EndossoPlat */
			case 2: /* EmissaoPlat */
			case 23: /* CTF */
				sql.append(" and vpo.cdNgoco = :codigo0 and vpo.nrItseg = :codigo1 ");
				break;
		// --------------------------------------------------------------------------------------------------

		}

		sql.append(" order by vpo.dtUltmaAlter desc ");

		Query query = em.createQuery(sql.toString());

		for (int i = 0 ; i < codigo.length ; i++) {
			query.setParameter("codigo" + i,codigo[i]);
		}

		try {
			return (VistoriaPreviaObrigatoria) query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (NonUniqueResultException ne){
			// Caso ocorrer erro, devido exisitir um ou mais pre-agendamento(s) com mais de 20 dias retorna
			// o ultimo da base de pré-agendamento, conforme a data.
			ne.printStackTrace();
			return (VistoriaPreviaObrigatoria) query.getResultList().get(0);
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<VistoriaPreviaObrigatoria> obterPreAgendamentos() {

		StringBuffer sql = new StringBuffer();
		sql.append(" select vpo ");
		sql.append("   from VistoriaPreviaObrigatoria vpo ");

		Query query = em.createQuery(sql.toString());

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

//	
//	@SuppressWarnings("unchecked")
//	public List<String> obterVoucherMesmoVeiculo(String cdPlacaVeicu,String cdChassiVeicu,Long cdCrtorCia) {
//
//		Boolean placaImpeditiva = this.isPlacaImpeditiva(cdPlacaVeicu);
//
//		StringBuffer sql = new StringBuffer();
//		sql.append(" select distinct vpo.cdVouch ");
//		sql.append("   from VistoriaPreviaObrigatoria vpo, AgendamentoVistoriaPrevia ag ");
//		sql.append("   where vpo.cdCrtorCia = :cdCrtorCia ");
//		sql.append("   and vpo.cdVouch = ag.cdVouch ");
//		sql.append("   and ag.dtUltmaAlter >= :dataInicialLimite ");
//
//		if (UtilJava.trueVar(cdChassiVeicu)) {
//			sql.append("and vpo.cdChassiVeicu = :cdChassiVeicu ");
//		}
//
//		if (UtilJava.trueVar(cdPlacaVeicu)) {
//
//			if (!placaImpeditiva) {
//				sql.append("and vpo.cdPlacaVeicu = :cdPlacaVeicu ");
//			} else {
//				// caso a placa seja impeditiva não pesquisa.
//				return new ArrayList<String>();
//			}
//		}
//
//		sql.append(" order by vpo.dtUltmaAlter desc");
//
//		Query query = em.createQuery(sql.toString());
//
//		if (UtilJava.trueVar(cdChassiVeicu)) {
//			query.setParameter("cdChassiVeicu",cdChassiVeicu);
//		}
//
//		if (!placaImpeditiva && UtilJava.trueVar(cdPlacaVeicu)) {
//			query.setParameter("cdPlacaVeicu",cdPlacaVeicu);
//		}
//		query.setParameter("cdCrtorCia",cdCrtorCia);
//
//		int qtidadeDiasRetrocessoAproveitaPreAg = agendamentoDAO.obterQtdDiasVencimentoAgendamentoPendente();
//
//		query.setParameter("dataInicialLimite",DateUtil.calculaNovaData(new Date(),0,0,-qtidadeDiasRetrocessoAproveitaPreAg));
//
//		try {
//			return query.getResultList();
//		} catch (RuntimeException e) {
//			throw new DAOException("Erro ao executar query obterVoucherMesmoVeiculo: " + ExceptionUtil.getRootMessage(e));
//		}
//	}

	
	@SuppressWarnings("unchecked")
	public boolean isPlacaImpeditiva(String codPlacaVeiculo) {

		StringBuffer sql = new StringBuffer();

		sql.append(" select p ");
		sql.append("   from PlacaImpeditiva p ");
		sql.append("  where p.codPlacaVeiculo = :codPlacaVeiculo ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("codPlacaVeiculo",codPlacaVeiculo);

		try {
			List<PlacaImpeditiva> listaPlacas = query.getResultList();
			if (listaPlacas != null && listaPlacas.size() > 0) { return true; }
		} catch (RuntimeException e) {
			throw new DAOException("Erro ao executar query isPlacaImpeditiva: " + ExceptionUtil.getRootMessage(e));
		}
		return false;
	}

	
	public boolean negocioEstaNaGrade(String codLaudoVistoriaPrevia) {

		try {

			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT N ");
			sql.append("   FROM ItemSegurado I, ");
			sql.append("        Proposta N ");
			sql.append("  WHERE I.codLaudoVistoriaPrevia = :codLaudoVistoriaPrevia ");
			sql.append("    AND I.tipoHistorico = '0' ");
			sql.append("    AND N.cdPpota = I.codNegocio ");
			sql.append("    AND N.tpPpota = 'N' ");
			sql.append("    AND N.cdStatuPpota = 'GRD' ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("codLaudoVistoriaPrevia",codLaudoVistoriaPrevia);

			query.getSingleResult();

			return true;

		} catch (NoResultException nre) {
			return false;
		}
	}

	
	public boolean endossoEstaNaGrade(String codLaudoVistoriaPrevia) {

		try {

			StringBuffer sql = new StringBuffer();

			sql.append(" SELECT C ");
			sql.append("   FROM ItemSegurado I, ");
			sql.append("        ControleEndosso C ");
			sql.append("  WHERE I.codLaudoVistoriaPrevia = :codLaudoVistoriaPrevia ");
			sql.append("    AND C.codEndosso = I.codEndosso ");
			sql.append("    AND C.cdSitucEndos = 'GRD' ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("codLaudoVistoriaPrevia",codLaudoVistoriaPrevia);

			query.getSingleResult();

			return true;

		} catch (NoResultException nre) {
			return false;
		}
	}

	/*
	 * TUINNING!
	 * @SuppressWarnings("unchecked")
	 * public List<AgendamentoBanco> obterRetiradaAgendamentos(Long cdEmpreVstra, int qtdRegistroAgendamento) {
	 * StringBuffer sql = new StringBuffer();
	 * sql.append(" SELECT new " + AgendamentoBanco.class.getCanonicalName() + "(");
	 * sql.append("        V, ");
	 * sql.append("        A, ");
	 * sql.append("        S) ");
	 * sql.append("   FROM VistoriaPreviaObrigatoria V, ");
	 * sql.append("        AgendamentoVistoriaPrevia A, ");
	 * sql.append("        StatusAgendamentoAgrupamento S ");
	 * sql.append("  WHERE A.cdAgrmtVspre = :cdEmpreVstra ");
	 * sql.append("    AND A.cdVouch = S.cdVouch ");
	 * sql.append("    AND V.cdVouch = A.cdVouch ");
	 * sql.append("    AND S.cdSitucAgmto = 'PEN'");
	 * sql.append("    AND S.idStatuAgmto = ( SELECT MAX ( Maior.idStatuAgmto ) ");
	 * sql.append("                             FROM StatusAgendamentoAgrupamento Maior ");
	 * sql.append("                            WHERE Maior.cdVouch = S.cdVouch) ");
	 * sql.append("    AND V.idVspreObgta = ( SELECT MAX ( MaiorPre.idVspreObgta ) ");
	 * sql.append("                             FROM VistoriaPreviaObrigatoria MaiorPre ");
	 * sql.append("                            WHERE MaiorPre.cdVouch = V.cdVouch) ");
	 * Query query = em.createQuery(sql.toString());
	 * query.setMaxResults(qtdRegistroAgendamento);
	 * query.setParameter("cdEmpreVstra",cdEmpreVstra);
	 * try {
	 * return (List<AgendamentoBanco>) query.getResultList();
	 * } catch (NoResultException e) {
	 * return null;
	 * }
	 * }
	 */
	@SuppressWarnings("unchecked")
//	public List<RetiradaAgendamento> obterRetiradaAgendamentos(final Long cdEmpreVstra, final int qtdRegistroAgendamento) {
//		try {						
//			StringBuffer sql = new StringBuffer();			
//			sql.append(" SELECT V.CD_VOUCH,");
//			sql.append(" V.NM_CLIEN,");
//			sql.append(" V.NR_CPF_CNPJ_CLIEN,");
//			sql.append(" V.CD_CRTOR_CIA,");
//			sql.append(" V.TP_VEICU,");
//			sql.append(" V.NM_FABRT,");
//			sql.append(" V.DS_MODEL_VEICU,");
//			sql.append(" V.CD_FIPE,");
//			sql.append(" V.CD_PLACA_VEICU,");
//			sql.append(" V.CD_CHASSI_VEICU,");
//			sql.append(" V.AA_FABRC,");
//			sql.append(" V.AA_MODEL,");
//			sql.append(" V.IC_VEICU_ZERO_KM,");
//			sql.append(" V.IC_CARRO,");
//			sql.append(" V.TP_CARRO,");
//			sql.append(" V.CD_ADPTO_EIXO,");
//			sql.append(" A.TP_VSPRE,");
//			sql.append(" A.CD_VOUCH as CD_VOUCH_AGENDAMENTO,");
//			sql.append(" A.CD_POSTO_VSPRE,");
//			sql.append(" A.CD_AGRMT_VSPRE,");
//			sql.append(" 'PEN'AS CD_SITUC_AGMTO");
//			sql.append(" FROM vpe0424_vspre_obgta V, vpe0425_agend_vspre A ");
//			sql.append(" WHERE A.Cd_Vouch in (select aa.cd_vouch ");
//			sql.append("                      from vpe0425_agend_vspre aa, VPE0437_STATU_AGMTO sa ");
//			sql.append("                   	  where aa.cd_agrmt_vspre = :cdEmpreVstra ");
//			sql.append("                      and sa.cd_vouch = aa.cd_vouch ");
//			sql.append("                      and aa.dt_ultma_alter >= ((select pc.dt_refer_onlin from ssv4213_param_contr_data pc where pc.cd_idetc_procm = 0) - 5)");
//			sql.append("                   	  group by aa.cd_vouch ");
//			sql.append("                      having count(*) < 2) ");
//		    sql.append(" AND V.CD_VOUCH = A.CD_VOUCH ");
//		    sql.append(" AND V.id_Vspre_Obgta = ");
//		    sql.append("					  (SELECT MAX(MaiorPre.id_Vspre_Obgta) ");
//		    sql.append("   					   FROM vpe0424_vspre_obgta MaiorPre ");
//		    sql.append("   					   WHERE MaiorPre.cd_Vouch = V.cd_Vouch	) ");
//		    sql.append(" AND ROWNUM <= :qtdRegistroAgendamento ");
//	
//			Query query = em.createNativeQuery(sql.toString(), RetiradaAgendamento.class);	
//			query.setParameter("cdEmpreVstra", cdEmpreVstra);
//			query.setParameter("qtdRegistroAgendamento", qtdRegistroAgendamento);
//					
//			return query.getResultList();
//			
//		} catch (NoResultException e) {
//			e.printStackTrace();
//			return null;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return null;
//		}
//	}
	
	public StatusAgendamentoAgrupamento obterStatusAgendamento(String cdVouch) {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT S ");
		sql.append("   FROM StatusAgendamentoAgrupamento S ");
		sql.append("  WHERE S.cdVouch = :cdVouch ");
		sql.append("    AND S.idStatuAgmto = (  SELECT MAX ( Maior.idStatuAgmto ) ");
		sql.append("                        		FROM StatusAgendamentoAgrupamento Maior ");
		sql.append("                       		   WHERE Maior.cdVouch = S.cdVouch ");
		sql.append("                    		   GROUP BY Maior.cdVouch ) ");

		Query query = em.createQuery(sql.toString());

		query.setParameter("cdVouch",cdVouch);

		try {
			return (StatusAgendamentoAgrupamento) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	@SuppressWarnings("unchecked")
	public boolean verificaVpDispensada(ItemSegurado itemSegurado) {

		try {

			StringBuffer sql = new StringBuffer();

			sql.append("  SELECT Rit.nrItseg ");
			sql.append("	FROM Proposta proposta, Restricao Rit ");
			sql.append("   WHERE proposta.cdPpota = :codigoProposta ");
			sql.append("     AND proposta.idPpota = Rit.idPpota ");
			sql.append("	 AND Rit.nrItseg = :nrItseg ");
			sql.append("	 AND Rit.tpRestr = :tpRestr ");
			sql.append("	 AND Rit.cdSitucRestr = :cdSitucRestr ");
			sql.append("	 AND Rit.tpLibec is not null "); // Caso possua motivo identifica uma dispensa de VP.

			Query query = em.createQuery(sql.toString());

			query.setParameter("codigoProposta",itemSegurado.getCodEndosso() !=null ? itemSegurado.getCodEndosso() : itemSegurado.getCodNegocio());
			query.setParameter("nrItseg",itemSegurado.getNumItemSegurado());
			query.setParameter("tpRestr","VIS");
			query.setParameter("cdSitucRestr","LIB");

			List<Long> ret = query.getResultList();
			if (ret == null || ret.isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (NoResultException e) {
			return false;
		} catch (RuntimeException e) {
			throw new DAOException("Erro ao executar query verificaVpDispensada: " + ExceptionUtil.getRootMessage(e));
		}
	}

	
	public String obterDescricaoVeiculo(Long codValorCaracteristica,Date dataRefer, Long codigoModuloProduto) {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT Vlcar.dsVaricInico ");
		sql.append("   FROM ValorCaracteristicaItemSegurado Vlcar, ValorCaracteristicaModuloProduto vmp ");
		sql.append("  WHERE ");
		sql.append("	vmp.cdVlcarItseg = Vlcar.cdVlcarItseg ");
		sql.append("	AND vmp.sqVlcarItseg = Vlcar.sqVlcarItseg ");
		sql.append("    AND Vlcar.cdVlcarItseg = :codValorCaracteristica ");
		sql.append("    AND vmp.cdMdupr = :moduloProduto ");
		sql.append("    AND :dataRefer BETWEEN vmp.dtInicoVigen AND vmp.dtFimVigen ");
		sql.append("    AND vmp.dtFimVigen <> :dataRegistroAguardandoEfetivacao ");

		Query query = em.createQuery(sql.toString());

		query.setParameter("moduloProduto",codigoModuloProduto);
		query.setParameter("codValorCaracteristica",codValorCaracteristica);
		query.setParameter("dataRefer",dataRefer);
		query.setParameter("dataRegistroAguardandoEfetivacao",DateUtil.DATA_REGISTRO_AGUARDANDO_EFETIVACAO);

		try {
			return (String) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Recupera uma lista de ParecerTecnicoLaudoVistoria do veiculo para os laudos Sujeito Analise/Recusado a mais de 10 dias
	 *
	 * @param idVspreObgta
	 * @param codSituacaoLaudo
	 * @return List<ParecerTecnicoLaudoVistoria>
	 */
	
//	@SuppressWarnings("unchecked")
//	public List<ParecerTecnicoLaudoVistoria> verificarParecerLaudoVeiculo(Long idVspreObgta,String codSituacaoLaudo, Integer qtDiasPesquisaLaudo) {
//
//		StringBuffer sql = new StringBuffer();
//
//		sql.append("    SELECT new ");
//		sql.append(ParecerTecnicoLaudoVistoria.class.getCanonicalName());
//		sql.append("        	 (  ");
//		sql.append("            ptvp.cdPatecVspre, ");
//		sql.append("            ptvp.dsPatecVspre, ");
//		sql.append("            ptvp.dsInforRecusAnalis, ");
//		sql.append("            Laudo.cdSitucVspre ");
//		sql.append("          ) ");
//		sql.append("     FROM VistoriaPreviaObrigatoria Vpo, ");
//		sql.append("          VeiculoVistoriaPrevia Vvp, ");
//		sql.append("          LaudoVistoriaPrevia Laudo, ");
//		sql.append("          ParecerTecnicoLaudoVistoriaPrevia ptlvp, ");
//		sql.append("          ParecerTecnicoVistoriaPrevia ptvp ");
//		sql.append("    WHERE Vpo.cdChassiVeicu = Vvp.cdChassiVeicu ");
//		sql.append("      AND Vpo.cdPlacaVeicu = Vvp.cdPlacaVeicu ");
//		sql.append("      AND Vpo.idVspreObgta = :idVspreObgta ");
//		sql.append("      AND Vpo.cdCrtorCia = Laudo.cdCrtorSegur ");
//		sql.append("      AND Laudo.cdLvpre = Vvp.cdLvpre ");
//
//		if (!UtilJava.trueVar(codSituacaoLaudo)) {
//			// *Alterado em 10/12/2012 (Solicitante Sergio Avena)
//			// sql.append("      AND Laudo.cdSitucVspre IN ( 'S', 'R', 'A' ) ");
//			sql.append("      AND Laudo.cdSitucVspre IN ( 'S', 'R') ");
//		} else {
//			sql.append("      AND Laudo.cdSitucVspre IN (:codSituacaoLaudo) ");
//		}
//
//		sql.append("      AND ptlvp.cdLvpre = Laudo.cdLvpre ");
//		sql.append("      AND Laudo.dtVspre >= :dataMinima ");
//		sql.append("      AND ptvp.cdPatecVspre = ptlvp.cdPatecVspre ");
//		sql.append(" ORDER BY Laudo.dtVspre DESC");
//
//		Query query = em.createQuery(sql.toString());
//
//		query.setParameter("idVspreObgta",idVspreObgta);
//		query.setParameter("dataMinima",DateUtil.calculaNovaData(new Date(),0,0, -qtDiasPesquisaLaudo));
//		if (UtilJava.trueVar(codSituacaoLaudo)) {
//			query.setParameter("codSituacaoLaudo",codSituacaoLaudo);
//		}
//
//		try {
//			return query.getResultList();
//		} catch (RuntimeException e) {
//			throw new DAOException("Erro ao executar query verificaLaudoRecusavel: " + ExceptionUtil.getRootMessage(e));
//		}
//	}

	/**
	 * Pesquisa laudo com parecer recusado e lista de parecer impeditivos para agendamento.
	 * @param codigoChassi
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ParecerTecnicoVistoriaPrevia> verificarParecerLaudoVeiculoImpeditivo(String codigoChassi){

		StringBuffer sql = new StringBuffer();

        sql.append(" select ptvp ");
		sql.append("     FROM ");
		sql.append("          VeiculoVistoriaPrevia Vvp, ");
		sql.append("          LaudoVistoriaPrevia Laudo, ");
		sql.append("          ParecerTecnicoLaudoVistoriaPrevia ptlvp, ");
		sql.append("          ParecerTecnicoVistoriaPrevia ptvp ");
		sql.append("    WHERE Vvp.cdChassiVeicu = :codigoChassi ");
		sql.append("      AND Laudo.cdLvpre = Vvp.cdLvpre ");
		sql.append("      AND Laudo.cdSitucVspre = 'R' ");
		sql.append("      AND ptlvp.cdLvpre = Laudo.cdLvpre ");
		sql.append("      AND ptvp.cdPatecVspre = ptlvp.cdPatecVspre ");
		sql.append(" ORDER BY Laudo.dtVspre DESC ");

		Query query = em.createQuery(sql.toString());

		query.setParameter("codigoChassi",codigoChassi);
		try {
			return query.getResultList();
		} catch (RuntimeException e) {
			throw new DAOException("Erro ao executar query verificarParecerLaudoVeiculoImpeditivo: " + ExceptionUtil.getRootMessage(e));
		}
	}

	
	public String obterVPKCWRegraAntiga(Long codNegocio) {

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT Ir.icVstri ");
		sql.append("  FROM ItemRecepcaoEletronica Ir, ");
		sql.append("       Negocio N ");
		sql.append(" WHERE N.codNegocio = :codNegocio ");
		sql.append("   AND N.idPpotaContr = Ir.idPpotaContr ");

		Query query = em.createQuery(sql.toString());

		query.setParameter("codNegocio",codNegocio);

		try {
			return (String) query.getSingleResult();
		} catch (NoResultException e) {

			sql = new StringBuffer();

			sql.append("SELECT HIr.icVstri ");
			sql.append("  FROM HistoricoItemRecepcaoEletronica HIr, ");
			sql.append("       Negocio N ");
			sql.append(" WHERE N.codNegocio = :codNegocio ");
			sql.append("   AND N.idPpotaContr = HIr.idPpotaContr ");

			query = em.createQuery(sql.toString());

			query.setParameter("codNegocio",codNegocio);
			try {
				return (String) query.getSingleResult();
			} catch (RuntimeException ex) {
				throw new DAOException("Erro ao executar query obterVPKCWRegraAntiga: " + ExceptionUtil.getRootMessage(ex));
			}

		} catch (RuntimeException e) {
			throw new DAOException("Erro ao executar query obterVPKCWRegraAntiga: " + ExceptionUtil.getRootMessage(e));
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<AjusteItemSegurado> obterAjustesItemSegurado(Long idRestricaoAjuste,String situacaoAjuste) {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT a ");
		sql.append("  FROM AjusteItemSegurado a ");
		sql.append(" WHERE a.idRestr = :idRestricao ");

		if (!StringUtil.isEmpty(situacaoAjuste)) {

			sql.append("   AND a.cdSitucAjust = :situacaoAjuste");
		}

		Query query = em.createQuery(sql.toString());
		query.setParameter("idRestricao",idRestricaoAjuste);

		if (!StringUtil.isEmpty(situacaoAjuste)) {

			query.setParameter("situacaoAjuste",situacaoAjuste);
		}


		return query.getResultList();
	}

	/**
	 * Retorna o valor medio de cotação.
	 *
	 * @param codigoFabricante
	 * @param codigoModelo
	 * @param tipoCombustivel
	 * @param anoModelo
	 * @param isZeroKM
	 * @param dataPesquisa
	 * @return Double
	 */
	
	public Double obterValorCotacao(Long codigoFabricante,Long codigoModelo,Long tipoCombustivel,Long anoModelo,String isZeroKM,Date dataPesquisa) {

		StringBuffer sql = new StringBuffer();

		sql.append(" select c.vlMedioCotacVeicu ");
		sql.append(" from Veiculo v, CotacaoVeiculo c ");
		sql.append("where v.idVeicu = c.idVeicu ");
		sql.append("and v.cdFabrt = :fabricante ");
		sql.append("and v.cdMarcaModel = :modelo ");
		sql.append("and v.tpCmbst = :combustivel ");
		sql.append("and c.aaModel = :ano ");
		sql.append("and c.icZeroKm = :isZeroKm ");
		sql.append("and :dataPesquisa between c.dtInicoVigen and c.dtFimVigen ");

		try {
			Query query = em.createQuery(sql.toString());

			query.setParameter("fabricante",codigoFabricante);
			query.setParameter("modelo",codigoModelo);
			query.setParameter("combustivel",tipoCombustivel);
			query.setParameter("ano",anoModelo);
			query.setParameter("isZeroKm",isZeroKM);
			query.setParameter("dataPesquisa",dataPesquisa);

			return (Double) query.getSingleResult();

		} catch (Exception e) {
			return null;
		}

	}

	// OS 144757 -- Sérgio Mateus
	/**
	 * Retorna informações de caracteristica do veiculo (codfabricante/ codModelo)
	 *
	 * @param codMarcaModelo
	 * @return Veiculo
	 */	
	@SuppressWarnings("unchecked")
	public Veiculo obterCaracteristicaVeiculo(Long codMarcaModelo) {

		StringBuffer sql = new StringBuffer();

		sql.append(" select o ");
		sql.append(" from Veiculo o ");
		sql.append(" where o.cdMarcaModel = :codMarcaModelo ");
		sql.append(" and o.icDvado = 'N' ");
		sql.append(" and o.cdSitucCadtr = 'FIN'");

		try {
			Query query = em.createQuery(sql.toString());
			query.setMaxResults(1);
			query.setParameter("codMarcaModelo",codMarcaModelo);

			// return (Veiculo) query.getSingleResult();
			List<Veiculo> listaVeiculos = query.getResultList();
			if (listaVeiculos.size() > 1) {
				return new Veiculo();
			} else {
				return listaVeiculos.get(0);
			}

		} catch (Exception e) {
			return null;
		}
	}
}