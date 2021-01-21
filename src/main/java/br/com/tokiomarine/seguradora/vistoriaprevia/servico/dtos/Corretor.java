package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoTelefoneDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
@JsonInclude(Include.NON_NULL)
public class Corretor implements Serializable {

	private static final long serialVersionUID = 6666411934429024915L;

	private Long idCorretor;
	private String nomeCorretor;
	private String codSucursal;
	private List<AgendamentoTelefoneDTO> telefones;
	private List<String> emails;

}