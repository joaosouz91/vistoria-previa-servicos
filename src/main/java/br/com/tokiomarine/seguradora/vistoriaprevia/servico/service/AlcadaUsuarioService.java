package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.ext.ssv.entity.ControleAlcadaDominio;
import br.com.tokiomarine.seguradora.ext.ssv.service.ControleAlcadaDominioService;
import br.com.tokiomarine.seguradora.ext.ssv.service.UsuarioAlcadaService;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.UsuarioAlcada;

@Component
public class AlcadaUsuarioService {

	@Autowired
	private UsuarioAlcadaService usuarioAlcadaService;
	
	@Autowired
	private ControleAlcadaDominioService controleAlcadaDominioService;
	
	private static final Logger LOGGER = LogManager.getLogger(AlcadaUsuarioService.class);
	
	public Boolean verificarAlcadaUsuario(final Long cdMdupr, final Long tpOperc){
		try{			
			final UsuarioAlcada usuarioAlcada = usuarioAlcadaService.buscarUsuarioAlcada(cdMdupr, new Date());	
			final ControleAlcadaDominio controleAlcadaDominio = controleAlcadaDominioService.buscarControleAlcadaDominio(cdMdupr, tpOperc);
									
			if(usuarioAlcada != null && controleAlcadaDominio != null && usuarioAlcada.getCdAlcad() >= controleAlcadaDominio.getCdAlcad()){
				return Boolean.TRUE;
			}		
		}catch (Exception e) {			
			LOGGER.info(e.getMessage());
		}
		
		return Boolean.FALSE;
	}
}
