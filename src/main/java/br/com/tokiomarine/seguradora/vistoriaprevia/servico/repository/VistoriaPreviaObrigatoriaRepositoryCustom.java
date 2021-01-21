package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaObrigatoriaDTO;

@Repository
@Transactional(readOnly = true)
public interface VistoriaPreviaObrigatoriaRepositoryCustom {

	void detach(VistoriaPreviaObrigatoria entity);
	
	public Page<VistoriaObrigatoriaDTO> recuperarListaItemAgendar(VistoriaFiltro filtro, Pageable page);

	public Page<VistoriaObrigatoriaDTO> recuperarListaItemAgendada(VistoriaFiltro filtro, Pageable page);

	public List<VistoriaObrigatoriaDTO> recuperarListaItemAgendar(VistoriaFiltro filtro);
	
	public List<VistoriaObrigatoriaDTO> recuperarListaItemAgendada(VistoriaFiltro filtro);

	Long countListaItemAgendada(VistoriaFiltro filtro);
	
	List<String> obterVoucherMesmoVeiculo(String cdPlacaVeicu,String cdChassiVeicu,Long cdCrtorCia, Boolean placaImpeditiva, int qtidadeDiasRetrocessoAproveitaPreAg);
}
