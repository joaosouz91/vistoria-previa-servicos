package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio;

import java.time.LocalDate;
import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.StatusParecerEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.VisaoRelatorioImprodutivoEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class RelatorioImprodutivoFiltro {

	private VisaoRelatorioImprodutivoEnum visao;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM", timezone = "GMT-3")
	private YearMonth dataReferencia;
	private Long superintendencia;
	private Long sucursal;
	private Long corretor;
	private String laudo;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-3")
	private LocalDate dataVistoria;
	private String voucher;
	private StatusParecerEnum parecerTecnico;
	private String placa;
	private String chassi;
}
