package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Date;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.LaudoImprodutivoPercentualAux;

public interface LaudoImprodutivoPercentualRepositoryCustom {

	LaudoImprodutivoPercentualAux carregarPercentualVPImprodutivaAtual(Long cdCrtorSegur, Long cdSucsl,
			Date dataReferencia);

}
