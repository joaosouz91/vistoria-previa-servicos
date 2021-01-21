package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoAvarias;

@Repository
public interface ArquivoAvariasRepository extends JpaRepository<ArquivoAvarias, Long>{

	List<ArquivoAvarias> findAllByCdSitucAvariOrderByCodigoAvaria(String codigoAvaria);


}
