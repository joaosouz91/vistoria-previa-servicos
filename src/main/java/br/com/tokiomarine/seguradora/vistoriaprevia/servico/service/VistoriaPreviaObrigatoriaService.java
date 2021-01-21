package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro.Fields.corretor;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro.Fields.cpfCnpj;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro.Fields.dataAte;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro.Fields.dataDe;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro.Fields.endosso;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.text.MaskFormatter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import br.com.tokiomarine.seguradora.aceitacao.rest.client.AcselXRestClient;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.ConsultaCorretorResponse;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.ConsultaEmailCorretorResponse;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.RequestLogAceitacao;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.util.CpfCnpjUtils;
import br.com.tokiomarine.seguradora.aceitacao.rest.client.util.DateUtil;
import br.com.tokiomarine.seguradora.ext.ssv.service.ConteudoColunaTipoService;
import br.com.tokiomarine.seguradora.ext.ssv.service.PlacaImpeditivaService;
import br.com.tokiomarine.seguradora.ext.ssv.service.UsuarioAlcadaService;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.MotivoVistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioLogado;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.StatusAgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaObrigatoriaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ClientePessoa;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Corretor;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.DadosEmailSegurado;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.DestinatarioDetalhes;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.EmailGNT;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.EmailGNTResponse;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.AmbienteEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamentoFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVeiculo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.ExternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NoContentException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.MotivoVistoriaPreviaObrigatoriaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.VistoriaPreviaObrigatoriaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.restclient.ClientePessoaRestClient;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.hateoas.VistoriaPreviaObrigatoriaResourceUtil;

@Service
public class VistoriaPreviaObrigatoriaService {

	private static final String USUARIO_CADASTRAMENTO = "ADMVPE";

	private static final String NOME_CORRETOR = "NOME_CORRETOR";

	private static final String TELEFONE_CORRET = "TELEFONE_CORRET";

	private static final String ENVIA_S = "?envia=S";
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private VistoriaPreviaObrigatoriaRepository vistoriaRepository;
	
	@Autowired
	private AgendamentoContatoTelefoneService agendamentoContatoTelefoneService;

	@Autowired
	private ParametroVPService parametroVPService;

	@Autowired
	private UsuarioAlcadaService usuarioAlcadaService;

	@Autowired
	private VistoriaPreviaObrigatoriaResourceUtil vistoriaRU;

	@Autowired
	private UsuarioService usuarioLogado;

	@Autowired
	private ClientePessoaRestClient clientePessoaRestClient;
	
	@Autowired
	private ConteudoColunaTipoService conteudoColunaTipoService;

	@Autowired
	private PlacaImpeditivaService placaImpeditivaService;
	
	@Autowired
	private CorretorService corretorService;
	
	@Autowired
	private MotivoVistoriaPreviaObrigatoriaRepository motivoVistoriaPreviaObrigatoriaRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ParametroVistoriaPreviaGeralService parametroService;

	@Autowired
	private AcselXRestClient acselXRestClient;
	
	@Value("${tokiomarine.infra.AMBIENTE}")
	private String ambiente;
	
	@Value("${tokiomarine.gravaLogService}")
	private String logUrl;
	
	private Long count=0L;
	
	@Autowired
	private PreAgendamentoService preAgendamentoService;
	
	@Autowired
	private ModelMapper mapper;
	
	private static final Logger LOGGER = LogManager.getLogger(VistoriaPreviaObrigatoriaService.class);

	@Transactional
	public VistoriaPreviaObrigatoria salvar(VistoriaPreviaObrigatoria entity) {
		return vistoriaRepository.save(entity);
	}
	
	public List<VistoriaObrigatoriaDTO> pesquisarVistoriasObrigatorias(VistoriaFiltro filtro) throws BindException {
		validarFiltro(filtro);
		return obterVistorias(filtro);
	}

	public VistoriaObrigatoriaDTO save(VistoriaObrigatoriaDTO vpoDTO) {
		VistoriaPreviaObrigatoria vpo = mapper.map(vpoDTO, VistoriaPreviaObrigatoria.class);
		vpo.setIdVspreObgta(null);
		vpo.setDtUltmaAlter(new Date());
		vpoDTO.setIdVspreObgta(salvar(vpo).getIdVspreObgta());
		return vpoDTO;
	}

	private List<VistoriaObrigatoriaDTO> obterVistorias(VistoriaFiltro filtro) {
		List<VistoriaObrigatoriaDTO> vistorias;
		
		addDadosComplementares(filtro);
		
		// * Se Filtro: Situação Vistoria = Todas
		if (isTodas(filtro)) {
			vistorias = obterVistoriaPorFiltroTodas(filtro);
			// * Se Filtro: Situação Vistoria = Agendar
		} else if (isAgendar(filtro)) {
			vistorias = obterVistoriaPorFiltroAgendar(filtro);
		} else {
			vistorias = obterVistoriaPorFiltroAgendada(filtro);
		}
		if(vistorias.isEmpty() && filtro.getChassi() != null) {
			vistorias = preAgendamentoService.pesquisarProposta(filtro);
			//aplicarLinksAgendada(vistorias);
		}
		if (vistorias.isEmpty()) {
			
			throw new NoContentException();
		}
		
		return vistorias;
	}

	private void addDadosComplementares(VistoriaFiltro filtro) {
		ajustarHorario(filtro);

		UsuarioLogado usuario = usuarioLogado.getUsuario();
		if (usuario.isWeb()) {
			filtro.setCorretor(usuario.getDadosPerfil().getCodigoInterno().longValue());
		}
	}

	private List<VistoriaObrigatoriaDTO> obterVistoriaPorFiltroAgendada(VistoriaFiltro filtro) {
		List<VistoriaObrigatoriaDTO> vistorias;
		vistorias = recuperarListaItemAgendadas(filtro);
		
		carregarMotivoCancelamento(vistorias);
		
		aplicarLinksAgendada(vistorias);
		return vistorias;
	}

