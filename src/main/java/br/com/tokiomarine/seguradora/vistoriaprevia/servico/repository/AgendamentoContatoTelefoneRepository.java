package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoContatoTelefone;

@Repository
@Transactional(readOnly = true)
public interface AgendamentoContatoTelefoneRepository extends JpaRepository<AgendamentoContatoTelefone, Long> {

	/**
	 * Consulta o contato telefonico por voucher e tipo de contato (C ou S). @See TipoContatoTelefoneEnum
	 * @param voucher
	 * @param tipoContatoTelefone
	 * @return Lista de AgendamentoContatoTelefone
	 */
	@Query(value = "from AgendamentoContatoTelefone a where a.cdVouch = :cdVouch and a.tpConttTelef = :tpConttTelef")
	public List<AgendamentoContatoTelefone> findAgendamentoContatoTelefone(@Param("cdVouch") String voucher,
			@Param("tpConttTelef") String tipoContatoTelefone);

	public Optional<AgendamentoContatoTelefone> findFirstByCdVouchAndTpConttTelefOrderByIdAgendConttTelefDesc(
			@Param("cdVouch") String voucher, @Param("tpConttTelef") String tipoContatoTelefone);

	public Optional<AgendamentoContatoTelefone> findFirstByCdVouchAndTpConttTelefAndTpTelefOrderByIdAgendConttTelefDesc(
			@Param("cdVouch") String voucher, @Param("tpConttTelef") String tipoContatoTelefone, @Param("tpTelef") String tipoTelefone);
}
