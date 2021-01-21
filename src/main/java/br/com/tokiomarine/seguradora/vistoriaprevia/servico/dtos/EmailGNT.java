package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.util.ArrayList;
import java.util.List;



public class EmailGNT {
	
	private String codigo;
	private List<DestinatarioDetalhes> destinatariosDetalhes;
	private String tipoEnvio;
	private List<ParametroGNT> parametros;
	private String rastreiaEnvio;
	private List<EmailCorretorDTO> comCopias;
	
	
	
	
	public EmailGNT() {
		super();
	}


	public EmailGNT(String codigo,String tipoEnvio, String rastreiaEnvio) {
		super();
		this.codigo = codigo;
		this.tipoEnvio = tipoEnvio;
		this.rastreiaEnvio = rastreiaEnvio;
	}
	
	public boolean existeEmailCopia(String email) {
		
		for(EmailCorretorDTO s : this.getComCopias()) {
			if(s.getDestino().equals(email)) {
				return true;
			}
		}
		return false;
	}
	
	
	public EmailGNT addOrReplaceParam(String valorParametro, String nomeParametro){
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
	
	
	
	public EmailGNT addComCopia(String copiaOculta){
		if(this.comCopias == null)
			this.comCopias = new ArrayList<>();
		this.comCopias.add(new EmailCorretorDTO(copiaOculta, "", "S"));
		return this;
	}

	
	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getTipoEnvio() {
		return tipoEnvio;
	}


	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}


	public List<ParametroGNT> getParametros() {
		return parametros;
	}


	public void setParametros(List<ParametroGNT> parametros) {
		this.parametros = parametros;
	}


	public String getRastreiaEnvio() {
		return rastreiaEnvio;
	}


	public void setRastreiaEnvio(String rastreiaEnvio) {
		this.rastreiaEnvio = rastreiaEnvio;
	}


	public List<EmailCorretorDTO> getComCopias() {
		return comCopias;
	}


	public void setComCopias(List<EmailCorretorDTO> comCopias) {
		this.comCopias = comCopias;
	}


	public List<DestinatarioDetalhes> getDestinatariosDetalhes() {
		return destinatariosDetalhes;
	}


	public void setDestinatariosDetalhes(List<DestinatarioDetalhes> destinatariosDetalhes) {
		this.destinatariosDetalhes = destinatariosDetalhes;
	}









	

}
