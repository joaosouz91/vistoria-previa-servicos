package br.com.tokiomarine.seguradora.ext.ssv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ext.ssv.repository.ParametroSsvRepository;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ParametroSSV;

@Service
public class ParametroSsvService {

	@Autowired
	private ParametroSsvRepository parametroSsvRepository;

	public ParametroSSV findByParametroSsv(Long cdGrpParamSsv, String cdParamSsv) {
		return parametroSsvRepository.findByParametroSsv(cdGrpParamSsv, cdParamSsv);
	}
}
