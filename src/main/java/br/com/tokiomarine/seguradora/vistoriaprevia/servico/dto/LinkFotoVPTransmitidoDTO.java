package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkFotoVPTransmitidoDTO {

    private String local;
    private String link;

    public static LinkFotoVPTransmitidoDTO buildFromLayoutMobile(
            br.com.tokiomarine.seguradora.vistoriaprevia.ws.agendamento.dtos.LinkFotoVistoria linkFotoVistoria) {
        return LinkFotoVPTransmitidoDTO.builder()
                .local(linkFotoVistoria.getLocal())
                .link(linkFotoVistoria.getLink())
                .build();
    }
}
