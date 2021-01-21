package br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.core.util.StringUtil;
import br.com.tokiomarine.seguradora.ext.ssv.enumerated.SSVModuloProduto;

public class UtilNegocio {

	/**
	 * Recupera descrição do tipo veiculo
	 *
	 * @param tipoVeiculo
	 * @return
	 */
	public static String retornaDescVeiculo(String tipoVeiculo) {

		String dsTpVeiculo = "";

		if (ConstantesVistoriaPrevia.COD_VEIC_AUTO_CLASSICO.equals(tipoVeiculo)) {
			dsTpVeiculo = ConstantesVistoriaPrevia.DS_VEIC_AUTO_CLASSICO;

		} else if (ConstantesVistoriaPrevia.COD_VEIC_AUTO_TRADICIONAL.equals(tipoVeiculo)) {
			dsTpVeiculo = ConstantesVistoriaPrevia.DS_VEIC_AUTO_TRADICIONAL;

		} else if (ConstantesVistoriaPrevia.COD_VEIC_AUTO_CARGA.equals(tipoVeiculo)) {
			dsTpVeiculo = ConstantesVistoriaPrevia.DS_VEIC_AUTO_CARGA;

		} else if (ConstantesVistoriaPrevia.COD_VEIC_AUTO_UTILITARIO.equals(tipoVeiculo)) {
			dsTpVeiculo = ConstantesVistoriaPrevia.DS_VEIC_AUTO_UTILITARIO;

		} else if (ConstantesVistoriaPrevia.COD_VEIC_AUTO_FROTA.equals(tipoVeiculo)) {
			dsTpVeiculo = ConstantesVistoriaPrevia.DS_VEIC_AUTO_FROTA;

		} else if (ConstantesVistoriaPrevia.COD_VEIC_AUTO_PASSEIO_POPULAR.equals(tipoVeiculo)) {
			dsTpVeiculo = ConstantesVistoriaPrevia.DS_VEIC_AUTO_PASSEIO_POPULAR;

		} else if (ConstantesVistoriaPrevia.COD_VEIC_AUTO_CARGA_POPULAR.equals(tipoVeiculo)) {
			dsTpVeiculo = ConstantesVistoriaPrevia.DS_VEIC_AUTO_CARGA_POPULAR;

		}

		return dsTpVeiculo;
	}

	/**
	 * Retorna descrição de Posto de Vistoria
	 *
	 * @param tipoVistoria
	 * @return
	 */
	public static String retornaDescTipoVistoria(String tipoVistoria) {

		if (ConstantesVistoriaPrevia.COD_LOCAL_POSTO.equals(tipoVistoria)) {
			return ConstantesVistoriaPrevia.DS_LOCAL_POSTO;
		} else if (ConstantesVistoriaPrevia.COD_LOCAL_DOMICILIO.equals(tipoVistoria)) {
			return ConstantesVistoriaPrevia.DS_LOCAL_DOMICILIO;
		} else {
			return "";
		}
	}

	public static String retornaDescSitucAgendamento(String cdSitucAgend) {

		String dsSitucAgend = "Agendar";

		if ("AGD".equals(cdSitucAgend)) {
			dsSitucAgend = "Agendada";
		} else if ("RCB".equals(cdSitucAgend)) {
			dsSitucAgend = "Aguardando Confirmação";
		} else if ("PEN".equals(cdSitucAgend)) {
			dsSitucAgend = "Aguardando Confirmação";
		} else if ("NAP".equals(cdSitucAgend)) {
			dsSitucAgend = "Não Aprovada";
		} else if ("VFR".equals(cdSitucAgend)) {
			dsSitucAgend = "Frustrada";
		} else if ("RLZ".equals(cdSitucAgend)) {
			dsSitucAgend = "Realizada";
		} else if ("CAN".equals(cdSitucAgend)) {
			dsSitucAgend = "Cancelada";
		} else if ("NAG".equals(cdSitucAgend)) {
			dsSitucAgend = "Não Agendada";
		} else if ("RGD".equals(cdSitucAgend)) {
			dsSitucAgend = "Reagendada";
		} else if ("FIM".equals(cdSitucAgend) || "FIN".equals(cdSitucAgend)) {
			dsSitucAgend = "Finalizada";
		}

		return dsSitucAgend;
	}