	/** 
	 * Para CAN e VFR carrega motivo de cancelamento/frustrada para ser exibido na tela de consulta(tooltip)
	 * @param vistorias
	 */
	private void carregarMotivoCancelamento(final List<VistoriaObrigatoriaDTO> vistorias) {
		if (CollectionUtils.isNotEmpty(vistorias)) {
			vistorias.stream()
			.filter(v -> v.getAgendamento().getStatus().getCdSitucAgmto() == SituacaoAgendamento.CAN 
					|| v.getAgendamento().getStatus().getCdSitucAgmto() == SituacaoAgendamento.VFR)
			.forEach(v -> {
				
				StatusAgendamentoDTO status = v.getAgendamento().getStatus();
				
				if (status.getCdMotvSitucAgmto() != null) {
					
					conteudoColunaTipoService.obterMotivoCancelamentoPorCodMotivo(status.getCdMotvSitucAgmto())
					.ifPresent(m -> status.setDsMotvSitucAgmto("Motivo Situacao: " + m.getDsCoptaColunTipo()));
					
				} else if (status.getDsMotvVstriFruda() != null) {
					status.setDsMotvSitucAgmto("Motivo Situacao: " + status.getDsMotvVstriFruda());
				} else {
					status.setDsMotvSitucAgmto("Motivo Situacao: Não Informado");
				}
			});
		}
	}

	private List<VistoriaObrigatoriaDTO> obterVistoriaPorFiltroAgendar(VistoriaFiltro filtro) {
		List<VistoriaObrigatoriaDTO> vistorias;
		vistorias = recuperarListaItemAgendar(filtro);
		aplicarLinksAgendar(vistorias);
		return vistorias;
	}

	private List<VistoriaObrigatoriaDTO> obterVistoriaPorFiltroTodas(VistoriaFiltro filtro) {
		List<VistoriaObrigatoriaDTO> vistorias;

		vistorias = obterVistoriaPorFiltroAgendar(filtro);

		List<VistoriaObrigatoriaDTO> agendadas = obterVistoriaPorFiltroAgendada(filtro);

		vistorias.addAll(agendadas);
		return vistorias;
	}

	/**
	 * Recupera lista de frota com outros agendamentos do cliente.
	 * @param cpfCnpj 
	 * @param corretor 
	 * @param endosso 
	 * @param negocio 
	 * @return
	 * @throws BindException
	 */
	public List<VistoriaObrigatoriaDTO> obterVistoriasFrota(String cpfCnpj, Long corretor, Long endosso, Long negocio) throws BindException{
		VistoriaFiltro filtro = new VistoriaFiltro();
		filtro.setTipo(TipoVeiculo.F);
		filtro.setCpfCnpj(cpfCnpj);
		filtro.setCorretor(corretor);
		filtro.setDataDe(Timestamp.from(Instant.now().minus(90, ChronoUnit.DAYS)));
		filtro.setDataAte(new Date());
		
		if(endosso != null){
			filtro.setEndosso(endosso);
		} else {
			filtro.setNegocio(negocio);
		}

		ajustarHorario(filtro);
		
		validarFiltroFrota(filtro);
		
		return obterVistorias(filtro);
	}
	
	private boolean isTodas(VistoriaFiltro filtro) {
		return filtro.getSituacao() == null && StringUtils.isBlank(filtro.getVoucher());
	}

	private boolean isAgendar(VistoriaFiltro filtro) {
		return SituacaoAgendamentoFiltro.COD_SITUC_A_AGENDAR == filtro.getSituacao()
				&& StringUtils.isBlank(filtro.getVoucher());
	}

	private List<VistoriaObrigatoriaDTO> recuperarListaItemAgendadas(VistoriaFiltro filtro) {
		return vistoriaRepository.recuperarListaItemAgendada(filtro);
	}

	private void aplicarLinksAgendar(List<VistoriaObrigatoriaDTO> vistorias) {
		vistorias.stream().forEach(v -> vistoriaRU.linkToAgendar(v));
	}

	private void aplicarLinksAgendada(List<VistoriaObrigatoriaDTO> vistorias) {
		Long alcadaCancelaAgendamento = parametroVPService.obterAlcadaCancelaAgendamento();
		Long codigoAlcada = usuarioAlcadaService.buscarCodigoAlcada(usuarioLogado.getUsuarioId()).orElse(0L);

		vistorias.stream().forEach(v -> {

			vistoriaRU.linkToVisualizar(v);

			StatusAgendamentoDTO statusAgendamentoDTO = v.getAgendamento().getStatus();

			if (statusAgendamentoDTO.isReagendamento()) {
				// * Remove da lista o item se data de agendamento na VPE0425_AGEND_VSPRE for
				// menor que data minima
				vistoriaRU.linkToAgendar(v);
			}

			// * Recupera flag que permite cancelar agendamento
			boolean exibirBotaoCancelar =  false;
			
			if(!v.getAgendamento().getTpVspre().getCodigo().equals("M")){
				exibirBotaoCancelar = statusAgendamentoDTO.permitirCancelamento(codigoAlcada,
						alcadaCancelaAgendamento);
			}
			
			if(v.getAgendamento().getTpVspre().getCodigo().equals("M") && v.getAgendamento().getStatus().permitirCancelamentoMobile(codigoAlcada)) {
				exibirBotaoCancelar = true;
			}
			
					
			if ( exibirBotaoCancelar) {
				vistoriaRU.linkToCancelar(v);
			}

		});
	}
	
	public Page<VistoriaObrigatoriaDTO> obterVistoriasObrigatorias(VistoriaFiltro filtro, Pageable page)
			throws BindException {

		validarFiltro(filtro);

		// * Se Filtro: Situação Vistoria = Todas
		if (isTodas(filtro)) {
			return this.recuperarListaItem(filtro, page);

			// * Se Filtro: Situação Vistoria = Agendar
		} else if (SituacaoAgendamentoFiltro.COD_SITUC_PENDENTE == filtro.getSituacao()
				&& StringUtils.isBlank(filtro.getVoucher())) {
			return this.recuperarListaItemAgendar(filtro, page);

		} else {
			return this.recuperarListaItemAgendada(filtro, page);
		}
	}

	private void ajustarHorario(VistoriaFiltro filtro) {
		if (filtro.getDataDe() != null) {
			filtro.setDataDe(DateUtils.truncate(filtro.getDataDe(), Calendar.DATE));
		}

		if (filtro.getDataAte() != null) {
			Calendar dataAte = Calendar.getInstance();
			dataAte.clear();
			dataAte.setLenient(false);
			dataAte.setTime(filtro.getDataAte());
			dataAte.set(Calendar.HOUR_OF_DAY, 23);
			dataAte.set(Calendar.MINUTE, 59);
			dataAte.set(Calendar.SECOND, 59);
			dataAte.set(Calendar.MILLISECOND, 999);
			filtro.setDataAte(dataAte.getTime());
		}
	}

