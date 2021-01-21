package br.com.tokiomarine.seguradora.ext.ssv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ext.ssv.repository.PlacaImpeditivaRepository;

@Service
public class PlacaImpeditivaService {

	@Autowired
	private PlacaImpeditivaRepository repository;

	public boolean isPlacaImpeditiva(String codPlacaVeiculo) {
		return repository.existsByCodPlacaVeiculo(codPlacaVeiculo);
	}
}
