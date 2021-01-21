package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PostoDTO;

public interface PostoVistoriaPreviaRepositoryCustom {

	List<String> findBairrosPorCidade(String uf, String cidade, boolean isAtivo, boolean isCaminhao);

	List<String> findBairrosPorCidade(Long idRegiaoAtnmtVstro, boolean isAtivo, boolean isCaminhao);

	List<PostoDTO> obterPostosPorLocalizacao(Long idRegiaoAtnmtVstro, String bairro, boolean isAtivo,
			boolean isCaminhao);

	List<PostoDTO> obterPostos(String uf, String cidade, String bairro);

	List<PostoDTO> obterPostos(Long prestadora, String uf, String cidade, String bairro);

	Page<PostoDTO> obterPostos(PostoDTO filtro, Pageable pageable);
}
