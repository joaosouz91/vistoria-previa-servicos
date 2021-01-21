package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PrestadoraVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.EstatisticaVistoriasRealizadas;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.EstatisticaVistoriasRealizadasRepositoryImpl;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesVistoriaPrevia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstatisticaVistoriasRealizadasService {

    @Autowired
    EstatisticaVistoriasRealizadasRepositoryImpl estatisticaVistoriasRealizadasRepo;

    @Autowired
    PrestadoraVistoriaPreviaService prestadoraVistoriaPreviaService;

    @Autowired
    PaginationService<EstatisticaVistoriasRealizadas> paginationService;


    public Page<EstatisticaVistoriasRealizadas> getEstatisticaVistoriasRealizadas(String periodo, Long cdPrestadora, Integer size, Integer pageIndex) {

        List<EstatisticaVistoriasRealizadas> estatisticaVistoriasRealizadasList =
                estatisticaVistoriasRealizadasRepo.getEstatisticaVistoriasRealizadas(periodo, cdPrestadora);

        if(estatisticaVistoriasRealizadasList.isEmpty()) return null;

        estatisticaVistoriasRealizadasList = agrupaPrestadoras(estatisticaVistoriasRealizadasList);

        return paginationService.listToPageImpl(size, pageIndex, estatisticaVistoriasRealizadasList);
    }

    public Page<EstatisticaVistoriasRealizadas> getDetalheEstatisticaVistoriasRealizadas(String periodo, Long cdPrestadora, String situacaoVistoria, Integer size, Integer pageIndex) {

        List<EstatisticaVistoriasRealizadas> detalhamentoEstatisticaVistoriasRealizadasList =
                estatisticaVistoriasRealizadasRepo.getDetalheEstatisticaVistoriasRealizadas(periodo, cdPrestadora, situacaoVistoria);

        if(detalhamentoEstatisticaVistoriasRealizadasList.isEmpty()) return null;

        setStatusVistoria(detalhamentoEstatisticaVistoriasRealizadasList);

        return paginationService.listToPageImpl(size, pageIndex, detalhamentoEstatisticaVistoriasRealizadasList);
    }

    private List<EstatisticaVistoriasRealizadas> agrupaPrestadoras(List<EstatisticaVistoriasRealizadas> estatisticaVistoriasRealizadasList) {

        Long codigoPrestadora           = null;
        Long quantidadeBloqueados       = 0L;
        Long quantidadeVinculados       = 0L;
        Long quantidadeNaoVinculados    = 0L;
        Long quantidadeAceitavel        = 0L;
        Long quantidadeSujetioAnalise   = 0L;
        Long quantidadeRecusado         = 0L;
        Long quantidadeLiberado         = 0L;
        Long quantidadeAceitacaoForcada = 0L;
        Long quantidadeRegularizado     = 0L;

        EstatisticaVistoriasRealizadas estatisticaVistoriasRealizadas = new EstatisticaVistoriasRealizadas();
        List<EstatisticaVistoriasRealizadas> estatisticasRealizadas = new ArrayList<>();

        for(EstatisticaVistoriasRealizadas estatisticaVistorias : estatisticaVistoriasRealizadasList) {

            if(codigoPrestadora == null || estatisticaVistorias.getCodigoPrestadora().equals(codigoPrestadora)) {

                estatisticaVistoriasRealizadas.setCodigoPrestadora(estatisticaVistorias.getCodigoPrestadora());

                // soma a quantidade de laudos bloqueados
                if(estatisticaVistorias.getSituacaoBloqueio().equals(1L)) {
                    quantidadeBloqueados = quantidadeBloqueados + 1;
                    estatisticaVistoriasRealizadas.setQuantLaudosBloqueados(quantidadeBloqueados);

                } else if ("S".equals(estatisticaVistorias.getIndicadorLaudoVinculado())) {
                    // soma a quantidade de laudos vinculados
                    quantidadeVinculados = quantidadeVinculados + 1;
                    estatisticaVistoriasRealizadas.setQuantLaudosVinculados(quantidadeVinculados);

                } else {
                    // soma a quantidade de laudos não vinculados
                    quantidadeNaoVinculados =  quantidadeNaoVinculados + 1;
                    estatisticaVistoriasRealizadas.setQuantLaudosNaoVinculados(quantidadeNaoVinculados);
                }

                // soma a quantidade de laudos aceitavel
                if(ConstantesVistoriaPrevia.LAUDO_STATUS_ACEITAVEL.equals(estatisticaVistorias.getSituacaoLaudo())) {
                    quantidadeAceitavel =  quantidadeAceitavel + 1;
                    estatisticaVistoriasRealizadas.setQuantAceitavel(quantidadeAceitavel);
                }

                // soma a quantidade de laudos sujeito a analise
                if(ConstantesVistoriaPrevia.SITUACAO_VISTORIA_PREVIA_SUJEITO_ANALISE.equals(estatisticaVistorias.getSituacaoLaudo())) {
                    quantidadeSujetioAnalise =  quantidadeSujetioAnalise + 1;
                    estatisticaVistoriasRealizadas.setQuantSujeitoAnalise(quantidadeSujetioAnalise);
                }

                // soma a quantidade de laudos recusados
                if(ConstantesVistoriaPrevia.SITUACAO_VISTORIA_PREVIA_RECUSADA.equals(estatisticaVistorias.getSituacaoLaudo())) {
                    quantidadeRecusado =  quantidadeRecusado + 1;
                    estatisticaVistoriasRealizadas.setQuantRecusados(quantidadeRecusado);
                }

                // soma a quantidade de laudos liberados
                if(ConstantesVistoriaPrevia.SITUACAO_VISTORIA_PREVIA_LIBERADA.equals(estatisticaVistorias.getSituacaoLaudo())) {
                    quantidadeLiberado = quantidadeLiberado + 1;
                    estatisticaVistoriasRealizadas.setQuantLiberado(quantidadeLiberado);
                }

                // soma a quantidade de laudos aceitacaoForcada
                if(ConstantesVistoriaPrevia.SITUACAO_VISTORIA_PREVIA_ACEITACAO_FORCADA.equals(estatisticaVistorias.getSituacaoLaudo())) {
                    quantidadeAceitacaoForcada = quantidadeAceitacaoForcada + 1;
                    estatisticaVistoriasRealizadas.setQuantAceitacaoForcada(quantidadeAceitacaoForcada);
                }
                // soma a quantidade de laudos Regularizados
                if(ConstantesVistoriaPrevia.SITUACAO_VISTORIA_PREVIA_REGULARIZADO.equals(estatisticaVistorias.getSituacaoLaudo())) {
                    quantidadeRegularizado++;
                    estatisticaVistoriasRealizadas.setQuantRegularizado(quantidadeRegularizado);
                }
            } else {

                estatisticasRealizadas.add(estatisticaVistoriasRealizadas);

                quantidadeBloqueados    = 0L;
                quantidadeVinculados    = 0L;
                quantidadeNaoVinculados = 0L;

                quantidadeAceitavel        = 0L;
                quantidadeSujetioAnalise   = 0L;
                quantidadeRecusado         = 0L;
                quantidadeLiberado         = 0L;
                quantidadeAceitacaoForcada = 0L;
                quantidadeRegularizado      = 0L;
                estatisticaVistoriasRealizadas = new EstatisticaVistoriasRealizadas();

                estatisticaVistoriasRealizadas.setCodigoPrestadora(estatisticaVistorias.getCodigoPrestadora());

                // soma a quantidade de laudos bloqueados
                if(estatisticaVistorias.getSituacaoBloqueio().equals(1L)) {
                    quantidadeBloqueados = quantidadeBloqueados + 1;
                    estatisticaVistoriasRealizadas.setQuantLaudosBloqueados(quantidadeBloqueados);

                } else if ("S".equals(estatisticaVistorias.getIndicadorLaudoVinculado())) {
                    // soma a quantidade de laudos vinculados
                    quantidadeVinculados = quantidadeVinculados + 1;
                    estatisticaVistoriasRealizadas.setQuantLaudosVinculados(quantidadeVinculados);

                } else {
                    // soma a quantidade de laudos não vinculados
                    quantidadeNaoVinculados =  quantidadeNaoVinculados + 1;
                    estatisticaVistoriasRealizadas.setQuantLaudosNaoVinculados(quantidadeNaoVinculados);
                }

                // soma a quantidade de laudos aceitavel
                if(ConstantesVistoriaPrevia.LAUDO_STATUS_ACEITAVEL.equals(estatisticaVistorias.getSituacaoLaudo())) {
                    quantidadeAceitavel = quantidadeAceitavel + 1;
                    estatisticaVistoriasRealizadas.setQuantAceitavel(quantidadeAceitavel);
                }

                // soma a quantidade de laudos sujeito a analise
                if(ConstantesVistoriaPrevia.SITUACAO_VISTORIA_PREVIA_SUJEITO_ANALISE.equals(estatisticaVistorias.getSituacaoLaudo())) {
                    quantidadeSujetioAnalise = quantidadeSujetioAnalise + 1;
                    estatisticaVistoriasRealizadas.setQuantSujeitoAnalise(quantidadeSujetioAnalise);
                }

                // soma a quantidade de laudos recusados
                if(ConstantesVistoriaPrevia.SITUACAO_VISTORIA_PREVIA_RECUSADA.equals(estatisticaVistorias.getSituacaoLaudo())) {
                    quantidadeRecusado = quantidadeRecusado + 1;
                    estatisticaVistoriasRealizadas.setQuantRecusados(quantidadeRecusado);
                }

                // soma a quantidade de laudos liberados
                if(ConstantesVistoriaPrevia.SITUACAO_VISTORIA_PREVIA_LIBERADA.equals(estatisticaVistorias.getSituacaoLaudo())) {
                    quantidadeLiberado = quantidadeLiberado + 1;
                    estatisticaVistoriasRealizadas.setQuantLiberado(quantidadeLiberado);
                }

                // soma a quantidade de laudos aceitacaoForcada
                if(ConstantesVistoriaPrevia.SITUACAO_VISTORIA_PREVIA_ACEITACAO_FORCADA.equals(estatisticaVistorias.getSituacaoLaudo())) {
                    quantidadeAceitacaoForcada = quantidadeAceitacaoForcada + 1;
                    estatisticaVistoriasRealizadas.setQuantAceitacaoForcada(quantidadeAceitacaoForcada);
                }
                // soma a quantidade de laudos aceitacaoForcada
                if(ConstantesVistoriaPrevia.SITUACAO_VISTORIA_PREVIA_REGULARIZADO.equals(estatisticaVistorias.getSituacaoLaudo())) {
                    quantidadeRegularizado = quantidadeRegularizado + 1;
                    estatisticaVistoriasRealizadas.setQuantRegularizado(quantidadeRegularizado);
                }
            }
            codigoPrestadora = estatisticaVistorias.getCodigoPrestadora();
        }

        estatisticasRealizadas.add(estatisticaVistoriasRealizadas);
        somaVistoriasRealizadas(estatisticasRealizadas);

        return estatisticasRealizadas;

    }

    private void somaVistoriasRealizadas(List<EstatisticaVistoriasRealizadas> estatisticaVistoriasRealizadasList) {

        for (EstatisticaVistoriasRealizadas estatisticaVistoriasRealizadas : estatisticaVistoriasRealizadasList) {
            // obtem o nome da prestadora
            PrestadoraVistoriaPrevia prestadora = prestadoraVistoriaPreviaService.obterPrestadoraPorId(estatisticaVistoriasRealizadas.getCodigoPrestadora());
            estatisticaVistoriasRealizadas.setNomePrestadora(prestadora != null ? prestadora.getNmRazaoSocal() : null);

            Long totalSituacaoLaudo = estatisticaVistoriasRealizadas.getQuantLaudosBloqueados() +  estatisticaVistoriasRealizadas.getQuantLaudosVinculados() +  estatisticaVistoriasRealizadas.getQuantLaudosNaoVinculados();
            estatisticaVistoriasRealizadas.setTotalSituacaoLaudo(totalSituacaoLaudo);

            Long totalStatusLaudo = estatisticaVistoriasRealizadas.getQuantAceitavel() + estatisticaVistoriasRealizadas.getQuantSujeitoAnalise() + estatisticaVistoriasRealizadas.getQuantRecusados() + estatisticaVistoriasRealizadas.getQuantLiberado() + estatisticaVistoriasRealizadas.getQuantAceitacaoForcada() +  + estatisticaVistoriasRealizadas.getQuantRegularizado();
            estatisticaVistoriasRealizadas.setTotalStatusLaudo(totalStatusLaudo);
        }
    }

    private void setStatusVistoria(List<EstatisticaVistoriasRealizadas> detalhamentoVistoriasRealizadasList) {
        for(EstatisticaVistoriasRealizadas estatistica : detalhamentoVistoriasRealizadasList) {
            if(estatistica.getSituacaoBloqueio() == 1L) {
                estatistica.setStatusVistoria("Bloqueado");
            } else if(estatistica.getNumeroItem() == null || estatistica.getNumeroItem() == 0L) {
                estatistica.setStatusVistoria("Não Vinculado");
            } else {
                estatistica.setStatusVistoria("Vinculado");
            }
        }
    }

}
