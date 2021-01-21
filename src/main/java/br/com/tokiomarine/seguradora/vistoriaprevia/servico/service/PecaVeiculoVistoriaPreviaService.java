package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;


import br.com.tokiomarine.seguradora.ssv.vp.crud.model.PecaVeiculoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.PecaVeiculoVistoriaPreviaFilter;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.PecaVeiculoVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.PecaVeiculoVistoriaPreviaRepositoryHql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class PecaVeiculoVistoriaPreviaService {

    @Autowired
    private PecaVeiculoVistoriaPreviaRepositoryHql hqlRepository;

    @Autowired
    private PecaVeiculoVistoriaPreviaRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    public List<PecaVeiculoVistoriaPrevia> getVistoriaPreviaByFilter(PecaVeiculoVistoriaPreviaFilter filter){
        trataFiltro(filter);
        return hqlRepository.getPecaVeiculoVistoriaPreviaByFilter(filter);
    }

    @Transactional
    public PecaVeiculoVistoriaPrevia criar(PecaVeiculoVistoriaPrevia model){

        model.setDtUltmaAlter(new Date());
        model.setCdUsuroUltmaAlter(usuarioService.getUsuarioId());

        return repository.save(model);
    }

    @Transactional
    public void atualizar(Long id, PecaVeiculoVistoriaPrevia model){

        repository.findById(id).orElseThrow(() -> new BusinessVPException("ID não existente no banco de dados."));

        model.setCdPecaAvada(id);
        model.setDtUltmaAlter(new Date());
        model.setCdUsuroUltmaAlter(usuarioService.getUsuarioId());

        repository.save(model);
    }

    public void trataFiltro(PecaVeiculoVistoriaPreviaFilter filter) {

        if(filter.getSituacao() != null && filter.getSituacao().trim().equals("")) {
            filter.setSituacao(null);
        }

        if(filter.getDescricao() != null && filter.getDescricao().trim().equals("")) {
            filter.setDescricao(null);
        }
    }

}
