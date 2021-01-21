package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.lang.reflect.Type;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParametroVistoriaPreviaGeral;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.DistribuicaoMunicipioDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ParamAGDAutomaticoMobileDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ParametroVistoriaPreviaGeralRepository;

@Service
public class ParametroVPService {

	private static final String COD_PARECER_SUJEITO_ANALISE_PERMITE_AGENDAMENTO = "CD_PAREC_SUJ_ANALISE_VIDROS_PERMT_AGD";
	
	private static final String QTD_DIAS_MAX_DESBLOQUEIO_LAUDO = "QTD_DIAS_MAX_DESBLOQUEIO_LAUDO";

	private static final String QTD_DIAS_RETROCESSO_AGENDAMENTO = "QTD_DIAS_RETROCESSO_AGENDAMENTO";

	private static final String CODIGOS_CORRETORES_PILOTO = "CODIGOS_CORRETORES_PILOTO";

	private static final String SERVIDOR_MAP = "SERVIDOR_MAP";

	private static final String NUMERO_ALCADA_CANCELA_AGENDAMENTO = "NUMERO_ALCADA_CANCELA_AGENDAMENTO";

	private static final String NUMERO_ALCADA_AGENDAMENTO_MOBILE = "NUMERO_ALCADA_AGENDAMENTO_MOBILE";

	private static final String CORRETORES_PERMITIDOS_AGD_MOBILE = "CORRETORES_PERMITIDOS_AGD_MOBILE";

	private static final String CONTINGENCIA_AGENDAMENTO_MOBILE = "CONTINGENCIA_AGENDAMENTO_MOBILE";
	
	private static final String QT_DIAS_VALIDADE_AGENDAMENTO = "QT_DIAS_VALIDADE_AGENDAMENTO";
	
	private static final String CORRETORES_AGD_AUTOMATICO_MOBILE = "CORRETORES_AGD_AUTOMATICO_MOBILE";
	
	private static final String CORRETORES_RGD_AUTOMATICO_MOBILE = "CORRETORES_RGD_AUTOMATICO_MOBILE";

	private static final String AGENDAMENTO_DOMICILIO_SANTANDER_DT_FINAL = "AGENDAMENTO_DOMICILIO_SANTANDER_DT_FINAL";

	private static final String AGENDAMENTO_DOMICILIO_DIAS_UTEIS = "AGENDAMENTO_DOMICILIO_DIAS_UTEIS";

	private static final String PRESTADOR_POSTO_UF_ = "PRESTADOR_POSTO_UF_";

	private static final String PRESTADOR_DOMICILIO_UF_ = "PRESTADOR_DOMICILIO_UF_";
	
	private static final String DISTRIBUICAO_PRESTADORA_UF = "DISTRIBUICAO_PRESTADORA_UF";
	
	private static final String DATA_REFER_LAUDOS_IMPRODUTIVOS = "DATA_REFER_LAUDOS_IMPRODUTIVOS";
	
	private static final String MENSAGEM_MOTIVO_LAUDO_IMPRODUTIVO_ = "MENSAGEM_MOTIVO_LAUDO_IMPRODUTIVO_";

	@Autowired
	private ParametroVistoriaPreviaGeralRepository repository;
	
	@Autowired
	private Gson gson;
	
	private static final String PARAMETRO = "Parâmetro [";
	
	private static final String NOTFOUND = "] não encontrado.";
	
	public List<ParametroVistoriaPreviaGeral> obterParametroVistoriaPreviaGeral(String nomeParametro) {
		return repository.findByNmParamVspre(nomeParametro);
	}
	
	private String obterParametroString(String nomeParametro) {
		return repository.findOneByNmParamVspre(nomeParametro)
				.map(ParametroVistoriaPreviaGeral::getVlParamVspre)
				.orElseThrow(() -> new NullPointerException(PARAMETRO + nomeParametro + NOTFOUND));
	}

	private Long obterParametroLong(String nomeParametro) {
		return repository.findOneByNmParamVspre(nomeParametro)
				.map(p -> new Long(p.getVlParamVspre()))
				.orElseThrow(() -> new NullPointerException(PARAMETRO + nomeParametro + NOTFOUND));
	}

