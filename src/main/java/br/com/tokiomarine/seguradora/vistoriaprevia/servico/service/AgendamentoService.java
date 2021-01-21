package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.StatusAgendamentoDTO.isMotivoCanclReagendar;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoAgendamentoNaoPermitidoEnum.MOTIVO_AGENDAMENTO_JA_REALIZADO;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoAgendamentoNaoPermitidoEnum.MOTIVO_AGENDAMENTO_REAPROVEITADO;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoAgendamentoNaoPermitidoEnum.MOTIVO_CORRETOR_NAO_PARTICIPA;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoAgendamentoNaoPermitidoEnum.MOTIVO_VP_DISPENSADA;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoAgendamentoNaoPermitidoEnum.MOTIVO_VP_NAO_NECESSARIA;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.AGD;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.CAN;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.NAG;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.NAP;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.PEN;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.RGD;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento.RLZ;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVistoria.D;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVistoria.M;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVistoria.P;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.joinWith;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;

import com.google.gson.Gson;

import br.com.tokiomarine.seguradora.aceitacao.rest.client.AcselXRestClient;
import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.core.util.NumericUtil;
import br.com.tokiomarine.seguradora.core.util.StringUtil;
import br.com.tokiomarine.seguradora.ext.ssv.repository.DescricaoItemSeguradoRepository;
import br.com.tokiomarine.seguradora.ext.ssv.service.ConteudoColunaTipoService;
import br.com.tokiomarine.seguradora.ext.ssv.service.PlacaImpeditivaService;
import br.com.tokiomarine.seguradora.ext.ssv.service.UsuarioAlcadaService;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.UsuarioAlcada;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoContatoTelefone;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoDomicilio;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.RegiaoAtendimentoVistoriador;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.StatusAgendamentoAgrupamento;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoDomicilioDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoTelefoneDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AtualizacaoStatusDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ConteudoColunaTipoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.DataAgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.LogradouroDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ParamAGDAutomaticoMobileDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ParametroPercentualDistribuicaoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PostoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PrestadoraDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.RangeDataAgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ResponseAgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.StatusAgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaObrigatoriaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Corretor;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.PeriodoAgendamentoEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SistemaChamador;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SistemaOrigem;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoLogGPAEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVeiculoLocalAgendamentoEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVistoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.AgtoReaproveitadoException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BadRequestException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.ExternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NoContentException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NotFoundException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.VistoriaBindException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.regra.agendamento.PermiteAgendamentoChain;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.regra.agendamento.PermiteAgendamentoContext;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.AgendamentoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesRegrasVP;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilJava;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.UtilNegocio;

@Service
public class AgendamentoService {

	private static final Logger LOGGER = LogManager.getLogger(AgendamentoService.class);

	@Value("${tokiomarine.infra.AMBIENTE}")
	private String ambiente;
	
	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PermiteAgendamentoChain permiteAgendamentoChain;

	@Autowired
	private AgendamentoVistoriaPreviaRepository agendamentoRepository;

	@Autowired
	private AgendamentoDomicilioService agendamentoDomicilioService;

	@Autowired
	private AgendamentoContatoEmailService agendamentoContatoEmailService;

	@Autowired
	private AgendamentoContatoTelefoneService agendamentoContatoTelefoneService;

	@Autowired
	private StatusAgendamentoService statusAgendamentoService;

	@Autowired
	private PostoVistoriaPreviaService postoVistoriaPreviaService;

	@Autowired
	private PrestadoraVistoriaPreviaService prestadoraVistoriaPreviaService;

	@Autowired
	private LaudoService laudoService;

	@Autowired
	private VistoriaPreviaObrigatoriaService vistoriaPreviaObrigatoriaService;

	@Autowired
	private PlacaImpeditivaService placaImpeditivaService;

	@Autowired
	private UsuarioAlcadaService usuarioAlcadaService;

	@Autowired
	private UsuarioService usuarioLogado;

	@Autowired
	private ParametroVPService parametroVPService;

	@Autowired
	private ConteudoColunaTipoService conteudoColunaTipoService;

	@Autowired
	private ParametroPercentualDistribuicaoService parametroPercentualDistribuicaoService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private RegiaoAtendimentoVistoriadorService regiaoAtendimentoVistoriadorService;
	
	@Autowired
	private DescricaoItemSeguradoRepository descricaoItemSeguradoRepository;
	
	@Autowired
	private MapService mapService;
	
	@Autowired
	private AcselXRestClient acselXRestClient;
	
	@Autowired
	private CorretorService corretorService;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private PreAgendamentoService preAgendamentoService;
	
	private void gravaLogVP(String texto, String chavePesquisa, TipoLogGPAEnum tipoLog) {
		String message = new StringBuffer().append("AgendamentoVP [chave:").append(chavePesquisa).append("] ")
				.append(texto).toString();
		if (TipoLogGPAEnum.LOG_ERRO_GPA == tipoLog) {
			LOGGER.error(message);
		} else {
			LOGGER.info(message);
		}
	}

	public Optional<AgendamentoDTO> obterAgendamentoPorVoucher(String voucher) {
		return obterAgendamento(voucher).map(agd -> {

			AgendamentoDTO agendamento = mapper.map(agd, AgendamentoDTO.class);

			carregarDadosPorTipoVistoria(agendamento);

			agendamento.setPrestadora(prestadoraVistoriaPreviaService.obterPrestadora(agendamento.getCdAgrmtVspre()));

			carregarUltimoStatusPendente(agendamento);

			carregarStatusAtual(agendamento);

			return agendamento;
		});
	}

	private void carregarStatusAtual(final AgendamentoDTO agendamento) {
		statusAgendamentoService.obterStatusAtualPorVoucher(agendamento.getCdVouch()).ifPresent(statusAtual -> {

			agendamento.setStatus(statusAtual);

			if (CAN == statusAtual.getCdSitucAgmto()) {

				List<StatusAgendamentoDTO> listaStatus = statusAgendamentoService
						.obterStatusPorVoucher(agendamento.getCdVouch());

				listaStatus.stream().findFirst()
						.ifPresent(agendamento::setStatusCancelamento);

				listaStatus.stream().filter(status -> status.getCdSitucAgmto() != CAN).findFirst().ifPresent(
						status -> agendamento.setStatusAnteriorCancelamento(status.getCdSitucAgmto().getDescricao()));

			} else if (RLZ == statusAtual.getCdSitucAgmto()) {

				laudoService.obterUltimoLaudoDtoPorVoucher(agendamento.getCdVouch())
						.ifPresent(agendamento::setLaudo);
			}
		});
	}

	private void carregarUltimoStatusPendente(final AgendamentoDTO agendamento) {
		statusAgendamentoService.obterStatusRecentePorVoucherSituacao(agendamento.getCdVouch(), PEN)
				.ifPresent(agendamento::setStatusPendente);
	}

	/**
	 * Carregar dados da prestadora para agendamento
	 * @param agendamento
	 */
	private void carregarDadosPrestadora(final AgendamentoDTO agendamento) {
		PrestadoraDTO prestadora;
		
		if(agendamento.isTipoMobile()) { 
			Set<ParamAGDAutomaticoMobileDTO> corretoresAGDAutomaticoMobile = parametroVPService.obterCorretoresAGDAutomaticoMobile();

			Optional<ParamAGDAutomaticoMobileDTO> param = corretoresAGDAutomaticoMobile
					.stream().filter(a -> a.getCorretores().contains(agendamento.getVistoria().getCorretor().getIdCorretor())).findFirst();
			
			ParamAGDAutomaticoMobileDTO paramAGDAutomaticoMobileDTO;

			if (param.isPresent()) {
				paramAGDAutomaticoMobileDTO = param.get();
			} else {
				paramAGDAutomaticoMobileDTO = corretoresAGDAutomaticoMobile
						.stream().filter(a -> a.getCorretores().contains(0L)).findFirst()
						.orElseThrow(() -> new BusinessVPException("Prestadora default para agendamento mobile não encontrada."));
			}

			prestadora = prestadoraVistoriaPreviaService.obterPrestadora(paramAGDAutomaticoMobileDTO.getPrestadoraMobile());
		} else {
			prestadora = prestadoraVistoriaPreviaService.obterPrestadora(agendamento.getCdAgrmtVspre());
		}
		
		agendamento.setPrestadora(prestadora);
	}

	private void carregarDadosPorTipoVistoria(final AgendamentoDTO agendamento) {
		if (P == agendamento.getTpVspre()) {
			carregarDadosPosto(agendamento);
		} else if (M == agendamento.getTpVspre()) {
			carregarDadosMobile(agendamento);
		} else {
			carregarDadosDomicilio(agendamento);
		}
	}

	private void carregarDadosPosto(final AgendamentoDTO agendamento) {
		PostoDTO posto = postoVistoriaPreviaService.obterPosto(agendamento.getCdAgrmtVspre(),
				agendamento.getCdPostoVspre());
		agendamento.setPosto(posto);
	}

	private void carregarDadosDomicilio(final AgendamentoDTO agendamento) {
		String voucher = agendamento.getCdVouch();

		agendamentoDomicilioService.obterAgendamentoPorVoucher(voucher)
				.ifPresent(agendamento::setAgendamentoDomicilio);

		List<AgendamentoTelefoneDTO> telefonesSegurado = agendamentoContatoTelefoneService
				.findContatoTelefoneSegurado(voucher);
		agendamento.setTelefones(telefonesSegurado);

		telefonesSegurado.stream().findFirst().ifPresent(t -> agendamento.setContato(t.getNmConttVspre()));

		List<String> emailsSegurado = agendamentoContatoEmailService.findAgendamentoEmailSegurado(voucher);
		agendamento.setEmails(emailsSegurado);
	}
	
	private void carregarDadosMobile(final AgendamentoDTO agendamento) {
		String voucher = agendamento.getCdVouch();

		List<AgendamentoTelefoneDTO> telefonesSegurado = agendamentoContatoTelefoneService
				.findContatoTelefoneSegurado(voucher);
		agendamento.setTelefones(telefonesSegurado);
	}

	private Optional<AgendamentoVistoriaPrevia> obterAgendamento(String voucher) {
		return agendamentoRepository.findAgendamentoPorVoucher(voucher);
	}

	/**
	 * Migração do serviço WS agendamentoVP
	 * @param nrCalculo
	 * @param agendamento
	 * @return
	 * @throws BindException
	 */
	@Transactional
	public List<ResponseAgendamentoDTO> createAgendamentoWS(Long nrCalculo, Long cdSistmOrigm, AgendamentoDTO agendamento) throws BindException  {
		StringBuilder log = new StringBuilder();

		adicionarLinhaLog("METODO createAgendamento WS: INICIO ", log);
		
		adicionarLinhaLog(
				"request calculo:" + nrCalculo + " origem:" + cdSistmOrigm + " agendamento:" + gson.toJson(agendamento),
				log);
		
		adicionarLinhaLog("carregando vistoria...", log);
		VistoriaPreviaObrigatoria vp = obterPreAgendamentoPorCalculo(nrCalculo);

		return createAgendamentoWS(cdSistmOrigm, "WS_VP", agendamento, vp, true, log);
	}

