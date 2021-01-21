package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro;

@Repository
@Transactional(readOnly = true)
public interface VistoriaPreviaObrigatoriaRepository
		extends JpaRepository<VistoriaPreviaObrigatoria, Long>, VistoriaPreviaObrigatoriaRepositoryCustom {

	@Query(value = "select vpo " + "from VistoriaPreviaObrigatoria vpo " + "where vpo.idVspreObgta = vpo.idVspreObgta "
			+ " and vpo.nrItseg = :nrItemSegurado " + "order by vpo.dtUltmaAlter desc ")
	public List<VistoriaPreviaObrigatoria> findObterPreAgendamentoPorItemSegurado(
			@Param("nrItemSegurado") Long nrItemSegurado);

	@Query(value = "select vpo " + "from VistoriaPreviaObrigatoria vpo " + "where vpo.idVspreObgta = vpo.idVspreObgta "
			+ "and vpo.nrItseg = :nrItemSegurado " + "and vpo.cdEndos = :codigoEndosso "
			+ "order by vpo.dtUltmaAlter desc ")
	public List<VistoriaPreviaObrigatoria> findObterPreAgendamentoPorItemSeguradoEndosso(
			@Param("nrItemSegurado") Long nrItemSegurado, @Param("codigoEndosso") Long codigoEndosso);

	@Query(value = "select vpo from VistoriaPreviaObrigatoria vpo where vpo.cdVouch = :cdVouch")
	public List<VistoriaPreviaObrigatoria> findObterPreAgendamentoPorVoucher(@Param("cdVouch") String cdVouch);

	public Optional<VistoriaPreviaObrigatoria> findFirstByCdVouchOrderByIdVspreObgtaDesc(String cdVouch);
	
	public Optional<VistoriaPreviaObrigatoria> findFirstByNrCalloOrderByIdVspreObgtaDesc(Long nrCallo);

	@Query("select v, a from VistoriaPreviaObrigatoria v inner join AgendamentoVistoriaPrevia a on v.cdVouch = a.cdVouch")
	public Page<Tuple> findAll(VistoriaFiltro filtro, Pageable page);

	@Modifying(flushAutomatically = true)
	@Transactional
	@Query(value = "UPDATE VistoriaPreviaObrigatoria vpo SET vpo.cdVouch =:codVoucher WHERE vpo.idVspreObgta =:idVspreObgta")
	public void updateVoucherPorId(@Param("idVspreObgta") Long idVspreObgta, @Param("codVoucher") String codVoucher);
	
	@Query(value = "select vp.nmClien, vp.nrItseg , vp.cdEndos, vp.cdChassiVeicu, vp.cdPlacaVeicu, vp.cdNgoco, tc.cdDddTelef, tc.nrTelef, tc.tpTelef, ec.dsEmail, vp.cdCrtorCia, vp.nrCpfCnpjClien, vp.nmFabrt, vp.dsModelVeicu "
					+ "from VistoriaPreviaObrigatoria vp, Negocio ng, EmailCliente ec, TelefoneCliente tc, Proposta ppota, Restricao rest "
					+ "where ng.codNegocio = vp.cdNgoco "
					+ "and ec.idEmail = ng.idEmail "
					+ "and tc.idTelef = ng.idTelef "
					+ "and vp.cdVouch is null "
					+ "and ng.codSituacaoNegocio = 'GRD' "
                    + "and trunc(vp.dtUltmaAlter) = trunc(:data) "
                    + "and vp.tpVeicu in('P','O','T','B','L','C','U') "
					+ "and (ppota.tpPpota = 'N' AND ppota.cdPpota=vp.cdNgoco OR ppota.tpPpota = 'E' AND ppota.cdPpota=vp.cdEndos) "
					+ "and rest.idPpota = ppota.idPpota "
					+ "and rest.tpRestr='VIS' "
					+ "and rest.cdSitucRestr = 'GRD' "
                    + "order by vp.idVspreObgta")
	public List<Object[]> emailsSolicitaAgendamentos (@Param("data") Date data);
	
}
