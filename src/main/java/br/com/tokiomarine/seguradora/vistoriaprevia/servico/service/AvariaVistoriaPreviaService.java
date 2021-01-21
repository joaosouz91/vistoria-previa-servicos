package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AvariaVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.AvariaVistoriaPreviaFilter;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.VistoriaPreviaServicoException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.AvariaVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.AvariaVistoriaPreviaRepositoryHql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class AvariaVistoriaPreviaService {

    @Autowired
    private AvariaVistoriaPreviaRepository repository;

    @Autowired
    private AvariaVistoriaPreviaRepositoryHql hqlRepository;

    @Autowired
    private UsuarioService usuarioService;


    public List<AvariaVistoriaPrevia> getAvariaVistoriaPreviaByFilter(AvariaVistoriaPreviaFilter filter) {
        trataFiltro(filter);
        return hqlRepository.getAvariaVistoriaPreviaByFilter(filter);
    }

    @Transactional
    public AvariaVistoriaPrevia criar(AvariaVistoriaPrevia model) {

        model.setDtUltmaAlter(new Date());
        model.setCdUsuroUltmaAlter(usuarioService.getUsuarioId());

        return repository.save(model);
    }

    @Transactional
    public AvariaVistoriaPrevia atualizar(String id, AvariaVistoriaPrevia model) {

        repository.findById(id).orElseThrow(() -> new VistoriaPreviaServicoException(500L, "Não foi possível encontrar o registro pelo ID informado."));

        model.setCdTipoAvari(id);
        model.setDtUltmaAlter(new Date());
        model.setCdUsuroUltmaAlter(usuarioService.getUsuarioId());

        return repository.save(model);
    }

    public void trataFiltro(AvariaVistoriaPreviaFilter filter) {

        if(filter.getSituacao() != null && filter.getSituacao().trim().equals("")) {
            filter.setSituacao(null);
        }

        if(filter.getDescricao() != null && filter.getDescricao().trim().equals("")) {
            filter.setDescricao(null);
        }
    }

}
