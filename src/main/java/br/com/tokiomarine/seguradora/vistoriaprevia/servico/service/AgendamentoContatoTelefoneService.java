package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoContatoTelefone;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoTelefoneDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoContatoTelefoneEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.AgendamentoContatoTelefoneRepository;

@Service
public class AgendamentoContatoTelefoneService {

	@Autowired
	private AgendamentoContatoTelefoneRepository agendamentoRepository;

	private Function<? super AgendamentoContatoTelefone, ? extends AgendamentoTelefoneDTO> toAgendamentoTelefone = a -> 
		new AgendamentoTelefoneDTO(
				a.getCdVouch(),
				a.getCdDddTelef(), 
				a.getNmConttVspre(), 
				a.getNrRamal(), 
				a.getNrTelef(),
				a.getTpTelef(),
				a.getTpConttTelef());

	/**
	 * @param voucher
	 * @param tipo
	 * @return
	 */
	public List<AgendamentoContatoTelefone> findAgendamentoContatoTelefone(String voucher,
			TipoContatoTelefoneEnum tipo) {
		return agendamentoRepository.findAgendamentoContatoTelefone(voucher, tipo.name());
	}

	/**
	 * @param voucher
	 * @return
	 */
	public List<AgendamentoContatoTelefone> findAgendamentoContatoTelefoneCorretor(String voucher) {
		return findAgendamentoContatoTelefone(voucher, TipoContatoTelefoneEnum.C);
	}

	/**
	 * @param voucher
	 * @return
	 */
	public List<AgendamentoTelefoneDTO> findContatoTelefoneCorretor(String voucher) {
		return findAgendamentoContatoTelefone(voucher, TipoContatoTelefoneEnum.C).stream()
				.map(toAgendamentoTelefone).collect(Collectors.toList());
	}

	/**
	 * @param voucher
	 * @return
	 */
	public List<AgendamentoContatoTelefone> findAgendamentoContatoTelefoneSegurado(String voucher) {
		return findAgendamentoContatoTelefone(voucher, TipoContatoTelefoneEnum.S);
	}
	
	public Optional<String> findContatosTelefonesSegurado(String voucher) {
		return findAgendamentoContatoTelefoneSegurado(voucher)
				.stream()
				.map(t -> StringUtils.joinWith(" ", t.getCdDddTelef(), t.getNrTelef()))
				.reduce((a, b) -> StringUtils.joinWith(" / ", a, b));
	}

	/**
	 * @param voucher
	 * @return
	 */
	public List<AgendamentoTelefoneDTO> findContatoTelefoneSegurado(String voucher) {
		return findAgendamentoContatoTelefone(voucher, TipoContatoTelefoneEnum.S).stream()
				.map(toAgendamentoTelefone).collect(Collectors.toList());
	}

	public Optional<AgendamentoTelefoneDTO> findUltimoTelefoneSegurado(String voucher) {
		return agendamentoRepository.findFirstByCdVouchAndTpConttTelefOrderByIdAgendConttTelefDesc(voucher, TipoContatoTelefoneEnum.S.name())
				.map(toAgendamentoTelefone);
	}
	
	public Optional<AgendamentoTelefoneDTO> findUltimoTelefoneSeguradoRecado(String voucher) {
		return agendamentoRepository.findFirstByCdVouchAndTpConttTelefAndTpTelefOrderByIdAgendConttTelefDesc(voucher, TipoContatoTelefoneEnum.S.name(), TipoContatoTelefoneEnum.R.name())
				.map(toAgendamentoTelefone);
	}

	public AgendamentoContatoTelefone salvar(AgendamentoContatoTelefone agendamentoTelefone) {
		agendamentoTelefone.setDtUltmaAlter(new Date());
		
		return agendamentoRepository.save(agendamentoTelefone);
	}

	public AgendamentoContatoTelefone salvarTelefoneCorretor(AgendamentoContatoTelefone agendamentoTelefone) {
		agendamentoTelefone.setTpConttTelef(TipoContatoTelefoneEnum.C.name());
		agendamentoTelefone.setTpTelef(TipoContatoTelefoneEnum.C.name());
		
		return salvar(agendamentoTelefone);
	}

	public AgendamentoContatoTelefone salvarTelefoneSegurado(AgendamentoContatoTelefone agendamentoTelefone) {
		agendamentoTelefone.setTpConttTelef(TipoContatoTelefoneEnum.S.name());
		agendamentoTelefone.setTpTelef(TipoContatoTelefoneEnum.R.name());
		
		return salvar(agendamentoTelefone);
	}
}

