package br.com.tokiomarine.seguradora.ext.ssv.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.ModuloProduto;
import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.pk.ModuloProdutoPK;

@Repository
public interface ModuloProdutoRepository extends JpaRepository<ModuloProduto, ModuloProdutoPK> {

	@Query(value =  "select o from ModuloProduto o " +
			 		"where " +
			 		"o.cdMdupr = :cdMdupr " +
			 		"and :dataInicioVigencia " +
			 		"between o.dtInicoVigen " +
			 		"and o.dtFimVigen " +
			 		"and o.dtFimVigen <> :dataInvalida")
	public ModuloProduto findModuloProdutoByCdMduprVigente(@Param("cdMdupr") Long cdMdupr, @Param("dataInicioVigencia") Date dataInicioVigencia, @Param("dataInvalida") Date dataInvalida);
}
