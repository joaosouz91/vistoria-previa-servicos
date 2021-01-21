package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoContatoEmail;

@Repository
@Transactional(readOnly = true)
public interface AgendamentoContatoEmailRepository extends JpaRepository<AgendamentoContatoEmail, Long> {

	/**
	 * Consulta o email por voucher e tipo de contato (C, S ou 3).
	 * 
	 * @param cdVouch
	 * @param tpConttEmail
	 * @return Lista de AgendamentoContatoEmail
	 */
	@Query(value = "from AgendamentoContatoEmail a where a.cdVouch = :cdVouch and a.tpConttEmail = :tpConttEmail")
	public List<AgendamentoContatoEmail> findAgendamentoContatoEmail(String cdVouch, String tpConttEmail);

	/**
	 * Consulta o email por voucher e tipo de contato (C, S ou 3).
	 * 
	 * @param cdVouch
	 * @param tpConttEmail
	 * @return Lista de AgendamentoContatoEmail
	 */
	@Query(value = "select a.dsEmail from AgendamentoContatoEmail a where a.cdVouch = :cdVouch and a.tpConttEmail = :tpConttEmail")
	public List<String> findEmailAgendamentoContatoEmail(String cdVouch, String tpConttEmail);
}
