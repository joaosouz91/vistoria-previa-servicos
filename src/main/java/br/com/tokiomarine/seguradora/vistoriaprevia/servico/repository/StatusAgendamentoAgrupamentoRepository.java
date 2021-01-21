package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.StatusAgendamentoAgrupamento;

@Repository
@Transactional(readOnly = true)
public interface StatusAgendamentoAgrupamentoRepository extends JpaRepository<StatusAgendamentoAgrupamento, Long>{

	@Query(value =  "select s " +
			" from StatusAgendamentoAgrupamento s " +
			" where s.cdVouch = :cdVouch  " +
			" and s.idStatuAgmto = (  select max ( Maior.idStatuAgmto ) " + 
			"                         from StatusAgendamentoAgrupamento Maior " +
			"						  where Maior.cdVouch = s.cdVouch " +
			" 						  group by Maior.cdVouch )" 
			)
	public Optional<StatusAgendamentoAgrupamento> findStatusAtualPorVoucher(@Param("cdVouch") String codigoVoucher);

	@Query(value =  "from StatusAgendamentoAgrupamento s where s.cdVouch = :cdVouch and s.cdSitucAgmto = :cdSitucAgmto and rownum <= 1 order by s.idStatuAgmto desc")
	public Optional<StatusAgendamentoAgrupamento> findStatusRecentePorVoucherSituacao(@Param("cdVouch") String codigoVoucher, @Param("cdSitucAgmto") String cdSitucAgmto);

	public List<StatusAgendamentoAgrupamento> findAllByCdVouchOrderByIdStatuAgmtoDesc(String cdVouch);
	
}
