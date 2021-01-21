package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoDTO;

@Repository
@Transactional(readOnly = true)
public interface AgendamentoVistoriaPreviaRepository extends JpaRepository<AgendamentoVistoriaPrevia, Long>, AgendamentoVistoriaPreviaRepositoryCustom {

	@Query(value = "select agendamento from AgendamentoVistoriaPrevia agendamento where agendamento.cdVouch = :cdVoucher ")
	public Optional<AgendamentoVistoriaPrevia> findAgendamentoPorVoucher(@Param("cdVoucher") String codigoVoucher);

	@Query(value = "SELECT COUNT(Ag) FROM AgendamentoVistoriaPrevia Ag, AgendamentoDomicilio AgD WHERE Ag.cdVouch = AgD.cdVouch AND AgD.dtVspre = :dtVspre")
	public long countAgendamentos(@Param("dtVspre") Date dtVspre);

	@Query(value = "SELECT COUNT(Ag) FROM AgendamentoVistoriaPrevia Ag, AgendamentoDomicilio AgD "
			+ "WHERE Ag.cdVouch = AgD.cdVouch AND Ag.cdAgrmtVspre = :codPrestadora AND AgD.dtVspre = :dtVspre")
	public long countAgendamentos(@Param("codPrestadora") Long codPrestadora, @Param("dtVspre") Date dtVspre);
	
	@Query(value = "SELECT new br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoDTO("
			+ "a.cdAgrmtVspre, a.cdVouch, a.cdVouchAnter, a.tpVspre, s.cdSitucAgmto, s.cdMotvSitucAgmto"
			+ ") "
			+ "FROM AgendamentoVistoriaPrevia a, StatusAgendamentoAgrupamento s, VistoriaPreviaObrigatoria vp "
			+ "WHERE a.cdVouch = s.cdVouch AND a.cdVouch = vp.cdVouch AND vp.idVspreObgta = :idVspreObgta "
			+ "ORDER BY s.idStatuAgmto DESC")
	public List<AgendamentoDTO> recuperarAgtoStatusPorIdPreAgto(@Param("idVspreObgta") Long idVspreObgta);
	
	@Query(value = "SELECT new br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoDTO("
			+ "a.cdAgrmtVspre, a.cdVouch, a.cdVouchAnter, a.tpVspre, s.cdSitucAgmto, s.cdMotvSitucAgmto"
			+ ") "
			+ "FROM AgendamentoVistoriaPrevia a, StatusAgendamentoAgrupamento s "
			+ "WHERE a.cdVouch = s.cdVouch AND a.cdVouch = :codVoucher ORDER BY s.idStatuAgmto DESC")
	public List<AgendamentoDTO> recuperarAgtoStatusPorCdVoucher(@Param("codVoucher") String codVoucher);

	@Query(value = "SELECT VPESQ0000_CONTROLE_NR_VOUCH.NEXTVAL FROM DUAL", nativeQuery = true)
	public Long recuperarSeqVoucher();

	@Query(nativeQuery = true,
			value = "select vp.cd_vouch, ACT0260.Cd_Statu_Ppota "
					 + "from VPE0424_VSPRE_OBGTA vp, "
					 + "vpe0437_statu_agmto st, "
					 + "act0260_ppota ACT0260 "
					 + "where substr(vp.cd_vouch,0,3) = 'TSA' "
					 + "and (vp.cd_endos is null or vp.cd_endos = 0) "
					 + "and vp.id_vspre_obgta in(select max(maxVis.ID_VSPRE_OBGTA) "
					 + "from VPE0424_VSPRE_OBGTA maxVis "
					 + "where maxVis.Cd_Vouch = vp.cd_vouch) "
					 + "and st.id_statu_agmto in (select max(ss.id_statu_agmto) from vpe0437_statu_agmto ss where ss.cd_vouch = vp.cd_vouch) "
					 + "and st.cd_situc_agmto = 'PEN' "
					 + "and ACT0260.Tp_Ppota = 'N' "
					 + "AND ACT0260.CD_PPOTA=vp.cd_ngoco "
					 + "and ACT0260.Cd_Statu_Ppota in ('REC', 'LIB') "
					 + "union "
					 + "select vp.cd_vouch, ACT0260.Cd_Statu_Ppota "
					 + "from VPE0424_VSPRE_OBGTA vp, "
					 + "vpe0437_statu_agmto st, "
					 + "act0260_ppota ACT0260 "
					 + "where substr(vp.cd_vouch,0,3) = 'TSA' "
					 + "and vp.cd_endos > 0 "
					 + "and vp.id_vspre_obgta in(select max(maxVis.ID_VSPRE_OBGTA) "
					 + "from VPE0424_VSPRE_OBGTA maxVis "
					 + "where maxVis.Cd_Vouch = vp.cd_vouch) "
					 + "and st.id_statu_agmto in (select max(ss.id_statu_agmto) from vpe0437_statu_agmto ss where ss.cd_vouch = vp.cd_vouch) "
					 + "and st.cd_situc_agmto = 'PEN' "
					 + "and ACT0260.Tp_Ppota = 'E' "
					 + "AND ACT0260.CD_PPOTA=vp.cd_endos "
					 + "and ACT0260.Cd_Statu_Ppota in ('REC', 'LIB')") 
	public List<Object[]> findAgendamentosMobile();
	
}
