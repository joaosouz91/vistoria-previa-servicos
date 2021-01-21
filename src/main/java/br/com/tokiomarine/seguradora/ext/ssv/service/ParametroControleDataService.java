package br.com.tokiomarine.seguradora.ext.ssv.service;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ZERO;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ext.ssv.repository.ParametroControleDataRepository;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ParametroControleData;

@Service
public class ParametroControleDataService {

	@Autowired
	private ParametroControleDataRepository parametroControleDataRepository;

	public ParametroControleData obterParametroControleData() {
		return parametroControleDataRepository.buscarDataDeControle().orElse(new ParametroControleData());
	}
	
	public Date obterDataMovimentoEmissao () {
		return parametroControleDataRepository.findByCdIdetcProcm(LONG_ZERO)
				.map(ParametroControleData::getDtReferOnlin)
				.orElseThrow(() -> new NullPointerException("DataMovimentoEmissao não encontrada."));
	}
}
