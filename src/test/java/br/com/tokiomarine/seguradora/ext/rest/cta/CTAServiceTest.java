package br.com.tokiomarine.seguradora.ext.rest.cta;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.VistoriaPreviaServicosApplication;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VistoriaPreviaServicosApplication.class)
@TestPropertySource(locations="classpath:test.properties")
public class CTAServiceTest {

	@Autowired
	private CTAService ctaService;
	
	static {
        System.setProperty("tokiomarine.infra.AMBIENTE", "LOCAL");
    }
	
//	@Test
	public void recuperarListaItemAgendarTest() {
		VistoriaFiltro filtro = new VistoriaFiltro();

		filtro.setCorretor(40l);
		filtro.setChassi("9BG148LP0EC404835");
		filtro.setPlaca("JKN4810");
		
		ctaService.pesquisaCotacaoNoCTA(filtro);
	}
}
