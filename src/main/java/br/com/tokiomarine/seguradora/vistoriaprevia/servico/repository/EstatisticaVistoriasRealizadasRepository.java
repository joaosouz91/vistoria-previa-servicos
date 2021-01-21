package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.EstatisticaVistoriasRealizadas;

import java.util.List;

public interface EstatisticaVistoriasRealizadasRepository {

    List<EstatisticaVistoriasRealizadas> getEstatisticaVistoriasRealizadas(String periodo, Long cdPrestadora);

    List<EstatisticaVistoriasRealizadas> getDetalheEstatisticaVistoriasRealizadas(String periodo, Long cdPrestadora,String situacaoVistoria);

}
