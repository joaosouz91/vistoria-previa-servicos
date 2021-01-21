package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;
import org.apache.commons.codec.binary.Base64;

public class AnexoGNT implements Serializable{
	
	private static final long serialVersionUID = -5067657647203277001L;
	private String nome;
	private String fileBase64;
	
	public AnexoGNT(String nome, byte[] file) {
		this.nome = nome;
		if(Base64.isArrayByteBase64(file)){
			this.fileBase64 = new String (file);
		}else{			
			this.fileBase64 = new String(Base64.encodeBase64(file));			
		}
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFileBase64() {
		return fileBase64;
	}
	public void setFileBase64(byte[] file) {
		if(Base64.isArrayByteBase64(file)){
			this.fileBase64 = new String (file);
		}else{			
			this.fileBase64 = new String(Base64.encodeBase64(file));
		}
	}
	
	
}