package br.com.tokiomarine.seguradora.ext.act.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ext.act.repository.PropostaRepository;

@Service
public class PropostaService {
		
	@Autowired
	private PropostaRepository propostaRepository;
	
	public List<Object[]> buscarPropostaDetalheDoItem(Long codigoProposta, Long numeroItemSegurado){
		return propostaRepository.buscarPropostaDetalheDoItem(codigoProposta, numeroItemSegurado);
	}
	
	public List<Object[]> buscarPropostaReclassificacao(Long codigoProposta, Long numeroItemSegurado){
		return propostaRepository.buscarPropostaReclassificacao(codigoProposta, numeroItemSegurado);
	}
}
