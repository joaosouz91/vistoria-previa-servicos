package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParecerTecnicoVistoriaPrevia;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reclassificacao implements Serializable{
	
	private static final long serialVersionUID = -3668202781011771664L;

	@EqualsAndHashCode.Include
	private String vistoria;
	private Long empresaVistoriadora;
	private Date dataTransmissao; 
	private Date dataVistoria;
	private String chassi;
	private String placa;	
	private String crlv;
	private Date expedido;
	private Long anoModelo;
	private String nomeCrlv;	
	@EqualsAndHashCode.Include
	private Long cprfCnpj;
	private String statusLaudo;
	private List<ComboNovoStatus> comboNovoStatusList;
	private List<ParecerTecnicoVistoriaPrevia> parecerTecnicoVistoriaPrevias;
	@EqualsAndHashCode.Include
	private Long versaoLaudo;
	@EqualsAndHashCode.Include
	private String tipoHistorico;
}