package br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils;

public class ConstantesVistoriaPrevia {

	private ConstantesVistoriaPrevia() {}
	
	/**
	 * Indica o nome de codigo de rede da empresa
	 */
	public static final String TIPO_MOTIVO_DISPENSA_VISTORIA_PREVIA = "TP_MOTV_DISPE_VSTRI";

	/**
	 * Indica o nome de codigo de rede da empresa
	 */
	public static final String CODIGO_REDE_EMPRESA = "CD_REDE_EMPR";

	/**
	 * status da restrição de negocio liberação obrigatória
	 */
	public static final String RESTRICAO_STATUS_OLI = "OLI";

	/**
	 * Status da restrição do negocio liberado
	 */
	public static final String RESTRICAO_STATUS_LIB = "LIB";

	/**
	 * Status da restrição do negocio recusado
	 */
	public static final String RESTRICAO_STATUS_REC = "REC";

	/**
	 * Codigo Indica que a restrição do negocio é vistoria
	 */
	public static final String RESTRICAO_CODIGO_VIS = "VIS";
	
	public static final Long CODIGO_MODULO_PRODUTO_AUTO_PASSEIO = 7L;
	public static final Long CODIGO_CARACTERISTICA_REGIAO_TARIFARIA_AUTO_PASSEIO = 8L;

	/**
	 * Mensagem que indica que existe uma restricao e não houve alteração
	 */
	public static final String RESTRICAO_MENSAGEM_ACEITA_ALTERACAO_NAO_PERMITIDA = "RESTRICAO ACEITA C/DADOS ORIGINAIS, ALTERACAO NAO PERMITIDA";

	/**
	 * Tipo manual de fechamento da restrião de negocio
	 */
	public static final Long RESTRICAO_TIPO_FECHAMENTO_MANUAL_NEGOCIO = 1L;

	/**
	 * Tipo altomatico de fechamento da retrrção de negócio
	 */
	public static final Long RESTRICAO_TIPO_FECHAMENTO_ALTOMATICO_NEGOCIO = 2L;

	/**
	 * Tipo histórico do negócio na grade
	 */
	public static final String TP_HISTO_NGOCO_GRADE = "0";

	/**
	 * Descrição de justificativa de alteração de restricao para alteração do laudo
	 */
	public static final String RESTRICAO_JUSTIFICATIVA_ALTERACAO_RESTRICAO_ALTERACAO_LAUDO = " ALTERACAO DE LAUDO DE VISTORIA";

	/**
	 * Mensagem de erro indicando que o endosso deste laudo não foi encontrado
	 */
	public static final String ERRO_ENDOSSO_NAO_ENCONTRADO = "error.naoexisteendosso";

	/**
	 * Negócio em grade
	 */
	public static final String SITUACAO_NEGOCIO_GRADE = "GRD";

	/**
	 * Situação de negócio indicando que o negócio é uma apolice
	 */
	public static final String SITUACAO_NEGOCIO_APO = "APO";

	/**
	 * Mensagem de erro indicando que o laudo não permirte alteração
	 */
	public static final String ERRO_VISTORIA_NAO_PERMITE_ALTERACAO = "error.vistorianaopermitealteracao";

	/**
	 * Indica que o laudo foi recusado
	 */
	public static final String SITUACAO_VISTORIA_PREVIA_RECUSADA = "R";

	/**
	 * Indica que o laudo é uma proposta
	 */
	public static final String SITUACAO_VISTORIA_PREVIA_PROPOSTA = "P";

	/**
	 * Indica que o laudo está sujeito a analise
	 */
	public static final String SITUACAO_VISTORIA_PREVIA_SUJEITO_ANALISE = "S";

	// Situacao do laudo ou negocio

	/**
	 * Indica que o laudo está em aceitação forçaca
	 */
	public static final String SITUACAO_VISTORIA_PREVIA_ACEITACAO_FORCADA = "AF";

	public static final String SITUACAO_VISTORIA_PREVIA_REGULARIZADO = "RV";

	// fim Situacao do laudo ou negocio

	// Situacao do NEGOCIO e ITEM SEGURADO

	/**
	 * Indica que o item ou negocio passaram por liberação
	 */
	public static final String SITUACAO_ITEM_NEGOCIO_LIBERACAO = "LIB";

	/**
	 * Indica que o item ou negocio estão com alguma pendencia
	 */
	public static final String SITUACAO_ITEM_NEGOCIO_PENDENTE = "PEN";

	/**
	 * Indica que o item ou negocio foram recusados
	 */
	public static final String SITUACAO_ITEM_NEGOCIO_RECUSA = "REC";

	/**
	 * Indica que o item ou negocio não tem nenhum situação no momento
	 */
	public static final String SITUACAO_ITEM_NEGOCIO_SEM_SITUACAO = "";

