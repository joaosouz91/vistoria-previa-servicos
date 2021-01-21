package br.com.tokiomarine.seguradora.ext.rest.cta.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class AgendamentoCotadorVP implements Serializable {
	private static final long serialVersionUID = -7728752596253926026L;

	@EqualsAndHashCode.Include
	@JsonProperty("CD_NGOCO_RSVDO")
	private Long cdNgocoRsvdo;
	
	@JsonProperty("NM_CLIEN")
	private String nmClien;
	
	@EqualsAndHashCode.Include
	@JsonProperty("CD_CLIEN")
	private String cdClien;
	
	@JsonProperty("CARACTERISTICAS")
	private List<Caracteristicas> caracteristicas;
	
	@EqualsAndHashCode.Include
	@JsonProperty("NR_ITEM")
	private Long nrItem;
	
	@EqualsAndHashCode.Include
	@JsonProperty("NR_DOCTO")
	private Long nrDocto;
	
	@EqualsAndHashCode.Include
	@JsonProperty("CD_COTAC")
	private Long cdCotac;
}