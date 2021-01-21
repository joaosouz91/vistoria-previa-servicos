package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AvariaLaudoVistoriaPrevia;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class AjusteParametros implements Serializable {
    	
	private static final long serialVersionUID = -6367310687375705763L;

	@EqualsAndHashCode.Include
	private Long iCdVistoriadoraVp;
	private String iNoLaudoVp;
	@EqualsAndHashCode.Include
	private Long numVersaoLaudoVistoriaPrevia;
	private String iStLaudoVp;
	private String iTpVistoriadorVp;
	private Long iCdFinalidadeVp;
	private Date iDtRealizacaoVp;
	private Long iNoAnoFabLaudoVp;
	private Long iNoAnoModLaudoVp;
	private String iNmAlienadoVp;
	private String iCdCambioVp;
	private Long iTpCambioVp;
	private String iIdRodoar;
	private Long iTpUtilizacaoVp;
	private String iIdVeiculoCargaVp;
	private String iCdCarroceriaVp;
	private Long iNoCpfCrlvVp;
	private Long iTpCarroceriaVp;
	private Long iNoCnpjCrlvVp;
	private String iIdTransformadoVp;
	private String iNoPlacaLaudoVp;
	private String iNoChassiLaudoVp;
	private Long iCdModeloLaudoVp;
	private String iCdOrigemChassiVp;
	private Long iCdFabricanteLaudoVp;
	private Long iNoCepProponenteVp;
	private String iTpCombust;
	private String qtLotacVeicuVp;
	private Long qtdKmRdadoVeicu;
	private Long statuDecodChasis;	
	private List<Long> codParecerTecnicoVPList;	
	private List<Long> codAcessoriosLaudoVPList;
	private List<AvariaLaudoVistoriaPrevia> avariaLaudoVPList;
	private String iChassiRemarcadoVp;
	private String iCabineSuplementarVp;
	private String iQuartoEixoVp;
	private String descricaoLinkPrestador;
	private String nomePrestador;
	
	public Long getiCdVistoriadoraVp() {
		return iCdVistoriadoraVp;
	}

	public void setiCdVistoriadoraVp(Long iCdVistoriadoraVp) {
		this.iCdVistoriadoraVp = iCdVistoriadoraVp;
	}

	public String getiNoLaudoVp() {
		return iNoLaudoVp;
	}

	public void setiNoLaudoVp(String iNoLaudoVp) {
		this.iNoLaudoVp = iNoLaudoVp;
	}

	public Long getNumVersaoLaudoVistoriaPrevia() {
		return numVersaoLaudoVistoriaPrevia;
	}

	public void setNumVersaoLaudoVistoriaPrevia(Long numVersaoLaudoVistoriaPrevia) {
		this.numVersaoLaudoVistoriaPrevia = numVersaoLaudoVistoriaPrevia;
	}

	public String getiStLaudoVp() {
		return iStLaudoVp;
	}

	public void setiStLaudoVp(String iStLaudoVp) {
		this.iStLaudoVp = iStLaudoVp;
	}

	public String getiTpVistoriadorVp() {
		return iTpVistoriadorVp;
	}

	public void setiTpVistoriadorVp(String iTpVistoriadorVp) {
		this.iTpVistoriadorVp = iTpVistoriadorVp;
	}

	public Long getiCdFinalidadeVp() {
		return iCdFinalidadeVp;
	}

	public void setiCdFinalidadeVp(Long iCdFinalidadeVp) {
		this.iCdFinalidadeVp = iCdFinalidadeVp;
	}

	public Date getiDtRealizacaoVp() {
		return iDtRealizacaoVp;
	}

	public void setiDtRealizacaoVp(Date iDtRealizacaoVp) {
		this.iDtRealizacaoVp = iDtRealizacaoVp;
	}

	public Long getiNoAnoFabLaudoVp() {
		return iNoAnoFabLaudoVp;
	}

	public void setiNoAnoFabLaudoVp(Long iNoAnoFabLaudoVp) {
		this.iNoAnoFabLaudoVp = iNoAnoFabLaudoVp;
	}

	public Long getiNoAnoModLaudoVp() {
		return iNoAnoModLaudoVp;
	}

	public void setiNoAnoModLaudoVp(Long iNoAnoModLaudoVp) {
		this.iNoAnoModLaudoVp = iNoAnoModLaudoVp;
	}

	public String getiNmAlienadoVp() {
		return iNmAlienadoVp;
	}

	public void setiNmAlienadoVp(String iNmAlienadoVp) {
		this.iNmAlienadoVp = iNmAlienadoVp;
	}

	public String getiCdCambioVp() {
		return iCdCambioVp;
	}

	public void setiCdCambioVp(String iCdCambioVp) {
		this.iCdCambioVp = iCdCambioVp;
	}

	public Long getiTpCambioVp() {
		return iTpCambioVp;
	}

	public void setiTpCambioVp(Long iTpCambioVp) {
		this.iTpCambioVp = iTpCambioVp;
	}

	public String getiIdRodoar() {
		return iIdRodoar;
	}

	public void setiIdRodoar(String iIdRodoar) {
		this.iIdRodoar = iIdRodoar;
	}

	public Long getiTpUtilizacaoVp() {
		return iTpUtilizacaoVp;
	}

	public void setiTpUtilizacaoVp(Long iTpUtilizacaoVp) {
		this.iTpUtilizacaoVp = iTpUtilizacaoVp;
	}

	public String getiIdVeiculoCargaVp() {
		return iIdVeiculoCargaVp;
	}

	public void setiIdVeiculoCargaVp(String iIdVeiculoCargaVp) {
		this.iIdVeiculoCargaVp = iIdVeiculoCargaVp;
	}

	public String getiCdCarroceriaVp() {
		return iCdCarroceriaVp;
	}

	public void setiCdCarroceriaVp(String iCdCarroceriaVp) {
		this.iCdCarroceriaVp = iCdCarroceriaVp;
	}

	public Long getiNoCpfCrlvVp() {
		return iNoCpfCrlvVp;
	}

	public void setiNoCpfCrlvVp(Long iNoCpfCrlvVp) {
		this.iNoCpfCrlvVp = iNoCpfCrlvVp;
	}

	public Long getiTpCarroceriaVp() {
		return iTpCarroceriaVp;
	}

	public void setiTpCarroceriaVp(Long iTpCarroceriaVp) {
		this.iTpCarroceriaVp = iTpCarroceriaVp;
	}

	public Long getiNoCnpjCrlvVp() {
		return iNoCnpjCrlvVp;
	}

	public void setiNoCnpjCrlvVp(Long iNoCnpjCrlvVp) {
		this.iNoCnpjCrlvVp = iNoCnpjCrlvVp;
	}

	public String getiIdTransformadoVp() {
		return iIdTransformadoVp;
	}

	public void setiIdTransformadoVp(String iIdTransformadoVp) {
		this.iIdTransformadoVp = iIdTransformadoVp;
	}

	public String getiNoPlacaLaudoVp() {
		return iNoPlacaLaudoVp;
	}

	public void setiNoPlacaLaudoVp(String iNoPlacaLaudoVp) {
		this.iNoPlacaLaudoVp = iNoPlacaLaudoVp;
	}

	public String getiNoChassiLaudoVp() {
		return iNoChassiLaudoVp;
	}

	public void setiNoChassiLaudoVp(String iNoChassiLaudoVp) {
		this.iNoChassiLaudoVp = iNoChassiLaudoVp;
	}

	public Long getiCdModeloLaudoVp() {
		return iCdModeloLaudoVp;
	}

	public void setiCdModeloLaudoVp(Long iCdModeloLaudoVp) {
		this.iCdModeloLaudoVp = iCdModeloLaudoVp;
	}

	public String getiCdOrigemChassiVp() {
		return iCdOrigemChassiVp;
	}

	public void setiCdOrigemChassiVp(String iCdOrigemChassiVp) {
		this.iCdOrigemChassiVp = iCdOrigemChassiVp;
	}

	public Long getiCdFabricanteLaudoVp() {
		return iCdFabricanteLaudoVp;
	}

	public void setiCdFabricanteLaudoVp(Long iCdFabricanteLaudoVp) {
		this.iCdFabricanteLaudoVp = iCdFabricanteLaudoVp;
	}

	public Long getiNoCepProponenteVp() {
		return iNoCepProponenteVp;
	}

	public void setiNoCepProponenteVp(Long iNoCepProponenteVp) {
		this.iNoCepProponenteVp = iNoCepProponenteVp;
	}

	public String getiTpCombust() {
		return iTpCombust;
	}

	public void setiTpCombust(String iTpCombust) {
		this.iTpCombust = iTpCombust;
	}

	public String getQtLotacVeicuVp() {
		return qtLotacVeicuVp;
	}

	public void setQtLotacVeicuVp(String qtLotacVeicuVp) {
		this.qtLotacVeicuVp = qtLotacVeicuVp;
	}

	public Long getQtdKmRdadoVeicu() {
		return qtdKmRdadoVeicu;
	}

	public void setQtdKmRdadoVeicu(Long qtdKmRdadoVeicu) {
		this.qtdKmRdadoVeicu = qtdKmRdadoVeicu;
	}

	public Long getStatuDecodChasis() {
		return statuDecodChasis;
	}

	public void setStatuDecodChasis(Long statuDecodChasis) {
		this.statuDecodChasis = statuDecodChasis;
	}
		
	public List<Long> getCodParecerTecnicoVPList() {
		return codParecerTecnicoVPList;
	}

	public void setCodParecerTecnicoVPList(List<Long> codParecerTecnicoVPList) {
		this.codParecerTecnicoVPList = codParecerTecnicoVPList;
	}

	public List<Long> getCodAcessoriosLaudoVPList() {
		return codAcessoriosLaudoVPList;
	}

	public void setCodAcessoriosLaudoVPList(List<Long> codAcessoriosLaudoVPList) {
		this.codAcessoriosLaudoVPList = codAcessoriosLaudoVPList;
	}

	public List<AvariaLaudoVistoriaPrevia> getAvariaLaudoVPList() {
		return avariaLaudoVPList;
	}

	public void setAvariaLaudoVPList(List<AvariaLaudoVistoriaPrevia> avariaLaudoVPList) {
		this.avariaLaudoVPList = avariaLaudoVPList;
	}

	public String getiChassiRemarcadoVp() {
		return iChassiRemarcadoVp;
	}

	public void setiChassiRemarcadoVp(String iChassiRemarcadoVp) {
		this.iChassiRemarcadoVp = iChassiRemarcadoVp;
	}

	public String getiCabineSuplementarVp() {
		return iCabineSuplementarVp;
	}

	public void setiCabineSuplementarVp(String iCabineSuplementarVp) {
		this.iCabineSuplementarVp = iCabineSuplementarVp;
	}

	public String getiQuartoEixoVp() {
		return iQuartoEixoVp;
	}

	public void setiQuartoEixoVp(String iQuartoEixoVp) {
		this.iQuartoEixoVp = iQuartoEixoVp;
	}

	public String getDescricaoLinkPrestador() {
		return descricaoLinkPrestador;
	}

	public void setDescricaoLinkPrestador(String descricaoLinkPrestador) {
		this.descricaoLinkPrestador = descricaoLinkPrestador;
	}

	public String getNomePrestador() {
		return nomePrestador;
	}

	public void setNomePrestador(String nomePrestador) {
		this.nomePrestador = nomePrestador;
	}
}