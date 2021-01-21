package br.com.tokiomarine.seguradora.ext.ssv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name="SSV2100_DESCR_MDUPR")
public class DescricaoMdupr implements Serializable {
	
	private static final long serialVersionUID = -8888391856655182863L;
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name="CD_MDUPR")
	private Long cdMDUPR;
	
	@Column(name="DS_MDUPR")
	private String dsMDUPR;
	
	@Column(name="TP_EMISS")
	private String tpEmissao;
}