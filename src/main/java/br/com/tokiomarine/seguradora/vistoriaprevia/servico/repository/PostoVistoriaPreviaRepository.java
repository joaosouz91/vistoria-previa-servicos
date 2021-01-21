package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PostoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.pk.PostoVistoriaPreviaPK;

@Repository
@Transactional(readOnly = true)
public interface PostoVistoriaPreviaRepository extends JpaRepository<PostoVistoriaPrevia, PostoVistoriaPreviaPK>, PostoVistoriaPreviaRepositoryCustom {
	
	@Query("select distinct nmCidad from PostoVistoriaPrevia where cdAgrmtVspre = ?1 and sgUniddFedrc = ?2 order by nmCidad Asc")
	List<String> findCidadesPorPrestadoraEstado(Long cdAgrmtVspre, String sgUniddFedrc);

	@Query("select distinct sgUniddFedrc from PostoVistoriaPrevia where cdSitucPosto = ?1 order by sgUniddFedrc Asc")
	List<String> findEstados(Long cdSitucPosto);

	@Query("select distinct sgUniddFedrc from PostoVistoriaPrevia where cdSitucPosto = ?1 and icAtndeCmhao = 'S' order by sgUniddFedrc Asc")
	List<String> findEstadosAtendeCaminhao(Long cdSitucPosto);

	@Query("select distinct nmCidad from PostoVistoriaPrevia where sgUniddFedrc = ?1 and cdSitucPosto = ?2 order by nmCidad Asc")
	List<String> findCidadesPorEstado(String sgUniddFedrc, Long cdSitucPosto);

	@Query("select distinct nmCidad from PostoVistoriaPrevia where sgUniddFedrc = ?1 and cdSitucPosto = ?2 and icAtndeCmhao = 'S' order by nmCidad Asc")
	List<String> findCidadesAtendeCaminhaoPorEstado(String sgUniddFedrc, Long cdSitucPosto);

	@Query("from PostoVistoriaPrevia where cdAgrmtVspre = ?1 and cdPostoVspre = ?2")
	Optional<PostoVistoriaPrevia> findPosto(Long cdAgrmtVspre, Long cdPostoVspre);
}
