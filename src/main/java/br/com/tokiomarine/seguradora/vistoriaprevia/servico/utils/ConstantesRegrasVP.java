package br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Rafael Moreira
 * @version 1.0
 */
public class ConstantesRegrasVP {

	private ConstantesRegrasVP() {}
	
	public static final String TIPO_HISTORICO_NEGOCIO = "0";
	public static final String TIPO_HISTORICO_ITEM = "0";

	/** Coberturas **/
	public static final Long CODIGO_COBERTURA_CARROCERIA = 70L;
	public static final Long CODIGO_COBERTURA_EQUIPAMENTOS = 71L;
	public static final List<Long> LISTA_COBERTURA_EQUIPAMENTOS = Collections.unmodifiableList(Arrays.asList(Long.valueOf(71),Long.valueOf(309),Long.valueOf(310),Long.valueOf(311),Long.valueOf(312),Long.valueOf(313),Long.valueOf(314),Long.valueOf(315),Long.valueOf(316)));
	public static final List<Long> LISTA_COBERTURAS_ACESSORIOS = Collections.unmodifiableList(Arrays.asList(Long.valueOf(320),Long.valueOf(321),Long.valueOf(322),Long.valueOf(323)));
	public static final Long LISTA_CODIGO_ZERO_KM_SIM = 3728L;
	public static final Long LISTA_CODIGO_ZERO_KM_NAO = 3729L;

	public static final Long CODIGO_VALOR_CHASSI_REMARCADO_SIM = 18676L;

	/** Caracteristicas **/
	public static final String ZERO_KM = "0KM (S/N)";
	public static final String DM_RCV = "NIVEL_IS_DM_RCV";
	public static final String DP_RCV = "NIVEL_IS_DP_RCV";
	public static final String DT_SAIDA_ZERO_KM = "DATA SAIDA VEIC 0KM";
	public static final String CODIGO_DA_CONGENERE = "CODIGO DA CONGENERE";
	public static final String NR_APOLICE_CONGENERE = "NR APOLICE CONGENERE";
	public static final String CHASSI = "CHASSI";
	public static final String VENCIMENTO_CONGENERE = "VENC APOL CONGENERE";
	public static final String ANO_MODELO = "ANO MODELO";
	public static final String TIPO_RESTRICAO_GRADE_AJUSTE = "AJ1";
	public static final String FABRICANTE = "FABRICANTE";
	public static final String MARCA_MODELO = "MARCA/MODELO";
	public static final String ANO_FABRICACAO = "ANO FABRICACAO";
	public static final String PLACA = "PLACA";
	public static final String TIPO_IMOVEL = "TIPO IMOVEL";
	public static final String CEP = "C.E.P.";
	public static final String LOCAL_RISCO = "LOCAL_RISCO";
	public static final String NUMERO_LOGRADOURO = "NUMERO_LOGRADOURO";
	public static final String COMPLEMENTO = "COMPLEMENTO";
	public static final String TIPO_OCUPACAO = "FORMA DE OCUPACAO";
	public static final String RAMO_ATIVIDADE = "RAMO DE ATIVIDADE";
	public static final String TIPO_MODALIDADE = "TIPO/MODALIDADE";
	public static final String TIPO_CONDOMINIO = "TIPO DE CONDOMINIO";
	public static final String ESPECIALIDADE_PRINCIPAL = "ESPEC. PRINCIPAL";
	public static final String ISENCAO_FRANQUIA_1_SINISTRO = "ISENCAO FRANQUIA 1 SINISTRO";
	public static final String TIPO_CARROCERIA = "TIPO CARROCERIA";

	public static final String POSSUI_4_EIXO_ADAPTADO = "4o EIXO ADAPTADO";
	public static final String CABINE_SUPLEMENTAR = "CABINE SUPLEMENTAR";
	public static final String CHASSI_REMARCADO = "CHASSI_REMARCADO";
	public static final String COMBUSTIVEL = "COMBUSTIVEL";

	/** Valores de Característica **/
	public static final Long VALOR_CARAC_POSSUI_4_EIXO_ADAPTADO_SIM = 18659L;
	public static final Long VALOR_CARAC_CABINE_SUPLEMENTAR_SIM = 18661L;

	public static final String VALOR_CARAC_CHASSI_REMARCADO_SIM = "VALOR_CARAC_CHASSI_REMARCADO_SIM";

	/** Acessórios **/
	public static final Long ACESSORIO_KIT_GAS = 14250L;
	public static final Long ACESSORIO_BLINDAGEM = 14249L;

	/** Tipo Renovação **/
	public static final String TIPO_RENOV_R = "R";
	public static final String TIPO_RENOV_M = "M";

	/** Tipo Pessoa **/
	public static final String PESSOA_FISICA = "F";
	public static final String PESSOA_JURIDICA = "J";

	/** Tipo de Seguro - Informado pelo Web Service **/
	public static final Long TP_SEGURO_NOVO = 1L;
	public static final Long TP_SEGURO_ENDOSSO = 2L;
	public static final Long TP_SEGURO_RENOVACAO_INTERNA = 3L;
	public static final Long TP_SEGURO_RENOVACAO_CONGENERE = 4L;