	@Transactional
	public List<ResponseAgendamentoDTO> createAgendamentoWS(Long cdSistmOrigm, String ws, AgendamentoDTO agendamento, 
			VistoriaPreviaObrigatoria vp, boolean enviarEmail, StringBuilder log) throws BindException {
		List<ResponseAgendamentoDTO> retorno = new ArrayList<>();
		
		agendamento.setIdsVistorias(Arrays.asList(vp.getIdVspreObgta()));
		carregarDadosCorretor(vp.getCdCrtorCia(), agendamento);

		validarRequestAgendamentoWS(agendamento);
		validarDataVistoriaPrevia(agendamento);
		
		try {
			if (!ws.equals("WS_AC")) {
				adicionarLinhaLog("validando novo agendamento...", log);
				validarNovoAgendamento(vp, log);
			}

			if (vp != null) {
				carregarDadosPrestadora(agendamento);
	
				vp.setCdSistmOrigm(cdSistmOrigm);
				retorno.add(salvarAgendamento(vp, agendamento, ws, enviarEmail, log));
			}
		} catch (Exception e) {
			retorno.add(new ResponseAgendamentoDTO(vp.getIdVspreObgta(), vp.getCdChassiVeicu(), vp.getCdVouch(), e));
		}
		
		return retorno;
	}
	
	private void carregarDadosCorretor(Long cdCorretor, AgendamentoDTO agendamento) {
		VistoriaObrigatoriaDTO vistoriaDto = new VistoriaObrigatoriaDTO();
		
		Corretor corretor = corretorService.obterCorretor(cdCorretor)
				.orElseThrow(() -> new BusinessVPException("Código de corretor " + cdCorretor + " inválido"));
		vistoriaDto.setCorretor(corretor);
		
		agendamento.setVistoria(vistoriaDto);
	}

	/**
	 * Utilizado pela tela para criar o agendamento
	 * @param agendamento
	 * @return
	 * @throws BindException
	 */
	@Transactional
	public List<ResponseAgendamentoDTO> createAgendamentoTela(AgendamentoDTO agendamento) throws BindException  {

		validarRequestAgendamentoTela(agendamento);
		validarDataVistoriaPrevia(agendamento);

		List<ResponseAgendamentoDTO> retorno = new ArrayList<>();
		StringBuilder log = new StringBuilder();

		adicionarLinhaLog("METODO createAgendamento: INICIO ", log);

		List<VistoriaPreviaObrigatoria> vistoriasValidas = carregarVistoriasValidas(agendamento.getIdsVistorias(), retorno,
				log);

		if (!vistoriasValidas.isEmpty()) {
			carregarDadosPrestadora(agendamento);

			vistoriasValidas.forEach(vistoria -> {
				retorno.add(salvarAgendamento(vistoria, agendamento, log));
			});
		}

		return retorno;
	}

	private ResponseAgendamentoDTO salvarAgendamento(VistoriaPreviaObrigatoria vistoria, AgendamentoDTO agendamento, StringBuilder log) {
		return salvarAgendamento(vistoria, agendamento, usuarioLogado.getUsuarioId(), true, log);
	}
	
	private ResponseAgendamentoDTO salvarAgendamento(VistoriaPreviaObrigatoria vistoria, AgendamentoDTO agendamento, String usuario, boolean enviarEmail, StringBuilder log) {
		ResponseAgendamentoDTO response;
		StringBuilder logGPA = new StringBuilder(log);
		TipoLogGPAEnum tipoLog = TipoLogGPAEnum.LOG_INFO_GPA;
		Long idVistoria = vistoria.getIdVspreObgta();
		String voucher = "";
		try {
			voucher = gerarNrVoucher(idVistoria, vistoria.getCdSistmOrigm(), 
					agendamento.getPrestadora());
			adicionarLinhaLog(" * numero voucher gerado - codVoucher: " + voucher, logGPA);
			VistoriaObrigatoriaDTO vistoriaDto = new VistoriaObrigatoriaDTO(vistoria);
			vistoriaDto.setCorretor(agendamento.getVistoria().getCorretor());
			vistoriaDto.setEmailCliente(agendamento.getVistoria().getEmailCliente());
			agendamento.setVistoria(vistoriaDto);
			
			// *Grava na VPE0425_AGEND_VSPRE, VPE0427_AGEND_CONTT_TELEF,
			// VPE0428_AGEND_CONTT_EMAIL
			gravarAgVistoriaPrevia(voucher, vistoria.getCdVouch(), agendamento, usuario);

			adicionarLinhaLog(" * dados veiculo utilizados - " + " Corretor: "
					+ agendamento.getVistoria().getCorretor().getIdCorretor() + " / Chassi: "
					+ vistoria.getCdChassiVeicu() + " / Placa: " + vistoria.getCdPlacaVeicu(), logGPA);

			gravarDadosEmail(voucher, agendamento);
			gravarDadosTelefone(voucher, agendamento);

			gravarAgtoDomicilio(voucher, agendamento, logGPA);

			gravarPreAgendamento(voucher, vistoria, agendamento);
			
			if (enviarEmail) {
				emailService.enviarEmailConfirmacao(agendamento);
			}
			
			adicionarLinhaLog("METODO salvarAgendamento: FIM", logGPA);

			response = new ResponseAgendamentoDTO(idVistoria, vistoria.getCdChassiVeicu(), voucher);

		} catch (Exception e) {
			LOGGER.error("[salvarAgendamento] Erro ao tentar criar o agendamento id: " + idVistoria
					+ " request: " + new Gson().toJson(agendamento), e);

			response = ResponseAgendamentoDTO.builder().idVistoria(idVistoria).chassi(vistoria.getCdChassiVeicu())
							.error("Ocorreu um erro ao tentar criar o agendamento.").build();

		} finally {
			gravaLogVP(logGPA.toString(), idVistoria + "." + voucher, tipoLog);
		}
		
		return response;
	}

	/**
	 * Atualiza a VPE0424 com o voucher e o endereço, caso seja agendamento domicílio.
	 * Se existir um agendamento anterior, cria um novo pre agendamento na VPE0424 com o voucher e o endereço, caso seja agendamento domicílio
	 * @param vistoria
	 * @param voucher
	 * @param agendamento
	 */
	private void gravarPreAgendamento(String voucher, VistoriaPreviaObrigatoria vistoria,
			AgendamentoDTO agendamento) {
		
		if (StringUtils.isNotBlank(vistoria.getCdVouch())) {
			vistoriaPreviaObrigatoriaService.detach(vistoria);
		}
		
		if (vistoria.getCdSistmOrigm() == null) {
			throw new BusinessVPException(format("Código do sistema de origem não encontrado para Vistoria Previa: {0}",
					vistoria.getIdVspreObgta()));
		}

		if (agendamento.isDomicilio()) {
			AgendamentoDomicilioDTO domicilio = agendamento.getAgendamentoDomicilio();
			vistoria.setNrCep(domicilio.getNrCep());
			vistoria.setNmLogra(domicilio.getNmLogra());
			vistoria.setNrLogra(domicilio.getNrLogra());
			vistoria.setDsCmploLogra(domicilio.getDsCmploLogra());
			vistoria.setNmBairr(domicilio.getNmBairr());
			vistoria.setNmCidad(domicilio.getNmCidad());
			vistoria.setSgUniddFedrc(domicilio.getSgUniddFedrc());
		}
		
		vistoria.setCdVouch(voucher);
		vistoria.setDtUltmaAlter(new Date());

		vistoriaPreviaObrigatoriaService.salvar(vistoria);
	}

	private List<VistoriaPreviaObrigatoria> carregarVistoriasValidas(List<Long> idsVistorias,
			List<ResponseAgendamentoDTO> retorno, StringBuilder log) {
		LOGGER.info("[carregarVistoriasValidas] carregando vistorias válidas");
		return idsVistorias.stream().map(id -> 
					vistoriaPreviaObrigatoriaService.findById(id).orElseThrow(() -> new BusinessVPException(
							format("Pré-Agendamento não econtrado para o código identificador: {0}.", id)))
				).filter(v -> {
					try {
						LOGGER.info("[carregarVistoriasValidas] validando novo agendamento");
						validarNovoAgendamento(v, log);
						return true;
					} catch (Exception e) {
						retorno.add(new ResponseAgendamentoDTO(v.getIdVspreObgta(), v.getCdChassiVeicu(),
								v.getCdVouch(), e));
						return false;
					}
				}).collect(Collectors.toList());
	}
	
	private void validarRequestAgendamentoWS(AgendamentoDTO agendamento) throws BindException {
		VistoriaBindException bindException = new VistoriaBindException(agendamento, "agendamento");

		if (CollectionUtils.isEmpty(agendamento.getIdsVistorias())) {
			bindException.required(AgendamentoDTO.Fields.idsVistorias);
		}

		String fieldTelefoneCorretor = joinWith(".", AgendamentoDTO.Fields.vistoria,
				VistoriaObrigatoriaDTO.Fields.corretor, Corretor.Fields.telefones);

		if (agendamento.getVistoria() == null || agendamento.getVistoria().getCorretor() == null
				|| CollectionUtils.isEmpty(agendamento.getVistoria().getCorretor().getTelefones())) {

			bindException.required(fieldTelefoneCorretor);

		} else if (agendamento.getVistoria().getCorretor().getTelefones().stream()
				.anyMatch(t -> StringUtils.isAnyBlank(t.getCdDddTelef(), t.getNrTelef()))) {
			bindException.rejectValue(fieldTelefoneCorretor, null, "Informe 'DDD/Telefone' de contato do corretor.");
		}

		if (agendamento.getTpVspre() == null) {
			bindException.required(AgendamentoDTO.Fields.tpVspre);

		} else if (agendamento.getTpVspre() == TipoVistoria.D) {

			validarAgdDomicilio(agendamento, bindException);

		} else if (agendamento.getTpVspre() == TipoVistoria.P) {
			//Validar prestadora e posto;
			boolean contemPosto = postoVistoriaPreviaService.contemPosto(agendamento.getCdAgrmtVspre(), agendamento.getCdPostoVspre());
			
			if (!contemPosto) {
				bindException.rejectValue(AgendamentoDTO.Fields.cdPostoVspre, null,
						"Posto não localizado - Prestadora: " + agendamento.getCdAgrmtVspre() + " Posto:"
								+ agendamento.getCdPostoVspre());
			}
			
		} else if (agendamento.isTipoMobile()) {
			if (CollectionUtils.isEmpty(agendamento.getTelefones()) 
					|| agendamento.getTelefones().stream()
					.anyMatch(t -> 
						StringUtils.isBlank(t.getCdDddTelef()) 
						|| StringUtils.isBlank(t.getNrTelef()) 
						|| StringUtils.isBlank(t.getNmConttVspre())	
					)) {
				
				bindException.rejectValue(AgendamentoDTO.Fields.telefones, null,
						"Informe os campos 'DDD/Telefone/Contato'.");
			}
		}

		if (bindException.hasErrors()) {
			throw bindException;
		}
	}
	
