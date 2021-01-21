package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
public class ReclassificacaoServiceTest {

	@Autowired
	private ReclassificacaoService service;

	static {
		System.setProperty("tokiomarine.infra.AMBIENTE", "LOCAL");
	}

//	@Test
	public void recuperarListaItemAgendarTest() {
//		service.executarAjuste(16111443L, 42913050L);
	}
}
