package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.core.util.StringUtil;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PecaVeiculoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.PecaVeiculoVistoriaPreviaFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PecaVeiculoVistoriaPreviaRepositoryHql  {

    @PersistenceContext
    private EntityManager em;

    public List<PecaVeiculoVistoriaPrevia> getPecaVeiculoVistoriaPreviaByFilter(PecaVeiculoVistoriaPreviaFilter filter) {

        StringBuffer sqlString;
        sqlString = new StringBuffer();

        sqlString.append("SELECT a ");
        sqlString.append("FROM  PecaVeiculoVistoriaPrevia a ");

        if (filter.getDescricao() != null && !StringUtil.isEmpty(filter.getDescricao())) {
            sqlString.append(" WHERE lower(a.dsPecaAvada) like lower(:desc) ");
        }

        if (filter.getSituacao() != null) {
            if ((filter.getDescricao() == null || StringUtil.isEmpty(filter.getDescricao()))) {
                sqlString.append("WHERE a.cdSitucPeca = :situacao ");
            } else {
                sqlString.append("AND a.cdSitucPeca = :situacao ");
            }
        }

        sqlString.append(" ORDER BY a.cdPecaAvada  ");

        Query query = em.createQuery(sqlString.toString(), PecaVeiculoVistoriaPrevia.class);

        if (filter.getDescricao() != null && !StringUtil.isEmpty(filter.getDescricao())) {
            query.setParameter("desc", "%" + filter.getDescricao() + "%");
        }

        if (filter.getSituacao() != null) {
            query.setParameter("situacao", filter.getSituacao());
        }

        return query.getResultList();
    }

    @Transactional
    public PecaVeiculoVistoriaPrevia save(PecaVeiculoVistoriaPrevia model) {
        if(model.getCdPecaAvada() != null) {
            return em.merge(model);
        }
        em.persist(model);
        return model;
    }


}
