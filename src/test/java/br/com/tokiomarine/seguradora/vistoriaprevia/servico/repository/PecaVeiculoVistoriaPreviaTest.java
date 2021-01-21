package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;



@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
public class PecaVeiculoVistoriaPreviaTest {

	static {
        System.setProperty("tokiomarine.infra.AMBIENTE", "LOCAL");
    }
	
	@Test
	public void save() {
	
	}

}
