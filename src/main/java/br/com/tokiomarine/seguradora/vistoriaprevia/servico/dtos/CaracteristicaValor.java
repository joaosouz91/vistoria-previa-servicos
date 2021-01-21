package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

/**
 * Model que mapeia informações de valor de caracteristica.<br>
 * 
 * <b>Observação:</b>Sobrecarga no método 'equals' do Objeto.<br>
 * 
 * @author Rafael Moreira
 */
@SuppressWarnings("serial")
@SqlResultSetMapping( name = "CaracteristicaValor", entities = { @EntityResult( entityClass = CaracteristicaValor.class, fields = { 
    @FieldResult( name = "codCaracteristica", column = "CD_CARAC_ITSEG" ), 
    @FieldResult( name = "codValorCaracteristica", column = "CD_VLCAR_ITSEG" ), 
    @FieldResult( name = "descVariacaoInicial", column = "DS_VARIC_INICO" ),
    @FieldResult( name = "descVariacaoFinal", column = "DS_VARIC_FINAL" ), 
    @FieldResult( name = "descValorCaracteristica", column = "DS_VLCAR" ), 
    @FieldResult( name = "icAgrmt", column = "IC_AGRMT" ),
    @FieldResult( name = "sqVlcarItseg", column = "SQ_VLCAR_ITSEG" ),
    @FieldResult( name = "nmAnalo", column = "NM_ANALO" ),
    @FieldResult( name = "icRiscoDeclv", column = "IC_RISCO_DECLV" ),
    @FieldResult( name = "cdAgatv", column = "CD_AGATV" ),
    @FieldResult( name = "nmAgatv", column = "NM_AGATV" ),
    @FieldResult( name = "sqAgatv", column = "SQ_AGATV" ),
    @FieldResult( name = "icLoclzShopp", column = "IC_LOCLZ_SHOPP" ),
    //OS 144757 -- Codigo Fabricante e Modelo 
    @FieldResult( name = "cdFabrt", column = "CD_FABRT" ),
    @FieldResult( name = "dsFabrt", column = "DS_FABRT" ),
    
    // -- NOVO COTADOR -- 
    @FieldResult( name = "cdMarcaModel", column = "CD_MARCA_MODEL" ),
    @FieldResult( name = "dsMarcaModel", column = "DS_MARCA_MODEL" ),
    @FieldResult( name = "anoMarcaModel", column = "ANO_MARCA_MODEL" ),    
}) })
    @Entity
public class CaracteristicaValor implements Serializable {

	@Id
	@Column(name = "CD_CARAC_ITSEG")
	private Long codCaracteristica;
	
	@Id
	@Column(name = "CD_VLCAR_ITSEG")
	private Long codValorCaracteristica;
	
	@Column(name = "DS_VARIC_INICO")
	private String descVariacaoInicial;
	
	@Column(name = "DS_VARIC_FINAL")
	private String descVariacaoFinal;
	
	@Column(name = "DS_VLCAR")
	private String descValorCaracteristica;
	
	@Transient
	private String descExibicaoTela;
	
	@Column(name = "IC_AGRMT")
	private String icAgrmt;
	
	@Column(name = "SQ_VLCAR_ITSEG")
    private Long sqVlcarItseg;
	
	@Id
	@Column(name = "NM_ANALO")
    private String nmAnalo;
	
	@Column(name = "IC_RISCO_DECLV")
    private String icRiscoDeclv;
	
	@Id
	@Column(name = "CD_AGATV")
    private String cdAgatv;

	@Column(name = "NM_AGATV")
    private String nmAgatv;

	@Column(name = "SQ_AGATV")
    private Long sqAgatv;
	
	@Id
	@Column(name = "IC_LOCLZ_SHOPP")
    private String icLoclzShopp;
	
	@Column(name = "CD_FABRT")
    private Long cdFabrt;
	
	@Column(name = "DS_FABRT")
    private String dsFabrt;
	
	@Column(name = "CD_MARCA_MODEL")
    private Long cdMarcaModel;
	
	@Column(name = "DS_MARCA_MODEL")
    private String dsMarcaModel;
	
