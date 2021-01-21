package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "VPE0097_PECA_VEICU_VSPRE")
public class ArquivoPecas {
	
	@Id
	@Column(name="CD_PECA_AVADA", nullable=false)
	private Long codigoPeca;
	
	@Column(name="DS_PECA_AVADA", nullable=false)
	private String descricaoPeca;
	
	@Column(name="CD_SITUC_PECA", nullable=false)
	private String cdSitucPeca;
}
	