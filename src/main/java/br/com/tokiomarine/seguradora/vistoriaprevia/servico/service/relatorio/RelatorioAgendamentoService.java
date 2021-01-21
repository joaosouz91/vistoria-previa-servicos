package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.relatorio;

import br.com.tokiomarine.seguradora.ssv.transacional.model.Conveniado;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PrestadoraVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PrestadoraStatusDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.*;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.RelatorioAgendamentoFilter;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.RelatorioAgendamentoRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.TotalAgendamentoStatusLocalRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.TotalStatusPrestadoraRepositoryImpl;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.PaginationService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RelatorioAgendamentoService {

    @Autowired
    private RelatorioAgendamentoRepository relatorioAgendamentoRepository;

    @Autowired
    private TotalStatusPrestadoraRepositoryImpl totalStatusPrestadoraRepository;

    @Autowired
    private TotalAgendamentoStatusLocalRepository totalAgendamentoStatusLocalRepository;

    @Autowired
    private PaginationService<RelatorioAgendamento> relAgendamentoPaginatioService;

    @Autowired
    private PaginationService<PrestadoraStatus> totalStatusPrestadoraPaginationService;


    public Page<RelatorioAgendamento> recuperarRelatorioRejeitadosFrustradosByFilter(RelatorioAgendamentoFilter filter, Integer page, Integer size) {
        trataFiltro(filter);
        List<RelatorioAgendamento> relatorioAgendamentoList = relatorioAgendamentoRepository.findRelatorioAgendamentoByFilter(filter, false, false, true);
        setDescricaoSituacaoAgendamento(relatorioAgendamentoList);
        return relAgendamentoPaginatioService.listToPageImpl(size, page, relatorioAgendamentoList);
    }

    public Page<RelatorioAgendamento> recuperarRelatorioHistoricoAgendamentosByFilter(RelatorioAgendamentoFilter filter, Integer page, Integer size) {
        trataFiltro(filter);
        validaFiltro(filter);
        List<RelatorioAgendamento> relatorioAgendamentoList = relatorioAgendamentoRepository.findRelatorioAgendamentoByFilter(filter, true, false, false);
        setDescricaoSituacaoAgendamento(relatorioAgendamentoList);
        return relAgendamentoPaginatioService.listToPageImpl(size, page, relatorioAgendamentoList);
    }

    public Page<RelatorioAgendamento> recuperarRelatorioSituacaoAgendamentoByFilter(RelatorioAgendamentoFilter filter, Integer page, Integer size) {
        trataFiltro(filter);
        validaFiltro(filter);
        List<RelatorioAgendamento> relatorioAgendamentoList = relatorioAgendamentoRepository.findRelatorioAgendamentoByFilter(filter, false, true, false);
        relatorioAgendamentoList.forEach(rel -> rel.setDsSitucAgend(this.retornaDescSitucAgendamento(rel.getCdSitucAgmto())));
        return relAgendamentoPaginatioService.listToPageImpl(size, page, relatorioAgendamentoList);
    }

    public PrestadoraStatusDTO recuperarTotalStatusPrestadoraByFilter(RelatorioAgendamentoFilter filter, Integer page, Integer size) {

        trataFiltro(filter);
        validaFiltro(filter);

        Long prestadoraAnterior = -1l;
        PrestadoraStatus prestadoraStatus = null;
        List<PrestadoraStatus> listaPrestadoraStatus = null;
        PrestadoraStatus totalPrestadoraStatus = new PrestadoraStatus();

        List<TotalStatusPrestadora> totalStatusPrestadoraList = totalStatusPrestadoraRepository.findTotalStatusPrestadoraByFilter(filter);

        for (TotalStatusPrestadora total : totalStatusPrestadoraList) {

            if (!prestadoraAnterior.equals(total.getCdAgrmtVspre())) {

                prestadoraAnterior = total.getCdAgrmtVspre();
                prestadoraStatus = new PrestadoraStatus();

                PrestadoraVistoriaPrevia prestadora = new PrestadoraVistoriaPrevia();
                prestadora.setCdAgrmtVspre(total.getCdAgrmtVspre());
                prestadora.setNmRazaoSocal(total.getNmRazaoSocialPrta());

                prestadoraStatus.setPrestadora(prestadora);

                if (listaPrestadoraStatus == null) {
                    listaPrestadoraStatus = new ArrayList<PrestadoraStatus>();
                }
                listaPrestadoraStatus.add(prestadoraStatus);
            }

            try {
                //* Set quantidade de cada situação por prestadora.
                Method metodo = prestadoraStatus.getClass().getMethod("getContStatus" + total.getCdSitucAgmto());
                Long contador = (Long) metodo.invoke(prestadoraStatus);
                contador = contador + total.getQtdSitucAgto();
                prestadoraStatus.getClass().getMethod("setContStatus" + total.getCdSitucAgmto(), metodo.getReturnType()).invoke(prestadoraStatus, contador);

                //* Set total de cada prestadora
                metodo = prestadoraStatus.getClass().getMethod("getTotalAgtoPrestadora");
                contador = (Long) metodo.invoke(prestadoraStatus);
                contador = contador + total.getQtdPrtraAgto();
                prestadoraStatus.getClass().getMethod("setTotalAgtoPrestadora", metodo.getReturnType()).invoke(prestadoraStatus, contador);

                //* Set total de cada situação
                metodo = totalPrestadoraStatus.getClass().getMethod("getContStatus" + total.getCdSitucAgmto());
                contador = (Long) metodo.invoke(totalPrestadoraStatus);
                contador = contador + total.getQtdSitucAgto();
                totalPrestadoraStatus.getClass().getMethod("setContStatus" + total.getCdSitucAgmto(), metodo.getReturnType()).invoke(totalPrestadoraStatus, contador);

                //* Set total agendamentos
                metodo = totalPrestadoraStatus.getClass().getMethod("getTotalAgtoPrestadora");
                contador = (Long) metodo.invoke(totalPrestadoraStatus);
                contador = contador + total.getQtdPrtraAgto();
                totalPrestadoraStatus.getClass().getMethod("setTotalAgtoPrestadora", metodo.getReturnType()).invoke(totalPrestadoraStatus, contador);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new PrestadoraStatusDTO(listaPrestadoraStatus, totalPrestadoraStatus);
    }

    public List<TotalAgendamentoStatusLocal> recuperarTotalAgendamentoStatusLocal(Long codCorretor, String periodo) {

        List<TotalAgendamentoStatusLocal> totalStatus;

        Date dataDe = UtilNegocio.obterDataInicioPesquisa(Long.valueOf(periodo.substring(0,2)), Long.valueOf(periodo.substring(2,6)));
        Date dataAte = UtilNegocio.obterDataFimPesquisa(Long.valueOf(periodo.substring(0,2)), Long.valueOf(periodo.substring(2,6)));

        totalStatus = totalAgendamentoStatusLocalRepository.recuperarTotalStatusLocal(codCorretor, dataDe, dataAte);

        List<TotalAgendamentoStatusLocal> retornoTotalStatus = new ArrayList<>();

        Long quantidadePostoRealizado = 0L;
        Long quantidadeDomicilioRealizado = 0L;

        for(TotalAgendamentoStatusLocal statusLocal : totalStatus){
            if(statusLocal.getCdSitucAgmto().equals(ConstantesVistoriaPrevia.COD_SITUC_FIM) &&
                    statusLocal.getTipoAgendamento().equals(ConstantesVistoriaPrevia.COD_LOCAL_POSTO)	){

                quantidadePostoRealizado = statusLocal.getQuantidadeSituacaoStatus();

            } else if(statusLocal.getCdSitucAgmto().equals(ConstantesVistoriaPrevia.COD_SITUC_FIM) &&
                    statusLocal.getTipoAgendamento().equals(ConstantesVistoriaPrevia.COD_LOCAL_DOMICILIO)) {

                quantidadeDomicilioRealizado = statusLocal.getQuantidadeSituacaoStatus();
            }
        }

        for(TotalAgendamentoStatusLocal statusLocal : totalStatus){

            if(statusLocal.getCdSitucAgmto().equals(ConstantesVistoriaPrevia.COD_SITUC_REALIZADA) &&
                    statusLocal.getTipoAgendamento().equals(ConstantesVistoriaPrevia.COD_LOCAL_POSTO)){

                statusLocal.setQuantidadeSituacaoStatus(statusLocal.getQuantidadeSituacaoStatus() + quantidadePostoRealizado);

            } else if(statusLocal.getCdSitucAgmto().equals(ConstantesVistoriaPrevia.COD_SITUC_REALIZADA) &&
                    statusLocal.getTipoAgendamento().equals(ConstantesVistoriaPrevia.COD_LOCAL_DOMICILIO)) {

                statusLocal.setQuantidadeSituacaoStatus(statusLocal.getQuantidadeSituacaoStatus() + quantidadeDomicilioRealizado);

            }

            if(!statusLocal.getCdSitucAgmto().equals(ConstantesVistoriaPrevia.COD_SITUC_FIM)){
                retornoTotalStatus.add(statusLocal);
            }
        }

        return retornoTotalStatus;
    }

    private void validaFiltro(RelatorioAgendamentoFilter filter) {
        LocalDateTime dataInicio = filter.getDataPesquisaDe().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime dataFim = filter.getDataPesquisaAte().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        long diasTotais = Duration.between(dataInicio, dataFim).toDays() + 1;

        if (diasTotais > 2
                && filter.getNumPlaca() == null
                && filter.getNumVoucher() == null
                && filter.getCodCorretor() == null) {
            throw new BusinessVPException("Para um período maior que dois (2) dias é necessário escolher um ou mais filtros a seguir: [Número da Placa], [Número do Voucher], [Código do Corretor].");
        }
    }

    private void setDescricaoSituacaoAgendamento(List<RelatorioAgendamento> relatorioAgendamentoList) {
        for (RelatorioAgendamento ra : relatorioAgendamentoList) {
            if (ra.getIdVspreObgta() == null && ra.getCdVouch() != null) {
                this.recuperarVpAgtoFilho(ra);
            }
            ra.setDsSitucAgend(this.retornaDescSitucAgendamento(ra.getCdSitucAgmto()));
        }
    }

    private void recuperarVpAgtoFilho(RelatorioAgendamento relatorioAgendamento) {

        String voucherPai = relatorioAgendamento.getCdVouch();
        String voucherFilho;

        VistoriaPreviaObrigatoria vp = null;
        VistoriaPreviaCorretor vpCorretor;
        Conveniado corretorSSV5046 = null;

        String voucherPosterior = null;

        //* tenta encontrar um voucher na 425 com vp. (Para casos de reagendamento)
        boolean temAgtoFilho = true;

        while (temAgtoFilho) {

            if(voucherPai != null) {
                voucherFilho = relatorioAgendamentoRepository.recuperAgtoFilho(voucherPai); //*buscar como codigo anterior na 425 usando voucherPai
                if(voucherFilho == null) break;
            } else {
                break;
            }

            if (voucherPosterior == null) {
                voucherPosterior = voucherFilho;
                relatorioAgendamento.setCdVouchPosterior(voucherFilho);
            }

            if (!voucherFilho.equals("")) {
                temAgtoFilho = false;
            } else {
                //* algoritmo é executado até encontrar vpCorretor usando voucherFilho.
                //* lembrando que pode ter ocorrido vários reagendamentos, ou seja
                //* pode haver filhos que também não tem relacionamento direto com a 424

                vpCorretor = relatorioAgendamentoRepository.obterPreAgtoCorretorVoucher(voucherFilho);

                if (vpCorretor != null) {
                    vp = vpCorretor.getVistoriaPrevia();
                    corretorSSV5046 = vpCorretor.getCorretor();
                }

                if (vp != null) {

                    relatorioAgendamento.setIdVspreObgta(vp.getIdVspreObgta());
                    relatorioAgendamento.setCdChassiVeicu(vp.getCdChassiVeicu());
                    relatorioAgendamento.setCdCrtorCia(vp.getCdCrtorCia());
                    if (corretorSSV5046 != null && corretorSSV5046.getNmConvo() != null && !corretorSSV5046.getNmConvo().equals("")) {
                        relatorioAgendamento.setNmCrtorCia(corretorSSV5046.getNmConvo());
                    } else {
                        relatorioAgendamento.setNmCrtorCia("NOME_NAO_ENCONTRADO");
                    }

                    relatorioAgendamento.setCdPlacaVeicu(vp.getCdPlacaVeicu());
                    relatorioAgendamento.setNmClien(vp.getNmClien());
                    relatorioAgendamento.setNrCpfCnpjClien(vp.getNrCpfCnpjClien());
                    temAgtoFilho = false;
                }

                voucherPai = voucherFilho;
            }
        }
    }

    private String retornaDescSitucAgendamento(String cdSitucAgend) {

        String dsSitucAgend = "Agendar";

        if ("AGD".equals(cdSitucAgend)) {
            dsSitucAgend = "Agendada";
        } else if ("RCB".equals(cdSitucAgend)) {
            dsSitucAgend = "Aguardando Confirmação";
        } else if ("PEN".equals(cdSitucAgend)) {
            dsSitucAgend = "Aguardando Confirmação";
        } else if ("NAP".equals(cdSitucAgend)) {
            dsSitucAgend = "Não Aprovada";
        } else if ("VFR".equals(cdSitucAgend)) {
            dsSitucAgend = "Frustrada";
        } else if ("RLZ".equals(cdSitucAgend)) {
            dsSitucAgend = "Realizada";
        } else if ("CAN".equals(cdSitucAgend)) {
            dsSitucAgend = "Cancelada";
        } else if ("NAG".equals(cdSitucAgend)) {
            dsSitucAgend = "Não Agendada";
        } else if ("RGD".equals(cdSitucAgend)) {
            dsSitucAgend = "Reagendada";
        } else if ("FIM".equals(cdSitucAgend) || "FIN".equals(cdSitucAgend)) {
            dsSitucAgend = "Finalizada";
        } else if ("FTR".equals(cdSitucAgend)) {
            dsSitucAgend = "Fotos Recepcionadas";
        } else if ("PEF".equals(cdSitucAgend)) {
            dsSitucAgend = "Pendência de Fotos";
        } else if ("LKX".equals(cdSitucAgend)) {
            dsSitucAgend = "Link Expirado";
        } else if ("FTT".equals(cdSitucAgend)) {
            dsSitucAgend = "Foto Transmitida";
        }

        return dsSitucAgend;
    }

    private void trataFiltro(RelatorioAgendamentoFilter filter) {
        if("".equals(filter.getNumVoucher())) filter.setNumVoucher(null);
        if(Long.valueOf(0L).equals(filter.getIdPrestadora())) filter.setIdPrestadora(null);
        if(Long.valueOf(0).equals(filter.getCodCorretor())) filter.setCodCorretor(null);
        if("".equals(filter.getNumPlaca())) filter.setNumPlaca(null);
        if("".equals(filter.getFormaAgrupamento())) filter.setFormaAgrupamento(null);
        if("".equals(filter.getCodSitVistoria())) filter.setCodSitVistoria(null);
    }


}