	@Column(name = "ANO_MARCA_MODEL")
    private Long anoMarcaModel;

    
	/**
	 * Construtor Padrão
	 */
	public CaracteristicaValor() {
	}

	
	public CaracteristicaValor(
			Long codCaracteristica ,
			Long codValorCaracteristica, 
			Long sqVlcarItseg, 
			String descVariacaoInicial,
			String icAgrmt) {
		
		this.codCaracteristica = codCaracteristica;
		this.codValorCaracteristica = codValorCaracteristica;  
		this.sqVlcarItseg = sqVlcarItseg; 
		this.descVariacaoInicial = descVariacaoInicial;
		this.icAgrmt = icAgrmt;
	}
	
	/**
	 * Sobrecarga do contrutor.<br>
	 * 
	 * @param codCaracteristica
	 *            Código da Caracteristica
	 * @param codValorCaracteristica
	 *            Código Valor da Caracteristica
	 * @param descVariacaoInicial
	 *            Descrição Variacção Inicial da Caracteristica
	 */
	public CaracteristicaValor(Long codCaracteristica, 
							   Long codValorCaracteristica, 
							   String descVariacaoInicial, 
							   String descVariacaoFinal, 
							   String descValorCaracteristica,
							   String icAgrmt,
							   Long sqVlcarItseg,
							   Long nrNivelIdetc) {

		if (descValorCaracteristica == null) {
			if (descVariacaoInicial != null && descVariacaoFinal != null) {
				this.descExibicaoTela = descVariacaoInicial + " - " + descVariacaoFinal;
			} else {
				this.descExibicaoTela = descVariacaoInicial;
			}
		} else if (descValorCaracteristica != null && descVariacaoInicial != null) {
			this.descExibicaoTela = descVariacaoInicial + " - " + descValorCaracteristica;
		} else if (descVariacaoInicial == null) {
			this.descExibicaoTela = descValorCaracteristica;
		}

		this.codCaracteristica = codCaracteristica;
		this.codValorCaracteristica = codValorCaracteristica;
		this.descVariacaoInicial = descVariacaoInicial;
		this.descVariacaoFinal = descVariacaoFinal;
		this.descValorCaracteristica = descValorCaracteristica;
		this.sqVlcarItseg = sqVlcarItseg;
	}
	
	public CaracteristicaValor(
			Long codCaracteristica , 
			Long codValorCaracteristica, 
			Long sqVlcarItseg, 
			String descVariacaoInicial, 
			String icAgrmt,
			Long cdFabrt,			
			Long cdMarcaModel,
			String dsFabrt,
			String dsMarcaModel,
			Long anoMarcaModel
			) {
		
		this.codCaracteristica = codCaracteristica;
		this.codValorCaracteristica = codValorCaracteristica;  
		this.sqVlcarItseg = sqVlcarItseg; 
		this.descVariacaoInicial = descVariacaoInicial; 
		this.cdFabrt = cdFabrt;
		this.cdMarcaModel = cdMarcaModel;
		this.dsFabrt = dsFabrt;
		this.dsMarcaModel = dsMarcaModel;
		this.anoMarcaModel = anoMarcaModel;
	}
	
	public CaracteristicaValor(
			Long codCaracteristica , 
			Long codValorCaracteristica, 
			Long sqVlcarItseg, 
			String descVariacaoInicial, 
			String icAgrmt,
			Long cdFabrt,			
			Long cdMarcaModel
			) {
		
		this.codCaracteristica = codCaracteristica;
		this.codValorCaracteristica = codValorCaracteristica;  
		this.sqVlcarItseg = sqVlcarItseg; 
		this.descVariacaoInicial = descVariacaoInicial; 
		this.cdFabrt = cdFabrt;
		this.cdMarcaModel = cdMarcaModel;
	}
	
	public CaracteristicaValor(
			Long codCaracteristica , 
			Long codValorCaracteristica, 
			Long sqVlcarItseg, 
			String descVariacaoInicial, 
			String icAgrmt,
			Long cdFabrt) {
		
		this.codCaracteristica = codCaracteristica;
		this.codValorCaracteristica = codValorCaracteristica;  
		this.sqVlcarItseg = sqVlcarItseg; 
		this.descVariacaoInicial = descVariacaoInicial; 
		this.cdFabrt = cdFabrt;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (o == null) {
			return false;
		}
		
		if (!(o instanceof CaracteristicaValor)) {
			return false;
		}
		
		CaracteristicaValor outra = (CaracteristicaValor) o;
		if (outra.getCodValorCaracteristica() == null || 
				this.getCodValorCaracteristica() == null) {
			return false;
		}
		
		return outra.getCodValorCaracteristica().equals(this.getCodValorCaracteristica());
			
	}

