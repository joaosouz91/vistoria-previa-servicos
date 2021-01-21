package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.StatusLaudoEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class ComboNovoStatus implements Serializable{
		
	private static final long serialVersionUID = 4612954485405031949L;
	
	@EqualsAndHashCode.Include
	private String codigo;
	private String nome;
	private String statusAnterior;
	private String statusAnteriorNome;
	
	public ComboNovoStatus(StatusLaudoEnum novo, StatusLaudoEnum anterior) {
		super();
		this.codigo = novo.getCodigo();
		this.nome = novo.getDescricao();
		this.statusAnterior = anterior.getCodigo();
		this.statusAnteriorNome = anterior.getDescricao();
	}
}