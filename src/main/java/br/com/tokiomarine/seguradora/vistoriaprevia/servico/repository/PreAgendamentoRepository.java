package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.core.exception.DAOException;
import br.com.tokiomarine.seguradora.core.exception.ExceptionUtil;
import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.DeParaFipe;
import br.com.tokiomarine.seguradora.ssv.transacional.model.ItemSegurado;
import br.com.tokiomarine.seguradora.ssv.transacional.model.PlacaImpeditiva;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPreviaGeral;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaObrigatoriaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.CaracteristicaValor;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesRegrasVP;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilJava;

@Repository
@Transactional(readOnly = true)
public class PreAgendamentoRepository {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Carrega parametros da tabela Fipe.
	 *
	 * @param codFabricante
	 * @param codModelo
	 * @param codCombustivel
	 * @param dataConsulta
	 *
	 * @return List<DeParaFipe>
	 *
	 * @see br.com.tokiomarine.seguradora.ssv.cadprodvar.model.DeParaFipe
	 * @see br.com.tokiomarine.seguradora.ssv.cadprodvar.model.Veiculo
	 */
	@SuppressWarnings("unchecked")
	public List<DeParaFipe> carregarDeParaFipe(Long codFabricante, Long codModelo, Long codCombustivel, Date dataConsulta) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT f ");
		sql.append("   FROM DeParaFipe f, ");
		sql.append("        Veiculo v     ");
		sql.append("  WHERE f.idVeicu       = v.idVeicu ");
		sql.append("    AND f.dtInicoVigen <= :dataConsulta ");
		sql.append("    AND f.dtFimVigen   >= :dataConsulta ");
		sql.append("    AND v.cdFabrt      =:codFabricante ");
		sql.append("    AND v.cdMarcaModel =:codModelo ");

		if (codCombustivel != null) {
			sql.append("    AND v.tpCmbst  =:codCombustivel ");
		}

