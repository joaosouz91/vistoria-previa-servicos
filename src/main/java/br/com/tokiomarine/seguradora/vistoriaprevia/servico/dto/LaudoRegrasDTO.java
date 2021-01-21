package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.LaudoVistoria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LaudoRegrasDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private RegraAcessoDTO regras;
	private LaudoVistoria laudos;
}
