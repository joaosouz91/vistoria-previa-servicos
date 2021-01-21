package br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioLogado implements Serializable  {
		
	private static final long serialVersionUID = -2053318320881683512L;

	@EqualsAndHashCode.Include
	private String usuarioId;
	private String tipoLocalUsuarioSSV;
	private String localUsuarioSSV;	
	private boolean web;
	private Long idUsuarioPortal;
	private RecoverSession dadosPerfil;
}
