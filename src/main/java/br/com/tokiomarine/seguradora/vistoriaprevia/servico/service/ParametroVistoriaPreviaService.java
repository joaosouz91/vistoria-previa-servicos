package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.BusinessVPException;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ParametroVistoriaPreviaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class ParametroVistoriaPreviaService {

    private static final Logger LOGGER = LogManager.getLogger(ParametroVistoriaPreviaGeralService.class);

    @Autowired
    private ParametroVistoriaPreviaRepository repository;

    public ParametroVistoriaPrevia getParametroVistoriaPreviaPorModuloSelecionado(Long modulo) {
        try {
            final long finalModulo = modulo != null && modulo != 0L ? modulo : 7L;
            return repository.findByCdMduprAndDtProcessamento(finalModulo, DateUtil.parseData(DateUtil.formataData(new Date())))
                    .orElseThrow(() -> new BusinessVPException("Parâmetro não encontrado para o módulo de ID: " + finalModulo));
        } catch (ParseException e) {
            LOGGER.error("Formato de data inválido");
            throw new BusinessVPException("Formato de data inválido");
        }

    }

}
