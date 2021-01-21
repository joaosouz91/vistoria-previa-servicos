package br.com.tokiomarine.seguradora.ext.ssv.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.pk.ConteudoColunaTipoPK;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ConteudoColunaTipoDTO;

@Repository
public interface ConteudoColunaTipoRepository extends JpaRepository<ConteudoColunaTipo, ConteudoColunaTipoPK>{

	@Query(value =  "select o " +
			 		"from ConteudoColunaTipo o " +
			 		"where "+
			 		"o.vlCntdoColunTipo = :vlCntdoColunTipo "+
			 		"and o.nmColunTipo = :nmColunTipo ")
	public ConteudoColunaTipo findConteudoColunaTipoByNmColunTipoVlCntdoColunTipo(@Param("nmColunTipo") String nmColunTipo, @Param("vlCntdoColunTipo") String vlCntdoColunTipo);								
		
	@Query(value =  "select "+
					"	o "+ 
					"from "+ 
					"	ConteudoColunaTipo o "+ 
					"where "+
					"	o.nmColunTipo = 'TP_MOTV_DISPE_VSTRI' "+
					"	order by o.dsRmidaColunTipo")
	public List<ConteudoColunaTipo> motivoDispensas();
	
	@Query(value =  "select "+
					"	o "+ 
					"from "+ 
					"	ConteudoColunaTipo o "+ 
					"where "+
					"	o.nmColunTipo = 'TP_LIBEC' "+
					"	order by o.dsRmidaColunTipo")
	public List<ConteudoColunaTipo> motivoDivergencia();
	
	@Query(value =  "select "+
			 		"	o "+
			 		"from "+
					"	ConteudoColunaTipo o "+
			 		"where "+
			 		"	o.nmColunTipo = 'TP_LOCAL_VSPRE'")
	public List<ConteudoColunaTipo> listaTipoLocalVistoria();
	
	@Query(value =  "select "+
			 		"	o "+
			 		"from "+
					"	ConteudoColunaTipo o "+
			 		"where "+
			 		"	o.nmColunTipo = 'TP_ESTAD_CIVIL'")
	public List<ConteudoColunaTipo> listaEstadoCivil();
	
	@Query(value =  "select "+
			 		"	o "+
			 		"from "+
					"	ConteudoColunaTipo o "+
			 		"where "+
			 		"	o.nmColunTipo = 'TP_CODUT_VEICU'")
	public List<ConteudoColunaTipo> listaTipoDeCondutor();
	
	@Query(value =  "select "+
			 		"	o "+
			 		"from "+
					"	ConteudoColunaTipo o "+
			 		"where "+
			 		"	o.nmColunTipo = 'TP_COMBUSTIVEL'")
	public List<ConteudoColunaTipo> listaTipoCombustivel();

	@Query(value = "select case when count(o)> 0 then true else false end from ConteudoColunaTipo o "
			+ "where o.nmColunTipo = 'CD_MOTV_SITUC_AGMTO' AND :dataReferencia between o.dtInicoVigen and o.dtFimVigen AND o.vlCntdoColunTipo = :codMotvSitucAgmto")
	public boolean existsConteudoPorDataCodMotiv(@Param("dataReferencia") Date dataReferencia, String codMotvSitucAgmto);

	@Query(value = "select new br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ConteudoColunaTipoDTO("
			+ "o.vlCntdoColunTipo, o.dsAbvdaColunTipo, o.dsCoptaColunTipo"
			+ ") "
			+ "from ConteudoColunaTipo o "
			+ "where o.nmColunTipo = 'CD_MOTV_SITUC_AGMTO' AND :dataReferencia between o.dtInicoVigen and o.dtFimVigen AND o.vlCntdoColunTipo = :codMotvSitucAgmto")
	public List<ConteudoColunaTipoDTO> findConteudoPorDataCodMotiv(@Param("dataReferencia") Date dataReferencia, String codMotvSitucAgmto);

	@Query(value = "select new br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ConteudoColunaTipoDTO("
			+ "o.vlCntdoColunTipo, o.dsAbvdaColunTipo, o.dsCoptaColunTipo"
			+ ") "
			+ "from ConteudoColunaTipo o "
			+ "where o.nmColunTipo = 'CD_MOTV_SITUC_AGMTO' AND o.vlCntdoColunTipo not in :vlCntdoColunTipo AND :dataReferencia between o.dtInicoVigen and o.dtFimVigen")
	public List<ConteudoColunaTipoDTO> listaConteudoPorTiposData(@Param("dataReferencia") Date dataReferencia, @Param("vlCntdoColunTipo") String... vlCntdoColunTipo);
}