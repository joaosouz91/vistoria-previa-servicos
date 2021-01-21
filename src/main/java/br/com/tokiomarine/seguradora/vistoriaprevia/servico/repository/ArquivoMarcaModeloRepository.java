package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoMarcaModelo;

@Repository
public interface ArquivoMarcaModeloRepository extends JpaRepository<ArquivoMarcaModelo, Long>{

	
	@Query(value = "SELECT DISTINCT A.CD_VLCAR_ITSEG CD_VEICULO "
					+ ",A.DS_VARIC_INICO NM_VEICULO "
					+ ",C.CD_VLCAR_ITSEG CD_FABRICANTE "
					+ ",C.DS_VARIC_INICO NM_FABRICANTE "
					+ ",DECODE(TIPOCATEG.DS_VARIC_INICO "
					+ ",'CMLEVE','C' "
					+ ",'CMMEDIO','C' "
					+ ",'CMPESA','C' "
					+ ",'DEMAIS-UTILIT','C' "
					+ ",'FURGAO-UTILIT','C' "
					+ ",'IMPLEMENTO','C' "
					+ ",'IMPORT-PASSEIO','P' "
					+ ",'MOTO<450CC','P' "
					+ ",'MOTO>450CC','P' "
					+ ",'MOTO','P' "
					+ ",'ONIBUS','C' "
					+ ",'PASSEIO','P' "
					+ ",'PKLEVE-PASSEIO','P' "
					+ ",'PKLEVE-UTILIT','C' "
					+ ",'PKPESA-PASSEIO','P' "
					+ ",'PKPESA-UTILIT','C' "
					+ ",'REBOCA','C' "
					+ ",'SEMIREBO','C','X') TIPO_VEICULO "
					+ "FROM SSV2069_VLCAR_ITSEG A, "
					+ "SSV2201_VEICU B, "
					+ "SSV2069_VLCAR_ITSEG C, "
					+ "SSV2066_VLCAR_MDUPR VMPC, "
					+ "SSV2066_VLCAR_MDUPR VMPA, "
					+ "SSV2202_CATGO_VEICU CATEGORIA, "
					+ "SSV2069_VLCAR_ITSEG TIPOCATEG, "
					+ "SSV2066_VLCAR_MDUPR CATEGMODU "
					+ "WHERE VMPA.CD_CARAC_ITSEG = 10 "
					+ "AND  VMPA.DT_FIM_VIGEN = to_date ('31/12/2699','dd/mm/yyyy') "
					+ "AND  A.CD_VLCAR_ITSEG = VMPA.CD_VLCAR_ITSEG  "
					+ "AND  A.SQ_VLCAR_ITSEG = VMPA.SQ_VLCAR_ITSEG  "
					+ "AND  VMPC.DT_FIM_VIGEN = to_date ('31/12/2699','dd/mm/yyyy') "
					+ "AND C.CD_VLCAR_ITSEG = VMPC.CD_VLCAR_ITSEG  "
					+ "AND  C.SQ_VLCAR_ITSEG = VMPC.SQ_VLCAR_ITSEG  "
					+ "AND  B.CD_MARCA_MODEL = A.CD_VLCAR_ITSEG  "
					+ "AND  (B.IC_DVADO = 'N' OR B.DT_DESAT > sysdate )  "
					+ "AND  C.CD_VLCAR_ITSEG = B.CD_FABRT  "
					+ "AND CATEGORIA.ID_VEICU = B.ID_VEICU  "
					+ "AND CATEGORIA.DT_FIM_VIGEN = to_date ('31/12/2699','dd/mm/yyyy') "
					+ "AND CATEGMODU.CD_CARAC_ITSEG = 326  "
					+ "AND CATEGMODU.Cd_Vlcar_Itseg = CATEGORIA.CD_CATGO_TRFRA "
					+ "AND CATEGMODU.DT_FIM_VIGEN = to_date ('31/12/2699','dd/mm/yyyy') "
					+ "AND TIPOCATEG.CD_VLCAR_ITSEG = CATEGMODU.CD_VLCAR_ITSEG "
					+ "AND TIPOCATEG.SQ_VLCAR_ITSEG = CATEGMODU.SQ_VLCAR_ITSEG  "
					+ "ORDER BY A.DS_VARIC_INICO ASC, C.DS_VARIC_INICO ASC ", nativeQuery = true )
	public List <ArquivoMarcaModelo> getArquivoMarcaModelo();

}
