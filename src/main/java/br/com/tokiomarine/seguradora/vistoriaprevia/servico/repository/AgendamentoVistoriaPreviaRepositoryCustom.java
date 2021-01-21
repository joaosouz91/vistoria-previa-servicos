package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoDTO;

@Repository
@Transactional(readOnly = true)
public interface AgendamentoVistoriaPreviaRepositoryCustom {

	public List<AgendamentoDTO> findAgendamentosDomicilioSicrediAGD(Date dataDe, Date dataAte);

	Optional<AgendamentoDTO> findAgendamentoDomicilioSicredi(String voucher);
}
