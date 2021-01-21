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
@Table(name = "VPE0433_AVARI_VSPRE")
public class ArquivoAvarias {
	
	@Id
	@Column(name="CD_TIPO_AVARI", nullable=false)
	private String codigoAvaria;
	
	@Column(name="DS_TIPO_AVARI", nullable=false)
	private String descricaoAvaria;
	
	@Column(name="CD_SITUC_AVARI", nullable=false)
	private String cdSitucAvari;
	
}