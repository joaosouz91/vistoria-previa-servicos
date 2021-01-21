package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AcessorioLaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParecerTecnicoVistoriaPrevia;

@Repository
@Transactional(readOnly = true)
public interface AcessorioLaudoVistoriaPreviaRepository extends JpaRepository<AcessorioLaudoVistoriaPrevia,Long>  {

	@Query(value =  "select "+
			"	 o.cdAcsroVspre "+
			"from "+
			"   AcessorioLaudoVistoriaPrevia o "+
			"where "+
			"	o.cdLvpre = :noLaudoVistPrevia")
	public List<Long> findByAcessorioLaudo(@Param("noLaudoVistPrevia") String noLaudoVistPrevia);

	//Obtem todos os acessórios 
	@Query(value = "SELECT \r\n" + 
			"    X2.CD_ACSRO_VSPRE codigo, \r\n" + 
			"    X2.DS_ACSRO_VSPRE descricao, \r\n" + 
			"    X2.TP_ACSRO_VSPRE tipo, \r\n" + 
			"    X1.TP_CMPLO_ACSRO_VSPRE complemento \r\n" + 
			"FROM VPE0001_ACSRO_LVPRE X1 \r\n" + 
			"    INNER JOIN VPE0002_ACSRO_VSPRE X2  on X2.CD_ACSRO_VSPRE = X1.CD_ACSRO_VSPRE\r\n" + 
			"WHERE X1.CD_LVPRE = ?1 AND X2.TP_ACSRO_VSPRE = 'A' \r\n" + 
			"order by X2.CD_ACSRO_VSPRE",  
			nativeQuery = true)
	public List<Object> obterAcessorios(@Param("cdLvpre") String cdLvpre);
	
	//Obtem todos os equipamentos de segurança
	@Query(value = "SELECT \r\n" + 
			"    X2.CD_ACSRO_VSPRE codigo, \r\n" + 
			"    X2.DS_ACSRO_VSPRE descricao, \r\n" + 
			"    X2.TP_ACSRO_VSPRE tipo, \r\n" + 
			"    X1.TP_CMPLO_ACSRO_VSPRE complemento \r\n" + 
			"FROM VPE0001_ACSRO_LVPRE X1 \r\n" + 
			"    INNER JOIN VPE0002_ACSRO_VSPRE X2  on X2.CD_ACSRO_VSPRE = X1.CD_ACSRO_VSPRE\r\n" + 
			"WHERE X1.CD_LVPRE = ?1 AND X2.TP_ACSRO_VSPRE = 'S' \r\n" + 
			"order by X2.CD_ACSRO_VSPRE",  
			nativeQuery = true)
	public List<Object> obterEquipSegur(@Param("cdLvpre") String cdLvpre);
	
	//Obtem todos os equipamentos 
	@Query(value = "SELECT \r\n" + 
			"    X2.CD_ACSRO_VSPRE codigo, \r\n" + 
			"    X2.DS_ACSRO_VSPRE descricao, \r\n" + 
			"    X2.TP_ACSRO_VSPRE tipo, \r\n" + 
			"    X1.TP_CMPLO_ACSRO_VSPRE complemento \r\n" + 
			"FROM VPE0001_ACSRO_LVPRE X1 \r\n" + 
			"    INNER JOIN VPE0002_ACSRO_VSPRE X2  on X2.CD_ACSRO_VSPRE = X1.CD_ACSRO_VSPRE\r\n" + 
			"WHERE X1.CD_LVPRE = ?1 AND X2.TP_ACSRO_VSPRE = 'E' \r\n" + 
			"order by X2.CD_ACSRO_VSPRE",  
			nativeQuery = true)
	public List<Object> obterEquipamentos(@Param("cdLvpre") String cdLvpre);
	
	//Obtem todos as avarias
	@Query(value = "SELECT \r\n" + 
			"      X2.CD_PECA_AVADA, \r\n" + 
			"      X2.DS_PECA_AVADA, \r\n" + 
			"      X1.TP_AVARI_VEICU, \r\n" +
			"      x3.ds_tipo_avari,  \r\n" + 
			"      (X1.QT_HORA_ELETR +X1. QT_HORA_FUNIL + X1.QT_HORA_PNTUR + X1.QT_HORA_TAPEC) \r\n" + 
			"FROM VPE0011_AVARI_LVPRE  X1 \r\n" + 
			"INNER JOIN VPE0097_PECA_VEICU_VSPRE X2 ON X2.CD_PECA_AVADA = X1.CD_PECA_AVADA \r\n" +
			"INNER JOIN VPE0433_AVARI_VSPRE x3 on x3.cd_tipo_avari = x1.tp_avari_veicu \r\n" +			
			"WHERE X1.CD_LVPRE= ?1 \r\n" + 
			"ORDER BY X2.CD_PECA_AVADA ",  
			nativeQuery = true)
	public List<Object> obterAvarias(@Param("cdLvpre") String cdLvpre);
	
	//Obtem todas as informações técnicas
	@Query(value = "SELECT \r\n" + 
			"  	  X1.CD_PATEC_VSPRE, \r\n" + 
			"     X2.DS_PATEC_VSPRE \r\n" + 
			"FROM VPE0095_PATEC_LVPRE  X1 \r\n" + 
			"INNER JOIN VPE0096_PATEC_VSPRE X2 ON X2.CD_PATEC_VSPRE=X1.CD_PATEC_VSPRE\r\n" + 
			"WHERE CD_LVPRE= ?1 \r\n" ,
			nativeQuery = true)
	public List<Object> obterInfTec(@Param("cdLvpre") String cdLvpre);
	
