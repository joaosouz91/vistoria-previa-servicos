package br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private HttpServletRequest request;

	@Value("${user.default:#{null}}")
	private String userDefault;

	public String getUsuarioId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null && StringUtils.isNotBlank(userDefault)) {
			return userDefault;
		}

		return authentication.getName();
	}

	public UsuarioLogado getUsuario() {
		return preencherDados(request);
	}

	private UsuarioLogado preencherDados(HttpServletRequest request) {
		UsuarioLogado usuarioLogado = new UsuarioLogado();

		usuarioLogado.setUsuarioId(getUsuarioId());

		RecoverSession recoverSession = new RecoverSession(request);

		usuarioLogado.setDadosPerfil(recoverSession);
		
		String idUsuarioSSV = recoverSession.getIdUsuarioSSV();
		if (idUsuarioSSV != null) {
			usuarioLogado.setUsuarioId(idUsuarioSSV);
		}

		usuarioLogado.setTipoLocalUsuarioSSV(recoverSession.getTipoLocalUsuarioSSV());
		usuarioLogado.setLocalUsuarioSSV(recoverSession.getLocalUsuarioSSV());
		usuarioLogado.setWeb(recoverSession.isWeb());
		usuarioLogado.setIdUsuarioPortal(recoverSession.getIdUsuarioPortal());

		if (usuarioLogado.getUsuarioId() == null && recoverSession.getIdUsuarioPortal() != null) {
			usuarioLogado.setUsuarioId(recoverSession.getIdUsuarioPortal().toString());
		} else {
			if (usuarioLogado.getUsuarioId() == null && recoverSession.getId() != null) {

				usuarioLogado.setUsuarioId(recoverSession.getId().toString());
			}
		}

		return usuarioLogado;
	}
}
