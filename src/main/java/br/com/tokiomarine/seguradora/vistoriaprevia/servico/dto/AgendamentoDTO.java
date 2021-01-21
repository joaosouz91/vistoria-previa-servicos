package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVistoria.D;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVistoria.M;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVistoria.P;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString
@FieldNameConstants
public class AgendamentoDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = -6835375986256382193L;

	@EqualsAndHashCode.Include
	@JsonProperty("voucher")
	@Size(max = 20)
	private String cdVouch;
	
	@JsonProperty("codigoPrestadora")
	@Digits(integer = 5, fraction = 0)
	private Long cdAgrmtVspre;
	
	@JsonProperty("codigoPosto")
	@Digits(integer = 4, fraction = 0)
	private Long cdPostoVspre;
	
	@JsonProperty("voucherAnterior")
	@Size(max = 20)
	private String cdVouchAnter;
	
	@JsonProperty("observacao")
	@Size(max = 200)
	private String dsObser;
	
	@JsonProperty("dataUltimaAlteracao")
	private Date dtUltmaAlter;
	
	@JsonProperty("tipo")
	private TipoVistoria tpVspre;
	
	@Valid
	private StatusAgendamentoDTO status;

	private StatusAgendamentoDTO statusPendente;

	private StatusAgendamentoDTO statusCancelamento;

	private String statusAnteriorCancelamento;

	private PostoDTO posto;
	
	private PrestadoraDTO prestadora;
	
	@Valid
	private AgendamentoDomicilioDTO agendamentoDomicilio;
	
	private LaudoVistoriaPreviaDTO laudo;
	
	@Valid
	private List<AgendamentoTelefoneDTO> telefones;
	
	@Size(max = 100)
	private String contato;
	
	private List<String> emails;
	
	private VistoriaObrigatoriaDTO vistoria;

	/**
	 * Utilizado para receber o id da vistoria e/ou os ids da frota para agendamento
	 */
	private List<Long> idsVistorias;
	
	/**
	 * Utilizado para agendamento Posto Santander
	 */
	private String uf;
	private String cidade;
	
	public AgendamentoDTO(Long cdAgrmtVspre, String cdVouch, String cdVouchAnter, String tpVspre, String cdSitucAgmto, Long cdMotvSitucAgmto) {
		this.cdAgrmtVspre = cdAgrmtVspre;
		this.cdVouch = cdVouch;
		this.cdVouchAnter = cdVouchAnter;
		this.tpVspre = TipoVistoria.valueOf(tpVspre);

		this.status = new StatusAgendamentoDTO(cdSitucAgmto, cdMotvSitucAgmto);
	}
	
	/**
	 * Construtor para o processo de cancelamento automático Sicredi e Mobile
	 * @param tpVspre
	 * @param cdPostoVspre
	 * @param cdSitucAgmto
	 * @param cdCrtorCia
	 * @param nmClien
	 * @param nrCpfCnpjClien
	 * @param tpVeicu
	 * @param nmFabrt
	 * @param dsModelVeicu
	 * @param cdPlacaVeicu
	 * @param cdChassiVeicu
	 * @param aaFabrc
	 * @param aaModel
	 * @param icVeicuZeroKm
	 * @param cdNgoco
	 * @param cdEndos
	 * @param dtVspre
	 * @param nmLogra
	 * @param nmBairr
	 * @param nmCidad
	 * @param sgUniddFedrc
	 * @throws ParseException 
	 */
	public AgendamentoDTO(Object[] obj) {
		
		
		String codVouch			= Objects.toString(		obj[0], null);
		String cdSitucAgmto 	= Objects.toString(		obj[1], null);
		String cdCrtorCia 		= Objects.toString(		obj[2], null);
		String nmClien 			= Objects.toString(		obj[3], null);
		String nrCpfCnpjClien 	= Objects.toString(		obj[4], null);
		String tpVeicu 			= Objects.toString(		obj[5], null);
		String nmFabrt 			= Objects.toString(		obj[6], null);
		String dsModelVeicu 	= Objects.toString(		obj[7], null);
		String cdPlacaVeicu 	= Objects.toString(		obj[8], null);
		String cdChassiVeicu 	= Objects.toString(		obj[9], null);
		String aaFabrc 			= Objects.toString(		obj[10], null);
		String aaModel 			= Objects.toString(		obj[11], null);
		String icVeicuZeroKm 	= Objects.toString(		obj[12], null);
		String cdNgoco 			= Objects.toString(		obj[13], null);
		String cdEndos 			= Objects.toString(		obj[14], null);
		String cdFipe 			= Objects.toString(		obj[15], null);
		Date dtVspre 			= new Date(((Timestamp)	obj[16]).getTime());
		String nmLogra 			= Objects.toString(		obj[17], null);
		String nrLogra 			= Objects.toString(		obj[18], null);
		String nmBairr 			= Objects.toString(		obj[19], null);
		String nmCidad 			= Objects.toString(		obj[20], null);
		String sgUniddFedrc		= Objects.toString(		obj[21], null);
		
		
		//Agendamento
		this.cdVouch = codVouch;
		
		//Status		
		StatusAgendamentoDTO statusDTO = new StatusAgendamentoDTO();
		statusDTO.setCdSitucAgmto(SituacaoAgendamento.valueOf(cdSitucAgmto));
		this.status = statusDTO;
		
		//Vistoria
		VistoriaObrigatoriaDTO vistoriaDTO = new VistoriaObrigatoriaDTO();
		vistoriaDTO.setCdCrtorCia(NumberUtils.createLong(cdCrtorCia));
		vistoriaDTO.setNmClien(nmClien);
		vistoriaDTO.setNrCpfCnpjClien(nrCpfCnpjClien);
		vistoriaDTO.setTpVeicu(TipoVeiculo.valueOf(tpVeicu));
		vistoriaDTO.setNmFabrt(nmFabrt);
		vistoriaDTO.setDsModelVeicu(dsModelVeicu);
		vistoriaDTO.setCdPlacaVeicu(cdPlacaVeicu);
		vistoriaDTO.setCdChassiVeicu(cdChassiVeicu);
		vistoriaDTO.setAaFabrc(aaFabrc);
		vistoriaDTO.setAaModel(aaModel);
		vistoriaDTO.setIcVeicuZeroKm(icVeicuZeroKm);
		vistoriaDTO.setCdNgoco(NumberUtils.createLong(cdNgoco));
		vistoriaDTO.setCdEndos(NumberUtils.createLong(cdEndos));
		vistoriaDTO.setCdFipe(cdFipe);
		this.vistoria = vistoriaDTO;
		
		//Domicilio
		AgendamentoDomicilioDTO domicilioDTO = new AgendamentoDomicilioDTO();
		domicilioDTO.setDtVspre(dtVspre);
		domicilioDTO.setNmLogra(nmLogra);
		domicilioDTO.setNrLogra(nrLogra);
		domicilioDTO.setNmBairr(nmBairr);
		domicilioDTO.setNmCidad(nmCidad);
		domicilioDTO.setSgUniddFedrc(sgUniddFedrc);
		this.agendamentoDomicilio = domicilioDTO;
	}
	
	@JsonIgnore
	public boolean isDomicilio() {
		return D == getTpVspre();
	}

	@JsonIgnore
	public boolean isTipoPosto() {
		return P == getTpVspre();
	}
	
	@JsonIgnore
	public boolean isTipoMobile() {
		return M == getTpVspre();
	}
}
