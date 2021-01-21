package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroPercentualDistribuicao;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PrestadoraVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.RegiaoAtendimentoVistoriador;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class ParametroPercentualDistribuicaoDTO implements Serializable {

	private static final long serialVersionUID = -1619033361993265327L;

	@EqualsAndHashCode.Include
	@JsonProperty("id")
	private Long idParamPerctDistr;

	@EqualsAndHashCode.Include
	@JsonProperty("codigoPrestadora")
	private Long cdAgrmtVspre;

	@EqualsAndHashCode.Include
	@JsonProperty("codigoRegiaoTarifaria")
	private Long cdRegiaoTrfra;

	@JsonProperty("usuarioUltimaAlteracao")
	private String cdUsuroUltmaAlter;

	@JsonProperty("dataFimVigencia")
	private Date dtFimVigen;

	@JsonProperty("dataInicioVigencia")
	private Date dtInicoVigen;

	@JsonProperty("dataUltimaAlteracao")
	private Date dtUltmaAlter;

	@JsonProperty("capital")
	private String icCapit;

	@JsonProperty("percentualDistribuicao")
	private Double pcDistr;
	
	@EqualsAndHashCode.Include
	@JsonProperty("idRegiaoAtnmtVstro")
	private Long idRegiaoAtnmtVstro;

	@JsonProperty("percentualVistoriaAgendada")
	private Double pcVistAgendada;

	@EqualsAndHashCode.Include
	private PrestadoraDTO prestadora;
	
	@EqualsAndHashCode.Include
	private RegiaoAtendimentoVistoriadorDTO regiao;

	public ParametroPercentualDistribuicaoDTO(ParametroPercentualDistribuicao parametro,
			PrestadoraVistoriaPrevia prestadora) {
		this.cdAgrmtVspre = parametro.getCdAgrmtVspre();
		this.cdRegiaoTrfra = parametro.getCdRegiaoTrfra();
		this.cdUsuroUltmaAlter = parametro.getCdUsuroUltmaAlter();
		this.dtFimVigen = parametro.getDtFimVigen();
		this.dtInicoVigen = parametro.getDtInicoVigen();
		this.dtUltmaAlter = parametro.getDtUltmaAlter();
		this.icCapit = parametro.getIcCapit();
		this.idParamPerctDistr = parametro.getIdParamPerctDistr();
		this.pcDistr = parametro.getPcDistr();
		this.idRegiaoAtnmtVstro = parametro.getIdRegiaoAtnmtVstro();

		PrestadoraDTO prestadoraDTO = new PrestadoraDTO();
		prestadoraDTO.setCdAgrmtVspre(prestadora.getCdAgrmtVspre());
		prestadoraDTO.setNmRazaoSocal(prestadora.getNmRazaoSocal());

		this.prestadora = prestadoraDTO;
	}
	
	public ParametroPercentualDistribuicaoDTO(ParametroPercentualDistribuicao parametro,
			PrestadoraVistoriaPrevia prestadora, RegiaoAtendimentoVistoriador regiao) {
		this.cdAgrmtVspre = parametro.getCdAgrmtVspre();
		this.cdRegiaoTrfra = parametro.getCdRegiaoTrfra();
		this.cdUsuroUltmaAlter = parametro.getCdUsuroUltmaAlter();
		this.dtFimVigen = parametro.getDtFimVigen();
		this.dtInicoVigen = parametro.getDtInicoVigen();
		this.dtUltmaAlter = parametro.getDtUltmaAlter();
		this.icCapit = parametro.getIcCapit();
		this.idParamPerctDistr = parametro.getIdParamPerctDistr();
		this.pcDistr = parametro.getPcDistr();
		this.idRegiaoAtnmtVstro = parametro.getIdRegiaoAtnmtVstro();

		PrestadoraDTO prestadoraDTO = new PrestadoraDTO();
		prestadoraDTO.setCdAgrmtVspre(prestadora.getCdAgrmtVspre());
		prestadoraDTO.setNmRazaoSocal(prestadora.getNmRazaoSocal());

		this.prestadora = prestadoraDTO;
		
		RegiaoAtendimentoVistoriadorDTO regiaoDTO = new RegiaoAtendimentoVistoriadorDTO();
		regiaoDTO.setIdRegiaoAtnmtVstro(regiao.getIdRegiaoAtnmtVstro());
		regiaoDTO.setCdMunic(regiao.getCdMunic());
		regiaoDTO.setNmMunic(regiao.getNmMunic());
		regiaoDTO.setSgUf(regiao.getSgUf());
		
		this.regiao = regiaoDTO ;
	}
}
