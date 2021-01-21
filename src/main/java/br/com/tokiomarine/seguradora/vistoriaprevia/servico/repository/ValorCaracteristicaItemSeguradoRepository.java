package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.ValorCaracteristicaItemSegurado;

@Repository
@Transactional(readOnly = true)
public interface ValorCaracteristicaItemSeguradoRepository extends JpaRepository<ValorCaracteristicaItemSegurado, Long> {

	
	@Query(value = "select vlCar from ValorCaracteristicaItemSegurado vlCar, ValorCaracteristicaModuloProduto vmp where "
 			+ " vlCar.cdVlcarItseg = vmp.cdVlcarItseg "
 			+ " and vlCar.sqVlcarItseg = vmp.sqVlcarItseg "
 			+ " and vlCar.cdVlcarItseg=:cdVlcarItseg "
 			+ " and :dataConsulta between vmp.dtInicoVigen and vmp.dtFimVigen and vmp.dtFimVigen <> :dataAguarde  "
 			+ "order by vmp.cdMdupr")
	List<ValorCaracteristicaItemSegurado> findByCdVlcarItsegAndSqVlcarItsegAndDataConsulta(@Param("cdVlcarItseg") Long cdVlcarItseg, @Param("dataConsulta") Date dataConsulta, @Param("dataAguarde") Date dataAguarde);

	
	@Query(value = "select tc.cdVlcarItseg, tc.dsVaricInico " + 
			"From ValorCaracteristicaItemSegurado tc, ValorCaracteristicaModuloProduto vmp " + 
			"Where " + 
			"tc.cdVlcarItseg = vmp.cdVlcarItseg " + 
			"and tc.sqVlcarItseg = vmp.sqVlcarItseg " + 
			"and vmp.cdCaracItseg = :codigoTipoCarroceria " + 
			"And vmp.dtFimVigen = :dataFimVigencia  " + 
			"And tc.cdVlcarItseg <> :codigoCarroceriaNaoIformado " + 
			"Order by tc.dsVaricInico ")
	List<ValorCaracteristicaItemSegurado> getListaTipoCarroceria(@Param("codigoTipoCarroceria") Long codigoTipoCarroceria, 
										@Param("dataFimVigencia") Date dataFimVigencia, @Param("codigoCarroceriaNaoIformado") Long codigoCarroceriaNaoIformado);
}