	private Integer obterParametroInteger(String nomeParametro) {
		return repository.findOneByNmParamVspre(nomeParametro)
				.map(p -> new Integer(p.getVlParamVspre()))
				.orElseThrow(() -> new NullPointerException(PARAMETRO + nomeParametro + NOTFOUND));
	}
	
	public Long obterAlcadaCancelaAgendamento() {
		return obterParametroLong(NUMERO_ALCADA_CANCELA_AGENDAMENTO);
	}

	public Long obterAlcadaAgendamentoMobile() {
		return obterParametroLong(NUMERO_ALCADA_AGENDAMENTO_MOBILE);
	}

	public String obterCorretoresPermitidosAgendamentoMobile() {
		return obterParametroString(CORRETORES_PERMITIDOS_AGD_MOBILE);
	}
	
	public String obterServidorMap() {
		return repository.findOneByNmParamVspre(SERVIDOR_MAP)
				.map(ParametroVistoriaPreviaGeral::getVlParamVspre)
				.orElseThrow(() -> new NullPointerException(PARAMETRO + SERVIDOR_MAP + NOTFOUND));
	}
	
	/**
	 * Verifica se o corretor tókio informado participa do piloto
	 *
	 * @param codigoCorretor código interno do corretor a ser verificado
	 * @return true se corretor informado participa do piloto ou false caso contrário
	 */
	public boolean isCorretorParticipantePiloto(Long codigoCorretor) {

		List<ParametroVistoriaPreviaGeral> parametros = repository.findByNmParamVspre(CODIGOS_CORRETORES_PILOTO);

		if (parametros == null || parametros.isEmpty()) { return true; }

		// Comentado para recuperar a lista de parametros de corretores do piloto - LEONARDO[02/10]

		for (int i = 0 ; i < parametros.size() ; i++) {

			if (parametros.get(i) != null && !StringUtils.isEmpty(parametros.get(i).getVlParamVspre())) {

				List<String> codigosCorretoresPiloto = Arrays.asList(parametros.get(i).getVlParamVspre().split(","));

				if (codigosCorretoresPiloto != null && !codigosCorretoresPiloto.isEmpty() && codigosCorretoresPiloto.contains(codigoCorretor.toString())) {
					  return true; 
				}
			}

		}

		return false;
	}

	public Set<Long> obterCodParecerSujeitoAnalisePermiteAgendamento() {
		String[] cods = obterParametroString(COD_PARECER_SUJEITO_ANALISE_PERMITE_AGENDAMENTO).split(",");
		return Stream.of(cods).map(Long::valueOf).collect(Collectors.toSet());
	}
	
	public Set<ParamAGDAutomaticoMobileDTO> obterCorretoresAGDAutomaticoMobile() {
		String parametroString = obterParametroString(CORRETORES_AGD_AUTOMATICO_MOBILE);
		
		if ("*".equals(parametroString)) {
			return Collections.emptySet();
		}
		
		Type type = new TypeToken<HashSet<ParamAGDAutomaticoMobileDTO>>(){}.getType();
		
		return gson.fromJson(parametroString, type);
	}
	
	public Set<Long> obterCorretoresRGDAutomaticoMobile() {
		String cods = obterParametroString(CORRETORES_RGD_AUTOMATICO_MOBILE);
		
		if ("*".equals(cods)) {
			return Collections.emptySet();
		}
		
		return Stream.of(cods.split(",")).map(Long::valueOf).collect(Collectors.toSet());
	}
	
	public boolean isCorretorRGDAutomaticoMobile(Long cdCorretor) {
		return obterCorretoresRGDAutomaticoMobile().contains(cdCorretor);
	}

	public Long obterQuantidadeDiasMaxDesbloqueioLaudo() {
		return obterParametroLong(QTD_DIAS_MAX_DESBLOQUEIO_LAUDO);
	}

	public Integer obterQuantidadeDiasValidadeAgendamento() {
		return obterParametroInteger(QT_DIAS_VALIDADE_AGENDAMENTO);
	}

	/**
	 * Obtém a quantidade de dias em que um agendamento/reagendamento pendente vence
	 *
	 * @return
	 */
	public Integer obterQtdDiasRetrocessoAgendamento() {
		return obterParametroInteger(QTD_DIAS_RETROCESSO_AGENDAMENTO);
	}
	
