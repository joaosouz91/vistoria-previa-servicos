package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.List;


import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonPluralRootName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
@JsonRootName("laudo")
@JsonPluralRootName("laudo")
@JsonInclude(Include.NON_NULL)
public class LaudoDTO extends ResourceSupport implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private LaudoVistoriaPreviaDTO laudo;
	
	private VeiculoVistoriaPreviaDTO veiculo;
	
	private ProponenteVistoriaPreviaDTO proponente;
	
	private List<Object> acessorios;
	
	private List<Object> equipSegur;
	
	private List<Object> avarias;

	private List<Object> infTech;
	
	private List<Object> equipamentos;
	
	private List<Object> propostasVinculadas;
	
	private List<ItemSeguradoDTO> itens;
	
	private List<ParecerTecnicoDTO> pareceres;
	
	private Boolean isBloqueado = false;
    
    private String menssagemBloqueado;
}