	public static String retornaDescMotivSitcAgto(Long cdMotvSitucAgmto) {

		String dsMotvSitucAgmto = "";

		if (1 == cdMotvSitucAgmto) {
			dsMotvSitucAgmto = "Agendamento cancelado devido ao cancelamento da proposta";
		} else if (2 == cdMotvSitucAgmto) {
			dsMotvSitucAgmto = "Agendamento cancelado devido ao cancelamento da proposta";
		} else if (3 == cdMotvSitucAgmto) {
			dsMotvSitucAgmto = "Agendamento cancelado pela vistoriadora";
		} else if (4 == cdMotvSitucAgmto) {
			dsMotvSitucAgmto = "Cancelamento de um retorno não agendado('NAG')";
		} else if (5 == cdMotvSitucAgmto) {
			dsMotvSitucAgmto = "Agendamento cancelado pela vistoriadora";
		} else if (6 == cdMotvSitucAgmto) {
			dsMotvSitucAgmto = "Agendamento cancelado manualmente por colaborador Tokio";
		} else if (7 == cdMotvSitucAgmto) {
			dsMotvSitucAgmto = "Agendamento Sicredi cancelado, será direcionado para VP Mobile";
		}

		return dsMotvSitucAgmto;
	}

	public static boolean isReagendamento(String cdSitucAgend,Long cdMotvSitucAgmto) {

		return (ConstantesVistoriaPrevia.COD_SITUC_NAO_AGENDADA.equals(cdSitucAgend) || 
				ConstantesVistoriaPrevia.COD_SITUC_NAO_APROVADA.equals(cdSitucAgend) || 
				ConstantesVistoriaPrevia.COD_SITUC_FRUSTRADA.equals(cdSitucAgend) || 
				(ConstantesVistoriaPrevia.COD_SITUC_CANCELADA.equals(cdSitucAgend) && isMotivoCanclReagendar(cdMotvSitucAgmto))); 
	}

	public static boolean isMotivoCanclReagendar(Long cdMotvSitucAgmto) {

		return !(ConstantesRegrasVP.CANCELAMENTO_A_CONFIRMAR.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_CONFIRMADO.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_DE_NAG.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_FORA_SISTEMA_CONFIRMADO.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_AGTO_POR_ATRASO.equals(cdMotvSitucAgmto));
	}

	public static boolean exibirMotivoCancelada(Long cdMotvSitucAgmto) {

		return !(ConstantesRegrasVP.CANCELAMENTO_A_CONFIRMAR.equals(cdMotvSitucAgmto) ||
				ConstantesRegrasVP.CANCELAMENTO_CONFIRMADO.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_DE_NAG.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_FORA_SISTEMA_CONFIRMADO.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_VISTORIADORA.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_TELA_CONSULTA_AGTO.equals(cdMotvSitucAgmto) || 
				ConstantesRegrasVP.CANCELAMENTO_AGTO_POR_ATRASO.equals(cdMotvSitucAgmto)); 
	}