	@Deprecated //substituir por validarRequestAgendamentoWS quando possível
	private void validarRequestAgendamentoTela(AgendamentoDTO agendamento) throws BindException {
		VistoriaBindException bindException = new VistoriaBindException(agendamento, "agendamento");

		if (CollectionUtils.isEmpty(agendamento.getIdsVistorias())) {
			bindException.required(AgendamentoDTO.Fields.idsVistorias);
		}

		String fieldTelefoneCorretor = joinWith(".", AgendamentoDTO.Fields.vistoria,
				VistoriaObrigatoriaDTO.Fields.corretor, Corretor.Fields.telefones);

		if (agendamento.getVistoria() == null || agendamento.getVistoria().getCorretor() == null
				|| CollectionUtils.isEmpty(agendamento.getVistoria().getCorretor().getTelefones())) {

			bindException.required(fieldTelefoneCorretor);

		} else if (agendamento.getVistoria().getCorretor().getTelefones().stream()
				.anyMatch(t -> StringUtils.isAnyBlank(t.getCdDddTelef(), t.getNrTelef()))) {
			bindException.rejectValue(fieldTelefoneCorretor, null, "Informe 'DDD/Telefone' de contato do corretor.");
		}

		if (agendamento.getTpVspre() == null) {
			bindException.required(AgendamentoDTO.Fields.tpVspre);

		} else if (agendamento.getTpVspre() == TipoVistoria.D) {

			validarAgdDomicilio(agendamento, bindException);

		}

		if (bindException.hasErrors()) {
			throw bindException;
		}
	}

	private void validarAgdDomicilio(AgendamentoDTO agendamento, VistoriaBindException bindException) {
		AgendamentoDomicilioDTO domicilio = agendamento.getAgendamentoDomicilio();

		if (domicilio == null) {
			bindException.required(AgendamentoDTO.Fields.agendamentoDomicilio);

		} else {

			validarEndereco(bindException, domicilio);

			if (domicilio.getDtVspre() == null) {
				bindException.required(AgendamentoDTO.Fields.agendamentoDomicilio+"."+AgendamentoDomicilioDTO.Fields.dtVspre);
			}

			if (domicilio.getIcPerioVspre() == null) {
				bindException.required(AgendamentoDTO.Fields.agendamentoDomicilio+"."+AgendamentoDomicilioDTO.Fields.icPerioVspre);
			}
		}

		if (isBlank(agendamento.getContato())) {
			bindException.required(AgendamentoDTO.Fields.contato);
		}

		if (CollectionUtils.isEmpty(agendamento.getTelefones())) {
			bindException.required(AgendamentoDTO.Fields.telefones);
		} else if (agendamento.getTelefones().stream()
				.anyMatch(t -> !isNumeric(t.getCdDddTelef()) || !isNumeric(t.getNrTelef()))) {
			bindException.rejectValue(AgendamentoDTO.Fields.telefones, null, "Informe 'DDD/Telefone' de contato.");
		}
	}

	private void validarEndereco(VistoriaBindException bindException, AgendamentoDomicilioDTO domicilio) {
		if (!isNumeric(domicilio.getNrCep())) {
			bindException.required(AgendamentoDTO.Fields.agendamentoDomicilio+"."+AgendamentoDomicilioDTO.Fields.nrCep);
		}

		if (isBlank(domicilio.getNmLogra())) {
			bindException.required(AgendamentoDTO.Fields.agendamentoDomicilio+"."+AgendamentoDomicilioDTO.Fields.nmLogra);
		}

		if (isBlank(domicilio.getNrLogra())) {
			bindException.required(AgendamentoDTO.Fields.agendamentoDomicilio+"."+AgendamentoDomicilioDTO.Fields.nrLogra);
		}

		if (isBlank(domicilio.getNmBairr())) {
			bindException.required(AgendamentoDTO.Fields.agendamentoDomicilio+"."+AgendamentoDomicilioDTO.Fields.nmBairr);
		}

		if (isBlank(domicilio.getNmCidad())) {
			bindException.required(AgendamentoDTO.Fields.agendamentoDomicilio+"."+AgendamentoDomicilioDTO.Fields.nmCidad);
		}

		if (isBlank(domicilio.getSgUniddFedrc())) {
			bindException.required(AgendamentoDTO.Fields.agendamentoDomicilio+"."+AgendamentoDomicilioDTO.Fields.sgUniddFedrc);
		}
	}

	private void validarNovoAgendamento(VistoriaPreviaObrigatoria vistoria, StringBuilder logGPA) {
		// * Regras de Validação para Novo Agendamento. Evita re-gravar com F5.
		try {

			this.validarNovoAgendamento(vistoria);
			adicionarLinhaLog(" * chamada validarNovoAgendamento  - OK", logGPA);

		} catch (AgtoReaproveitadoException e) {

			adicionarLinhaLog(" * chamada validarNovoAgendamento  - NOT OK(AgtoReaproveitadoException) <br>\n "
					+ ExceptionUtils.getRootCauseMessage(e), logGPA);

			gravaLogVP(logGPA.toString(), vistoria.getIdVspreObgta().toString(), TipoLogGPAEnum.LOG_INFO_GPA);

			throw e;

		} catch (BusinessVPException e) {

			adicionarLinhaLog(" * chamada validarNovoAgendamento  - NOT OK(VistoriaPreviaException) <br>\n "
					+ ExceptionUtils.getRootCauseMessage(e), logGPA);

			gravaLogVP(logGPA.toString(), vistoria.getIdVspreObgta().toString(), TipoLogGPAEnum.LOG_INFO_GPA);

			throw e;
		}
	}

	public void validarNovoAgendamento(Long idVspreObgta) {
		VistoriaPreviaObrigatoria vistoriaPreviaObrigatoria = vistoriaPreviaObrigatoriaService.findById(idVspreObgta)
				.orElseThrow(NotFoundException::new);
		validarNovoAgendamento(vistoriaPreviaObrigatoria);
	}

	/**
	 * Regras de Validação para Novo Agendamento.
	 *
	 * @param itemAgendamento
	 *
	 * @throws VistoriaPreviaException
	 */
	public void validarNovoAgendamento(VistoriaPreviaObrigatoria vPObrigatoria) {
		PermiteAgendamentoContext permiteAgto;
		try {
			permiteAgto = permiteAgendamento(vPObrigatoria.getIdVspreObgta());
			if (!permiteAgto.getPermiteAgendamento()) {

				if (MOTIVO_CORRETOR_NAO_PARTICIPA == permiteAgto.getMotivoAgendamentoNaoPermitido()) {
					throw new BusinessVPException(messageUtil.get("erro.agendamento.corretor.nao.participa"));
				}
				if (MOTIVO_VP_NAO_NECESSARIA == permiteAgto.getMotivoAgendamentoNaoPermitido()) {
					throw new BusinessVPException(messageUtil.get("erro.agendamento.vistoria.nao.necessaria",
							vPObrigatoria.getCdChassiVeicu()));
				}
				if (MOTIVO_AGENDAMENTO_JA_REALIZADO == permiteAgto.getMotivoAgendamentoNaoPermitido()) {
					throw new BusinessVPException(messageUtil.get("erro.agendamento.novo.agendamento.nao.permitido",
							vPObrigatoria.getCdChassiVeicu(), permiteAgto.getVoucher()));
				}
				if (MOTIVO_AGENDAMENTO_REAPROVEITADO == permiteAgto.getMotivoAgendamentoNaoPermitido()) {
					throw new AgtoReaproveitadoException(messageUtil.get("alerta.agendamento.reaproveitado",
							vPObrigatoria.getCdChassiVeicu(), permiteAgto.getVoucher()));
				}
				if (MOTIVO_VP_DISPENSADA == permiteAgto.getMotivoAgendamentoNaoPermitido()) {
					throw new AgtoReaproveitadoException(messageUtil.get("alerta.agendamento.vistoria.dispensada",
							vPObrigatoria.getCdChassiVeicu()));
				}
			}
		} catch (BusinessVPException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerException(e);
		}
	}

	private PermiteAgendamentoContext permiteAgendamento(Long idVspreObgta) throws Exception {

		StringBuilder logPermiteAgendamento = new StringBuilder();

		adicionarLinhaLog("Obtém pré-agendamento...", logPermiteAgendamento);
		
		VistoriaPreviaObrigatoria vp = vistoriaPreviaObrigatoriaService.findById(idVspreObgta)
			.orElseThrow(() -> new InternalServerException("Pré-agendamento não localizado pelo idVspreObgta: "+idVspreObgta));
		
		PermiteAgendamentoContext permite = new PermiteAgendamentoContext(vp);
		
		LOGGER.info("[permiteAgendamentoChain] inicio idVspreObgta:" + idVspreObgta);
		
		permiteAgendamentoChain.execute(permite);

		LOGGER.info("[permiteAgendamentoChain] fim idVspreObgta:" + idVspreObgta);
		
		gravaLogDebug(permite.getLog(), vp);

		return permite;
	}

