package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CondutorLaudoVPTransmitidoDTO {

    private String nome;
    private String estadoCivil;
    private Long cpf;
    private Date dataNascimento;
    private String cnh;
    private String ufCnh;
    private Long tipo;

    public static CondutorLaudoVPTransmitidoDTO buildFromLayoutAntigo(
            br.com.tokiomarine.seguradora.ssv.vistoriaprevia.ws.model.CondutorVeiculoLaudoVP condutorVeiculoLaudoVP) {
        return CondutorLaudoVPTransmitidoDTO.builder()
                .nome(condutorVeiculoLaudoVP.getNome())
                .estadoCivil(condutorVeiculoLaudoVP.getEstadoCivil())
                .cpf(condutorVeiculoLaudoVP.getCpf())
                .dataNascimento(condutorVeiculoLaudoVP.getDataNascimento())
                .cnh(condutorVeiculoLaudoVP.getCnh())
                .ufCnh(condutorVeiculoLaudoVP.getUfCnh())
                .tipo(condutorVeiculoLaudoVP.getTipo())
                .build();
    }

    public static CondutorLaudoVPTransmitidoDTO buildFromLayoutNovo(
            br.com.tokiomarine.seguradora.vistoriaprevia.ws.dtos.CondutorVeiculoLaudoVP condutorVeiculoLaudoVP) {
        return CondutorLaudoVPTransmitidoDTO.builder()
                .nome(condutorVeiculoLaudoVP.getNome())
                .estadoCivil(condutorVeiculoLaudoVP.getEstadoCivil())
                .cpf(condutorVeiculoLaudoVP.getCpf())
                .dataNascimento(condutorVeiculoLaudoVP.getDataNascimento())
                .cnh(condutorVeiculoLaudoVP.getCnh())
                .ufCnh(condutorVeiculoLaudoVP.getUfCnh())
                .tipo(condutorVeiculoLaudoVP.getTipo())
                .build();
    }

}