	// FIM Situacao do NEGOCIO e ITEM SEGURADO

	/**
	 * Indica que o laudo está em aceitação liberada
	 */
	public static final String SITUACAO_VISTORIA_PREVIA_LIBERADA = "L";

	/**
	 * Indica que o laudo está aceita
	 */
	public static final String LAUDO_STATUS_ACEITAVEL = "A";

	/**
	 * Indica que o Item Segurado esta na grade de vistoria previa
	 */
	public static final String SITUACAO_ITEM_SEGURADO_GRADE_VISTORIA = "V";

	/**
	 * Indica usuario de processos automáticos
	 */
	public static final String USUARIO_AUTOMATICO_SISTEMA = "RECVP";

	/**
	 * Mensagem de erro indicando que o laudo bloqueado para alteração
	 */
	public static final String ERRO_LAUDO_BLOQUEADO = "error.laudoBloqueiado";

	/**
	 * Mensagem de erro indicando que jão foi emitido uma apolice para este laudo
	 */
	public static final String ERRO_JA_FOI_EMITIDA_APOLICE = "error.apoliceojaemitida";

	/**
	 * Mensagem informando o motivo de não poder reclassificar este laudo
	 */
	public static final String DESBLOQUEAR_LAUDO_PARA_RECLASSIFICACAO = "error.desbloquearLaudoReclassificacao";

	/**
	 * Mensagem Indicando que a reclassificação só pode ser feita pela grade.
	 */
	public static final String RECLASSIFICACAO_PELA_GRADE = "error.reclassificacaoPelaGrade";

	/**
	 * Mensagem indicando que o laudo está vinculado a um item ja emitodo
	 */
	public static final String LAUDO_VINCULADO = "erro.laudoVinculado";

	/**
	 * Mensagem que indica que um laudo já foi desbloqueado
	 */
	public static final String VISTORIA_DESBLOQUEADA = "erro.vistoriaDesbloqueada";

	/**
	 * Mensagem indicando que a data de processamento não está no prazo de vigencia
	 */
	public static final String PRAZO_PARAMETRO_NAO_CADASTRADO = "erro.qtdDiasMaxDesbloqueioLaudoNaoCadastrado";

	/**
	 * Mensagem indicando que a data de processamento não está no prazo de vigencia
	 */
	public static final String PRAZO_DESBLOQUEIO_LAUDO_ULTRAPASSADO = "erro.prazodesbloqueioultrapassado";

	/**
	 * Mensagem indicando que Não foi emcontrado nenhum item de negócio
	 */
	public static final String NENHUM_ITEM_ENCONTRADO = "erro.nenhumItemEncontrado";

	/**
	 * Mensagem indicando que o laudo foi desbloqueado
	 */
	public static final String DESBLOQUEIO_EFETUADO = "erro.desbloqueioEfetuado";

	/**
	 * Indica se é ou veiculo de carga
	 */
	public static final String VEICULO_CARGA = "S";

	/**
	 * Indica se é um veiculo de passeio
	 */
	public static final String VEICULO_PASSEIO = "N";

	/**
	 * Indica se é ou veiculo de carga, se diferencia dos acima somente por ter numero
	 */
	public static final Long MODULO_VEICULO_CARGA = 9L;

	/**
	 * Indica se é um veiculo de passeio, se diferencia dos acima somente por ter numero
	 */
	// verificado quanto ao modulo 20
	public static final Long MODULO_VEICULO_PASSEIO = 7L;

	/**
	 * Código da situação do historico
	 */
	public static final Long CODIGO_SITUACAO_HISTORICO_DESBLOQUEIO_USUARIO = 2L;

	/**
	 * Código da situação do historico 91
	 */
	public static final Long CODIGO_SITUACAO_HISTORICO_91 = 91L;

	/**
	 * Código da situação do historico que indica inconsistencia de laudo com encosso
	 */
	public static final Long CODIGO_SITUACAO_HISTORICO_INCONSISTENCIA_LAUDO_COM_ENDOSSO = 84L;

	/**
	 * Código da situação do historico que indica inconsistencia de laudo com encosso
	 */
	public static final Long CODIGO_SITUACAO_HISTORICO_INCONSISTENCIA_LAUDO_COM_CLIENTE = 91L;

	/**
	 * Sigla que indica que há inconsistencia entre laudo e proposta
	 */
	public static final String ITEM_SEGURADO_CODIGO_INCONSISTENCIA_LAUDO = "s";

	/**
	 * Sigla que indica a substituição de um item no endosso. Pode ser s ou U
	 */
	public static final String ENDOSSO_SUBSTITUICAO_ITEM_S = "S";