	/** Tipo de comercialização */
	public static final Long DETERMINADO_CARGA = 0L;
	public static final Long DETERMINADO_PASSEIO = 1L;
	public static final Long MES_A_MES = 2L;
	public static final Long AJUSTAVEL = 3L;
	public static final Long AJUSTAVEL_CARGA = 4L;

	/** Status de agendamento */
	public static final String AGD = "AGD";
	public static final String RCB = "RCB";
	public static final String PEN = "PEN";
	public static final String NAP = "NAP";
	public static final String VFR = "VFR";
	public static final String RLZ = "RLZ";
	public static final String FIM = "FIM";
	public static final String CAN = "CAN";
	public static final String NAG = "NAG";
	public static final String RGD = "RGD";

	/** Motivo Cancelamento */
	public static final Long CANCELAMENTO_A_CONFIRMAR = 1L;
	public static final Long CANCELAMENTO_CONFIRMADO = 2L;
	public static final Long CANCELAMENTO_VISTORIADORA = 3L;
	public static final Long CANCELAMENTO_DE_NAG = 4L;
	public static final Long CANCELAMENTO_FORA_SISTEMA_CONFIRMADO = 5L; // VISTORIA DISPENSADA
	public static final Long CANCELAMENTO_TELA_CONSULTA_AGTO = 6L;
	public static final Long CANCELAMENTO_AGTO_POR_ATRASO = 7L;

	public static final String DS_MOTV_SITUC_NAO_CADASTRADO = "Motivo situação não cadastrado";

	/** Motivo Agendamento não permitido */
	public static final Long MOTIVO_CORRETOR_NAO_PARTICIPA = 1L;
	public static final Long MOTIVO_VP_NAO_NECESSARIA = 2L;
	public static final Long MOTIVO_AGENDAMENTO_JA_REALIZADO = 3L;
	public static final Long MOTIVO_AGENDAMENTO_REAPROVEITADO = 4L;
	public static final Long MOTIVO_VP_DISPENSADA = 5L;

	/** Origem Dispensa Vistoria (ou cancelamento de agendamento) */
	public static final Long ORIGEM_REGRA = 1L;
	public static final Long ORIGEM_DISPENSA = 2L;
	public static final Long ORIGEM_RECUSA = 4L;

	/** Categoria de endosso */
	public static final String EXCLUSAO_AVARIAS = "V";
	public static final String SUBSTITUICAO_DE_ITEM = "S";
	public static final String ALTERACAO_GERAL = "G";
	
	
	/** Sistema Chamador */
	public static final Long KCW = 1L;
	public static final Long EMISSAO_PLAT = 2L;
	public static final Long ENDOSSO_PLAT = 3L;
	public static final Long EMISSAO_SSV = 4L;
	public static final Long RECEP_SSV = 5L;
	public static final Long ENDOSSO_SSV = 6L;
	public static final Long ENDOSSO_WEB = 7L;
	public static final Long RESTR_BONUS = 8L;
	public static final Long MULTICALCULO = 9L;
	public static final Long LIBERACAO_PROPOSTA = 10L;
	public static final Long LIBERACAO_ENDOSSO_SSV = 11L;
	public static final Long LIBERACAO_ENDOSSO_WEB = 12L;
	public static final Long TRASMISSAO_KCW = 13L;
	public static final Long AGTO_SEM_PROPOSTA_TMS = 14L;
	public static final Long AGTO_SEM_PROPOSTA_TMB = 15L;
	public static final Long SISTEMA_VINCULO = 16L;
	public static final Long AUTOCOMPARA_SANTANDER = 17L;
	public static final Long EMISSAO_COTADOR = 20L;
	public static final Long TRANSMISSAO_COTADOR = 21L;
	public static final Long WS_COTADOR = 22L;
	public static final Long COTADOR_FROTA = 23L; //Validacao de regras via Blaze
	public static final Long GERACAO_RESTRICAO = 24L; //Validacao de regras via Blaze
	public static final Long ENDOSSO_CTF = 25L; //Validacao de regras via Blaze
	public static final Long AgtoSemPropostaTMS = 14L;
	public static final Long AgtoSemPropostaTMB = 15L;
	/**
	 * Nome do parametro que cont�m a lista de parecer técnico aceit�vel utilizado para avaria de vidros.
	 */
	public static final String COD_PARECER_SUJEITO_ANALISE_PERMITE_AGENDAMENTO = "CD_PAREC_SUJ_ANALISE_VIDROS_PERMT_AGD";

	/**
	 * Nome do parametro que cont�m a lista de parecer técnico impeditivos
	 */
	public static final String COD_PARECER_SUJEITO_ANALISE_NAO_PERMITE_AGENDAMENTO = "CD_PAREC_SUJ_ANALISE_VIDROS_NAO_PERMT_AGD";

	/**
	 * Nome do parametro que cont�m a quantidade de dias validos para um agendamento
	 */
	public static final String QT_DIAS_VALIDADE_AGENDAMENTO = "QT_DIAS_VALIDADE_AGENDAMENTO";

}