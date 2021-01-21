package br.com.tokiomarine.seguradora.ext.ssv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.ssv.transacional.model.PlacaImpeditiva;

@Repository
public interface PlacaImpeditivaRepository extends JpaRepository<PlacaImpeditiva, Long>{

	public boolean existsByCodPlacaVeiculo(String codPlacaVeiculo);
}
