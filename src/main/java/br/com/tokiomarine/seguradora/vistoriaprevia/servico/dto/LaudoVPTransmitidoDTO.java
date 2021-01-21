package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.RecepcaoLaudo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.VistoriaMobileEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.VistoriaPreviaServicoException;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LaudoVPTransmitidoDTO {

    private DadosCabecalhoLaudoVPTransmitidoDTO dadosCabecalhoDTO;
    private DadosBasicosLaudoVPTransmitidoDTO dadosBasicosDTO;
    private ProponenteLaudoVPTransmitidoDTO proponenteDTO;
    private VeiculoLaudoVPTransmitidoDTO veiculoDTO;
    private ProprietarioVeiculoLaudoVPTransmitidoDTO proprietarioDTO;
    private CondutorLaudoVPTransmitidoDTO condutorDTO;
    private List<Long> codigosInformacoesTecnicas;
    private List<LinkFotoVPTransmitidoDTO> linkFotoVPTransmitidoDTOList;

    public static LaudoVPTransmitidoDTO buildFromLayoutAntigo(
            br.com.tokiomarine.seguradora.ssv.vistoriaprevia.ws.model.LaudoVP laudoVP,
            RecepcaoLaudo recepcaoLaudo){

        List<Long> codigosInformacoesTecnicas = Arrays.stream(laudoVP.getCodigosInformacoesTecnicas()).collect(Collectors.toList());

        return LaudoVPTransmitidoDTO.builder()
                .dadosCabecalhoDTO(DadosCabecalhoLaudoVPTransmitidoDTO.builder()
                        .dataTransmissao(recepcaoLaudo.getDtRcpaoLaudo())
                        .codigoLaudo(laudoVP.getNumeroLaudo())
                        .codigoVoucher(laudoVP.getCodigoVoucher())
                        .build())
                .dadosBasicosDTO(DadosBasicosLaudoVPTransmitidoDTO.builder()
                        .cidadeDestinoVistoria(laudoVP.getCidadeDestinoVistoria())
                        .cidadeOrigemVistoria(laudoVP.getCidadeOrigemVistoria())
                        .codigoCompanhia(laudoVP.getCodigoCompanhia())
                        .codigoCorretorInterno(laudoVP.getCodigoCorretorInterno())
                        .codigoPostoVistoria(laudoVP.getCodigoPostoVistoria())
                        .codigoSituacao(laudoVP.getCodigoSituacao())
                        .codigoVistoriador(laudoVP.getCodigoVistoriador())
                        .dataCadastro(laudoVP.getDataCadastro())
                        .dataVistoria(laudoVP.getDataVistoria())
                        .horaVistoria(laudoVP.getHoraVistoria())
                        .indicadorPostoDomicilio(laudoVP.getIndicadorPostoDomicilio())
                        .indicadorVistoriaFrustrada(laudoVP.getIndicadorVistoriaFrustrada())
                        .kmRealizacaoVistoria(laudoVP.getKmRealizacaoVistoria())
                        .nomeSolicitanteVistoria(laudoVP.getNomeSolicitanteVistoria())
                        .observacao(laudoVP.getObservacao())
                        .statusVistoria(laudoVP.getStatusVistoria())
                        .validacao(laudoVP.getValidacao())
                        .ufRealizacaoVistoria(laudoVP.getUfRealizacaoVistoria())
                        .mobile(VistoriaMobileEnum.NAO)
                        .build())
                .codigosInformacoesTecnicas(codigosInformacoesTecnicas)
                .condutorDTO(CondutorLaudoVPTransmitidoDTO.buildFromLayoutAntigo(laudoVP.getCondutor()))
                .proponenteDTO(ProponenteLaudoVPTransmitidoDTO.buildFromLayoutAntigo(laudoVP.getProponente()))
                .proprietarioDTO(ProprietarioVeiculoLaudoVPTransmitidoDTO.buildFromLayoutAntigo(laudoVP.getProprietario()))
                .veiculoDTO(VeiculoLaudoVPTransmitidoDTO.buildFromLayoutAntigo(laudoVP.getVeiculo()))
                .linkFotoVPTransmitidoDTOList(null)
                .build();
    }

    public static LaudoVPTransmitidoDTO buildFromLayoutNovo(br.com.tokiomarine.seguradora.vistoriaprevia.ws.dtos.LaudoVP laudoVP, RecepcaoLaudo recepcaoLaudo){

        return LaudoVPTransmitidoDTO.builder()
                .dadosCabecalhoDTO(DadosCabecalhoLaudoVPTransmitidoDTO.builder()
                        .dataTransmissao(recepcaoLaudo.getDtRcpaoLaudo())
                        .codigoLaudo(laudoVP.getNumeroLaudo())
                        .codigoVoucher(laudoVP.getCodigoVoucher())
                        .build())
                .dadosBasicosDTO(DadosBasicosLaudoVPTransmitidoDTO.builder()
                        .cepLocalVistoria(laudoVP.getCepLocalVistoria())
                        .cidadeDestinoVistoria(laudoVP.getCidadeDestinoVistoria())
                        .cidadeOrigemVistoria(laudoVP.getCidadeOrigemVistoria())
                        .codigoCompanhia(laudoVP.getCodigoCompanhia())
                        .codigoCorretorInterno(laudoVP.getCodigoCorretorInterno())
                        .codigoPostoVistoria(laudoVP.getCodigoPostoVistoria())
                        .codigoSituacao(laudoVP.getCodigoSituacao())
                        .codigoVistoriador(laudoVP.getCodigoVistoriador())
                        .dataCadastro(laudoVP.getDataCadastro())
                        .dataVistoria(laudoVP.getDataVistoria())
                        .horaVistoria(laudoVP.getHoraVistoria())
                        .indicadorPostoDomicilio(laudoVP.getIndicadorPostoDomicilio())
                        .indicadorVistoriaFrustrada(laudoVP.getIndicadorVistoriaFrustrada())
                        .kmRealizacaoVistoria(laudoVP.getKmRealizacaoVistoria())
                        .nomeSolicitanteVistoria(laudoVP.getNomeSolicitanteVistoria())
                        .observacao(laudoVP.getObservacao())
                        .statusVistoria(laudoVP.getStatusVistoria())
                        .validacao(laudoVP.getValidacao())
                        .ufRealizacaoVistoria(laudoVP.getUfRealizacaoVistoria())
                        .mobile(VistoriaMobileEnum.NAO)
                        .build())
                .codigosInformacoesTecnicas(laudoVP.getCodigosInformacoesTecnicas())
                .condutorDTO(CondutorLaudoVPTransmitidoDTO.buildFromLayoutNovo(laudoVP.getCondutor()))
                .proponenteDTO(ProponenteLaudoVPTransmitidoDTO.buildFromLayoutNovo(laudoVP.getProponente()))
                .proprietarioDTO(ProprietarioVeiculoLaudoVPTransmitidoDTO.buildFromLayoutNovo(laudoVP.getProprietario()))
                .veiculoDTO(VeiculoLaudoVPTransmitidoDTO.buildFromLayoutNovo(laudoVP.getVeiculo()))
                .linkFotoVPTransmitidoDTOList(null)
                .build();
    }

    public static LaudoVPTransmitidoDTO buildFromMobileLayout(br.com.tokiomarine.seguradora.vistoriaprevia.ws.agendamento.dtos.LaudoVP laudoVP, RecepcaoLaudo recepcaoLaudo){

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

        Date dataVistoria;

        try {
            dataVistoria = sdf.parse(laudoVP.getDataVistoria());
        } catch (ParseException e) {
            throw new VistoriaPreviaServicoException(500L, "Erro ao converter o campo: [Data Vistoria]");
        }

        List<LinkFotoVPTransmitidoDTO> linkFotoVistoriaPreviaTransmitidoDTOList =
                laudoVP.getLinkFotoVistoria()
                        .stream()
                        .map(LinkFotoVPTransmitidoDTO::buildFromLayoutMobile)
                        .collect(Collectors.toList());

        return LaudoVPTransmitidoDTO.builder()
                .dadosCabecalhoDTO(DadosCabecalhoLaudoVPTransmitidoDTO.builder()
                        .dataTransmissao(recepcaoLaudo.getDtRcpaoLaudo())
                        .codigoLaudo(laudoVP.getNumeroLaudo())
                        .codigoVoucher(laudoVP.getCodigoVoucher())
                        .build())
                .dadosBasicosDTO(DadosBasicosLaudoVPTransmitidoDTO.builder()
                        .dataVistoria(dataVistoria)
                        .horaVistoria(laudoVP.getHoraVistoria())
                        .ufRealizacaoVistoria(laudoVP.getUfRealizacaoVistoria())
                        .cidadeOrigemVistoria(laudoVP.getCidadeOrigemVistoria())
                        .codigoCorretorInterno(laudoVP.getCodigoCorretorInterno())
                        .statusVistoria(laudoVP.getStatusVistoria())
                        .dataCadastro(laudoVP.getDataCadastro())
                        .observacao(laudoVP.getObservacao())
                        .mobile(VistoriaMobileEnum.SIM)
                        .build())
                .codigosInformacoesTecnicas(laudoVP.getCodigosInformacoesTecnicas())
                .veiculoDTO(VeiculoLaudoVPTransmitidoDTO.buildFromLayoutMobile(laudoVP.getVeiculo()))
                .proponenteDTO(ProponenteLaudoVPTransmitidoDTO.buildFromLayoutMobile(laudoVP.getProponente()))
                .linkFotoVPTransmitidoDTOList(linkFotoVistoriaPreviaTransmitidoDTOList)
                .build();
    }

}
