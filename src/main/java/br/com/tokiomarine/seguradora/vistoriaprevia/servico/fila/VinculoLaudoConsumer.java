package br.com.tokiomarine.seguradora.vistoriaprevia.servico.fila;

//@Component
public class VinculoLaudoConsumer {
/*
	private static final Logger LOGGER = LoggerFactory.getLogger(VinculoLaudoConsumer.class);

	@Autowired
	private ProcessaVinculoIndividualService processaVinculoIndividualService;
		
	@Autowired
	private VinculoLaudoProducer vinculoLaudoProducer;

	private CountDownLatch latch = new CountDownLatch(1);
	
	public CountDownLatch getLatch() {
			return latch;
	}	
	
	@JmsListeners({
					@JmsListener(containerFactory = "defaultlistenerContainerFactoryConsumidor1",destination = "${fila.vinculado}"),
					@JmsListener(containerFactory = "defaultlistenerContainerFactoryConsumidor2",destination = "${fila.vinculado}"),
					@JmsListener(containerFactory = "defaultlistenerContainerFactoryConsumidor3",destination = "${fila.vinculado}")})
	public void receiveVinculado(final TextMessage mensagem) {
		String codigoLaudo = "";
		try {			
			codigoLaudo = mensagem.getStringProperty("CODIGO_LAUDO");			
			processaVinculoIndividualService.vincularLaudo(codigoLaudo);
			
			latch.countDown();
			
		} catch (Exception exception) {
			vinculoLaudoProducer.sendErro(codigoLaudo, exception);
			exception.printStackTrace();
		} 
	}	*/
}