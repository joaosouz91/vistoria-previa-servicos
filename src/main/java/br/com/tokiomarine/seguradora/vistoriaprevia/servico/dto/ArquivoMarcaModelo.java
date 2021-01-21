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
public class ArquivoMarcaModelo  {
	
    @Id
    @Column(name="CD_VEICULO")
    private Long codigoVeiculo;

    @Column(name="NM_VEICULO")
    private String nomeVeiculo;

    @Column(name="CD_FABRICANTE")
    private Long codigoFabricante;

    @Column(name="NM_FABRICANTE")
    private String nomeFabricante;

    @Column(name="TIPO_VEICULO")
    private String tipoVeiculo;
    
    

} 
    
    
    