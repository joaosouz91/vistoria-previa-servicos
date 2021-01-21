package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoPecas;

@Repository
public interface ArquivoPecasRepository extends JpaRepository<ArquivoPecas, Long>{

	List<ArquivoPecas> findAllByCdSitucPecaOrderByCodigoPeca(String cdSitucPeca);

}
