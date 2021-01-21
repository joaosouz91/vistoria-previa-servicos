package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
public class PreAgendamentoServiceTest {

	@Autowired
	private PreAgendamentoService service;

	@Autowired
	private LaudoService laudoService;

	static {
		System.setProperty("tokiomarine.infra.AMBIENTE", "LOCAL");
	}

//	@Test
	public void recuperarListaItemAgendarTest() {
//		Método isLaudoComParecerLiberado é privado, alterar para public quando testar
//		try {
//			LaudoVistoriaPrevia laudo = laudoService.obterLaudoPorCodigoVistoria("T25460463");
//			boolean liberado = service.isLaudoComParecerLiberado(laudo);
//			System.out.println("LAUDO = " + liberado);
//		} catch (Exception e) {
//			ExceptionUtils.getRootCause(e).getMessage();
//		}
	}
	
//	@Test
	public void pesquisaPropostaAgendamentoSSVTest() {
		//		Método pesquisaPropostaAgendamentoSSV é privado, alterar para public quando testar
//		VistoriaFiltro filtro = new VistoriaFiltro();
//		filtro.setChassi("9BGRZ0810AG200138");
//		filtro.setPlaca("EIS4220");
//		
//		service.pesquisaPropostaAgendamentoSSV(filtro);
	}
}
