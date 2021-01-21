package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.util.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PreAgendamentoDTO {
	
	 private String aaFabrc;  
	 private String aaModel;  
	 private String cdAdptoEixo;  
	 private String cdChassiVeicu;  
	 private Long cdCrtorCia; 
	 private Long cdEndos;
	 private Long cdFabrt;
	 private String cdFipe;
	 private Long cdModelVeicu;
	 private Long cdNgoco;
	 private String cdPlacaVeicu;
	 private Long cdSistmOrigm;
	 private String cdVouch;
	 private String dsCmploLogra;
	 private String dsModelVeicu;
	 private String dsObser;
	 private Date dtUltmaAlter;
	 private String icCarro;
	 private String icFrota;
     private String icVeicuZeroKm;
	 private String nmBairr;
	 private String nmCidad;
	 private String nmClien;
	 private String nmFabrt; 
	 private String nmLogra;
	 private Long nrCallo;
	 private String nrCep;
	 private String nrCpfCnpjClien;
	 private Long nrItseg;
	 private String nrLogra;
     private String sgUniddFedrc;
     private String tpCarro;
     private String tpVeicu;
     private Long idVspreObgta;
     private String icMbile;
}
