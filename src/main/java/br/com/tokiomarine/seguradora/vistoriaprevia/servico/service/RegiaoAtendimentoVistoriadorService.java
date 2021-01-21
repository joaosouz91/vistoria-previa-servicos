package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.RegiaoAtendimentoVistoriador;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.LogradouroDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ParametroPercentualDistribuicaoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.RegiaoAtendimentoVistoriadorRepository;

@Service
public class RegiaoAtendimentoVistoriadorService {

	@Autowired
	private RegiaoAtendimentoVistoriadorRepository regiaoAtendimentoVistoriadorRepository;
	
	@Autowired
	private ParametroPercentualDistribuicaoService percentualDistribuicaoService;
	
	@Autowired
	private MapService mapService;
	
	public Long obterIdRegiao(String cep) {
		LogradouroDTO logradouroDTO = mapService.obterLogradouro(cep)
				.orElseThrow(() -> new BusinessVPException("Cep " + cep + " não localizado."));

		return regiaoAtendimentoVistoriadorRepository.obterIdRegiaoPorUFCidade(logradouroDTO.getUf(), logradouroDTO.getCidade()).orElseThrow(
				() -> new BusinessVPException("Região de atendimento não localizada para a uf " + logradouroDTO.getUf() + " / cidade " + logradouroDTO.getCidade() + "."));
	}

	public boolean exists(Example<RegiaoAtendimentoVistoriador> example) {
		return regiaoAtendimentoVistoriadorRepository.exists(example);
	}
	
	public List<ParametroPercentualDistribuicaoDTO> findVigenteByIdRegiao(Long idRegiao) {
		return percentualDistribuicaoService.findVigenteByIdRegiao(idRegiao, new Date());
	}
}
