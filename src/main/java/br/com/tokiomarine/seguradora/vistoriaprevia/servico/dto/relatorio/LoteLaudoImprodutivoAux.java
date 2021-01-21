package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivo;

public class LoteLaudoImprodutivoAux implements Serializable {

	private static final long serialVersionUID = -3246684847053502076L;

	private LoteLaudoImprodutivo loteLaudoImprodutivo;
	private String nomeCorretor;
	
	private List<LoteLaudoImprodutivoDetalheAux> listaLoteDetalhe = new ArrayList<LoteLaudoImprodutivoDetalheAux>();
	private List<LoteLaudoImprodutivoDetalheAux> listaLoteDetalheAdicional = new ArrayList<LoteLaudoImprodutivoDetalheAux>();
	private List<LoteLaudoImprodutivoDetalheAux> listaPesquisaLaudoAdicional = new ArrayList<LoteLaudoImprodutivoDetalheAux>();

	private Long qtdTotalVistoria = 0L;
	private Double pctPermitidoImprodutiva = 0d;

	/**
	 * Controle Auxiliar de icfranq. Utilizada em conjunto com operação calcular. Varia de acordo com retiradas de laudos.
	 * icFranq somente alterada na base por meio do metodo atualizaValoresEQuantidadesLotes
	 * @see br.com.tokiomarine.seguradora.ssv.improdutiva.vistoriaprevia.bean.dao.VPImprodutivaDAO
	 */
	private String icFranqAux;
	
	private Double valorTotalImprodutivoFinal = 0d;
	private Double valorTotalImprodutivoOriginal = 0d;
	private Double valorTotalLaudoCalculado = 0d;
	private Double valorTotalLaudoRetirado = 0d;
	private Double valorTotalLaudoIncluido = 0d;
	private Double valorTotalLaudoEstornado = 0d;
	
	private Long qtdTotalImprodutivoOriginal = 0l;
	private Long qtdTotalLaudoCalculado = 0l;
	private Long qtdTotalLaudoRetirado = 0l;
	private Long qtdTotalLaudoIncluido = 0l;
	private Long qtdTotalLaudoEstornado = 0l;
	
	private String textoJustificativa;
	
	public LoteLaudoImprodutivoAux(){
		
	}
	
	public LoteLaudoImprodutivoAux(LoteLaudoImprodutivo loteLaudoImprodutivo, String icFranqAux) {
		
		this.loteLaudoImprodutivo = loteLaudoImprodutivo;
		this.icFranqAux = icFranqAux;
	}
	
	public Boolean getRetirado() {
		return "S".equals(loteLaudoImprodutivo.getIcExclu());
	}

	public LoteLaudoImprodutivo getLoteLaudoImprodutivo() {
		return loteLaudoImprodutivo;
	}

	public void setLoteLaudoImprodutivo(LoteLaudoImprodutivo loteLaudoImprodutivo) {
		this.loteLaudoImprodutivo = loteLaudoImprodutivo;
	}

	public String getNomeCorretor() {
		return nomeCorretor;
	}

	public void setNomeCorretor(String nomeCorretor) {
		this.nomeCorretor = nomeCorretor;
	}

	public List<LoteLaudoImprodutivoDetalheAux> getListaLoteDetalhe() {
		return listaLoteDetalhe;
	}

	public void setListaLoteDetalhe(List<LoteLaudoImprodutivoDetalheAux> listaLoteDetalhe) {
		this.listaLoteDetalhe = listaLoteDetalhe;
	}

	public List<LoteLaudoImprodutivoDetalheAux> getListaPesquisaLaudoAdicional() {
		return listaPesquisaLaudoAdicional;
	}

	public void setListaPesquisaLaudoAdicional(List<LoteLaudoImprodutivoDetalheAux> listaPesquisaLaudoAdicional) {
		this.listaPesquisaLaudoAdicional = listaPesquisaLaudoAdicional;
	}

	public List<LoteLaudoImprodutivoDetalheAux> getListaLoteDetalheAdicional() {
		return listaLoteDetalheAdicional;
	}

	public void setListaLoteDetalheAdicional(List<LoteLaudoImprodutivoDetalheAux> listaLoteDetalheAdicional) {
		this.listaLoteDetalheAdicional = listaLoteDetalheAdicional;
	}