	/**
	 * Sigla que indica a substituição de um item no endosso. Pode ser s ou U
	 */
	public static final String ENDOSSO_SUBSTITUICAO_ITEM_U = "U";

	/**
	 * Indica que um laudo está desbloqueado
	 */
	public static final Long LAUDO_STATUS_DESBLOQUEADO = 0L;

	/**
	 * Indica que um laudo está bloqueado
	 */
	public static final Long LAUDO_STATUS_BLOQUEADO = 1L;

	/**
	 * Tipos de bloqueio de um laudo
	 */
	public static final Long LAUDO_TIPO_BLOQUEIO_TEMPO = 1L;
	public static final Long LAUDO_TIPO_BLOQUEIO_SUPERVISAO = 2L;

	/**
	 * Código de finalidade do laudo por supervisão
	 */
	public static final Long CODIGO_FINALIDADE_LAUDO_SUPERVISAO = 10L;

	/**
	 * Mensagem que indica que o laudo já tem uma proposta vinculada
	 */
	public static final String SITUACAO_LAUDO_VINCULADO = "situacaoLaudo.Vinculado";

	/**
	 * Mensagem que indica que o laudo não tem uma proposta vinculada
	 */
	public static final String SITUACAO_LAUDO_NAO_VINCULADO = "situacaoLaudo.NaoVinculado";

	/**
	 * Mensagem que indica que o laudo não tem uma proposta vinculada e é do sistema Plataforma
	 */
	public static final String SITUACAO_LAUDO_NAO_BLOQUEADO_PLATAFORMA = "situacaoLaudo.NaoBloqueado.Plataforma";

	/**
	 * Mensagem que indica que o laudo está bloqueado
	 */
	public static final String SITUACAO_LAUDO_BLOQUEADO = "situacaoLaudo.Bloqueado";

	/**
	 * Mensagem que indica que o laudo está bloqueado por supervisão
	 */
	public static final String SITUACAO_LAUDO_BLOQUEADO_SUPERVISAO = "situacaoLaudo.Bloqueado.Supervisao";

	/**
	 * Mensagem que indica que o laudo está bloqueado
	 */
	public static final String RECLASSIFICADO_COM_SUCESSO = "mensagem.reclassificadoComSucesso";

	/**
	 * Mensagem indicando que o usuário não tem alçada para reclassificar
	 */
	public static final String USUARIO_SEM_ALCADA_PARA_RECLASSIFICACAO = "erro.usuarioSemAlcadaParaReclassificacao";

	// Código do de fechamento
	public static final Long MANUAL_NEG = 01L;
	public static final Long AUTOMATICO_NEG = 02L;
	public static final Long MANUAL_END = 03L;
	public static final Long AUTOMATICO = 04L;
	public static final String ALTERACAO_DE_LAUDO_DE_VISTORIA = "erro.alteracaoDeLaudoDeVistoria";

	/**
	 * Status do laudo recusado
	 */
	public static final String LAUDO_STATUS_RECUSADO = "R";

	/**
	 * Status do laudo sujeito a analise
	 */
	public static final String LAUDO_STATUS_SUJEITO_ANALISE = "S";

	/**
	 * Código que identifica o motivo do histórico da vistoria prévia por inconsistencia de cpf e cgc com DUT
	 */
	public static final Long MOTIVO_HISTORICO_LAUDO_INCONSISTENCIA_CPF_CGC = 11L;

	/**
	 * Código que identifica o campo CD_HISTORICO
	 * 91 Não está especificado no dominio
	 */
	public static final Long HISTORICO_LAUDO_CD_HISTORICO = 91L;

	/**
	 * Código que identifica o motivo do histórico da vistoria prévia por inconsistencia de dados do veiculo
	 */
	public static final Long MOTIVO_HISTORICO_LAUDO_INCONSISTENCIA_DADOS_VEICULO = 4L;

	/**
	 * Código que identifica o motivo do histórico da vistoria prévia por inconsistencia com dados que nao influenciam no calculo
	 */
	public static final Long MOTIVO_HISTORICO_LAUDO_INCONSISTENCIA_CALCULO = 10L;

	/**
	 * Quando usado no indicador de sobreposição dos dados inconsistentes entre a vistoria prévia e a proposta Identificador de divergência liberada,
	 * indica devergência liberada
	 */
	public static final String INDICADOR_DE_DIVERGEBCIA_LIBERADA = "x";

	/**
	 * Mensagem que indica que uma divergência foi liberada altomativamente
	 */
	public static final String DIVERGENCIA_LIBERADA_ALTOMATIVAMENTE = "DIVERGENCIA LIBERADA AUTOMATICAMENTE";

