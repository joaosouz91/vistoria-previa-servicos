package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.util.ArrayList;
import java.util.List;

public class ParametroGNTBuilder {
	
	private List<ParametroGNT> parametros;
		
	public ParametroGNTBuilder(){
		this.parametros = new ArrayList<>();
	}

	public ParametroGNTBuilder addOrReplaceParam(String valorParametro, String nomeParametro){
		boolean existParametro = false;
		for( ParametroGNT parametroGNT: this.parametros){
			if(parametroGNT.getNomeParametro().equals(nomeParametro)){
				parametroGNT.setValorParametro(valorParametro);
				existParametro = true;
				break;
			}
		}
		if(!existParametro)
			this.parametros.add(new ParametroGNT(valorParametro, nomeParametro));
		
		return this;
	}
	
	public List<ParametroGNT> build(){
		return parametros;
	}

}