	/**
	 * Verificação de datas e períodos disponíveis para vistoria domiciliar
	 */
	private void validarDataVistoriaPrevia(AgendamentoDTO agendamento) {
		if (agendamento.getTpVspre() == TipoVistoria.D) {
			try {

				AgendamentoDomicilioDTO agendamentoDomicilio = agendamento.getAgendamentoDomicilio();

				PeriodoAgendamentoEnum periodoVspre = agendamentoDomicilio.getIcPerioVspre();
				Date dtVspre = agendamentoDomicilio.getDtVspre();

				LocalDate dataVistoria = br.com.tokiomarine.seguradora.aceitacao.rest.client.util.DateUtil
						.toLocalDate(dtVspre);

				RangeDataAgendamentoDTO dataDisponivel = obterDatasAgendamento(agendamentoDomicilio.getNmCidad());

				if (dataVistoria.isBefore(dataDisponivel.getMinDate())
						|| dataVistoria.isAfter(dataDisponivel.getMaxDate())) {
					
					if (dataVistoria.isBefore(dataDisponivel.getMinDate()) && dataVistoria.getDayOfWeek() == DayOfWeek.SATURDAY ) {
						throw new BusinessVPException(
								messageUtil.get("erro.agendamento.nao.ha.tempo.habil", DateUtil.formataData(dtVspre)));
					}
					
					throw new BusinessVPException(messageUtil.get("erro.agendamento.data.vistoria.intervalo"));
				}

				if (!dataDisponivel.getPeriodoVistoria().equals(PeriodoAgendamentoEnum.C.getValor())
						&& !dataDisponivel.getPeriodoVistoria().equals(periodoVspre.getValor()) && dataDisponivel.getMinDate().isEqual(dataVistoria)) {
					throw new BusinessVPException(
							messageUtil.get("erro.agendamento.periodo.não.permitido", DateUtil.formataData(dtVspre)));
				}

				if ((dataVistoria.getDayOfWeek() != DayOfWeek.SATURDAY && acselXRestClient.isFeriado(dtVspre)) 
						|| (dataVistoria.getDayOfWeek() == DayOfWeek.SATURDAY && periodoVspre != PeriodoAgendamentoEnum.M) 
						|| dataVistoria.getDayOfWeek() == DayOfWeek.SUNDAY) {
					throw new BusinessVPException(messageUtil.get("erro.agendamento.data.vistoria.nao.dia.util"));
				}

			} catch (BusinessVPException e) {
				throw e;
			} catch (Exception e) {
				throw new InternalServerException(messageUtil.get("erro.agendamento.data.vistoria"), e);
			}
		}
	}
	
	private void gravaLogDebug(StringBuilder log, VistoriaPreviaObrigatoria vp) {
		String chave = NumericUtil.isZeroOrNull(vp.getIdVspreObgta()) ? "" : String.valueOf(vp.getIdVspreObgta());
		String cdSistmOrigm = " [cdSistmOrigm]:" + vp.getCdSistmOrigm() + " ";
		LOGGER.info("[idVspreObgta]:" + chave + cdSistmOrigm + log.toString());
	}

	/**
	 * Realiza o append da String no StringBuffer
	 * 
	 * @param linhaLog
	 * @param log
	 */
	private void adicionarLinhaLog(String linhaLog, StringBuilder log) {
		log.append(linhaLog + "\r\n");
	}

	/**
	 * Grava o voucher do sem proposta no agto pai que estava com status A Agendar
	 * Quando realizar consulta por Numero Item, Codigo Negocio ou Numero Calculo o
	 * agendamento não irá aparecer como A Agendar Praticamente realiza mesma função
	 * do permite agendamento (uma vez que ao clicar sobre esse agendamento o
	 * permite agendamento iria gravar o voucher nesse agto)
	 * 
	 * @param logGPA
	 */
	@Transactional
	public void gravarVoucherSemPropostaAgtoPai(VistoriaPreviaObrigatoria vp, StringBuilder logGPA) {

		adicionarLinhaLog(" METODO gravarVoucherSemPropostaAgtoPai: INICIO ", logGPA);

		VistoriaFiltro filtro = new VistoriaFiltro();
		filtro.setCorretor(vp.getCdCrtorCia());
		filtro.setChassi(vp.getCdChassiVeicu());
		filtro.setPlaca(vp.getCdPlacaVeicu());
		filtro.setDataAte(null);

		// * Placa+Chassi+Corretor
		List<VistoriaObrigatoriaDTO> listaItemAgendamento = vistoriaPreviaObrigatoriaService
				.recuperarListaItemAgendar(filtro);

		adicionarLinhaLog(" * buscando por Placa+Chassi+Corretor", logGPA);

		if (CollectionUtils.isEmpty(listaItemAgendamento)) {

			adicionarLinhaLog(" * buscando por Chassi+Corretor", logGPA);

			// * Chassi+Corretor
			filtro.setChassi(vp.getCdChassiVeicu());
			filtro.setPlaca(null);
			listaItemAgendamento = vistoriaPreviaObrigatoriaService.recuperarListaItemAgendar(filtro);

			// * Placa+Corretor
			if (CollectionUtils.isEmpty(listaItemAgendamento)
					&& !placaImpeditivaService.isPlacaImpeditiva(vp.getCdPlacaVeicu())) {

				adicionarLinhaLog(" * buscando por Placa+Corretor", logGPA);

				filtro.setChassi(null);
				filtro.setPlaca(vp.getCdPlacaVeicu());
				listaItemAgendamento = vistoriaPreviaObrigatoriaService.recuperarListaItemAgendar(filtro);
			}
		}

		if (CollectionUtils.isNotEmpty(listaItemAgendamento)) {

			Long idVpPai = listaItemAgendamento.get(0).getIdVspreObgta();

			adicionarLinhaLog(" * VP pai encontrada - IdVspreObgta = " + idVpPai, logGPA);

			// * Apenas garantindo que não é o mesmo registro
			if (!idVpPai.equals(vp.getIdVspreObgta())) {
				vistoriaPreviaObrigatoriaService.updateVoucherPorId(idVpPai, vp.getCdVouch());

				adicionarLinhaLog(" * VP pai alterada", logGPA);
			}
		} else {
			adicionarLinhaLog(" * VP pai NAO encontrada", logGPA);
		}

		adicionarLinhaLog(" METODO gravarVoucherSemPropostaAgtoPai: FIM", logGPA);
	}

	private void gravarAgtoDomicilio(String cdVouch, AgendamentoDTO agendamento, StringBuilder logGPA) {
		if (agendamento.isDomicilio()) {
			AgendamentoDomicilio agtoDomicilio = mapper.map(agendamento.getAgendamentoDomicilio(),
					AgendamentoDomicilio.class);

			if (isBlank(agtoDomicilio.getNrLogra())) {
				agtoDomicilio.setNrLogra("S/N");
			}

			agtoDomicilio.setCdVouch(cdVouch);

			agendamentoDomicilioService.salvar(agtoDomicilio);
			
			adicionarLinhaLog(" * dados gravados na  VPE0426_AGEND_DOMCL - DATA_VISTORIA: "
					+ agendamento.getAgendamentoDomicilio().getDtVspre() + "/ PERIODO_VISTORIA: "
					+ agendamento.getAgendamentoDomicilio().getIcPerioVspre(), logGPA);
		}
	}

	@Transactional
	public void gravarDadosTelefone(String cdVouch, AgendamentoDTO agendamento) {
		Predicate<? super AgendamentoTelefoneDTO> isTelefoneValido = t -> t.isTelefoneAtendimento()
				|| (isNotBlank(t.getCdDddTelef()) && isNotBlank(t.getNrTelef()));

		Function<AgendamentoTelefoneDTO, AgendamentoContatoTelefone> toEntityAddValues = t -> {
			if (t.isTelefoneAtendimento()) {
				t.setCdDddTelef("00");
			}
			t.setCdVouch(cdVouch);
			return mapper.map(t, AgendamentoContatoTelefone.class);
		};

		// Salva telefones do corretor
		agendamento.getVistoria().getCorretor().getTelefones().stream().filter(isTelefoneValido).map(toEntityAddValues)
				.peek(t -> t.setNmConttVspre(agendamento.getVistoria().getCorretor().getNomeCorretor())).forEach(t -> 
					agendamentoContatoTelefoneService.salvarTelefoneCorretor(t)
				);

		// Salva telefones do segurado caso seja um agendamento tipo domicílio
		if (agendamento.getTpVspre() == D) {
			agendamento.getTelefones().stream().filter(isTelefoneValido).map(toEntityAddValues)
					.peek(t -> t.setNmConttVspre(agendamento.getContato())).forEach(t -> 
						agendamentoContatoTelefoneService.salvarTelefoneSegurado(t)
					);
		}
		
		// Salva telefones do segurado caso seja um agendamento tipo mobile
		if (agendamento.getTpVspre() == M) {
			agendamento.getTelefones().stream().filter(isTelefoneValido).map(toEntityAddValues)
			.forEach(t -> agendamentoContatoTelefoneService.salvarTelefoneSegurado(t));
		}

	}

	/**
	 * Grava lista de emails na tabela VPE0428_AGEND_CONTT_EMAIL
	 * 
	 * @param codVoucher
	 */
	@Transactional
	public void gravarDadosEmail(String codVoucher, AgendamentoDTO agendamento) {
		List<String> emailsCorretor = agendamento.getVistoria().getCorretor().getEmails();
		if (emailsCorretor != null) {
			emailsCorretor.stream().filter(StringUtils::isNotBlank).forEach(e -> 
				agendamentoContatoEmailService.salvarEmailCorretor(e, codVoucher)
			);
		}

		if (agendamento.getTpVspre() == D && CollectionUtils.isNotEmpty(agendamento.getEmails())) {
			agendamento.getEmails().stream().filter(StringUtils::isNotBlank).forEach(e -> 
				agendamentoContatoEmailService.salvarEmailSegurado(e, codVoucher)
			);
		}

		Optional.ofNullable(agendamento.getVistoria().getEmailCliente()).filter(StringUtils::isNotBlank)
		 .ifPresent(e -> 
			agendamentoContatoEmailService.salvarEmailCliente(e, codVoucher)
		);
	}

	/**
	 * Grava os dados da tabela VPE0425_AGEND_VSPRE e VPE0437_STATU_AGMTO
	 * 
	 * @param cdVouch
	 * @param cdVouchAnter
	 * @param usuario 
	 */
	@Transactional
	public void gravarAgVistoriaPrevia(String cdVouch, String cdVouchAnter, AgendamentoDTO dadosVistoria, String usuario) {
		PrestadoraDTO prestadora = dadosVistoria.getPrestadora();

		AgendamentoVistoriaPrevia agVistoriaPrevia = new AgendamentoVistoriaPrevia();

		agVistoriaPrevia.setCdVouch(cdVouch);
		agVistoriaPrevia.setCdVouchAnter(cdVouchAnter);
		agVistoriaPrevia.setTpVspre(dadosVistoria.getTpVspre().getCodigo());
		agVistoriaPrevia.setDtUltmaAlter(new Date());
		agVistoriaPrevia.setCdAgrmtVspre(prestadora.getCdAgrmtVspre());
		agVistoriaPrevia.setDsObser(dadosVistoria.getDsObser());

		if (P == dadosVistoria.getTpVspre()) {
			agVistoriaPrevia.setCdPostoVspre(dadosVistoria.getCdPostoVspre());
		}

		agendamentoRepository.save(agVistoriaPrevia);
		dadosVistoria.setCdVouch(cdVouch);

		statusAgendamentoService.salvar(cdVouch, PEN, usuario);
		
		if (StringUtils.isNotBlank(cdVouchAnter)) {
			statusAgendamentoService.confirmarCancelamento(cdVouchAnter);
		}
	}

