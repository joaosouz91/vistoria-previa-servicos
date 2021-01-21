package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PostoVistoriaPrevia;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.pk.PostoVistoriaPreviaPK;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PostoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NoContentException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.NotFoundException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.PostoVistoriaPreviaRepository;

@Service
@Transactional
public class PostoVistoriaPreviaService {
	
	private static final Logger LOGGER = LogManager.getLogger(PostoVistoriaPreviaService.class);

	public static final Long CD_POSTO_APP_MOBILE = 999L;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PostoVistoriaPreviaRepository postoRepository;

	public Page<PostoDTO> obterPostos(PostoDTO filtro, Pageable page) {
		Page<PostoDTO> postosPage = postoRepository.obterPostos(filtro, page);

		if (postosPage.isEmpty())
			throw new NoContentException();

		return postosPage;
	}
	
	public List<PostoDTO> obterPostos(Long idVistoria, Long cdMunic, String bairro, boolean isCaminhao, boolean isAtivo) {
		List<PostoDTO> postos = postoRepository.obterPostosPorLocalizacao(cdMunic, bairro, isAtivo, isCaminhao);

		if (postos.isEmpty())
			throw new NoContentException();

		return postos;
	}

	public List<PostoDTO> obterPostos(String uf, String cidade, String bairro) {
		LOGGER.info("[obterPostos] filtro: " + uf + "/" + cidade + "/" + bairro);
		List<PostoDTO> postos = postoRepository.obterPostos(uf, cidade, bairro);

		if (postos.isEmpty() && StringUtils.isNotBlank(bairro)) {
			LOGGER.info("[obterPostos] nenhum posto encontrado. Pesquisando por uf e cidade...");
			postos = postoRepository.obterPostos(uf, cidade, null);
		}

		if (postos.isEmpty()) {
			LOGGER.warn("[obterPostos] nenhum posto encontrado.");
			throw new NoContentException();
		}

		return postos;
	}
	
	public List<PostoDTO> obterPostos(Long prestadora, String uf, String cidade) {
		LOGGER.info("[obterPostos] filtro: " + prestadora + "/" + uf + "/" + cidade);
		List<PostoDTO> postos = postoRepository.obterPostos(prestadora, uf, cidade, null);

		if (postos.isEmpty()) {
			LOGGER.warn("[obterPostos] nenhum posto encontrado.");
			throw new NoContentException();
		}

		return postos;
	}

	public List<String> obterCidadesPorPrestadoraEstado(Long cdAgrmtVspre, String sgUniddFedrc) {
		List<String> cidades = postoRepository.findCidadesPorPrestadoraEstado(cdAgrmtVspre, sgUniddFedrc);

		if (cidades.isEmpty())
			throw new NoContentException();

		return cidades;
	}

	public List<String> obterUfs(boolean isCaminhao, boolean isAtivo) {
		List<String> estados;

		Long ativo = isAtivo ? 1L : 0L;

		if (isCaminhao) {
			estados = postoRepository.findEstadosAtendeCaminhao(ativo);
		} else {
			estados = postoRepository.findEstados(ativo);
		}

		if (estados.isEmpty())
			throw new NoContentException();

		return estados;
	}
	
	public List<String> obterCidadesPorEstado(String sgUniddFedrc, boolean isCaminhao, boolean isAtivo) {
		List<String> cidades;

		Long ativo = isAtivo ? 1L : 0L;

		if (isCaminhao) {
			cidades = postoRepository.findCidadesAtendeCaminhaoPorEstado(sgUniddFedrc, ativo);
		} else {
			cidades = postoRepository.findCidadesPorEstado(sgUniddFedrc, ativo);
		}

		if (cidades.isEmpty())
			throw new NoContentException();

		return cidades.stream().map(c -> c.toUpperCase().replaceAll("['<>\\|/]", "")).distinct()
				.collect(Collectors.toList());
	}
	
	public List<String> recuperarListaBairroPorCidade(String uf, String cidade, boolean isCaminhao, boolean isAtivo) {

		List<String> bairros = postoRepository.findBairrosPorCidade(uf, cidade, isAtivo, isCaminhao);

		if (bairros.isEmpty())
			throw new NoContentException();

		return bairros;
	}

	public List<String> recuperarListaBairroPorCidade(Long idRegiaoAtnmtVstro, boolean isCaminhao, boolean isAtivo) {

		List<String> bairros = postoRepository.findBairrosPorCidade(idRegiaoAtnmtVstro, isAtivo, isCaminhao);

		if (bairros.isEmpty())
			throw new NoContentException();

		return bairros;
	}

	public PostoVistoriaPrevia atualizar(PostoVistoriaPreviaPK pk, PostoVistoriaPrevia posto) {
		PostoVistoriaPrevia pVP = postoRepository.findById(pk).orElseThrow(NotFoundException::new);

		pVP.setCdEmail(posto.getCdEmail());
		pVP.setCdSitucPosto(posto.getCdSitucPosto());
//		pVP.setCdUsuroUltmaAlter(posto.getCdUsuroUltmaAlter());
		pVP.setDsEnder(posto.getDsEnder());
		pVP.setDsHorrFuncm(posto.getDsHorrFuncm());
		pVP.setDsReferEnder(StringUtils.defaultIfBlank(posto.getDsReferEnder(), " "));
//		pVP.setDtFimSelec(posto.getDtFimSelec());
//		pVP.setDtInicoSelec(posto.getDtInicoSelec());
		pVP.setDtUltmaAlter(new Date());
		pVP.setIcAtndeCmhao(posto.getIcAtndeCmhao());
		pVP.setNmBairr(posto.getNmBairr());
		pVP.setNmCidad(posto.getNmCidad());
		pVP.setNmPostoVspre(posto.getNmPostoVspre());
		pVP.setNrCep(posto.getNrCep());
//		pVP.setNrLatit(posto.getNrLatit());
//		pVP.setNrLongt(posto.getNrLongt());
		pVP.setNrTelef(posto.getNrTelef());
		pVP.setSgUniddFedrc(posto.getSgUniddFedrc());

		return postoRepository.save(pVP);
	}

	public PostoDTO obterPosto(Long cdAgrmtVspre, Long cdPostoVspre) {
		return postoRepository.findPosto(cdAgrmtVspre, cdPostoVspre).map(p -> mapper.map(p, PostoDTO.class))
				.orElse(null);
	}
	
	public boolean contemPosto(Long idRegiao) {
		PostoVistoriaPrevia filtro = new PostoVistoriaPrevia();
		filtro.setIdRegiaoAtnmtVstro(idRegiao);
		filtro.setCdSitucPosto(1L);
		
		return postoRepository.exists(Example.of(filtro));
	}
	
	public boolean contemPosto(Long cdAgrmtVspre, Long cdPostoVspre) {
		PostoVistoriaPrevia filtro = new PostoVistoriaPrevia();
		filtro.setCdAgrmtVspre(cdAgrmtVspre);
		filtro.setCdPostoVspre(cdPostoVspre);
		filtro.setCdSitucPosto(1L);
		
		return postoRepository.exists(Example.of(filtro));
	}
}
