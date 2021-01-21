package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import br.com.tokiomarine.seguradora.core.util.NumericUtil;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.EstatisticaAtrasosTransmissaoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.AtrasoTransmissaoAgrupamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.DetalhamentoAtrasoTransmissao;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.DetalhamentoAtrasoTransmissaoTotal;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.EstatisticaAtrasosTransmissaoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class EstatisticaAtrasosTransmissaoService {

    @Autowired
    EstatisticaAtrasosTransmissaoRepositoryImpl estatisticaAtrasosTransmissaoRepository;

    @Autowired
    ParametroVistoriaPreviaService parametroVistoriaPreviaService;

    @Autowired
    DateService dateService;

    @Autowired
    PaginationService<AtrasoTransmissaoAgrupamento> agrupamentoPaginationService;

    @Autowired
    PaginationService<DetalhamentoAtrasoTransmissao> detalhamentoPaginationService;

    public EstatisticaAtrasosTransmissaoDTO getAtrasoTransmissaoAgrupamentoDTO(String periodo, Long cdPrestadora, Long moduloSelected, Integer size, Integer pageIndex) {

        ParametroVistoriaPrevia paramVP = parametroVistoriaPreviaService.getParametroVistoriaPreviaPorModuloSelecionado(moduloSelected);
        List<AtrasoTransmissaoAgrupamento> atrasoTransmissaoList = estatisticaAtrasosTransmissaoRepository.findAtrasoTransmissaoAgrupamentoList(periodo, cdPrestadora, paramVP);
        atrasoTransmissaoList = getDiferencaDias(atrasoTransmissaoList, paramVP, periodo.substring(0,2), periodo.substring(2,6));
        somaQuantidadeRegistros(atrasoTransmissaoList);
        setPorcentagemAtrasosTransmissaoAgrupamentoPrestadora(atrasoTransmissaoList);

        AtrasoTransmissaoAgrupamento linhaComTotal = null;
        if(cdPrestadora.equals(0L)) {
            linhaComTotal = getTotalAtrasosTransmissaoAgrupamentoPrestadora(atrasoTransmissaoList);
        }

        Page<AtrasoTransmissaoAgrupamento> atrasoTransmissaoAgrupamentoPageableList = agrupamentoPaginationService.listToPageImpl(size, pageIndex, atrasoTransmissaoList);

        return new EstatisticaAtrasosTransmissaoDTO(
                atrasoTransmissaoAgrupamentoPageableList,
                linhaComTotal);
    }

    public Page<DetalhamentoAtrasoTransmissao> getDetalhamentoLaudoAtrasoTransmissao(String periodo, Long cdPrestadora, Integer diaInicio, Integer diaFim, Integer size, Integer pageIndex) {

        List<DetalhamentoAtrasoTransmissao> listaRetorno = new ArrayList<>();

        // obtem os dias que são feriados
        List<String> diasList = null;
        List<DetalhamentoAtrasoTransmissao> consultaRetorno = estatisticaAtrasosTransmissaoRepository.findDetalhamentoLaudoAtrasoTransmissaoList(periodo, cdPrestadora);

        if(diaFim == 0) {
            diaFim = Integer.MAX_VALUE;
        }

        for(DetalhamentoAtrasoTransmissao detalhamentoAtrasoTransmissao : consultaRetorno) {
            Long quantidadeDiasEmAtraso;
            if (detalhamentoAtrasoTransmissao.getAtrasoDias() != null){
                quantidadeDiasEmAtraso = detalhamentoAtrasoTransmissao.getAtrasoDias();
            } else {
                if (diasList == null) {
                    diasList = dateService.getDatasUteis(periodo.substring(0,2),periodo.substring(2,6));
                }
                quantidadeDiasEmAtraso = Long.parseLong(dateService.getDiferencaDiasUteis(detalhamentoAtrasoTransmissao.getDataTransmissao(), detalhamentoAtrasoTransmissao.getDataVistoria(), diasList)+"");
            }
            if(diaInicio == 0) {
                if(quantidadeDiasEmAtraso >= diaInicio && quantidadeDiasEmAtraso <= diaFim) {
                    detalhamentoAtrasoTransmissao.setAtrasoDias(quantidadeDiasEmAtraso);
                    listaRetorno.add(detalhamentoAtrasoTransmissao);
                }
            } else {
                if(quantidadeDiasEmAtraso > diaInicio && quantidadeDiasEmAtraso <= diaFim) {
                    detalhamentoAtrasoTransmissao.setAtrasoDias(quantidadeDiasEmAtraso);
                    listaRetorno.add(detalhamentoAtrasoTransmissao);
                }
            }
        }

        return detalhamentoPaginationService.listToPageImpl(size, pageIndex, listaRetorno);
    }

    public List<DetalhamentoAtrasoTransmissaoTotal> getDetalhamentoAtrasoTransmissaoTotal(Long cdPrestadora, String periodo) {

        List<DetalhamentoAtrasoTransmissaoTotal> detalhamentoAtrasoTransmissaoTotalList = estatisticaAtrasosTransmissaoRepository.findDetalhamentoAtrasoTransmissaoTotal(cdPrestadora, periodo);

        List<DetalhamentoAtrasoTransmissaoTotal > detalheTransmissaoList = new ArrayList<>();

        // obtem os dias que são feriados
        List<String> diasList = null;
        HashMap<String, DetalhamentoAtrasoTransmissaoTotal> hashFranquias = new HashMap<>();

        for(DetalhamentoAtrasoTransmissaoTotal detalhamentoAtrasoTransmissaoTotal : detalhamentoAtrasoTransmissaoTotalList) {

            Long quantidadeDiasEmAtraso;

            if(detalhamentoAtrasoTransmissaoTotal.getAtrasoDias()!= null) {
                quantidadeDiasEmAtraso = detalhamentoAtrasoTransmissaoTotal.getAtrasoDias();
            } else {
                if (diasList == null) {
                    diasList = dateService.getDatasUteis(periodo.substring(0, 2), periodo.substring(2, 6));
                }
                quantidadeDiasEmAtraso = Long.parseLong(dateService.getDiferencaDiasUteis(detalhamentoAtrasoTransmissaoTotal.getDataTransmissao(),detalhamentoAtrasoTransmissaoTotal.getDataVistoria(), diasList)+"");
            }

            String codFranquia = detalhamentoAtrasoTransmissaoTotal.getCodigoLaudo().substring(0,3);
            DetalhamentoAtrasoTransmissaoTotal detalheTransmissao;

            if(!hashFranquias.containsKey(codFranquia)) {
                detalheTransmissao = new DetalhamentoAtrasoTransmissaoTotal();
            } else {
                detalheTransmissao = hashFranquias.get(codFranquia);
            }

            detalheTransmissao.setCodigoEmpresa(codFranquia);
            detalheTransmissao.setNomeEmpresa(detalhamentoAtrasoTransmissaoTotal.getNomeEmpresa());

            if(quantidadeDiasEmAtraso >= 0 && quantidadeDiasEmAtraso <= 2) {
                detalheTransmissao.setQuantidadeAteDois(detalheTransmissao.getQuantidadeAteDois() + 1);
            } else if(quantidadeDiasEmAtraso == 3) {
                detalheTransmissao.setQuantidadeAteTres(detalheTransmissao.getQuantidadeAteTres() +1);
            } else {
                detalheTransmissao.setQuantidadeMaiorTres(detalheTransmissao.getQuantidadeMaiorTres() +1);
            }

            hashFranquias.put(codFranquia, detalheTransmissao);
        }

        for (DetalhamentoAtrasoTransmissaoTotal detalheTransmissaoFinal : hashFranquias.values()) {
            detalheTransmissaoFinal.setTotalRegistros(detalheTransmissaoFinal.getQuantidadeAteDois() + detalheTransmissaoFinal.getQuantidadeAteTres() + detalheTransmissaoFinal.getQuantidadeMaiorTres());
            detalheTransmissaoList.add(detalheTransmissaoFinal);
        }

        calculaPorcentagemGeral(detalheTransmissaoList);
        Collections.sort(detalheTransmissaoList);

        return detalheTransmissaoList;
    }

    private List<AtrasoTransmissaoAgrupamento> getDiferencaDias(List<AtrasoTransmissaoAgrupamento> atrasoTransmissaoList, ParametroVistoriaPrevia paramVP, String mes, String ano) {

        Long codigoEmpresa = null;

        long quantDiaPrimeiroRankEmissaoRelatorio = 0L;
        long quantDiaSegundoRankEmissaoRelatorio = 0L;
        long quantDiaTerceiroRankEmissaoRelatorio = 0L;
        long quantDiaQuartoRankEmissaoRelatorio = 0L;
        long quantDiaQuintoRankEmissaoRelatorio = 0L;
        long quantDiaUltimoRankEmissaoRelatorio = 0L;

        if(atrasoTransmissaoList.size() == 0) return atrasoTransmissaoList;

        List<AtrasoTransmissaoAgrupamento> atrasoTransmissaoAgrupamentoList = new ArrayList<>();
        AtrasoTransmissaoAgrupamento atrasoTransmissaoAgrupamento = new AtrasoTransmissaoAgrupamento();

        // obtem os dias que são feriados
        List<String> diasList = null;

        for(AtrasoTransmissaoAgrupamento atrasoTransmissao : atrasoTransmissaoList) {
            int quantidadeDiasEmAtraso;
            if(atrasoTransmissao.getQuantidadeDiasAtraso() != null) {
                quantidadeDiasEmAtraso = atrasoTransmissao.getQuantidadeDiasAtraso().intValue();
            }
            else {
                if (diasList == null){
                    diasList = dateService.getDatasUteis(mes, ano);
                }
                quantidadeDiasEmAtraso = dateService.getDiferencaDiasUteis(atrasoTransmissao.getDataTransmissao(), atrasoTransmissao.getDataVistoria(), diasList);
                //atualizaLaudoDiasAtraso() // Está na wkspace antiga somente. Removido da api.
            }
            if(codigoEmpresa == null || codigoEmpresa.equals(atrasoTransmissao.getCodigoEmpresa())) {
                atrasoTransmissaoAgrupamento.setCodigoEmpresa(atrasoTransmissao.getCodigoEmpresa());
                atrasoTransmissaoAgrupamento.setNomeEmpresa(atrasoTransmissao.getNomeEmpresa());

                if(quantidadeDiasEmAtraso >= 0 && quantidadeDiasEmAtraso <= paramVP.getQtDiaPrmroRnkngEmissRelat().intValue()) {
                    quantDiaPrimeiroRankEmissaoRelatorio = quantDiaPrimeiroRankEmissaoRelatorio + 1;
                    atrasoTransmissaoAgrupamento.setQuantDiaPrimeiroRankEmissaoRelatorio(quantDiaPrimeiroRankEmissaoRelatorio);
                } else if(quantidadeDiasEmAtraso  ==  paramVP.getQtDiaSegdoRnkngEmissRelat().intValue()) {
                    quantDiaSegundoRankEmissaoRelatorio = quantDiaSegundoRankEmissaoRelatorio + 1;
                    atrasoTransmissaoAgrupamento.setQuantDiaSegundoRankEmissaoRelatorio(quantDiaSegundoRankEmissaoRelatorio);
                } else if(quantidadeDiasEmAtraso  >  paramVP.getQtDiaSegdoRnkngEmissRelat().intValue() && quantidadeDiasEmAtraso  <=  paramVP.getQtDiaTerceRnkngEmissRelat().intValue()) {
                    quantDiaTerceiroRankEmissaoRelatorio = quantDiaTerceiroRankEmissaoRelatorio + 1;
                    atrasoTransmissaoAgrupamento.setQuantDiaTerceiroRankEmissaoRelatorio(quantDiaTerceiroRankEmissaoRelatorio);
                } else if(quantidadeDiasEmAtraso  >  paramVP.getQtDiaTerceRnkngEmissRelat().intValue() && quantidadeDiasEmAtraso  <=  paramVP.getQtDiaQurtoRnkngEmissRelat().intValue()) {
                    quantDiaQuartoRankEmissaoRelatorio = quantDiaQuartoRankEmissaoRelatorio + 1;
                    atrasoTransmissaoAgrupamento.setQuantDiaQuartoRankEmissaoRelatorio(quantDiaQuartoRankEmissaoRelatorio);
                } else if(quantidadeDiasEmAtraso  >  paramVP.getQtDiaQurtoRnkngEmissRelat().intValue() && quantidadeDiasEmAtraso  <=  paramVP.getQtDiaUltmoRnkngEmissRelat().intValue()) {
                    quantDiaQuintoRankEmissaoRelatorio = quantDiaQuintoRankEmissaoRelatorio + 1;
                    atrasoTransmissaoAgrupamento.setQuantDiaQuintoRankEmissaoRelatorio(quantDiaQuintoRankEmissaoRelatorio);
                } else {
                    quantDiaUltimoRankEmissaoRelatorio = quantDiaUltimoRankEmissaoRelatorio + 1;
                    atrasoTransmissaoAgrupamento.setQuantUltimoRankEmissaoRelatorio(quantDiaUltimoRankEmissaoRelatorio);;
                }
            } else {

                atrasoTransmissaoAgrupamentoList.add(atrasoTransmissaoAgrupamento);
                atrasoTransmissaoAgrupamento = new AtrasoTransmissaoAgrupamento();

                quantDiaPrimeiroRankEmissaoRelatorio = 0L;
                quantDiaSegundoRankEmissaoRelatorio = 0L;
                quantDiaTerceiroRankEmissaoRelatorio = 0L;
                quantDiaQuartoRankEmissaoRelatorio = 0L;
                quantDiaQuintoRankEmissaoRelatorio = 0L;
                quantDiaUltimoRankEmissaoRelatorio = 0L;

                atrasoTransmissaoAgrupamento.setCodigoEmpresa(atrasoTransmissao.getCodigoEmpresa());
                atrasoTransmissaoAgrupamento.setNomeEmpresa(atrasoTransmissao.getNomeEmpresa());

                if(quantidadeDiasEmAtraso >= 0 && quantidadeDiasEmAtraso <= paramVP.getQtDiaPrmroRnkngEmissRelat().intValue()) {
                    quantDiaPrimeiroRankEmissaoRelatorio = quantDiaPrimeiroRankEmissaoRelatorio + 1;
                    atrasoTransmissaoAgrupamento.setQuantDiaPrimeiroRankEmissaoRelatorio(quantDiaPrimeiroRankEmissaoRelatorio);
                } else if(quantidadeDiasEmAtraso  ==  paramVP.getQtDiaSegdoRnkngEmissRelat().intValue()) {
                    quantDiaSegundoRankEmissaoRelatorio = quantDiaSegundoRankEmissaoRelatorio + 1;
                    atrasoTransmissaoAgrupamento.setQuantDiaSegundoRankEmissaoRelatorio(quantDiaSegundoRankEmissaoRelatorio);
                } else if(quantidadeDiasEmAtraso  >  paramVP.getQtDiaSegdoRnkngEmissRelat().intValue() && quantidadeDiasEmAtraso  <=  paramVP.getQtDiaTerceRnkngEmissRelat().intValue()) {
                    quantDiaTerceiroRankEmissaoRelatorio = quantDiaTerceiroRankEmissaoRelatorio + 1;
                    atrasoTransmissaoAgrupamento.setQuantDiaTerceiroRankEmissaoRelatorio(quantDiaTerceiroRankEmissaoRelatorio);
                } else if(quantidadeDiasEmAtraso  >  paramVP.getQtDiaTerceRnkngEmissRelat().intValue() && quantidadeDiasEmAtraso  <=  paramVP.getQtDiaQurtoRnkngEmissRelat().intValue()) {
                    quantDiaQuartoRankEmissaoRelatorio = quantDiaQuartoRankEmissaoRelatorio + 1;
                    atrasoTransmissaoAgrupamento.setQuantDiaQuartoRankEmissaoRelatorio(quantDiaQuartoRankEmissaoRelatorio);
                } else if(quantidadeDiasEmAtraso  >  paramVP.getQtDiaQurtoRnkngEmissRelat().intValue() && quantidadeDiasEmAtraso  <=  paramVP.getQtDiaUltmoRnkngEmissRelat().intValue()) {
                    quantDiaQuintoRankEmissaoRelatorio = quantDiaQuintoRankEmissaoRelatorio + 1;
                    atrasoTransmissaoAgrupamento.setQuantDiaQuintoRankEmissaoRelatorio(quantDiaQuintoRankEmissaoRelatorio);
                } else {
                    quantDiaUltimoRankEmissaoRelatorio = quantDiaUltimoRankEmissaoRelatorio + 1;
                    atrasoTransmissaoAgrupamento.setQuantUltimoRankEmissaoRelatorio(quantDiaUltimoRankEmissaoRelatorio);;
                }
            }
            codigoEmpresa = atrasoTransmissao.getCodigoEmpresa();
        }

        atrasoTransmissaoAgrupamentoList.add(atrasoTransmissaoAgrupamento);

        return atrasoTransmissaoAgrupamentoList;
    }

    private void somaQuantidadeRegistros(List<AtrasoTransmissaoAgrupamento> atrasoTransmissaoAgrupamentoList) {

        for(AtrasoTransmissaoAgrupamento atrasoTransmissaoAgrupamento : atrasoTransmissaoAgrupamentoList) {

            Long totalGeral = atrasoTransmissaoAgrupamento.getQuantDiaPrimeiroRankEmissaoRelatorio() +
                    atrasoTransmissaoAgrupamento.getQuantDiaSegundoRankEmissaoRelatorio() +
                    atrasoTransmissaoAgrupamento.getQuantDiaTerceiroRankEmissaoRelatorio() +
                    atrasoTransmissaoAgrupamento.getQuantDiaQuartoRankEmissaoRelatorio() +
                    atrasoTransmissaoAgrupamento.getQuantDiaQuintoRankEmissaoRelatorio() +
                    atrasoTransmissaoAgrupamento.getQuantUltimoRankEmissaoRelatorio();

            atrasoTransmissaoAgrupamento.setTotalGeral(totalGeral);
        }
    }

    private void setPorcentagemAtrasosTransmissaoAgrupamentoPrestadora(List<AtrasoTransmissaoAgrupamento> atrasoTransmissaoAgrupamentoList) {

        for(AtrasoTransmissaoAgrupamento atrasoTransmissaoAgrupamento : atrasoTransmissaoAgrupamentoList) {

            float porcentagemPrimeiroRankEmissaoRelatorio = atrasoTransmissaoAgrupamento.getQuantDiaPrimeiroRankEmissaoRelatorio() * 100.00f / atrasoTransmissaoAgrupamento.getTotalGeral();
            float porcentagemSegundoRankEmissaoRelatorio = atrasoTransmissaoAgrupamento.getQuantDiaSegundoRankEmissaoRelatorio() * 100.00f / atrasoTransmissaoAgrupamento.getTotalGeral();
            float porcentagemTerceiroRankEmissaoRelatorio = atrasoTransmissaoAgrupamento.getQuantDiaTerceiroRankEmissaoRelatorio() * 100.00f / atrasoTransmissaoAgrupamento.getTotalGeral();
            float porcentagemQuartoRankEmissaoRelatorio = atrasoTransmissaoAgrupamento.getQuantDiaQuartoRankEmissaoRelatorio() * 100.00f / atrasoTransmissaoAgrupamento.getTotalGeral();
            float porcentagemQuintoRankEmissaoRelatorio = atrasoTransmissaoAgrupamento.getQuantDiaQuintoRankEmissaoRelatorio() * 100.00f / atrasoTransmissaoAgrupamento.getTotalGeral();
            float porcentagemUltimoRankEmissaoRelatorio = atrasoTransmissaoAgrupamento.getQuantUltimoRankEmissaoRelatorio() * 100.00f / atrasoTransmissaoAgrupamento.getTotalGeral();

            atrasoTransmissaoAgrupamento.setPorcentagemPrimeiroRankEmissaoRelatorio(NumericUtil.trunc(porcentagemPrimeiroRankEmissaoRelatorio,2));
            atrasoTransmissaoAgrupamento.setPorcentagemSegundoRankEmissaoRelatorio(NumericUtil.trunc(porcentagemSegundoRankEmissaoRelatorio,2));
            atrasoTransmissaoAgrupamento.setPorcentagemTerceiroRankEmissaoRelatorio(NumericUtil.trunc(porcentagemTerceiroRankEmissaoRelatorio,2));
            atrasoTransmissaoAgrupamento.setPorcentagemQuartoRankEmissaoRelatorio(NumericUtil.trunc(porcentagemQuartoRankEmissaoRelatorio,2));
            atrasoTransmissaoAgrupamento.setPorcentagemQuintoRankEmissaoRelatorio(NumericUtil.trunc(porcentagemQuintoRankEmissaoRelatorio,2));
            atrasoTransmissaoAgrupamento.setPorcentagemUltimoRankEmissaoRelatorio(NumericUtil.trunc(porcentagemUltimoRankEmissaoRelatorio,2));
        }
    }

    public AtrasoTransmissaoAgrupamento getTotalAtrasosTransmissaoAgrupamentoPrestadora(List<AtrasoTransmissaoAgrupamento> atrasoTransmissaoList) {

        Long quantTotalPrimeiroRankEmissaoRelatorio = 0L;
        Long quantTotalSegundoRankEmissaoRelatorio = 0L;
        Long quantTotalTerceiroRankEmissaoRelatorio = 0L;
        Long quantTotalQuartoRankEmissaoRelatorio = 0L;
        Long quantTotalQuintoRankEmissaoRelatorio = 0L;
        Long quantTotalUltimoRankEmissaoRelatorio = 0L;
        Long quantTotalGeralRankEmissaoRelatorio = 0L;

        AtrasoTransmissaoAgrupamento totalAtrasoTransmissao = new AtrasoTransmissaoAgrupamento();

        if (atrasoTransmissaoList.size() == 0) return totalAtrasoTransmissao;

        for(AtrasoTransmissaoAgrupamento atrasoTransmissaoAgrupamento : atrasoTransmissaoList) {

            quantTotalPrimeiroRankEmissaoRelatorio = quantTotalPrimeiroRankEmissaoRelatorio + atrasoTransmissaoAgrupamento.getQuantDiaPrimeiroRankEmissaoRelatorio();
            totalAtrasoTransmissao.setQuantTotalPrimeiroRankEmissaoRelatorio(quantTotalPrimeiroRankEmissaoRelatorio);

            quantTotalSegundoRankEmissaoRelatorio = quantTotalSegundoRankEmissaoRelatorio + atrasoTransmissaoAgrupamento.getQuantDiaSegundoRankEmissaoRelatorio();
            totalAtrasoTransmissao.setQuantTotalSegundoRankEmissaoRelatorio(quantTotalSegundoRankEmissaoRelatorio);

            quantTotalTerceiroRankEmissaoRelatorio = quantTotalTerceiroRankEmissaoRelatorio + atrasoTransmissaoAgrupamento.getQuantDiaTerceiroRankEmissaoRelatorio();
            totalAtrasoTransmissao.setQuantTotalTerceiroRankEmissaoRelatorio(quantTotalTerceiroRankEmissaoRelatorio);

            quantTotalQuartoRankEmissaoRelatorio = quantTotalQuartoRankEmissaoRelatorio + atrasoTransmissaoAgrupamento.getQuantDiaQuartoRankEmissaoRelatorio();
            totalAtrasoTransmissao.setQuantTotalQuartoRankEmissaoRelatorio(quantTotalQuartoRankEmissaoRelatorio);

            quantTotalQuintoRankEmissaoRelatorio = quantTotalQuintoRankEmissaoRelatorio + atrasoTransmissaoAgrupamento.getQuantDiaQuintoRankEmissaoRelatorio();
            totalAtrasoTransmissao.setQuantTotalQuintoRankEmissaoRelatorio(quantTotalQuintoRankEmissaoRelatorio);

            quantTotalUltimoRankEmissaoRelatorio = quantTotalUltimoRankEmissaoRelatorio + atrasoTransmissaoAgrupamento.getQuantUltimoRankEmissaoRelatorio();
            totalAtrasoTransmissao.setQuantTotalUltimoRankEmissaoRelatorio(quantTotalUltimoRankEmissaoRelatorio);

            quantTotalGeralRankEmissaoRelatorio = quantTotalGeralRankEmissaoRelatorio + atrasoTransmissaoAgrupamento.getTotalGeral();
            totalAtrasoTransmissao.setTotalGeral(quantTotalGeralRankEmissaoRelatorio);
        }

        if(totalAtrasoTransmissao.getTotalGeral() == 0L) return null; // não pode deixar dividir por 0

        float porcentagemTotalPrimeiroRankEmissaoRelatorio = totalAtrasoTransmissao.getQuantTotalPrimeiroRankEmissaoRelatorio() * 100.00f / totalAtrasoTransmissao.getTotalGeral();
        float porcentagemTotalSegundoRankEmissaoRelatorio = totalAtrasoTransmissao.getQuantTotalSegundoRankEmissaoRelatorio() * 100.00f / totalAtrasoTransmissao.getTotalGeral();
        float porcentagemTotalTerceiroRankEmissaoRelatorio = totalAtrasoTransmissao.getQuantTotalTerceiroRankEmissaoRelatorio() * 100.00f / totalAtrasoTransmissao.getTotalGeral();
        float porcentagemTotalQuartoRankEmissaoRelatorio = totalAtrasoTransmissao.getQuantTotalQuartoRankEmissaoRelatorio() * 100.00f / totalAtrasoTransmissao.getTotalGeral();
        float porcentagemTotalQuintoRankEmissaoRelatorio = totalAtrasoTransmissao.getQuantTotalQuintoRankEmissaoRelatorio() * 100.00f / totalAtrasoTransmissao.getTotalGeral();
        float porcentagemTotalUltimoRankEmissaoRelatorio = totalAtrasoTransmissao.getQuantUltimoRankEmissaoRelatorio() * 100.00f / totalAtrasoTransmissao.getTotalGeral();

        totalAtrasoTransmissao.setPorcentagemPrimeiroRankEmissaoRelatorio(NumericUtil.trunc(porcentagemTotalPrimeiroRankEmissaoRelatorio,2));
        totalAtrasoTransmissao.setPorcentagemSegundoRankEmissaoRelatorio(NumericUtil.trunc(porcentagemTotalSegundoRankEmissaoRelatorio,2));
        totalAtrasoTransmissao.setPorcentagemTerceiroRankEmissaoRelatorio(NumericUtil.trunc(porcentagemTotalTerceiroRankEmissaoRelatorio,2));
        totalAtrasoTransmissao.setPorcentagemQuartoRankEmissaoRelatorio(NumericUtil.trunc(porcentagemTotalQuartoRankEmissaoRelatorio,2));
        totalAtrasoTransmissao.setPorcentagemQuintoRankEmissaoRelatorio(NumericUtil.trunc(porcentagemTotalQuintoRankEmissaoRelatorio,2));
        totalAtrasoTransmissao.setPorcentagemUltimoRankEmissaoRelatorio(NumericUtil.trunc(porcentagemTotalUltimoRankEmissaoRelatorio,2));

        return totalAtrasoTransmissao;
    }

    private void calculaPorcentagemGeral(List<DetalhamentoAtrasoTransmissaoTotal> detalhamentoTotalList) {

        for(DetalhamentoAtrasoTransmissaoTotal detalhamentoTotal : detalhamentoTotalList) {

            float porcentagemAteDois = detalhamentoTotal.getQuantidadeAteDois() * 100.00f / detalhamentoTotal.getTotalRegistros();
            float porcentagemAteTres = detalhamentoTotal.getQuantidadeAteTres() * 100.00f / detalhamentoTotal.getTotalRegistros();
            float procentagemMaiorTres = detalhamentoTotal.getQuantidadeMaiorTres() * 100.00f / detalhamentoTotal.getTotalRegistros();

            detalhamentoTotal.setPorcentagemAteDois(NumericUtil.trunc(porcentagemAteDois,2));
            detalhamentoTotal.setPorcentagemAteTres(NumericUtil.trunc(porcentagemAteTres,2));
            detalhamentoTotal.setPorcentagemMaiorTres(NumericUtil.trunc(procentagemMaiorTres,2));
        }

    }

}
