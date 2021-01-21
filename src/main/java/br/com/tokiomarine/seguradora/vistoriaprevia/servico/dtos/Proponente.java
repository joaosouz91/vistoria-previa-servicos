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
public class Proponente implements Serializable {
	
	private static final long serialVersionUID = -7181824508817115604L;

	@EqualsAndHashCode.Include
	private String cdCnhCodut;
	@EqualsAndHashCode.Include
	private String cdCnhPrpnt;
	private String cdLvpre;
	private String dsAtivdProflPrpnt;
	private String dsEnder;
	private String nmBairr;
	private String nmCidad;
	private String nmCodut;
	private String nmFontc;
	private String nmPrpnt;
	private String nrTelefPrpnt;
	private String sgUniddFedrc;
	private String sgUniddFedrcCnhPrpnt;
	private String tpEstadCivilCodut;
	private String tpEstadCivilPrpnt;
	private String tpSexoPrpnt;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private Date dtFimSelec;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private Date dtInicoSelec;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private Date dtNascmCodut;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private Date dtNascmPrpnt;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private Date dtPrmraHbltcCodut;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private Date dtPrmraHbltcPrpnt;
		
	private Long nrCep;
	private Long nrCnpjPrpnt;
	private Long nrCpfCodut;
	private Long nrCpfPrpnt;	
	private Long nrVrsaoLvpre;	
	private Long tpCodutVeicu;
	
	@EqualsAndHashCode.Include
	private String cpfCnpj;
}