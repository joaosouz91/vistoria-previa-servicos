package br.com.tokiomarine.seguradora.ext.ssv.service;

import static br.com.tokiomarine.seguradora.ext.ssv.enumerated.SSVModuloProduto.AUTOPASSEIO;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ext.ssv.repository.RegiaoTarifariaCepRepository;

@Service
public class RegiaoTarifariaCepService {

	@Autowired
	private RegiaoTarifariaCepRepository repository;

	public Optional<Long> recuperarRegiaoTarifariaAutoPasseio(String cep, Date dataReferencia) {
		
		if (NumberUtils.isParsable(cep)) {
			return repository.recuperarRegiaoTarifaria(AUTOPASSEIO.getCodigoModuloProduto(), Long.parseLong(cep), dataReferencia)
					.stream()
					.findFirst();
		}
		
		return Optional.empty();		
	}

	public Optional<Long> recuperarRegiaoTarifariaAutoPasseio(String cep) {
		return recuperarRegiaoTarifariaAutoPasseio(cep, new Date());
	}
}
