package br.com.tokiomarine.seguradora.vistoriaprevia.servico.fila;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
public class VinculoLaudoProducerTest {

	@Autowired
	private VinculoLaudoProducer producer;
	
//	@Test
	public void sendVinculadoTest() {		
		producer.sendVinculado("030172");
	}
}