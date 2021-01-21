package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto.RastreiaEnvioGNT;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.comunicacao.email.gnt.dto.TipoEnvioGNT;




public class EmailGNTRequest implements  Serializable{

	private static final long serialVersionUID = -5763764941053144372L;
	
	private String codigo;
	private List<DestinatarioDetalhes> destinatariosDetalhes;
	private String tipoEnvio;
	private List<ParametroGNT> parametros;
	private String rastreiaEnvio;
	private List<AnexoGNT> anexos;
	private List<DestinatarioDetalhesComCopia> comCopias;

	public EmailGNTRequest(String codigo, List<DestinatarioDetalhes> destinatariosDetalhes, TipoEnvioGNT tipoEnvio,
			List<ParametroGNT> parametros, RastreiaEnvioGNT rastreiaEnvio, List<AnexoGNT> anexos, List<DestinatarioDetalhesComCopia> comCopias) {
		this.codigo = codigo;
		this.destinatariosDetalhes = destinatariosDetalhes;
		this.tipoEnvio = tipoEnvio.name();
		this.rastreiaEnvio = rastreiaEnvio.name();
		this.anexos = anexos;
		this.parametros = parametros;
		this.comCopias = comCopias;
	}
	
	public EmailGNTRequest(String codigo, TipoEnvioGNT tipoEnvio, RastreiaEnvioGNT rastreiaEnvio) {
		this.codigo = codigo;
		this.tipoEnvio = tipoEnvio.name();
		this.rastreiaEnvio = rastreiaEnvio.name();
	}
	
	public EmailGNTRequest addOrReplaceParam(String valorParametro, String nomeParametro){
		if(this.parametros == null)
			this.parametros = new ArrayList<>();
		boolean existParametro = false;
		for( ParametroGNT p: this.getParametros()){
			if(p.getNomeParametro().equals(nomeParametro)){
				p.setValorParametro(valorParametro);
				existParametro = true;
				break;
			}
		}
		if(!existParametro)
			this.parametros.add(new ParametroGNT(valorParametro, nomeParametro));
		return this;
	}
	
	public EmailGNTRequest addDestinatario(String destino, String cpfCnpj){
		if(this.destinatariosDetalhes == null)
			this.destinatariosDetalhes = new ArrayList<>();
		this.destinatariosDetalhes.add(new DestinatarioDetalhes(destino, cpfCnpj));
		return this;
	}
	
	public EmailGNTRequest addComCopia(String destino, String cpfCnpj, String copiaOculta){
		if(this.comCopias == null)
			this.comCopias = new ArrayList<>();
		this.comCopias.add(new DestinatarioDetalhesComCopia(destino, cpfCnpj, copiaOculta));
		return this;
	}
	
	public EmailGNTRequest addAnexo(String nome, byte[] file){
		if(this.anexos == null)
			this.anexos = new ArrayList<>();
		this.anexos.add(new AnexoGNT(nome, file));
		return this;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<DestinatarioDetalhes> getDestinatariosDetalhes() {
		return destinatariosDetalhes;
	}

	public void setDestinatariosDetalhes(List<DestinatarioDetalhes> destinatariosDetalhes) {
		this.destinatariosDetalhes = destinatariosDetalhes;
	}

	public String getTipoEnvio() {
		return tipoEnvio;
	}

	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	public String getRastreiaEnvio() {
		return rastreiaEnvio;
	}

	public void setRastreiaEnvio(String rastreiaEnvio) {
		this.rastreiaEnvio = rastreiaEnvio;
	}

	public List<AnexoGNT> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<AnexoGNT> anexos) {
		this.anexos = anexos;
	}

	public List<ParametroGNT> getParametros() {
		return parametros;
	}

	public void setParametros(List<ParametroGNT> parametros) {
		this.parametros = parametros;
	}

	public List<DestinatarioDetalhesComCopia> getComCopias() {
		return comCopias;
	}
	
	public void setComCopias(List<DestinatarioDetalhesComCopia> comCopias) {
		this.comCopias = comCopias;
	}
	
	@Override
	public String toString() {
		return "EmailGNTRequest [codigo=" + codigo + ", destinatariosDetalhes=" + destinatariosDetalhes + ", tipoEnvio="
				+ tipoEnvio + ", parametros=" + parametros + ", rastreiaEnvio=" + rastreiaEnvio + ", anexos=" + anexos
				+ ", comCopias=" + comCopias + "]";
	}

}
