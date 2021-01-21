package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoDomicilio;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoDomicilioDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.AgendamentoDomicilioRepository;

@Service
public class AgendamentoDomicilioService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AgendamentoDomicilioRepository agendamentoDomicilioRepository;

	public Optional<AgendamentoDomicilioDTO> obterAgendamentoPorVoucher(String voucher) {
		return obterAgendamentoDomicilio(voucher).map(a -> mapper.map(a, AgendamentoDomicilioDTO.class));
	}

	public Optional<AgendamentoDomicilio> obterAgendamentoDomicilio(String voucher) {
		return agendamentoDomicilioRepository.findAgendamentoDomicilioPorVoucher(voucher);
	}
	
	public AgendamentoDomicilio salvar(AgendamentoDomicilio entity) {
		
		entity.setDtUltmaAlter(new Date());
		
		return agendamentoDomicilioRepository.save(entity);
	}
}
