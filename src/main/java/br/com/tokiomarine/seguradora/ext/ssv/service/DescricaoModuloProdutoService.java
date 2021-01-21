package br.com.tokiomarine.seguradora.ext.ssv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ext.ssv.repository.DescricaoModuloProdutoRepository;
import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.DescricaoModuloProduto;

@Service
public class DescricaoModuloProdutoService {

	@Autowired
	private DescricaoModuloProdutoRepository descricaoModuloProdutoRepository;

	public DescricaoModuloProduto findDescricaoModuloProdutoByCdMdupr(Long cdMdupr) {
		return descricaoModuloProdutoRepository.findDescricaoModuloProdutoByCdMdupr(cdMdupr);
	}
}
