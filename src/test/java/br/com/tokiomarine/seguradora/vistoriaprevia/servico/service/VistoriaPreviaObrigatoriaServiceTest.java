package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.StatusAgendamentoAgrupamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaFiltro;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.SituacaoAgendamento;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.StatusAgendamentoAgrupamentoRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
public class VistoriaPreviaObrigatoriaServiceTest {

	@Autowired
	private VistoriaPreviaObrigatoriaService service;

	@Autowired
	private StatusAgendamentoAgrupamentoRepository statusAgendamentoAgrupamentoRepository;

	static {
        System.setProperty("tokiomarine.infra.AMBIENTE", "LOCAL");
    }
	
//	@Test
	public void recuperarListaItemAgendarTest() {
		VistoriaFiltro filtro = new VistoriaFiltro();
		
		LocalDateTime localDate = LocalDateTime.of(2019, 6, 26, 0, 0);
		filtro.setDataDe(Timestamp.valueOf(localDate.minusDays(30l)));
		filtro.setDataAte(Timestamp.valueOf(localDate));
		
		service.recuperarListaItemAgendar(filtro, PageRequest.of(0, 10));
	}

//	@Test
	public void recuperarListaItemAgendadaTest() {
		VistoriaFiltro filtro = new VistoriaFiltro();
		
		LocalDateTime localDate = LocalDateTime.of(2019, 6, 26, 0, 0);
		filtro.setDataDe(Timestamp.valueOf(localDate.minusDays(30l)));
		filtro.setDataAte(Timestamp.valueOf(localDate));
		
		service.recuperarListaItemAgendada(filtro, PageRequest.of(0, 10));
	}

//	@Test
	public void recuperarListaItemTest() {
		VistoriaFiltro filtro = new VistoriaFiltro();
		
		LocalDateTime localDate = LocalDateTime.of(2019, 6, 26, 0, 0);
		filtro.setDataDe(Timestamp.valueOf(localDate.minusDays(30l)));
		filtro.setDataAte(Timestamp.valueOf(localDate));
		
		service.recuperarListaItem(filtro, PageRequest.of(1881, 10));
	}
	
//	@Test
	public void novoStatus() {
		StatusAgendamentoAgrupamento statusAgto = new StatusAgendamentoAgrupamento();
		
		statusAgto.setCdSitucAgmto(SituacaoAgendamento.AGD.getValor());
		statusAgto.setCdVouch("TSB22085327");
		statusAgto.setCdUsuroUltmaAlter("PROC_VIS");
		statusAgto.setDtUltmaAlter(new Date());
		statusAgendamentoAgrupamentoRepository.save(statusAgto);
		
		statusAgto = new StatusAgendamentoAgrupamento();
		statusAgto.setCdSitucAgmto(SituacaoAgendamento.AGD.getValor());
		statusAgto.setCdVouch("TSD22085300");
		statusAgto.setCdUsuroUltmaAlter("PROC_VIS");
		statusAgto.setDtUltmaAlter(new Date());
		statusAgendamentoAgrupamentoRepository.save(statusAgto);
		
		statusAgto = new StatusAgendamentoAgrupamento();
		statusAgto.setCdSitucAgmto(SituacaoAgendamento.CAN.getValor());
		statusAgto.setCdVouch("TSD22085335");
		statusAgto.setCdUsuroUltmaAlter("PROC_VIS");
		statusAgto.setDtUltmaAlter(new Date());
		statusAgendamentoAgrupamentoRepository.save(statusAgto);
		
		statusAgto = new StatusAgendamentoAgrupamento();
		statusAgto.setCdSitucAgmto(SituacaoAgendamento.CAN.getValor());
		statusAgto.setCdVouch("TSM22085319");
		statusAgto.setCdUsuroUltmaAlter("PROC_VIS");
		statusAgto.setDtUltmaAlter(new Date());
		statusAgendamentoAgrupamentoRepository.save(statusAgto);
		
		statusAgto = new StatusAgendamentoAgrupamento();
		statusAgto.setCdSitucAgmto(SituacaoAgendamento.VFR.getValor());
		statusAgto.setCdVouch("TSS22085297");
		statusAgto.setCdUsuroUltmaAlter("PROC_VIS");
		statusAgto.setDtUltmaAlter(new Date());
		statusAgendamentoAgrupamentoRepository.save(statusAgto);
		
		statusAgto = new StatusAgendamentoAgrupamento();
		statusAgto.setCdSitucAgmto(SituacaoAgendamento.VFR.getValor());
		statusAgto.setCdVouch("TSY22085343");
		statusAgto.setCdUsuroUltmaAlter("PROC_VIS");
		statusAgto.setDtUltmaAlter(new Date());
		statusAgendamentoAgrupamentoRepository.save(statusAgto);
	}
	
//	@Test
	public void email1contato() throws Exception {
		service.emailContato1();
	}
}
