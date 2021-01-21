package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonStringToBoolean;
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
public class RegiaoAtendimentoVistoriadorDTO {

	@EqualsAndHashCode.Include
	private Long idRegiaoAtnmtVstro;
	
	@EqualsAndHashCode.Include
	private String sgUf;
	
	@EqualsAndHashCode.Include
	private Long cdMunic;
	
	private String nmMunic;
	
	@JsonStringToBoolean(strTrue = "S", strFalse = "N")
	private String icAtivo;

	private String cdUsuroUltmaAlter;
	
	private Date dtUltmaAlter;
	
	@Transient
	private List<ParametroPercentualDistribuicaoDTO> distribuicao;

	public RegiaoAtendimentoVistoriadorDTO(Long idRegiaoAtnmtVstro, String sgUf, Long cdMunic, String nmMunic) {
		this.idRegiaoAtnmtVstro = idRegiaoAtnmtVstro;
		this.sgUf = sgUf;
		this.cdMunic = cdMunic;
		this.nmMunic = nmMunic;
	}
}