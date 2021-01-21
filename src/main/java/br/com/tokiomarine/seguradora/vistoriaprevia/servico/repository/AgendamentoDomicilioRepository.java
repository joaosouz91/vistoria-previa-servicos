package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoDomicilio;

@Repository
@Transactional(readOnly = true)
public interface AgendamentoDomicilioRepository  extends JpaRepository<AgendamentoDomicilio, Long> {

	@Query (value = "select agendamento from AgendamentoDomicilio agendamento where agendamento.cdVouch = :cdVoucher order by agendamento.idAgendDomcl desc ")
	public Optional<AgendamentoDomicilio> findAgendamentoDomicilioPorVoucher(@Param("cdVoucher") String codigoVoucher);
	
}
