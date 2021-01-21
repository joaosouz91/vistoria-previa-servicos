package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ValorDescricaoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioImprodutividadeCorretorDTO implements Serializable {

	private static final long serialVersionUID = -4234250885845592680L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "GMT-3")
	private YearMonth dataReferencia;
	private String descricaoDataReferencia;
	
	private Double valorTotalImprodutivoFinal = 0d;
	private Double valorTotalImprodutivoOriginal = 0d;
	private Double valorTotalLaudoCalculado = 0d;
	private Double valorTotalLaudoIncluido = 0d;
	private Double valorTotalLaudoEstornado = 0d;
	
	private Long qtdTotalImprodutivoOriginal = 0l;
	private Long qtdTotalLaudoCalculado = 0l;
	private Long qtdTotalLaudoIncluido = 0l;
	private Long qtdTotalLaudoEstornado = 0l;
	
	private LoteLaudoImprodutivoAux loteImprodutivoAux;	

	private boolean permiteEdicao;
	private boolean permiteTransmissao;
	private boolean exibirPesquisaAdicional;
	
	private List<ValorDescricaoDTO> listaMesAnoAdicional;
}
