package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VeiculoVistoriaPreviaDTO.Fields.nmDutVeicu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VeiculoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.FiltroConsultaVistoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.LaudoVistoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoDeVinculo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NoContentException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.LaudoVistoriaPreviaRepositoryHql;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.VeiculoVistoriaPreviaRepository;


@Service
@Transactional
public class VeiculoVistoriaPreviaService {

	
	
	@Autowired
	VeiculoVistoriaPreviaRepository veiculoVistoriaPreviaRepository;
	
	@Autowired
	private LaudoVistoriaPreviaRepositoryHql laudoVistoriaPreviaRepositoryHql;
	
	
	
	
	
	public List<LaudoVistoria> consultaLaudos(final FiltroConsultaVistoria  filtroConsultaVistoria){
		List<LaudoVistoria> laudoVistorias = new ArrayList<>();
		
		try{			
			final List<?> list = laudoVistoriaPreviaRepositoryHql.findByLaudosByVeiculo(filtroConsultaVistoria);
			final Iterator<?> iterator = list.listIterator();
			
			while (iterator.hasNext()) {        	
	            final Object[] itemList = (Object[]) iterator.next();	            
	            final VeiculoVistoriaPrevia veiculoVistoriaPrevia = (VeiculoVistoriaPrevia) itemList[0];
	            final LaudoVistoriaPrevia laudoVistoriaPrevia = (LaudoVistoriaPrevia) itemList[1];
	            	            
	            LaudoVistoria laudoVistoria = new LaudoVistoria();
	            laudoVistoria.setNumeroVistoria(laudoVistoriaPrevia.getCdLvpre());
	            laudoVistoria.setPlaca(veiculoVistoriaPrevia.getCdPlacaVeicu());
	            laudoVistoria.setChassi(veiculoVistoriaPrevia.getCdChassiVeicu());
	            laudoVistoria.setData(laudoVistoriaPrevia.getDtVspre());	            
	            //laudoVistoria.setStatus(StatusLaudo.valueOf(laudoVistoriaPrevia.getCdSitucVspre()).getValue());
	            laudoVistoria.setSituacao(TipoDeVinculo.valueOf(laudoVistoriaPrevia.getIcLaudoVicdo()).getValue());
	            	            	            
	            laudoVistorias.add(laudoVistoria);
			}				
		}catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}
		
		return laudoVistorias;
	}
	
	
	public Page<VeiculoVistoriaPrevia> listaVeiculos(VeiculoVistoriaPrevia filtro, Pageable page) {
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.STARTING).withIgnoreCase();
		Example<VeiculoVistoriaPrevia> example = Example.of(filtro, matcher);
		PageRequest pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by(Direction.ASC, nmDutVeicu));
		Page<VeiculoVistoriaPrevia> findAll = veiculoVistoriaPreviaRepository.findAll(example, pageable);
		if (findAll.isEmpty())
			throw new NoContentException();
		return findAll;
	}
	

	public VeiculoVistoriaPrevia findByVeiculoVistoriaPrevia(String cdLvpre) {
		return veiculoVistoriaPreviaRepository.findByVeiculoVistoriaPrevia(cdLvpre);
	}


	public Optional<VeiculoVistoriaPrevia> findByCdLvpre(String cdLvpre) {
		return veiculoVistoriaPreviaRepository.findByCdLvpreAndNrVrsaoLvpre(cdLvpre, 0L);
	}
}
