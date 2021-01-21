package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ListaDeItem implements Serializable {
	
	private static final long serialVersionUID = -9075121641818620226L;

	@EqualsAndHashCode.Include
	private Long nrOrdemItSeg; 
	@EqualsAndHashCode.Include
	private Long nritSeg;
	@EqualsAndHashCode.Include
	private Long cdEndos;
	private String dsVlcarInfdoFabricante;
	private String dsVlcarInfdoModelo;
	private String dsVlcarInfdoAnoModelo;
	private String dsVlcarInfdoAnoFabricante;
	private Long cdPlacaVeicu; 
	private Long cdChassiVeicu;
	private Long cdSitucVspre;
	private String dsRmidaColunTipo;
}