package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.HistoricoLaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ListaReclassificacaoAlterarStatus;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.ReclassificacaoAlterarStatus;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.HistoricoLaudoVistoriaPreviaRepository;

@Component
public class HistoricoLaudoVistoriaPreviaService {

	@Autowired
	private HistoricoLaudoVistoriaPreviaRepository historicoLaudoVistoriaPreviaRepository;
	
	@Transactional
	public void salvarNovoHistoricoLaudoVistoriaPrevia(final LaudoVistoriaPrevia laudoVistoriaPrevia, 
			final ReclassificacaoAlterarStatus reclassificacaoAlterarStatus, final String idUsuarioLogado){
		
		HistoricoLaudoVistoriaPrevia historicoLaudo = new HistoricoLaudoVistoriaPrevia();
		historicoLaudo.setNrVrsaoLvpre(laudoVistoriaPrevia.getNrVrsaoLvpre());
		historicoLaudo.setCdLvpre(laudoVistoriaPrevia.getCdLvpre());
		historicoLaudo.setCdEndos(laudoVistoriaPrevia.getCdEndos());
		historicoLaudo.setCdSitucAnterVspre(laudoVistoriaPrevia.getCdSitucAnterVspre());
		historicoLaudo.setCdLocalCaptc(laudoVistoriaPrevia.getCdLocalCaptc());
		historicoLaudo.setCdSitucHistoVspre(2L);
		historicoLaudo.setDtUltmaAlter(new Date());
		historicoLaudo.setDsMotvSbrpoDado(reclassificacaoAlterarStatus.getJustificativa() != null && reclassificacaoAlterarStatus.getJustificativa().length() > 30 ? reclassificacaoAlterarStatus.getJustificativa().substring(0,30) : reclassificacaoAlterarStatus.getJustificativa());
		historicoLaudo.setCdUsuroIncls(idUsuarioLogado);
		
		
		
		historicoLaudoVistoriaPreviaRepository.save(historicoLaudo);
	}
	
	@Transactional
	public void salvarListaNovoHistoricoLaudoVistoriaPrevia(final LaudoVistoriaPrevia laudoVistoriaPrevia, 
			final ListaReclassificacaoAlterarStatus listaReclassificacaoAlterarStatus, final String idUsuarioLogado){
		
		HistoricoLaudoVistoriaPrevia historicoLaudo = new HistoricoLaudoVistoriaPrevia();
		historicoLaudo.setNrVrsaoLvpre(laudoVistoriaPrevia.getNrVrsaoLvpre());
		historicoLaudo.setCdLvpre(laudoVistoriaPrevia.getCdLvpre());
		historicoLaudo.setCdEndos(laudoVistoriaPrevia.getCdEndos());
		historicoLaudo.setCdSitucAnterVspre(laudoVistoriaPrevia.getCdSitucAnterVspre());
		historicoLaudo.setCdLocalCaptc(laudoVistoriaPrevia.getCdLocalCaptc());
		historicoLaudo.setCdSitucHistoVspre(2L);
		historicoLaudo.setDtUltmaAlter(new Date());
		historicoLaudo.setDsMotvSbrpoDado(listaReclassificacaoAlterarStatus.getJustificativa() != null && listaReclassificacaoAlterarStatus.getJustificativa().length() > 30 ? listaReclassificacaoAlterarStatus.getJustificativa().substring(0,30) : listaReclassificacaoAlterarStatus.getJustificativa());
		historicoLaudo.setCdUsuroIncls(idUsuarioLogado);
		
		
		historicoLaudoVistoriaPreviaRepository.save(historicoLaudo);
	}
}