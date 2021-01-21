package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PostoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.Create;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonLongToBoolean;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonPluralRootName;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonStringToBoolean;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.Update;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
@JsonRootName("posto")
@JsonPluralRootName("postos")
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public class PostoDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = -2724713990275292634L;

	@JsonProperty("codigoPrestadora")
	@Digits(integer = 5, fraction = 0)
	private Long cdAgrmtVspre;

	@JsonProperty("codigoPosto")
	@NotNull(groups = Create.class)
	@Digits(integer = 4, fraction = 0)
	private Long cdPostoVspre;

	@JsonProperty("nome")
	@NotBlank(groups = { Create.class, Update.class })
	@Size(min = 1, max = 40)
	private String nmPostoVspre;

	@JsonProperty("email")
	@NotBlank(groups = { Create.class, Update.class })
	@Size(min = 1, max = 100)
	private String cdEmail;

	@JsonProperty("telefone")
	@NotBlank(groups = { Create.class, Update.class })
	@Size(min = 1, max = 24)
	private String nrTelef;

	@JsonProperty("ativo")
	@JsonLongToBoolean
	@NotNull(groups = { Create.class, Update.class })
	@Digits(integer = 1, fraction = 0)
	private Long cdSitucPosto;

	@JsonProperty("funcionamento")
	@NotBlank(groups = { Create.class, Update.class })
	@Size(min = 1, max = 80)
	private String dsHorrFuncm;

	@JsonProperty("atendeVeiculoCarga")
	@JsonStringToBoolean(strFalse = "N", strTrue = "S")
	@NotBlank(groups = { Create.class, Update.class })
	@Size(min = 1, max = 1)
	private String icAtndeCmhao;

	@JsonProperty("logradouro")
	@NotBlank(groups = { Create.class, Update.class })
	@Size(min = 1, max = 55)
	private String dsEnder;

	@JsonProperty("bairro")
	@NotBlank(groups = { Create.class, Update.class })
	@Size(min = 1, max = 20)
	private String nmBairr;

	@JsonProperty("cidade")
	@NotBlank(groups = { Create.class, Update.class })
	@Size(min = 1, max = 30)
	private String nmCidad;

	@JsonProperty("uf")
	@NotBlank(groups = { Create.class, Update.class })
	@Size(min = 1, max = 2)
	private String sgUniddFedrc;

	@JsonProperty("cep")
	@NotNull(groups = { Create.class, Update.class })
	@Digits(integer = 8, fraction = 0)
	private Long nrCep;

	@JsonProperty("logradouroReferencia")
	@Size(max = 1000)
	private String dsReferEnder;

	@JsonProperty("usuarioUltimaAlteracao")
	@Size(max = 8)
	private String cdUsuroUltmaAlter;

	@JsonProperty("dataFimSelec")
	private Date dtFimSelec;

	@JsonProperty("dataInicioSelec")
	private Date dtInicoSelec;

	@JsonProperty("dataUltimaAlteracao")
	private Date dtUltmaAlter;

	@JsonProperty("idRegiaoAtendimento")
	private Long idRegiaoAtnmtVstro;
	
	private PrestadoraDTO prestadora;
	
	public PostoDTO(PostoVistoriaPrevia posto, String nmRazaoSocal) {

		this.cdPostoVspre = posto.getCdPostoVspre();
		this.cdAgrmtVspre = posto.getCdAgrmtVspre();

		this.nmPostoVspre = posto.getNmPostoVspre();
		this.dsEnder = posto.getDsEnder();
		this.nmBairr = posto.getNmBairr();
		this.nmCidad = posto.getNmCidad();
		this.sgUniddFedrc = posto.getSgUniddFedrc();
		this.dsReferEnder = posto.getDsReferEnder();
		this.cdEmail = posto.getCdEmail();
		this.nrCep = posto.getNrCep();
		this.nrTelef = posto.getNrTelef();
		this.dsHorrFuncm = posto.getDsHorrFuncm();
        this.cdSitucPosto = posto.getCdSitucPosto();
		this.prestadora = new PrestadoraDTO();
		this.prestadora.setCdAgrmtVspre(cdAgrmtVspre);
		this.prestadora.setNmRazaoSocal(nmRazaoSocal);
		this.idRegiaoAtnmtVstro = posto.getIdRegiaoAtnmtVstro();
	}
}
