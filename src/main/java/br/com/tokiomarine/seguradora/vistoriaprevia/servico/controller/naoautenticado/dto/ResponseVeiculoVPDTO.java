package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RetornoServico;

@JsonInclude(value = Include.ALWAYS)
public class ResponseVeiculoVPDTO implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = -7978205618407959065L;
	private String cdCamboVeicu;//
	private String cdCarroVeicu;//
	private String cdChassiVeicu;//
	private String cdDutVeicu;//
	private String cdEixoDiantVeicu;
	private String cdEixoTrsroVeicu;
	private Long cdFabrt;//
	private Long cdFormaCarroVeicu;//
	private String cdLvpre;//
	private Long cdModelVeicu;
	private String cdMotorVeicu;
	private String cdOrigmChassi;//
	private String cdPlacaVeicu;//
	private String cdRenavam;//
	private String dsCorVeicu;
	private String dsMarcaCarroCarga;
	private String dsModelVeicu;//
	@JsonFormat(shape = Shape.NUMBER)
	private Date dtExpdcDut;//


	private String icVeicuCarga;//
	private String icVeicuTrafm;
	private String nmAlienVeicu;//
	private String nmCidadExpdcDut;//
	private String nmDutVeicu;//
	private Long nrAnoFabrcVeicu;
	private Long nrAnoModelVeicu;
	private Long nrCnpjDut;//
	private Long nrCpfDut;//
	private Long nrVrsaoLvpre;//

	private String qtLotacVeicu;//
	private String sgUniddFedrcDut;//
	private Long statuDecodChasis;

	private Long tpCamboVeicu;//
	private Long tpCarroVeicu;
	private String tpCmbst;//

	private Long qtKmRdadoVeicu;//

	private RetornoServico retorno;


	public String getCdCamboVeicu() {
		return cdCamboVeicu;
	}


	public void setCdCamboVeicu(String cdCamboVeicu) {
		this.cdCamboVeicu = cdCamboVeicu;
	}


	public String getCdCarroVeicu() {
		return cdCarroVeicu;
	}


	public void setCdCarroVeicu(String cdCarroVeicu) {
		this.cdCarroVeicu = cdCarroVeicu;
	}


	public String getCdChassiVeicu() {
		return cdChassiVeicu;
	}


	public void setCdChassiVeicu(String cdChassiVeicu) {
		this.cdChassiVeicu = cdChassiVeicu;
	}


	public String getCdDutVeicu() {
		return cdDutVeicu;
	}


	public void setCdDutVeicu(String cdDutVeicu) {
		this.cdDutVeicu = cdDutVeicu;
	}


	public String getCdEixoDiantVeicu() {
		return cdEixoDiantVeicu;
	}


	public void setCdEixoDiantVeicu(String cdEixoDiantVeicu) {
		this.cdEixoDiantVeicu = cdEixoDiantVeicu;
	}


	public String getCdEixoTrsroVeicu() {
		return cdEixoTrsroVeicu;
	}


	public void setCdEixoTrsroVeicu(String cdEixoTrsroVeicu) {
		this.cdEixoTrsroVeicu = cdEixoTrsroVeicu;
	}


	public Long getCdFabrt() {
		return cdFabrt;
	}


	public void setCdFabrt(Long cdFabrt) {
		this.cdFabrt = cdFabrt;
	}


	public Long getCdFormaCarroVeicu() {
		return cdFormaCarroVeicu;
	}


	public void setCdFormaCarroVeicu(Long cdFormaCarroVeicu) {
		this.cdFormaCarroVeicu = cdFormaCarroVeicu;
	}


	public String getCdLvpre() {
		return cdLvpre;
	}


	public void setCdLvpre(String cdLvpre) {
		this.cdLvpre = cdLvpre;
	}


	public Long getCdModelVeicu() {
		return cdModelVeicu;
	}


	public void setCdModelVeicu(Long cdModelVeicu) {
		this.cdModelVeicu = cdModelVeicu;
	}


	public String getCdMotorVeicu() {
		return cdMotorVeicu;
	}


	public void setCdMotorVeicu(String cdMotorVeicu) {
		this.cdMotorVeicu = cdMotorVeicu;
	}


	public String getCdOrigmChassi() {
		return cdOrigmChassi;
	}


	public void setCdOrigmChassi(String cdOrigmChassi) {
		this.cdOrigmChassi = cdOrigmChassi;
	}


	public String getCdPlacaVeicu() {
		return cdPlacaVeicu;
	}


	public void setCdPlacaVeicu(String cdPlacaVeicu) {
		this.cdPlacaVeicu = cdPlacaVeicu;
	}


	public String getCdRenavam() {
		return cdRenavam;
	}


	public void setCdRenavam(String cdRenavam) {
		this.cdRenavam = cdRenavam;
	}


	public String getDsCorVeicu() {
		return dsCorVeicu;
	}


	public void setDsCorVeicu(String dsCorVeicu) {
		this.dsCorVeicu = dsCorVeicu;
	}


	public String getDsMarcaCarroCarga() {
		return dsMarcaCarroCarga;
	}


	public void setDsMarcaCarroCarga(String dsMarcaCarroCarga) {
		this.dsMarcaCarroCarga = dsMarcaCarroCarga;
	}


	public String getDsModelVeicu() {
		return dsModelVeicu;
	}


	public void setDsModelVeicu(String dsModelVeicu) {
		this.dsModelVeicu = dsModelVeicu;
	}


	public Date getDtExpdcDut() {
		return dtExpdcDut;
	}


	public void setDtExpdcDut(Date dtExpdcDut) {
		this.dtExpdcDut = dtExpdcDut;
	}


	public String getIcVeicuCarga() {
		return icVeicuCarga;
	}


	public void setIcVeicuCarga(String icVeicuCarga) {
		this.icVeicuCarga = icVeicuCarga;
	}


	public String getIcVeicuTrafm() {
		return icVeicuTrafm;
	}


	public void setIcVeicuTrafm(String icVeicuTrafm) {
		this.icVeicuTrafm = icVeicuTrafm;
	}


	public String getNmAlienVeicu() {
		return nmAlienVeicu;
	}


	public void setNmAlienVeicu(String nmAlienVeicu) {
		this.nmAlienVeicu = nmAlienVeicu;
	}


	public String getNmCidadExpdcDut() {
		return nmCidadExpdcDut;
	}


	public void setNmCidadExpdcDut(String nmCidadExpdcDut) {
		this.nmCidadExpdcDut = nmCidadExpdcDut;
	}


	public String getNmDutVeicu() {
		return nmDutVeicu;
	}


	public void setNmDutVeicu(String nmDutVeicu) {
		this.nmDutVeicu = nmDutVeicu;
	}


	public Long getNrAnoFabrcVeicu() {
		return nrAnoFabrcVeicu;
	}


	public void setNrAnoFabrcVeicu(Long nrAnoFabrcVeicu) {
		this.nrAnoFabrcVeicu = nrAnoFabrcVeicu;
	}


	public Long getNrAnoModelVeicu() {
		return nrAnoModelVeicu;
	}


	public void setNrAnoModelVeicu(Long nrAnoModelVeicu) {
		this.nrAnoModelVeicu = nrAnoModelVeicu;
	}


	public Long getNrCnpjDut() {
		return nrCnpjDut;
	}


	public void setNrCnpjDut(Long nrCnpjDut) {
		this.nrCnpjDut = nrCnpjDut;
	}


	public Long getNrCpfDut() {
		return nrCpfDut;
	}


	public void setNrCpfDut(Long nrCpfDut) {
		this.nrCpfDut = nrCpfDut;
	}


	public Long getNrVrsaoLvpre() {
		return nrVrsaoLvpre;
	}


	public void setNrVrsaoLvpre(Long nrVrsaoLvpre) {
		this.nrVrsaoLvpre = nrVrsaoLvpre;
	}


	public String getQtLotacVeicu() {
		return qtLotacVeicu;
	}


	public void setQtLotacVeicu(String qtLotacVeicu) {
		this.qtLotacVeicu = qtLotacVeicu;
	}


	public String getSgUniddFedrcDut() {
		return sgUniddFedrcDut;
	}


	public void setSgUniddFedrcDut(String sgUniddFedrcDut) {
		this.sgUniddFedrcDut = sgUniddFedrcDut;
	}


	public Long getStatuDecodChasis() {
		return statuDecodChasis;
	}


	public void setStatuDecodChasis(Long statuDecodChasis) {
		this.statuDecodChasis = statuDecodChasis;
	}


	public Long getTpCamboVeicu() {
		return tpCamboVeicu;
	}


	public void setTpCamboVeicu(Long tpCamboVeicu) {
		this.tpCamboVeicu = tpCamboVeicu;
	}


	public Long getTpCarroVeicu() {
		return tpCarroVeicu;
	}


	public void setTpCarroVeicu(Long tpCarroVeicu) {
		this.tpCarroVeicu = tpCarroVeicu;
	}


	public String getTpCmbst() {
		return tpCmbst;
	}


	public void setTpCmbst(String tpCmbst) {
		this.tpCmbst = tpCmbst;
	}


	public RetornoServico getRetorno() {
		return retorno;
	}


	public void setRetorno(RetornoServico retorno) {
		this.retorno = retorno;
	}



	public Long getQtKmRdadoVeicu() {
		return qtKmRdadoVeicu;
	}



	public void setQtKmRdadoVeicu(Long qtKmRdadoVeicu) {
		this.qtKmRdadoVeicu = qtKmRdadoVeicu;
	}
}
