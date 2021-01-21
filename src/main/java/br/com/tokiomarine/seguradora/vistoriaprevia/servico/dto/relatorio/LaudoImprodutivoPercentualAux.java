package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.relatorio;

import java.io.Serializable;
import java.lang.reflect.Method;

import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoImprodutivoPercentual;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaudoImprodutivoPercentualAux extends LaudoImprodutivoPercentual implements Serializable {

	private static final long serialVersionUID = -9026813600382344926L;

	private String nomeCorretor;
	private String dtFimVigenStr = DateUtil.formataData(DateUtil.DATA_REGISTRO_VIGENTE);
	private String dtInicoVigenStr;

	public LaudoImprodutivoPercentualAux() {

	}

	public LaudoImprodutivoPercentualAux(LaudoImprodutivoPercentual laudoImpPercentual) {

		try {
			Method[] allMethods = laudoImpPercentual.getClass().getDeclaredMethods();
			for (Method m : allMethods) {
				if (m.getName().contains("get") && !m.getName().contains("Pk")) {
					super.getClass().getMethod("set" + m.getName().substring(3), m.getReturnType()).invoke(this, m.invoke(laudoImpPercentual));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getNomeCorretor() {
		return nomeCorretor;
	}

	public void setNomeCorretor(String nomeCorretor) {
		this.nomeCorretor = nomeCorretor;
	}

	public String getDtFimVigenStr() {
		return dtFimVigenStr;
	}

	public void setDtFimVigenStr(String dtFimVigenStr) {
		this.dtFimVigenStr = dtFimVigenStr;
	}

	public String getDtInicoVigenStr() {
		return dtInicoVigenStr;
	}

	public void setDtInicoVigenStr(String dtInicoVigenStr) {
		this.dtInicoVigenStr = dtInicoVigenStr;
	}
}
