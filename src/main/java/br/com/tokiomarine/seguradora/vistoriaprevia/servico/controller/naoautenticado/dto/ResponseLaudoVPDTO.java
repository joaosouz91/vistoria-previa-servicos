package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RetornoServico;

@JsonInclude(value = Include.ALWAYS)
public class ResponseLaudoVPDTO implements Serializable {

	private String cdLvpre;
	private Long nrVrsaoLvpre;
	private String dsMotvRclsfVspre;
	private String dtVspre;
	@JsonFormat(shape = Shape.NUMBER)
	private Date dtInclsRgist;
	private String cdSitucVspre;
	private String dsObserVspre;
	private String nmSolttVspre;
	@JsonFormat(shape = Shape.NUMBER)
	private Date dtRclsfVspre;
	@JsonFormat(shape = Shape.NUMBER)
	private Date dtTrnsmVspre;
	private String nmCidadOrigmVspre;
	private String nmCidadDestnVspre;
	private Long cdClien;
	private Long cdEndos;
	private Long cdCrtorSegur;
	private String tpLocalVspre;
	private Long nrCepLocalVspre;
	private Long cdLocalCaptc;
	private Long cdSucslComrl;
	private String cdUsuroUltmaAlter;
	private String cdVouch;
	private String sgUniddFedrcVspre;
	private String icLaudoVicdo;
	private Long cdPostoVspre;
	private String cdSitucAnterVspre;
	private String nomeEmpresaVistoriadora;
	private String numeroCPFCNPJVistoriadora;

	private RetornoServico retorno;

	private static final long serialVersionUID = 6749528897349746618L;

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

	public String getDsMotvRclsfVspre() {
		return dsMotvRclsfVspre;
	}

	public void setDsMotvRclsfVspre(String dsMotvRclsfVspre) {
		this.dsMotvRclsfVspre = dsMotvRclsfVspre;
	}

	public String getDtVspre() {
		return dtVspre;
	}

	public void setDtVspre(String dtVspre) {
		this.dtVspre = dtVspre;
	}

	public Date getDtInclsRgist() {
		return dtInclsRgist;
	}

	public void setDtInclsRgist(Date dtInclsRgist) {
		this.dtInclsRgist = dtInclsRgist;
	}

	public String getCdSitucVspre() {
		return cdSitucVspre;
	}

	public void setCdSitucVspre(String cdSitucVspre) {
		this.cdSitucVspre = cdSitucVspre;
	}

	public String getDsObserVspre() {
		return dsObserVspre;
	}

	public void setDsObserVspre(String dsObserVspre) {
		this.dsObserVspre = dsObserVspre;
	}

	public String getNmSolttVspre() {
		return nmSolttVspre;
	}

	public void setNmSolttVspre(String nmSolttVspre) {
		this.nmSolttVspre = nmSolttVspre;
	}

	public Date getDtRclsfVspre() {
		return dtRclsfVspre;
	}

	public void setDtRclsfVspre(Date dtRclsfVspre) {
		this.dtRclsfVspre = dtRclsfVspre;
	}

	public Date getDtTrnsmVspre() {
		return dtTrnsmVspre;
	}

	public void setDtTrnsmVspre(Date dtTrnsmVspre) {
		this.dtTrnsmVspre = dtTrnsmVspre;
	}

	public String getNmCidadOrigmVspre() {
		return nmCidadOrigmVspre;
	}

	public void setNmCidadOrigmVspre(String nmCidadOrigmVspre) {
		this.nmCidadOrigmVspre = nmCidadOrigmVspre;
	}

	public String getNmCidadDestnVspre() {
		return nmCidadDestnVspre;
	}

	public void setNmCidadDestnVspre(String nmCidadDestnVspre) {
		this.nmCidadDestnVspre = nmCidadDestnVspre;
	}

	public Long getCdClien() {
		return cdClien;
	}

	public void setCdClien(Long cdClien) {
		this.cdClien = cdClien;
	}

	public Long getCdEndos() {
		return cdEndos;
	}

	public void setCdEndos(Long cdEndos) {
		this.cdEndos = cdEndos;
	}

	public Long getCdCrtorSegur() {
		return cdCrtorSegur;
	}

	public void setCdCrtorSegur(Long cdCrtorSegur) {
		this.cdCrtorSegur = cdCrtorSegur;
	}

	public String getTpLocalVspre() {
		return tpLocalVspre;
	}

	public void setTpLocalVspre(String tpLocalVspre) {
		this.tpLocalVspre = tpLocalVspre;
	}

	public Long getNrCepLocalVspre() {
		return nrCepLocalVspre;
	}

	public void setNrCepLocalVspre(Long nrCepLocalVspre) {
		this.nrCepLocalVspre = nrCepLocalVspre;
	}

	public Long getCdLocalCaptc() {
		return cdLocalCaptc;
	}

	public void setCdLocalCaptc(Long cdLocalCaptc) {
		this.cdLocalCaptc = cdLocalCaptc;
	}

	public Long getCdSucslComrl() {
		return cdSucslComrl;
	}

	public void setCdSucslComrl(Long cdSucslComrl) {
		this.cdSucslComrl = cdSucslComrl;
	}

	public String getCdUsuroUltmaAlter() {
		return cdUsuroUltmaAlter;
	}

	public void setCdUsuroUltmaAlter(String cdUsuroUltmaAlter) {
		this.cdUsuroUltmaAlter = cdUsuroUltmaAlter;
	}

	public String getCdVouch() {
		return cdVouch;
	}

	public void setCdVouch(String cdVouch) {
		this.cdVouch = cdVouch;
	}

	public String getSgUniddFedrcVspre() {
		return sgUniddFedrcVspre;
	}

	public void setSgUniddFedrcVspre(String sgUniddFedrcVspre) {
		this.sgUniddFedrcVspre = sgUniddFedrcVspre;
	}

	public String getIcLaudoVicdo() {
		return icLaudoVicdo;
	}

	public void setIcLaudoVicdo(String icLaudoVicdo) {
		this.icLaudoVicdo = icLaudoVicdo;
	}

	public Long getCdPostoVspre() {
		return cdPostoVspre;
	}

	public void setCdPostoVspre(Long cdPostoVspre) {
		this.cdPostoVspre = cdPostoVspre;
	}

	public String getCdSitucAnterVspre() {
		return cdSitucAnterVspre;
	}

	public void setCdSitucAnterVspre(String cdSitucAnterVspre) {
		this.cdSitucAnterVspre = cdSitucAnterVspre;
	}

	public RetornoServico getRetorno() {
		return retorno;
	}

	public void setRetorno(RetornoServico retorno) {
		this.retorno = retorno;
	}

	public String getNomeEmpresaVistoriadora() {
		return nomeEmpresaVistoriadora;
	}

	public void setNomeEmpresaVistoriadora(String nomeVistoriadora) {
		this.nomeEmpresaVistoriadora = nomeVistoriadora;
	}

	public String getNumeroCPFCNPJVistoriadora() {
		return numeroCPFCNPJVistoriadora;
	}

	public void setNumeroCPFCNPJVistoriadora(String numeroCPFCNPJVistoriadora) {
		this.numeroCPFCNPJVistoriadora = numeroCPFCNPJVistoriadora;
	}

}