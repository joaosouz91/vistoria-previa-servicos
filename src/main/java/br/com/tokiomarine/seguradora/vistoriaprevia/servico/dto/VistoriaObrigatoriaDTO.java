package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonCEP;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonStringToBoolean;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Corretor;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVeiculo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVistoria;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString
public class VistoriaObrigatoriaDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 7592956540033502970L;

	@EqualsAndHashCode.Include
	@JsonProperty("id")
	@Digits(integer = 38, fraction = 0)
	private Long idVspreObgta;

	@JsonProperty("sistemaChamador")
	@Digits(integer = 2, fraction = 0)
	private Long cdSistmOrigm;

	@JsonProperty("negocio")
	@Digits(integer = 38, fraction = 0)
	private Long cdNgoco;

	@JsonProperty("item")
	@Digits(integer = 38, fraction = 0)
	private Long nrItseg;

	@JsonProperty("endosso")
	@Digits(integer = 38, fraction = 0)
	private Long cdEndos;

	@JsonProperty("calculo")
	@Digits(integer = 9, fraction = 0)
	private Long nrCallo;

	@JsonProperty("nomeCliente")
	@Size(max = 100)
	private String nmClien;

	@JsonProperty("cpfCnpj")
	@Size(max = 14)
	private String nrCpfCnpjClien;

	@JsonProperty("codCorretor")
	@Digits(integer = 6, fraction = 0)
	private Long cdCrtorCia;

	@JsonProperty("tipoVeiculo")
	private TipoVeiculo tpVeicu;

	@JsonProperty("nomeFabricante")
	@Size(max = 50)
	private String nmFabrt;

	@JsonProperty("nomeModelo")
	@Size(max = 50)
	private String dsModelVeicu;

	@JsonProperty("fipe")
	@Size(max = 10)
	private String cdFipe;

	@JsonProperty("placa")
	@Size(max = 7)
	private String cdPlacaVeicu;

	@JsonProperty("chassi")
	@Size(max = 20)
	private String cdChassiVeicu;

	@JsonProperty("anoFabricacao")
	@Size(max = 4)
	private String aaFabrc;

	@JsonProperty("anoModelo")
	@Size(max = 4)
	private String aaModel;

	@JsonProperty("zeroKM")
	@JsonStringToBoolean(strFalse = "N", strTrue = "S")
	@Size(max = 1)
	private String icVeicuZeroKm;

	@JsonProperty("carroceria")
	@JsonStringToBoolean(strFalse = "N", strTrue = "S")
	@Size(max = 1)
	private String icCarro;

	@JsonProperty("tipoCarroceria")
	@Size(max = 3)
	private String tpCarro;

	@JsonProperty("adaptacaoEixo")
	@Size(max = 3)
	private String cdAdptoEixo;

	@JsonProperty("frota")
	@JsonStringToBoolean(strFalse = "N", strTrue = "S")
	@Size(max = 1)
	private String icFrota;

	@JsonProperty("observacao")
	@Size(max = 200)
	private String dsObser;

	@JsonProperty("logradouro")
	@Size(max = 60)
	private String nmLogra;

	@JsonProperty("numeroLogradouro")
	@Size(max = 10)
	private String nrLogra;

	@JsonProperty("complemento")
	@Size(max = 50)
	private String dsCmploLogra;

	@JsonProperty("bairro")
	@Size(max = 40)
	private String nmBairr;

	@JsonProperty("cep")
	@JsonCEP
	@Size(max = 8)
	private String nrCep;

	@JsonProperty("cidade")
	@Size(max = 40)
	private String nmCidad;

	@JsonProperty("uf")
	@Size(max = 2)
	private String sgUniddFedrc;

	@JsonProperty("dataUltimaAlteracao")
	private Date dtUltmaAlter;

	@JsonProperty("voucher")
	@Size(max = 20)
	private String cdVouch;

	@JsonProperty("codigoFabricante")
	@Digits(integer = 9, fraction = 0)
	private Long cdFabrt;

	@JsonProperty("codigoModelo")
	@Digits(integer = 9, fraction = 0)
	private Long cdModelVeicu;

	private String icMbile;

	private AgendamentoDTO agendamento;
	
	private Corretor corretor;
	
	private String emailCliente;
	private String telefoneCliente;

	public VistoriaObrigatoriaDTO( 
			Long idVspreObgta,
			Long cdCrtorCia,
			String tpVeicu,
			 String nmClien,
			 String dsModelVeicu,
			 String cdPlacaVeicu, 
			 String cdChassiVeicu,
			 String nmFabrt,
			 String aaFabrc,
			 String aaModel, 
			 String icVeicuZeroKm
			 ) {
		this.idVspreObgta = idVspreObgta;
		this.nmClien = nmClien;
		this.cdCrtorCia = cdCrtorCia;
		
		if (tpVeicu != null) {
			this.tpVeicu = TipoVeiculo.valueOf(tpVeicu);
		}
		
		this.nmFabrt = nmFabrt;
		this.dsModelVeicu = dsModelVeicu;
		this.cdPlacaVeicu = cdPlacaVeicu;
		this.cdChassiVeicu = cdChassiVeicu;
		this.aaFabrc = aaFabrc;
		this.aaModel = aaModel;
		this.icVeicuZeroKm = icVeicuZeroKm;

	}
	
	public VistoriaObrigatoriaDTO(Long idVspreObgta, Long cdCrtorCia, String tpVeicu, String nmClien,
			String dsModelVeicu, String cdPlacaVeicu, String cdChassiVeicu, String nmFabrt, String aaFabrc, 
			String aaModel, String icVeicuZeroKm, String cdVouch, String tpVspre,
			String cdSitucAgmto, Long codigoMotivoSituacao) {

		this.idVspreObgta = idVspreObgta;
		this.cdCrtorCia = cdCrtorCia;
		this.nmClien = nmClien;
		this.dsModelVeicu = dsModelVeicu;
		this.cdPlacaVeicu = cdPlacaVeicu;
		this.cdChassiVeicu = cdChassiVeicu;
		this.nmFabrt = nmFabrt;
		this.aaFabrc = aaFabrc;
		this.aaModel = aaModel;
		this.icVeicuZeroKm = icVeicuZeroKm;
		
		if (tpVeicu != null) {
			this.tpVeicu = TipoVeiculo.valueOf(tpVeicu);
		}

		this.agendamento = new AgendamentoDTO();
		this.agendamento.setCdVouch(cdVouch);
		
		if (tpVspre != null) {
			this.agendamento.setTpVspre(TipoVistoria.valueOf(tpVspre));
		}
		
		this.agendamento.setStatus(new StatusAgendamentoDTO(cdSitucAgmto, codigoMotivoSituacao));
	}
	
	public VistoriaObrigatoriaDTO(VistoriaPreviaObrigatoria vistoria) {
		super();
		this.cdSistmOrigm = vistoria.getCdSistmOrigm();
		this.cdNgoco = vistoria.getCdNgoco();
		this.nrItseg = vistoria.getNrItseg();
		this.cdEndos = vistoria.getCdEndos();
		this.nrCallo = vistoria.getNrCallo();
		this.nmClien = vistoria.getNmClien();
		this.nrCpfCnpjClien = vistoria.getNrCpfCnpjClien();
		this.cdCrtorCia = vistoria.getCdCrtorCia();
		this.tpVeicu = TipoVeiculo.valueOf(vistoria.getTpVeicu());
		this.nmFabrt = vistoria.getNmFabrt();
		this.dsModelVeicu = vistoria.getDsModelVeicu();
		this.cdFipe = vistoria.getCdFipe();
		this.cdPlacaVeicu = vistoria.getCdPlacaVeicu();
		this.cdChassiVeicu = vistoria.getCdChassiVeicu();
		this.aaFabrc = vistoria.getAaFabrc();
		this.aaModel = vistoria.getAaModel();
		this.icVeicuZeroKm = vistoria.getIcVeicuZeroKm();
		this.icCarro = vistoria.getIcCarro();
		this.tpCarro = vistoria.getTpCarro();
		this.cdAdptoEixo = vistoria.getCdAdptoEixo();
		this.icFrota = vistoria.getIcFrota();
		this.dsObser = vistoria.getDsObser();
		this.nmLogra = vistoria.getNmLogra();
		this.nrLogra = vistoria.getNrLogra();
		this.dsCmploLogra = vistoria.getDsCmploLogra();
		this.nmBairr = vistoria.getNmBairr();
		this.nrCep = vistoria.getNrCep();
		this.nmCidad = vistoria.getNmCidad();
		this.sgUniddFedrc = vistoria.getSgUniddFedrc();
		this.dtUltmaAlter = vistoria.getDtUltmaAlter();
		this.cdVouch = vistoria.getCdVouch();
		this.cdFabrt = vistoria.getCdFabrt();
		this.cdModelVeicu = vistoria.getCdModelVeicu();
		this.icMbile = vistoria.getIcMbile();
	}
}
