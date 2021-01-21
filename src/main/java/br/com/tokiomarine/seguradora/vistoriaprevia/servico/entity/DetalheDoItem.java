package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import java.io.Serializable;
import java.util.Date;

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
public class DetalheDoItem implements Serializable {

	private static final long serialVersionUID = -7129281317371551696L;

	@EqualsAndHashCode.Include
	private Long nrItseg;
	@EqualsAndHashCode.Include
	private String tpHistoItseg;
	@EqualsAndHashCode.Include
	private Long cdEndos;
	private String propostaPlaca;
	private String propostaChassi;
	private String propostaNomeCliente;
	private Long propsotaClienteCpfCnpj;
	private String propostaFabricante;
	private Long propostaCodFabricante;
	private String propostaModelo;
	private Long propostaCodModelo;
	private String propostaAnoModelo;
	private String propostaCombustivel;
	private Long propostaCodCombustivel;
	private String laudoPlaca;
	private String laudoChassi;
	private String laudoNomeCrlv;
	private Long laudoCpfCnpjCrlv;
	private String laudoFabricante;
	private Long laudoCodFabricante;
	private String laudoModelo;
	private Long laudoCodModelo;
	private Long laudoAnoModelo;
	private String laudoCombustivel;
	private Long laudoCodCombustivel;
	private String laudoStatus;
	private Long cdClien;
	private String codigoLaudo;
	private String responsavel;
	private Date dataDaAcao;
	private String justificativa;
	private String statusRestricao;
	private String urlAmbiente;
	private Long tipoFechamentoRestricao;
	private String codigoSituacaoRestricao;
	private String codigoSituacaoGrade;
	private Boolean liberarDispensaAlcada;	
	private Boolean liberarTabelaReclassificacao;
	private String tituloDaTela;
	private String sitePrestadora;
	private String nomePrestadora;
	private Boolean liberarPrestadora;
	private Long nrVrsaoLvpre;
	private Boolean liberaDetalhe;	
	private String urlLinkDetalhe;
}