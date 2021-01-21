package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreAgendamentoSantanderNoCotador implements Serializable {

	private static final long serialVersionUID = 1938235817228066216L;

	@JsonProperty(value = "MODELODESC")
	private String descricaoModelo;

	@JsonProperty(value = "FABRICANTECOD")
	private Long codigoFabricante;

	@JsonProperty(value = "MODELOCOD")
	private Long codigoModelo;

	@JsonProperty(value = "ZEROKM")
	private String isZeroKM;

	@JsonProperty(value = "DOCUMENTO")
	private Long numeroDocumento;

	@JsonProperty(value = "PLACA")
	private String placa;

	@JsonProperty(value = "CEP")
	private String numeroCEP;

	@JsonProperty(value = "FABRICANTEDESC")
	private String descricaoFabricante;

	@JsonProperty(value = "MODELOANO")
	private String anoModelo;

	@JsonProperty(value = "NMCLIEN")
	private String nomeCliente;

	@JsonProperty(value = "TPPESSOA")
	private String tipoPessoa;

	@JsonProperty(value = "CHASSI")
	private String chassi;

	@JsonProperty(value = "FABRICACAOANO")
	private String anoFabricacao;
	
	@JsonProperty("CD_COTAC")
	private Long cdCotac;
}