	private void validarFiltroFrota(VistoriaFiltro filtro) throws BindException {
		BindException bindException = new BindException(filtro, "filtro");

		if (isBlank(filtro.getCpfCnpj())) {
			addNull(bindException, cpfCnpj);
		} 
		
		if (isNull(filtro.getCorretor())) {
			addNull(bindException, corretor);
		}
		
		if (isNull(filtro.getEndosso()) && isNull(filtro.getNegocio())) {
			addNull(bindException, endosso);
		}
	
		if (bindException.hasErrors()) {
			throw bindException;
		}
	}

	private void addNull(BindException bindException, String field) {
		bindException.rejectValue(field, null, messageUtil.get("error.required.field", field));
	}

	private void validarFiltro(VistoriaFiltro filtro) throws BindException {
		BindException bindException = new BindException(filtro, "filtro");
		
		if (filtro.getDataDe() == null && filtro.getDataAte() != null) {
			addNull(bindException, dataDe);
			
		} else if (filtro.getDataAte() == null && filtro.getDataDe() != null) {
			addNull(bindException, dataAte);
			
		} else if (isNecessarioData(filtro)) {
			addNull(bindException, dataDe);
			addNull(bindException, dataAte);
			
		} else if (filtro.getDataDe() != null) {
			
			if (filtro.getDataDe().after(filtro.getDataAte())) {
				bindException.rejectValue(dataDe, null,
						"Campo " + dataDe + " deve ser inferior ao campo " + dataAte + " .");
			}
			
			if (filtro.getDataDe().toInstant().plus(31l, DAYS).isBefore(filtro.getDataAte().toInstant())) {
				bindException.rejectValue(dataDe, null, "Periodo de pesquisa não deve ultrapassar 30 dias.");
			}
		}
		
		if (bindException.hasErrors()) {
			throw bindException;
		}
	}

	private boolean isNecessarioData(VistoriaFiltro filtro) {
		return (filtro.getDataDe() == null && 
			isBlank(filtro.getCpfCnpj())
			&& isBlank(filtro.getVoucher())
			&& isBlank(filtro.getPlaca())
			&& isBlank(filtro.getChassi())
			&& null == filtro.getNegocio()
			&& null == filtro.getItem()
			&& null == filtro.getEndosso()
			&& null == filtro.getCalculo());
	}

	public List<VistoriaObrigatoriaDTO> recuperarListaItemAgendar(VistoriaFiltro filtro) {
		return vistoriaRepository.recuperarListaItemAgendar(filtro);
	}

	public Page<VistoriaObrigatoriaDTO> recuperarListaItemAgendar(VistoriaFiltro filtro, Pageable page) {
		return vistoriaRepository.recuperarListaItemAgendar(filtro, page);
	}

	public Page<VistoriaObrigatoriaDTO> recuperarListaItemAgendada(VistoriaFiltro filtro, Pageable page) {
		return vistoriaRepository.recuperarListaItemAgendada(filtro, page);
	}

	public Page<VistoriaObrigatoriaDTO> recuperarListaItem(VistoriaFiltro filtro, Pageable page) {

		Page<VistoriaObrigatoriaDTO> itensAgendar = recuperarListaItemAgendar(filtro, page);

		if (itensAgendar.getNumberOfElements() < page.getPageSize()) {
			int paginaAtual = (page.getPageNumber() + 1) - itensAgendar.getTotalPages();
			Page<VistoriaObrigatoriaDTO> itensAgendada = recuperarListaItemAgendada(filtro,
					PageRequest.of(paginaAtual, page.getPageSize()));

			long total = itensAgendar.getTotalElements() + itensAgendada.getTotalElements();

			List<VistoriaObrigatoriaDTO> itens = new ArrayList<>();
			itens.addAll(itensAgendar.getContent());
			itens.addAll(itensAgendada.getContent());
			List<VistoriaObrigatoriaDTO> subList = itens.subList(0,
					(itens.size() < page.getPageSize() ? itens.size() : page.getPageSize()));

			return new PageImpl<>(subList, page, total);

		} else {
			Long countListaItemAgendada = vistoriaRepository.countListaItemAgendada(filtro);
			long total = itensAgendar.getTotalElements() + countListaItemAgendada;

			return new PageImpl<>(itensAgendar.getContent(), page, total);
		}
	}
	
	public Optional<VistoriaPreviaObrigatoria> findById(Long idVistoria) {
		return vistoriaRepository.findById(idVistoria);
	}

	public VistoriaObrigatoriaDTO buscarVistoria(Long idVistoria) {
		VistoriaObrigatoriaDTO dto = findById(idVistoria).map(v -> vistoriaRU.toDto(v).orElse(null)).orElseThrow(NoContentException::new);

		// Dados do corretor
		Corretor corretor = corretorService.obterCorretor(dto.getCdCrtorCia())
				.orElseThrow(() -> new ExternalServerException("Erro ao consultar dados cadastrais do Corretor: " + dto.getCdCrtorCia()));
		dto.setCorretor(corretor);
		
		addEmailCliente(dto);
		addTelefoneCliente(dto);

		return dto;
	}

	private void addTelefoneCliente(VistoriaObrigatoriaDTO dto) {
		if (dto != null && StringUtils.isNotBlank(dto.getCdVouch())) {
			Optional<String> telefones = agendamentoContatoTelefoneService.findContatosTelefonesSegurado(dto.getCdVouch());
			dto.setTelefoneCliente(telefones.orElse(null));
		}
	}

	private void addEmailCliente(final VistoriaObrigatoriaDTO dto) {
		if (AmbienteEnum.PRODUCAO.getValue().equals(ambiente)) {
			String nrCpfCnpjClien = dto.getNrCpfCnpjClien();
			
			LOGGER.info("[addEmailCliente] início consulta de cliente: " + nrCpfCnpjClien);
			
			clientePessoaRestClient
			.findClientByCnpjCpf(new Long(nrCpfCnpjClien), CpfCnpjUtils.getTipo(nrCpfCnpjClien, "F", "J"))
			.stream()
			.findFirst()
			.map(ClientePessoa::getDsEmail)
			.ifPresent(dto::setEmailCliente);

			LOGGER.info("[addEmailCliente] email cliente: " + dto.getEmailCliente());
			LOGGER.info("[addEmailCliente] fim consulta de cliente: " + nrCpfCnpjClien);
		}
	}

