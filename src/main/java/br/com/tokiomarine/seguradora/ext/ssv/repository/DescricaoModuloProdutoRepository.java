package br.com.tokiomarine.seguradora.ext.ssv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.DescricaoModuloProduto;

@Repository
public interface DescricaoModuloProdutoRepository extends JpaRepository<DescricaoModuloProduto, Long>{

	@Query(value =  "select o from DescricaoModuloProduto o where o.cdMdupr = :cdMdupr")
	public DescricaoModuloProduto findDescricaoModuloProdutoByCdMdupr(@Param("cdMdupr") Long cdMdupr);
}
