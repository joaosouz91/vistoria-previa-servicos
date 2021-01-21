package br.com.tokiomarine.seguradora.ext.ssv.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="SSV4010_CONTR_ALCAD")
public class ControleAlcadaDominio implements Serializable {
	    
	private static final long serialVersionUID = 1L;
	
	@Column(name="CD_ALCAD")
	private Long cdAlcad;
	
	@Column(name="CD_EVNTO_COMDT")
	private String cdEvntoComdt;

	@Id
	@Column(name="CD_MDUPR")
	private Long cdMdupr;
	
	@Column(name="CD_RESTR_GRADE")
	private String cdRestrGrade;
	
	@Column(name="CD_USURO_ULTMA_ALTER")
	private String cdUsuroUltmaAlter;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_ULTMA_ALTER")
	private Date dtUltmaAlter;
	
	@Column(name="IC_LIBEC")
	private String icLibec;
	
	@Column(name="IC_RGITA_OCCOR")
	private String icRgitaOccor;
	
	@Column(name="IC_SERASA")
	private String icSerasa;
	
	@Column(name="TP_OPERC")
	private Long tpOperc;



	public Long getCdAlcad()
  {
    return this.cdAlcad;
  }

  public String getCdEvntoComdt()
  {
    return this.cdEvntoComdt;
  }

  public Long getCdMdupr()
  {
    return this.cdMdupr;
  }

  public String getCdRestrGrade()
  {
    return this.cdRestrGrade;
  }

  public String getCdUsuroUltmaAlter()
  {
    return this.cdUsuroUltmaAlter;
  }
  
  public Date getDtUltmaAlter()
  {
    return this.dtUltmaAlter;
  }

  public String getIcLibec()
  {
    return this.icLibec;
  }

  public String getIcRgitaOccor()
  {
    return this.icRgitaOccor;
  }

  public String getIcSerasa()
  {
    return this.icSerasa;
  }

  public Long getTpOperc()
  {
    return this.tpOperc;
  }

  public void setCdAlcad(Long cdAlcad)
  {
    this.cdAlcad = cdAlcad;
  }

  public void setCdEvntoComdt(String cdEvntoComdt)
  {
    this.cdEvntoComdt = cdEvntoComdt;
  }

  public void setCdMdupr(Long cdMdupr)
  {
    this.cdMdupr = cdMdupr;
  }

  public void setCdRestrGrade(String cdRestrGrade)
  {
    this.cdRestrGrade = cdRestrGrade;
  }

  public void setCdUsuroUltmaAlter(String cdUsuroUltmaAlter)
  {
    this.cdUsuroUltmaAlter = cdUsuroUltmaAlter;
  }
  
  public void setDtUltmaAlter(Date dtUltmaAlter)
  {
    this.dtUltmaAlter = dtUltmaAlter;
  }

  public void setIcLibec(String icLibec)
  {
    this.icLibec = icLibec;
  }

  public void setIcRgitaOccor(String icRgitaOccor)
  {
    this.icRgitaOccor = icRgitaOccor;
  }

  public void setIcSerasa(String icSerasa)
  {
    this.icSerasa = icSerasa;
  }

  public void setTpOperc(Long tpOperc)
  {
    this.tpOperc = tpOperc;
  }
}