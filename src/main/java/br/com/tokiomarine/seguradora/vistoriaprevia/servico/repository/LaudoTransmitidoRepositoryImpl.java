package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.core.util.NumericUtil;
import br.com.tokiomarine.seguradora.core.util.StringUtil;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.LaudoTransmitido;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.LaudoTransmitidoFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class LaudoTransmitidoRepositoryImpl implements LaudoTransmitidoRepository {

    @PersistenceContext
    private EntityManager em;

    public List<LaudoTransmitido> getLaudoTransmitidoListByFilter(LaudoTransmitidoFilter filter){

        StringBuilder sqlSelectClause = new StringBuilder();
        sqlSelectClause.append("SELECT new " + LaudoTransmitido.class.getCanonicalName());
        sqlSelectClause.append("(r.idRcpaoLaudo, r.cdLvpre, r.cdAgrmtVspre, r.cdChassiVeicu, r.cdPlacaVeicu,");
        sqlSelectClause.append(" r.dtRcpaoLaudo, r.icEmpre, r.icLaudoAceto, r.ctObjetIncon) ");

        // cria select
        StringBuilder sqlString = criarSelectObterLaudosRecepcionados(sqlSelectClause, filter.getIdPrestadora(), filter.getCodLaudo(), filter.getNumPlaca(), filter.getChassi(), filter.getLaudoAceito());
        sqlString.append("order by r.dtRcpaoLaudo desc ");

        // cria objeto query do jpql
        Query query = criarObjetoQueryJQPLObterLaudosRecepcionados(filter.getIdPrestadora(), filter.getDataPesquisaDe(), filter.getDataPesquisaAte(), filter.getCodLaudo(), filter.getNumPlaca(), filter.getChassi(), filter.getLaudoAceito(), sqlString);

        // obtém os laudos sem inconsistências
        List<LaudoTransmitido> laudosTransmitidos = query.getResultList();

        return laudosTransmitidos;
    }

    private Query criarObjetoQueryJQPLObterLaudosRecepcionados(Long codigoAgrupamentoPrestadora, Date dataInicioPesquisa, Date dataFimPesquisa, String codigoLaudo, String numeroPlaca, String numeroChassi, String indicadorLaudoAceito, StringBuilder sqlString) {

        Query query = em.createQuery(sqlString.toString());
        query.setParameter("dataInicio", dataInicioPesquisa);
        query.setParameter("dataFim", DateUtil.calculaNovaData(dataFimPesquisa,0,0,1));

        if (!NumericUtil.isZeroOrNull(codigoAgrupamentoPrestadora)) {
            query.setParameter("codigoAgrupamentoPrestadora",codigoAgrupamentoPrestadora);
        }

        if (!StringUtil.isEmpty(codigoLaudo)) {
            query.setParameter("codigoLaudo", codigoLaudo);
        }

        if (!StringUtil.isEmpty(numeroPlaca)) {
            query.setParameter("numeroPlaca", numeroPlaca);
        }

        if (!StringUtil.isEmpty(numeroChassi)) {
            query.setParameter("numeroChassi", numeroChassi);
        }

        if (!StringUtil.isEmpty(indicadorLaudoAceito)) {
            query.setParameter("indicadorLaudoAceito", indicadorLaudoAceito);
        }

        return query;
    }


    private StringBuilder criarSelectObterLaudosRecepcionados(StringBuilder sqlSelectClause, Long codigoAgrupamentoPrestadora,
                                                             String codigoLaudo, String numeroPlaca, String numeroChassi, String indicadorLaudoAceito) {
        StringBuilder sqlRetorno = new StringBuilder();
        sqlRetorno.append(sqlSelectClause);
        sqlRetorno.append("FROM RecepcaoLaudo r ");
        sqlRetorno.append("where r.dtRcpaoLaudo >= :dataInicio ");
        sqlRetorno.append("      and r.dtRcpaoLaudo < :dataFim ");

        if (codigoAgrupamentoPrestadora != null && codigoAgrupamentoPrestadora != 0L) {
            sqlRetorno.append("and r.cdAgrmtVspre = :codigoAgrupamentoPrestadora ");
        }

        if (codigoLaudo != null && !codigoLaudo.isEmpty()) {
            sqlRetorno.append("and r.cdLvpre = :codigoLaudo ");
        }

        if (numeroPlaca != null && !numeroPlaca.isEmpty()) {
            sqlRetorno.append("and r.cdPlacaVeicu = :numeroPlaca ");
        }

        if (numeroChassi != null && !numeroChassi.isEmpty()) {
            sqlRetorno.append("and r.cdChassiVeicu = :numeroChassi ");
        }

        if (indicadorLaudoAceito != null && !indicadorLaudoAceito.isEmpty()) {
            sqlRetorno.append("and r.icLaudoAceto = :indicadorLaudoAceito ");
        }

        return sqlRetorno;
    }

}
