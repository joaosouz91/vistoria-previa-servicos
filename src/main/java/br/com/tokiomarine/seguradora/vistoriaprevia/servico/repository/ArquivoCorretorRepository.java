package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoCorretor;

@Repository
public interface ArquivoCorretorRepository extends JpaRepository<ArquivoCorretor, String>{

	
	@Query(value = "SELECT LPAD(COR.CODINTERCLI,6,'0') || RPAD(NVL(TER.NOMTER,' '),40,' ')  ||  RPAD(NVL(TER.APETER,' '),40,' ')  || " + 
					"LPAD(SUBSTR(COR.CODOFI,1,5),5,'0') || RPAD(COR.CODSUPERINT,14,' ') LINHA_COR " + 
					"FROM   INTERMEDIARIO@DBL_APPLKSSVACX02 COR , TERCERO@DBL_APPLKSSVACX02   TER " + 
					"WHERE  COR.TIPOID    = TER.TIPOID  AND    COR.NUMID     = TER.NUMID  AND    COR.DVID = TER.DVID " + 
					"AND    COR.TIPOINTER IN ( 'C' , 'I' )  AND    COR.STSINTER  =  'ACT' " +
					" AND COR.CODSUPERINT IS NOT NULL    ORDER BY LPAD(COR.CODINTERCLI,6,'0') " 
					, nativeQuery = true)
	public List <ArquivoCorretor> getArquivoCorretor();

}
