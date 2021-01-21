package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvariaLaudoVPTransmitidoDTO {

    private Long codigoPecaAvariada;
    private String codigoAvaria;
    private Long horasFunilaria;
    private Long horasPintura;
    private Long horasTapecaria;
    private Long horasEletrica;

    public static AvariaLaudoVPTransmitidoDTO buildFromLayoutAntigo(
            br.com.tokiomarine.seguradora.ssv.vistoriaprevia.ws.model.AvariaLaudoVP avariaLaudoVP){

        return AvariaLaudoVPTransmitidoDTO.builder()
                .codigoPecaAvariada(avariaLaudoVP.getCodigoPecaAvariada())
                .codigoAvaria(avariaLaudoVP.getCodigoAvaria())
                .horasFunilaria(avariaLaudoVP.getHorasFunilaria())
                .horasPintura(avariaLaudoVP.getHorasPintura())
                .horasTapecaria(avariaLaudoVP.getHorasTapecaria())
                .horasEletrica(avariaLaudoVP.getHorasEletrica())
                .build();
    }

    public static AvariaLaudoVPTransmitidoDTO buildFromLayoutNovo(
            br.com.tokiomarine.seguradora.vistoriaprevia.ws.dtos.AvariasLaudoVP avariaLaudoVP){

        return AvariaLaudoVPTransmitidoDTO.builder()
                .codigoPecaAvariada(avariaLaudoVP.getCodigoPecaAvariada())
                .codigoAvaria(avariaLaudoVP.getCodigoAvaria())
                .horasFunilaria(avariaLaudoVP.getHorasFunilaria())
                .horasPintura(avariaLaudoVP.getHorasPintura())
                .horasTapecaria(avariaLaudoVP.getHorasTapecaria())
                .horasEletrica(avariaLaudoVP.getHorasEletrica())
                .build();
    }

    public static AvariaLaudoVPTransmitidoDTO buildFromLayoutMobile(
            br.com.tokiomarine.seguradora.vistoriaprevia.ws.agendamento.dtos.AvariaLaudoVP avariaLaudoVP){

        return AvariaLaudoVPTransmitidoDTO.builder()
                .codigoPecaAvariada(avariaLaudoVP.getCodigoPecaAvariada())
                .codigoAvaria(avariaLaudoVP.getCodigoAvaria())
                .horasFunilaria(avariaLaudoVP.getHorasFunilaria())
                .horasPintura(avariaLaudoVP.getHorasPintura())
                .horasTapecaria(avariaLaudoVP.getHorasTapecaria())
                .horasEletrica(avariaLaudoVP.getHorasEletrica())
                .build();
    }

}
