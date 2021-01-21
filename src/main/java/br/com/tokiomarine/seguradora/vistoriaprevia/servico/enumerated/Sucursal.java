package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Sucursal implements Serializable {
    
	private static final long serialVersionUID = 6908792623397506323L;
	
	@EqualsAndHashCode.Include
	private Long cdSucursal;
    private Long cdSuperintendencia;
    private String dsSucursal;

	/**
     * @param cdSucursal
     */
    public void setCdSucursal(Long cdSucursal) {
        this.cdSucursal = cdSucursal;
    }

    /**
     * @return Long
     */
    public Long getCdSucursal() {
        return cdSucursal;
    }

    public Long getCdSuperintendencia() {
		return cdSuperintendencia;
	}

	public void setCdSuperintendencia(Long cdSuperintendencia) {
		this.cdSuperintendencia = cdSuperintendencia;
	}
    
    /**
     * @param dsSucursal
     */
    public void setDsSucursal(String dsSucursal) {
        this.dsSucursal = dsSucursal;
    }

    /**
     * @return String
     */
    public String getDsSucursal() {
        return dsSucursal;
    }
    
    public String getDescricao() {
    	
    	return this.cdSucursal + " - " + this.dsSucursal;
    }
}