	public static Long retornaCodigoSistemaOrigem(Long cdSistmOrigm) {

		if (ConstantesRegrasVP.ENDOSSO_SSV.equals(cdSistmOrigm) || ConstantesRegrasVP.ENDOSSO_WEB.equals(cdSistmOrigm) || ConstantesRegrasVP.EMISSAO_SSV.equals(cdSistmOrigm) || ConstantesRegrasVP.RECEP_SSV.equals(cdSistmOrigm) || ConstantesRegrasVP.RESTR_BONUS.equals(cdSistmOrigm) || ConstantesRegrasVP.LIBERACAO_PROPOSTA.equals(cdSistmOrigm) || ConstantesRegrasVP.LIBERACAO_ENDOSSO_SSV.equals(cdSistmOrigm) || ConstantesRegrasVP.LIBERACAO_ENDOSSO_WEB.equals(cdSistmOrigm)) {

			return ConstantesVistoriaPrevia.ORIGEM_SSV;

		} else if (ConstantesRegrasVP.KCW.equals(cdSistmOrigm) || ConstantesRegrasVP.MULTICALCULO.equals(cdSistmOrigm)) {

			return ConstantesVistoriaPrevia.ORIGEM_KCW;

		} else if (ConstantesRegrasVP.EMISSAO_PLAT.equals(cdSistmOrigm) || ConstantesRegrasVP.ENDOSSO_PLAT.equals(cdSistmOrigm)) {

			return ConstantesVistoriaPrevia.ORIGEM_PLATAFORMA;

		} else if (ConstantesRegrasVP.AGTO_SEM_PROPOSTA_TMS.equals(cdSistmOrigm) || ConstantesRegrasVP.AGTO_SEM_PROPOSTA_TMB.equals(cdSistmOrigm)) {

		return ConstantesVistoriaPrevia.ORIGEM_SEM_PROPOSTA; }

		return null;
	}

	public static Long retornaCodModProduto(String tipoVeiculo) {

		Long codModuloProduto = SSVModuloProduto.AUTOPASSEIO.getCodigoModuloProduto();

		if (ConstantesVistoriaPrevia.COD_VEIC_AUTO_CLASSICO.equals(tipoVeiculo)) {
			codModuloProduto = SSVModuloProduto.AUTOCLASSICO.getCodigoModuloProduto();
		} else if (ConstantesVistoriaPrevia.COD_VEIC_AUTO_CARGA.equals(tipoVeiculo) || ConstantesVistoriaPrevia.COD_VEIC_AUTO_UTILITARIO.equals(tipoVeiculo)) {
			codModuloProduto = SSVModuloProduto.AUTOCARGA.getCodigoModuloProduto();
		}

		return codModuloProduto;
	}

	public static String retornaCodTipoVeicPorCodModulo(Long codModProduto) {

		String codTipoVeiculo = ConstantesVistoriaPrevia.COD_VEIC_AUTO_TRADICIONAL;

		if (SSVModuloProduto.AUTOPASSEIO.getCodigoModuloProduto().equals(codModProduto)) {
			codTipoVeiculo = ConstantesVistoriaPrevia.COD_VEIC_AUTO_CLASSICO;
		} else if (SSVModuloProduto.AUTOCARGA.getCodigoModuloProduto().equals(codModProduto)) {
			codTipoVeiculo = ConstantesVistoriaPrevia.COD_VEIC_AUTO_CARGA;
		} else if (SSVModuloProduto.CAMINHAO.getCodigoModuloProduto().equals(codModProduto)) {
			codTipoVeiculo = ConstantesVistoriaPrevia.COD_VEIC_AUTO_CARGA;
		} else if (SSVModuloProduto.PASSEIOPOPULAR.getCodigoModuloProduto().equals(codModProduto)) {
			codTipoVeiculo = ConstantesVistoriaPrevia.COD_VEIC_AUTO_PASSEIO_POPULAR;
		} else if (SSVModuloProduto.CAMINHAOPOPULAR.getCodigoModuloProduto().equals(codModProduto)) {
			codTipoVeiculo = ConstantesVistoriaPrevia.COD_VEIC_AUTO_CARGA_POPULAR;
		}

		return codTipoVeiculo;
	}

	public static String retornaDescPeriodoVistoria(String codPeriodoVistoria) {

		String descPeriodo = "Manhã";

		if ("T".equals(codPeriodoVistoria)) {
			descPeriodo = "Tarde";
		} else if ("C".equals(codPeriodoVistoria)) {
			descPeriodo = "Comercial";
		}

		return descPeriodo;
	}