	/**
	 * Numero de versão de laudo 0
	 */
	public static final Long NUMERO_VERSAO_LAUDO = 0L;

	/**
	 * Motivo de inconsistencia do historico do laudo indicando que o cpf dute está em transferencia
	 */
	public static final Long MOTIVO_INCONSISTENCIA_HISTORICO_LAUDO_CPF_DUTE_EM_CONFERENCIA = 16L;

	/**
	 * Motivo de inconsistencia do historico do laudo indicando que o cgc dute CRLV está em transferencia (Definir o que significa o numero 17 no CD_INC_HIST)
	 */
	public static final Long MOTIVO_INCONSISTENCIA_HISTORICO_LAUDO_CGC_DUTE_EM_CONFERENCIA = 17L;

	/**
	 * Motivo de inconsistencia do historico do laudo com relação ao nome do fabricante
	 */
	public static final Long MOTIVO_INCONSISTENCIA_HISTORICO_LAUDO_FABRICANTE = 9L;

	/**
	 * Motivo de inconsistencia do historico do laudo com relação ao nome do modelo
	 */
	public static final Long MOTIVO_INCONSISTENCIA_HISTORICO_LAUDO_MODELO = 8L;

	/**
	 * Motivo de inconsistencia do historico do laudo com relação ao ano do modelo
	 */
	public static final Long MOTIVO_INCONSISTENCIA_HISTORICO_LAUDO_ANO_MODELO = 10L;

	/**
	 * Motivo de inconsistencia do historico do laudo com ralação a tipo de combustivel
	 */
	public static final Long MOTIVO_INCONSISTENCIA_HISTORICO_LAUDO_TIPO_COMBUSTIVEL = 12L;

	/**
	 * Motivo de inconsistencia do historico do laudo com relação a ano de fabricação
	 */
	public static final Long MOTIVO_INCONSISTENCIA_HISTORICO_LAUDO_ANO_FABRICACAO = 18L;

	/**
	 * Motivo de inconsistencia do historico do laudo com relação a COR DO VEICULO
	 */
	public static final Long MOTIVO_INCONSISTENCIA_HISTORICO_LAUDO_COR_VEICULO = 11L;

	/**
	 * Motivo de inconsistencia do historico do laudo com relação a kilometro rodado
	 */
	public static final Long MOTIVO_INCONSISTENCIA_HISTORICO_LAUDO_KM_RODADO = 19L;

	/**
	 * Indica de um historico de laudo está livre de inconsistencias
	 */
	public static final Long MOTIVO_INCONSISTENCIA_HISTORICO_LAUDO_LIVRE_INCONSISTENCIA = 0L;

	/**
	 * Código de caracteristica que indica O fabricante de um veiculo de passeio
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_PASSEIO_FABRICANTE = 9L;

	/**
	 * Código de caracteristica que indica o fabricante de um veiculo de carga
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_CARGA_FABRICANTE = 54L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta o tipo de combustivel veiculo passeio
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_PASSEIO_TIPO_COMBUSTIVEL = 12L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta o tipo de combustivel veiculo carga
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_CARGA_TIPO_COMBUSTIVEL = 81L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta o ano de fabricação veiculo passeio
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_PASSEIO_ANO_FABRICACAO = 24L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta o ano de fabricação veiculo carga
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_CARGA_ANO_FABRICACAO = 59L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta a cor veiculo passeio
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_PASSEIO_COR = 27L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta a cor veiculo carga
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_CARGA_COR = 62L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta o numero do laudo de um veiculo de passeio
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_PASSEIO_NUMERO_LAUDO = 46L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta a data
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_DATA = 64L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta o numero do laudo de um veiculo de carga
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_CARGA_NUMERO_LAUDO = 65L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta a empresa vistoriadora
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_EMPRESA_VISTORIADORA = 67L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta a kilometragen do veiculo
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_KILOMETRAGEM_VEICULO = 172L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta o modelo do veiculo de passeio
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_PASSEIO_MODELO = 10L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta o modelo do veiculo de carga
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_CARGA_MODELO = 52L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta o ano modelo do veiculo de passeio
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_PASSEIO_ANO_MODELO = 13L;

	/**
	 * codigo de valor de codigo de caracteristica que represnta o ano modelo do veiculo de carga
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_CARGA_ANO_MODELO = 55L;

	/**
	 * código de valor de caracteristica que representa o tipo de conbustivel do veiculo de carga
	 */
	public static final Long CODIGO_VALOR_CARACTERISTICA_VEICULO_CARGA_TIPO_CONBUSTIVEL = 12L;

	/**
	 * Indica que tipo de pessoa é fisíca
	 */
	public static final String TIPO_PESSOA_FISICA = "F";

