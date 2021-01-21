package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.time.YearMonth;
import java.util.Date;
import java.util.List;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.LoteImprodutivoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.LoteLaudoImprodutivoAux;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.RelatorioImprodutivoFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.LaudoImprodutivo;

public interface LoteLaudoImprodutivoRepositoryCustom {

	List<LoteImprodutivoDTO> obterLotes(Long mesReferencia, Long anoReferencia);

	List<LaudoImprodutivo> obterAceitaveisNaoVinculadosProposta(Date dataInicial, Date dataFinal);
	
	List<LaudoImprodutivo> obterAceitaveisNaoVinculadosEndosso(Date dataInicial,Date dataFinal);
	
	List<LaudoImprodutivo> obterAceitaveisNaoVinculadosEndossoInadimplencia(Date dataInicial,Date dataFinal);

	List<LaudoImprodutivo> obterLaudoRepetido(Date dataInicial, Date dataFinal);

	List<LaudoImprodutivo> obter2LaudoDesnecessario(Date dataInicial, Date dataFinal);

	List<LaudoImprodutivo> obterLaudosNuncaVinculados(Date dataInicial, Date dataFinal, Date dataFinalFechamento);

	void atualizaValoresEQuantidadesLotes(Long idLote, Long mesReferencia, Long anoReferencia);

	void atualizaFlagDeFranquia(Long idLote, Long mesReferencia, Long anoReferencia, Date dataInicio, Date dataFim);

	List<LoteLaudoImprodutivo> obterLotesForaFranquia(Long idLote, Long mesReferencia, Long anoReferencia);

	Double obterPercentualFranquiaCorretor(Long codigoCorretor);

	void atualizaValorCalculado(Long idLote, Double valorTotalLoteCalculado, Long qtCalculado, String icFranquia);

	List<YearMonth> recuperarListaMesAnoReferencia();

	List<LoteLaudoImprodutivoAux> carregarLotesImprodutivos(RelatorioImprodutivoFiltro filtro);

	LoteLaudoImprodutivoAux carregarLoteImprodutivo(Long id);

	Long recuperarQtdLotesTransmitidos(YearMonth ref);

	Double carregarVlTotalLoteCaldo(Long idLoteLaudoImpdv);

	void transmitirLotes(YearMonth dataReferencia);

}
