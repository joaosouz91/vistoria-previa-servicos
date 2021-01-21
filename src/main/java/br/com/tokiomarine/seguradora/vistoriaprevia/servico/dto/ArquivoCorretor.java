package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class ArquivoCorretor  {

    @Id
    @Column(name="LINHA_COR")
    private String linha;

 
}