	/**
	 * Indica que tipo de pessoa é jurídica
	 */
	public static final String TIPO_PESSOA_JURIDICA = "J";

	/**
	 * O CODIGO DE INFORMACAO TECNICA 96 REFERE-SE A DUT EM NOME DE EMPRESA DE LEASING. PARA ESTE CASO NAO EH GERADA A DIVERGENCIA DUT X CLIENTE DA PROPOSTA.
	 */
	public static final Long CODIGO_INFORMACAO_TECNICA_DUT_EMPRESA_LEASING = 97L;

	/**
	 * Indica a quantidade de caracteres suportados pelo campo de justificativa na tabela de restrição de itens segurados
	 */
	public static final int TAMANHO_JUSTIFICATIVA_RESTRICAOITEMSEGURADO = 60;

	/**
	 * Indica a quantidade de caracteres suportados pelo campo de justificativa na tabela de histórico de laudo de vistoria prévia
	 */
	public static final int TAMANHO_JUSTIFICATIVA_HISTORICOLAUDOVISTORIAPREVIA = 30;

	/**
	 * Indica a quantidade de caracteres suportados pelo campo de justificativa na tabela de laudo vistoria prévia
	 */
	public static final int TAMANHO_JUSTIFICATIVA_LAUDOVISTORIAPREVIA = 120;

	public static final String JUSTIFICATIVA_LIBERACAO = "LIBERADA VIA PROCESSO DE VINCULO VP";

	/**
	 * Indica o valor do grupo e o parâmetro aonde será realizado o controle de execução do processo
	 * de conciliação das propostas de seguro com seus laudos de vistoria prévia.
	 */
	public static final Long PARAM_CONC_CD_GRP_PARAM = 5L;
	public static final String PARAM_CONC_CD_PARAM = "VISTORIA-CONCILIACAO";
	public static final String PARAM_LAUDO_EM_EXEC = "VISTORIA-PROCESSA_LAUDO";

	/**
	 * Indica o valor do grupo aonde serão cadastrados todos os e-mails a serem notificados no caso de
	 * acontecer uma chamada ao processo de conciliação de propostas de seguro e laudos de vistoria prévia
	 * enquanto o flag de execução indicar que a mesma ainda está sendo processada.
	 */
	public static final String EMAIL_PROCS_TP_PROCS = "GRD";
	public static final String EMAIL_PROCS_TP_FNCLD = "VIS";
	// verificado quanto ao modulo 20
	public static final Long EMAIL_PROCS_CD_MDUPR = 7L;

	/**
	 * e-mail padrão da área de sistemas
	 */
	public static final String EMAIL_AREA_SISTEMAS = "sistema@tokiomarine.com.br";

	public static final String FIRST_DATE = "1";

	public static final String TP_SEXO = "TP_SEXO";
	public static final String TP_PNTUR_VEICU = "TP_PNTUR_VEICU";
	public static final String TP_ESTAD_CIVIL = "TP_ESTAD_CIVIL";
	public static final String TP_CODUT_VEICU = "TP_CODUT_VEICU";
	public static final String TP_LOCAL_VSPRE = "TP_LOCAL_VSPRE";
	public static final String TP_CARRO_VEICU = "TP_CARRO_VEICU";
	public static final String TP_MTRAL_CARGA_VEICU = "TP_MTRAL_CARGA_VEICU";
	public static final String TP_UTLZC_VEICU = "TP_UTLZC_VEICU";
	public static final String TP_CMPLO_ACSRO_VSPRE = "TP_CMPLO_ACSRO_VSPRE";
	public static final String TP_AVARI_VEICU = "TP_AVARI_VEICU";
	public static final String TP_GUARD_VEICU = "TP_GUARD_VEICU";
	public static final String TP_CAMBO_VEICU = "TP_CAMBO_VEICU";
	public static final String TP_FORMA_CARRO_VEICU = "CD_FORMA_CARRO_VEICU";
	public static final String CD_INDCT_FRUST = "CD_MOTV_FRUDO";
	public static final String CD_FNALD_VSPRE = "CD_FNALD_VSPRE";
	public static final String CD_SIM_NAO = "CD_SIM_NAO";
	public static final String TP_STATU_VSPRE = "TP_STATU_VSPRE";
	public static final String CD_SITUC_VSPRE = "CD_SITUC_VSPRE";
	public static final String TIPO_COMBUSTIVEL_VEICULO_AUTO = "TP_COMBUSTIVEL";

	public static final String POR_ITEM = "ITEM";
	public static final String POR_NEGOCIO = "NEGOCIO";
	public static final String POR_DISPENSA = "DISPENSA";
	public static final String POR_DIVERGENCIA = "DIVERGENCIA";
	public static final String POR_RECLASSIFICACAO = "RECLASSIFICACAO";

