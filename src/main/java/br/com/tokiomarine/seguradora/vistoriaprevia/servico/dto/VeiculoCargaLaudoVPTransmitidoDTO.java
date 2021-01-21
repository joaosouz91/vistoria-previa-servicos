package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VeiculoCargaLaudoVPTransmitidoDTO {

    private String indicadorVeiculoCarga;
    private String marcaCarroceria;
    private String numeroCarroceria;
    private Long formatoCarroceria;
    private Long codigoMaterialCarroceria;
    private String indicadorRodoar;
    private Long codigoTipoCabine;
    private Long tipoCarroceria;

}