	public Long getCodCaracteristica() {
		return codCaracteristica;
	}

	public void setCodCaracteristica(Long codCaracteristica) {
		this.codCaracteristica = codCaracteristica;
	}

	public Long getCodValorCaracteristica() {
		return codValorCaracteristica;
	}

	public void setCodValorCaracteristica(Long codValorCaracteristica) {
		this.codValorCaracteristica = codValorCaracteristica;
	}

	public String getDescVariacaoInicial() {
		return descVariacaoInicial;
	}

	public void setDescVariacaoInicial(String descVariacaoInicial) {
		this.descVariacaoInicial = descVariacaoInicial;
	}

	public String getDescVariacaoFinal() {
		return descVariacaoFinal;
	}

	public void setDescVariacaoFinal(String descVariacaoFinal) {
		this.descVariacaoFinal = descVariacaoFinal;
	}

	public String getDescValorCaracteristica() {
		return descValorCaracteristica;
	}

	public void setDescValorCaracteristica(String descValorCaracteristica) {
		this.descValorCaracteristica = descValorCaracteristica;
	}

	public String getDescExibicaoTela() {
		return descExibicaoTela;
	}

	public void setDescExibicaoTela(String descExibicaoTela) {
		this.descExibicaoTela = descExibicaoTela;
	}
	
	public String getIcAgrmt() {
		return icAgrmt;
	}
	
	public void setIcAgrmt(String icAgrmt) {
		this.icAgrmt = icAgrmt;
	}


	public Long getSqVlcarItseg() {
		return sqVlcarItseg;
	}

	public void setSqVlcarItseg(Long sqVlcarItseg) {
		this.sqVlcarItseg = sqVlcarItseg;
	}

	public String getNmAnalo() {
		return nmAnalo;
	}

	public void setNmAnalo(String nmAnalo) {
		this.nmAnalo = nmAnalo;
	}

	public String getIcRiscoDeclv() {
		return icRiscoDeclv;
	}

	public void setIcRiscoDeclv(String icRiscoDeclv) {
		this.icRiscoDeclv = icRiscoDeclv;
	}

	public String getCdAgatv() {
		return cdAgatv;
	}

	public void setCdAgatv(String cdAgatv) {
		this.cdAgatv = cdAgatv;
	}

	public String getNmAgatv() {
		return nmAgatv;
	}

	public void setNmAgatv(String nmAgatv) {
		this.nmAgatv = nmAgatv;
	}

	public Long getSqAgatv() {
		return sqAgatv;
	}

	public void setSqAgatv(Long sqAgatv) {
		this.sqAgatv = sqAgatv;
	}

	public String getIcLoclzShopp() {
		return icLoclzShopp;
	}

	public void setIcLoclzShopp(String icLoclzShopp) {
		this.icLoclzShopp = icLoclzShopp;
	}

	public Long getCdFabrt() {
		return cdFabrt;
	}

	public void setCdFabrt(Long cdFabrt) {
		this.cdFabrt = cdFabrt;
	}

	public Long getCdMarcaModel() {
		return cdMarcaModel;
	}

	public void setCdMarcaModel(Long cdMarcaModel) {
		this.cdMarcaModel = cdMarcaModel;
	}


	public String getDsFabrt() {
		return dsFabrt;
	}


	public void setDsFabrt(String dsFabrt) {
		this.dsFabrt = dsFabrt;
	}


	public String getDsMarcaModel() {
		return dsMarcaModel;
	}


	public void setDsMarcaModel(String dsMarcaModel) {
		this.dsMarcaModel = dsMarcaModel;
	}


	public Long getAnoMarcaModel() {
		return anoMarcaModel;
	}


	public void setAnoMarcaModel(Long anoMarcaModel) {
		this.anoMarcaModel = anoMarcaModel;
	}
	
	
	
}
