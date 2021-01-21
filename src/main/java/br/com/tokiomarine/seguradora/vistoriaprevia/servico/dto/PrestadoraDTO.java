package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonPluralRootName;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoPessoaEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@FieldNameConstants
@JsonRootName("prestadora")
@JsonPluralRootName("prestadoras")
@JsonInclude(Include.NON_NULL)
public class PrestadoraDTO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 7114843776855599667L;

	@EqualsAndHashCode.Include
	@JsonProperty("codigoPrestadora")
	@Digits(integer = 5, fraction = 0)
	private Long cdAgrmtVspre;

	@JsonProperty("dddTelefone")
	@Size(max = 4)
	private String cdDddTelef;

	@JsonProperty("email")
	@Size(max = 100)
	private String cdEmail;

	@JsonProperty("voucher")
	@Size(min = 1, max = 1)
	private String cdLetraPrtraVouch;

	@JsonProperty("usuarioUltimaAlteracao")
	@Size(max = 8)
	private String cdUsuroUltmaAlter;

	@JsonProperty("site")
	@Size(max = 250)
	private String dsSite;

	@JsonProperty("dataCadastramento")
	private Date dtCadmt;

	@JsonProperty("dataDesativacao")
	private Date dtDesat;

	@JsonProperty("dataFimSelec")
	private Date dtFimSelec;

	@JsonProperty("dataInicioAtividade")
	private Date dtInicoAtivd;

	@JsonProperty("dataInicioSelec")
	private Date dtInicoSelec;

	@JsonProperty("dataUltimaAlteracao")
	private Date dtUltmaAlter;

	@JsonProperty("bairro")
	@Size(max = 20)
	private String nmBairr;

	@JsonProperty("cidade")
	@Size(max = 30)
	private String nmCidad;

	@JsonProperty("contato")
	@Size(max = 40)
	private String nmContt;

	@JsonProperty("logradouro")
	@Size(max = 40)
	private String nmLogra;

	@JsonProperty("razaoSocial")
	@Size(min = 1, max = 50)
	private String nmRazaoSocal;

	@JsonProperty("cep")
	@Digits(integer = 8, fraction = 0)
	private Long nrCep;

	@JsonProperty("cpfCnpj")
	@Digits(integer = 14, fraction = 0)
	private Long nrCpfCnpj;

	@JsonProperty("numeroLogradouro")
	@Digits(integer = 6, fraction = 0)
	private Long nrLogra;

	@JsonProperty("complemento")
	@Size(max = 15)
	private String dsCmploLogra;

	@JsonProperty("ramal")
	@Size(max = 5)
	private String nrRamal;

	@JsonProperty("telefoneComercial")
	@Size(max = 24)
	private String nrTelefEmpre;

	@JsonProperty("uf")
	@Size(max = 2)
	private String sgUniddFedrc;

	@JsonProperty("tipoPessoa")
	private TipoPessoaEnum tpPesoa;
	
	private Double percentualDistribuicao;
	
	@Override
	@JsonIgnore
	public List<Link> getLinks() {
		return super.getLinks();
	}
}
