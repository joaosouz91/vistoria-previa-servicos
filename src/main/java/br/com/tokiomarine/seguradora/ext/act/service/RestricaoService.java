package br.com.tokiomarine.seguradora.ext.act.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.aceitacao.crud.model.Restricao;
import br.com.tokiomarine.seguradora.ext.act.repository.RestricaoRepository;
import br.com.tokiomarine.seguradora.ssv.transacional.model.ItemSegurado;

@Service
public class RestricaoService {

	@Autowired
	private RestricaoRepository restricaoRepository;
	
	public Restricao save(Restricao restricao) {
		return restricaoRepository.save(restricao);
	}
	
	public Restricao buscarRestricao(Long codigoProposta, Long numeroItemSegurado, String tipoRestricao) {
		return restricaoRepository.buscarRestricao(codigoProposta, numeroItemSegurado, tipoRestricao);
	}

	public boolean verificaVpDispensada(ItemSegurado itemSegurado) {
		Long codigoProposta = itemSegurado.getCodEndosso() !=null ? itemSegurado.getCodEndosso() : itemSegurado.getCodNegocio();
		
		return restricaoRepository.verificaVpDispensada(codigoProposta, itemSegurado.getNumItemSegurado());
	}
}
