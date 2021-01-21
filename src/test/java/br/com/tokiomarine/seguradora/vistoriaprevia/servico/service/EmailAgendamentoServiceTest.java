package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
public class EmailAgendamentoServiceTest {

	@Autowired
	private AgendamentoService agendamentoService;

	@Autowired
	private EmailService service;

	static {
        System.setProperty("tokiomarine.infra.AMBIENTE", "LOCAL");
    }
	
//	@Test
	public void validarNovoAgendamentoTest() throws Exception {
		agendamentoService.validarNovoAgendamento(2726254L);
	}
	
//	@Test
	public void enviarEmailVistoriaRecusadaTest() throws Exception {
		service.enviarEmailVistoriaRecusada("TSD25464159", "TESTE");
	}
}