	private String gerarNrVoucher(Long idVspreObgta, Long cdSistmOrigm, PrestadoraDTO prestadoraDTO) {
		String letraSistmOrigm = obterLetraSistemaOrigem(idVspreObgta, cdSistmOrigm);

		// * Numero sequencial
		Long numSequencial = agendamentoRepository.recuperarSeqVoucher();

		// * Digito Verificador
		int digitoVerificador = NumericUtil.calculaModulo11(numSequencial);

		if(prestadoraDTO.getCdAgrmtVspre().equals(30L))
			return "TVM" + numSequencial + digitoVerificador;
		else
			return letraSistmOrigm + prestadoraDTO.getCdLetraPrtraVouch() + numSequencial + digitoVerificador;
	}

	private String obterLetraSistemaOrigem(Long idVspreObgta, Long cdSistmOrigm) {

		if (cdSistmOrigm == null) {
			throw new InternalServerException(
					format("Código do sistema de origem não encontrado para Vistoria Previa: {0}", idVspreObgta));
		}

		SistemaOrigem sistmOrigm = SistemaOrigem.retornaSistemaOrigem(cdSistmOrigm);

		if (sistmOrigm != SistemaOrigem.ORIGEM_PLATAFORMA && sistmOrigm != SistemaOrigem.ORIGEM_SEM_PROPOSTA) {
			sistmOrigm = SistemaOrigem.ORIGEM_SSV;
		}
		return sistmOrigm.getLetra();
	}

	public long countAgendamentos(Date dtVspre) {
		return agendamentoRepository.countAgendamentos(dtVspre);
	}

	public long countAgendamentos(Long codPrestadora, Date dtVspre) {
		return agendamentoRepository.countAgendamentos(codPrestadora, dtVspre);
	}

	public Optional<AgendamentoDTO> recuperarAgtoStatusPorIdPreAgto(Long idVspreObgta) {
		return agendamentoRepository.recuperarAgtoStatusPorIdPreAgto(idVspreObgta).stream().findFirst();
	}

	public Optional<AgendamentoDTO> recuperarAgtoStatusPorCdVoucher(String codVoucher) {
		return agendamentoRepository.recuperarAgtoStatusPorCdVoucher(codVoucher).stream().findFirst();
	}

	/**
	 * Verifica regras relacionandas a reagendamento - Se o ultimo status for NAP
	 * não permite reagendamento para Domicilio. - Recupera prestatora anterior para
	 * realizar redistribuição dos percentuais. (Prestadora não receberá
	 * agendamento) NAG OU (CAN E (motivo 3 OU 6)
	 *
	 * @param idVspreObgta
	 * @param listDistrVistorias
	 * @return
	 * @throws VistoriaPreviaException
	 */
	public Set<Long> verificarRegrasReagendamento(Long idVspreObgta,
			List<ParametroPercentualDistribuicaoDTO> listDistrVistorias) {

		Set<Long> listaCodPrtraAnterior = new HashSet<>();

		// * Carrega o ultimo agendamento com o ultimo status desse Pre-Agendamento
		recuperarAgtoStatusPorIdPreAgto(idVspreObgta).ifPresent(agendamentoStatus -> {

			// * Se o ultimo status for NAP não permite reagendamento para Domicilio.
			StatusAgendamentoDTO status = agendamentoStatus.getStatus();

			if (NAP == status.getCdSitucAgmto()) {
				throw new BusinessVPException(
						"Vistoria a Domicilio não permitida devido a distância com km excessivo, selecionar Posto como 'Tipo Vistoria'.");
			}

			String voucherPai = null;

			// * Verifica o status anterior de um agendamento(caso ele tenha)
			boolean temAgtoPai = true;
			Long codPrtraAnterior = null;
			while (temAgtoPai) {

				codPrtraAnterior = null;

				// * Para casos de mais cancelamentos que numero de prestadoras disponiveis
				// * Desconsidera a ultima a entrar na lista
				if (listaCodPrtraAnterior.size() == listDistrVistorias.size()) {
					listaCodPrtraAnterior.remove(codPrtraAnterior);
					break;
				}

				// * Recupera prestatora anterior para realizar redistribuição dos percentuais.
				// (Prestadora não receberá agendamento)
				// * NAG OU (CAN E (motivos permitidos)
				if (NAG == status.getCdSitucAgmto() || (CAN == status.getCdSitucAgmto()
						&& (isMotivoCanclReagendar(status.getCdMotvSitucAgmto())))) {

					codPrtraAnterior = agendamentoStatus.getCdAgrmtVspre();
					listaCodPrtraAnterior.add(codPrtraAnterior);
				}

				voucherPai = agendamentoStatus.getCdVouchAnter();

				if (isBlank(voucherPai)) {
					temAgtoPai = false;
					// * Para casos de numero de prestadoras anterior igual a de prestadoras
					// disponiveis
					// * Desconsidera a ultima a entrar na lista
					if (listaCodPrtraAnterior.size() == listDistrVistorias.size()) {
						listaCodPrtraAnterior.remove(codPrtraAnterior);
					}
				} else {

					agendamentoStatus = recuperarAgtoStatusPorCdVoucher(voucherPai).orElse(null);
					if (agendamentoStatus == null) {
						temAgtoPai = false;
					}
				}
			}
		});

		return listaCodPrtraAnterior;
	}
	
	public void cancelarAgendamento(StatusAgendamentoDTO status) {
		String chave = "[cancelarAgendamento] voucher:" + status.getCdVouch() + " ";
		LOGGER.info(chave + "validando");
		validarCancelamento(status);

		StatusAgendamentoAgrupamento novoStatus = new StatusAgendamentoAgrupamento();

		novoStatus.setCdSitucAgmto(CAN.getValor());
		novoStatus.setCdMotvSitucAgmto(status.getCdMotvSitucAgmto());
		novoStatus.setDsMotvVstriFruda(status.getDsMotvVstriFruda());
		novoStatus.setCdVouch(status.getCdVouch());
		novoStatus.setCdUsuroUltmaAlter(usuarioLogado.getUsuarioId());

		LOGGER.info(chave + "salvando novo status");
		statusAgendamentoService.salvar(novoStatus);
		
		LOGGER.info(chave + "enviando email de cancelamento");
		emailService.enviarEmailCancelamento(status.getCdVouch(), status.getCdMotvSitucAgmto());
	}

	private void validarCancelamento(StatusAgendamentoDTO status) {
		String voucher = status.getCdVouch();
		String chave = "[cancelarAgendamento] voucher:" + voucher + " ";

		LOGGER.info(chave + "validando motivo do cancelamento");
		if (!conteudoColunaTipoService.existsConteudo(status.getCdMotvSitucAgmto().toString())) {
			throw new BusinessVPException("Motivo do cancelamento inválido.");
		}

		LOGGER.info(chave + "validando ultimo laudo");
		laudoService.obterUltimoLaudoPorVoucher(voucher)
				.ifPresent(l -> new BusinessVPException(format(
						"Cancelamento não permitido, voucher {0} com vistoria já realizada e vinculado ao laudo {1}.",
						voucher, l.getCdLvpre())));
		
		LOGGER.info(chave + "recuperando status");
		AgendamentoDTO agendamento = recuperarAgtoStatusPorCdVoucher(voucher).orElse(new AgendamentoDTO());

		StatusAgendamentoDTO statusAgendamentoDTO = agendamento.getStatus();
		SituacaoAgendamento codSitucAgto = statusAgendamentoDTO.getCdSitucAgmto();
		
		Long codigoAlcada = usuarioAlcadaService.buscarCodigoAlcada().orElse(null);
		Long codigoAlcadaPermitida = parametroVPService.obterAlcadaCancelaAgendamento();
		LOGGER.info(chave + "obtendo alçadas [usuario]:" + codigoAlcada + " [permitida]:" + codigoAlcadaPermitida);

		validarStatusAgendamentoParaCancelamento(agendamento, codigoAlcada);

		// * Permite Cancelamento
		LOGGER.info(chave + "validando por alçada e status");
		if (!statusAgendamentoDTO.permitirCancelamento(codigoAlcada, codigoAlcadaPermitida)) {
			throw new BusinessVPException(
					format("Agendamento com status {0}, cancelamento não permitido.", codSitucAgto.getDescricao()));
		}
	}
	
	/**
	 * Verifica se o status do agendamento permite cancelamento, caso não seja
	 * posto.
	 * 
	 * @param agendamento
	 * @param codigoAlcada
	 */
	private void validarStatusAgendamentoParaCancelamento(AgendamentoDTO agendamento, Long codigoAlcada) {
		if (agendamento.getTpVspre() == D) {

			boolean icPermiteCancelamento = true;

			// Alteração:(Solicitado por #Milton Adriano (20/07/2016) - OS702434)
			// Permitir o cancelamento de RLZ somente para alçada 10.

			// Alteração:(Solicitado por #Milton Adriano (18/06/2015) - OS284836)
			// Recupera o agendamento e verifica se é em domicilio
			// caso seja, verifica se a data do agendamneto é igual da data atual
			// caso seja não permite o cancelamento do agendamento.

			SituacaoAgendamento situacaoAgendamento = agendamento.getStatus().getCdSitucAgmto();

			if (AGD == situacaoAgendamento || RGD == situacaoAgendamento) {
				AgendamentoDomicilio agendamentoDomicilio = agendamentoDomicilioService
						.obterAgendamentoDomicilio(agendamento.getCdVouch()).orElse(null);
					// Caso a data do agendmaento seja igual a data atual e alçada não for 10, não				
				icPermiteCancelamento = validaAgDomicilio(agendamentoDomicilio, codigoAlcada, agendamento.getCdVouch());					
				
				// caso o status da solicitação seja igual RLZ e alçada menor que 10 não permite
				// o cancelamento.
			} else if (RLZ == situacaoAgendamento && codigoAlcada < 10) {

				icPermiteCancelamento = false;
			}

			// caso o status seja igual a PEN, RCB, FLZ com alçada 10 permite o
			// cancelamento.
			if (!icPermiteCancelamento) {
				throw new BusinessVPException(
						"Cancelamento não permitido, data e/ou status do agendamento não permitido.");
			}
			
		}
		if (agendamento.getTpVspre() == TipoVistoria.M) {

			if (!agendamento.getStatus().permitirCancelamentoMobile(codigoAlcada)) {
				throw new BusinessVPException(
						"Cancelamento não permitido, data e/ou status do agendamento não permitido.");
			}
			
		}
	}
	
