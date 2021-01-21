package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.corporativa.model.pk.ConteudoColunaTipoPK;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParecerTecnicoVistoriaPrevia;

@Repository
@Transactional(readOnly = true)
public interface ParecerTecnicoVistoriaPreviaRepository extends JpaRepository<ParecerTecnicoVistoriaPrevia, ConteudoColunaTipoPK>{

	@Query(value =  "select "+
					"	ift "+
					"from "+
					"	ParecerTecnicoVistoriaPrevia ift, "+ 
					"   ParecerTecnicoLaudoVistoriaPrevia lift "+ 
					"where "+
					"	ift.cdPatecVspre = lift.cdPatecVspre "+
					"	and lift.cdLvpre = :codigoLaudo "+
					"	and lift.nrVrsaoLvpre = :versaoLaudo"	)
	public List<ParecerTecnicoVistoriaPrevia> buscarInformacaoTecnica(@Param("codigoLaudo") String codigoLaudo, @Param("versaoLaudo") Long versaoLaudo);

	@Query(" SELECT pla.cdPatecVspre FROM ParecerTecnicoLaudoVistoriaPrevia pla "
			+ "WHERE pla.cdPatecVspre in(525,566) and pla.nrVrsaoLvpre = '0' and pla.cdLvpre = :codigoLaudo")
	public Long verificaParecerAvariaChassi(String codigoLaudo);

}
