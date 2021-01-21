package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import lombok.*;

import java.text.DateFormat;
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
public class VeiculoLaudoVPTransmitidoDTO {

    private String numeroCRLV;
    private String nomeCRLV;
    private String cidadeExpedicaoCRLV;
    private String ufExpedicaoCRLV;
    private Date dataExpedicaoCRLV;
    private String codigoRenavam;
    private String codigoChassi;
    private String origemChassi;
    private String numeroMotor;
    private Long cpfCRLV;
    private Long cnpjCRLV;
    private String placa;
    private String codigoCombustivel;
    private Long codigoVeiculo;
    private Long codigoFabricante;
    private String descricaoModelo;
    private Long anoFabricacao;
    private Long anoModelo;
    private Long lotacao;
    private Long quantidadePortas;
    private String cor;
    private String tipoPintura;
    private String nomeAlienacao;
    private String cambio;
    private Long tipoCambio;
    private Long quantidadeMarchas;
    private Long quantidadeCilindros;
    private Long kmRodado;
    private Long codigoUtilizacao;
    private Long localGuarda;
    private String indicadorVeiculoTransformado;
    private VeiculoCargaLaudoVPTransmitidoDTO veiculoCargaDTO;
    private List<AcessorioLaudoVPTransmitidoDTO> acessorioDTOlist;
    private List<AvariaLaudoVPTransmitidoDTO> avariaDTOlist;

    public static VeiculoLaudoVPTransmitidoDTO buildFromLayoutAntigo(
            br.com.tokiomarine.seguradora.ssv.vistoriaprevia.ws.model.VeiculoLaudoVP veiculoLaudoVP){

        List<AcessorioLaudoVPTransmitidoDTO> acessorioDTOList =
                Arrays.stream(veiculoLaudoVP.getAcessorios())
                        .map(AcessorioLaudoVPTransmitidoDTO::buildFromLayoutAntigo)
                        .collect(Collectors.toList());

        List<AvariaLaudoVPTransmitidoDTO> avariaDTOList =
                Arrays.stream(veiculoLaudoVP.getAvarias())
                        .map(AvariaLaudoVPTransmitidoDTO::buildFromLayoutAntigo)
                        .collect(Collectors.toList());

        return VeiculoLaudoVPTransmitidoDTO.builder()
                .numeroCRLV(veiculoLaudoVP.getNumeroCRLV())
                .nomeCRLV(veiculoLaudoVP.getNomeCRLV())
                .cidadeExpedicaoCRLV(veiculoLaudoVP.getCidadeExpedicaoCRLV())
                .ufExpedicaoCRLV(veiculoLaudoVP.getUfExpedicaoCRLV())
                .dataExpedicaoCRLV(veiculoLaudoVP.getDataExpedicaoCRLV())
                .codigoRenavam(veiculoLaudoVP.getCodigoRenavam())
                .codigoChassi(veiculoLaudoVP.getCodigoChassi())
                .origemChassi(veiculoLaudoVP.getOrigemChassi())
                .numeroMotor(veiculoLaudoVP.getNumeroMotor())
                .cpfCRLV(veiculoLaudoVP.getCpfCRLV())
                .cnpjCRLV(veiculoLaudoVP.getCnpjCRLV())
                .placa(veiculoLaudoVP.getPlaca())
                .codigoCombustivel(veiculoLaudoVP.getCodigoCombustivel())
                .codigoVeiculo(veiculoLaudoVP.getCodigoVeiculo())
                .codigoFabricante(veiculoLaudoVP.getCodigoFabricante())
                .descricaoModelo(veiculoLaudoVP.getDescricaoModelo())
                .anoFabricacao(veiculoLaudoVP.getAnoFabricacao())
                .anoModelo(veiculoLaudoVP.getAnoModelo())
                .lotacao(veiculoLaudoVP.getLotacao())
                .quantidadePortas(veiculoLaudoVP.getQuantidadePortas())
                .cor(veiculoLaudoVP.getCor())
                .tipoPintura(veiculoLaudoVP.getTipoPintura())
                .nomeAlienacao(veiculoLaudoVP.getNomeAlienacao())
                .cambio(veiculoLaudoVP.getCambio())
                .tipoCambio(veiculoLaudoVP.getTipoCambio())
                .quantidadeMarchas(veiculoLaudoVP.getQuantidadeMarchas())
                .quantidadeCilindros(veiculoLaudoVP.getQuantidadeCilindros())
                .kmRodado(veiculoLaudoVP.getKmRodado())
                .codigoUtilizacao(veiculoLaudoVP.getCodigoUtilizacao())
                .localGuarda(veiculoLaudoVP.getLocalGuarda())
                .indicadorVeiculoTransformado(veiculoLaudoVP.getIndicadorVeiculoTransformado())
                .acessorioDTOlist(acessorioDTOList)
                .avariaDTOlist(avariaDTOList)
                .veiculoCargaDTO(
                        VeiculoCargaLaudoVPTransmitidoDTO.builder()
                            .indicadorVeiculoCarga(veiculoLaudoVP.getIndicadorVeiculoCarga())
                            .marcaCarroceria(veiculoLaudoVP.getMarcaCarroceria())
                            .numeroCarroceria(veiculoLaudoVP.getNumeroCarroceria())
                            .formatoCarroceria(veiculoLaudoVP.getFormatoCarroceria())
                            .codigoMaterialCarroceria(veiculoLaudoVP.getCodigoMaterialCarroceria())
                            .indicadorRodoar(veiculoLaudoVP.getIndicadorRodoar())
                            .codigoTipoCabine(veiculoLaudoVP.getCodigoTipoCabine())
                            .tipoCarroceria(veiculoLaudoVP.getTipoCarroceria())
                            .build())
                .build();
    }

