package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.tokiomarine.seguradora.aceitacao.crud.model.Proposta;
import br.com.tokiomarine.seguradora.aceitacao.crud.model.Restricao;
import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.ModuloProduto;
import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.ValorCaracteristicaItemSegurado;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.Cliente;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ClientePessoaFisica;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ClientePessoaJuridica;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.ssv.transacional.model.DescricaoItemSegurado;
import br.com.tokiomarine.seguradora.ssv.transacional.model.ItemSegurado;
import br.com.tokiomarine.seguradora.ssv.transacional.model.ModuloNegocio;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParecerTecnicoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PrestadoraVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ProponenteVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VeiculoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.Sucursal;

public class PendenciaProposta implements Serializable {
	
	private static final long serialVersionUID = -1923705210725434144L;
	
	private Long alcada;
	private Long alcadaDispensa;
	private String alcadaTpRestr;
	private Long nrDias;
	private Sucursal sucursal;
	private LocalCaptador local;
	private CorretorVP corretor;
	private Long nrNegocio;
	private Date dtCadmtPpota;
	private Date dtEmissaoNegEnd;
	private String situcNegEnd;
	private ModuloNegocio moduloNegocio;
	private ModuloProduto moduloProduto;
	private Proposta proposta;
	private Restricao restricaoItemSegurado;
	private ItemSegurado itemSegurado;
	private String descModuloProduto;
	private String descMotivoVP;
	private LaudoVistoriaPrevia laudo;
	private PrestadoraVistoriaPrevia prestadoraVistoriaPrevia;
	private VeiculoVistoriaPrevia veiculo;
	private ValorCaracteristicaItemSegurado valorCaracteristicaModelo;
	private Long codigoCombustivelVeiculoLaudo;
	private ValorCaracteristicaItemSegurado valorCaracteristica;
	private String dsFabricanteVeiculo;
	private ProponenteVistoriaPrevia proponente;
	private List<DescricaoItemSegurado> descricaoItemSegurado;
	private String combustivel;
	private List<ParecerTecnicoVistoriaPrevia> informacaoTecnicaList;
	private Cliente cliente;
	private ClientePessoaFisica clientePessoaFisica;
	private ClientePessoaJuridica clientePessoaJuridica;
	private String descrAgrupamentoItemSegurado;
	private ConteudoColunaTipo statusLaudo;
	private ConteudoColunaTipo statusRestricaoItemSegurado;
	private ConteudoColunaTipo tipoFechamentoRestricao;

	// guarda o cdigo do parecer tcnico do laudo anterior que causou a pendncia de reclassificao do laudo atual
	private Long codParecerLaudoAnteriorPendenciaAtual;

	public PendenciaProposta() {
		super();
	}
	
	/**
	 * @param nrDias
	 */
	public void setNrDias(Long nrDias) {
		this.nrDias = nrDias;
	}

	/**
	 * @return
	 */
	public Long getNrDias() {
		return nrDias;
	}

	/**
	 * @param sucursal
	 */
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	/**
	 * @return
	 */
	public Sucursal getSucursal() {
		return sucursal;
	}

	/**
	 * @param local
	 */
	public void setLocal(LocalCaptador local) {
		this.local = local;
	}

	/**
	 * @return
	 */
	public LocalCaptador getLocal() {
		return local;
	}

	/**
	 * @param corretor
	 */
	public void setCorretor(CorretorVP corretor) {
		this.corretor = corretor;
	}

	/**
	 * @return
	 */
	public CorretorVP getCorretor() {
		return corretor;
	}

	/**
	 * @param nrNegocio
	 */
	public void setNrNegocio(Long nrNegocio) {
		this.nrNegocio = nrNegocio;
	}

	/**
	 * @return
	 */
	public Long getNrNegocio() {
		return nrNegocio;
	}

	/**
	 * @param dtCadmtPpota
	 */
	public void setDtCadmtPpota(Date dtCadmtPpota) {
		this.dtCadmtPpota = dtCadmtPpota;
	}

	/**
	 * @return
	 */
	public Date getDtCadmtPpota() {
		return dtCadmtPpota;
	}

	/**
	 * @param dtEmissaoNegEnd
	 */
	public void setDtEmissaoNegEnd(Date dtEmissaoNegEnd) {
		this.dtEmissaoNegEnd = dtEmissaoNegEnd;
	}

	/**
	 * @return
	 */
	public Date getDtEmissaoNegEnd() {
		return dtEmissaoNegEnd;
	}

	//
	/**
	 * @param situcNegEnd
	 */
	public void setSitucNegEnd(String situcNegEnd) {
		this.situcNegEnd = situcNegEnd;
	}

