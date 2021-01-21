package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import br.com.tokiomarine.seguradora.aceitacao.rest.client.AcselXRestClient;
import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class DateService {

    private static final Logger LOGGER = LogManager.getLogger(VistoriaPreviaObrigatoriaService.class);

    @Autowired
    private AcselXRestClient acselXRestClient;

    public List<String> getDatasUteis(String mes, String ano) {

        try {
            Date data =  DateUtil.parseDataDDMMYYYY("01" + mes + ano);
            Integer ultimoDiaMes = DateUtil.getUltimoDiaDoMes(data);
            Calendar calendar = Calendar.getInstance();
            List<String> diasList = new ArrayList<>();

            for(int i = 1; i <= ultimoDiaMes; i++) {
                calendar.setTime(data);
                boolean isDataUtil = acselXRestClient.isFeriado(data);
                if(isDataUtil) {
                    diasList.add(DateUtil.formataDataDDMMYYYY(data));
                }
                data = DateUtil.calculaNovaData(data,0,0,1);
            }
            return diasList;
        } catch (Exception e) {
            LOGGER.error("Erro ao calcular data:" + e.getMessage());
            throw new InternalServerException("Erro ao executar metodo calculoData [AcselXRestClient.calculoData]. Erro: ", e);
        }
    }

    public int getDiferencaDiasUteis(Date dataTransmissao, Date dataVistoria,List<String> diasList) {

        GregorianCalendar gc = new GregorianCalendar();

        String sDataTransmissao = DateUtil.formataDataDDMMYYYY(dataTransmissao);
        String sDataVistoria = DateUtil.formataDataDDMMYYYY(dataVistoria);

        try {
            dataTransmissao =  DateUtil.parseDataDDMMYYYY(sDataTransmissao);
            dataVistoria = DateUtil.calculaNovaData(DateUtil.parseDataDDMMYYYY(sDataVistoria),0,0,1);
        } catch (ParseException e) {
            throw new InternalServerException("[DateService.getDiferencaDiasUteis] - Não foi possível realizar o parse da data: ", e);
        }

        gc.setTime(dataVistoria);

        if(sDataTransmissao.equals(sDataVistoria)) {
            return 0;
        }

        long diferencaEntreDias =  DateUtil.getDiferencaEmDias(dataTransmissao,dataVistoria);

        int quantidadeDiasAtraso  = 0;
        for(int i = 0; i <= diferencaEntreDias; i++) {

            if(diasList.contains(DateUtil.formataDataDDMMYYYY(dataVistoria))) {
                dataVistoria =   DateUtil.calculaNovaData(dataVistoria,0,0,1);
                continue;
            } else if(gc.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                quantidadeDiasAtraso = quantidadeDiasAtraso + 1;
            } else if (gc.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                quantidadeDiasAtraso = quantidadeDiasAtraso + 1;
            } else if (gc.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                quantidadeDiasAtraso = quantidadeDiasAtraso + 1;
            } else if (gc.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                quantidadeDiasAtraso = quantidadeDiasAtraso + 1;
            } else if (gc.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                quantidadeDiasAtraso = quantidadeDiasAtraso + 1;
            }

            // soma um na data
            dataVistoria = DateUtil.calculaNovaData(dataVistoria,0,0,1);

            // seta a nova data
            gc.setTime(dataVistoria);
        }

        return Math.min(quantidadeDiasAtraso, 99);
    }


}
