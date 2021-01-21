package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.EmpresaVistoriadora;

//EmpresaVistoriadora = Franquia
@Repository
@Transactional(readOnly = true)
public interface FranquiaRespository extends JpaRepository<EmpresaVistoriadora, Long>{
	
	public EmpresaVistoriadora findByCdEmpreVstra(String codigo);

}
