package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ValorDescricaoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.LoteLaudoImprodutivoAux;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.RelatorioImprodutivoFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LoteLaudoImprodutivoRepository;

@Service
public class LoteLaudoImprodutivoService {

	@Autowired
	private LoteLaudoImprodutivoRepository laudoImprodutivoRepository;

	public List<ValorDescricaoDTO> obterListaReferenciaLotesLaudosImprodutivos() {
		List<YearMonth> recuperarListaMesAnoReferencia = laudoImprodutivoRepository.recuperarListaMesAnoReferencia();
		return montarListaReferenciaMesAno(recuperarListaMesAnoReferencia);
	}

	public List<LoteLaudoImprodutivoAux> carregarLotesImprodutivos(RelatorioImprodutivoFiltro filtro) {
		return laudoImprodutivoRepository.carregarLotesImprodutivos(filtro);
	}

	public LoteLaudoImprodutivoAux carregarLoteImprodutivo(Long id) {
		return laudoImprodutivoRepository.carregarLoteImprodutivo(id);
	}

	public Long recuperarQtdLotesTransmitidos(YearMonth ref) {
		return laudoImprodutivoRepository.recuperarQtdLotesTransmitidos(ref);
	}

	/**
	 * Verifica se Lote foi Transmitido.
	 */
	public boolean permitirEdicao(YearMonth ref) {
		// Se campo dataEnvioLote para referencia for nulo permite edição.
		Long lotesTransmitidos = recuperarQtdLotesTransmitidos(ref);

		return lotesTransmitidos == null || lotesTransmitidos.longValue() == 0l;
	}

	public LoteLaudoImprodutivo save(LoteLaudoImprodutivo loteLaudoImprodutivo) {
		return laudoImprodutivoRepository.save(loteLaudoImprodutivo);
	}

	public void atualizaValoresEQuantidadesLotes(Long idLoteLaudoImpdv, Long mesReferencia, Long anoReferencia) {
		laudoImprodutivoRepository.atualizaValoresEQuantidadesLotes(idLoteLaudoImpdv, mesReferencia, anoReferencia);
	}

	public Optional<LoteLaudoImprodutivo> findById(Long id) {
		return laudoImprodutivoRepository.findById(id);
	}

	/**
	 * Permite apenas meses inferiores ao mes de referencia do Lote para pesquisa de
	 * Laudos Adicionais.
	 */
	public List<ValorDescricaoDTO> recuperarListaMesAnoAdicional(YearMonth dtReferencia) {

		List<YearMonth> listaMesAno = laudoImprodutivoRepository.recuperarListaMesAnoReferencia().stream()
				.filter(mesAno -> {
					LocalDate dataRefencia = dtReferencia.atDay(1);
					LocalDate dataRefenciaLista = mesAno.atDay(1);

					return dataRefencia.isAfter(dataRefenciaLista);
				}).collect(Collectors.toList());

		return this.montarListaReferenciaMesAno(listaMesAno);
	}

	private List<ValorDescricaoDTO> montarListaReferenciaMesAno(List<YearMonth> recuperarListaMesAnoReferencia) {
		String[] nomeMeses = new DateFormatSymbols().getMonths();
		return recuperarListaMesAnoReferencia.stream().map(r -> {
			String label = nomeMeses[r.getMonthValue() - 1] + "/" + r.getYear();
			return new ValorDescricaoDTO(r, label);
		}).collect(Collectors.toList());
	}

	public Double carregarVlTotalLoteCaldo(Long idLoteLaudoImpdv) {
		return laudoImprodutivoRepository.carregarVlTotalLoteCaldo(idLoteLaudoImpdv);
	}

	public void transmitirLotes(YearMonth dataReferencia) {
		laudoImprodutivoRepository.transmitirLotes(dataReferencia);
	}
}
