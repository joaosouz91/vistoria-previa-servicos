package br.com.tokiomarine.seguradora.vistoriaprevia.servico.fila;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.StatusVistoriaPrevia;

@Component
public class VinculoLaudoProducer {

	private static final Logger LOGGER = LogManager.getLogger(VinculoLaudoProducer.class);

	@Autowired
	private JmsTemplate jmsTemplateProdutor;
	
	@Value("${fila.vinculado}")
	private String vinculado;
	
	@Value("${fila.erro}")
	private String erro;
					
	public void sendVinculado(final String codigoLaudo) {		
		final String mensagem = "Laudo em processo de vinculo!";
		sendWithHeaders(vinculado,codigoLaudo, StatusVistoriaPrevia.PENDENTE.getValor(), mensagem);	
	}
	
	public void sendErro(final String codigoLaudo, final Exception stackTrace) {		
		final String mensagem = stackTrace.getMessage();
		sendWithHeaders(erro, codigoLaudo, StatusVistoriaPrevia.BACKOUT.getValor(), mensagem);
	}
			
	private void sendWithHeaders(final String destination, final String codigoLaudo, final String status, final String corpoMensagem) {
		LOGGER.info("sending message='{}' to destination='{}'", codigoLaudo, destination);

		jmsTemplateProdutor.send(destination,new MessageCreator() {
            
			@Override
			public Message createMessage(javax.jms.Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(corpoMensagem);
				textMessage.setJMSCorrelationID(codigoLaudo == null || codigoLaudo.isEmpty() ? "0" : codigoLaudo);
				textMessage.setStringProperty("DESTINO", destination);
				textMessage.setStringProperty("STATUS", status);
				textMessage.setStringProperty("CODIGO_LAUDO", codigoLaudo == null || codigoLaudo.isEmpty() ? "0" : codigoLaudo);
				return textMessage;
			}
			
		}); 		
	}		
}