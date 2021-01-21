package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcessorioLaudoVPTransmitidoDTO {

    private Long codigoAcessorio;
    private String tipoAcessorio;
    private String codigoComplemento;

    public static AcessorioLaudoVPTransmitidoDTO buildFromLayoutAntigo(
            br.com.tokiomarine.seguradora.ssv.vistoriaprevia.ws.model.AcessorioLaudoVP acessorioLaudoVP) {

        return AcessorioLaudoVPTransmitidoDTO.builder()
                .codigoAcessorio(acessorioLaudoVP.getCodigoAcessorio())
                .tipoAcessorio(acessorioLaudoVP.getTipoAcessorio())
                .codigoComplemento(acessorioLaudoVP.getCodigoComplemento())
                .build();
    }

    public static AcessorioLaudoVPTransmitidoDTO buildFromLayoutNovo(
            br.com.tokiomarine.seguradora.vistoriaprevia.ws.dtos.AcessoriosLaudoVP acessorioLaudoVP) {

        return AcessorioLaudoVPTransmitidoDTO.builder()
                .codigoAcessorio(acessorioLaudoVP.getCodigoAcessorio())
                .tipoAcessorio(acessorioLaudoVP.getTipoAcessorio())
                .codigoComplemento(acessorioLaudoVP.getCodigoComplemento())
                .build();
    }

    public static AcessorioLaudoVPTransmitidoDTO buildFromLayoutMobile(
            br.com.tokiomarine.seguradora.vistoriaprevia.ws.agendamento.dtos.AcessorioLaudoVP acessorioLaudoVP) {

        return AcessorioLaudoVPTransmitidoDTO.builder()
                .codigoAcessorio(acessorioLaudoVP.getCodigoAcessorio())
                .tipoAcessorio(acessorioLaudoVP.getTipoAcessorio())
                .codigoComplemento(acessorioLaudoVP.getCodigoComplemento())
                .build();
    }
}
