package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.StatusAgendamentoAgrupamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.StatusAgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoCancelamentoEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.StatusAgendamentoAgrupamentoRepository;

@Service
public class StatusAgendamentoService {

	private static final Logger LOGGER = LogManager.getLogger(StatusAgendamentoService.class);
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private StatusAgendamentoAgrupamentoRepository statusAgendamentoAgrupamentoRepository;
	
	public Optional<StatusAgendamentoDTO> obterStatusAtualPorVoucher(String voucher) {
		return obterStatusAgendamento(voucher)
				.map(s -> mapper.map(s, StatusAgendamentoDTO.class));
	}

	public Optional<StatusAgendamentoAgrupamento> obterStatusAgendamento(String voucher) {
		return statusAgendamentoAgrupamentoRepository.findStatusAtualPorVoucher(voucher);
	}

	public Optional<StatusAgendamentoDTO> obterStatusRecentePorVoucherSituacao(String voucher,
			SituacaoAgendamento situacao) {
		return statusAgendamentoAgrupamentoRepository.findStatusRecentePorVoucherSituacao(voucher, situacao.name())
				.map(s -> mapper.map(s, StatusAgendamentoDTO.class));
	}

	public List<StatusAgendamentoDTO> obterStatusPorVoucher(String voucher) {
		return statusAgendamentoAgrupamentoRepository
				.findAllByCdVouchOrderByIdStatuAgmtoDesc(voucher)
				.stream()
				.map(s -> mapper.map(s, StatusAgendamentoDTO.class))
				.collect(Collectors.toList());
	}

	public StatusAgendamentoAgrupamento salvar(StatusAgendamentoAgrupamento statusAgto) {
		statusAgto.setDtUltmaAlter(new Date());
		
		LOGGER.info("[StatusAgendamentoService] Salvando novo status: " + new Gson().toJson(statusAgto));
		return statusAgendamentoAgrupamentoRepository.save(statusAgto);
	}

	/**
	 * Cria novo StatusAgendamentoAgrupamento adicionando o usuario logado e a data de ultima alteração
	 * @param cdVouch
	 * @param status
	 * @param CdMotvSitucAgmto 
	 * @return Objeto criado
	 */
	public StatusAgendamentoAgrupamento salvar(String cdVouch, SituacaoAgendamento status, String usuario, Long cdMotvSitucAgmto) {
		StatusAgendamentoAgrupamento statusAgto = new StatusAgendamentoAgrupamento();
		
		statusAgto.setCdVouch(cdVouch);
		statusAgto.setCdSitucAgmto(status.getValor());
		statusAgto.setCdUsuroUltmaAlter(usuario);
		statusAgto.setCdMotvSitucAgmto(cdMotvSitucAgmto);
		statusAgto.setDtUltmaAlter(new Date());
		
		return statusAgendamentoAgrupamentoRepository.save(statusAgto);
	}

	@Transactional
	public StatusAgendamentoAgrupamento salvar(String cdVouch, SituacaoAgendamento status, String usuario) {
		return salvar(cdVouch, status, usuario, null);
	}
	
	public StatusAgendamentoAgrupamento cancelarAgendamento(String cdVouch, String usuario) {
		StatusAgendamentoAgrupamento statusAgto = new StatusAgendamentoAgrupamento();
		
		statusAgto.setCdSitucAgmto(SituacaoAgendamento.CAN.getValor());
		statusAgto.setCdMotvSitucAgmto(7L);
		statusAgto.setCdVouch(cdVouch);
		
		statusAgto.setCdUsuroUltmaAlter(usuario);
		statusAgto.setDtUltmaAlter(new Date());
		
		return statusAgendamentoAgrupamentoRepository.save(statusAgto);
	}
	
	/**
	 * Se o ultimo status for CAN, altera o motivo de cancelamento para CANCELAMENTO_CONFIRMADO(2L)
	 * @param cdVouch
	 */
	@Transactional
	public void confirmarCancelamento(String cdVouch) {
		obterStatusAgendamento(cdVouch)
		.filter(s -> SituacaoAgendamento.CAN.getValor().equals(s.getCdSitucAgmto()))
		.ifPresent(s -> {
			s.setCdMotvSitucAgmto(MotivoCancelamentoEnum.CANCELAMENTO_CONFIRMADO.getValor());
			statusAgendamentoAgrupamentoRepository.save(s);
		});
	}
}
