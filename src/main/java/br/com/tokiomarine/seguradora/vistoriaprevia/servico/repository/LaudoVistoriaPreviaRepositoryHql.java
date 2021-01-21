package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.FiltroConsultaVistoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.RemoverCaracteres;

@Repository
@Transactional(readOnly = true)
public class LaudoVistoriaPreviaRepositoryHql {

	 @PersistenceContext
	 private EntityManager entityManager;
	 
	 @SuppressWarnings("unchecked")
	public List<Object[]> findByLaudosByVeiculo(final FiltroConsultaVistoria  filtroConsultaVistoria){
		 RemoverCaracteres removerCaracteres = new RemoverCaracteres();
		 
		 StringBuilder sqlQuery = new StringBuilder();
		 sqlQuery.append("SELECT ");
		 sqlQuery.append("	v, l ");
		 sqlQuery.append("FROM ");
		 sqlQuery.append(" 	LaudoVistoriaPrevia l, VeiculoVistoriaPrevia v, ProponenteVistoriaPrevia p ");
		 sqlQuery.append("WHERE ");
		 sqlQuery.append("   p.cdLvpre = l.cdLvpre ");
		 sqlQuery.append("   AND p.nrVrsaoLvpre = l.nrVrsaoLvpre ");
		 sqlQuery.append("   AND v.cdLvpre = l.cdLvpre ");
		 sqlQuery.append("   AND v.nrVrsaoLvpre = l.nrVrsaoLvpre ");
        	        
		 if(filtroConsultaVistoria.getPlaca() != null && !filtroConsultaVistoria.getPlaca().equals("") && filtroConsultaVistoria.getPlaca().trim().length() > 0) {
			 sqlQuery.append(" AND v.cdPlacaVeicu = :placa ");
		 }
        
		 if(filtroConsultaVistoria.getChassi() != null && !filtroConsultaVistoria.getChassi().equals("") && filtroConsultaVistoria.getChassi().trim().length() > 0) {
			 sqlQuery.append(" AND v.cdChassiVeicu = :chassi ");
		 }
        
		 if(filtroConsultaVistoria.getNumeroVistoria() != null && !filtroConsultaVistoria.getNumeroVistoria().equals("") && filtroConsultaVistoria.getNumeroVistoria().trim().length() > 0) {
			 sqlQuery.append(" AND l.cdLvpre = :laudo ");
		 }
			        
		 if(filtroConsultaVistoria.getCpf() != null && filtroConsultaVistoria.getCpf().trim().length() > 0) {
			 sqlQuery.append(" AND p.nrCpfPrpnt = :cpf ");
		 }
        
		 if(filtroConsultaVistoria.getCnpj() != null && filtroConsultaVistoria.getCnpj().trim().length() > 0) {
			 sqlQuery.append(" and p.nrCnpjPrpnt = :cnpj ");
		 }
        
		 if(filtroConsultaVistoria.getNumeroVoucher() != null && filtroConsultaVistoria.getNumeroVoucher().trim().length() > 0) {
			 sqlQuery.append(" AND l.cdVouch = :codVoucher ");
		 }
		
		 Query query = entityManager.createQuery (sqlQuery.toString());
		
		 if(filtroConsultaVistoria.getPlaca() != null && !filtroConsultaVistoria.getPlaca().equals("") && filtroConsultaVistoria.getPlaca().trim().length() > 0) {
			 query.setParameter("placa", filtroConsultaVistoria.getPlaca().trim().toUpperCase());
		 }
		
		 if(filtroConsultaVistoria.getChassi() != null && !filtroConsultaVistoria.getChassi().equals("") && filtroConsultaVistoria.getChassi().trim().length() > 0) {
			 query.setParameter("chassi", filtroConsultaVistoria.getChassi().trim().toUpperCase());
		 }
		
		 if(filtroConsultaVistoria.getNumeroVistoria() != null && !filtroConsultaVistoria.getNumeroVistoria().equals("") && filtroConsultaVistoria.getNumeroVistoria().trim().length() > 0) {
			 query.setParameter("laudo", filtroConsultaVistoria.getNumeroVistoria().trim());
		 }
		 		 
		 if(filtroConsultaVistoria.getCpf() != null && filtroConsultaVistoria.getCpf().trim().length() > 0) {
			 final String cpfString = removerCaracteres.removerCaracteres(filtroConsultaVistoria.getCpf().trim()); 
			 query.setParameter("cpf", Long.parseLong(cpfString));
		 }
        		 
		 if(filtroConsultaVistoria.getCnpj() != null && filtroConsultaVistoria.getCnpj().trim().length() > 0) {
			 final String cnpjString = removerCaracteres.removerCaracteres(filtroConsultaVistoria.getCnpj().trim()); 
			 query.setParameter("cnpj", Long.parseLong(cnpjString));
		 }
        
		 if(filtroConsultaVistoria.getNumeroVoucher() != null && filtroConsultaVistoria.getNumeroVoucher().trim().length() > 0) {
			 query.setParameter("codVoucher", filtroConsultaVistoria.getNumeroVoucher().trim());
		 }
		
		 return query.getResultList();
	 }
}