	/** Placa AAVISAR */
	public static final String PLACA_AAVISAR = "AAVISAR";

	/** Placa AVI0000 */
	public static final String PLACA_AVI0000 = "AVI0000";

	public static final String CD_CARACTERISTICA_FABRICANTE = "FABRICANTE";
	public static final String CD_CARACTERISTICA_MODELO = "MARCA/MODELO";

	public static final String CODIGO_PAIS = "BR";

	public static final String DIR_ARQUIVO_LAUDO_IN = "VISTORIA_PREVIA/ENTRADA";
	public static final String DIR_ARQUIVO_LAUDO_HIST_IN = "VISTORIA_PREVIA/BACKUP";
	public static final String DIR_ARQUIVO_LAUDO_HIST_ERRO = "VISTORIA_PREVIA/BACKUP/LAUDOS_COM_ERROS";
	public static final String DIR_ARQUIVO_RET_LAUDO_BKP = "VISTORIA_PREVIA/SAIDA";
	public static final String DIR_SFT_ARQ_RET_LAUDO = "GRPDATA/SFT/REC";
	public static final String TO_EMAIL_ERRO = "murilo.penessor@";

	public static final String TIPO_ACESSORIO_PADRAO = "A";
	public static final String TIPO_ACESSORIO_SEGURANCA = "S";
	public static final String TIPO_ACESSORIO_EQUIPAMENTO = "E";

	/** Endereco properties */
	public static final String APPLICATION_RESOURCES_PROPERTIES = "resource/ApplicationResourcesVP.properties";

	/** Tipos de mensagens */
	public static final String TP_MSG_ERRO = "erro";
	public static final String TP_MSG_ALERTA = "alerta";
	public static final String TP_MSG_CONFIR = "confirma";

	/**
	 * ConstantesMRDAO para acesso ao serviço MAP
	 */
	public static final String COD_LOGRADOURO_RECUPERADO = "00";
	public static final String COD_LOGRADOURO_CEP_UNICO_RECUPERADO = "01";
	public static final String COD_PARAMETROS_MAP_INVALIDOS = "90";
	public static final String COD_LOGRADOURO_NAO_ENCONTRADO = "98";

	/**
	 * Situações Agendamento
	 */
	public static final String COD_SITUC_A_AGENDAR = "A_AGENDAR";
	public static final String COD_SITUC_AGENDADA = "AGD";
	public static final String COD_SITUC_RECEBIDA = "RCB";
	public static final String COD_SITUC_PENDENTE = "PEN";
	public static final String COD_SITUC_REALIZADA = "RLZ";
	public static final String COD_SITUC_REAGENDADA = "RGD";

	public static final String COD_SITUC_FIM = "FIM";

	public static final String COD_SITUC_A_REAGENDAR = "A_REAGENDAR";
	public static final String COD_SITUC_FRUSTRADA = "VFR";
	public static final String COD_SITUC_NAO_APROVADA = "NAP";
	public static final String COD_SITUC_CANCELADA = "CAN";
	public static final String COD_SITUC_NAO_AGENDADA = "NAG";

	/**
	 * Codigo Sistema Origem
	 */
	public static final Long ORIGEM_SSV = 1L;
	public static final Long ORIGEM_KCW = 2L;
	public static final Long ORIGEM_PLATAFORMA = 3L;
	public static final Long ORIGEM_SEM_PROPOSTA = 4L;

	/**
	 * Codigo Letra Sistema Origem
	 */
	public static final String LETRA_SYS_ORIGEM_PLATAFORMA = "TB";
	public static final String LETRA_SYS_ORIGEM_SSV = "TS";
	public static final String LETRA_SYS_ORIGEM_SEM_PROPOSTA = "TT";

	/**
	 * Tipo Local Vistoria
	 */
	public static final String COD_LOCAL_POSTO = "P";
	public static final String COD_LOCAL_DOMICILIO = "D";

	public static final String DS_LOCAL_POSTO = "Posto";
	public static final String DS_LOCAL_DOMICILIO = "Domicílio";

	/**
	 * Codigo indicador SIM/NAO
	 */
	public static final String COD_IND_SIM = "S";
	public static final String COD_IND_NAO = "N";

	/**
	 * Codigo Periodo Agendamento
	 */
	public static final String COD_PERIODO_MANHA = "M";
	public static final String COD_PERIODO_TARDE = "T";
	public static final String COD_PERIODO_COMERCIAL = "C";

