package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;


import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DadosCabecalhoLaudoVPTransmitidoDTO {

    private Date dataTransmissao;
    private String codigoLaudo;
    private String codigoVoucher;

}