	private Boolean validaAgDomicilio(AgendamentoDomicilio agendamentoDomicilio, Long codigoAlcada, String cdVouch) {
		LOGGER.info("[cancelarAgendamento] voucher:" + cdVouch + " validando agendamento domicilio");
		
		if (agendamentoDomicilio != null && agendamentoDomicilio.getDtVspre() != null && codigoAlcada < 10) {
			Date dataAgendamento = DateUtil.truncaData(agendamentoDomicilio.getDtVspre());
			return dataAgendamento.compareTo(DateUtil.truncaData(new Date())) != 0;
		} else {
			return codigoAlcada.equals(10L);
		}
		
	}

	public AgendamentoDTO visualizarAgendamento(Long idVistoria, String voucher) {
		VistoriaObrigatoriaDTO dto = vistoriaPreviaObrigatoriaService.buscarVistoria(idVistoria);

		addTelefoneEmailCorretor(voucher, dto.getCorretor());

		AgendamentoDTO agendamentoDTO = obterAgendamentoPorVoucher(voucher).orElseThrow(NoContentException::new);

		agendamentoDTO.setVistoria(dto);

		return agendamentoDTO;
	}

	private void addTelefoneEmailCorretor(String voucher, final Corretor dto) {
		// Dados do corretor

		List<AgendamentoTelefoneDTO> telefonesCorretor = agendamentoContatoTelefoneService
				.findContatoTelefoneCorretor(voucher);
		dto.setTelefones(telefonesCorretor);

		List<String> emailsCorretor = agendamentoContatoEmailService.findAgendamentoEmailCorretor(voucher);
		dto.setEmails(emailsCorretor);
	}

	public List<PrestadoraDTO> obterPrestadorasDistribuicao(Long calculo, String cep) {
		VistoriaPreviaObrigatoria preAgendamento = obterPreAgendamentoPorCalculo(calculo);
		Long idRegiao = regiaoAtendimentoVistoriadorService.obterIdRegiao(cep);
		
		return obterPrestadorasDistribuicao(preAgendamento.getIdVspreObgta(), idRegiao, new Date()).stream().map(d -> {
			PrestadoraDTO prestadora = new PrestadoraDTO();
			
			prestadora.setCdAgrmtVspre(d.getCdAgrmtVspre());
			prestadora.setNmRazaoSocal(d.getNmRazaoSocal());
			
			return prestadora ;
		}).collect(Collectors.toList());
	}
	
	public List<PrestadoraDTO> obterPrestadorasDistribuicao(Long idVspreObgta, Long idRegiao, Date dataReferencia) {

		return parametroPercentualDistribuicaoService.carregarParametrosDistribuicao(idVspreObgta, idRegiao, dataReferencia)
				.stream().map(pd -> {
					pd.getPrestadora().setPercentualDistribuicao(pd.getPcDistr());
					return pd.getPrestadora();
				}).collect(Collectors.toList());
	}
	
	/**
	 * Retorna os tipos disponíveis de local de vistoria para a tela de agendamento.
	 * @param idVistoria
	 * @param idRegiao
	 * @return
	 */
	public Set<TipoVistoria> obterTiposAgendamento(Long idVistoria, Long idRegiao) {
		Set<TipoVistoria> tipos = new HashSet<>();

		VistoriaPreviaObrigatoria vistoria = vistoriaPreviaObrigatoriaService.findById(idVistoria).orElseThrow(() -> new BusinessVPException("[obterTiposAgendamento] Vistoria id:"+idVistoria+" não localizada."));

		boolean permiteMobilePorAlcada = isPermiteMobilePorAlcada();
		if (parametroVPService.isContingenciaAtivada() && isCorretorPermitido(vistoria.getCdCrtorCia()) && !permiteMobilePorAlcada) {
			tipos.add(TipoVistoria.M);
			return tipos;
		}
		
		if (isPermiteMobilePorVistoria(vistoria) || permiteMobilePorAlcada) {
			tipos.add(TipoVistoria.M);
		}
		
		if (postoVistoriaPreviaService.contemPosto(idRegiao)) {
			tipos.add(TipoVistoria.P);
		}
		
		if (parametroPercentualDistribuicaoService.contemVistoriador(idRegiao)) {
			tipos.add(TipoVistoria.D);
		}
		
		if (tipos.isEmpty() && isPermiteMobilePorExcecao(vistoria, idRegiao)) {
			tipos.add(TipoVistoria.M);
		}
		
		return tipos;
	}
	
	private boolean isPermiteMobilePorAlcada() {
		StringBuilder msg = new StringBuilder("[isPermiteMobilePorAlcada]\r\n");

		try {
			Long codigoAlcada = 0L;
			String usuarioId = usuarioLogado.getUsuarioId();

			Optional<UsuarioAlcada> usuarioAlcada = usuarioAlcadaService
					.getUsuarioAlcadaByCdMduprCdUsuarioDataVigencia(7L, usuarioId);

			msg.append("Pesquisando alçada do usuario " + usuarioId + "\r\n");

			if (usuarioAlcada.isPresent()) {
				codigoAlcada = usuarioAlcada.get().getCdAlcad();

				msg.append("Alçada encontrada: " + codigoAlcada + "\r\n");
			} else {
				msg.append("Alçada não encontrada\r\n");
			}

			Long alcadaAgendamentoMobile = parametroVPService.obterAlcadaAgendamentoMobile();

			msg.append("Alçada para agendamento mobile: " + alcadaAgendamentoMobile.toString() + "\r\n");

			LOGGER.info(msg);
			
			return codigoAlcada.longValue() >= alcadaAgendamentoMobile.longValue();

		} catch (Exception e) {
			throw new InternalServerException(msg.toString(), e);
		}
	}
	
	private boolean isPermiteMobilePorVistoria(VistoriaPreviaObrigatoria vistoria) {
		return "S".equals(vistoria.getIcMbile());
	}
	
	private boolean isPermiteMobilePorExcecao(VistoriaPreviaObrigatoria vistoria, Long idRegiao) {
		Long idCorretor = vistoria.getCdCrtorCia();
		String cep = vistoria.getNrCep();
		Long itSeg = vistoria.getNrItseg();

		return (isNotCaminhoMoto(itSeg) && (isMesmaCidadePerNoite(cep, idRegiao) || isCorretorPermitido(idCorretor)));
	}

	private boolean isNotCaminhoMoto(Long itSeg) {
		List<Long> listRestricaoMobile = new ArrayList<>();
		listRestricaoMobile.add(18051l);
		listRestricaoMobile.add(18061l);
		listRestricaoMobile.add(18078l);
		listRestricaoMobile.add(18058l);
		listRestricaoMobile.add(18059l);
		listRestricaoMobile.add(18060l);
		listRestricaoMobile.add(18062l);
		listRestricaoMobile.add(18056l);
		listRestricaoMobile.add(18057l);
		listRestricaoMobile.add(18063l);
		listRestricaoMobile.add(18048l);
		listRestricaoMobile.add(18055l);

		return !descricaoItemSeguradoRepository
				.existsByNumItemSeguradoAndCodCaracteristicaItemSeguradoAndCodValorCaracteristicaItemSeguradoIn(itSeg,
						326L, listRestricaoMobile);
	}

	private boolean isCorretorPermitido(Long idCorretor) {
		String param = parametroVPService.obterCorretoresPermitidosAgendamentoMobile();

		if (param.equals("*")) {
			return true;
		}
		
		if (param.equals("0")) {
			return false;
		}

		String[] corretores = StringUtils.split(param, ";");

		return Stream.of(corretores).anyMatch(c -> c.equals(idCorretor.toString()));
	}

	private boolean isMesmaCidadePerNoite(String cep, Long idRegiao) {
		Optional<LogradouroDTO> obterLogradouro = mapService.obterLogradouro(cep);
		
		if (obterLogradouro.isPresent()) {
			RegiaoAtendimentoVistoriador filtro = new RegiaoAtendimentoVistoriador();
			filtro.setIdRegiaoAtnmtVstro(idRegiao);
			filtro.setNmMunic(obterLogradouro.get().getCidade());
			
			return regiaoAtendimentoVistoriadorService.exists(Example.of(filtro));
		}
		
		return false;
	}

	public boolean isPermiteMobile(Long idVistoria, Long idRegiao) {
		return obterTiposAgendamento(idVistoria, idRegiao).contains(TipoVistoria.M);
	}
	
	/**
	 * Retorna os tipos disponíveis de local de vistoria para o multicalculo.
	 * @param cepVistoria
	 * @param cepPernoite
	 * @param codCorretor
	 * @param tipoVeiculo
	 * @return [D, P, M].
	 */
	public Set<String> obterTiposAgendamento(String cepVistoria, String cepPernoite, Long codCorretor,
			TipoVeiculoLocalAgendamentoEnum tipoVeiculo) {
		try {
			Set<String> tipos = new HashSet<>();

			Long idRegiao = regiaoAtendimentoVistoriadorService.obterIdRegiao(cepVistoria);

			if (postoVistoriaPreviaService.contemPosto(idRegiao)) {
				tipos.add(TipoVistoria.P.getCodigo());
			}

			if (parametroPercentualDistribuicaoService.contemVistoriador(idRegiao)) {
				tipos.add(TipoVistoria.D.getCodigo());
			}

			if (tipos.isEmpty() && tipoVeiculo == TipoVeiculoLocalAgendamentoEnum.P
					&& (cepVistoria.equals(cepPernoite) || isCorretorPermitido(codCorretor))) {
				tipos.add(TipoVistoria.M.getCodigo());
			}

			return tipos;
			
		} catch (BusinessVPException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerException("Ocorreu um erro ao tentar obter os tipos de vistoria.", e);
		}
	}

	public List<DataAgendamentoDTO> obterDatasAgendamento() {
		List<DataAgendamentoDTO> datas = new ArrayList<>();

		RangeDataAgendamentoDTO range = obterDatasAgendamento(false);

		for (LocalDate dt = range.getMinDate(); !dt.isAfter(range.getMaxDate()); dt = dt.plusDays(1l)) {

			if (dt.getDayOfWeek() != DayOfWeek.SUNDAY) {
				
				if (dt.getDayOfWeek() == DayOfWeek.SATURDAY) {
					datas.add(new DataAgendamentoDTO(PeriodoAgendamentoEnum.M.getValor(), dt));
				
				} else {
					boolean isFeriado;
					try {
						isFeriado = acselXRestClient.isFeriado(java.sql.Date.valueOf(dt));
					} catch (Exception e) {
						throw new ExternalServerException("ACSELX - Erro ao validar feriado. Erro: "
								+ ExceptionUtils.getRootCause(e).getMessage(), e);
					}
					
					if (!isFeriado) {
						if (dt.isEqual(range.getMinDate()) && !PeriodoAgendamentoEnum.C.getValor().equals(range.getPeriodoVistoria())) {
							datas.add(new DataAgendamentoDTO(range.getPeriodoVistoria(), dt));
						} else {
							datas.add(new DataAgendamentoDTO(PeriodoAgendamentoEnum.C.getValor(), dt));
							datas.add(new DataAgendamentoDTO(PeriodoAgendamentoEnum.M.getValor(), dt));
							datas.add(new DataAgendamentoDTO(PeriodoAgendamentoEnum.T.getValor(), dt));
						}
					}
				}
			}
		}

		return datas;
	}
	