	public void updateVoucherPorId(Long id, String codVoucher) {
		vistoriaRepository.updateVoucherPorId(id, codVoucher);
	}
	
	public Optional<VistoriaPreviaObrigatoria> obterPreAgendamentoPorVoucher(String cdVouch) {
		return vistoriaRepository.findFirstByCdVouchOrderByIdVspreObgtaDesc(cdVouch);
	}

	public Optional<VistoriaPreviaObrigatoria> obterPreAgendamentoPorCalculo(Long nrCallo) {
		return vistoriaRepository.findFirstByNrCalloOrderByIdVspreObgtaDesc(nrCallo);
	}

	public List<String> obterVoucherMesmoVeiculo(String cdPlacaVeicu,String cdChassiVeicu,Long cdCrtorCia){
		Boolean placaImpeditiva = placaImpeditivaService.isPlacaImpeditiva(cdPlacaVeicu);
		int qtidadeDiasRetrocessoAproveitaPreAg = parametroVPService.obterQtdDiasRetrocessoAgendamento();
		return vistoriaRepository.obterVoucherMesmoVeiculo(cdPlacaVeicu, cdChassiVeicu, cdCrtorCia, placaImpeditiva, qtidadeDiasRetrocessoAproveitaPreAg);
	}
	
	public String obterMotivoVPPorItemEndosso(final Long nrItemSegurado, final Long codigoEndosso) {

		List<VistoriaPreviaObrigatoria> listVistoriaPreviaObrigatoria = null;

		if (nrItemSegurado != null && codigoEndosso != null) {
			listVistoriaPreviaObrigatoria = vistoriaRepository
					.findObterPreAgendamentoPorItemSeguradoEndosso(nrItemSegurado, codigoEndosso);
		} else if (nrItemSegurado != null) {
			listVistoriaPreviaObrigatoria = vistoriaRepository
					.findObterPreAgendamentoPorItemSegurado(nrItemSegurado);
		}

		if (CollectionUtils.isNotEmpty(listVistoriaPreviaObrigatoria)) {

			// recupera o ultimo pré-agendamento gerado.
			VistoriaPreviaObrigatoria vistoriaPreviaObrigatoria = listVistoriaPreviaObrigatoria.get(0);

			Optional<MotivoVistoriaPreviaObrigatoria> motivoVistoriaPreviaObrigatoria = motivoVistoriaPreviaObrigatoriaRepository
					.findById(vistoriaPreviaObrigatoria.getIdVspreObgta());

			if (motivoVistoriaPreviaObrigatoria.isPresent()) {
				return motivoVistoriaPreviaObrigatoria.get().getDsMotvVspreObgta();
			}
		}

		return "";
	}
	
	public List<DadosEmailSegurado> carregaDadosEmailSegurado(String parametroCobranca) {
		List<DadosEmailSegurado> retorno = new ArrayList<>();
		Date dataValida = obterDataValida(parametroCobranca);
		List<Object[]> dados = vistoriaRepository.emailsSolicitaAgendamentos(dataValida);
		String[] codCorretoresExcluidos = parametroService.findByPorNome("LISTA_EXEC_CORRETOR_PEN_AGD").split(",");

		final Iterator<Object[]> iterator = dados.listIterator();
		
		String[] email = null;
		
		if(!ambiente.equals(AmbienteEnum.PRODUCAO.getValue())) {
			email = parametroService.findByPorNome("EMAILS_MONITORACAO_ERROS_INESPERADOS").split(",");
		}
		
		while(iterator.hasNext()) {
			
			DadosEmailSegurado dado = getDadosEmail(email, iterator.next());

            boolean insere = true;
            
			for(String cod : codCorretoresExcluidos) {
				if(dado.getCodCorretor().equals(cod)) {
				    insere = false;
				}
			}
			
			if(insere) {
				retorno.add(dado);
			}
		}
		
		return retorno;
	}

	private DadosEmailSegurado getDadosEmail(String[] email, final Object[] itemList) {
		String endosso="";
		String nome =itemList[0] == null ? "": itemList[0].toString();
		
		if(itemList[2] != null && new Long(itemList[2].toString()) > 0L) {
			 endosso = itemList[2].toString();
		}
		
		String chassi = itemList[3] == null ? "":itemList[3].toString();
		String placa = itemList[4] == null ? "":itemList[4].toString();
		String proposta = itemList[5] == null ? "":itemList[5].toString();
		String ddd = itemList[6] == null ? "":itemList[6].toString();
		String telefone = itemList[7] == null ? "":itemList[7].toString();
		String tipo = itemList[8] == null ? "":itemList[8].toString();			
		String emailCliente = email !=null ? email[0] : itemList[9].toString();			
		String codCorretor = itemList[10] == null ? "":itemList[10].toString();
		String cpfCnpj = itemList[11] == null ? "":itemList[11].toString();
		String fabricante = itemList[12] == null ? "":itemList[12].toString();
		String modelo = itemList[13] == null ? "":itemList[13].toString();
		
		DadosEmailSegurado dado = new DadosEmailSegurado();
		dado.setNomeCliente(nome);
		dado.setItemSegurado(fabricante + " - "  +  modelo); // descrição do veiculo
		dado.setCodChassi(chassi);
		dado.setPlacaVeiculo(placa);
		dado.setProposta(String.valueOf(proposta));
		dado.setEndosso(String.valueOf(endosso));
		dado.setDddTelCliente(ddd);
		dado.setTelefoneCliente(telefone);
		dado.setTipo(tipo);
		dado.setEmailCliente(emailCliente);
		dado.setCodCorretor(codCorretor);
		dado.setCpfCnpj(cpfCnpj);
		
		return dado;
	}

	private Date obterDataValida(String parametroCobranca) {
		Date dataAmbiente= null;
		
		if(AmbienteEnum.PRODUCAO.getValue().equals(ambiente)){
			dataAmbiente = new Date();
		} else {
			 String dataParametro = parametroService.findByPorNome("DATA_SIMULACAO_TESTE_EMAIL");
			 dataAmbiente = DateUtil.parseData(dataParametro);
		}
		
		return this.calculaData(dataAmbiente, parametroCobranca);
	}

