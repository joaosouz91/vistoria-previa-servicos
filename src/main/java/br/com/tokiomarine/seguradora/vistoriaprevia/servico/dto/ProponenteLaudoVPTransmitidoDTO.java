package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilJava;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProponenteLaudoVPTransmitidoDTO {

    private String nome;
    private String endereco;
    private String bairro;
    private String cidade;
    private String uf;
    private String cpf;
    private String cnpj;
    private Long cep;
    private String telefone;
    private String tipoPessoa;

    public static ProponenteLaudoVPTransmitidoDTO buildFromLayoutAntigo(
            br.com.tokiomarine.seguradora.ssv.vistoriaprevia.ws.model.ProponenteLaudoVP proponenteLaudoVP){

        String cnpj = proponenteLaudoVP.getCnpj() != null && proponenteLaudoVP.getCnpj().toString().length() > 0 ? proponenteLaudoVP.getCnpj().toString() : null;
        String cpf = proponenteLaudoVP.getCpf() != null && proponenteLaudoVP.getCpf().toString().length() > 0 ? proponenteLaudoVP.getCpf().toString() : null;

        cnpj = UtilJava.addLeftZerosToString(cnpj, 14);
        cpf = UtilJava.addLeftZerosToString(cpf, 11);

        return ProponenteLaudoVPTransmitidoDTO.builder()
                .nome(proponenteLaudoVP.getNome())
                .endereco(proponenteLaudoVP.getEndereco())
                .bairro(proponenteLaudoVP.getBairro())
                .cidade(proponenteLaudoVP.getCidade())
                .uf(proponenteLaudoVP.getUf())
                .cpf(cpf != null && Long.parseLong(cpf) != 0L ? cpf : null)
                .cnpj(cnpj != null && Long.parseLong(cnpj) != 0L ? cnpj : null)
                .cep(proponenteLaudoVP.getCep())
                .telefone(proponenteLaudoVP.getTelefone())
                .build();
    }

    public static ProponenteLaudoVPTransmitidoDTO buildFromLayoutNovo(
            br.com.tokiomarine.seguradora.vistoriaprevia.ws.dtos.ProponenteLaudoVP proponenteLaudoVP){

        String cnpj = proponenteLaudoVP.getCnpj() != null && proponenteLaudoVP.getCnpj().toString().length() > 0 ? proponenteLaudoVP.getCnpj().toString() : null;
        String cpf = proponenteLaudoVP.getCpf() != null && proponenteLaudoVP.getCpf().toString().length() > 0 ? proponenteLaudoVP.getCpf().toString() : null;

        cnpj = UtilJava.addLeftZerosToString(cnpj, 14);
        cpf = UtilJava.addLeftZerosToString(cpf, 11);

        return ProponenteLaudoVPTransmitidoDTO.builder()
                .nome(proponenteLaudoVP.getNome())
                .endereco(proponenteLaudoVP.getEndereco())
                .bairro(proponenteLaudoVP.getBairro())
                .cidade(proponenteLaudoVP.getCidade())
                .uf(proponenteLaudoVP.getUf())
                .cpf(cpf != null && Long.parseLong(cpf) != 0L ? cpf : null)
                .cnpj(cnpj != null && Long.parseLong(cnpj) != 0L ? cnpj : null)
                .cep(proponenteLaudoVP.getCep())
                .telefone(proponenteLaudoVP.getTelefone())
                .build();
    }

    public static ProponenteLaudoVPTransmitidoDTO buildFromLayoutMobile(
            br.com.tokiomarine.seguradora.vistoriaprevia.ws.agendamento.dtos.ProponenteLaudoVP proponenteLaudoVP){

        String cpfCnpj = proponenteLaudoVP.getCpfCnpj() != null ? proponenteLaudoVP.getCpfCnpj().toString() : null;

        String cpf = null;
        String cnpj = null;

        if(proponenteLaudoVP.getTipoPessoa() != null) {
            if(proponenteLaudoVP.getTipoPessoa().substring(0,1).equalsIgnoreCase("F")) {
                cpf = UtilJava.addLeftZerosToString(cpfCnpj, 11);
            } else {
                cnpj = UtilJava.addLeftZerosToString(cpfCnpj, 14);
            }
        }

        return ProponenteLaudoVPTransmitidoDTO.builder()
                .nome(proponenteLaudoVP.getNome())
                .telefone(proponenteLaudoVP.getTelefone())
                .tipoPessoa(proponenteLaudoVP.getTipoPessoa())
                .cpf(cpf)
                .cnpj(cnpj)
                .build();
    }

}