	/**
	 * Para cálculo do dígito verificador utilizaremos o ‘módulo 11’:
	 * Para calcular o primeiro dígito verificador, cada dígito do número, começando da direita para a esquerda (do dígito menos significativo para o dígito mais significativo)
	 * é multiplicado, na ordem, por 2, depois 3, depois 4 e assim sucessivamente, até o primeiro dígito do número.
	 * O somatório dessas multiplicações é multiplicado por 10 e dividido por 11.
	 * O resto desta divisão (módulo 11) é o primeiro dígito verificador.
	 * +----+----+----+----+----+----+
	 * | 2 | 6 | 1 | 5 | 3 | 3 |
	 * +----+----+----+----+----+----+
	 * | x7 | x6 | x5 | x4 | x3 | x2 |
	 * +----+----+----+----+----+----+
	 * | =14| =36| =5 | =20| =9 | =6 |
	 * +----+----+----+----+----+----+
	 * -> = (90 x 10) / 11 = 81, resto 9 => Dígito = 9
	 */
	public static Integer calcularDigVerificador(Long numSequencial) {

		String campo = numSequencial.toString();

		int val = 0;
		int somaVal = 0;
		// * Maior peso de multiplicacao é tamanho do num sequencial + 1
		int peso = campo.length() + 1;

		for (int i = 0 ; i < campo.length() ; i++) {

			val = Integer.parseInt(String.valueOf(campo.charAt(i)));
			val = val * peso;

			somaVal = somaVal + val;
			peso--;
		}

		

		return (somaVal * 10) % 11;
	}

	/**
	 * Obtém o sistema chamador do voucher
	 */
	public static String obterSistemaChamadorVoucher(String codigoVoucher) {

		// o número do voucher é composto por SSPNNNNNNNNNNNNNNNND
		// onde: S - sistema, P - prestadora, N - número sequencial e D - dígito

		return codigoVoucher.substring(0,2);
	}

	/**
	 * Obtém a letra da prestadora do voucher
	 */
	public static String obterLetraPrestadoraVoucher(String codigoVoucher) {

		// o número do voucher é composto por SSPNNNNNNNNNNNNNNNND
		// onde: S - sistema, P - prestadora, N - número sequencial e D - dígito

		return codigoVoucher.substring(2,3);
	}

	/**
	 * Obtém o número sequencial do voucher
	 */
	public static String obterNumeroSequencialVoucher(String codigoVoucher) {

		// o número do voucher é composto por SSPNNNNNNNNNNNNNNNND
		// onde: S - sistema, P - prestadora, N - número sequencial e D - dígito

		return codigoVoucher.substring(3, (codigoVoucher.length() - 1));
	}

	/**
	 * Obtém o dígito verificador do voucher
	 */
	public static String obterDigitoVoucher(String codigoVoucher) {

		// o número do voucher é composto por SSPNNNNNNNNNNNNNNNND
		// onde: S - sistema, P - prestadora, N - número sequencial e D - dígito

		return codigoVoucher.substring(codigoVoucher.length() - 1);
	}

	/**
	 * Recupera descrição da situação da vistoria previa no laudo
	 *
	 * @param cdSitucVspre
	 * @return
	 */
	public static String retornaDescSitucVsPreLaudo(String cdSitucVspre) {
		// a = aceitavel, r = recusado, af = aceitacao forcada, s = sujeito a analise, f = frustrada, l = aceitacao liberada.
		String dsSitucVspre = "";

		if ("A".equals(cdSitucVspre)) {
			dsSitucVspre = "Aceitavel";
		} else if ("R".equals(cdSitucVspre)) {
			dsSitucVspre = "Recusado";
		} else if ("AF".equals(cdSitucVspre)) {
			dsSitucVspre = "Aceitação Forçada";
		} else if ("S".equals(cdSitucVspre)) {
			dsSitucVspre = "Sujeito a Análise";
		} else if ("F".equals(cdSitucVspre)) {
			dsSitucVspre = "Frustrada";
		} else if ("L".equals(cdSitucVspre)) {
			dsSitucVspre = "Aceitação Liberada";
		}

		return dsSitucVspre;
	}

