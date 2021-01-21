package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum VistoriaMobileEnum {

    @JsonProperty("Sim")
    SIM,
    @JsonProperty("Não")
    NAO

}
