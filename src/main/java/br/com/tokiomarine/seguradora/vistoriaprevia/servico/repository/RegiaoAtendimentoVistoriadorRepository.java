package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.RegiaoAtendimentoVistoriador;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RegiaoAtendimentoVistoriadorDTO;

@Repository
@Transactional(readOnly = true)
public interface RegiaoAtendimentoVistoriadorRepository extends JpaRepository<RegiaoAtendimentoVistoriador, Long> {

	@Query("select idRegiaoAtnmtVstro from RegiaoAtendimentoVistoriador where sgUf = ?1 and nmMunic = ?2")
	Optional<Long> obterIdRegiaoPorUFCidade(String uf, String nmMunic);

	@Query("select new br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RegiaoAtendimentoVistoriadorDTO(r.idRegiaoAtnmtVstro, r.sgUf, r.cdMunic, r.nmMunic)"
			+ " from RegiaoAtendimentoVistoriador r where r.idRegiaoAtnmtVstro = ?1")
	Optional<RegiaoAtendimentoVistoriadorDTO> obterRegiaoByIdRegiaoAtnmtVstro(Long idRegiao);

}