	public List<DataAgendamentoDTO> obterDatasAgendamentoSantander(String cotacao) {
		obterPreAgendamentoPorCotacaoAutoCompara(cotacao);
		
		try {
			List<DataAgendamentoDTO> datas = new ArrayList<>();

			Long minDate = parametroVPService.obterQtdDiasUteisAGDDomicilioSantander();
			int maxDate = parametroVPService.obterDataFinalAGDDomicilioSantander();
			Date dataDMais = acselXRestClient.somarDiasCorridos(DateUtil.truncaData(new Date()), minDate);

			for (int i = 0; i < maxDate; i++) {

				boolean isSabado = new Integer(7).equals(DateUtil.getDiaDaSemana(dataDMais));

				if (isSabado || !acselXRestClient.isFeriado(dataDMais)) {

					LocalDate dt = new java.sql.Date(dataDMais.getTime()).toLocalDate();
					if (isSabado) {
						datas.add(new DataAgendamentoDTO(PeriodoAgendamentoEnum.M.getValor(), dt));
					} else {
						datas.add(new DataAgendamentoDTO(PeriodoAgendamentoEnum.M.getValor(), dt));
						datas.add(new DataAgendamentoDTO(PeriodoAgendamentoEnum.T.getValor(), dt));
					}
				} else {
					maxDate++;
				}

				if ((i + 1) < maxDate) {
					dataDMais = acselXRestClient.somarDiasCorridos(dataDMais, 1L);
				}
			}

			return datas;
		} catch (Exception e) {
			throw new ExternalServerException(
					"ACSELX - Erro no serviço de data. Erro: " + ExceptionUtils.getRootCause(e).getMessage(), e);
		}
	}

	private RangeDataAgendamentoDTO obterDatasAgendamento(boolean isCapital) {
		try {
			int minDate = isCapital ? 1 : 2;
			int maxDate = 6;

			Date dataReferencia = new Date();

			SimpleDateFormat formataHora = new SimpleDateFormat("kk:mm");

			Date dataAtual = DateUtil.truncaData(dataReferencia);

			Date horaAtual = formataHora.parse(DateUtil.formataHora(dataReferencia));
			Date horaInicManha = formataHora.parse("00:01");
			Date horaFimManha = formataHora.parse("12:00");
			Date horaInicTarde = formataHora.parse("12:01");
			Date horaFimTarde = formataHora.parse("18:00");
			Date horaInicNoite = formataHora.parse("18:01");

			String periodoVistoria = ConstantesVistoriaPrevia.COD_PERIODO_COMERCIAL;

			// SE hora atual maior que 18:01
			if (horaAtual.getTime() >= horaInicNoite.getTime()) {
				minDate++;
				maxDate++;
				dataAtual = acselXRestClient.somarDiasCorridos(dataAtual, 1l);
			} else if (horaAtual.getTime() >= horaInicTarde.getTime()
					&& horaAtual.getTime() <= horaFimTarde.getTime()) {
				periodoVistoria = ConstantesVistoriaPrevia.COD_PERIODO_TARDE;
			}

			Date dataDMais = acselXRestClient.somarDiasCorridos(dataAtual, (isCapital ? 1L : 2L));

			// * Considerar somente dias uteis e sabados no periodo da manhã(08:00 as 12:00)
			boolean isSabado = new Integer(7).equals(DateUtil.getDiaDaSemana(dataDMais));
			if (!acselXRestClient.isFeriado(dataDMais) || isSabado) {

				if (isSabado) {

					if (horaAtual.getTime() > horaInicManha.getTime() && horaAtual.getTime() < horaFimManha.getTime()) {
						periodoVistoria = ConstantesVistoriaPrevia.COD_PERIODO_MANHA;
					} else {
						minDate++;
						periodoVistoria = ConstantesVistoriaPrevia.COD_PERIODO_COMERCIAL;
					}
				}
				// minDateCapital++;
				// maxDateCapital++;
			} else {
				// * SE D+1 não é um Dia Util ou Sábado
				minDate++;
			}

			RangeDataAgendamentoDTO dataAgendamento = new RangeDataAgendamentoDTO();
			dataAgendamento.setPeriodoVistoria(periodoVistoria);
			dataAgendamento.setMaxDate(LocalDate.now().plusDays(maxDate));
			dataAgendamento.setMinDate(LocalDate.now().plusDays(minDate));

			return dataAgendamento;
		} catch (Exception e) {
			throw new InternalServerException("[obterDatasAgendamento] Ocorreu um erro", e);
		}
	}
	
	public RangeDataAgendamentoDTO obterDatasAgendamento(String cidade) {

		boolean isCapital = parametroPercentualDistribuicaoService.isCapital(cidade);

		return obterDatasAgendamento(isCapital);
	}
	
	public void atualizarStatus(AtualizacaoStatusDTO req) {
		LOGGER.info("[atualizarStatus:" + req.getCdVoucher() + "] Atualizando status... " + gson.toJson(req));

		boolean isReagendado = false;
		StatusAgendamentoAgrupamento statusAtual = req.getStatusAtual();
		StatusAgendamentoAgrupamento novoStatus = req.getNovoStatus();
		
		if (novoStatus == null) {
			LOGGER.warn("[atualizarStatus:" + req.getCdVoucher() + "] request com status nulo");
			throw new BadRequestException();
		}
		
		statusAgendamentoService.salvar(novoStatus);
		
		if (RGD.getValor().equals(novoStatus.getCdSitucAgmto())) {
			LOGGER.info("[atualizarStatus:" + req.getCdVoucher() + "] Atualizando agendamento domicílio...");
			
			AgendamentoDomicilio agendamentoDomicilio = agendamentoDomicilioService.obterAgendamentoDomicilio(req.getCdVoucher())
			.orElseThrow(() -> new BusinessVPException("[atualizarStatus] agendamento domicílio não localizado para o voucher:" + req.getCdVoucher()));
			
			AgendamentoDomicilio agendamento = req.getAgendamentoDomicilio();
			agendamentoDomicilio.setDtVspre(agendamento.getDtVspre());
			if (!StringUtil.isEmpty(agendamento.getIcPerioVspre())) {
				agendamentoDomicilio.setIcPerioVspre(agendamento.getIcPerioVspre());
			}
			
			agendamentoDomicilioService.salvar(agendamentoDomicilio);
			
		} else if (CAN.getValor().equals(novoStatus.getCdSitucAgmto())) {
			isReagendado = gerarAgendamentoDigital(req.getCdVoucher());
		}
		
		//* Se for caso para reagendamento envia email informativo para corretor.
		if (UtilNegocio.isReagendamento(novoStatus.getCdSitucAgmto(), novoStatus.getCdMotvSitucAgmto()) && !isReagendado) {
			try {
				LOGGER.info("[atualizarStatus:" + req.getCdVoucher() + "] Enviando e-mail para corretor para reagendar vistoria...");

    			String motivoRecusa = novoStatus.getDsMotvVstriFruda();
    			if (!UtilJava.trueVar(motivoRecusa.trim()) && UtilJava.trueVar(novoStatus.getCdMotvSitucAgmto())) {
    				Optional<ConteudoColunaTipoDTO> motivo = conteudoColunaTipoService.obterMotivoCancelamentoPorCodMotivo(novoStatus.getCdMotvSitucAgmto(), statusAtual.getDtUltmaAlter());
    				if (motivo.isPresent()) {
    					motivoRecusa = motivo.get().getDsCoptaColunTipo();
    				}
    			}

    			if (!UtilJava.trueVar(motivoRecusa)) {
    				motivoRecusa = "NÃO INFORMADO";
    			}

				emailService.enviarEmailVistoriaRecusada(novoStatus.getCdVouch(), motivoRecusa);
			} catch (Exception e) {
				LOGGER.info("[atualizarStatus:" + req.getCdVoucher() + "] erro ao enviar e-mail para corretor, erro:" + ExceptionUtils.getRootCause(e).getMessage());
			}
		}
	}

	private boolean gerarAgendamentoDigital(String voucher) {
		boolean isReagendado = false;
		StringBuilder log = new StringBuilder();
		adicionarLinhaLog("METODO gerarMobile: INICIO [vistorias]:" + voucher, log);

		VistoriaPreviaObrigatoria preAgendamento = vistoriaPreviaObrigatoriaService
				.obterPreAgendamentoPorVoucher(voucher).orElseThrow(
						() -> new BusinessVPException("Pré-agendamento não encontrado para o voucher:" + voucher));

		if (parametroVPService.isCorretorRGDAutomaticoMobile(preAgendamento.getCdCrtorCia())) {
			LOGGER.info("[gerarMobile:" + voucher + "] Gerando agendamento digital...");
			
			Optional<ParamAGDAutomaticoMobileDTO> param = parametroVPService.obterCorretoresAGDAutomaticoMobile()
					.stream().filter(a -> a.getCorretores().contains(preAgendamento.getCdCrtorCia())).findFirst();
			
			if (param.isPresent()) {
	
				try {
					List<AgendamentoTelefoneDTO> telefonesSegurado = agendamentoContatoTelefoneService
							.findContatoTelefoneSegurado(voucher);
					
					List<String> emailsCliente = agendamentoContatoEmailService.findAgendamentoEmailCliente(voucher);
	
					Corretor corretor = new Corretor();
					addTelefoneEmailCorretor(voucher, corretor);
					corretor.setIdCorretor(preAgendamento.getCdCrtorCia());
					corretor.setNomeCorretor(corretor.getTelefones().stream().findFirst().get().getNmConttVspre());
	
					VistoriaObrigatoriaDTO vistoria = new VistoriaObrigatoriaDTO();
					vistoria.setEmailCliente(emailsCliente.stream().findFirst().orElse(null));
					vistoria.setCorretor(corretor);
					
					AgendamentoDTO agendamento = new AgendamentoDTO();
					agendamento.setTpVspre(TipoVistoria.M);
					agendamento.setIdsVistorias(Arrays.asList(preAgendamento.getIdVspreObgta()));
					agendamento.setTelefones(telefonesSegurado);
					agendamento.setVistoria(vistoria);
	
					adicionarLinhaLog("carregando prestadora... ", log);
					PrestadoraDTO prestadora = prestadoraVistoriaPreviaService.obterPrestadora(param.get().getPrestadoraMobile());
					agendamento.setPrestadora(prestadora);
					
					adicionarLinhaLog("salvando agendamento... ", log);
					salvarAgendamento(preAgendamento, agendamento, "ADMVPE", true, log);
	
					isReagendado = true;
					
					adicionarLinhaLog("METODO gerarMobile: FIM ", log);
		
				} catch (BusinessVPException e) {
					throw e;
				} catch (Exception e) {
					throw new InternalServerException(
							"Ocorreu um erro ao tentar gerar um agendamento mobile para o voucher " + voucher, e);
				}
			} else {
				LOGGER.info("[gerarMobile:" + voucher + "] prestadora para agendamento digital não encontrada para o corretor: " + preAgendamento.getCdCrtorCia());
			}
		} else {
			LOGGER.info("[gerarMobile:" + voucher + "] Corretor: " + preAgendamento.getCdCrtorCia() + " não cadastrado para reagendamento mobile automático.");
		}
		
		LOGGER.info(log);
		
		return isReagendado;
	}

