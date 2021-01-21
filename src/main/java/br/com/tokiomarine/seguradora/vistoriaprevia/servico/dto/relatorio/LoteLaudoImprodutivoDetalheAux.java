package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio;

import java.io.Serializable;
import java.util.Date;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivoDetalhe;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilJava;

public class LoteLaudoImprodutivoDetalheAux implements Serializable {

	private static final long serialVersionUID = -4207646438293192571L;

	public LoteLaudoImprodutivoDetalheAux () {
		
	}
	
	public LoteLaudoImprodutivoDetalheAux(
											LoteLaudoImprodutivoDetalhe loteLaudoImprodutivoDetalhe,
											String icLoteRetirado,
											String icLoteFranquia,
											Date dataLoteEnviado,
											String codLaudo,
											Date dataLaudo,
											String codStatusLaudo,
											String codVoucher,
											Long codNegocio,
											String numPlaca,
											String numChassi,
											String codMotivoImprodutiva) {
		
		this.loteLaudoImprodutivoDetalhe = loteLaudoImprodutivoDetalhe;
		this.icLoteRetirado = icLoteRetirado;
		this.icLoteFranquia = icLoteFranquia;
		this.dataLoteEnviado = dataLoteEnviado;
		this.codLaudo = codLaudo;
		this.dataLaudo = dataLaudo;
		this.codStatusLaudo = codStatusLaudo;
		this.codVoucher = codVoucher;
		this.codNegocio = codNegocio;
		this.numPlaca = numPlaca;
		this.numChassi = numChassi;
		this.codMotivoImprodutiva = codMotivoImprodutiva;
	}
	
	private LoteLaudoImprodutivoDetalhe loteLaudoImprodutivoDetalhe;
	
	private String icLoteRetirado = "N";
	private String icLoteFranquia = "N"; 
	private Date dataLoteEnviado;
	
	private String codLaudo;
	private Date dataLaudo;
	private String codStatusLaudo;
	private String descStatusLaudo;

	private String codMotivoImprodutiva;
	private String descMotivoImprodutiva;

	private String codVoucher;
	private Long codNegocio;

	private String numPlaca;
	private String numChassi;

	private String adicionarLaudo = "N";
	private String descTipoLaudo;
	
	private String removerLaudo = "N";
	
	public Boolean getRetirado() {
		return "S".equals(loteLaudoImprodutivoDetalhe.getIcExclu());
	}
	
	public Boolean getLoteRetirado() {
		return "S".equals(icLoteRetirado);
	}

	public Boolean getLoteDentroFranquia() {
		return "S".equals(icLoteFranquia);
	}
	
	public Boolean getLoteEnviado() {
		return UtilJava.trueVar(dataLoteEnviado);
	}	
	
	public LoteLaudoImprodutivoDetalhe getLoteLaudoImprodutivoDetalhe() {
		return loteLaudoImprodutivoDetalhe;
	}

	public void setLoteLaudoImprodutivoDetalhe(LoteLaudoImprodutivoDetalhe loteLaudoImprodutivoDetalhe) {
		this.loteLaudoImprodutivoDetalhe = loteLaudoImprodutivoDetalhe;
	}

	public String getCodLaudo() {
		return codLaudo;
	}

	public void setCodLaudo(String codLaudo) {
		this.codLaudo = codLaudo;
	}

	public Date getDataLaudo() {
		return dataLaudo;
	}

	public void setDataLaudo(Date dataLaudo) {
		this.dataLaudo = dataLaudo;
	}

	public String getCodStatusLaudo() {
		return codStatusLaudo;
	}

	public void setCodStatusLaudo(String codStatusLaudo) {
		this.codStatusLaudo = codStatusLaudo;
	}

	public String getDescStatusLaudo() {
		return descStatusLaudo;
	}

	public void setDescStatusLaudo(String descStatusLaudo) {
		this.descStatusLaudo = descStatusLaudo;
	}

	public String getCodMotivoImprodutiva() {
		return codMotivoImprodutiva;
	}

	public void setCodMotivoImprodutiva(String codMotivoImprodutiva) {
		this.codMotivoImprodutiva = codMotivoImprodutiva;
	}

	public String getDescMotivoImprodutiva() {
		return descMotivoImprodutiva;
	}

	public void setDescMotivoImprodutiva(String descMotivoImprodutiva) {
		this.descMotivoImprodutiva = descMotivoImprodutiva;
	}

	public String getCodVoucher() {
		return codVoucher;
	}

	public void setCodVoucher(String codVoucher) {
		this.codVoucher = codVoucher;
	}

	public Long getCodNegocio() {
		return codNegocio;
	}

	public void setCodNegocio(Long codNegocio) {
		this.codNegocio = codNegocio;
	}

	public String getNumPlaca() {
		return numPlaca;
	}

	public void setNumPlaca(String numPlaca) {
		this.numPlaca = numPlaca;
	}

	public String getNumChassi() {
		return numChassi;
	}

	public void setNumChassi(String numChassi) {
		this.numChassi = numChassi;
	}

	public String getAdicionarLaudo() {
		return adicionarLaudo;
	}

	public void setAdicionarLaudo(String adicionarLaudo) {
		this.adicionarLaudo = adicionarLaudo;
	}

	public String getDescTipoLaudo() {
		return descTipoLaudo;
	}

	public void setDescTipoLaudo(String descTipoLaudo) {
		this.descTipoLaudo = descTipoLaudo;
	}

	public String getRemoverLaudo() {
		return removerLaudo;
	}

	public void setRemoverLaudo(String removerLaudo) {
		this.removerLaudo = removerLaudo;
	}

	public String getIcLoteRetirado() {
		return icLoteRetirado;
	}

	public void setIcLoteRetirado(String icLoteRetirado) {
		this.icLoteRetirado = icLoteRetirado;
	}

	public String getIcLoteFranquia() {
		return icLoteFranquia;
	}

	public void setIcLoteFranquia(String icLoteFranquia) {
		this.icLoteFranquia = icLoteFranquia;
	}

	public Date getDataLoteEnviado() {
		return dataLoteEnviado;
	}

	public void setDataLoteEnviado(Date dataLoteEnviado) {
		this.dataLoteEnviado = dataLoteEnviado;
	}
}