	public EmailGNT preparaEmailGNT(DadosEmailSegurado dado, String template) throws Exception {
		SimpleDateFormat sdf = null;				
		EmailGNT emailGNT = null;
		
		try {
			
			sdf = new SimpleDateFormat("dd/MM/yyyy");				
			emailGNT = new EmailGNT(template, "CORRETOR", "N");
			
			/* Adciona os parametros padrão */
			emailGNT.addOrReplaceParam(dado.getNomeCliente(), "NOME_PROPONENTE");
			emailGNT.addOrReplaceParam(dado.getItemSegurado(), "ITEM");
			emailGNT.addOrReplaceParam(dado.getPlacaVeiculo(), "PLACA");
			emailGNT.addOrReplaceParam(dado.getCodChassi(), "CHASSI");
			emailGNT.addOrReplaceParam(dado.getEndosso() !=null && !"".equals(dado.getEndosso()) ? dado.getEndosso()  : dado.getProposta(), "COD_PROPOSTA");
			emailGNT.addOrReplaceParam(sdf.format(new Date()), "DT_EMISSAO");
			
			if(template.equals("EMACTPROP0009") ) {
				emailGNT.addOrReplaceParam("1", "NUM_COMUNICADO");
			}else {
				emailGNT.addOrReplaceParam("2", "NUM_COMUNICADO");
			}
			
			if(!ambiente.equals(AmbienteEnum.PRODUCAO.getValue())){ 	
				ConsultaCorretorResponse corretorResponse = acselXRestClient.consultaCorretor(Long.parseLong(dado.getCodCorretor()));
				Random random = SecureRandom.getInstanceStrong();  
				Integer numbercp = random.nextInt(2);
				String[] email = parametroService.findByPorNome("EMAILS_MONITORACAO_ERROS_INESPERADOS").split(","); 	
				/* Set destinatário cliente email */
				emailGNT.setDestinatariosDetalhes(java.util.Arrays.asList(new DestinatarioDetalhes(email[2],dado.getCpfCnpj())));						
				emailGNT.addComCopia(email[numbercp]);	
				emailGNT.addOrReplaceParam(corretorResponse.getPo_reg().get(0).getNomter(), NOME_CORRETOR);						
				emailGNT.addOrReplaceParam(this.formataMascaraTelefone(corretorResponse),TELEFONE_CORRET);

			} else  {
				
				/* Set destinatário cliente email */
				ConsultaCorretorResponse corretorResponse = acselXRestClient.consultaCorretor(Long.parseLong(dado.getCodCorretor()));
				
				LOGGER.info("Dados do corretor: " + dado.getCodCorretor() + " - Retorno Consulta Corretor:" + corretorResponse.getPo_mensagem().getStatus_retorno() + "chassi" + dado.getCodChassi());
				
				if(corretorResponse.getPo_mensagem().getStatus_retorno().equals(0L) ){ // retonro 0 sucesso - 3 não localizado
					
					emailGNT.setDestinatariosDetalhes(this.recuperarEmailContato(dado));
					emailGNT.addOrReplaceParam(corretorResponse.getPo_reg().get(0).getNomter(), NOME_CORRETOR);
					
					emailGNT.addOrReplaceParam(this.formataMascaraTelefone(corretorResponse),TELEFONE_CORRET);
					
				} else {
					LOGGER.info("Dados do corretor:" + dado.getCodCorretor() + " não localizado, retorno :"  + corretorResponse.getPo_mensagem().getStatus_retorno() + "mensagem:" + corretorResponse.getPo_mensagem().getMensagem_retorno());
				}
				
			}			
		} catch (Exception e) {
			LOGGER.error("Erro ao preparar envio do e-mail:" + e.getMessage());
			throw e;
		}
					
		return emailGNT;
	}
	
	private List<DestinatarioDetalhes> recuperarEmailContato(DadosEmailSegurado dadosEmail) {
		List<DestinatarioDetalhes> lisatDestinatarioDetalhes = new ArrayList<>();
		lisatDestinatarioDetalhes.add(new DestinatarioDetalhes(dadosEmail.getEmailCliente(), dadosEmail.getCpfCnpj()));

		/* busca dado do corretor pelo codigo */
		ConsultaEmailCorretorResponse result = acselXRestClient
				.consultaEmailVistoriaPrevia(new Long(dadosEmail.getCodCorretor()));

		try {

			if (result == null || !result.getPo_mensagem().getStatus_retorno().equals(0L)) {

				LOGGER.info("Não localizado email de VISTORIA PREVIA para o corretor:" + dadosEmail.getCodCorretor());

				result = acselXRestClient.consultaEmailOperacional(new Long(dadosEmail.getCodCorretor()));
				LOGGER.info("Localizado email de OPERACIONAL para o corretor:" + dadosEmail.getCodCorretor());

			} else {
				LOGGER.info("Localizado email VISTORIA PREVIA para o corretor:" + dadosEmail.getCodCorretor()
						+ " será enviado e-mail de contato para este corretor!");
			}

		} catch (Exception e) {
			LOGGER.info("Erro ao consultar email operacional:" + e.getStackTrace());
		}

		if (result == null) {
			throw new InternalServerException(messageUtil.get(""));
		}

		result.getPo_reg().stream().forEach(emailCorretor -> lisatDestinatarioDetalhes
				.add(new DestinatarioDetalhes(emailCorretor.getEmail(), null)));

		return lisatDestinatarioDetalhes;
	}
	