	public Object gerarAgendamentoSantander(String cotacao, AgendamentoDTO dto) throws BindException {
		LOGGER.info("[gerarAgendamento] cotacao:" + cotacao + " agendamento:" + gson.toJson(dto));
		if (dto.isDomicilio()) {
			return gerarAgendamentoDomicilioSantander(cotacao, dto);

		} else if (dto.isTipoPosto()) {
			return gerarAgendamentoPostoSantander(cotacao, dto);

		} else if (dto.isTipoMobile()) {
			return gerarAgendamentoMobileSantander(cotacao, dto);

		} else {
			throw new BusinessVPException("Parametro Tipo de Vistoria inválido [tpVistoria P = posto| D = Domicilio| M = Mobile]");
		}
	}

	private Object gerarAgendamentoMobileSantander(String cotacao, AgendamentoDTO dto) throws BindException {
		StringBuilder log = new StringBuilder("[gerarAgendamentoMobileSantander] cotacao: " + cotacao + " req: "+ gson.toJson(dto) );
		VistoriaObrigatoriaDTO preAgdSantander = obterPreAgendamentoPorCotacaoAutoCompara(cotacao);

		VistoriaPreviaObrigatoria vp = vistoriaPreviaObrigatoriaService.obterPreAgendamentoPorCalculo(preAgdSantander.getNrCallo()).orElse(null);
		
		if (vp != null) {
			adicionarLinhaLog("vp localizada pelo numero do calculo: " + preAgdSantander.getNrCallo(), log);
			preAgdSantander.setCdPlacaVeicu(vp.getCdPlacaVeicu());
			preAgdSantander.setCdChassiVeicu(vp.getCdChassiVeicu());
			preAgdSantander.setNrCpfCnpjClien(vp.getNrCpfCnpjClien());
			preAgdSantander.setNmClien(vp.getNmClien());
		}
		
		if (vp == null) {
			adicionarLinhaLog("gravando vp", log);
			vp = preAgendamentoService.gravarPreAgendamentoSantander(preAgdSantander.getNrCallo(), preAgdSantander, log);
		}
		
		vp.setCdCrtorCia(ConstantesVistoriaPrevia.COD_CORRETOR_SANTANDER);
		
		List<ResponseAgendamentoDTO> response = createAgendamentoWS(ConstantesRegrasVP.MULTICALCULO, "WS_AC", dto, vp, true, log);
		
		LOGGER.info(log);
		
		return response;
	}

	private Object gerarAgendamentoDomicilioSantander(String cotacao, AgendamentoDTO dto) throws BindException {
		StringBuilder log = new StringBuilder();
		
		if (dto.getTpVspre() == TipoVistoria.D) {
			VistoriaObrigatoriaDTO preAgdSantander = obterPreAgendamentoPorCotacaoAutoCompara(cotacao);
			
			// Verifica se o serviço de consulta da cotação no CTA(AutoCompara) retornou chassi, caso não tenha retornado, utiliza paramatros enviados
			// pelo serviço chamador (efetivação on line ou recepção eletrônica).

			preAgdSantander.setNrCpfCnpjClien(preAgdSantander.getNrCpfCnpjClien() == null ? dto.getVistoria().getNrCpfCnpjClien() : preAgdSantander.getNrCpfCnpjClien()); // CPF/CNPJ
			preAgdSantander.setCdChassiVeicu(preAgdSantander.getCdChassiVeicu() == null  ? dto.getVistoria().getCdChassiVeicu() : preAgdSantander.getCdChassiVeicu()); // Chassi
			preAgdSantander.setCdPlacaVeicu(preAgdSantander.getCdPlacaVeicu() == null || preAgdSantander.getCdPlacaVeicu().equals(ConstantesVistoriaPrevia.PLACA_AAVISAR) ?
					dto.getVistoria().getCdPlacaVeicu() : preAgdSantander.getCdPlacaVeicu()); // Placa
			
			Optional<AgendamentoDomicilio> agendamentoDomicilio = Optional.empty();
			
			String voucher = preAgendamentoService.obterVoucher(preAgdSantander.getCdChassiVeicu(), ConstantesVistoriaPrevia.COD_CORRETOR_SANTANDER, SistemaChamador.AUTOCOMPARASANTANDER);
			if (voucher != null) {
				agendamentoDomicilio = agendamentoDomicilioService.obterAgendamentoDomicilio(voucher);
			}
			
			if (!agendamentoDomicilio.isPresent()) {
				VistoriaPreviaObrigatoria vp = preAgendamentoService.gravarPreAgendamentoSantander(preAgdSantander.getNrCallo(), preAgdSantander, log);
			
				Long cdPrestadora = parametroVPService.obterPrestadoraDomicilioUF(dto.getAgendamentoDomicilio().getSgUniddFedrc());
				dto.setCdAgrmtVspre(cdPrestadora);
				
				List<ResponseAgendamentoDTO> response = createAgendamentoWS(ConstantesRegrasVP.MULTICALCULO, "WS_AC", dto, vp, true, log);
				
				LOGGER.info(log);
				
				return response;
			} else {
				return Arrays.asList(new ResponseAgendamentoDTO(null, preAgdSantander.getCdChassiVeicu(), voucher));
			}
		}
		
		return null;
	}

	private VistoriaObrigatoriaDTO obterPreAgendamentoPorCotacaoAutoCompara(String cotacao) {
		VistoriaObrigatoriaDTO preAgdSantander = preAgendamentoService.recuperaPreAgendamentoSantanderAutoComparaNoCotador(cotacao);

		if (preAgdSantander == null) {
			throw new BusinessVPException("Cotação não encontrada para o Numero Cotação AutoCompara : " + cotacao);
		}
		return preAgdSantander;
	}
	
	private Object gerarAgendamentoPostoSantander(String cotacao, AgendamentoDTO dto) throws BindException {
		String uf = dto.getUf(); 
		
		if (uf == null) {
			throw new BusinessVPException("Informe a UF.");
		}

		StringBuilder log = new StringBuilder();
		VistoriaObrigatoriaDTO preAgdSantander = obterPreAgendamentoPorCotacaoAutoCompara(cotacao);

		VistoriaPreviaObrigatoria vp = vistoriaPreviaObrigatoriaService.obterPreAgendamentoPorCalculo(preAgdSantander.getNrCallo()).orElse(null);
		
		if (vp != null) {
			preAgdSantander.setCdPlacaVeicu(vp.getCdPlacaVeicu());
			preAgdSantander.setCdChassiVeicu(vp.getCdChassiVeicu());
			preAgdSantander.setNrCpfCnpjClien(vp.getNrCpfCnpjClien());
			preAgdSantander.setNmClien(vp.getNmClien());
		}
		
		List<PostoDTO> postos; 
		
		try {
			postos = obterPostosSantander(uf, dto.getCidade());
		} catch (NoContentException e) {
			throw new BusinessVPException("Postos não recuperados! Verifique UF e Cidade!");
		}
		
		boolean ufAlterado = false;
		boolean vpSemVoucher = false;

		if (vp != null) {
			//Se encontrar vp, verifica se a UF é a mesmo do voucher já gerado, se não gera novo voucher.
			ufAlterado = !StringUtils.equalsIgnoreCase(vp.getSgUniddFedrc(), uf);
			
			//Se não tiver voucher no pre-agendamento, gerar novo voucher
			vpSemVoucher = StringUtils.isBlank(vp.getCdVouch());
		}
		
		//Verifica se já tem pré agendamento na VPE0424 e Se tem retorno de postos para os parametros informados
		if((vp == null || ufAlterado || vpSemVoucher) && postos.size() > 0){
			
			if(vp == null) {
				vp = preAgendamentoService.gravarPreAgendamentoSantander(preAgdSantander.getNrCallo(), preAgdSantander, log);
			}
			
			vp.setCdCrtorCia(ConstantesVistoriaPrevia.COD_CORRETOR_SANTANDER);
			vp.setSgUniddFedrc(uf);
			vp.setNmCidad(StringUtils.isNotBlank(dto.getCidade()) ? dto.getCidade() : null);
			
			PostoDTO postoDTO = postos.stream().findFirst().get();
			dto.setCdPostoVspre(postoDTO.getCdPostoVspre());
			dto.setCdAgrmtVspre(postoDTO.getCdAgrmtVspre());
			dto.setTpVspre(P);
			
			List<ResponseAgendamentoDTO> response = createAgendamentoWS(ConstantesRegrasVP.MULTICALCULO, "WS_AC", dto, vp, false, log);
			
			LOGGER.info(log);
			
			return response;
		} else {
			return Arrays.asList(new ResponseAgendamentoDTO(null, preAgdSantander.getCdChassiVeicu(), vp.getCdVouch()));
		}
	}

	public List<PostoDTO> obterPostosSantander(String cotacao, String uf, String cidade) {
		obterPreAgendamentoPorCotacaoAutoCompara(cotacao);
		
		return obterPostosSantander(uf, cidade);
	}
	
	private List<PostoDTO> obterPostosSantander(String uf, String cidade) {
		Long cdPrestadora = parametroVPService.obterPrestadoraPostoUF(uf);
		
		return postoVistoriaPreviaService.obterPostos(cdPrestadora, uf, cidade);
	}
	
	public VistoriaPreviaObrigatoria obterPreAgendamentoPorCalculo(Long calculo) {
		return vistoriaPreviaObrigatoriaService.obterPreAgendamentoPorCalculo(calculo)
				.orElseThrow(() -> new BusinessVPException(
						format("Pré-Agendamento não encontrado para o número do cálculo: {0}.", calculo)));
	}
}