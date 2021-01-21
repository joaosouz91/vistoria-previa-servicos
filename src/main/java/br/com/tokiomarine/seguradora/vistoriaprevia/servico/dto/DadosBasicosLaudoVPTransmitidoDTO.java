package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.VistoriaMobileEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DadosBasicosLaudoVPTransmitidoDTO {

    private Long codigoSituacao;
    private Long indicadorVistoriaFrustrada;
    private String nomeSolicitanteVistoria;
    private String codigoCompanhia;
    private Date dataVistoria;
    private String horaVistoria;
    private String codigoVistoriador;
    private String indicadorPostoDomicilio;
    private Long codigoPostoVistoria;
    private Date dataCadastro;
    private String observacao;
    private String statusVistoria;
    private Long cepLocalVistoria;
    private Long kmRealizacaoVistoria;
    private String cidadeOrigemVistoria;
    private String cidadeDestinoVistoria;
    private Long codigoCorretorInterno;
    private String ufRealizacaoVistoria;
    private String validacao;
    private VistoriaMobileEnum mobile;

}
