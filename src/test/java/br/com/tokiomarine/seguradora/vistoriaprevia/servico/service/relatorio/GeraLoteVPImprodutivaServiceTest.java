package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.relatorio;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
public class GeraLoteVPImprodutivaServiceTest {

	@Autowired
	private GeraLoteVPImprodutivaService geraLoteVPImprodutivaService;

//	@Test
	public void geraLoteTest() throws Exception {
		geraLoteVPImprodutivaService.gerarLote();
	}
}
