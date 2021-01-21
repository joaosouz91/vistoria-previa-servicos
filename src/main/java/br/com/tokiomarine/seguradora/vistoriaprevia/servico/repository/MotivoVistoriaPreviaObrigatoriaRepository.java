package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.MotivoVistoriaPreviaObrigatoria;

@Repository
@Transactional(readOnly = true)
public interface MotivoVistoriaPreviaObrigatoriaRepository extends JpaRepository<MotivoVistoriaPreviaObrigatoria, Long> {
	
	@Query(value =  "SELECT "+
			 		"	m.dsMotvVspreObgta "+					
					"FROM "+
					"	MotivoVistoriaPreviaObrigatoria m "+					
					"WHERE "+
					"	m.nrItseg = :numeroItemSegurado ")
	public List<String> buscarMotivoVistoriaPreviaPorNumeroItemSegurado(@Param("numeroItemSegurado") Long numeroItemSegurado);
	
	@Query(value =  "SELECT "+
			 		"	m.dsMotvVspreObgta "+
					"FROM "+
					"	MotivoVistoriaPreviaObrigatoria m, "+
					"	VistoriaPreviaObrigatoria v "+
					"WHERE "+
					"	m.idMotvVspreObgta = v.idVspreObgta "+
					"	AND v.nrItseg = :numeroItemSegurado")
	public List<String> buscarMotivoVistoriaPreviaTabelaAntigaPorNumeroItemSegurado(@Param("numeroItemSegurado") Long numeroItemSegurado);
	
	@Query(value =  "SELECT "+
			 		"	m.dsMotvVspreObgta "+					
					"FROM "+
					"	MotivoVistoriaPreviaObrigatoria m "+					
					"WHERE "+
					"	m.nrItseg = :numeroItemSegurado "+					
					"	AND m.cdEndos = :codigoEndosso")
	public List<String> buscarMotivoVistoriaPreviaPorCodigoEndosso(@Param("numeroItemSegurado") Long numeroItemSegurado, @Param("codigoEndosso") Long codigoEndosso);
		
	@Query(value =  "SELECT "+
			 		"	m.dsMotvVspreObgta "+
					"FROM "+
					"	MotivoVistoriaPreviaObrigatoria m, "+
					"	VistoriaPreviaObrigatoria v "+
					"WHERE "+
					"	m.idMotvVspreObgta = v.idVspreObgta "+
					"	AND v.nrItseg = :numeroItemSegurado "+
					"	AND v.cdEndos = :codigoEndosso")
	public List<String> buscarMotivoVistoriaPreviaTabelaAntigaPorCodigoEndosso(@Param("numeroItemSegurado") Long numeroItemSegurado, @Param("codigoEndosso") Long codigoEndosso);
	 
	}
