package br.com.tokiomarine.seguradora.ext.ssv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ext.ssv.repository.DescricaoItemSeguradoRepository;
import br.com.tokiomarine.seguradora.ssv.transacional.model.DescricaoItemSegurado;

@Service
public class DescricaoItemSeguradoService {

	@Autowired
	private DescricaoItemSeguradoRepository descricaoItemSeguradoRepository;

	public List<DescricaoItemSegurado> findDescricaoItemSeguradoByNrItsegTpHisto(Long numItemSegurado,
			String tipoHistorico) {
		return descricaoItemSeguradoRepository.findDescricaoItemSeguradoByNrItsegTpHisto(numItemSegurado,
				tipoHistorico);
	}
}
