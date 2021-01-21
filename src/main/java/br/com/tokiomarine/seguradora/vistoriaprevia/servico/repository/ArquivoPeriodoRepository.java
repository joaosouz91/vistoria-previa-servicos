package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoPeriodo;

@Repository
public interface ArquivoPeriodoRepository extends JpaRepository<ArquivoPeriodo, Long>{

	
	@Query(value = "SELECT Y.CD_MARCA_MODEL RG_CD_MOD_REAL_PERIOD, CCT.DS_ABVDA_COLUN_TIPO  RG_TP_COMBUSTIVEL_PERIOD, " +
			"  Y.AA_FABRC_INICL  RG_AA_INICIAL_PERIOD, DECODE(Y.AA_FABRC_FINAL, NULL, 9999, Y.AA_FABRC_FINAL) RG_AA_FINAL_PERIOD " +
			" FROM SSV2201_VEICU Y,  SSV2069_VLCAR_ITSEG VI, SSV2066_VLCAR_MDUPR VM, SSV4001_CNTDO_COLUN_TIPO CCT" +
			" WHERE" +
			" Y.CD_MARCA_MODEL = VI.CD_VLCAR_ITSEG " +
			" AND VM.CD_VLCAR_ITSEG = VI.CD_VLCAR_ITSEG " +
			" AND VM.SQ_VLCAR_ITSEG = VI.SQ_VLCAR_ITSEG " +
			" AND SYSDATE BETWEEN VM.DT_INICO_VIGEN AND VM.DT_FIM_VIGEN" +
			" AND CCT.NM_COLUN_TIPO = 'TP_COMBUSTIVEL'" +
			" AND CCT.VL_CNTDO_COLUN_TIPO = Y.TP_CMBST" +
			" ORDER BY Y.CD_MARCA_MODEL ASC, CCT.DS_ABVDA_COLUN_TIPO, Y.AA_FABRC_INICL ASC, Y.AA_FABRC_FINAL ASC" 
					, nativeQuery = true)
	public List <ArquivoPeriodo> getArquivoPeriodo();

}
