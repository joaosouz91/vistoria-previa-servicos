package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.FranquiaDTO.Fields.cdEmpreVstra;

import java.util.Date;

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

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.EmpresaVistoriadora;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.DuplicateResourceException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NoContentException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.FranquiaRespository;

@Service
@Transactional
public class FranquiaService {
	
	@Autowired
	private FranquiaRespository franquiaRepository;
	
	public Page<EmpresaVistoriadora> listaFranquia(EmpresaVistoriadora filtro, Pageable page) {
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.STARTING).withIgnoreCase();
		Example<EmpresaVistoriadora> example = Example.of(filtro, matcher);
		PageRequest pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by(Direction.ASC, cdEmpreVstra));
		Page<EmpresaVistoriadora> findAll = franquiaRepository.findAll(example, pageable);
		if (findAll.isEmpty())
			throw new NoContentException();
		return findAll;
	}

	public EmpresaVistoriadora salvar(EmpresaVistoriadora franquia) {
		EmpresaVistoriadora f =	franquiaRepository.findByCdEmpreVstra(franquia.getCdEmpreVstra());
		if (f != null ) {
			throw new DuplicateResourceException();
		}
		return franquiaRepository.save(franquia);
	}
	
	public EmpresaVistoriadora atualizar(String codigoFranquia, EmpresaVistoriadora franquia) {
		
		EmpresaVistoriadora fr = franquiaRepository.findByCdEmpreVstra(codigoFranquia);
		
		fr.setCdAgrmtVspre(franquia.getCdAgrmtVspre());
		fr.setCdDddTelef(franquia.getCdDddTelef());
		fr.setCdEmail(franquia.getCdEmail());
		fr.setCdEmpreVstra(franquia.getCdEmpreVstra());
		fr.setCdSitucEmpreVstra(franquia.getCdSitucEmpreVstra());
		fr.setCdUsuroUltmaAlter(franquia.getCdUsuroUltmaAlter());
		fr.setDsCmploLogra(franquia.getDsCmploLogra());
		fr.setDtFimSelec(franquia.getDtFimSelec());
		fr.setDtInicoSelec(franquia.getDtInicoSelec());
		fr.setDtUltmaAlter(new Date());
		fr.setIcPagtoKmRdado(franquia.getIcPagtoKmRdado());
		fr.setNmBairr(franquia.getNmBairr());
		fr.setNmCidad(franquia.getNmCidad());
		fr.setNmContt(franquia.getNmContt());
		fr.setNmEmpreVstra(franquia.getNmEmpreVstra());
		fr.setNmLogra(franquia.getNmLogra());
		fr.setNrCep(franquia.getNrCep());
		fr.setNrCpfCnpj(franquia.getNrCpfCnpj());
		fr.setNrLogra(franquia.getNrLogra());
		fr.setNrRamal(franquia.getNrRamal());
		fr.setNrTelefEmpre(franquia.getNrTelefEmpre());
		fr.setQtKmFrqdo(franquia.getQtKmFrqdo());
		fr.setSgUniddFedrc(franquia.getSgUniddFedrc());
		fr.setTpPesoa(franquia.getTpPesoa());
		
		return franquiaRepository.save(fr);
	}
}