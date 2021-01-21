package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoAcessorios;

@Repository
public interface ArquivoAcessoriosRepository extends JpaRepository<ArquivoAcessorios, Long>{

	List<ArquivoAcessorios> findAllByCdSitucAcsroVspreOrderByCodigoAcessorio(String cdSitucAcsroVspre);

}