	@Query(value=" FROM ParecerTecnicoVistoriaPrevia WHERE cdSitucPatec='A' order by cdPatecVspre ")
	public List<ParecerTecnicoVistoriaPrevia> obterParecerTec();
	
	@Query(value = "select * from (SELECT " + 
			"        CD_NGOCO, " + 
			"        NR_ITSEG, " + 
			"        DT_BASE_CALLO_ITEM, " + 
			"        CD_ENDOS, " + 
			"        DS_RMIDA_COLUN_TIPO, " + 
			"        CD_APOLI, " + 
			"        TP_SEGUR    " + 
			"    FROM " + 
			"        ( " + 
			"        SELECT * FROM SSV0076_ITSEG  I  " + 
			"        INNER JOIN SSV4001_CNTDO_COLUN_TIPO  T ON T.NM_COLUN_TIPO='CD_SITUC_NGOCO'  " + 
			"        AND VL_CNTDO_COLUN_TIPO=CD_SITUC_NGOCO " + 
			"        where  i.CD_LVPRE= ?1 ORDER BY DT_BASE_CALLO_ITEM desc) tab) where rownum <= 1 ",  nativeQuery = true)
	public List<Object> obterPropostasVinculadas(String cdvlpre);
	
	@Query(value = "SELECT DISTINCT VLC.CD_VLCAR_ITSEG,VLC.DS_VARIC_INICO " + 
			"       FROM SSV2069_VLCAR_ITSEG VLC, " + 
			"       SSV2066_VLCAR_MDUPR VMP " + 
			"       WHERE VLC.CD_VLCAR_ITSEG = VMP.CD_VLCAR_ITSEG " + 
			"       AND VLC.SQ_VLCAR_ITSEG = VMP.SQ_VLCAR_ITSEG " + 
			"       AND VMP.CD_CARAC_ITSEG = 9" + 
			"       AND sysdate BETWEEN VMP.DT_INICO_VIGEN " + 
			"       AND VMP.DT_FIM_VIGEN " + 
			"       ORDER BY VLC.DS_VARIC_INICO " + 
			"",  nativeQuery = true)
	public List<Object> obterFabricantes();
	
	@Query(value = "SELECT DISTINCT VLC.DS_VARIC_INICO " + 
			"       FROM SSV2069_VLCAR_ITSEG VLC, " + 
			"       SSV2066_VLCAR_MDUPR VMP " + 
			"       WHERE VLC.CD_VLCAR_ITSEG = VMP.CD_VLCAR_ITSEG " + 
			"       AND VLC.SQ_VLCAR_ITSEG = VMP.SQ_VLCAR_ITSEG " + 
			"       AND VMP.CD_CARAC_ITSEG = 9" + 
			"       AND sysdate BETWEEN VMP.DT_INICO_VIGEN " + 
			"       AND VMP.DT_FIM_VIGEN " +
			"       AND VLC.CD_VLCAR_ITSEG = :cod " +
			"       ORDER BY VLC.DS_VARIC_INICO " + 
			"",  nativeQuery = true)
	public String obterFabricante(Long cod);
	
	@Query(value = "SELECT DISTINCT B.CD_MARCA_MODEL, A.DS_VARIC_INICO, CT.CD_FAMIL_AUTOM " + 
			"		FROM SSV2201_VEICU B,   " + 
			"		SSV2202_CATGO_VEICU CT, " + 
			"		SSV2069_VLCAR_ITSEG A,  " + 
			"		SSV2066_VLCAR_MDUPR VMP " + 
			"		WHERE B.CD_FABRT = ?1   " + 
			"		AND A.CD_VLCAR_ITSEG = VMP.CD_VLCAR_ITSEG  " + 
			"		AND A.SQ_VLCAR_ITSEG = VMP.SQ_VLCAR_ITSEG  " + 
			"		AND VMP.CD_CARAC_ITSEG = 10   " + 
			"		AND (B.IC_DVADO = 'N' OR B.DT_DESAT > SYSDATE) " + 
			"		AND CT.ID_VEICU = B.ID_VEICU  " + 
			"		AND A.CD_VLCAR_ITSEG = B.CD_MARCA_MODEL  " + 
			"		ORDER BY A.DS_VARIC_INICO ASC ",  nativeQuery = true)
	public List<Object> obterModeloPorFabricante(String fabric);
	
	@Query(value = "SELECT DISTINCT A.DS_VARIC_INICO" + 
			"		FROM SSV2201_VEICU B,   " + 
			"		SSV2202_CATGO_VEICU CT, " + 
			"		SSV2069_VLCAR_ITSEG A,  " + 
			"		SSV2066_VLCAR_MDUPR VMP " + 
			"		WHERE B.CD_FABRT = ?1   " + 
			"		AND A.CD_VLCAR_ITSEG = VMP.CD_VLCAR_ITSEG  " + 
			"		AND A.SQ_VLCAR_ITSEG = VMP.SQ_VLCAR_ITSEG  " + 
			"		AND VMP.CD_CARAC_ITSEG = 10   " + 
			"		AND (B.IC_DVADO = 'N' OR B.DT_DESAT > SYSDATE) " + 
			"		AND CT.ID_VEICU = B.ID_VEICU  " + 
			"		AND A.CD_VLCAR_ITSEG = B.CD_MARCA_MODEL  " +
			"		AND B.CD_MARCA_MODEL = ?2  " +
			"		ORDER BY A.DS_VARIC_INICO ASC ",  nativeQuery = true)
	public String obterModelo(Long fabric, Long model);
}