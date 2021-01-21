package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@Builder
public class LogradouroDTO implements Serializable {

	private static final long serialVersionUID = 1208648827692990562L;

	private String cep;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String uf;	
}
