package br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.naoautenticado;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.AgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.DataAgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PostoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.PrestadoraDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ResponseAgendamentoDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.AgendamentoService;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.PostoVistoriaPreviaService;

@RestController
@RequestMapping("/api/servicos/agendamento")
public class AgendamentoServicosApiController {

	@Autowired
	private AgendamentoService agendamentoService;

	@Autowired
	private PostoVistoriaPreviaService postoService;

	@CrossOrigin
	@PostMapping("/cotacao/{nrCotacao}/santander")
	public ResponseEntity<?> gerarAgendamentoSantander(@PathVariable String nrCotacao,
			@Valid @RequestBody AgendamentoDTO dto) throws BindException {
		return ResponseEntity.ok(agendamentoService.gerarAgendamentoSantander(nrCotacao, dto));
	}

	@GetMapping("/cotacao/{nrCotacao}/datas/santander")
	public ResponseEntity<?> getDatasDisponiveisSantander(@PathVariable String nrCotacao) throws BindException {
		return ResponseEntity.ok(agendamentoService.obterDatasAgendamentoSantander(nrCotacao));
	}

	@GetMapping("/cotacao/{nrCotacao}/postos/santander")
	public ResponseEntity<?> getPostosSantander(@PathVariable String nrCotacao,
			@RequestParam(name = "uf", required = true) String uf,
			@RequestParam(name = "cidade", required = false) String cidade) {
		return ResponseEntity.ok(agendamentoService.obterPostosSantander(nrCotacao, uf, cidade));
	}

	@CrossOrigin
	@PostMapping("/calculo/{nrCalculo}/origem/{cdSistmOrigm}")
	public ResponseEntity<Object> agendamentoVP(@PathVariable Long nrCalculo, @PathVariable Long cdSistmOrigm,
			@Valid @RequestBody AgendamentoDTO dto) throws BindException {
		List<ResponseAgendamentoDTO> voucher = agendamentoService.createAgendamentoWS(nrCalculo, cdSistmOrigm, dto);
		return ResponseEntity.ok(voucher);
	}

	@GetMapping("/calculo/{calculo}/postos/ufs")
	public ResponseEntity<List<String>> listaUfs(@PathVariable Long calculo,
			@RequestParam(name = "isCaminhao", required = false) boolean isCaminhao,
			@RequestParam(name = "isAtivo", required = false, defaultValue = "true") boolean isAtivo) {

		agendamentoService.obterPreAgendamentoPorCalculo(calculo);

		return ResponseEntity.ok(postoService.obterUfs(isCaminhao, isAtivo));
	}

	@GetMapping("/calculo/{calculo}/postos/{uf}/cidades")
	public ResponseEntity<List<String>> listaCidadesPorEstado(@PathVariable Long calculo, @PathVariable String uf,
			@RequestParam(name = "isCaminhao", required = false) boolean isCaminhao,
			@RequestParam(name = "isAtivo", required = false, defaultValue = "true") boolean isAtivo) {

		agendamentoService.obterPreAgendamentoPorCalculo(calculo);

		return ResponseEntity.ok(postoService.obterCidadesPorEstado(uf, isCaminhao, isAtivo));
	}

	@GetMapping("/calculo/{calculo}/postos/{uf}/{cidade}/bairros")
	public ResponseEntity<List<String>> listaBairrosPorCidade(@PathVariable Long calculo, @PathVariable String uf,
			@PathVariable String cidade, @RequestParam(name = "isCaminhao", required = false) boolean isCaminhao,
			@RequestParam(name = "isAtivo", required = false, defaultValue = "true") boolean isAtivo) {

		agendamentoService.obterPreAgendamentoPorCalculo(calculo);

		return ResponseEntity.ok(postoService.recuperarListaBairroPorCidade(uf, cidade, isCaminhao, isAtivo));
	}

	@GetMapping("/calculo/{calculo}/postos/{uf}/{cidade}")
	public ResponseEntity<List<PostoDTO>> getPostosDisponiveis(@PathVariable Long calculo, @PathVariable String uf,
			@PathVariable String cidade, @RequestParam(name = "bairro", required = false) String bairro) {

		agendamentoService.obterPreAgendamentoPorCalculo(calculo);

		return ResponseEntity.ok(postoService.obterPostos(uf, cidade, bairro));
	}

	@GetMapping("/calculo/{calculo}/datas/cep-vistoria/{cepVistoria}")
	public ResponseEntity<?> getDatasDisponiveis(@PathVariable Long calculo, @PathVariable String cepVistoria) {

		agendamentoService.obterPreAgendamentoPorCalculo(calculo);

		List<DataAgendamentoDTO> datas = agendamentoService.obterDatasAgendamento();
		List<PrestadoraDTO> prestadoras = agendamentoService.obterPrestadorasDistribuicao(calculo, cepVistoria);

		Map<String, Object> map = new HashMap<>();
		map.put("datas", datas);
		map.put("prestadoraPrioritaria", prestadoras.stream().findFirst().orElseGet(null));
		map.put("prestadorasOpcionais", prestadoras.stream().skip(1l).collect(Collectors.toList()));

		return ResponseEntity.ok(map);
	}
}