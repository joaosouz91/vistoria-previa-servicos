package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

public class GenericList {

	private String codigo;
	private Integer id;
	private String descricao;
	

	public GenericList() {

	}
	
	
	public GenericList(String codigo, Integer id, String descricao) {
		super();
		this.codigo = codigo;
		this.id = id;
		this.descricao = descricao;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