	/**
	 * Codigo Tipos Veiculos
	 */
	public static final String COD_VEIC_AUTO_CLASSICO = "B";
	public static final String COD_VEIC_AUTO_TRADICIONAL = "P";
	public static final String COD_VEIC_AUTO_CARGA = "C";
	public static final String COD_VEIC_AUTO_FROTA = "F";
	public static final String COD_VEIC_AUTO_UTILITARIO = "U";
	public static final String COD_VEIC_AUTO_PASSEIO_POPULAR = "O";
	public static final String COD_VEIC_AUTO_CARGA_POPULAR = "Z";

	/**
	 * Descricao Tipos Veiculos
	 */
	public static final String DS_VEIC_AUTO_CLASSICO = "Auto Passeio Clássico";
	public static final String DS_VEIC_AUTO_TRADICIONAL = "Auto Passeio Tradicional";
	public static final String DS_VEIC_AUTO_UTILITARIO = "Auto Carga Utilitário";
	public static final String DS_VEIC_AUTO_CARGA = "Auto Carga";
	public static final String DS_VEIC_AUTO_FROTA = "Auto Frota";
	public static final String DS_VEIC_AUTO_PASSEIO_POPULAR = "Auto Passeio Popular";
	public static final String DS_VEIC_AUTO_CARGA_POPULAR = "Auto Carga Popular";

	/**
	 * Codigo Modulo Produto
	 */
	public static final Long COD_MOD_TRADICIONAL = 7L;
	public static final Long COD_MOD_CLASSICO = 20L;
	public static final Long COD_MOD_CARGA = 9L;
	public static final Long COD_MOD_PASSEIO_POPULAR = 21L;
	public static final Long COD_MOD_CARGA_POPULAR = 22L;

	public static final int CODIGO_ARQUIVO_ACESSORIOS = 1;
	public static final int CODIGO_ARQUIVO_AVARIAS = 2;
	public static final int CODIGO_ARQUIVO_PECAS = 3;
	public static final int CODIGO_ARQUIVO_ACEITACAO_RECUSA = 4;
	public static final int CODIGO_ARQUIVO_TIPO_CARROCERIA = 5;

	/**
	 * Codigo Corretor Santander
	 */
	public static final Long COD_CORRETOR_SANTANDER = 83501L;

	public static final Long COD_PARECER_TECNICO_VEICULO_OK = 116L;

	public static final String INDICADOR_PARECER_TECNICO_INATIVO = "I";

	public static final String INDICADOR_AVARIA_INATIVA = "I";
	public static final String INDICADOR_AVARIA_ATIVA = "A";

	public static final String INDICADOR_PECA_INATIVA = "I";
	public static final String INDICADOR_PECA_ATIVA = "A";

	public static final String INDICADOR_ACESSORIO_INATIVO = "I";
	public static final String INDICADOR_ACESSORIO_ATIVO = "A";

	public static final Long INDICADOR_AGRUPAMENTO_PRESTADROA_DEKRA = 1L;

	/**
	 * Código Tipo Laudo
	 */
	public static final String COD_TIPO_LAUDO_IMPRODUTIVO = "I";
	public static final String COD_TIPO_LAUDO_IMPRODUTIVO_INCLUIDO = "C";
	public static final String COD_TIPO_LAUDO_IMPRODUTIVO_ESTORNADO = "E";

	/**
	 * Código Status Processamento LOTE
	 */
	public static final String COD_STATUS_LOTE_NAO_ENVIADO = "N";
	public static final String COD_STATUS_LOTE_ENVIADO = "E";
	public static final String COD_STATUS_LOTE_RETIRADO = "R";

	/**
	 * Código Visão Relatorio Improdutiva.
	 */
//	public static final String COD_VISAO_GERAL = "G";
//	public static final String COD_VISAO_SUPERINTENDENCIA = "SI";
//	public static final String COD_VISAO_SUCURSAL = "S";
//	public static final String COD_VISAO_CORRETOR = "C";
//	public static final String COD_VISAO_VISTORIA_PREVIA = "VP";
//	public static final String COD_VISAO_VEICULO = "VL";

	public static final Long COD_CORRETOR_COMUM_IMPRODUTIVO = 999999L;
	public static final Long COD_SUCURSAL_COMUM_IMPRODUTIVO = 9999L;
	public static final String NOME_CORRETOR_COMUM_IMPRODUTIVO = "PERCENTUAL CORRETOR NÃO CADASTRADO";

