package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class Veiculo implements Serializable {
	
	private static final long serialVersionUID = 8710972221724760387L;

	private String cdBombInjetVeicu;
	private String cdCamboVeicu;
	private String cdCarroVeicu;
	@EqualsAndHashCode.Include
	private String cdChassiVeicu;
	private String cdDutVeicu;
	private String cdEixoDiantVeicu;
	private String cdEixoTrsroVeicu;
	private String cdLvpre;
	private String cdMotorVeicu;
	private String cdOrigmChassi;
	@EqualsAndHashCode.Include
	private String cdPlacaVeicu;
	private String cdRenavam;
	private String dsCorVeicu;
	private String dsMarcaCarroCarga;
	private String dsModelVeicu;
	private String icRodoar;
	private String icVeicuCarga;
	private String icVeicuTrafm;
	private String nmAlienVeicu;
	private String nmCidadExpdcDut;
	private String nmDutVeicu;
	private String qtLotacVeicu;
	private String sgUniddFedrcDut;
	private String tpCmbst;
	private String tpPnturVeicu;

	private Long cdFabrt;
	private Long cdFormaCarroVeicu;
	private Long cdModelVeicu;
	private Long nrAnoFabrcVeicu;
	private Long nrAnoModelVeicu;
	private Long nrCnpjDut;
	private Long nrCpfDut;
	private Long nrVrsaoLvpre;
	private Long qtCilinVeicu;
	private Long qtKmRdadoVeicu;
	private Long qtMrchaVeicu;
	private Long qtPortaVeicu;
	private Long statuDecodChasis;
	private Long tpCabinVeicu;
	private Long tpCamboVeicu;
	private Long tpCarroVeicu;
	private Long tpMtralCargaVeicu;
	private Long tpUtlzcVeicu;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private Date dtExpdcDut;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private Date dtFimSelec;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private Date dtInicoSelec;
}