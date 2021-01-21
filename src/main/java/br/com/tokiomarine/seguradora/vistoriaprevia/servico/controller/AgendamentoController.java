package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AtualizacaoStatusDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PrestadoraDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ResponseAgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.StatusAgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaObrigatoriaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVeiculoLocalAgendamentoEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVistoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NoContentException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NotFoundException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.AgendamentoService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.CancelamentoAutomaticoService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.PreAgendamentoService;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

	private static final Logger LOGGER = LogManager.getLogger(AgendamentoController.class);
	
	@Autowired
	private AgendamentoService agendamentoService;

	@Autowired
	private PreAgendamentoService preAgendamentoService;

	@Autowired
	private CancelamentoAutomaticoService cancelamentoService;

	@GetMapping("/{voucher}/vistoria/{idVistoria}")
	public ResponseEntity<AgendamentoDTO> visualizar(@PathVariable Long idVistoria, @PathVariable String voucher) {
		AgendamentoDTO agendamento = agendamentoService.visualizarAgendamento(idVistoria, voucher);
		return ResponseEntity.ok(agendamento);
	}
	
	@GetMapping("/vistoria/{idVistoria}")
	public ResponseEntity<Object> isPermiteAgendamento(@PathVariable Long idVistoria) {
		LOGGER.info("[AgendamentoController] isPermiteAgendamento");
		agendamentoService.validarNovoAgendamento(idVistoria);
		return ResponseEntity.ok().build();
	}
	
	@CrossOrigin
	@PostMapping
	public ResponseEntity<Object> agendar(@Valid @RequestBody AgendamentoDTO dto) throws BindException {
		List<ResponseAgendamentoDTO> voucher = agendamentoService.createAgendamentoTela(dto);
		return ResponseEntity.ok(voucher);
	}
	
	@CrossOrigin
	@PutMapping("/{voucher}/cancelamento")
	public ResponseEntity<Object> cancelar(@PathVariable String voucher, @RequestBody StatusAgendamentoDTO status,
			BindingResult bindingResult) throws BindException {
		
		if (status.getCdMotvSitucAgmto() == null) {
			bindingResult.rejectValue(StatusAgendamentoDTO.Fields.cdMotvSitucAgmto, null,
					"Motivo do cancelamento não informado.");
			throw new BindException(bindingResult);
		}
		
		status.setCdVouch(voucher);
		
		agendamentoService.cancelarAgendamento(status);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/distribuicao/{idVistoria}/{idRegiao}/{data}")
	public ResponseEntity<Object> obterPrestadorasDistribuicao(@PathVariable Long idVistoria, @PathVariable Long idRegiao,
			@PathVariable String data) {
		try {

			Date dt = DateUtil.parseDataDDMMYYYY(data);

			List<PrestadoraDTO> prestadoras = agendamentoService.obterPrestadorasDistribuicao(idVistoria, idRegiao, dt);

			if (prestadoras.isEmpty())
				throw new NoContentException();

			return ResponseEntity.ok(prestadoras);

		} catch (ParseException e) {
			LOGGER.error("Formato de data inválido: " + data);
			return ResponseEntity.badRequest().build();
		}
	}

//	@NaoAutenticar
	@GetMapping("/cancelamento/sicredi")
	public ResponseEntity<Object> atualizarStatusSicoob() {
		try {
			cancelamentoService.cancelarSicrediMobile();

			return ResponseEntity.ok(0);

		} catch (Exception e) {
			LOGGER.error("[CANCELAMENTO AUTOMATICO] ERRO: " + ExceptionUtils.getRootCause(e).getMessage(), e);

			return ResponseEntity.ok(1);
		}
	}

	@GetMapping("/cancelamento/sicredi/{voucher}/{isCancelar}")
	public ResponseEntity<Object> atualizarStatusSicoob(@PathVariable String voucher, @PathVariable String isCancelar) {
		cancelamentoService.cancelarDomicilioSicredi(voucher, isCancelar.equals("S"));
		return ResponseEntity.ok().build();
	}

	@PostMapping("/cancelamento/sicredi/contingencia/digital")
	public ResponseEntity<Object> atualizarStatus(@RequestBody AtualizacaoStatusDTO novoStatus) {
		agendamentoService.atualizarStatus(novoStatus);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/santander/{cotacao}")
	public ResponseEntity<VistoriaObrigatoriaDTO> recuperarPreAgtoSantander(@PathVariable Long cotacao) {
		return ResponseEntity.ok(preAgendamentoService.recuperarPreAgtoSantander(cotacao));
	}

	@PutMapping("/santander/{cotacao}")
	public ResponseEntity<Optional<VistoriaObrigatoriaDTO>> gravarPreAgtoSantander(@PathVariable Long cotacao, @RequestBody VistoriaObrigatoriaDTO preAgto) {
		return ResponseEntity.ok(preAgendamentoService.gravarPreAgendamentoSantander(cotacao, preAgto));
	}
	
	@GetMapping("/vistoria/{idVistoria}/regiao/{idRegiao}/tipos")
	public ResponseEntity<Set<TipoVistoria>> obterTiposAgendamento(@PathVariable Long idVistoria, @PathVariable Long idRegiao) {
		return ResponseEntity.ok(agendamentoService.obterTiposAgendamento(idVistoria, idRegiao));
	}
	
	@GetMapping("/tipos/cep-vistoria/{cepVistoria}/cep-pernoite/{cepPernoite}/corretor/{codCorretor}/tipo-veiculo/{tipoVeiculo}")
	public ResponseEntity<?> obterTiposAgendamento(@PathVariable String cepVistoria,
			@PathVariable String cepPernoite, @PathVariable Long codCorretor,
			@PathVariable TipoVeiculoLocalAgendamentoEnum tipoVeiculo) {
		Set<String> tiposAgendamento = agendamentoService.obterTiposAgendamento(cepVistoria, cepPernoite, codCorretor,
				tipoVeiculo);

		if (tiposAgendamento.isEmpty()) {
			throw new NotFoundException(
					"No momento não temos prestador disponível nessa cidade para realizar a vistoria prévia. Tente agendar em outra cidade ou consulte sua sucursal.");
		}

		return ResponseEntity.ok(tiposAgendamento);
	}
	
	@GetMapping("/datas/{cidade}")
	public ResponseEntity<?> obterDatasAgendamento(@PathVariable String cidade) {
		return ResponseEntity.ok(agendamentoService.obterDatasAgendamento(cidade));
	}
}
