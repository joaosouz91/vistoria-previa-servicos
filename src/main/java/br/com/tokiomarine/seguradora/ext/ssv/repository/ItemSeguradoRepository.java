package br.com.tokiomarine.seguradora.ext.ssv.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.transacional.model.ItemSegurado;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PropostaAgendamentoSSV;

@Repository
@Transactional(readOnly = true)
public interface ItemSeguradoRepository extends JpaRepository<ItemSegurado, Long> {

	@Query(value =  "select "+
			 		"	e " +
					"from "+
					"	ItemSegurado e " +
					"where " +
					"	e.numItemSegurado = :numeroItemSegurado "+
					"	and e.tipoHistorico = :tipoHistorico ")
	public ItemSegurado findByItemSegurado(@Param("numeroItemSegurado") Long numeroItemSegurado, @Param("tipoHistorico") String tipoHistorico);

	@Query(value =  "select "+
			 		"	e " +
					"from "+
					"	ItemSegurado e " +
					"where " +
					"	e.numItemSegurado = :numeroItemSegurado ")
	public ItemSegurado findByItemSeguradoNumeroItem(@Param("numeroItemSegurado") Long numeroItemSegurado);
			
	@Query(value =  "select i " +
    				"from ItemSegurado i, Restricao r, Proposta proposta " +
    				"where " + 
					"   i.codEndosso = :codigoEndosso" + 
					"   and proposta.cdPpota = i.codEndosso " +
					"   and r.idPpota = proposta.idPpota " + 
					"   and r.nrItseg = i.numItemSegurado ")	
	public ItemSegurado findItemSeguradoPorEndossoRestricaoVP(@Param("codigoEndosso") Long codigoEndosso);
	
	@Query(value =  "select i " +
			"from ItemSegurado i, Restricao r, Proposta proposta " +
			"where " + 
			"   i.codEndosso = :codigoEndosso" + 
			"   and i.numItemSegurado = :numeroItem " +
			"   and proposta.cdPpota = i.codEndosso " +
			"   and r.idPpota = proposta.idPpota " + 
			"   and r.nrItseg = i.numItemSegurado " +
			"   and r.tpRestr ='VIS' " )				
	public ItemSegurado findItemSeguradoPorEndossoItemRestricaoVP(@Param("codigoEndosso") Long codigoEndosso, @Param("numeroItem") Long numeroItem);	
	
	public List<ItemSegurado> findItemSeguradoBycodLaudoVistoriaPrevia(String codLaudoVistoriaPrevia);
	
	public Optional<ItemSegurado> findByCodEndossoAndNumItemSegurado(Long codEndosso, Long numItemSegurado);

	public Optional<ItemSegurado> findByCodEndosso(Long codEndosso);
	
	@Query("SELECT case when count(C)> 0 then true else false end " 
			+ "FROM ItemSegurado I, ControleEndosso C "
			+ "WHERE I.codLaudoVistoriaPrevia = :codLaudoVistoriaPrevia " 
			+ "AND C.codEndosso = I.codEndosso "
			+ "AND C.cdSitucEndos = 'GRD' ")
	boolean endossoEstaNaGrade(@Param("codLaudoVistoriaPrevia") String codLaudoVistoriaPrevia);
	
	@Query("SELECT case when count(N)> 0 then true else false end " 
			+ "FROM ItemSegurado I, Proposta N "
			+ "WHERE I.codLaudoVistoriaPrevia = :codLaudoVistoriaPrevia " 
			+ "AND I.tipoHistorico = '0' "
			+ "AND N.cdPpota = I.codNegocio " 
			+ "AND N.tpPpota = 'N' " 
			+ "AND N.cdStatuPpota = 'GRD'")
	boolean negocioEstaNaGrade(@Param("codLaudoVistoriaPrevia") String codLaudoVistoriaPrevia);