	/**
	 * @return
	 */
	public String getSitucNegEnd() {
		return situcNegEnd;
	}

	/**
	 * @param itemSegurado
	 */
	public void setItemSegurado(ItemSegurado itemSegurado) {
		this.itemSegurado = itemSegurado;
	}

	/**
	 * @return ItemSegurado
	 */
	public ItemSegurado getItemSegurado() {
		return itemSegurado;
	}

	/**
	 * @param informacaoTecnicaList
	 */
	public void setInformacaoTecnicaList(List<ParecerTecnicoVistoriaPrevia> informacaoTecnicaList) {
		this.informacaoTecnicaList = informacaoTecnicaList;
	}

	/**
	 * @return
	 */
	public List<ParecerTecnicoVistoriaPrevia> getInformacaoTecnicaList() {
		return informacaoTecnicaList;
	}

	/**
	 * @param laudo
	 */
	public void setLaudo(LaudoVistoriaPrevia laudo) {
		this.laudo = laudo;
	}

	/**
	 * @return
	 */
	public LaudoVistoriaPrevia getLaudo() {
		return laudo;
	}

	/**
	 * @param descricaoItemSegurado
	 */
	public void setDescricaoItemSegurado(List<DescricaoItemSegurado> descricaoItemSegurado) {
		this.descricaoItemSegurado = descricaoItemSegurado;
	}

	/**
	 * @return
	 */
	public List<DescricaoItemSegurado> getDescricaoItemSegurado() {
		return descricaoItemSegurado;
	}

	/**
	 * @param veiculo
	 */
	public void setVeiculo(VeiculoVistoriaPrevia veiculo) {
		this.veiculo = veiculo;
	}

	public Long getCodigoCombustivelVeiculoLaudo() {
		return codigoCombustivelVeiculoLaudo;
	}

	public void setCodigoCombustivelVeiculoLaudo(Long codigoCombustivelVeiculoLaudo) {
		this.codigoCombustivelVeiculoLaudo = codigoCombustivelVeiculoLaudo;
	}

	/**
	 * @return
	 */
	public VeiculoVistoriaPrevia getVeiculo() {
		return veiculo;
	}

	/**
	 * @param proponente
	 */
	public void setProponente(ProponenteVistoriaPrevia proponente) {
		this.proponente = proponente;
	}

	/**
	 * @return
	 */
	public ProponenteVistoriaPrevia getProponente() {
		return proponente;
	}

	/**
	 * @param restricaoItemSegurado
	 */
	public void setRestricaoItemSegurado(Restricao restricaoItemSegurado) {
		this.restricaoItemSegurado = restricaoItemSegurado;
	}

	/**
	 * @return
	 */
	public Restricao getRestricaoItemSegurado() {
		return restricaoItemSegurado;
	}

	public Proposta getProposta() {
		return proposta;
	}

	public void setProposta(Proposta proposta) {
		this.proposta = proposta;
	}

	/**
	 * @param cliente
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param clientePessoaFisica
	 */
	public void setClientePessoaFisica(ClientePessoaFisica clientePessoaFisica) {
		this.clientePessoaFisica = clientePessoaFisica;
	}

	/**
	 * @return
	 */
	public ClientePessoaFisica getClientePessoaFisica() {
		return clientePessoaFisica;
	}

	/**
	 * @param clientePessoaJuridica
	 */
	public void setClientePessoaJuridica(ClientePessoaJuridica clientePessoaJuridica) {
		this.clientePessoaJuridica = clientePessoaJuridica;
	}

	/**
	 * @return
	 */
	public ClientePessoaJuridica getClientePessoaJuridica() {
		return clientePessoaJuridica;
	}

	/**
	 * @param dsFabricanteVeiculo
	 */
	public void setDsFabricanteVeiculo(String dsFabricanteVeiculo) {
		this.dsFabricanteVeiculo = dsFabricanteVeiculo;
	}

	/**
	 * @return
	 */
	public String getDsFabricanteVeiculo() {
		return dsFabricanteVeiculo;
	}

	/**
	 * @param valorCaracteristica
	 */
	public void setValorCaracteristica(ValorCaracteristicaItemSegurado valorCaracteristica) {
		this.valorCaracteristica = valorCaracteristica;
	}

	/**
	 * @return
	 */
	public ValorCaracteristicaItemSegurado getValorCaracteristica() {
		return valorCaracteristica;
	}

	/**
	 * @param combustivel
	 */
	public void setCombustivel(String combustivel) {
		this.combustivel = combustivel;
	}

	/**
	 * @return
	 */
	public String getCombustivel() {
		return combustivel;
	}