	public EmailGNT preparaWwpGNT (DadosEmailSegurado dadoWp, String templateWWP) throws Exception {
		EmailGNT emailGNT = new EmailGNT(templateWWP, "CORRETOR", "N");
		
		/* Adciona os parametros padrão */ 
		emailGNT.addOrReplaceParam(dadoWp.getNomeCliente(), "NOME_PROPONENTE");
		emailGNT.addOrReplaceParam(dadoWp.getItemSegurado(), "ITEM");
		emailGNT.addOrReplaceParam(dadoWp.getPlacaVeiculo(), "PLACA");
		emailGNT.addOrReplaceParam(dadoWp.getCodChassi(), "CHASSI");
		emailGNT.addOrReplaceParam(dadoWp.getEndosso() !=null && !"".equals(dadoWp.getEndosso()) ? dadoWp.getEndosso() : dadoWp.getProposta(), "COD_PROPOSTA");
		
		if(!ambiente.equals(AmbienteEnum.PRODUCAO.getValue())) {
			ConsultaCorretorResponse corretorResponse = acselXRestClient.consultaCorretor(Long.parseLong(dadoWp.getCodCorretor()));
			
			/* Set destinatário cliente email numero de telefone e nome */
			String cellPhoneTest = parametroService.findByPorNome("CELULAR_AMBIENTE_TESTE_GNT");
			emailGNT.setDestinatariosDetalhes(java.util.Arrays.asList(new DestinatarioDetalhes(cellPhoneTest, dadoWp.getCpfCnpj())));
			emailGNT.addOrReplaceParam(corretorResponse.getPo_reg().get(0).getNomter(), NOME_CORRETOR);	
			emailGNT.addOrReplaceParam(this.formataMascaraTelefone(corretorResponse),TELEFONE_CORRET);
			
		} else {
			ConsultaCorretorResponse corretorResponse = acselXRestClient.consultaCorretor(Long.parseLong(dadoWp.getCodCorretor()));
			
			/* Set destinatário cliente email */
			emailGNT.setDestinatariosDetalhes(java.util.Arrays.asList(new DestinatarioDetalhes(dadoWp.getDddTelCliente() + dadoWp.getTelefoneCliente(), dadoWp.getCpfCnpj())));
			emailGNT.addOrReplaceParam(corretorResponse.getPo_reg().get(0).getNomter(), NOME_CORRETOR);
			emailGNT.addOrReplaceParam(this.formataMascaraTelefone(corretorResponse),TELEFONE_CORRET);
		}

		return emailGNT;
	}
	
