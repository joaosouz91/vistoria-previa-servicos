package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio.LaudoImprodutivoPercentualAux;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LaudoImprodutivoPercentualRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilJava;

@Service
public class LaudoImprodutivoPercentualService {

	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private LaudoImprodutivoPercentualRepository laudoImprodutivoPercentualRepository;

	public Double recuperarPercentualImprodutivaReferencia(Long cdCrtorSegur, Long cdSucsl, Date dataReferencia) {
		Double pctPermitidoImprodutiva = 0d;
		LaudoImprodutivoPercentualAux percentual = laudoImprodutivoPercentualRepository
				.carregarPercentualVPImprodutivaAtual(cdCrtorSegur, cdSucsl, dataReferencia);

		if (UtilJava.trueVar(percentual)) {
			pctPermitidoImprodutiva = percentual.getPcLaudoImpdv();

		} else {

			percentual = laudoImprodutivoPercentualRepository.carregarPercentualVPImprodutivaAtual(
					ConstantesVistoriaPrevia.COD_CORRETOR_COMUM_IMPRODUTIVO, null, dataReferencia);
			if (UtilJava.trueVar(percentual)) {
				pctPermitidoImprodutiva = percentual.getPcLaudoImpdv();
			} else {
				throw new BusinessVPException(messageUtil.get("erro.percentual.vp.improdutiva.nao.encontrado"));
			}
		}

		return pctPermitidoImprodutiva;
	}
}
