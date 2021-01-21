package br.com.tokiomarine.seguradora.vistoriaprevia.servico.regra.agendamento;

import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoAgendamentoNaoPermitidoEnum.MOTIVO_VP_DISPENSADA;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoAgendamentoNaoPermitidoEnum.MOTIVO_VP_NAO_NECESSARIA;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SistemaChamador.AGTOSEMPROPOSTATMS;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SistemaChamador.ENDOSSOCTF;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SistemaChamador.GERACAORESTRICAO;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SistemaChamador.isEndossoSSV;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SistemaChamador.isPlataforma;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoHistoricoEnum.TIPO_HISTORICO_ITEM;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.ext.act.service.RestricaoService;
import br.com.tokiomarine.seguradora.ext.ssv.service.ItemSeguradoService;
import br.com.tokiomarine.seguradora.ssv.transacional.model.ItemSegurado;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;

@Component
public class SistemaOrigemNaoPLTCommand implements Command {
	
	private ItemSeguradoService itemSeguradoService;
	private RestricaoService restricaoService;
	
	public SistemaOrigemNaoPLTCommand(ItemSeguradoService itemSeguradoService, RestricaoService restricaoService) {
		this.itemSeguradoService=itemSeguradoService;
		this.restricaoService=restricaoService;
	}

	@Override
	public boolean execute(Context context) throws Exception {
		PermiteAgendamentoContext permite = (PermiteAgendamentoContext) context;
		VistoriaPreviaObrigatoria vp = permite.getVistoriaPrevia();
		
		if (!isPlataforma(vp.getCdSistmOrigm())) {

			ItemSegurado itemSegurado = obterItemSegurado(permite);

			// Passa a verificar a flag de VP no item caso o agendamento seja diferente do
			// sem proposta (agendamento antecipado) - (16/06/2015).
			if (vp.getNrItseg() != null && itemSegurado != null && "N".equals(itemSegurado.getIcVspre())
					&& !vp.getCdSistmOrigm().equals(AGTOSEMPROPOSTATMS)) {

				permite.addLog("Item segurado não necessita de VP. Não permite agendamento.");
				permite.setPermiteAgendamento(false);
				permite.setMotivoAgendamentoNaoPermitido(MOTIVO_VP_NAO_NECESSARIA);
				
				return true;
			}

			// * Vistoria Prévia dispensada via sistema.
			if (itemSegurado != null && restricaoService.verificaVpDispensada(itemSegurado)) {

				permite.addLog("VP dispensada via sistema. Não permite agendamento.");
				
				permite.setPermiteAgendamento(false);
				permite.setMotivoAgendamentoNaoPermitido(MOTIVO_VP_DISPENSADA);
				
				return true;
			}
		}
		
		return false;
	}

	private ItemSegurado obterItemSegurado(PermiteAgendamentoContext permite) {
		VistoriaPreviaObrigatoria vp = permite.getVistoriaPrevia();
		ItemSegurado itemSegurado = null;
		
		if (isEndossoSSV(vp.getCdSistmOrigm())
				|| (vp.getCdSistmOrigm().equals(GERACAORESTRICAO) && vp.getCdEndos() != null)) {

			permite.addLog("Sistema origem Endosso SSV.");

			if (ENDOSSOCTF.equals(vp.getCdSistmOrigm())
					|| (vp.getCdSistmOrigm().equals(GERACAORESTRICAO) && vp.getCdEndos() != null)) {
				itemSegurado = itemSeguradoService.obterItemPorEndossoItem(vp.getCdEndos(), vp.getNrItseg())
						.orElse(null);
			} else { // quando CTF pesquisa por item
				itemSegurado = itemSeguradoService.obterItemPorEndosso(vp.getCdEndos()).orElse(null);
			}

		} else if (vp.getNrItseg() != null) {

			permite.addLog("Pré-agendamento possui item segurado. Buscando o item na SSV0076...");
			itemSegurado = itemSeguradoService.findByItemSegurado(vp.getNrItseg(), TIPO_HISTORICO_ITEM.getCodigo());
		}
		
		return itemSegurado;
	}

}
