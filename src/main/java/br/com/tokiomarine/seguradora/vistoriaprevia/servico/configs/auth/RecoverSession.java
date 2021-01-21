package br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth;

import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;


public class RecoverSession implements Serializable {
		
	private static final long serialVersionUID = -3876212266191086932L;
	
	private Long id;
	private Long idUsuarioPortal;
	private String login;
	private String nome;
	private Long cpf;
	private Integer codigoInterno;
	private String siteOrigem;
	private String tipoSite;
	private Long idSistema;
	private String userPreposto;

	private Long idParceiroNegocioPrimario;
	private Integer codigoParceiroNegocioPrimario;
	private String nomeParceiroNegocioPrimario;
	private String tipoParceiroNegocioPrimario;
	private String prepostoNegocioPrimario;
	private String indicadorVendaNegocioPrimario;
	private String contaCorrenteNegocioPrimario;

	private Long idParceiroNegocioSecundario;
	private Integer codigoParceiroNegocioSecundario;
	private String nomeParceiroNegocioSecundario;
	private String tipoParceiroNegocioSecundario;
	private String prepostoNegocioSecundario;
	private String indicadorVendaNegocioSecundario;
	private String contaCorrenteNegocioSecundario;

	private Integer totalConcessionariasRelacionadas;
	private String tipoUsuarioPortal;
	private String codigoAssessoriaPortal;
	private String idUsuarioSSV;
	private String empresaUsuarioSSV;
	private String tipoLocalUsuarioSSV;
	private String localUsuarioSSV;
	private String funcaoUsuarioSSV;

	private boolean web = false;
	private static final Logger LOGGER = LogManager.getLogger(RecoverSession.class);

	public RecoverSession(final HttpServletRequest request) {
		try {
			if(request == null)
				throw new UsuarioLogadoException("HttpServletRequest NAO pode ser NULO!");

			id = this.parseLong(this.getValue(request, RecoverSessionType.ID_KEY.getValue()));
			idUsuarioPortal = this.parseLong(this.getValue(request, RecoverSessionType.IDUSUARIOPORTAL_KEY.getValue()));
			login = this.getValue(request, RecoverSessionType.LOGIN_KEY.getValue());
			nome = this.getValue(request, RecoverSessionType.NOME_KEY.getValue());
			cpf = this.parseLong(this.getValue(request, RecoverSessionType.CPF_KEY.getValue()));
			codigoInterno = this.parseInt(this.getValue(request, RecoverSessionType.CODIGOINTERNO_KEY.getValue()));
			siteOrigem = this.getValue(request, RecoverSessionType.SITEORIGEM_KEY.getValue());
			tipoSite = this.getValue(request, RecoverSessionType.TIPOSITE.getValue());
			idSistema = this.parseLong(this.getValue(request, RecoverSessionType.ID_SISTEMA.getValue()));

			idParceiroNegocioPrimario = this.parseLong(this.getValue(request, RecoverSessionType.IDPARCEIRONEGOCIOPRIMARIO_KEY.getValue()));
			codigoParceiroNegocioPrimario = this.parseInt(this.getValue(request, RecoverSessionType.CODIGOPARCEIRONEGOCIOPRIMARIO_KEY.getValue()));
			nomeParceiroNegocioPrimario = this.getValue(request, RecoverSessionType.NOMEPARCEIRONEGOCIOPRIMARIO_KEY.getValue());
			tipoParceiroNegocioPrimario = this.getValue(request, RecoverSessionType.TIPOPARCEIRONEGOCIOPRIMARIO_KEY.getValue());
			prepostoNegocioPrimario = this.getValue(request, RecoverSessionType.PREPOSTO_PARCEIRONEGOCIOPRIMARIO_KEY.getValue());
			indicadorVendaNegocioPrimario = this.getValue(request, RecoverSessionType.INDICADORVENDA_PARCEIRONEGOCIOPRIMARIO_KEY.getValue());
			contaCorrenteNegocioPrimario = this.getValue(request, RecoverSessionType.CONTACORRENTE_PARCEIRONEGOCIOPRIMARIO_KEY.getValue());

			idParceiroNegocioSecundario = this.parseLong(this.getValue(request, RecoverSessionType.IDPARCEIRONEGOCIOSECUNDARIO_KEY.getValue()));
			codigoParceiroNegocioSecundario = this.parseInt(this.getValue(request, RecoverSessionType.CODIGOPARCEIRONEGOCIOSECUNDARIO_KEY.getValue()));
			nomeParceiroNegocioSecundario = this.getValue(request, RecoverSessionType.NOMEPARCEIRONEGOCIOSECUNDARIO_KEY.getValue());
			tipoParceiroNegocioSecundario = this.getValue(request, RecoverSessionType.TIPOPARCEIRONEGOCIOSECUNDARIO_KEY.getValue());
			prepostoNegocioSecundario = this.getValue(request, RecoverSessionType.PREPOSTO_PARCEIRONEGOCIOSECUNDARIO_KEY.getValue());
			indicadorVendaNegocioSecundario = this.getValue(request, RecoverSessionType.INDICADORVENDA_PARCEIRONEGOCIOSECUNDARIO_KEY.getValue());
			contaCorrenteNegocioSecundario = this.getValue(request, RecoverSessionType.CONTACORRENTE_PARCEIRONEGOCIOSECUNDARIO_KEY.getValue());

			totalConcessionariasRelacionadas = this.parseInt(this.getValue(request, RecoverSessionType.TOTAL_CONCESSIONARIAS_RELACIONADAS.getValue()));

			//verifica se e Web (Portal corretor)
			if("true".equals(this.getValue(request, RecoverSessionType.IS_WEB.getValue())) || 
					(tipoSite != null && (RecoverSessionType.ORIGEM_CORRETOR.getValue().equalsIgnoreCase(tipoSite))) ||
					(tipoSite != null && (RecoverSessionType.ORIGEM_ASSESSORIA.getValue().equalsIgnoreCase(tipoSite)))){

				setAtributes(request);
			}

			if(tipoSite != null && RecoverSessionType.ORIGEM_CONCESSIONARIA.getValue().equalsIgnoreCase(tipoSite)){
				this.setWeb(true);
			}

			//verifica se e usuario ssv
			if(this.getValue(request, RecoverSessionType.LOGIN_KEY.getValue()) != null){
				idUsuarioSSV = this.getValue(request, RecoverSessionType.LOGIN_KEY.getValue());

				empresaUsuarioSSV = this.getValue(request, RecoverSessionType.EMPRESA_USUARIO_SSV.getValue());
				tipoLocalUsuarioSSV = this.getValue(request, RecoverSessionType.TIPO_LOCAL_USUARIO_SSV.getValue());
				localUsuarioSSV = this.getValue(request, RecoverSessionType.LOCAL_USUARIO_SSV.getValue());
				funcaoUsuarioSSV = this.getValue(request, RecoverSessionType.FUNCAO_USUARIO_SSV.getValue());
			}

		} catch (final Exception e) {
			throw new InternalServerException("Usuario nao encontrado HEADER HTTP, SESSION ou QUERY STRING! Rever configuracao da aplicacao no OpenAM.", e);
		}
	}