	public static final String SITUACAO_AJUSTE_ACATADO = "A";
	public static final String PARAM_TEMPLATE_EMAIL_PENDENCIA_VISTORIA_PREVIA_GNT = "TEMPLATE_EMAIL_PENDENCIA_VISTORIA_PREVIA_GNT";
	public static final String PARAM_TEMPLATE_EMAIL_CONFIRMACAO_AGENDAMENTO_VISTORIA_PREVIA_GNT = "TEMPLATE_EMAIL_CONFIRMACAO_AGENDAMENTO_VP_GNT";
	public static final String PARAM_TEMPLATE_EMAIL_CANCELAMENTO_AGENDAMENTO_VISTORIA_PREVIA_GNT = "TEMPLATE_EMAIL_CANCELAMENTO_AGENDAMENTO_VP_GNT";
	public static final String PARAM_TEMPLATE_EMAIL_AGENDAMENTO_RECUSADO = "PARAM_TEMPLATE_EMAIL_AGENDAMENTO_RECUSADO";

	/**
	 * Representa as informações de Característica. <br>
	 */
	public enum Caracteristica {

		// Comuns
		FABRICANTE(9L),
		MODELO(10L),
		COMBUSTIVEL(12L),
		ANO_MODELO(13L),;

		/** Codigo referente passeio. */
		private final Long codigoPasseio;

		/**
		 * Caracteristica.
		 *
		 * @param codigoPasseio the codigo passeio
		 * @param codigoCarga the codigo carga
		 */
		private Caracteristica(Long codigoPasseio) {

			this.codigoPasseio = codigoPasseio;
		}

		/**
		 * Recupera codigo passeio
		 *
		 * @return codigo passeio
		 */
		public Long getCodigoPasseio() {
			return codigoPasseio;
		}

		/**
		 * Recupera codigo carga.
		 *
		 * @return codigo carga
		 */

		/**
		 * Retorna o codigo de acordo com o modulo produto.
		 *
		 * @param codModuloProduto Codigo Modulo Produto
		 * @return Codigo da caracteristica
		 */
		public Long getCodigo() {
				return codigoPasseio;
			}

		}

	public enum SiglaPrestadora{

		SIGLA_DEKRA(1L,"D"),
		SIGLA_SULTEC(4L,"S"),
		SIGLA_BONE(7L,"B"),
		SIGLA_CONFERE(8L,"C"),
		SIGLA_RC_PERICIA(13L,"R"),
		SIGLA_MULROTA(14L,"M"),
		SIGLA_MPM(15L,"P"),
		SIGLA_STYLLUS(16L,"Y"),
		SIGLA_LOOK(18L,"L"),
		NAO_ENCONTRADO(99L,"X");

		SiglaPrestadora(Long valor,String chaveMensagem) {
			this.valor = valor;
			this.chaveMensagem = chaveMensagem;
	}

		private Long valor;
		private String chaveMensagem;

		public Long getValor() {
			return valor;
		}

		public String getChaveMensagem() {
			return chaveMensagem;
		}

		public static SiglaPrestadora getEnum(Long valor) {

			for(SiglaPrestadora sigla: values()){
				if(sigla.getValor().equals(valor)){
					return sigla;
				}
			}
			return SiglaPrestadora.NAO_ENCONTRADO;
		}
	}

	// Valores de status de decodificação de chassi na VP.
	public static final Long DECODIFICADOR_INDISPONIVEL = 0L;
	public static final Long DECODIFICADO_E_LAUDO_VALIDO = 1L;
	public static final Long DECODIFICADO_E_LAUDO_INVALIDO = 2L;
	public static final Long MESMO_CHASSI_PROPOSTA_E_LAUDO = 3L;
	public static final Long CHASSI_NAO_DECODIFICADO = 9L;

	// Valores para caracteristica do tipo de carroceria.
	public static final Long CODIGO_CARAC_TIPO_CARROCERIA = 333L;
	public static final Long CODIGO_CARAC_TIPO_CARROCERIA_NAO_INFORMADO = 18082L;

	public static final String TIPO_CARROCERIA_VEICULO = "TP_CARROCERIA";
	public static final String DT_REF_CARAC_TIPO_CARROCERIA = "DT_REF_CARAC_TIPO_CARROCERIA";
	public static final Long CD_CARACT_INICIAL_TP_CARROCERIA = 18678L;
	public static final Long CD_CARACT_FINAL_TP_CARROCERIA = 18697L;

	public static final String CD_MOTV_SITUC_AGMTO_POSTO = "POSTO";
	public static final String CD_MOTV_SITUC_AGMTO_DOMICILIO = "DOCLO";

	public static final String PARAM_URL_DECODIFICADOR = "PARAM_URL_DECODIFICADOR";
	public static final String PARAM_URL_AJUSTE = "PARAM_URL_AJUSTE";
	public static final String PARAM_URL_CONSULTA_COTACAO_CTA = "URL_CONSULTA_COTACAO_CTA";

	public static final Long TIPO_LIBERACAO_RESTRICAO_DISPENSA_VP = 10L;
	
	public static final String LOG_ERRO_GPA = "LOG_ERRO_GPA";

}