	@Query( value = "SELECT "
			+ "iseg.NR_ITSEG as numItemSegurado, "
			+ "iseg.TP_HISTO_ITSEG as tipoHistorico, "
			+ "iseg.CD_CHASSI_VEICU as codChassiVeiculo, "
			+ "iseg.CD_PLACA_VEICU as codPlacaVeiculo, "
			+ "iseg.CD_NGOCO as codNegocio, "
			+ "iseg.CD_ENDOS as codEndosso, "
			+ "iseg.CD_MDUPR as codModuloProduto, "
			+ "neg.cd_crtor_segur_prcpa as codCorretor, "
			+ "iseg.CD_CLIEN as codCliente " +
	        "FROM SSV0076_ITSEG iseg, " +
	        "ssv0084_ngoco neg, " +
	        "SSV0025_CONTR_ENDOS ce " +
	        "where neg.cd_ngoco = iseg.cd_ngoco " +
	        "and neg.tp_histo = iseg.tp_histo_ngoco " +
	        "and ce.cd_endos (+)= iseg.cd_endos " +
	        "and ( neg.cd_situc_ngoco in('COT','PRO','PRC','LIP','GRD') OR (neg.cd_situc_ngoco = 'APO' and iseg.cd_endos is not null " +
	        "and ce.cd_situc_endos not in('END','REC') ) ) " +
	        "and neg.dt_cadmt_ppota >= :dt_cadmt_ppota " +

	        "and (iseg.cd_chassi_veicu = :chassi " +
	        "and (iseg.cd_placa_veicu = :placa" +
	        "    and (iseg.cd_placa_veicu not in (select pi.cd_placa_veicu from SSV0099_PLACA_IMPDT pi) ) )" +
	         "   or (iseg.cd_chassi_veicu= :chassi ) ) " +        
	       " order by neg.dt_ultma_alter desc", nativeQuery = true )
	public List<PropostaAgendamentoSSV> getItemSeguradoByPlacaOrChassiAgendamento(@Param("chassi") String chassi, @Param("placa") String placa, @Param("dt_cadmt_ppota") Date dt_cadmt_ppota );

	@Query( value = "SELECT "
			+ "iseg.NR_ITSEG as numItemSegurado, "
			+ "iseg.TP_HISTO_ITSEG as tipoHistorico, "
			+ "iseg.CD_CHASSI_VEICU as codChassiVeiculo, "
			+ "iseg.CD_PLACA_VEICU as codPlacaVeiculo, "
			+ "iseg.CD_NGOCO as codNegocio, "
			+ "iseg.CD_ENDOS as codEndosso, "
			+ "iseg.CD_MDUPR as codModuloProduto, "
			+ "neg.cd_crtor_segur_prcpa as codCorretor, "
			+ "iseg.CD_CLIEN as codCliente " +
	        "FROM SSV0076_ITSEG iseg, " +
	        "ssv0084_ngoco neg, " +
	        "SSV0025_CONTR_ENDOS ce " +
	        "where neg.cd_ngoco = iseg.cd_ngoco " +
	        "and neg.tp_histo = iseg.tp_histo_ngoco " +
	        "and ce.cd_endos (+)= iseg.cd_endos " +
	        "and ( neg.cd_situc_ngoco in('COT','PRO','PRC','LIP','GRD') OR (neg.cd_situc_ngoco = 'APO' and iseg.cd_endos is not null " +
	        "and ce.cd_situc_endos not in('END','REC') ) ) " +
	        "and neg.dt_cadmt_ppota >= :dt_cadmt_ppota " +

	        "and neg.cd_crtor_segur_prcpa = :corretor " +
	        "and (iseg.cd_chassi_veicu = :chassi " +
	        "and (iseg.cd_placa_veicu = :placa" +
	        "    and (iseg.cd_placa_veicu not in (select pi.cd_placa_veicu from SSV0099_PLACA_IMPDT pi) ) )" +
	         "   or (iseg.cd_chassi_veicu= :chassi ) ) " +        
	       " order by neg.dt_ultma_alter desc", nativeQuery = true )
	public List<PropostaAgendamentoSSV> getItemSeguradoByCorretorPlacaOrChassiAgendamento(String chassi, String placa,
			Long corretor, @Param("dt_cadmt_ppota") Date dataLimitePesquisa);
}
