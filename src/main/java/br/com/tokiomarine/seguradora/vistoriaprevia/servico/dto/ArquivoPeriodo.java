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
public class ArquivoPeriodo {
  
    @Id
    @Column(name="RG_CD_MOD_REAL_PERIOD")
    private Long codigoModeloPeriodo;
    
    @Column(name="RG_TP_COMBUSTIVEL_PERIOD")
    private String tipoCombustivelPeriodo;
    
    @Column(name="RG_AA_INICIAL_PERIOD")
    private Long anoInicioPeriodo;
    
    @Column(name="RG_AA_FINAL_PERIOD")
    private  Long anoFimPeriodo;
    
}