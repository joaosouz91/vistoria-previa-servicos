package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LoteLaudoImprodutivo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity.LaudoImprodutivo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoteImprodutivoDTO {

	LoteLaudoImprodutivo lote = new LoteLaudoImprodutivo();

	List<LaudoImprodutivo> laudosImprodutivos = new ArrayList<LaudoImprodutivo>();
	
	public LoteImprodutivoDTO(LoteLaudoImprodutivo loteLaudoImprodutivo) {
		this.lote = loteLaudoImprodutivo;
	}

	@Override
	public boolean equals(Object o) {
		
		if (o == null) {
			return false;
		}
		
		if (!(o instanceof LoteImprodutivoDTO)) {
			return false;
		}
		
		LoteImprodutivoDTO outroLote = (LoteImprodutivoDTO) o;
		
		
		if (outroLote.getLote().getIdLoteLaudoImpdv() != null && lote.getIdLoteLaudoImpdv() != null) {
			return outroLote.getLote().getIdLoteLaudoImpdv().equals(lote.getIdLoteLaudoImpdv());
		}
		
		
		if (outroLote.getLote().getMmRefer().equals(lote.getMmRefer())
				&& outroLote.getLote().getAaRefer().equals(lote.getAaRefer())
				&& outroLote.getLote().getCdCrtorSegur().equals(lote.getCdCrtorSegur())) {
			return true;
		}
		
		return false;
	}
}
