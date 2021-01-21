package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class LogVinculoRepository {

	@PersistenceContext
	private EntityManager em;
	
	public List<String> obterLogVinculoLaudo(String numeroLaudo, Date dataLaudo) {
		
		StringBuilder sql = new StringBuilder();
		
		List<String> listaLog = new ArrayList<>();

    	sql.append(" select gpad.ds_log_detal_procm_asscn ");
    	sql.append(" from ssv4221_log_procm_asscn gpa, ssv4222_log_detal_procm_asscn gpad ");
    	sql.append(" where gpa.nm_mdulo = 'VISTORIA_PREVIA_VINCULO' ");
    	sql.append(" and trunc(gpa.dt_inico_procs) >= ?1 ");
    	sql.append(" and gpad.id_log_procm_asscn = gpa.id_log_procm_asscn ");
    	sql.append(" and gpad.cd_argum_psqsa_usuro = ?2 ");
    	sql.append(" order by gpad.id_log_detal_procm_asscn desc ");
    	
    	Query query = em.createNativeQuery(sql.toString());
    	query.setParameter(1,dataLaudo);
    	query.setParameter(2,numeroLaudo);
    	
    	List<Clob> list = query.getResultList();

        for(Clob obj: list) {
        	listaLog.add(clobToString(obj));
        }

    	return listaLog;
	}
	
	private String clobToString(Clob data) {
		StringBuilder sb = new StringBuilder();
		try {
			Reader reader = data.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);

			String line;
			while (null != (line = br.readLine())) {
				sb.append(line);
			}
			br.close();
		} catch (SQLException e) {
			// handle this exception
		} catch (IOException e) {
			// handle this exception
		}
		return sb.toString();
	}
}
