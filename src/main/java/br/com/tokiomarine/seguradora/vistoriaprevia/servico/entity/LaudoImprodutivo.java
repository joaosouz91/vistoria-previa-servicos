package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivoDetalhe;

@SuppressWarnings("serial")
@SqlResultSetMapping( name = "LaudoImprodutivo", entities = { @EntityResult( entityClass = LaudoImprodutivo.class, fields = { 
    @FieldResult( name = "cdLvpre", column = "CD_LVPRE" ), 
    @FieldResult( name = "nrVrsaoLvpre", column = "NR_VRSAO_LVPRE" ), 
    @FieldResult( name = "cdSitucVspre", column = "CD_SITUC_VSPRE" ),
    @FieldResult( name = "corretor", column = "CD_CRTOR_SEGUR" ), 
    @FieldResult( name = "sucursal", column = "CD_SUCSL_COMRL" ), 
    @FieldResult( name = "superintendencia", column = "CD_SUPIN" )}) })
@Entity
public class LaudoImprodutivo implements Serializable, Comparable<LaudoImprodutivo> {

	@Id
	@Column(name = "CD_LVPRE")
	private String cdLvpre;
	
	@Id
	@Column(name = "NR_VRSAO_LVPRE")
	private Long nrVrsaoLvpre;
	
	@Column(name = "CD_SITUC_VSPRE")
	private String cdSitucVspre;
	
	@Column(name = "CD_CRTOR_SEGUR")
	private Long corretor;
	
	@Column(name = "CD_SUCSL_COMRL")
	private Long sucursal;
	
	@Column(name = "CD_SUPIN")
	private Long superintendencia;

	@Transient
	private LoteLaudoImprodutivoDetalhe detalhe = new LoteLaudoImprodutivoDetalhe();

	public LaudoImprodutivo() {
		
	}
	
	public LaudoImprodutivo(LaudoVistoriaPrevia l) {

		detalhe = new LoteLaudoImprodutivoDetalhe();

		detalhe.setCdLvpre(l.getCdLvpre());
		detalhe.setNrVrsaoLvpre(l.getNrVrsaoLvpre());
		detalhe.setCdMotvImpdv("B");
		detalhe.setIcExclu("N");
		detalhe.setCdTipoLaudo("I");
		detalhe.setCdUsuroUltmaAtulz("Batch");
		detalhe.setDtUltmaAtulz(new Date());
		// Sempre bloqueio por tempo
		detalhe.setCdMotvImpdv("B");
		// Se for Sujeito a An�lise ou Recus�vel ajusta o motivo
		if (l.getCdSitucVspre().equals("S") || l.getCdSitucVspre().equals("R")) {
			detalhe.setCdMotvImpdv(l.getCdSitucVspre());
		}
		corretor = l.getCdCrtorSegur();
		sucursal = l.getCdSucslComrl();
		superintendencia = l.getCdSupin();
	}
	
	public String getCdLvpre() {
		return cdLvpre;
	}

	public void setCdLvpre(String cdLvpre) {
		this.cdLvpre = cdLvpre;
	}

	public Long getNrVrsaoLvpre() {
		return nrVrsaoLvpre;
	}

	public void setNrVrsaoLvpre(Long nrVrsaoLvpre) {
		this.nrVrsaoLvpre = nrVrsaoLvpre;
	}

	public String getCdSitucVspre() {
		return cdSitucVspre;
	}

	public void setCdSitucVspre(String cdSitucVspre) {
		this.cdSitucVspre = cdSitucVspre;
	}

	public LaudoImprodutivo(LoteLaudoImprodutivoDetalhe detalhe) {
		this.detalhe = detalhe;
	}

	public LoteLaudoImprodutivoDetalhe getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(LoteLaudoImprodutivoDetalhe detalhe) {
		this.detalhe = detalhe;
	}

	public Long getCorretor() {
		return corretor;
	}

	public void setCorretor(Long corretor) {
		this.corretor = corretor;
	}

	public Long getSucursal() {
		return sucursal;
	}

	public void setSucursal(Long sucursal) {
		this.sucursal = sucursal;
	}

	public Long getSuperintendencia() {
		return superintendencia;
	}

	public void setSuperintendencia(Long superintendencia) {
		this.superintendencia = superintendencia;
	}

	@Override
	public boolean equals(Object o) {

		if (o == null) {
			return false;
		}

		if (!(o instanceof LaudoImprodutivo)) {
			return false;
		}

		LaudoImprodutivo outroDetalhe = (LaudoImprodutivo) o;

		if (outroDetalhe.getDetalhe().getCdLvpre().equals(detalhe.getCdLvpre()) && outroDetalhe.getDetalhe().getNrVrsaoLvpre().equals(detalhe.getNrVrsaoLvpre())) {
			return true;
		}

		return false;
	}

	public int compareTo(LaudoImprodutivo o) {
		
		if (o.getCorretor().compareTo(corretor) == 0) {
			if (o.getDetalhe().getCdLvpre().compareTo(detalhe.getCdLvpre()) == 0)  {
				return o.getDetalhe().getNrVrsaoLvpre().compareTo(detalhe.getNrVrsaoLvpre());
			} else {
				return o.getDetalhe().getCdLvpre().compareTo(detalhe.getCdLvpre());
			}
		}
		return o.getCorretor().compareTo(corretor);
	}
}
