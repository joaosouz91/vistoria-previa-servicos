package br.com.tokiomarine.seguradora.ext.ssv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ext.ssv.entity.ControleAlcadaDominio;
import br.com.tokiomarine.seguradora.ext.ssv.repository.ControleAlcadaDominioRepository;

@Service
public class ControleAlcadaDominioService {

	@Autowired
	private ControleAlcadaDominioRepository controleAlcadaDominioRepository;

	public ControleAlcadaDominio buscarControleAlcadaDominio(Long cdMdupr, Long tpOperc) {
		return controleAlcadaDominioRepository.buscarControleAlcadaDominio(cdMdupr, tpOperc);
	}
}