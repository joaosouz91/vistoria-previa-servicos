package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import java.time.YearMonth;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;



@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
public class LoteLaudoImprodutivoRepositoryTest {

	static {
        System.setProperty("tokiomarine.infra.AMBIENTE", "LOCAL");
    }
	
	@Autowired
	private LoteLaudoImprodutivoRepository loteLaudoImprodutivoRepository;
	
//	@Test
	public void transmitirLotesTest() {
		loteLaudoImprodutivoRepository.transmitirLotes(YearMonth.of(2018, 7));
	}

}
