package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.LoteLaudoImprodutivoDetalheAux;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.RelatorioImprodutivoFiltro;

public interface LoteLaudoImprodutivoDetalheRepositoryCustom {

	List<LoteLaudoImprodutivoDetalheAux> carregarDetalheLoteImprodutivo(Long idLoteLaudoImpdv, String codTipoLaudo,
			RelatorioImprodutivoFiltro filtro);

	List<LoteLaudoImprodutivoDetalheAux> carregarListaLaudoAdicional(RelatorioImprodutivoFiltro filtro);

	void excluirListaLaudoAdicional(Long idLoteLaudoImpdv);

	void alterarSitucDetalheLote(Long idLoteLaudoImpdv, String codIndSim);

}
