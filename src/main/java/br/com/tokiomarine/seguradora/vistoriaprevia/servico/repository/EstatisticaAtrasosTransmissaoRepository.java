package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.AtrasoTransmissaoAgrupamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.DetalhamentoAtrasoTransmissao;

import java.util.List;

public interface EstatisticaAtrasosTransmissaoRepository {

    List<AtrasoTransmissaoAgrupamento> findAtrasoTransmissaoAgrupamentoList(String periodo, Long cdPrestadora, ParametroVistoriaPrevia paramVP);

    List<DetalhamentoAtrasoTransmissao> findDetalhamentoLaudoAtrasoTransmissaoList(String periodo, Long cdPrestadora);

}