		try {
			Query query = em.createQuery(sql.toString());
			query.setParameter("dataConsulta", dataConsulta);

			query.setParameter("codFabricante", codFabricante);
			query.setParameter("codModelo", codModelo);

			if (codCombustivel != null) {
				query.setParameter("codCombustivel", codCombustivel);
			}

			return query.getResultList();

		} catch (RuntimeException e) {
			throw new InternalServerException("Erro ao executar query carregarDeParaFipe: " + ExceptionUtils.getRootCause(e).getMessage());
		}
	}
	
	public VistoriaPreviaObrigatoria obterPreAgendamentoPorChave(Long codSistema,Long...codigo) {

		StringBuilder sql = new StringBuilder();
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
			default: 
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
			return null;
		} catch (NonUniqueResultException ne){
			// Caso ocorrer erro, devido exisitir um ou mais pre-agendamento(s) com mais de 20 dias retorna
			// o ultimo da base de pré-agendamento, conforme a data.
			return (VistoriaPreviaObrigatoria) query.getResultList().get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public List<VistoriaObrigatoriaDTO> pesquisaPropostaAgendamento(VistoriaFiltro filtro) {
		filtro = filtro != null ? filtro : new VistoriaFiltro();
		return (List<VistoriaObrigatoriaDTO>) filtro;
	}
	
	public VistoriaPreviaObrigatoria obterPreAgendamentoSemPropostaPorVeiculo(String cdPlacaVeicu,String cdChassiVeicu,Long cdCrtorCia) {

		Boolean placaImpeditiva = this.isPlacaImpeditiva(cdPlacaVeicu);

		StringBuilder sql = new StringBuilder();

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
		query.setParameter("agtoSemPropostaTMS",ConstantesRegrasVP.AGTO_SEM_PROPOSTA_TMS);
		query.setParameter("agtoSemPropostaTMB",ConstantesRegrasVP.AGTO_SEM_PROPOSTA_TMB);

		try {
			List<VistoriaPreviaObrigatoria> listaVPO = query.getResultList();
			if (listaVPO == null || listaVPO.isEmpty()) { return null; }

			return listaVPO.get(0);

		} catch (RuntimeException e) {
			throw new DAOException("Erro ao executar query obterPreAgendamentoSemPropostaPorVeiculo: " + ExceptionUtil.getRootMessage(e));
		}
	}
	
	public boolean isPlacaImpeditiva(String codPlacaVeiculo) {

		StringBuilder sql = new StringBuilder();

		sql.append(" select p ");
		sql.append("   from PlacaImpeditiva p ");
		sql.append("  where p.codPlacaVeiculo = :codPlacaVeiculo ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("codPlacaVeiculo",codPlacaVeiculo);

		try {
			List<PlacaImpeditiva> listaPlacas = query.getResultList();
			if (listaPlacas != null && !listaPlacas.isEmpty()) { return true; }
		} catch (RuntimeException e) {
			throw new DAOException("Erro ao executar query isPlacaImpeditiva: " + ExceptionUtil.getRootMessage(e));
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<CaracteristicaValor> recuperarFabricante(Long codCaracteristica, Date dataConsulta, boolean isSomenteAtivos, Long codFabricante) {

		StringBuffer sql = new StringBuffer();
        sql.append(" Select DISTINCT new ");
        sql.append( 		CaracteristicaValor.class.getCanonicalName() );
        sql.append(" 		( vmp.cdCaracItseg , vl.cdVlcarItseg , vl.sqVlcarItseg , vl.dsVaricInico, vmp.icAgrmt, v.cdFabrt) ");
		sql.append("   FROM ValorCaracteristicaItemSegurado vl, ValorCaracteristicaModuloProduto vmp, ");
		sql.append("   		Veiculo v ");
		sql.append("  WHERE  ");
		sql.append("	vl.cdVlcarItseg = vmp.cdVlcarItseg ");
        sql.append("	AND vl.sqVlcarItseg = vmp.sqVlcarItseg ");
        sql.append("	AND vmp.cdCaracItseg  = :codCaracteristica ");
		sql.append("    AND vmp.dtInicoVigen <= :dataConsulta ");
		sql.append("    AND vmp.dtFimVigen   >= :dataConsulta ");
		sql.append("    AND vmp.dtFimVigen   <> :dataNaoEfetivado ");
		sql.append("    AND v.cdFabrt       = vl.cdVlcarItseg ");
		sql.append("    AND v.cdSitucCadtr  = 'FIN' ");

		if (isSomenteAtivos) {
			sql.append("    AND v.icDvado = 'N' ");
		}
        if (UtilJava.trueVar(codFabricante)) {
        	sql.append("    AND v.cdFabrt =:codFabricante ");
        }

		sql.append(" ORDER BY vl.dsVaricInico ASC");

		try {

			Query query = em.createQuery(sql.toString());

			query.setParameter("codCaracteristica", codCaracteristica);
			query.setParameter("dataConsulta", dataConsulta);
			query.setParameter("dataNaoEfetivado", DateUtil.DATA_REGISTRO_AGUARDANDO_EFETIVACAO);

	        if (UtilJava.trueVar(codFabricante)) {
	        	query.setParameter("codFabricante", codFabricante);
	        }

			return query.getResultList();

		} catch (RuntimeException e) {

			throw new DAOException("Erro ao executar query recuperarFabricante: " + ExceptionUtil.getRootMessage(e));
		}
	}

    /**
     * Retorna lista de Modelo
     *
     * @param codFabricante
     * @param codCaracteristica
     * @param dataConsulta
     * @param isSomenteAtivos
     * @param codModelo
     *
     * @return List<CaracteristicaValor>
     *
     * @see br.com.tokiomarine.seguradora.ssv.cadprodvar.model.ValorCaracteristicaItemSegurado
     * @see br.com.tokiomarine.seguradora.ssv.cadprodvar.model.Veiculo
     */
	@SuppressWarnings("unchecked")
	public List<CaracteristicaValor> recuperarModelo(Long codFabricante, Long codCaracteristica, Date dataConsulta, boolean isSomenteAtivos, Long codModelo) {

        StringBuffer sql = new StringBuffer();

        sql.append(" Select DISTINCT new ");
        sql.append( 		CaracteristicaValor.class.getCanonicalName() );
        sql.append(" 		( vmp.cdCaracItseg , vl.cdVlcarItseg , vl.sqVlcarItseg , vl.dsVaricInico, vmp.icAgrmt , v.cdFabrt, v.cdMarcaModel ) ");
		sql.append("   FROM ValorCaracteristicaItemSegurado vl, ValorCaracteristicaModuloProduto vmp, ");
		sql.append("   		Veiculo v ");
        sql.append("  WHERE ");
		sql.append("	vl.cdVlcarItseg = vmp.cdVlcarItseg ");
        sql.append("	AND vl.sqVlcarItseg = vmp.sqVlcarItseg ");
        sql.append("	AND vmp.cdCaracItseg  = :codCaracteristica ");
        sql.append("    AND vmp.dtInicoVigen <= :dataConsulta ");
        sql.append("    AND vmp.dtFimVigen   >= :dataConsulta ");
        sql.append("    AND vmp.dtFimVigen   <> :dataNaoEfetivado ");
        sql.append("    AND v.cdMarcaModel  = vl.cdVlcarItseg " );
        sql.append("    AND v.cdSitucCadtr  = 'FIN' ");

        if (UtilJava.trueVar(codFabricante)) {
        	sql.append("    AND v.cdFabrt =:codFabricante " );
        }
        if (UtilJava.trueVar(codModelo)) {
        	sql.append("    AND v.cdMarcaModel =:codModelo ");
        }

        if ( isSomenteAtivos ) {
            sql.append("    AND v.icDvado = 'N' ");
        }

        sql.append(" ORDER BY vl.dsVaricInico ASC" );

        try {

            Query query = em.createQuery(sql.toString());

            query.setParameter("codCaracteristica", codCaracteristica);
            query.setParameter("dataConsulta", dataConsulta);
            query.setParameter("dataNaoEfetivado", DateUtil.DATA_REGISTRO_AGUARDANDO_EFETIVACAO);

            if (UtilJava.trueVar(codFabricante)) {
                query.setParameter("codFabricante", codFabricante);
            }
            if (UtilJava.trueVar(codModelo)) {
            	query.setParameter("codModelo", codModelo);
            }

            return query.getResultList();

        }  catch (RuntimeException e) {

            throw new DAOException("Erro ao executar query recuperarModelo: " + ExceptionUtil.getRootMessage( e ) );
        }
    }
	
	
   public List<ItemSegurado> getItemSeguradoByCorretorPlacaOrChassiAgendamento(String cdChassiVeicu, String cdPlacaVeicu, Date dataLimitePesquisa) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT iseg.cd_chassi_veicu as cd_chassi_veicu ,");
        sql.append(" iseg.cd_placa_veicu as cd_placa_veicu, ");
        sql.append(" iseg.nr_itseg as nr_itseg ,");
        sql.append(" iseg.tp_histo_itseg as tp_histo_itseg, ");
        sql.append(" iseg.cd_endos as cd_endos, ");
        sql.append(" iseg.cd_situc_ngoco as cd_situc_ngoco, ");
        sql.append(" iseg.cd_mdupr as cd_mdupr, ");
        sql.append(" iseg.cd_clien as cd_clien ");
        sql.append(" FROM SSV0076_ITSEG iseg,");
        sql.append(" ssv0084_ngoco neg,");
        sql.append(" SSV0025_CONTR_ENDOS ce");
        sql.append(" where neg.cd_ngoco = iseg.cd_ngoco");
        sql.append(" and neg.tp_histo = iseg.tp_histo_ngoco");
        sql.append(" and ce.cd_endos (+)= iseg.cd_endos");
        sql.append(" and ( neg.cd_situc_ngoco in('COT','PRO','PRC','LIP','GRD') OR (neg.cd_situc_ngoco = 'APO' and iseg.cd_endos is not null and ce.cd_situc_endos not in('END','REC') ) )");
        if(cdChassiVeicu == null)
        sql.append(" and neg.dt_cadmt_ppota >= :dataLimite ");
        if(cdChassiVeicu != null)
        sql.append(" and (iseg.cd_chassi_veicu = :codChassiVeiculo ");
        if(cdPlacaVeicu != null) {
        	sql.append( "(iseg.cd_placa_veicu = :codPlacaVeiculo and iseg.cd_placa_veicu not in "
	        		+ "(select pi.cd_placa_veicu from SSV0099_PLACA_IMPDT pi) ) ");
        }		
        if(cdChassiVeicu != null)       		
        sql.append("                            or (iseg.cd_chassi_veicu= :codChassiVeiculo ) )        	");
       
        sql.append(" order by neg.dt_ultma_alter desc ");

        try {

            Query query = em.createNativeQuery(sql.toString(), ItemSegurado.class);
	        if(cdChassiVeicu != null)       		
	        	query.setParameter("codChassiVeiculo", cdChassiVeicu.trim());
            
	        if(cdPlacaVeicu != null)       		
	        	query.setParameter("codPlacaVeiculo", cdPlacaVeicu.trim());
           
	        if(dataLimitePesquisa != null && cdChassiVeicu == null)       		
	        	query.setParameter("dataLimite", dataLimitePesquisa);
	        
	        List<ItemSegurado> list=  query.getResultList();
            return list;

        } catch (Exception t) {
            throw t;
        }
    }
   
   @SuppressWarnings("unchecked")
	public List<ParametroVistoriaPreviaGeral> getParametroVistoriaPreviaGeral(String nmParamVspre) {
        
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select o from ParametroVistoriaPreviaGeral o ");
    	sql.append(" where o.nmParamVspre = :nmParamVspre ");
        
        Query query = em.createQuery(sql.toString());        
        
        query.setParameter("nmParamVspre",nmParamVspre);
        try {
        	return query.getResultList();
        } catch (NoResultException e) {
        	return null;
		}
    }
}
