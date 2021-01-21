package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPreviaGeral;

@Repository
@Transactional(readOnly = true)
public interface ParametroVistoriaPreviaGeralRepository extends JpaRepository<ParametroVistoriaPreviaGeral, Long> {

	@Query(value =  "select o from ParametroVistoriaPreviaGeral o where o.nmParamVspre = 'QTD_DIAS_MAX_DESBLOQUEIO_LAUDO'")
	public ParametroVistoriaPreviaGeral findByParametroVistoriaPreviaGeral();
	
	@Query(value =  "select o from ParametroVistoriaPreviaGeral o where o.nmParamVspre = 'QTD_DIAS_MAX_DESBLOQUEIO_LAUDO'")
	public List<ParametroVistoriaPreviaGeral> findBylistaParametroVistoriaPreviaGeral();
	
	@Query(value =  "select o from ParametroVistoriaPreviaGeral o where o.nmParamVspre = 'DT_INICIO_VP_F3'")
	public List<ParametroVistoriaPreviaGeral> findByParametroVistoriaPreviaGeralDataInicioVpFase3();

	public Optional<ParametroVistoriaPreviaGeral> findOneByNmParamVspre(String nmParamVspre);

	
	@Query(value =  "select o from ParametroVistoriaPreviaGeral o where o.nmParamVspre = :nometParmetroVP")
	public List<ParametroVistoriaPreviaGeral> findByParametroVistoriaPreviaGeralPorNome(String nometParmetroVP);
	
	@Query(value =  "select o from ParametroVistoriaPreviaGeral o where o.nmParamVspre = :nometParmetroVP")
	public Optional<ParametroVistoriaPreviaGeral> findByPorNome(String nometParmetroVP);

	public List<ParametroVistoriaPreviaGeral> findByNmParamVspre(String nmParam);

	public List<ParametroVistoriaPreviaGeral> findByNmParamVspreStartsWith(String nmParam);

}
