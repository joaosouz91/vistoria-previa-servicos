package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.LaudoVistoriaPrevia;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
public class LaudoServiceTest {

	@Autowired
	private LaudoService service;

	static {
        System.setProperty("tokiomarine.infra.AMBIENTE", "LOCAL");
    }
	
//	@Test
	public void isLaudoComParecerLiberadoTest() throws Exception {
		LaudoVistoriaPrevia laudo = new LaudoVistoriaPrevia();
		
		laudo.setCdLvpre("0000000");
		
		service.isLaudoComParecerLiberado(laudo);
	}
}
