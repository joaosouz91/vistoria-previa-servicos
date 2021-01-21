package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.auth.UsuarioService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.filter.ConteudoColunaTipoFilter;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.MotivoDispensaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MotivoDispensaService {

    private static final String COLUNA_TIPO_MOTIVO_DISPENSA_VISTORIA_PREVIA = "TP_MOTV_DISPE_VSTRI";

    @Autowired
    private MotivoDispensaRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    public List<ConteudoColunaTipo> getMotivoDispensaByFilter(ConteudoColunaTipoFilter filter) {

        trataFiltro(filter);

        if(filter.getDescricao() == null) {
            return repository.findAll(COLUNA_TIPO_MOTIVO_DISPENSA_VISTORIA_PREVIA)
                    .stream()
                    .sorted(Comparator.comparingLong(a -> Long.parseLong(a.getVlCntdoColunTipo())))
                    .collect(Collectors.toList());
        }

        if(filter.getDescricao() != null) {
            return repository.findMotivoDispensaByDsCoptaColunTipo(COLUNA_TIPO_MOTIVO_DISPENSA_VISTORIA_PREVIA, filter.getDescricao())
                    .stream()
                    .sorted(Comparator.comparingLong(a -> Long.parseLong(a.getVlCntdoColunTipo())))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public ConteudoColunaTipo getMotivoDispensaById(Long id) {
        String strId = addZeroToLongSizeOne(id);
        return repository.findById(COLUNA_TIPO_MOTIVO_DISPENSA_VISTORIA_PREVIA, strId)
                    .orElseThrow(() -> new BusinessVPException("ID não existente no banco de dados."));
    }

    @Transactional
    public ConteudoColunaTipo criar(ConteudoColunaTipo model) {

        Optional<Long> optionalMaxId = repository.findAll(COLUNA_TIPO_MOTIVO_DISPENSA_VISTORIA_PREVIA)
                .stream()
                .map(c -> Long.parseLong(c.getVlCntdoColunTipo()))
                .max(Comparator.comparingLong(a -> a));

        String newIdStr;

        if(optionalMaxId.isPresent()) {
            Long newIdLong = optionalMaxId.get() + 1;
            newIdStr = addZeroToLongSizeOne(newIdLong);
        } else {
            newIdStr = "01";
        }

        model.setVlCntdoColunTipo(newIdStr);
        model.setNmColunTipo(COLUNA_TIPO_MOTIVO_DISPENSA_VISTORIA_PREVIA);
        model.setSgSistmInfor("SSV");
        model.setDsRmidaColunTipo(model.getDsCoptaColunTipo());
        model.setDtUltmaAlter(new Date());
        model.setCdUsuroUltmaAlter(usuarioService.getUsuarioId());

        return repository.save(model);
    }

    @Transactional
    public void atualizar(String id, ConteudoColunaTipo model) {

        ConteudoColunaTipo c = repository.findById(COLUNA_TIPO_MOTIVO_DISPENSA_VISTORIA_PREVIA, id).orElseThrow(() -> new BusinessVPException("ID não existente no banco de dados."));

        c.setDsCoptaColunTipo(model.getDsCoptaColunTipo());
        c.setDsRmidaColunTipo(model.getDsCoptaColunTipo());
        c.setDtUltmaAlter(new Date());
        c.setCdUsuroUltmaAlter(usuarioService.getUsuarioId());

        repository.save(c);
    }

    public void trataFiltro(ConteudoColunaTipoFilter filter) {

        if(filter.getDescricao() != null && filter.getDescricao().trim().equals("")) {
            filter.setDescricao(null);
        }
    }

    private String addZeroToLongSizeOne(Long maxId) {
        if(maxId.toString().length() == 1) {
            return "0" + maxId;
        }
        return maxId.toString();
    }
}
