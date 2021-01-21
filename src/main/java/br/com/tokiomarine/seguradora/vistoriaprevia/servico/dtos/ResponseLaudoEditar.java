package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.ResourceSupport;

import br.com.tokiomarine.seguradora.ssv.transacional.model.ItemSegurado;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParecerTecnicoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ProponenteVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VeiculoVistoriaPrevia;

public class ResponseLaudoEditar extends ResourceSupport implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LaudoVistoriaPrevia laudo;
	
	private ProponenteVistoriaPrevia proponente;
	
	private VeiculoVistoriaPrevia veiculo;
	
	private List<Object> acessorios;
	
	private List<Object> equipSegur;
	
	private List<Object> avarias;
	
	private List<Object> infTech;
	
	private List<Object> propostasVinculadas;
	
	private List<Object> equipamentos;
    
    private List<ItemSegurado>  itens;
    
    private List<ParecerTecnicoVistoriaPrevia> pareceres;
    
    private Boolean isBloqueado = false;
    
    private String menssagemBloqueado;




	public LaudoVistoriaPrevia getLaudo() {
		return laudo;
	}

	public void setLaudo(LaudoVistoriaPrevia laudo) {
		this.laudo = laudo;
	}

	public ProponenteVistoriaPrevia getProponente() {
		return proponente;
	}

	public void setProponente(ProponenteVistoriaPrevia proponente) {
		this.proponente = proponente;
	}

	public VeiculoVistoriaPrevia getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(VeiculoVistoriaPrevia veiculo) {
		this.veiculo = veiculo;
	}

	public List<Object> getAcessorios() {
		return acessorios;
	}

	public void setAcessorios(List<Object> acessorios) {
		this.acessorios = acessorios;
	}

	public List<Object> getEquipSegur() {
		return equipSegur;
	}

	public void setEquipSegur(List<Object> equipSegur) {
		this.equipSegur = equipSegur;
	}

	public List<Object> getAvarias() {
		return avarias;
	}

	public void setAvarias(List<Object> avarias) {
		this.avarias = avarias;
	}

	public List<Object> getInfTech() {
		return infTech;
	}

	public void setInfTech(List<Object> infTech) {
		this.infTech = infTech;
	}

	public List<Object> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Object> equipamentos) {
		this.equipamentos = equipamentos;
	}

	public List<ItemSegurado> getItens() {
		return itens;
	}

	public void setItens(List<ItemSegurado> itens) {
		this.itens = itens;
	}

	public List<ParecerTecnicoVistoriaPrevia> getPareceres() {
		return pareceres;
	}

	public void setPareceres(List<ParecerTecnicoVistoriaPrevia> pareceres) {
		this.pareceres = pareceres;
	}

	public List<Object> getPropostasVinculadas() {
		return propostasVinculadas;
	}

	public void setPropostasVinculadas(List<Object> propostasVinculadas) {
		this.propostasVinculadas = propostasVinculadas;
	}

	public Boolean getIsBloqueado() {
		return isBloqueado;
	}

	public void setIsBloqueado(Boolean isBloqueado) {
		this.isBloqueado = isBloqueado;
	}

	public String getMenssagemBloqueado() {
		return menssagemBloqueado;
	}

	public void setMenssagemBloqueado(String menssagemBloqueado) {
		this.menssagemBloqueado = menssagemBloqueado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(acessorios, avarias, equipSegur, equipamentos, infTech, isBloqueado,
				itens, laudo, menssagemBloqueado, pareceres, proponente, propostasVinculadas, veiculo);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseLaudoEditar other = (ResponseLaudoEditar) obj;
		return Objects.equals(acessorios, other.acessorios) && Objects.equals(avarias, other.avarias)
				&& Objects.equals(equipSegur, other.equipSegur) && Objects.equals(equipamentos, other.equipamentos)
				&& Objects.equals(infTech, other.infTech) && Objects.equals(isBloqueado, other.isBloqueado)
				&& Objects.equals(itens, other.itens) && Objects.equals(laudo, other.laudo)
				&& Objects.equals(menssagemBloqueado, other.menssagemBloqueado)
				&& Objects.equals(pareceres, other.pareceres) && Objects.equals(proponente, other.proponente)
				&& Objects.equals(propostasVinculadas, other.propostasVinculadas)
				&& Objects.equals(veiculo, other.veiculo);
	}

	
}