	/**
	 * Retorna o nome do mes informado
	 *
	 * @param mesReferencia
	 * @return
	 */
	public static String getNomeMes(int mes) {

		switch (mes) {
			case 1:
				return "Janeiro";
			case 2:
				return "Fevereiro";
			case 3:
				return "Março";
			case 4:
				return "Abril";
			case 5:
				return "Maio";
			case 6:
				return "Junho";
			case 7:
				return "Julho";
			case 8:
				return "Agosto";
			case 9:
				return "Setembro";
			case 10:
				return "Outubro";
			case 11:
				return "Novembro";
			case 12:
				return "Dezembro";
			 default:
				return null;
		}
	}

	public static Integer getDiaDaSemana(Date dt) {
		if (dt == null) { return null; }
		Calendar calendario = new GregorianCalendar();
		calendario.setLenient(false);
		calendario.setTime(dt);
		return Integer.valueOf(calendario.get(Calendar.DAY_OF_WEEK));
	}

	public static String getNomeDiaSemana(Date dt) {
		int dia = dt != null ? getDiaDaSemana(dt).intValue() : 0;
		switch (dia) {
			case 1:
				return "Domingo";
			case 2:
				return "Segunda-feira";
			case 3:
				return "Terça-feira";
			case 4:
				return "Quarta-feira";
			case 5:
				return "Quinta-feira";
			case 6:
				return "Sexta-feira";
			case 7:
				return "Sábado";
			 default:
				return null;

		}
	}

	public static List<String> getListaMeses() {

		List<String> meses = new ArrayList<>();

		for (int i = 1 ; i < 13 ; i++) {
			meses.add(String.valueOf(i));
		}
		return meses;
	}

	public static List<String> getListaAno() {

        List<String> anos = new ArrayList<>();
        Date dataAtual = new Date();
        int anoAtual = DateUtil.getAno(dataAtual);

        for(int ano = anoAtual; ano >= 1990; ano--) {
            anos.add(String.valueOf(ano));
        }

		return anos;
	}

	public static Date obterDataInicioPesquisa(Long mesReferencia, Long anoReferencia) {

		String dataLote = "01";

		dataLote += StringUtil.lpad(mesReferencia.toString(), '0', 2);
		dataLote += anoReferencia;

		try {
			return DateUtil.parseDataDDMMYYYY(dataLote);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date obterDataFimPesquisa(Long mesReferencia, Long anoReferencia) {

		// caso o mês seja novembro 11, atribui como 12 o mes final de pesquisa
		int mesDataFimReferencia = mesReferencia == 11L? 12 : (mesReferencia.intValue() + 1) % 12;
		Long anoDataFimReferencia = mesDataFimReferencia == 1? anoReferencia.intValue() + 1: anoReferencia;
		String dataLote = "01" + StringUtil.lpad(String.valueOf(mesDataFimReferencia), '0', 2)  + anoDataFimReferencia;

		try {
			return DateUtil.parseDataDDMMYYYY(dataLote);
		} catch (ParseException e) {
			return null;
		}
	}

    public static String converterPlacaMercosul(String codigoPlaca){

    	Map<String, String> mapaLetras = new HashMap<>();
    	String placaConvertida = null;

    	mapaLetras.put("0", "A");
    	mapaLetras.put("1", "B");
    	mapaLetras.put("2", "C");
    	mapaLetras.put("3", "D");
    	mapaLetras.put("4", "E");
    	mapaLetras.put("5", "F");
    	mapaLetras.put("6", "G");
    	mapaLetras.put("7", "H");
    	mapaLetras.put("8", "I");
    	mapaLetras.put("9", "J");

    	String numeroPlaca = codigoPlaca.substring(4, 5);

    	if(NumberUtils.isCreatable(numeroPlaca)){

    		String letraPlaca =  mapaLetras.get(numeroPlaca);

    		placaConvertida = codigoPlaca.substring(0,4) + letraPlaca + codigoPlaca.substring(5,7);

    	} else {
    		placaConvertida = codigoPlaca; // mantem a placa original.
    	}

    	return placaConvertida;

    }
    
    
    private UtilNegocio() {
        throw new IllegalStateException("Utility class");
      }
}
