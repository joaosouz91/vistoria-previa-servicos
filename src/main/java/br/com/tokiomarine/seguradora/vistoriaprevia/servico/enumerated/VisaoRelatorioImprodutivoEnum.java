package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;

@Getter
@JsonFormat(shape = Shape.OBJECT)
public enum VisaoRelatorioImprodutivoEnum {

	COD_VISAO_GERAL("G", "Geral"),
	COD_VISAO_SUPERINTENDENCIA("SI", "Superintendencia"),
	COD_VISAO_SUCURSAL("S", "Sucursal"),
	COD_VISAO_CORRETOR("C", "Corretor"),
	COD_VISAO_VISTORIA_PREVIA("VP", "Vistoria Prévia"),
	COD_VISAO_VEICULO("VL", "Veículo");
	
	private String value;
	private String label;

	private VisaoRelatorioImprodutivoEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}

	@JsonCreator
	public static VisaoRelatorioImprodutivoEnum forValues(String value) {
		for (VisaoRelatorioImprodutivoEnum e : VisaoRelatorioImprodutivoEnum.values()) {
			if (e.getValue().equals(value)) {
				return e;
			}
		}

		return null;
	}
}