	private void setAtributes(final HttpServletRequest request) {
		idUsuarioPortal = this.parseLong(this.getValue(request, RecoverSessionType.CODIGO_USUARIO_PORTAL.getValue()));
		login = this.getValue(request, RecoverSessionType.LOGIN_USUARIO_PORTAL.getValue());

		if(idUsuarioPortal == null || idUsuarioPortal.equals(0L)){
			idUsuarioPortal = this.parseLong(this.getValue(request, RecoverSessionType.CODIGOPARCEIRONEGOCIOPRIMARIO_KEY.getValue()));
		}

		if(login == null || login.equals("0") || login.equals("")){
			login = this.getValue(request, RecoverSessionType.LOGIN_KEY.getValue());
		}
		

		tipoUsuarioPortal = this.getValue(request, RecoverSessionType.TIPO_USUARIO_PORTAL.getValue());
		codigoAssessoriaPortal = this.getValue(request, RecoverSessionType.CODIGO_ASSESSORIA_PORTAL.getValue());
		if(codigoInterno == null){
			codigoInterno = this.parseInt(this.getValue(request, RecoverSessionType.CODIGOINTERNO.getValue()));
		}
		id = this.parseLong(this.getValue(request, RecoverSessionType.USERID.getValue()));
		cpf = this.parseLong(this.getValue(request, RecoverSessionType.USERCPF.getValue()));
		userPreposto = this.getValue(request, RecoverSessionType.USERPREPOSTO.getValue());
		prepostoNegocioPrimario = userPreposto;
		this.setWeb(true);
	}
	
