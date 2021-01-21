package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.ValorCaracteristicaModuloProduto;

@Repository
@Transactional(readOnly = true)
public interface ValorCaracteristicaModuloProdutoRepository extends JpaRepository<ValorCaracteristicaModuloProduto, Long> {

}
