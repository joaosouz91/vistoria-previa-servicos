package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="VPE0443_REGIAO_ATNMT_VSTRO")
public class Municipio {

	@Column(name ="SG_UF", columnDefinition = "VARCHAR2(2)", nullable = false)
	private String uf;
	
	@Column(name ="CD_MUNIC", columnDefinition = "NUMBER(7)", nullable = false)
	private Long codMunicipio;
	
	@Column(name ="NM_MUNIC", columnDefinition = "VARCHAR2(100)")
	private String nmMunicipio;
	
	@Column(name ="IC_ATIVO", columnDefinition = "VARCHAR2(1)")
	private String icAtivo;

	@Column(name ="CD_USURO_ULTMA_ALTER", columnDefinition = "VARCHAR2(8)")
	private String codUsuarioUltAlter;
	
	@Column(name ="DT_ULTMA_ALTER", columnDefinition = "DATE")
	private LocalDateTime dtUltiAlter;
	
	@Id
	@Column(name= "ID_REGIAO_ATNMT_VSTRO")
	private Long idRegiaoAtnmtVstro;
}
