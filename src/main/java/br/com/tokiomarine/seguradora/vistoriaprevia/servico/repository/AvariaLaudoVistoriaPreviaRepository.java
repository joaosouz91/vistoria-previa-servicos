package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AvariaLaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.AvariaDTO;

@Repository
@Transactional(readOnly = true)
public interface AvariaLaudoVistoriaPreviaRepository extends JpaRepository<AvariaLaudoVistoriaPrevia, String>{
	
	@Query(value =  "select "+
					"	 o "+
					"from "+					 
					"   AvariaLaudoVistoriaPrevia o "+ 
					"where "+
					"	o.cdLvpre = :noLaudoVistPrevia")
	public List<AvariaLaudoVistoriaPrevia> findByAvariaLaudo(@Param("noLaudoVistPrevia") String noLaudoVistPrevia);

	@Query("select new br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto.AvariaDTO(a, pv.dsPecaAvada) "
			+ "from AvariaLaudoVistoriaPrevia a, PecaVeiculoVistoriaPrevia pv "
			+ "where a.cdPecaAvada=pv.cdPecaAvada and a.cdLvpre=:cdLvpre and a.nrVrsaoLvpre=:nrVrsaoLvpre")
	public List<AvariaDTO> findByCdLvpreAndNrVrsaoLvpre(String cdLvpre, Long nrVrsaoLvpre);

	public AvariaLaudoVistoriaPrevia findByCdLvpreAndCdPecaAvadaAndTpAvariVeicu(String cdLvpre, Long codPeca,
			String tpAvaria);
}
