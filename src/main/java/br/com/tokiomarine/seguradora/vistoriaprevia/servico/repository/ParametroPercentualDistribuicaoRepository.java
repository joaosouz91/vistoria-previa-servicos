package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroPercentualDistribuicao;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ParametroPercentualDistribuicaoDTO;

@Repository
@Transactional(readOnly = true)
public interface ParametroPercentualDistribuicaoRepository
		extends JpaRepository<ParametroPercentualDistribuicao, Long>, ParametroPercentualDistribuicaoRepositoryCustom {

	@Query(value = " SELECT CASE WHEN COUNT(pd.idParamPerctDistr) > 0 THEN true ELSE false END "
			+ "FROM ParametroPercentualDistribuicao pd, RegiaoAtendimentoVistoriador reg "
			+ "WHERE pd.idRegiaoAtnmtVstro = reg.idRegiaoAtnmtVstro "
			+ "AND reg.nmMunic = :nmMunic "
			+ "AND :dataReferencia BETWEEN pd.dtInicoVigen AND pd.dtFimVigen "
			+ "AND pd.icCapit = 'S'")
	public boolean recuperarIndicadorCapital(@Param("nmMunic") String cidade,
			@Param("dataReferencia") Date dataReferencia);

	@Query(value = "SELECT new br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ParametroPercentualDistribuicaoDTO(pd, p) "
			+ "FROM ParametroPercentualDistribuicao pd, PrestadoraVistoriaPrevia p "
			+ "WHERE pd.cdAgrmtVspre = p.cdAgrmtVspre "
			+ "AND pd.idRegiaoAtnmtVstro = :idRegiaoAtnmtVstro "
			+ "AND :dataReferencia BETWEEN pd.dtInicoVigen AND pd.dtFimVigen "
			+ "AND pd.pcDistr > 0 "
			+ "ORDER BY pd.pcDistr DESC")
	public List<ParametroPercentualDistribuicaoDTO> recuperarDistrPercentual(@Param("idRegiaoAtnmtVstro") Long idRegiaoAtnmtVstro,
			@Param("dataReferencia") Date dataReferencia);

	@Query(value = "SELECT distinct pd.idRegiaoAtnmtVstro "
			+ "FROM ParametroPercentualDistribuicao pd, RegiaoAtendimentoVistoriador reg "
			+ "WHERE pd.idRegiaoAtnmtVstro = reg.idRegiaoAtnmtVstro "
			+ "AND reg.sgUf = :uf "
			+ "AND pd.dtFimVigen > current_date() ")
	List<Long> obterIdsRegiaoAtendimento(String uf);

	List<ParametroPercentualDistribuicao> findByIdRegiaoAtnmtVstroAndDtFimVigenGreaterThanEqual(Long r, Date dataRegistroVigente);

	@Query(value = "SELECT new br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ParametroPercentualDistribuicaoDTO(pd, p) "
			+ "FROM ParametroPercentualDistribuicao pd, PrestadoraVistoriaPrevia p "
			+ "WHERE pd.cdAgrmtVspre = p.cdAgrmtVspre "
			+ "AND pd.idRegiaoAtnmtVstro = :idRegiaoAtnmtVstro "
			+ "AND pd.dtFimVigen >= :dataReferencia "
			+ "ORDER BY pd.dtFimVigen DESC, pd.pcDistr DESC, p.nmRazaoSocal ASC")
	List<ParametroPercentualDistribuicaoDTO> findAllDistrPercentual(@Param("idRegiaoAtnmtVstro") Long idRegiaoAtnmtVstro,
			@Param("dataReferencia") Date dataReferencia);
}