	public List<ParametroVistoriaPreviaGeral> findBylistaParametroVistoriaPreviaGeral() {
		return repository.findBylistaParametroVistoriaPreviaGeral();
	}

	public boolean isContingenciaAtivada() {
		return obterParametroString(CONTINGENCIA_AGENDAMENTO_MOBILE).equals("S");
	}

	public Integer obterDataFinalAGDDomicilioSantander() {
		return obterParametroInteger(AGENDAMENTO_DOMICILIO_SANTANDER_DT_FINAL);
	}

	public Long obterQtdDiasUteisAGDDomicilioSantander() {
		return obterParametroLong(AGENDAMENTO_DOMICILIO_DIAS_UTEIS);
	}

	public Long obterPrestadoraPostoUF(String uf) {
		return obterParametroLong(PRESTADOR_POSTO_UF_ + uf.toUpperCase());
	}

	public Long obterPrestadoraDomicilioUF(String uf) {
		return obterParametroLong(PRESTADOR_DOMICILIO_UF_ + uf.toUpperCase());
	}

	public List<DistribuicaoMunicipioDTO> obterParamDistribuicaoUf() {
		String parametroString = obterParametroString(DISTRIBUICAO_PRESTADORA_UF);
		
		if ("*".equals(parametroString)) {
			return Collections.emptyList();
		}
		
		Type type = new TypeToken<ArrayList<DistribuicaoMunicipioDTO>>(){}.getType();
		
		return gson.fromJson(parametroString, type);
	}
	
	public void salvarParamDistribuicaoUf(Set<DistribuicaoMunicipioDTO> params) {
		ParametroVistoriaPreviaGeral parametro = repository.findOneByNmParamVspre(DISTRIBUICAO_PRESTADORA_UF)
		.orElseThrow(() -> new NullPointerException(PARAMETRO + DISTRIBUICAO_PRESTADORA_UF + NOTFOUND));
		
		if (CollectionUtils.isEmpty(params)) {
			parametro.setVlParamVspre("*");
		} else {
			if ("*".equals(parametro.getVlParamVspre())) {
				parametro.setVlParamVspre(gson.toJson(params));
			} else {
				List<DistribuicaoMunicipioDTO> paramsAtuais = obterParamDistribuicaoUf();
				params.forEach(p -> {
					paramsAtuais.remove(p);
					paramsAtuais.add(p);
				});
				
				parametro.setVlParamVspre(gson.toJson(paramsAtuais));
			}
		}
		
		parametro.setDtUltmaAlter(new Date());
		repository.save(parametro);
	}
	
	public YearMonth obterDataReferLaudosImprodutivos() {
		String dtReferencia = obterParametroString(DATA_REFER_LAUDOS_IMPRODUTIVOS);
		return YearMonth.of(Integer.parseInt(dtReferencia.split("/")[1]), Integer.parseInt(dtReferencia.split("/")[0]));
	}

	public void salvarDataReferLaudosImprodutivos(YearMonth proximoMesReferencia) {
		ParametroVistoriaPreviaGeral param = repository.findOneByNmParamVspre(DATA_REFER_LAUDOS_IMPRODUTIVOS)
				.orElseThrow(() -> new NullPointerException(PARAMETRO + DATA_REFER_LAUDOS_IMPRODUTIVOS + NOTFOUND));

		param.setVlParamVspre(proximoMesReferencia.getMonthValue() + "/" + proximoMesReferencia.getYear());
		param.setDtUltmaAlter(new Date());

		repository.save(param);
	}

	/**
	 * Carrega Map com Mensagens Improdutivas < Letra_Motivo_Improdutivo, Mensagem>
	 */
	public Map<String,String> carregarMensagemImprodutiva() {

		Map<String,String> mapMensagem = new HashMap<String,String>();

		List<ParametroVistoriaPreviaGeral> listaParametro = repository.findByNmParamVspreStartsWith(MENSAGEM_MOTIVO_LAUDO_IMPRODUTIVO_);

		for (ParametroVistoriaPreviaGeral parametro : listaParametro) {

			String nomeParametro = parametro.getNmParamVspre();

			mapMensagem.put(nomeParametro.substring(nomeParametro.length() - 1,nomeParametro.length()),parametro.getVlParamVspre());
		}

		return mapMensagem;
	}
}
