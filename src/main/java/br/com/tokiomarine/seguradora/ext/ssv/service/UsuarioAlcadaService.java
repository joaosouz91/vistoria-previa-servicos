package br.com.tokiomarine.seguradora.ext.ssv.service;

import static br.com.tokiomarine.seguradora.ext.ssv.enumerated.SSVModuloProduto.AUTOPASSEIO;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import javax.persistence.NonUniqueResultException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.ext.ssv.repository.UsuarioAlcadaRepository;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.UsuarioAlcada;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;

@Service
public class UsuarioAlcadaService {

	private static final Logger LOGGER = LogManager.getLogger(UsuarioAlcadaService.class);
	
	@Autowired
	private UsuarioAlcadaRepository usuarioAlcadaRepository;

	@Autowired
	private UsuarioService usuarioLogado;

	public UsuarioAlcada buscarUsuarioAlcada(Long cdMdupr, Date dataConsulta) {
		return usuarioAlcadaRepository.buscarUsuarioAlcada(usuarioLogado.getUsuarioId(), cdMdupr, dataConsulta);
	}

	public Optional<Long> buscarCodigoAlcada() {
		return buscarCodigoAlcada(usuarioLogado.getUsuarioId());
	}

	public Optional<Long> buscarCodigoAlcada(String codigoUsuario) {
		try {
			return usuarioAlcadaRepository.buscarCodigoUsuarioAlcada(codigoUsuario,
					AUTOPASSEIO.getCodigoModuloProduto(), new Date());
		} catch (NonUniqueResultException e) {
			throw new NonUniqueResultException(
					"Usuario possui mais de uma alçada vigente: " + ExceptionUtils.getRootCauseMessage(e));
		} catch (Exception e) {
			throw e;
		}
	}

	public Optional<UsuarioAlcada> getUsuarioAlcadaByCdMduprCdUsuarioDataVigencia(Long cdMdupr, String cdUsuro) {
		Date dataConsulta;
		try {
			
			dataConsulta = DateUtil.parseData(DateUtil.formataData(new Date()));
			
			Date dataAguardEfetiv = DateUtil.DATA_REGISTRO_AGUARDANDO_EFETIVACAO;
			
			return usuarioAlcadaRepository.getUsuarioAlcadaByCdMduprCdUsuarioDataVigencia(cdMdupr, cdUsuro,
					dataConsulta, dataAguardEfetiv);
		
		} catch (ParseException e) {
			LOGGER.error("Erro ao tentar fazer parse de Date", e);
			throw new InternalServerException("Erro ao tentar fazer parse de Date", e);
		}
	}
}
