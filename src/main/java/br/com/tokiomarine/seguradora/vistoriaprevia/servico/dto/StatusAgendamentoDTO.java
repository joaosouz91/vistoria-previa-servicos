package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoCancelamentoEnum.CANCELAMENTO_A_CONFIRMAR;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoCancelamentoEnum.CANCELAMENTO_CONFIRMADO;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoCancelamentoEnum.CANCELAMENTO_DE_NAG;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoCancelamentoEnum.CANCELAMENTO_FORA_SISTEMA_CONFIRMADO;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.CAN;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.NAG;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.NAP;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.VFR;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.PEN;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.RCB;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.PEF;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.LKX;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@NoArgsConstructor
@FieldNameConstants
@JsonInclude(Include.NON_NULL)
public class StatusAgendamentoDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = -2358124642583246745L;

	@JsonProperty("id")
	private Long idStatuAgmto;

	@JsonProperty("voucher")
	@Size(max = 20)
	private String cdVouch;
	
	@JsonProperty("situacao")
	private SituacaoAgendamento cdSitucAgmto;

	@JsonProperty("codigoMotivo")
	@Digits(integer = 2, fraction = 0)
	private Long cdMotvSitucAgmto;

	@JsonProperty("descricaoMotivoSituacao")
	private String dsMotvSitucAgmto;
	
	@JsonProperty("descricaoMotivo")
	@Size(max = 200)
	private String dsMotvVstriFruda;

	@JsonProperty("usuarioUltimaAlteracao")
	@Size(max = 20)
	private String cdUsuroUltmaAlter;

	@JsonProperty("dataUltimaAlteracao")
	private Date dtUltmaAlter;

	public StatusAgendamentoDTO(String cdSitucAgmto, Long cdMotvSitucAgmto) {
		if (cdSitucAgmto != null) {
			this.cdSitucAgmto = SituacaoAgendamento.valueOf(cdSitucAgmto);
		}
		
		this.cdMotvSitucAgmto = cdMotvSitucAgmto;
	}
	
	public boolean permitirCancelamento(Long codigoAlcada, Long codigoAlcadaPermitida) {

		return !(cdSitucAgmto == null || VFR == cdSitucAgmto || CAN == cdSitucAgmto || codigoAlcada == null || codigoAlcada < codigoAlcadaPermitida);
	}

	public boolean permitirCancelamentoMobile(Long codigoAlcada) {

		return cdSitucAgmto != null 
				&& codigoAlcada >= 2
				&& (PEN == cdSitucAgmto || RCB == cdSitucAgmto || PEF == cdSitucAgmto || LKX == cdSitucAgmto);
	}

	public boolean isReagendamento() {
		
		return (NAG == cdSitucAgmto || NAP == cdSitucAgmto || VFR == cdSitucAgmto
				|| (CAN == cdSitucAgmto && isMotivoCanclReagendar()));
	}

	public static boolean isMotivoCanclReagendar(Long cdMotvSitucAgmto) {
	
		return !(CANCELAMENTO_A_CONFIRMAR.getValor().equals(cdMotvSitucAgmto)
				|| CANCELAMENTO_CONFIRMADO.getValor().equals(cdMotvSitucAgmto)
				|| CANCELAMENTO_DE_NAG.getValor().equals(cdMotvSitucAgmto)
				|| CANCELAMENTO_FORA_SISTEMA_CONFIRMADO.getValor().equals(cdMotvSitucAgmto));
	}

	public boolean isMotivoCanclReagendar() {
		return isMotivoCanclReagendar(cdMotvSitucAgmto);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cdMotvSitucAgmto, cdSitucAgmto, cdUsuroUltmaAlter, cdVouch,
				dsMotvSitucAgmto, dsMotvVstriFruda, dtUltmaAlter, idStatuAgmto);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatusAgendamentoDTO other = (StatusAgendamentoDTO) obj;
		return Objects.equals(cdMotvSitucAgmto, other.cdMotvSitucAgmto) && cdSitucAgmto == other.cdSitucAgmto
				&& Objects.equals(cdUsuroUltmaAlter, other.cdUsuroUltmaAlter) && Objects.equals(cdVouch, other.cdVouch)
				&& Objects.equals(dsMotvSitucAgmto, other.dsMotvSitucAgmto)
				&& Objects.equals(dsMotvVstriFruda, other.dsMotvVstriFruda)
				&& Objects.equals(dtUltmaAlter, other.dtUltmaAlter) && Objects.equals(idStatuAgmto, other.idStatuAgmto);
	}
	
	
}