	/**
	 * @param statusLaudo
	 */
	public void setStatusLaudo(ConteudoColunaTipo statusLaudo) {
		this.statusLaudo = statusLaudo;
	}

	/**
	 * @return
	 */
	public ConteudoColunaTipo getStatusLaudo() {
		return statusLaudo;
	}

	/**
	 * @param statusRestricaoItemSegurado
	 */
	public void setStatusRestricaoItemSegurado(ConteudoColunaTipo statusRestricaoItemSegurado) {
		this.statusRestricaoItemSegurado = statusRestricaoItemSegurado;
	}

	/**
	 * @return
	 */
	public ConteudoColunaTipo getStatusRestricaoItemSegurado() {
		return statusRestricaoItemSegurado;
	}

	/**
	 * @param tipoFechamentoRestricao
	 */
	public void setTipoFechamentoRestricao(ConteudoColunaTipo tipoFechamentoRestricao) {
		this.tipoFechamentoRestricao = tipoFechamentoRestricao;
	}

	/**
	 * @return
	 */
	public ConteudoColunaTipo getTipoFechamentoRestricao() {
		return tipoFechamentoRestricao;
	}

	/**
	 * @param moduloNegocio
	 */
	public void setModuloNegocio(ModuloNegocio moduloNegocio) {
		this.moduloNegocio = moduloNegocio;
	}

	/**
	 * @return
	 */
	public ModuloNegocio getModuloNegocio() {
		return moduloNegocio;
	}

	/**
	 * @param descModuloProduto
	 */
	public void setDescModuloProduto(String descModuloProduto) {
		this.descModuloProduto = descModuloProduto;
	}

	/**
	 * @return
	 */
	public String getDescModuloProduto() {
		return descModuloProduto;
	}

	/**
	 * @param alcada
	 */
	public void setAlcada(Long alcada) {
		this.alcada = alcada;
	}

	/**
	 * @return
	 */
	public Long getAlcada() {
		return alcada;
	}

	/**
	 * @param alcadaTpRestr
	 */
	public void setAlcadaTpRestr(String alcadaTpRestr) {
		this.alcadaTpRestr = alcadaTpRestr;
	}

	/**
	 * @return
	 */
	public String getAlcadaTpRestr() {
		return alcadaTpRestr;
	}

	/**
	 * @param alcadaDispensa
	 */
	public void setAlcadaDispensa(Long alcadaDispensa) {
		this.alcadaDispensa = alcadaDispensa;
	}

	/**
	 * @return
	 */
	public Long getAlcadaDispensa() {
		return alcadaDispensa;
	}

	/**
	 * @param moduloProduto
	 */
	public void setModuloProduto(ModuloProduto moduloProduto) {
		this.moduloProduto = moduloProduto;
	}

	/**
	 * @return
	 */
	public ModuloProduto getModuloProduto() {
		return moduloProduto;
	}

	/**
	 * @param descrAgrupamentoItemSegurado
	 */
	public void setDescrAgrupamentoItemSegurado(String descrAgrupamentoItemSegurado) {
		this.descrAgrupamentoItemSegurado = descrAgrupamentoItemSegurado;
	}

	/**
	 * @return
	 */
	public String getDescrAgrupamentoItemSegurado() {
		return descrAgrupamentoItemSegurado;
	}

	public Long getCodParecerLaudoAnteriorPendenciaAtual() {
		return codParecerLaudoAnteriorPendenciaAtual;
	}

	public void setCodParecerLaudoAnteriorPendenciaAtual(Long codParecerLaudoAnteriorPendenciaAtual) {
		this.codParecerLaudoAnteriorPendenciaAtual = codParecerLaudoAnteriorPendenciaAtual;
	}

	public PrestadoraVistoriaPrevia getPrestadoraVistoriaPrevia() {
		return prestadoraVistoriaPrevia;
	}

	public void setPrestadoraVistoriaPrevia(PrestadoraVistoriaPrevia prestadoraVistoriaPrevia) {
		this.prestadoraVistoriaPrevia = prestadoraVistoriaPrevia;
	}

	public ValorCaracteristicaItemSegurado getValorCaracteristicaModelo() {
		return valorCaracteristicaModelo;
	}

	public void setValorCaracteristicaModelo(ValorCaracteristicaItemSegurado valorCaracteristicaModelo) {
		this.valorCaracteristicaModelo = valorCaracteristicaModelo;
	}

	public String getDescMotivoVP() {
		return descMotivoVP;
	}

	public void setDescMotivoVP(String descMotivoVP) {
		this.descMotivoVP = descMotivoVP;
	}

}