    public static VeiculoLaudoVPTransmitidoDTO buildFromLayoutNovo(
            br.com.tokiomarine.seguradora.vistoriaprevia.ws.dtos.VeiculoLaudoVP veiculoLaudoVP){

        List<AcessorioLaudoVPTransmitidoDTO> acessorioDTOList =
                veiculoLaudoVP.getAcessorios().stream()
                        .map(AcessorioLaudoVPTransmitidoDTO::buildFromLayoutNovo)
                        .collect(Collectors.toList());

        List<AvariaLaudoVPTransmitidoDTO> avariaDTOList =
                veiculoLaudoVP.getAvarias().stream()
                        .map(AvariaLaudoVPTransmitidoDTO::buildFromLayoutNovo)
                        .collect(Collectors.toList());

        return VeiculoLaudoVPTransmitidoDTO.builder()
                .numeroCRLV(veiculoLaudoVP.getNumeroCRLV())
                .nomeCRLV(veiculoLaudoVP.getNomeCRLV())
                .cidadeExpedicaoCRLV(veiculoLaudoVP.getCidadeExpedicaoCRLV())
                .ufExpedicaoCRLV(veiculoLaudoVP.getUfExpedicaoCRLV())
                .dataExpedicaoCRLV(veiculoLaudoVP.getDataExpedicaoCRLV())
                .codigoRenavam(veiculoLaudoVP.getCodigoRenavam())
                .codigoChassi(veiculoLaudoVP.getCodigoChassi())
                .origemChassi(veiculoLaudoVP.getOrigemChassi())
                .numeroMotor(veiculoLaudoVP.getNumeroMotor())
                .cpfCRLV(veiculoLaudoVP.getCpfCRLV())
                .cnpjCRLV(veiculoLaudoVP.getCnpjCRLV())
                .placa(veiculoLaudoVP.getPlaca())
                .codigoCombustivel(veiculoLaudoVP.getCodigoCombustivel())
                .codigoVeiculo(veiculoLaudoVP.getCodigoVeiculo())
                .codigoFabricante(veiculoLaudoVP.getCodigoFabricante())
                .descricaoModelo(veiculoLaudoVP.getDescricaoModelo())
                .anoFabricacao(veiculoLaudoVP.getAnoFabricacao())
                .anoModelo(veiculoLaudoVP.getAnoModelo())
                .lotacao(veiculoLaudoVP.getLotacao())
                .quantidadePortas(veiculoLaudoVP.getQuantidadePortas())
                .cor(veiculoLaudoVP.getCor())
                .tipoPintura(veiculoLaudoVP.getTipoPintura())
                .nomeAlienacao(veiculoLaudoVP.getNomeAlienacao())
                .cambio(veiculoLaudoVP.getCambio())
                .tipoCambio(veiculoLaudoVP.getTipoCambio())
                .quantidadeMarchas(veiculoLaudoVP.getQuantidadeMarchas())
                .quantidadeCilindros(veiculoLaudoVP.getQuantidadeCilindros())
                .kmRodado(veiculoLaudoVP.getKmRodado())
                .codigoUtilizacao(veiculoLaudoVP.getCodigoUtilizacao())
                .localGuarda(veiculoLaudoVP.getLocalGuarda())
                .indicadorVeiculoTransformado(veiculoLaudoVP.getIndicadorVeiculoTransformado())
                .acessorioDTOlist(acessorioDTOList)
                .avariaDTOlist(avariaDTOList)
                .veiculoCargaDTO(
                        VeiculoCargaLaudoVPTransmitidoDTO.builder()
                                .indicadorVeiculoCarga(veiculoLaudoVP.getIndicadorVeiculoCarga())
                                .marcaCarroceria(veiculoLaudoVP.getMarcaCarroceria())
                                .numeroCarroceria(veiculoLaudoVP.getNumeroCarroceria())
                                .formatoCarroceria(veiculoLaudoVP.getFormatoCarroceria())
                                .codigoMaterialCarroceria(veiculoLaudoVP.getCodigoMaterialCarroceria())
                                .indicadorRodoar(veiculoLaudoVP.getIndicadorRodoar())
                                .codigoTipoCabine(veiculoLaudoVP.getCodigoTipoCabine())
                                .tipoCarroceria(veiculoLaudoVP.getTipoCarroceria())
                                .build())
                .build();
    }

