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
@Table(name = "VPE0002_ACSRO_VSPRE")
public class ArquivoAcessorios {
	
	@Id
	@Column(name ="CD_ACSRO_VSPRE")
	private Long codigoAcessorio;
	
	@Column(name ="DS_ACSRO_VSPRE")
	private String descricaoAcessorio;
	
	@Column(name ="TP_ACSRO_VSPRE")
	private String tipoAcessorio;
	
	@Column(name ="CD_SITUC_ACSRO_VSPRE")
	private String cdSitucAcsroVspre;
	
}