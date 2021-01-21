package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ConteudoColunaTipoDTO implements Serializable {

	private static final long serialVersionUID = 6238401892342543692L;

	@JsonProperty("nomeColuna")
	private String nmColunTipo;

	@JsonProperty("siglaSistema")
	private String sgSistmInfor;

	@JsonProperty("valor")
	private String vlCntdoColunTipo;

	@JsonProperty("usuarioUltimaAlteracao")
	private String cdUsuroUltmaAlter;

	@JsonProperty("descricaoAbreviada")
	private String dsAbvdaColunTipo;

	@JsonProperty("descricaoCompleta")
	private String dsCoptaColunTipo;

	@JsonProperty("descricaoResumida")
	private String dsRmidaColunTipo;

	@JsonProperty("dataFimVigencia")
	private Date dtFimVigen;

	@JsonProperty("dataInicioVigencia")
	private Date dtInicoVigen;

	@JsonProperty("dataUltimaAlteracao")
	private Date dtUltmaAlter;

	@JsonProperty("nomeNegocio")
	private String nmNgocoColunTipo;

	@JsonProperty("sequenciaExibicao")
	private Long sqExibcPrior;

	public ConteudoColunaTipoDTO(String vlCntdoColunTipo, String dsAbvdaColunTipo, String dsCoptaColunTipo) {
		this.vlCntdoColunTipo = vlCntdoColunTipo;
		this.dsAbvdaColunTipo = dsAbvdaColunTipo;
		this.dsCoptaColunTipo = dsCoptaColunTipo;
	}
}
