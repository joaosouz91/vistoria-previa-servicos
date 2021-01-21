package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;

@Repository
@Transactional(readOnly = true)
public interface LaudoVistoriaPreviaServicoRepository extends JpaRepository<LaudoVistoriaPrevia, Long> {
	
	@Query(value = "select distinct l "+  
                    "from LaudoVistoriaPrevia l , VeiculoVistoriaPrevia v, ItemSegurado i, br.com.tokiomarine.seguradora.aceitacao.crud.model.Restricao r, Proposta n "+    
                    "where "+
                    "l.cdSitucBlqueVspre = 0 "+
                    "and l.cdLvpre = v.cdLvpre "+
                    "and l.nrVrsaoLvpre = v.nrVrsaoLvpre "+  
                    "and l.icLaudoVicdo in('N','A') "+
                    "and n.cdPpota = i.codNegocio "+
                    "and i.codEndosso is null "+
                    "and n.cdStatuPpota = 'GRD' "+
                    "and r.nrItseg = i.numItemSegurado "+
                    "and r.idPpota = n.idPpota "+
                    "and r.tpRestr = 'VIS' "+
                    "and r.cdSitucRestr = 'GRD' "+
                    "and i.codSituacaoNegocio = 'GRD' "+
                    "and i.codPlacaVeiculo = :placa "+
                    "and i.codChassiVeiculo = :chassi "+
                    "and l.cdCrtorSegur = :corretor "+
                    "and (i.codChassiVeiculo = v.cdChassiVeicu "+ 
                    "or (i.codPlacaVeiculo = v.cdPlacaVeicu and v.cdPlacaVeicu not in (select pi.codPlacaVeiculo from PlacaImpeditiva pi)) ) order by l.cdSitucVspre desc")
	public LaudoVistoriaPrevia findLaudoVistoriaPreviaNegocioNative(@Param("placa") String placa, @Param("chassi") String chassi, @Param("corretor") Long corretor);
	
	@Query(value = "select distinct l "+
					"from LaudoVistoriaPrevia l , VeiculoVistoriaPrevia v, ItemSegurado i, br.com.tokiomarine.seguradora.aceitacao.crud.model.Restricao r, Proposta p "+
					"where "+
					"l.cdSitucBlqueVspre = 0 "+
					"and l.cdLvpre = v.cdLvpre "+
					"and l.nrVrsaoLvpre = v.nrVrsaoLvpre "+
					"and l.icLaudoVicdo in('N','A') "+
					"and i.codEndosso is not null "+
					"and p.cdPpota = i.codEndosso "+
					"and p.cdStatuPpota = 'GRD' "+
					"and r.idPpota = p.idPpota "+
					"and r.nrItseg = i.numItemSegurado "+
					"and r.tpRestr = 'VIS' "+
					"and r.cdSitucRestr = 'GRD' "+
					"and i.codSituacaoNegocio = 'GRD' "+
					"and i.codPlacaVeiculo = :placa "+
					"and i.codChassiVeiculo = :chassi "+
					"and l.cdCrtorSegur = :corretor "+
					"and (i.codChassiVeiculo = v.cdChassiVeicu "+
					"or (i.codPlacaVeiculo = v.cdPlacaVeicu and v.cdPlacaVeicu not in (select pi.codPlacaVeiculo from PlacaImpeditiva pi))) order by l.cdSitucVspre desc")
	public LaudoVistoriaPrevia findLaudoVistoriaPreviaEndossoNative(@Param("placa") String placa, @Param("chassi") String chassi, @Param("corretor") Long corretor);
}
