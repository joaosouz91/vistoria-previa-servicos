package br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailGNT {

	private String codigo;
	private Set<DestinatarioDetalhes> destinatariosDetalhes;
	private String corretorCpfCnpj;
	private TipoEnvioGNT tipoEnvio;
	private RastreiaEnvioGNT rastreiaEnvio;
	private Set<ParametroGNT> parametros;
	
	@Override
	public String toString() {
		return "EmailGNTRequest [codigo=" + codigo + ", destinatariosDetalhes=" + destinatariosDetalhes
				+ ", corretorCpfCnpj=" + corretorCpfCnpj + ", tipoEnvio=" + tipoEnvio + ", rastreiaEnvio="
				+ rastreiaEnvio + ", parametros=" + parametros + "]";
	}
}
