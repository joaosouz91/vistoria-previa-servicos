package br.com.tokiomarine.seguradora.ext.ssv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.ssv.transacional.model.DescricaoItemSegurado;

@Repository
public interface DescricaoItemSeguradoRepository extends JpaRepository<DescricaoItemSegurado, Long>{

	@Query(value =  "select di " +
					"from DescricaoItemSegurado di " +
					"where " +
					"di.numItemSegurado = :numItemSegurado " +
					"and di.tipoHistorico = :tipoHistorico")
	public List<DescricaoItemSegurado> findDescricaoItemSeguradoByNrItsegTpHisto(@Param("numItemSegurado") Long numItemSegurado, @Param("tipoHistorico") String tipoHistorico);

	public DescricaoItemSegurado findByCodCaracteristicaItemSeguradoAndNumItemSeguradoAndTipoHistorico(
			Long codigoCaracteristica, Long numeroItem, String tipoHistoricoItem);
	
	@Query(value = "select d from AgrupamentoCaracteristicaItemSegurado a, DescricaoItemSegurado d "
			+ "where a.cdAgrmtCaracItseg = :codigoAgrupamento and a.cdMdupr = :codigoProduto "
			+ "and d.codCaracteristicaItemSegurado = a.cdCaracItseg "
			+ "and d.numItemSegurado = :numeroItem "
			+ "and d.tipoHistorico = :tipoHistorico")
	public DescricaoItemSegurado findDescricaoItemSeguradoPorAgrupamento(
			@Param("numeroItem") Long numeroItem, @Param("tipoHistorico") String tipoHistorico, @Param("codigoAgrupamento") String codigoAgrupamento, @Param("codigoProduto")  Long codigoProduto);

	public Boolean existsByNumItemSeguradoAndCodCaracteristicaItemSeguradoAndCodValorCaracteristicaItemSeguradoIn(
			Long itSeg, Long codCarac, List<Long> listRestricaoMobile);
}
