package br.com.tokiomarine.seguradora.ext.ssv.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ext.ssv.repository.ItemSeguradoRepository;
import br.com.tokiomarine.seguradora.ssv.transacional.model.ItemSegurado;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PropostaAgendamentoSSV;

@Service
public class ItemSeguradoService {

	@Autowired
	private ItemSeguradoRepository itemSeguradoRepository;

	public ItemSegurado save(ItemSegurado itemSegurado) {
		return itemSeguradoRepository.save(itemSegurado);
	}

	public ItemSegurado findByItemSegurado(Long numeroItemSegurado, String tipoHistorico) {
		return itemSeguradoRepository.findByItemSegurado(numeroItemSegurado, tipoHistorico);
	}

	public ItemSegurado findByItemSeguradoNumeroItem(Long numeroItemSegurado) {
		return itemSeguradoRepository.findByItemSeguradoNumeroItem(numeroItemSegurado);
	}

	public ItemSegurado findItemSeguradoPorEndossoRestricaoVP(Long codigoEndosso) {
		return itemSeguradoRepository.findItemSeguradoPorEndossoRestricaoVP(codigoEndosso);
	}
	
	public Optional<ItemSegurado> obterItemPorEndossoItem(Long codEndosso, Long numItemSegurado) {
		return itemSeguradoRepository.findByCodEndossoAndNumItemSegurado(codEndosso, numItemSegurado);
	}

	public Optional<ItemSegurado> obterItemPorEndosso(Long codEndosso) {
		return itemSeguradoRepository.findByCodEndosso(codEndosso);
	}
	
	public boolean endossoEstaNaGrade(String codLaudoVistoriaPrevia) {
		return itemSeguradoRepository.endossoEstaNaGrade(codLaudoVistoriaPrevia);
	}
	
	public boolean negocioEstaNaGrade(String codLaudoVistoriaPrevia) {
		return itemSeguradoRepository.negocioEstaNaGrade(codLaudoVistoriaPrevia);
	}

	public List<PropostaAgendamentoSSV> findItemSegurado(String chassi, String placa, Long corretor,
			Date dataLimitePesquisa) {

		if (corretor == null || corretor.intValue() == 0) {
			return itemSeguradoRepository.getItemSeguradoByPlacaOrChassiAgendamento(chassi, placa, dataLimitePesquisa);
		}
		
		return itemSeguradoRepository.getItemSeguradoByCorretorPlacaOrChassiAgendamento(chassi, placa, corretor, dataLimitePesquisa);
	}
}
