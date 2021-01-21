package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import br.com.tokiomarine.seguradora.ssv.vistoriaprevia.ws.model.ErroRetornoEnvioLaudo;
import br.com.tokiomarine.seguradora.ssv.vistoriaprevia.ws.model.ErroVP;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PrestadoraVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.RecepcaoLaudo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.LaudoVPTransmitidoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.LaudoTransmitido;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.LaudoTransmitidoFilter;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.VistoriaPreviaServicoException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LaudoTransmitidoRepositoryImpl;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.PrestadoraVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.RecepcaoLaudoRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.ws.dtos.EnvioLaudoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LaudoTransmitidoService {

    @Autowired
    private LaudoTransmitidoRepositoryImpl laudoTransmitidoRepository;

    @Autowired
    private RecepcaoLaudoRepository recepcaoLaudoRepository;

    @Autowired
    private PrestadoraVistoriaPreviaRepository prestadoraVistoriaPreviaRepository;

    @Autowired
    private PaginationService<LaudoTransmitido> laudoTransmitidoPaginationService;

    public Page<LaudoTransmitido> getLaudoTransmitidoListByFilter(LaudoTransmitidoFilter filter, Integer page, Integer size){

        List<LaudoTransmitido> laudoTransmitidoList = laudoTransmitidoRepository.getLaudoTransmitidoListByFilter(filter);

        Set<Long> ids = laudoTransmitidoList.stream().map(LaudoTransmitido::getCdAgrmtVspre).collect(Collectors.toSet());

        ids.forEach(id -> {
            final PrestadoraVistoriaPrevia prestadora = prestadoraVistoriaPreviaRepository
                    .findById(id)
                    .orElse(null);

            laudoTransmitidoList.stream()
                    .filter(laudo -> laudo.getCdAgrmtVspre().equals(id))
                    .forEach(laudo -> laudo.setNmPrestadora(laudo.getCdAgrmtVspre().toString()
                                                                    .concat(" - ")
                                                                    .concat(prestadora != null ? prestadora.getNmRazaoSocal() : "")));
        });

        return laudoTransmitidoPaginationService.listToPageImpl(size, page, laudoTransmitidoList);
    }

    public LaudoVPTransmitidoDTO getLaudoDetalhe(Long idLaudo){

        RecepcaoLaudo recepcaoLaudo = recepcaoLaudoRepository.findById(idLaudo).orElse(null);
        Object laudoObject;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(recepcaoLaudo.getCtObjetLaudo());
            ObjectInputStream ois = new ObjectInputStream(bais);
            laudoObject = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new VistoriaPreviaServicoException(500L, "Erro ao converter objeto [Recepcao Laudo]");
        }

        if(laudoObject instanceof br.com.tokiomarine.seguradora.ssv.vistoriaprevia.ws.model.LaudoVP){
            return LaudoVPTransmitidoDTO.buildFromLayoutAntigo(
                    (br.com.tokiomarine.seguradora.ssv.vistoriaprevia.ws.model.LaudoVP)laudoObject, recepcaoLaudo);
        }

        if(laudoObject instanceof br.com.tokiomarine.seguradora.vistoriaprevia.ws.dtos.LaudoVP) {
            return LaudoVPTransmitidoDTO.buildFromLayoutNovo(
                    (br.com.tokiomarine.seguradora.vistoriaprevia.ws.dtos.LaudoVP)laudoObject, recepcaoLaudo);
        }

        if(laudoObject instanceof br.com.tokiomarine.seguradora.vistoriaprevia.ws.agendamento.dtos.LaudoVP){
            return LaudoVPTransmitidoDTO.buildFromMobileLayout(
                    (br.com.tokiomarine.seguradora.vistoriaprevia.ws.agendamento.dtos.LaudoVP)laudoObject, recepcaoLaudo);
        }

        return null;
    }

    public List<ErroVP> getInconsistencias(Long idLaudoRecepcao){

        EnvioLaudoResponseDto.ErroRetornoEnvioLaudo erroRetornoEnvioLaudoWS;
        ErroRetornoEnvioLaudo erroInterno;
        br.com.tokiomarine.seguradora.vistoriaprevia.webservice.dtos.EnvioLaudoResponseDto.ErroRetornoEnvioLaudo erroRetornoEnvioLaudoMobile;

        List<ErroVP> listaErrosLaudo = new ArrayList<>();

        RecepcaoLaudo recepcaoLaudo = recepcaoLaudoRepository.findById(idLaudoRecepcao).orElse(null);
        Object laudoObject;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(recepcaoLaudo.getCtObjetIncon());
            ObjectInputStream ois = new ObjectInputStream(bais);
            laudoObject = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new VistoriaPreviaServicoException(500L, "Erro ao converter objeto [Recepcao Laudo]");
        }

            if(laudoObject instanceof EnvioLaudoResponseDto.ErroRetornoEnvioLaudo){
                erroRetornoEnvioLaudoWS = (EnvioLaudoResponseDto.ErroRetornoEnvioLaudo) laudoObject;

                if (erroRetornoEnvioLaudoWS.getErrosVP() != null) {

                    for(EnvioLaudoResponseDto.ErroRetornoEnvioLaudo.ErrosVP erroWS : erroRetornoEnvioLaudoWS.getErrosVP()){

                        ErroVP erroVP = new ErroVP();
                        erroVP.setAnoFabricacao(erroWS.getAnoFabricacao());
                        erroVP.setChassi(erroWS.getChassi());
                        erroVP.setCodigoAgrupamentoEmpresaVistoriadora(erroWS.getCodigoAgrupamentoEmpresaVistoriadora());
                        erroVP.setCodigoErro(erroWS.getCodigoErro());
                        erroVP.setCodigoFabricante(erroWS.getCodigoFabricante());
                        erroVP.setCodigoVeiculo(erroWS.getCodigoVeiculo());
                        erroVP.setDataRealizacaoVistoria(erroWS.getDataRealizacaoVistoria());
                        erroVP.setDataTransmissaoVistoria(erroWS.getDataTransmissaoVistoria());
                        erroVP.setDescricaoModelo(erroWS.getDescricaoModelo());
                        erroVP.setDescricaoMotivoErro(erroWS.getDescricaoMotivoErro());
                        erroVP.setDetalheErro1(erroWS.getDetalheErro1());
                        erroVP.setDetalheErro2(erroWS.getDetalheErro2());
                        erroVP.setIndicadorAceiteVistoria(erroWS.getIndicadorAceiteVistoria());
                        erroVP.setPlaca(erroWS.getPlaca());
                        erroVP.setStatusVistoria(erroWS.getStatusVistoria());
                        erroVP.setTipoCombustivel(erroWS.getTipoCombustivel());

                        listaErrosLaudo.add(erroVP);
                    }
                }

            } else if(laudoObject instanceof br.com.tokiomarine.seguradora.vistoriaprevia.webservice.dtos.EnvioLaudoResponseDto.ErroRetornoEnvioLaudo) {

                erroRetornoEnvioLaudoMobile = (br.com.tokiomarine.seguradora.vistoriaprevia.webservice.dtos.EnvioLaudoResponseDto.ErroRetornoEnvioLaudo) laudoObject;

                if (erroRetornoEnvioLaudoMobile.getErrosVP() != null) {

                    for(br.com.tokiomarine.seguradora.vistoriaprevia.webservice.dtos.EnvioLaudoResponseDto.ErroRetornoEnvioLaudo.ErrosVP erroWS : erroRetornoEnvioLaudoMobile.getErrosVP()){
                        ErroVP erroVP = new ErroVP();
                        erroVP.setAnoFabricacao(erroWS.getAnoFabricacao());
                        erroVP.setChassi(erroWS.getChassi());
                        erroVP.setCodigoErro(erroWS.getCodigoErro());
                        erroVP.setCodigoFabricante(erroWS.getCodigoFabricante());
                        erroVP.setCodigoVeiculo(erroWS.getCodigoVeiculo());
                        erroVP.setDataRealizacaoVistoria(erroWS.getDataRealizacaoVistoria());
                        erroVP.setDataTransmissaoVistoria(erroWS.getDataTransmissaoVistoria());
                        erroVP.setDescricaoModelo(erroWS.getDescricaoModelo());
                        erroVP.setDescricaoMotivoErro(erroWS.getDescricaoMotivoErro());
                        erroVP.setDetalheErro1(erroWS.getDetalheErro1());
                        erroVP.setDetalheErro2(erroWS.getDetalheErro2());
                        erroVP.setIndicadorAceiteVistoria(erroWS.getIndicadorAceiteVistoria());
                        erroVP.setPlaca(erroWS.getPlaca());
                        erroVP.setStatusVistoria(erroWS.getStatusVistoria());
                        erroVP.setTipoCombustivel(erroWS.getTipoCombustivel());
                        listaErrosLaudo.add(erroVP);
                    }
                }

            } else {
                erroInterno = (ErroRetornoEnvioLaudo) laudoObject;
                if (erroInterno.getErros() != null) {
                    listaErrosLaudo.addAll(Arrays.asList(erroInterno.getErros()));
                }
            }

        return listaErrosLaudo;
    }
}