	public Long getQtdTotalLaudoIncluido() {
		return qtdTotalLaudoIncluido;
	}

	public void setQtdTotalLaudoIncluido(Long qtdTotalLaudoIncluido) {
		this.qtdTotalLaudoIncluido = qtdTotalLaudoIncluido;
	}

	public Long getQtdTotalLaudoEstornado() {
		return qtdTotalLaudoEstornado;
	}

	public void setQtdTotalLaudoEstornado(Long qtdTotalLaudoEstornado) {
		this.qtdTotalLaudoEstornado = qtdTotalLaudoEstornado;
	}

	public Double getValorTotalLaudoIncluido() {
		return valorTotalLaudoIncluido;
	}

	public void setValorTotalLaudoIncluido(Double valorTotalLaudoIncluido) {
		this.valorTotalLaudoIncluido = valorTotalLaudoIncluido;
	}

	public Double getValorTotalLaudoEstornado() {
		return valorTotalLaudoEstornado;
	}

	public void setValorTotalLaudoEstornado(Double valorTotalLaudoEstornado) {
		this.valorTotalLaudoEstornado = valorTotalLaudoEstornado;
	}

	public String getTextoJustificativa() {
		return textoJustificativa;
	}

	public void setTextoJustificativa(String textoJustificativa) {
		this.textoJustificativa = textoJustificativa;
	}

	public Double getPctPermitidoImprodutiva() {
		return pctPermitidoImprodutiva;
	}

	public void setPctPermitidoImprodutiva(Double pctPermitidoImprodutiva) {
		this.pctPermitidoImprodutiva = pctPermitidoImprodutiva;
	}

	public Long getQtdTotalVistoria() {
		return qtdTotalVistoria;
	}

	public void setQtdTotalVistoria(Long qtdTotalVistoria) {
		this.qtdTotalVistoria = qtdTotalVistoria;
	}

	public Double getValorTotalImprodutivoFinal() {
		return valorTotalImprodutivoFinal;
	}

	public void setValorTotalImprodutivoFinal(Double valorTotalImprodutivoFinal) {
		this.valorTotalImprodutivoFinal = valorTotalImprodutivoFinal;
	}

	public Double getValorTotalImprodutivoOriginal() {
		return valorTotalImprodutivoOriginal;
	}

	public void setValorTotalImprodutivoOriginal(Double valorTotalImprodutivoOriginal) {
		this.valorTotalImprodutivoOriginal = valorTotalImprodutivoOriginal;
	}

	public Double getValorTotalLaudoCalculado() {
		return valorTotalLaudoCalculado;
	}

	public void setValorTotalLaudoCalculado(Double valorTotalLaudoCalculado) {
		this.valorTotalLaudoCalculado = valorTotalLaudoCalculado;
	}

	public Double getValorTotalLaudoRetirado() {
		return valorTotalLaudoRetirado;
	}

	public void setValorTotalLaudoRetirado(Double valorTotalLaudoRetirado) {
		this.valorTotalLaudoRetirado = valorTotalLaudoRetirado;
	}

	public Long getQtdTotalImprodutivoOriginal() {
		return qtdTotalImprodutivoOriginal;
	}

	public void setQtdTotalImprodutivoOriginal(Long qtdTotalImprodutivoOriginal) {
		this.qtdTotalImprodutivoOriginal = qtdTotalImprodutivoOriginal;
	}

	public Long getQtdTotalLaudoCalculado() {
		return qtdTotalLaudoCalculado;
	}

	public void setQtdTotalLaudoCalculado(Long qtdTotalLaudoCalculado) {
		this.qtdTotalLaudoCalculado = qtdTotalLaudoCalculado;
	}

	public Long getQtdTotalLaudoRetirado() {
		return qtdTotalLaudoRetirado;
	}

	public void setQtdTotalLaudoRetirado(Long qtdTotalLaudoRetirado) {
		this.qtdTotalLaudoRetirado = qtdTotalLaudoRetirado;
	}

	public String getIcFranqAux() {
		return icFranqAux == null ? this.getLoteLaudoImprodutivo().getIcFranq() : icFranqAux;
	}

	public void setIcFranqAux(String icFranqAux) {
		this.icFranqAux = icFranqAux;
	}

}