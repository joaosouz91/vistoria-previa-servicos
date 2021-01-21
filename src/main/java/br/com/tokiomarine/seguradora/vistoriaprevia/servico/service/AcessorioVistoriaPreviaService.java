package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AcessorioVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.AcessorioVistoriaPreviaFilter;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.AcessorioVistoriaPreviaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AcessorioVistoriaPreviaService {

    @Autowired
    private AcessorioVistoriaPreviaRepository repository;

    public List<AcessorioVistoriaPrevia> getAcessorioVistoriaPreviaListByFilter(AcessorioVistoriaPreviaFilter filter){

        trataFiltro(filter);

        if(filter.getDescricao() != null && filter.getTipoAcessorio() == null && filter.getSituacao() == null) {
            return repository.findAcessorioVistoriaPreviaByDsAcsroVspreContainingIgnoreCase(filter.getDescricao());
        }

        if(filter.getDescricao() == null && filter.getTipoAcessorio() != null && filter.getSituacao() == null) {
            return repository.findAcessorioVistoriaPreviaByTpAcsroVspre(filter.getTipoAcessorio());
        }

        if(filter.getDescricao() == null && filter.getTipoAcessorio() == null && filter.getSituacao() != null) {
            return repository.findAcessorioVistoriaPreviaByCdSitucAcsroVspre(filter.getSituacao());
        }

        if(filter.getDescricao() != null && filter.getTipoAcessorio() != null && filter.getSituacao() == null) {
            return repository.findAcessorioVistoriaPreviaByDsAcsroVspreContainingIgnoreCaseAndTpAcsroVspre(filter.getDescricao(), filter.getTipoAcessorio());
        }

        if(filter.getDescricao() == null && filter.getTipoAcessorio() != null && filter.getSituacao() != null) {
            return repository.findAcessorioVistoriaPreviaByCdSitucAcsroVspreAndTpAcsroVspre(filter.getSituacao(), filter.getTipoAcessorio());
        }

        if(filter.getDescricao() != null && filter.getTipoAcessorio() == null && filter.getSituacao() != null) {
            return repository.findAcessorioVistoriaPreviaByCdSitucAcsroVspreAndDsAcsroVspreContainingIgnoreCase(filter.getSituacao(), filter.getDescricao());
        }

        if(filter.getDescricao() != null && filter.getTipoAcessorio() != null && filter.getSituacao() != null) {

            return repository.findAcessorioVistoriaPreviaByDsAcsroVspreContainingIgnoreCaseAndTpAcsroVspreAndCdSitucAcsroVspre(
                    filter.getDescricao(),
                    filter.getTipoAcessorio(),
                    filter.getSituacao());
        }

        return repository.findAll();
    }

    @Transactional
    public AcessorioVistoriaPrevia criar(AcessorioVistoriaPrevia model){
        return repository.save(model);
    }

    @Transactional
    public void atualizar(Long id, AcessorioVistoriaPrevia model){

        AcessorioVistoriaPrevia a = repository.findById(id).orElseThrow(() -> new BusinessVPException("ID não existente no banco de dados."));

        a.setDsAcsroVspre(model.getDsAcsroVspre());
        a.setCdSitucAcsroVspre(model.getCdSitucAcsroVspre());
        a.setTpAcsroVspre(model.getTpAcsroVspre());

        repository.save(a);
    }

    public void trataFiltro(AcessorioVistoriaPreviaFilter filter) {

        if(filter.getSituacao() != null && filter.getSituacao().trim().equals("")) {
            filter.setSituacao(null);
        }

        if(filter.getDescricao() != null && filter.getDescricao().trim().equals("")) {
            filter.setDescricao(null);
        }

        if(filter.getTipoAcessorio() != null && filter.getTipoAcessorio().trim().equals("")) {
            filter.setTipoAcessorio(null);
        }
    }

}
