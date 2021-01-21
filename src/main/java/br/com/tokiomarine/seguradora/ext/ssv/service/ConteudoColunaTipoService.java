package br.com.tokiomarine.seguradora.ext.ssv.service;

import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoCancelamentoEnum.CANCELAMENTO_A_CONFIRMAR;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoCancelamentoEnum.CANCELAMENTO_CONFIRMADO;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoCancelamentoEnum.CANCELAMENTO_DE_NAG;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoCancelamentoEnum.CANCELAMENTO_FORA_SISTEMA_CONFIRMADO;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoCancelamentoEnum.CANCELAMENTO_TELA_CONSULTA_AGTO;
import static br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.MotivoCancelamentoEnum.CANCELAMENTO_VISTORIADORA;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.ext.ssv.repository.ConteudoColunaTipoRepository;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ConteudoColunaTipoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoVistoria;

@Service
public class ConteudoColunaTipoService {

	@Autowired
	private ConteudoColunaTipoRepository conteudoColunaTipoRepository;

	public ConteudoColunaTipo findConteudoColunaTipoByNmColunTipoVlCntdoColunTipo(String nmColunTipo,
			String vlCntdoColunTipo) {
		return conteudoColunaTipoRepository.findConteudoColunaTipoByNmColunTipoVlCntdoColunTipo(nmColunTipo,
				vlCntdoColunTipo);
	}

	public List<ConteudoColunaTipo> motivoDispensas() {
		return conteudoColunaTipoRepository.motivoDispensas();
	}

	public List<ConteudoColunaTipo> motivoDivergencia() {
		return conteudoColunaTipoRepository.motivoDivergencia();
	}

	public List<ConteudoColunaTipo> listaTipoLocalVistoria() {
		return conteudoColunaTipoRepository.listaTipoLocalVistoria();
	}

	public List<ConteudoColunaTipo> listaEstadoCivil() {
		return conteudoColunaTipoRepository.listaEstadoCivil();
	}

	public List<ConteudoColunaTipo> listaTipoDeCondutor() {
		return conteudoColunaTipoRepository.listaTipoDeCondutor();
	}

	public List<ConteudoColunaTipo> listaTipoCombustivel() {
		return conteudoColunaTipoRepository.listaTipoCombustivel();
	}
	
	public List<ConteudoColunaTipoDTO> listarMotivosCancelamentosPorTipoVistoria(TipoVistoria tipoVistoria) {
		
		List<ConteudoColunaTipoDTO> motivos = conteudoColunaTipoRepository.listaConteudoPorTiposData(new Date(),
				CANCELAMENTO_A_CONFIRMAR.getValor().toString(),
				CANCELAMENTO_CONFIRMADO.getValor().toString(),
				CANCELAMENTO_DE_NAG.getValor().toString(),
				CANCELAMENTO_FORA_SISTEMA_CONFIRMADO.getValor().toString(),
				CANCELAMENTO_VISTORIADORA.getValor().toString(),
				CANCELAMENTO_TELA_CONSULTA_AGTO.getValor().toString());
		
		List<ConteudoColunaTipoDTO> motivoPorTipo = motivos.stream()
				.filter(m -> tipoVistoria.getCodMotivo().equals(m.getDsAbvdaColunTipo()))
				.collect(Collectors.toList());
		
		return motivoPorTipo.isEmpty() ? motivos : motivoPorTipo;
	}

	public boolean existsConteudo(String codMotvSitucAgmto) {
		return conteudoColunaTipoRepository.existsConteudoPorDataCodMotiv(new Date(), codMotvSitucAgmto);
	}
	
	public Optional<ConteudoColunaTipoDTO> obterMotivoCancelamentoPorCodMotivo(Long codMotivo) {
		return obterMotivoCancelamentoPorCodMotivo(codMotivo, new Date());
	}

	public Optional<ConteudoColunaTipoDTO> obterMotivoCancelamentoPorCodMotivo(Long codMotivo, Date data) {
		return conteudoColunaTipoRepository.findConteudoPorDataCodMotiv(data, codMotivo.toString()).stream().findFirst();
	}
}