	private String getValue(final HttpServletRequest request, final String key) {
		//Verifica se a chave existe na HTTP HEADER
		if(request.getHeader(key) != null) {
			return request.getHeader(key).trim();
		//Se nao encontrar, verifica se existe na QUERY STIRNG
		} else if(request.getParameter(key) != null) {
			return request.getParameter(key).trim();
		//Se nao econtrar, verifica se existe no SESSION
		} else if(request.getSession().getAttribute(key) != null) {
			return request.getSession().getAttribute(key).toString().trim();
		//Se nao encontrar, verifica se exite no cookie
		} else {
			try {
				Cookie[] cookies = request.getCookies();
				return validaCookie(cookies, key);
			} catch (Exception e) {
				LOGGER.error("ERRO ao procurar a key " + key + " no cookie!");
				LOGGER.error(e.getMessage());
			}
		}
		return null;
	}
	
	private String validaCookie(Cookie[] cookies, final String key){

		if(cookies != null) {
			for (Cookie cookie : cookies) {

				if (cookie.getName().equals(RecoverSessionType.COOKIE_NAME.getValue())) {
					return getValores(cookie, key);
					
				} else if (cookie.getName().equals(key)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	private String getValores(Cookie cookie, String key) {
		String[] parametros = cookie.getValue().split("[|]");

		for (String param : parametros) {
			String[] valores = param.split("=");
			if (valores != null && valores.length > 1 && valores[0].equals(key)) {
				return valores[1]; 
			}
		}
		return null;
	}
	


	private Long parseLong(String value){
		if(value != null && isNumeric(value))
			return Long.parseLong(value);
		return null;
	}

	private Integer parseInt(String value){
		if(value != null && isNumeric(value))
			return Integer.parseInt(value);
		return null;
	}


	public static boolean isNumeric(String s) {
	    return s.matches("[-+]?\\d*\\.?\\d+");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUsuarioPortal() {
		return idUsuarioPortal;
	}

	public void setIdUsuarioPortal(Long idUsuarioPortal) {
		this.idUsuarioPortal = idUsuarioPortal;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Integer getCodigoInterno() {
		return codigoInterno;
	}

	public void setCodigoInterno(Integer codigoInterno) {
		this.codigoInterno = codigoInterno;
	}

	public String getSiteOrigem() {
		return siteOrigem;
	}

	public void setSiteOrigem(String siteOrigem) {
		this.siteOrigem = siteOrigem;
	}

	public Long getIdParceiroNegocioPrimario() {
		return idParceiroNegocioPrimario;
	}

	public void setIdParceiroNegocioPrimario(Long idParceiroNegocioPrimario) {
		this.idParceiroNegocioPrimario = idParceiroNegocioPrimario;
	}

	public Integer getCodigoParceiroNegocioPrimario() {
		return codigoParceiroNegocioPrimario;
	}

	public void setCodigoParceiroNegocioPrimario(
			Integer codigoParceiroNegocioPrimario) {
		this.codigoParceiroNegocioPrimario = codigoParceiroNegocioPrimario;
	}

	public String getNomeParceiroNegocioPrimario() {
		return nomeParceiroNegocioPrimario;
	}

	public void setNomeParceiroNegocioPrimario(String nomeParceiroNegocioPrimario) {
		this.nomeParceiroNegocioPrimario = nomeParceiroNegocioPrimario;
	}

	public String getTipoParceiroNegocioPrimario() {
		return tipoParceiroNegocioPrimario;
	}

	public void setTipoParceiroNegocioPrimario(String tipoParceiroNegocioPrimario) {
		this.tipoParceiroNegocioPrimario = tipoParceiroNegocioPrimario;
	}

	public String getPrepostoNegocioPrimario() {
		return prepostoNegocioPrimario;
	}

	public void setPrepostoNegocioPrimario(String prepostoNegocioPrimario) {
		this.prepostoNegocioPrimario = prepostoNegocioPrimario;
	}

	public String getIndicadorVendaNegocioPrimario() {
		return indicadorVendaNegocioPrimario;
	}

	public void setIndicadorVendaNegocioPrimario(
			String indicadorVendaNegocioPrimario) {
		this.indicadorVendaNegocioPrimario = indicadorVendaNegocioPrimario;
	}

	public String getContaCorrenteNegocioPrimario() {
		return contaCorrenteNegocioPrimario;
	}

	public void setContaCorrenteNegocioPrimario(String contaCorrenteNegocioPrimario) {
		this.contaCorrenteNegocioPrimario = contaCorrenteNegocioPrimario;
	}

	public Long getIdParceiroNegocioSecundario() {
		return idParceiroNegocioSecundario;
	}

	public void setIdParceiroNegocioSecundario(Long idParceiroNegocioSecundario) {
		this.idParceiroNegocioSecundario = idParceiroNegocioSecundario;
	}

	public Integer getCodigoParceiroNegocioSecundario() {
		return codigoParceiroNegocioSecundario;
	}

	public void setCodigoParceiroNegocioSecundario(
			Integer codigoParceiroNegocioSecundario) {
		this.codigoParceiroNegocioSecundario = codigoParceiroNegocioSecundario;
	}

	public String getNomeParceiroNegocioSecundario() {
		return nomeParceiroNegocioSecundario;
	}

	public void setNomeParceiroNegocioSecundario(
			String nomeParceiroNegocioSecundario) {
		this.nomeParceiroNegocioSecundario = nomeParceiroNegocioSecundario;
	}

	public String getTipoParceiroNegocioSecundario() {
		return tipoParceiroNegocioSecundario;
	}

	public void setTipoParceiroNegocioSecundario(
			String tipoParceiroNegocioSecundario) {
		this.tipoParceiroNegocioSecundario = tipoParceiroNegocioSecundario;
	}

	public String getPrepostoNegocioSecundario() {
		return prepostoNegocioSecundario;
	}

	public void setPrepostoNegocioSecundario(String prepostoNegocioSecundario) {
		this.prepostoNegocioSecundario = prepostoNegocioSecundario;
	}

	public String getIndicadorVendaNegocioSecundario() {
		return indicadorVendaNegocioSecundario;
	}

	public void setIndicadorVendaNegocioSecundario(
			String indicadorVendaNegocioSecundario) {
		this.indicadorVendaNegocioSecundario = indicadorVendaNegocioSecundario;
	}

	public String getContaCorrenteNegocioSecundario() {
		return contaCorrenteNegocioSecundario;
	}

	public void setContaCorrenteNegocioSecundario(
			String contaCorrenteNegocioSecundario) {
		this.contaCorrenteNegocioSecundario = contaCorrenteNegocioSecundario;
	}

	public Integer getTotalConcessionariasRelacionadas() {
		return totalConcessionariasRelacionadas;
	}

	public void setTotalConcessionariasRelacionadas(
			Integer totalConcessionariasRelacionadas) {
		this.totalConcessionariasRelacionadas = totalConcessionariasRelacionadas;
	}

	public boolean isWeb() {
		return web;
	}

	public void setWeb(boolean web) {
		this.web = web;
	}

	public String getTipoUsuarioPortal() {
		return tipoUsuarioPortal;
	}

	public void setTipoUsuarioPortal(String tipoUsuarioPortal) {
		this.tipoUsuarioPortal = tipoUsuarioPortal;
	}

	public String getCodigoAssessoriaPortal() {
		return codigoAssessoriaPortal;
	}

	public void setCodigoAssessoriaPortal(String codigoAssessoriaPortal) {
		this.codigoAssessoriaPortal = codigoAssessoriaPortal;
	}

	public String getIdUsuarioSSV() {
		return idUsuarioSSV;
	}

	public void setIdUsuarioSSV(String idUsuarioSSV) {
		this.idUsuarioSSV = idUsuarioSSV;
	}

	public String getEmpresaUsuarioSSV() {
		return empresaUsuarioSSV;
	}

	public void setEmpresaUsuarioSSV(String empresaUsuarioSSV) {
		this.empresaUsuarioSSV = empresaUsuarioSSV;
	}

	public String getTipoLocalUsuarioSSV() {
		return tipoLocalUsuarioSSV;
	}

	public void setTipoLocalUsuarioSSV(String tipoLocalUsuarioSSV) {
		this.tipoLocalUsuarioSSV = tipoLocalUsuarioSSV;
	}

	public String getLocalUsuarioSSV() {
		return localUsuarioSSV;
	}

	public void setLocalUsuarioSSV(String localUsuarioSSV) {
		this.localUsuarioSSV = localUsuarioSSV;
	}

	public String getFuncaoUsuarioSSV() {
		return funcaoUsuarioSSV;
	}

	public void setFuncaoUsuarioSSV(String funcaoUsuarioSSV) {
		this.funcaoUsuarioSSV = funcaoUsuarioSSV;
	}

	public String getCodigoSucursalSSV() {
		if (("80".equals(getTipoLocalUsuarioSSV())) && ("0001".equals(getEmpresaUsuarioSSV()))){
          return getLocalUsuarioSSV();
        }
		return null;
	}

	public String getTipoSite() {
		return tipoSite;
	}

	public void setTipoSite(String tipoSite) {
		this.tipoSite = tipoSite;
	}

	public Long getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(Long idSistema) {
		this.idSistema = idSistema;
	}

	public String getUserPreposto() {
		return userPreposto;
	}

	public void setUserPreposto(String userPreposto) {
		this.userPreposto = userPreposto;
	}

	public RecoverSession() {
		super();
	}
}
