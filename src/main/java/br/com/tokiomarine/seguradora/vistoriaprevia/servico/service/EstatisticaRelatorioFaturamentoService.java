package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.EstatisticaRelatorioFaturamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.DetalhamentoEstatisticaFaturamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.EstatisticaFaturamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.EstatisticaRelatorioFaturamentoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstatisticaRelatorioFaturamentoService {

    @Autowired
    private EstatisticaRelatorioFaturamentoRepositoryImpl relatorioFaturamentoRepository;

    @Autowired
    private PaginationService<EstatisticaFaturamento> estatisticaFaturamentoPagination;

    @Autowired
    private PaginationService<DetalhamentoEstatisticaFaturamento> detalhamentoPagination;


    public EstatisticaRelatorioFaturamentoDTO getEstatisticaFaturamentoDTO(Long idPrestadora, String periodo, Integer size, Integer page){

        List<EstatisticaFaturamento> estatisticaFaturamentoList;

        if(!Long.valueOf(0).equals(idPrestadora)) {
            estatisticaFaturamentoList = relatorioFaturamentoRepository.findEstatisticaFranquias(periodo, idPrestadora);
        } else {
            estatisticaFaturamentoList = relatorioFaturamentoRepository.findEstatisticaPrestadoras(periodo);
        }

        estatisticaFaturamentoList = agrupaDadosPorEmpresa(estatisticaFaturamentoList);

        estatisticaFaturamentoList = estatisticaFaturamentoList
                .stream()
                .map(this::setTotalGeralEstatisticaFaturamento)
                .collect(Collectors.toList());

        Page<EstatisticaFaturamento> estatisticaFaturamentoPageableList = estatisticaFaturamentoPagination.listToPageImpl(size, page, estatisticaFaturamentoList);

        return new EstatisticaRelatorioFaturamentoDTO(estatisticaFaturamentoPageableList, null);
    }

    public Page<DetalhamentoEstatisticaFaturamento> getDetalheEstatisticaFaturamentoList(Long idPrestadora, String periodo, String tipoLocalVistoria,
                                                                                         Long distanciaKilometragem, Integer size, Integer page){

        List<DetalhamentoEstatisticaFaturamento> retorno = relatorioFaturamentoRepository
                                                                .findDetalheEstatisticaPrestadora(
                                                                        idPrestadora,
                                                                        periodo,
                                                                        tipoLocalVistoria,
                                                                        distanciaKilometragem);

        return detalhamentoPagination.listToPageImpl(size, page, retorno);
    }

    private List<EstatisticaFaturamento> agrupaDadosPorEmpresa(List<EstatisticaFaturamento> estatisticas) {
        EstatisticaFaturamento estatisticaFaturamento = new EstatisticaFaturamento();
        long codigoEmpresa = 0L;

        // variaveis para somar a quantidade de km
        long quantidadeKMPrimeiro = 0L;
        long quantidadeKMSegundo = 0L;
        long quantidadeKMTerceiro = 0L;
        long quantidadeKMQuarto = 0L;
        long quantidadePosto = 0L;
        long quantidadeMobile = 0L;

        List<EstatisticaFaturamento> estatisticaList = new ArrayList<>();

        for(EstatisticaFaturamento estatistica : estatisticas) {
            if(codigoEmpresa == 0L || codigoEmpresa == estatistica.getCodigoEmpresa()) {

                // seta valores
                estatisticaFaturamento.setCodigoEmpresa(estatistica.getCodigoEmpresa());
                estatisticaFaturamento.setNomeEmpresa(estatistica.getNomeEmpresa());
                if("D".equals(estatistica.getTipoLocalVistoria())) {

                    if(estatistica.getQuantidadeKmRealizado() >= 0L && estatistica.getQuantidadeKmRealizado() <= 50L) {
                        quantidadeKMPrimeiro = quantidadeKMPrimeiro + estatistica.getQuantidadeKM();
                        estatisticaFaturamento.setQuantidadeDistKmPrimeiro(quantidadeKMPrimeiro);
                    } else if (estatistica.getQuantidadeKmRealizado() >= 51L && estatistica.getQuantidadeKmRealizado() <= 100L) {
                        quantidadeKMSegundo = quantidadeKMSegundo + estatistica.getQuantidadeKM();
                        estatisticaFaturamento.setQuantidadeDistKmSegundo(quantidadeKMSegundo);
                    } else if(estatistica.getQuantidadeKmRealizado() >= 101L && estatistica.getQuantidadeKmRealizado() <= 200L) {
                        quantidadeKMTerceiro = quantidadeKMTerceiro + estatistica.getQuantidadeKM();
                        estatisticaFaturamento.setQuantidadeDistKmTerceiro(quantidadeKMTerceiro);
                    } else {
                        quantidadeKMQuarto = quantidadeKMQuarto + estatistica.getQuantidadeKM();
                        estatisticaFaturamento.setQuantidadeDistKmQuarto(quantidadeKMQuarto);
                    }
                } else if("P".equals(estatistica.getTipoLocalVistoria())) {
                    quantidadePosto = quantidadePosto + 1;
                    estatisticaFaturamento.setQuantidadePosto(quantidadePosto);
                } else {
                    quantidadeMobile = quantidadeMobile + 1;
                    estatisticaFaturamento.setQuantidadeMobile(quantidadeMobile);
                }

            } else {

                // soma a quantidade do último registro e seta no total de dominilio
                estatisticaFaturamento.setQuantidadeDomicilio(quantidadeKMPrimeiro + quantidadeKMSegundo + quantidadeKMTerceiro + quantidadeKMQuarto);

                // adiciona objeto na lista
                estatisticaList.add(estatisticaFaturamento);

                quantidadeKMPrimeiro = 0L;
                quantidadeKMSegundo = 0L;
                quantidadeKMTerceiro = 0L;
                quantidadeKMQuarto = 0L;
                quantidadePosto = 0L;

                // cria novo objeto
                estatisticaFaturamento = new EstatisticaFaturamento();

                // seta valores
                estatisticaFaturamento.setCodigoEmpresa(estatistica.getCodigoEmpresa());
                estatisticaFaturamento.setNomeEmpresa(estatistica.getNomeEmpresa());
                if("D".equals(estatistica.getTipoLocalVistoria())) {
                    if(estatistica.getQuantidadeKmRealizado() >= 0L && estatistica.getQuantidadeKmRealizado() <= 50L) {
                        quantidadeKMPrimeiro = quantidadeKMPrimeiro + estatistica.getQuantidadeKM();
                        estatisticaFaturamento.setQuantidadeDistKmPrimeiro(quantidadeKMPrimeiro);
                    } else if (estatistica.getQuantidadeKmRealizado() >= 51L && estatistica.getQuantidadeKmRealizado() <= 100L) {
                        quantidadeKMSegundo = quantidadeKMSegundo + estatistica.getQuantidadeKM();
                        estatisticaFaturamento.setQuantidadeDistKmSegundo(quantidadeKMSegundo);
                    } else if(estatistica.getQuantidadeKmRealizado() >= 101L && estatistica.getQuantidadeKmRealizado() <= 200L) {
                        quantidadeKMTerceiro = quantidadeKMTerceiro + estatistica.getQuantidadeKM();
                        estatisticaFaturamento.setQuantidadeDistKmTerceiro(quantidadeKMTerceiro);
                    } else {
                        quantidadeKMQuarto = quantidadeKMQuarto + estatistica.getQuantidadeKM();
                        estatisticaFaturamento.setQuantidadeDistKmQuarto(quantidadeKMQuarto);
                    }
                } else if("P".equals(estatistica.getTipoLocalVistoria())) {
                    quantidadePosto = quantidadePosto + 1;
                    estatisticaFaturamento.setQuantidadePosto(quantidadePosto);
                } else {
                    quantidadeMobile = quantidadeMobile + 1;
                    estatisticaFaturamento.setQuantidadeMobile(quantidadeMobile);
                }
            }

            codigoEmpresa = estatistica.getCodigoEmpresa();
        }

        // soma a quantidade do último registro e seta no total de dominilio
        estatisticaFaturamento.setQuantidadeDomicilio(quantidadeKMPrimeiro + quantidadeKMSegundo + quantidadeKMTerceiro + quantidadeKMQuarto);

        // adiciona o último elemento na lista
        estatisticaList.add(estatisticaFaturamento);

        return estatisticaList.stream().peek(linha -> {
            if(linha.getQuantidadeDistKmPrimeiro() == null) linha.setQuantidadeDistKmPrimeiro(0L);
            if(linha.getQuantidadeDistKmSegundo() == null) linha.setQuantidadeDistKmSegundo(0L);
            if(linha.getQuantidadeDistKmTerceiro() == null) linha.setQuantidadeDistKmTerceiro(0L);
            if(linha.getQuantidadeDistKmQuarto() == null) linha.setQuantidadeDistKmQuarto(0L);
            if(linha.getQuantidadeDomicilio() == null) linha.setQuantidadeDomicilio(0L);
            if(linha.getQuantidadePosto() == null) linha.setQuantidadePosto(0L);
            if(linha.getTotalGeral() == null) linha.setTotalGeral(0L);
        }).collect(Collectors.toList());
    }

    private EstatisticaFaturamento setTotalGeralEstatisticaFaturamento(EstatisticaFaturamento estatisticaFaturamento) {

        Long quantidadePosto = estatisticaFaturamento.getQuantidadePosto() == null ? 0L : estatisticaFaturamento.getQuantidadePosto();
        Long quantidadeDomicilio = estatisticaFaturamento.getQuantidadeDomicilio() == null ? 0L : estatisticaFaturamento.getQuantidadeDomicilio();

        estatisticaFaturamento.setTotalGeral(quantidadePosto + quantidadeDomicilio);

        return estatisticaFaturamento;
    }


}