    public static VeiculoLaudoVPTransmitidoDTO buildFromLayoutMobile(
            br.com.tokiomarine.seguradora.vistoriaprevia.ws.agendamento.dtos.VeiculoLaudoVP veiculoLaudoVP){

        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        Date dataExpedicaoCRVL = null;

        try {
            dataExpedicaoCRVL = simpleDateFormat.parse(veiculoLaudoVP.getDataExpedicaoCRLV());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<AcessorioLaudoVPTransmitidoDTO> acessorioDTOList =
                veiculoLaudoVP.getAcessorios().stream()
                        .map(AcessorioLaudoVPTransmitidoDTO::buildFromLayoutMobile)
                        .collect(Collectors.toList());

        List<AvariaLaudoVPTransmitidoDTO> avariaDTOList =
                veiculoLaudoVP.getAvarias().stream()
                        .map(AvariaLaudoVPTransmitidoDTO::buildFromLayoutMobile)
                        .collect(Collectors.toList());

        return VeiculoLaudoVPTransmitidoDTO.builder()
                .numeroCRLV(veiculoLaudoVP.getNumeroCRLV())
                .nomeCRLV(veiculoLaudoVP.getNomeCRLV())
                .cidadeExpedicaoCRLV(veiculoLaudoVP.getCidadeExpedicaoCRLV())
                .ufExpedicaoCRLV(veiculoLaudoVP.getUfExpedicaoCRLV())
                .dataExpedicaoCRLV(dataExpedicaoCRVL)
                .codigoRenavam(veiculoLaudoVP.getCodigoRenavam())
                .codigoChassi(veiculoLaudoVP.getCodigoChassi())
                .numeroMotor(veiculoLaudoVP.getNumeroMotor())
                .cpfCRLV(veiculoLaudoVP.getCpfCRLV())
                .cnpjCRLV(veiculoLaudoVP.getCnpjCRLV())
                .placa(veiculoLaudoVP.getPlaca())
                .codigoCombustivel(veiculoLaudoVP.getCodigoCombustivel())
                .codigoVeiculo(veiculoLaudoVP.getCodigoVeiculo())
                .codigoFabricante(veiculoLaudoVP.getCodigoFabricante())
                .anoFabricacao(veiculoLaudoVP.getAnoFabricacao())
                .anoModelo(veiculoLaudoVP.getAnoModelo())
                .lotacao(veiculoLaudoVP.getLotacao())
                .quantidadePortas(veiculoLaudoVP.getQuantidadePortas())
                .cor(veiculoLaudoVP.getCor())
                .nomeAlienacao(veiculoLaudoVP.getNomeAlienacao())
                .tipoCambio(veiculoLaudoVP.getTipoCambio())
                .quantidadeMarchas(veiculoLaudoVP.getQuantidadeMarchas())
                .kmRodado(veiculoLaudoVP.getKmRodado())
                .acessorioDTOlist(acessorioDTOList)
                .avariaDTOlist(avariaDTOList)
                .veiculoCargaDTO(
                        VeiculoCargaLaudoVPTransmitidoDTO.builder()
                                .indicadorVeiculoCarga(veiculoLaudoVP.getIndicadorVeiculoCarga())
                                .marcaCarroceria(veiculoLaudoVP.getMarcaCarroceria())
                                .formatoCarroceria(veiculoLaudoVP.getFormatoCarroceria())
                                .codigoMaterialCarroceria(veiculoLaudoVP.getCodigoMaterialCarroceria())
                                .indicadorRodoar(veiculoLaudoVP.getIndicadorRodoar())
                                .tipoCarroceria(veiculoLaudoVP.getTipoCarroceria())
                                .build())
                .build();
    }

}
