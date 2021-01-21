package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name = "VPE0096_PATEC_VSPRE")
public class ArquivoCodigosAceitacaoRecusa {

	@Id
	@Column(name="CD_PATEC_VSPRE", nullable=false)
	private Long codigoParecerTecnico;
	
	@Column(name="DS_PATEC_VSPRE", nullable=false)
	private String descricaoParecerTecnico;
	
	@Column(name="CD_CLASF_PATEC", nullable=false)
	private String codigoClassificacaoParecerTecnico;
	
	@Column(name="CD_SITUC_PATEC", nullable=false)
	private String cdSitucPatec;
}