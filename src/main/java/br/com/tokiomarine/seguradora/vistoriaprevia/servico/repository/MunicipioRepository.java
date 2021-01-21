package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.Municipio;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long>{

	@Query(value="select *" + 
			"        from" + 
			"            VPE0443_REGIAO_ATNMT_VSTRO a" + 
			"        where" + 
			"            a.SG_UF=:uf", nativeQuery = true)
	List<Municipio> findByUf(@Param("uf") String uf);

	Optional<Municipio> findFirstByCodMunicipio(Long codMunicipio);



}
