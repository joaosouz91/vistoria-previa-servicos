package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProprietarioVeiculoLaudoVPTransmitidoDTO {

    private String estadoCivil;
    private String sexo;
    private Date dataNascimento;
    private String cnh;
    private String ufCnh;
    private Date dataCnh;
    private String atividadeProfissional;

    public static ProprietarioVeiculoLaudoVPTransmitidoDTO buildFromLayoutAntigo(
            br.com.tokiomarine.seguradora.ssv.vistoriaprevia.ws.model.ProprietarioVeiculoLaudoVP proprietarioVeiculoLaudoVP) {

        return ProprietarioVeiculoLaudoVPTransmitidoDTO.builder()
                .estadoCivil(proprietarioVeiculoLaudoVP.getEstadoCivil())
                .sexo(proprietarioVeiculoLaudoVP.getSexo())
                .dataNascimento(proprietarioVeiculoLaudoVP.getDataNascimento())
                .cnh(proprietarioVeiculoLaudoVP.getCnh())
                .ufCnh(proprietarioVeiculoLaudoVP.getUfCnh())
                .dataCnh(proprietarioVeiculoLaudoVP.getDataCnh())
                .atividadeProfissional(proprietarioVeiculoLaudoVP.getAtividadeProfissional())
                .build();
    }

    public static ProprietarioVeiculoLaudoVPTransmitidoDTO buildFromLayoutNovo(
            br.com.tokiomarine.seguradora.vistoriaprevia.ws.dtos.ProprietarioVeiculoLaudoVP proprietarioVeiculoLaudoVP) {

        return ProprietarioVeiculoLaudoVPTransmitidoDTO.builder()
                .estadoCivil(proprietarioVeiculoLaudoVP.getEstadoCivil())
                .sexo(proprietarioVeiculoLaudoVP.getSexo())
                .dataNascimento(proprietarioVeiculoLaudoVP.getDataNascimento())
                .cnh(proprietarioVeiculoLaudoVP.getCnh())
                .ufCnh(proprietarioVeiculoLaudoVP.getUfCnh())
                .dataCnh(proprietarioVeiculoLaudoVP.getDataCnh())
                .atividadeProfissional(proprietarioVeiculoLaudoVP.getAtividadeProfissional())
                .build();
    }

}
