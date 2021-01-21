package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoCodigosAceitacaoRecusa;

@Repository
public interface ArquivoCodigosAceitacaoRecusaRepository extends JpaRepository<ArquivoCodigosAceitacaoRecusa, Long>{

	List<ArquivoCodigosAceitacaoRecusa> findAllByCdSitucPatecOrderByCodigoParecerTecnico(String cdSitucPatec);

}