	public ResponseEntity<EmailGNTResponse> emailContato1() throws Exception {
		LOGGER.info("Iniciando processo de Email de Primeiro contato...");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(java.util.Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);     
		
		//obter codigo do template do primeiro envio 

		String template = parametroService.findByPorNome("TEMPLATE_EMAIL_1_COB_PEN_AGENDAMENTO_GNT");
		
		//obter Oarametro de cobrança

		String parametroCobranca =  parametroService.findByPorNome("QTS_COBRANCA_1_ENVIO");
		
		//Obter serviço de envio de email URL

		String urlWServicoEmail = parametroService.findByPorNome("URL_SERVICO_GNT");

		//Carrega a lista de todos os clientes que serão notificados
		
		List<DadosEmailSegurado> dadosSegurado = this.carregaDadosEmailSegurado(parametroCobranca);
		LOGGER.info("Quantidade total de clientes que serão comunicados; " + dadosSegurado.size());
		count = 0l;
		for(DadosEmailSegurado obj : dadosSegurado ) {
			count++;
			
			try {
				enviarEmail(headers, template, urlWServicoEmail, obj);				
				
			} catch (Exception e) {
				enviarEmailErro(headers, obj, e);
				
				continue;
			}			
			
			LOGGER.info("======================================================================================");
		}
		
		LOGGER.info("Quantidade de Registros enviados:"+ count);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void enviarEmailErro(HttpHeaders headers, DadosEmailSegurado obj, Exception e) {
		LOGGER.info(" Erro no envio do e-mail da proposta:" + obj.getProposta() + " - err:" + e.getMessage());
		
		RequestLogAceitacao requestLogAceitacao = new RequestLogAceitacao();
		requestLogAceitacao.setCodigoEndosso(obj.getEndosso() !=null && !"".equals(obj.getEndosso()) ? new Long(obj.getEndosso()):0L);
		requestLogAceitacao.setCodigoNegocio(obj.getProposta() !=null && !"".equals(obj.getProposta()) ? new Long(obj.getProposta()):0L);
		requestLogAceitacao.setLogPendente("N");
		requestLogAceitacao.setTextoLog("[Cobrança Agendamento - Primeiro Contato] Não Enviada...");
		requestLogAceitacao.setUsuarioCadastramento(USUARIO_CADASTRAMENTO);
		HttpEntity<RequestLogAceitacao> requestEntityLog = new HttpEntity<>(requestLogAceitacao, headers);
		restTemplate.exchange(logUrl, HttpMethod.POST, requestEntityLog, RequestLogAceitacao.class);
	}

	private void enviarEmail(HttpHeaders headers, String template, String urlWServicoEmail, DadosEmailSegurado obj)
			throws Exception {
		LOGGER.info("======================================================================================");
		LOGGER.info("Preparando email envio de email da proposta:" + obj.getProposta());
		EmailGNT gnt = this.preparaEmailGNT(obj, template);
		
		HttpEntity<EmailGNT> requestEntity = new HttpEntity<>(gnt, headers);

		ResponseEntity<EmailGNTResponse> emailGNTResponse = restTemplate.exchange(urlWServicoEmail.concat(ENVIA_S), HttpMethod.POST, requestEntity, EmailGNTResponse.class);
		
		if(emailGNTResponse.getBody().getCodigoRetorno() == 0){

			LOGGER.info(" Email da chassi:" + obj.getCodChassi() + " enviado com sucesso! seq:" + emailGNTResponse.getBody().getSeqAgendamento());
			
			RequestLogAceitacao requestLogAceitacao = new RequestLogAceitacao();
			requestLogAceitacao.setCodigoEndosso(obj.getEndosso() !=null && !"".equals(obj.getEndosso()) ? new Long(obj.getEndosso()):0L);
			requestLogAceitacao.setCodigoNegocio(obj.getProposta() !=null && !"".equals(obj.getProposta()) ? new Long(obj.getProposta()):0L);
			requestLogAceitacao.setLogPendente("N");
			requestLogAceitacao.setTextoLog("[Cobrança Agendamento - Primeiro Contato] Seq: " + emailGNTResponse.getBody().getSeqAgendamento());
			requestLogAceitacao.setUsuarioCadastramento(USUARIO_CADASTRAMENTO);
			
			LOGGER.info("Gerando log de aceitação da chassi:" + obj.getCodChassi());
			
			HttpEntity<RequestLogAceitacao> requestEntityLog = new HttpEntity<>(requestLogAceitacao, headers);
			restTemplate.exchange(logUrl, HttpMethod.POST, requestEntityLog, Object.class);
		
			LOGGER.info(" Log de aceitação da chassi:" + obj.getCodChassi() + " enviado com sucesso!");
			
		} else {					
			logEmailNaoEnviado(obj, emailGNTResponse);
		}
	}
	

	public ResponseEntity<EmailGNTResponse> emailContato2() throws Exception {
		count = 0l;

		LOGGER.info("Iniciando processo de Email de Segundo contato...");
		//obter codigo do template do primeiro envio 
		String template =  parametroService.findByPorNome("TEMPLATE_EMAIL_2_COB_PEN_AGENDAMENTO_GNT");
		
		String templateWPP =  parametroService.findByPorNome("TEMPLATE_WHATSAPP_COB_PEN_AGENDAMENTO_GNT");
		
		//obter Oarametro de cobrança
		String parametroCobranca =  parametroService.findByPorNome("QTS_COBRANCA_2_ENVIO");
		
		//Obter serviço de envio de email URL
		String urlWServicoEmail = parametroService.findByPorNome("URL_SERVICO_GNT");
		
		//Carrega a lista de todos os clientes que serão notificados
		List<DadosEmailSegurado> dadosSegurado = this.carregaDadosEmailSegurado(parametroCobranca);
		LOGGER.info("Quantidade total de clientes que serão comunicados; " + dadosSegurado.size());
		for(DadosEmailSegurado obj : dadosSegurado ) {
			
			try {	
				LOGGER.info("Preparando email envio de email da proposta:" + obj.getProposta());
				
				count++;
				EmailGNT emailGNTRequest = this.preparaEmailGNT(obj, template);
				EmailGNT wwpGNTRequest = this.preparaWwpGNT(obj, templateWPP);
				
				this.enviaEmailSegundoCotato(obj, urlWServicoEmail, emailGNTRequest);
				LOGGER.info("email enviado com sucesso da proposta:" + obj.getProposta());
				
				this.enviaWhatsAppSegundoContato(obj, urlWServicoEmail, wwpGNTRequest);
				LOGGER.info("whatsapp enviado com sucesso da proposta:" + obj.getProposta());
			
			} catch (Exception e) {
				LOGGER.info(" Erro no envio do e-mail da proposta:" + e.getStackTrace());
			}		
		}
		
		LOGGER.info("Quantidade de Registros enviados:"+ count);
		return new ResponseEntity<>(HttpStatus.OK);	
	}

	private void enviaWhatsAppSegundoContato(DadosEmailSegurado obj, String urlWServicoEmail, EmailGNT wwpGNTRequest) {
	
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(java.util.Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON); 		
		
		try {
			       
			HttpEntity<EmailGNT> requestEntityWWP = new HttpEntity<>(wwpGNTRequest, headers);
			ResponseEntity<EmailGNTResponse> emailGNTResponse = restTemplate.exchange(urlWServicoEmail.concat(ENVIA_S), HttpMethod.POST, requestEntityWWP, EmailGNTResponse.class);
			
			if(emailGNTResponse.getBody().getCodigoRetorno() == 0){
				RequestLogAceitacao requestLogAceitacao = new RequestLogAceitacao();
				requestLogAceitacao.setCodigoEndosso(obj.getEndosso() !=null && !"".equals(obj.getEndosso()) ? new Long(obj.getEndosso()):0L);
				requestLogAceitacao.setCodigoNegocio(obj.getProposta() !=null && !"".equals(obj.getProposta()) ? new Long(obj.getProposta()):0L);
				requestLogAceitacao.setLogPendente("N");
				requestLogAceitacao.setTextoLog("[Cobrança Agendamento Via Whatsapp - segundo contato Contato] Seq: " + emailGNTResponse.getBody().getSeqAgendamento());
				requestLogAceitacao.setUsuarioCadastramento(USUARIO_CADASTRAMENTO);
				
				HttpEntity<RequestLogAceitacao> requestEntityLog = new HttpEntity<>(requestLogAceitacao, headers);
				restTemplate.exchange(logUrl, HttpMethod.POST, requestEntityLog, RequestLogAceitacao.class);
				
			} else {
				logEmailNaoEnviado(obj, emailGNTResponse);
			}

		} catch (Exception e) {
			
			RequestLogAceitacao requestLogAceitacao = new RequestLogAceitacao();
			requestLogAceitacao.setCodigoEndosso(obj.getEndosso() !=null && !"".equals(obj.getEndosso()) ? new Long(obj.getEndosso()):0L);
			requestLogAceitacao.setCodigoNegocio(obj.getProposta() !=null && !"".equals(obj.getProposta()) ? new Long(obj.getProposta()):0L);
			requestLogAceitacao.setLogPendente("N");
			requestLogAceitacao.setTextoLog("[Cobrança Agendamento Agendamento Via Whatsapp - Primeiro Contato] Não Enviada...");
			requestLogAceitacao.setUsuarioCadastramento(USUARIO_CADASTRAMENTO);
			
			HttpEntity<RequestLogAceitacao> requestEntityLog = new HttpEntity<>(requestLogAceitacao, headers);
			restTemplate.exchange(logUrl, HttpMethod.POST, requestEntityLog, RequestLogAceitacao.class);
		}
	}

	private void enviaEmailSegundoCotato(DadosEmailSegurado obj, String urlWServicoEmail, EmailGNT emailGNTRequest) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(java.util.Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON); 			
		
		try {
			
			HttpEntity<EmailGNT> requestEntityEmail = new HttpEntity<>(emailGNTRequest, headers);
			ResponseEntity<EmailGNTResponse> emailGNTResponse = restTemplate.exchange(urlWServicoEmail.concat(ENVIA_S), HttpMethod.POST, requestEntityEmail, EmailGNTResponse.class);
			
			if(emailGNTResponse.getBody().getCodigoRetorno() == 0){

				RequestLogAceitacao requestLogAceitacao = new RequestLogAceitacao();
				requestLogAceitacao.setCodigoEndosso(obj.getEndosso() !=null && !"".equals(obj.getEndosso()) ? new Long(obj.getEndosso()):0L);
				requestLogAceitacao.setCodigoNegocio(obj.getProposta() !=null && !"".equals(obj.getProposta()) ? new Long(obj.getProposta()):0L);
				requestLogAceitacao.setLogPendente("N");
				requestLogAceitacao.setTextoLog("[Cobrança Agendamento - segundo contato Contato] Seq: " + emailGNTResponse.getBody().getSeqAgendamento());
				requestLogAceitacao.setUsuarioCadastramento(USUARIO_CADASTRAMENTO);
				
				HttpEntity<RequestLogAceitacao> requestEntityLog = new HttpEntity<>(requestLogAceitacao, headers);
				restTemplate.exchange(logUrl, HttpMethod.POST, requestEntityLog, RequestLogAceitacao.class);
				
			} else {
				logEmailNaoEnviado(obj, emailGNTResponse);
			}

		} catch (Exception e) {
			
			RequestLogAceitacao requestLogAceitacao = new RequestLogAceitacao();
			requestLogAceitacao.setCodigoEndosso(obj.getEndosso() !=null && !"".equals(obj.getEndosso()) ? new Long(obj.getEndosso()):0L);
			requestLogAceitacao.setCodigoNegocio(obj.getProposta() !=null && !"".equals(obj.getProposta()) ? new Long(obj.getProposta()):0L);
			requestLogAceitacao.setLogPendente("N");
			requestLogAceitacao.setTextoLog("[Cobrança Agendamento - Primeiro Contato] Não Enviada...");
			requestLogAceitacao.setUsuarioCadastramento(USUARIO_CADASTRAMENTO);
			
			HttpEntity<RequestLogAceitacao> requestEntityLog = new HttpEntity<>(requestLogAceitacao, headers);
			restTemplate.exchange(logUrl, HttpMethod.POST, requestEntityLog, RequestLogAceitacao.class);
		}
	}

