package br.com.tokiomarine.seguradora.ext.ssv.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ext.ssv.repository.ModuloProdutoRepository;
import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.ModuloProduto;

@Service
public class ModuloProdutoService {

	@Autowired
	private ModuloProdutoRepository moduloProdutoRepository;

	public ModuloProduto findModuloProdutoByCdMduprVigente(Long cdMdupr, Date dataInicioVigencia, Date dataInvalida) {
		return moduloProdutoRepository.findModuloProdutoByCdMduprVigente(cdMdupr, dataInicioVigencia, dataInvalida);
	}
}
