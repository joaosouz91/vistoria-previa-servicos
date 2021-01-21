package br.com.tokiomarine.seguradora.ext.ssv.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.ssv.corporativa.model.UsuarioAlcada;

@Repository
public interface UsuarioAlcadaRepository extends JpaRepository<UsuarioAlcada, Long>{

	@Query(value = "select "+
			 		"	o "+
					"from "+
					"	UsuarioAlcada o "+
					"where "+
					"	o.cdMdupr = :cdMdupr "+
					"	and o.cdUsuro = :codigoUsuario "+					
					"	and o.dtFimVigen <> to_date('31/12/2111','dd/mm/yyyy') "+
					"	and :dataConsulta between o.dtInicoVigen and o.dtFimVigen "+
					"	and o.dtDesat is null")
	public UsuarioAlcada buscarUsuarioAlcada(@Param("codigoUsuario") String codigoUsuario, @Param("cdMdupr") Long cdMdupr, @Param("dataConsulta") Date dataConsulta);

	@Query(value = "select o.cdAlcad from UsuarioAlcada o where o.cdMdupr = :cdMdupr and o.cdUsuro = :codigoUsuario "
			+ "and o.dtDesat is null and :dataReferencia between o.dtInicoVigen and o.dtFimVigen")
	public Optional<Long> buscarCodigoUsuarioAlcada(@Param("codigoUsuario") String codigoUsuario, @Param("cdMdupr") Long cdMdupr, @Param("dataReferencia") Date dataReferencia);
	
	@Query(value = "select o from UsuarioAlcada o where o.cdMdupr =:cdMdupr and o.cdUsuro =:cdUsuro and :dataConsulta between o.dtInicoVigen and o.dtFimVigen and o.dtFimVigen <> :dataAguardEfetiv and o.dtDesat is null")
	public Optional<UsuarioAlcada> getUsuarioAlcadaByCdMduprCdUsuarioDataVigencia(Long cdMdupr, String cdUsuro, Date dataConsulta, Date dataAguardEfetiv);
}
