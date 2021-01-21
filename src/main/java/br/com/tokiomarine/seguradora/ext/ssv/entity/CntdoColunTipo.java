package br.com.tokiomarine.seguradora.ext.ssv.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name="SSV4001_CNTDO_COLUN_TIPO")
public class CntdoColunTipo implements Serializable {
	
	private static final long serialVersionUID = -6071900839976794246L;
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name="NM_COLUN_TIPO")
	private String nmColunTipo;
	
	@Column(name="VL_CNTDO_COLUN_TIPO")
	private String vlCntdoColunTipo;
	
	@Column(name="SG_SISTM_INFOR")
	private String sgSistmInfor;
	
	@Column(name="DS_COPTA_COLUN_TIPO")
	private String dsCoptaColunTipo;
	
	@Column(name="DS_RMIDA_COLUN_TIPO")
	private String dsRmidaColunTipo;
	
	@Column(name="DS_ABVDA_COLUN_TIPO")
	private String dsAbvdaColunTipo;
	
	@Column(name="NM_NGOCO_COLUN_TIPO")
	private String nmNgocoColunTipo;
	
	@Column(name="SQ_EXIBC_PRIOR")
	private String sqExibcPrior;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INICIO_VIGEN")
	private Date dtInicioVigen;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_FIM_VIGEN")
	private Date dtFimVigen;
	
	@Column(name="CD_USURO_ULTIMA_ALTER")
	private String cdUsuroUltimaAlter;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_ULTIMA_ALTER")
	private Date dtUltimaAlter;
}