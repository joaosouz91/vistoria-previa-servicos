package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivoDetalhe;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.LoteLaudoImprodutivoDetalheAux;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.RelatorioImprodutivoFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LoteLaudoImprodutivoDetalheRepository;

@Service
public class LoteLaudoImprodutivoDetalheService {

	@Autowired
	private LoteLaudoImprodutivoDetalheRepository laudoImprodutivoDetalheRepository;

	public List<LoteLaudoImprodutivoDetalheAux> carregarDetalheLoteImprodutivo(Long idLoteSelecionado, String string,
			RelatorioImprodutivoFiltro filtro) {
		return laudoImprodutivoDetalheRepository.carregarDetalheLoteImprodutivo(idLoteSelecionado, string, filtro);
	}

	public List<LoteLaudoImprodutivoDetalheAux> carregarListaLaudoAdicional(RelatorioImprodutivoFiltro filtro) {
		return laudoImprodutivoDetalheRepository.carregarListaLaudoAdicional(filtro);
	}

	public void update(LoteLaudoImprodutivoDetalhe loteLaudoImprodutivoDetalhe) {
		laudoImprodutivoDetalheRepository.save(loteLaudoImprodutivoDetalhe);
	}

	public void excluirListaLaudoAdicional(Long idLoteLaudoImpdv) {
		laudoImprodutivoDetalheRepository.excluirListaLaudoAdicional(idLoteLaudoImpdv);
	}

	public void alterarSitucDetalheLote(Long idLoteLaudoImpdv, String codIndSim) {
		laudoImprodutivoDetalheRepository.alterarSitucDetalheLote(idLoteLaudoImpdv, codIndSim);
	}
}
