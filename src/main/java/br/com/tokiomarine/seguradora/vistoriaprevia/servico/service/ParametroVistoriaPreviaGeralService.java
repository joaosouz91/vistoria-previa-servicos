package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ParametroVistoriaPreviaGeralRepository;

@Service
public class ParametroVistoriaPreviaGeralService {

	@Autowired
	private ParametroVistoriaPreviaGeralRepository repository;
	
	public String findByPorNome(String parametro) {
		return repository.findByPorNome(parametro).orElseThrow(() -> new InternalServerException("Parâmetro não encontrado: " + parametro)).getVlParamVspre();
	}
	
}
