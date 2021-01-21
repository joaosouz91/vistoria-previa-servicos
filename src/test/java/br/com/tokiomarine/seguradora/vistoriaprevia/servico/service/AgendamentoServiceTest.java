package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.tokiomarine.seguradora.aceitacao.rest.client.dto.ParametroRegrasVP;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
public class AgendamentoServiceTest {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AgendamentoService agendamentoService;
	
	public void emailDispensaTest() throws Exception {
		emailService.enviarEmailPrestadora("TSD25460471", "Teste Dispensa", "Teste Dispensa");
	}
	
//	@Test
	public void emailPreAgendamentoTest() throws Exception {
		ParametroRegrasVP parametros = new ParametroRegrasVP();

		parametros.setCodCorretor(40L);
		parametros.setNomeCliente("Filipe");
		parametros.setNumItem(0L);
		parametros.setCodModuloProduto(1L);
		parametros.setChassi("AAAAAAAAAAAAA");
		parametros.setPlaca("AAA0000");
		parametros.setCodNegocio(2L);
		parametros.setMotivoVPBonus("Motivo");
		parametros.setCpfCnpj("01234567890");
		
		emailService.enviarEmailCorretorAgendamento(parametros);
	}
	
//	@Test
	public void gerarMobileTest() {
//		agendamentoService.gerarAgendamentoDigital("TSY25sdfds019");
	}
}
