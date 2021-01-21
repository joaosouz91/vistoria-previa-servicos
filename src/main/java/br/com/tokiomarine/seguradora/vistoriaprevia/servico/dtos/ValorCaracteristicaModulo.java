package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos;

import java.io.Serializable;

import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.ValorCaracteristicaItemSegurado;
import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.ValorCaracteristicaModuloProduto;

public class ValorCaracteristicaModulo implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private ValorCaracteristicaItemSegurado valorCaracteristicaItemSegurado;
	
	private ValorCaracteristicaModuloProduto valorCaracteristicaModuloProduto;

	public ValorCaracteristicaItemSegurado getValorCaracteristicaItemSegurado() {
		return valorCaracteristicaItemSegurado;
	}

	public void setValorCaracteristicaItemSegurado(ValorCaracteristicaItemSegurado valorCaracteristicaItemSegurado) {
		this.valorCaracteristicaItemSegurado = valorCaracteristicaItemSegurado;
	}

	public ValorCaracteristicaModuloProduto getValorCaracteristicaModuloProduto() {
		return valorCaracteristicaModuloProduto;
	}

	public void setValorCaracteristicaModuloProduto(ValorCaracteristicaModuloProduto valorCaracteristicaModuloProduto) {
		this.valorCaracteristicaModuloProduto = valorCaracteristicaModuloProduto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}