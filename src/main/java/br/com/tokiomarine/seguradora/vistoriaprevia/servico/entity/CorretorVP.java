package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CorretorVP implements Serializable {
	
	private static final long serialVersionUID = -6076653815326221452L;
	
	private String cdCorretorSusep;
	
	@EqualsAndHashCode.Include
    private String cdCorretor;
	
    private String nomeCorretor;
    
    @EqualsAndHashCode.Include
    private String cdSucursal;

    public CorretorVP(String codigoCorretor, String nomeCorretor, String codigoSucursal) {    	
        this.cdCorretor   = codigoCorretor;
        this.nomeCorretor = nomeCorretor;
        this.cdSucursal   = codigoSucursal;
    }
    
}