	private void logEmailNaoEnviado(DadosEmailSegurado obj, ResponseEntity<EmailGNTResponse> emailGNTResponse) {
		LOGGER.info(" Email não enviado para chassi:" + obj.getCodChassi() + " - codigo retorno:"  +  emailGNTResponse.getBody().getCodigoRetorno() + " - mensagem:" +  emailGNTResponse.getBody().getMensagemRetorno());
	}

	Date calculaData(Date data, String parametro) {
		Date retorno = null;
		
		try {
			
			LOGGER.info("Inicio calculaData");
			retorno = acselXRestClient.calcularDiasUteis(data, Long.parseLong(parametro), -1L);
			LOGGER.info("Fim calculaData");
			
		} catch (Exception e) {
			LOGGER.error("Erro ao calcular data:" + e.getMessage());
			throw new InternalServerException("Erro ao executar metodo calculoData [AcselXRestClient.calculoData]. Erro: ", e);
		}
		
		return retorno;
	}
	
	private String formataMascaraTelefone(ConsultaCorretorResponse consultaCorretorResponse ){
		try {
			
			String numeroDDD;
			String telefone;
			
			numeroDDD = consultaCorretorResponse.getPo_reg().get(0).getDddtelef1() !=null && consultaCorretorResponse.getPo_reg().get(0).getDddtelef1().compareTo(BigDecimal.valueOf(0L)) > 0 ? consultaCorretorResponse.getPo_reg().get(0).getDddtelef1().toString() : "00" ;
			telefone =  consultaCorretorResponse.getPo_reg().get(0).getNumtelef1() !=null && consultaCorretorResponse.getPo_reg().get(0).getNumtelef1().compareTo(BigDecimal.valueOf(0L)) > 0? consultaCorretorResponse.getPo_reg().get(0).getNumtelef1().toString() : " ";
			
			if( (numeroDDD!=null && telefone !=null) && (numeroDDD.length() > 0 && telefone.length() > 0) ){
				
				String formatoMascara = telefone.length() <= 7 ? "(##) ###-####" : "(##) ####-####";
				
				MaskFormatter mf = new MaskFormatter(formatoMascara);
				mf.setValueContainsLiteralCharacters(false);			
				
				return mf.valueToString(numeroDDD+telefone);
				
			} else {
				LOGGER.error("Telefone do corretor nulo!");
				return "";
			}
			
		} catch (Exception e) {
			LOGGER.error("Erro na formatação do telefone");
			return "";			
		}		
	}
	
	public VistoriaPreviaObrigatoria toEntity(VistoriaObrigatoriaDTO vistoriaDto) {
		return vistoriaRU.toEntity(vistoriaDto).get();
	}

	public void detach(final VistoriaPreviaObrigatoria vistoria) {
		vistoriaRepository.detach(vistoria);
		vistoria.setIdVspreObgta(null);
	}

	@Transactional
	public void removerVistoriasInativas(Long cdEndos, Long cdNgoco, Long nrCallo, Set<Long> idsVistoriasAtivas) {
		String chave = "[cdEndos:"+cdEndos+"] [cdNgoco:"+cdNgoco+"] [nrCallo:"+nrCallo+"] ";
		
		Set<Long> ativas;
		
		if (idsVistoriasAtivas != null) {
			ativas = idsVistoriasAtivas;
		} else {
			ativas = new HashSet<>();
		}
		
		LOGGER.info(chave + "Removendo vistorias inativas");
		VistoriaPreviaObrigatoria filtro = new VistoriaPreviaObrigatoria();
		
		if (cdEndos != null && !cdEndos.equals(0L)) {
			filtro.setCdEndos(cdEndos);
		} else if (cdNgoco != null && !cdNgoco.equals(0L)) {
			filtro.setCdNgoco(cdNgoco);
		} else if (nrCallo != null && !nrCallo.equals(0L)) {
			filtro.setNrCallo(nrCallo);
		} else {
			LOGGER.error(chave + "Não há dados para localizar vistorias inativas");
			return;
		}
		
		List<VistoriaPreviaObrigatoria> inativos = vistoriaRepository.findAll(Example.of(filtro))
		.stream()
		.filter(v -> !ativas.contains(v.getIdVspreObgta()))
		.filter(v -> StringUtils.isBlank(v.getCdVouch()))
		.collect(Collectors.toList());
		
		if (!inativos.isEmpty()) {
			LOGGER.info(chave + "Removendo vistorias: " + gson.toJson(inativos));
			
			vistoriaRepository.deleteAll(inativos);
			
			LOGGER.info(chave + "Vistorias removidas");
		} else {
			LOGGER.info(chave + "Nenhuma vistoria removida");
		}
		
	}
}
