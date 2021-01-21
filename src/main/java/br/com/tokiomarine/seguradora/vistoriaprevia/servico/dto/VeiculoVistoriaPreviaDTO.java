package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonPluralRootName;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.CombustivelEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoMaterialCarroceriaEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;


@Data
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
@JsonRootName("veiculo")
@JsonPluralRootName("veiculo")
@JsonInclude(Include.NON_NULL)
public class VeiculoVistoriaPreviaDTO  extends ResourceSupport implements Serializable {
	
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("codigo_bomba_injetora")
	private String cdBombInjetVeicu;
	
	@JsonProperty("codigo_Cambio_veiculo")
	private String cdCamboVeicu;
	
	@JsonProperty("codigo_veiculo")
	private String cdCarroVeicu;
	
	@JsonProperty("codigo_chassi")
	private String cdChassiVeicu;
	
	@JsonProperty("dut_veiculo")
	private String cdDutVeicu;
	
	@JsonProperty("codigo_eixo_diant")
	private String cdEixoDiantVeicu;
	
	@JsonProperty("codigo_eixo_tras")
	private String cdEixoTrsroVeicu;
	
	@JsonProperty("codigo_vistoria")
	private String cdLvpre;
	
	@JsonProperty("codigo_motor")
	private String cdMotorVeicu;
	
	@JsonProperty("origem_motor")
	private String cdOrigmChassi;
	
	@JsonProperty("placa_veiculo")
	private String cdPlacaVeicu;
	
	@JsonProperty("renavam")
	private String cdRenavam;
	
	@JsonProperty("cor_veiculo")
	private String dsCorVeicu;
	
	@JsonProperty("marca_carro_carga")
	private String dsMarcaCarroCarga;
	
	@JsonProperty("modelo_veiculo")
	private String dsModelVeicu;
	
	@JsonProperty("icRodoar")
	private String icRodoar;
	
	@JsonProperty("icVeicuCarga")
	private String icVeicuCarga;
	
	@JsonProperty("icVeicuTrafm")
	private String icVeicuTrafm;
	
	@JsonProperty("nmAlienVeicu")
	private String nmAlienVeicu;
	
	@JsonProperty("cidade_dut")
	private String nmCidadExpdcDut;
	
	@JsonProperty("nmDutVeicu")
	private String nmDutVeicu;
	
	@JsonProperty("qtLotacVeicu")
	private String qtLotacVeicu;
	
	
	@JsonProperty("uf_dut")
	private String sgUniddFedrcDut;
	
	@JsonProperty("tpCmbst")
	private String tpCmbst;
	
	@JsonProperty("tpPnturVeicu")
	private String tpPnturVeicu;
	
	@JsonProperty("codigo_fabricante")
	private Long cdFabrt;
	
	@JsonProperty("cdFormaCarroVeicu")
	private Long cdFormaCarroVeicu;
	
	@JsonProperty("codigo_modelo_veiculo")
	private Long cdModelVeicu;
	
	@JsonProperty("ano_fab_veiculo")
	private Long nrAnoFabrcVeicu;
	
	@JsonProperty("ano_modelo_veiculo")
	private Long nrAnoModelVeicu;
	
	@JsonProperty("cnpj_dut")
	private Long nrCnpjDut;
	
	@JsonProperty("cpf_dut")
	private Long nrCpfDut;
	
	@JsonProperty("nrVrsaoLvpre")
	private Long nrVrsaoLvpre;
	
	@JsonProperty("qnts_cilindrada")
	private Long qtCilinVeicu;
	
	@JsonProperty("qntd_km_rodado")
	private Long qtKmRdadoVeicu;
	
	@JsonProperty("qntd_marcha")
	private Long qtMrchaVeicu;
	
	@JsonProperty("qtPortaVeicu")
	private Long qtPortaVeicu;
	
	@JsonProperty("statuDecodChasis")
	private Long statuDecodChasis;
	
	@JsonProperty("tpCabinVeicu")
	private Long tpCabinVeicu;
	
	@JsonProperty("tpCamboVeicu")
	private Long tpCamboVeicu;
	
	@JsonProperty("tpCarroVeicu")
	private Long tpCarroVeicu;
	
	@JsonProperty("tpMtralCargaVeicu")
	private Long tpMtralCargaVeicu;
	
	@JsonProperty("tpUtlzcVeicu")
	private Long tpUtlzcVeicu;
	
	@JsonProperty("data_exp_dut")
	private Date dtExpdcDut;
	
	@JsonProperty("dtFimSelec")
	private Date dtFimSelec;
	
	@JsonProperty("dtInicoSelec")
	private Date dtInicoSelec;
	
	@JsonProperty("DESCRICAO_Cmbst")
	private String getDescricaoCmbst() {
		return CombustivelEnum.obterDescricao(tpCmbst);
	}
	
	@JsonProperty("DESCRICAO_MtralCargaVeicu")
	private String getDescricaoMtralCargaVeicu() {
		return TipoMaterialCarroceriaEnum.obterDescricao(tpMtralCargaVeicu);
	}
}
