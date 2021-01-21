package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.core.util.StringUtil;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AvariaVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.AvariaVistoriaPreviaFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AvariaVistoriaPreviaRepositoryHql {

    @PersistenceContext
    private EntityManager em;

    public List<AvariaVistoriaPrevia> getAvariaVistoriaPreviaByFilter(AvariaVistoriaPreviaFilter filter) {

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT a ");
        sql.append("FROM  AvariaVistoriaPrevia a ");

        if (filter.getDescricao() != null && !StringUtil.isEmpty(filter.getDescricao())) {
            sql.append(" WHERE lower(a.dsTipoAvari) like lower(:desc) ");
        }

        if (filter.getSituacao() != null) {
            if (filter.getDescricao() == null || StringUtil.isEmpty(filter.getDescricao())) {
                sql.append("WHERE a.cdSitucAvari = :sit ");
            } else {
                sql.append("AND a.cdSitucAvari = :sit ");
            }
        }

        sql.append(" ORDER BY a.cdTipoAvari  ");

        Query query = em.createQuery(sql.toString(), AvariaVistoriaPrevia.class);

        if (filter.getDescricao() != null && !StringUtil.isEmpty(filter.getDescricao())) {
            query.setParameter("desc", "%" + filter.getDescricao() + "%");
        }

        if (filter.getSituacao() != null) {
            query.setParameter("sit", filter.getSituacao());
        }

        return query.getResultList();

    }
}
