package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.Create;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonPluralRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;


@Data
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
@JsonRootName("franquia")
@JsonPluralRootName("franquia")
@JsonInclude(Include.NON_NULL)
public class FranquiaDTO  extends ResourceSupport implements Serializable{
	
	private static final long serialVersionUID = 1L;
	


	@JsonProperty("cdAgrmtVspre")
	@Digits(integer = 5, fraction = 0)
	private Long cdAgrmtVspre;
	
	@JsonProperty("dddTel")
	@Size(max = 4)
	private String cdDddTelef;
	
	@JsonProperty("email")
	@Size(max = 100) 
	private String cdEmail;

	
	@JsonProperty("codigo")
	@Digits(integer = 3, fraction = 0)
	@NotNull(groups = Create.class)
	private String cdEmpreVstra;
	
	
	@JsonProperty("ativo")
	@NotNull(groups = Create.class)
	private Long cdSitucEmpreVstra;
	
	
	@JsonProperty("usuarioUltimaAlteracao")
	@Size(max = 8)
	@NotNull(groups = Create.class)
	private String cdUsuroUltmaAlter;

	
	@JsonProperty("dsCmploLogra")
	@Size(max = 15)
	private String dsCmploLogra;
	
	
	@JsonProperty("dataFim")
	private Date dtFimSelec;
	
	
	@JsonProperty("dataInicio")
	private Date dtInicoSelec;
	
	
	@JsonProperty("dataUltimaAlter")
	private Date dtUltmaAlter;
	
	
	@JsonProperty("pagKmRodado")
	@NotNull(groups = Create.class)
	private String icPagtoKmRdado;
	
	
	@JsonProperty("bairro")
	private String nmBairr;
	
	
	@JsonProperty("cidade")
	@Size(max = 30)
	private String nmCidad;
	
	
	@JsonProperty("contato")
	@Size(max = 40)
	private String nmContt;
	
	
	@JsonProperty("nome")
	@Size(max = 40)
	@NotNull(groups = Create.class)
	private String nmEmpreVstra;
	
	
	@JsonProperty("logradouro")
	private String nmLogra;
	
	
	@JsonProperty("cep")
	//@Size(max = 8)
	private Long nrCep;
	
	
	@JsonProperty("cpfCnpj")
	private Long nrCpfCnpj;
	
	
	@JsonProperty("numLogradouro")
	private Long nrLogra;
	
	
	@JsonProperty("ramal")
	@Size(max = 5)
	private String nrRamal;
	
	
	@JsonProperty("telEmpresa")
	@Size(max = 24)
	private String nrTelefEmpre;
	
	
	@JsonProperty("qtKmFrqdo")
	@Digits(integer = 6, fraction = 0)
	@NotNull(groups = Create.class)
	private Long qtKmFrqdo;
	
	
	@JsonProperty("uf")
	@Size(max = 2)
	private String sgUniddFedrc;
	
	
	@JsonProperty("tipoPessoa")
	@Size(max = 1)
	@NotNull(groups = Create.class)
	private String tpPesoa;
	

}
