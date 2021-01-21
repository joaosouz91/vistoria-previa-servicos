package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PrestadoraDTO.Fields.nmRazaoSocal;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PrestadoraVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PrestadoraDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.DuplicateResourceException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NoContentException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NotFoundException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.PrestadoraVistoriaPreviaRepository;

@Service
@Transactional
public class PrestadoraVistoriaPreviaService {

	@Autowired
	private ModelMapper mapper;	
	
	@Autowired
	private PrestadoraVistoriaPreviaRepository prestadoraRepository;
	
	public PrestadoraVistoriaPrevia salvar(PrestadoraVistoriaPrevia prestadora) {
		boolean exists = prestadoraRepository.existsById(prestadora.getCdAgrmtVspre());
		if (exists) {
			throw new DuplicateResourceException();
		}

//		prestadora.setCdUsuroUltmaAlter(cdUsuroUltmaAlter);t
		prestadora.setDtCadmt(new Date());

		return prestadoraRepository.save(prestadora);
	}

	public PrestadoraVistoriaPrevia atualizar(Long codigoPrestadora, PrestadoraVistoriaPrevia prestadora) {
		PrestadoraVistoriaPrevia pVP = prestadoraRepository.findById(codigoPrestadora)
				.orElseThrow(NotFoundException::new);

		pVP.setCdDddTelef(prestadora.getCdDddTelef());
		pVP.setCdEmail(prestadora.getCdEmail());
		pVP.setCdLetraPrtraVouch(prestadora.getCdLetraPrtraVouch());
//		pVP.setCdUsuroUltmaAlter(prestadora.getCdUsuroUltmaAlter());
		pVP.setDsCmploLogra(prestadora.getDsCmploLogra());
		pVP.setDsSite(prestadora.getDsSite());
		pVP.setDtDesat(prestadora.getDtDesat());
//		pVP.setDtFimSelec(prestadora.getDtFimSelec());
		pVP.setDtInicoAtivd(prestadora.getDtInicoAtivd());
//		pVP.setDtInicoSelec(prestadora.getDtInicoSelec());
		pVP.setDtUltmaAlter(new Date());
		pVP.setNmBairr(prestadora.getNmBairr());
		pVP.setNmCidad(prestadora.getNmCidad());
		pVP.setNmContt(prestadora.getNmContt());
		pVP.setNmLogra(prestadora.getNmLogra());
		pVP.setNmRazaoSocal(prestadora.getNmRazaoSocal());
		pVP.setNrCep(prestadora.getNrCep());
		pVP.setNrCpfCnpj(prestadora.getNrCpfCnpj());
		pVP.setNrLogra(prestadora.getNrLogra());
		pVP.setNrRamal(prestadora.getNrRamal());
		pVP.setNrTelefEmpre(prestadora.getNrTelefEmpre());
		pVP.setSgUniddFedrc(prestadora.getSgUniddFedrc());
		pVP.setTpPesoa(prestadora.getTpPesoa());

		return prestadoraRepository.save(pVP);
	}

	public Page<PrestadoraVistoriaPrevia> listaPrestadoraVistoriaPrevia(PrestadoraVistoriaPrevia filtro,
			Pageable page) {

		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.STARTING).withIgnoreCase();

		Example<PrestadoraVistoriaPrevia> example = Example.of(filtro, matcher);

		PageRequest pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(),
				Sort.by(Direction.ASC, nmRazaoSocal));

		Page<PrestadoraVistoriaPrevia> findAll = prestadoraRepository.findAll(example, pageable);

		if (findAll.isEmpty())
			throw new NoContentException();

		return findAll;
	}
	
	public PrestadoraVistoriaPrevia obterPrestadoraPorId(Long cdAgrmtVspre) {
		return prestadoraRepository.findById(cdAgrmtVspre).orElseThrow(NotFoundException::new);
	}

	public List<PrestadoraVistoriaPrevia> obterTodas(Boolean ativo) {

		List<PrestadoraVistoriaPrevia> findAll = prestadoraRepository.findAll();

		if (findAll.isEmpty()) throw new NoContentException();

		if(ativo != null && ativo) {
			findAll = findAll.stream()
					.filter(prestadoraVistoriaPrevia -> prestadoraVistoriaPrevia.getDtDesat() == null)
					.collect(Collectors.toList());
		}

		return findAll;
	}

	public PrestadoraDTO obterPrestadora(Long cdAgrmtVspre) {
		Optional<PrestadoraVistoriaPrevia> prestadora = prestadoraRepository.findById(cdAgrmtVspre);

		return prestadora.map(p -> mapper.map(p, PrestadoraDTO.class)).orElse(null);
	}

	public Optional<PrestadoraVistoriaPrevia> obterPrestadoraPorAgendamento(String voucher) {
		return prestadoraRepository.findPrestadoraPorAgendamento(voucher);
	}

	public List<PrestadoraDTO> obterPrestadoras(Iterable<Long> codigos) {
		List<PrestadoraVistoriaPrevia> prestadoras = prestadoraRepository.findAllById(codigos);
		
		return prestadoras.stream().map(p -> mapper.map(p, PrestadoraDTO.class)).collect(Collectors.toList());
	}
}
