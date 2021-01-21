package br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.destination.DestinationResolver;

@Configuration
@EnableJms
public class FilaSpringConfig {

	private static Logger logger = LogManager.getLogger(FilaSpringConfig.class);

	@Value("${activemq.broker.url.producer}")
	private String activemqBrokerUrlProducer;

//	@Value("${activemq.broker.url_1}")
//	private String activemqBrokerUrl1;

//	@Value("${activemq.broker.url_2}")
//	private String activemqBrokerUrl2;

//	@Value("${activemq.broker.url_3}")
//	private String activemqBrokerUrl3;

	@Value("${activemq.broker.user}")
	private String activemqBrokerUser;

	@Value("${activemq.broker.password}")
	private String activemqBrokerPassword;

	@Bean(name = "jmsTemplateProdutor")
	public JmsTemplate jmsTemplateProdutor() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(cachingConnectionFactoryProdutor());
		return jmsTemplate;
	}

//	@Bean(name = "jmsTemplateConsumidor1")
//	public JmsTemplate jmsTemplateConsumidor1() {
//		JmsTemplate jmsTemplate = new JmsTemplate();
//		jmsTemplate.setConnectionFactory(cachingConnectionFactoryConsumidor1());
//		return jmsTemplate;
//	}
//
//	@Bean(name = "jmsTemplateConsumidor2")
//	public JmsTemplate jmsTemplateConsumidor2() {
//		JmsTemplate jmsTemplate = new JmsTemplate();
//		jmsTemplate.setConnectionFactory(cachingConnectionFactoryConsumidor2());
//		return jmsTemplate;
//	}
//
//	@Bean(name = "jmsTemplateConsumidor3")
//	public JmsTemplate jmsTemplateConsumidor3() {
//		JmsTemplate jmsTemplate = new JmsTemplate();
//		jmsTemplate.setConnectionFactory(cachingConnectionFactoryConsumidor3());
//		return jmsTemplate;
//	}

	public ActiveMQConnectionFactory activeMQConnectionFactoryProdutor() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(activemqBrokerUrlProducer);
		activeMQConnectionFactory.setUserName(activemqBrokerUser);
		activeMQConnectionFactory.setPassword(activemqBrokerPassword);
		return activeMQConnectionFactory;
	}

//	public ActiveMQConnectionFactory activeMQConnectionFactoryConsumidor1() {
//		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
//		activeMQConnectionFactory.setBrokerURL(activemqBrokerUrl1);
//		activeMQConnectionFactory.setUserName(activemqBrokerUser);
//		activeMQConnectionFactory.setPassword(activemqBrokerPassword);
//		return activeMQConnectionFactory;
//	}
//
//	public ActiveMQConnectionFactory activeMQConnectionFactoryConsumidor2() {
//		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
//		activeMQConnectionFactory.setBrokerURL(activemqBrokerUrl2);
//		activeMQConnectionFactory.setUserName(activemqBrokerUser);
//		activeMQConnectionFactory.setPassword(activemqBrokerPassword);
//		return activeMQConnectionFactory;
//	}
//
//	public ActiveMQConnectionFactory activeMQConnectionFactoryConsumidor3() {
//		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
//		activeMQConnectionFactory.setBrokerURL(activemqBrokerUrl3);
//		activeMQConnectionFactory.setUserName(activemqBrokerUser);
//		activeMQConnectionFactory.setPassword(activemqBrokerPassword);
//		return activeMQConnectionFactory;
//	}

	public CachingConnectionFactory cachingConnectionFactoryProdutor() {
		return new CachingConnectionFactory(activeMQConnectionFactoryProdutor());
	}

//	public CachingConnectionFactory cachingConnectionFactoryConsumidor1() {
//		return new CachingConnectionFactory(activeMQConnectionFactoryConsumidor1());
//	}
//
//	@Bean
//	public CachingConnectionFactory cachingConnectionFactoryConsumidor2() {
//		return new CachingConnectionFactory(activeMQConnectionFactoryConsumidor2());
//	}
//
//	public CachingConnectionFactory cachingConnectionFactoryConsumidor3() {
//		return new CachingConnectionFactory(activeMQConnectionFactoryConsumidor3());
//	}

	@Bean(name = "defaultlistenerContainerFactoryProdutor")
	public JmsListenerContainerFactory<DefaultMessageListenerContainer> defaultlistenerContainerFactoryProdutor() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		return factory;
	}

//	@Bean(name = "defaultlistenerContainerFactoryConsumidor1")
//	public JmsListenerContainerFactory<DefaultMessageListenerContainer> defaultlistenerContainerFactoryConsumidor1() {
//		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//		factory.setConnectionFactory(cachingConnectionFactoryConsumidor1());
//		factory.setDestinationResolver(envPropertyDestinationResolver());
//		factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
//		return factory;
//	}
//
//	@Bean(name = "defaultlistenerContainerFactoryConsumidor2")
//	public JmsListenerContainerFactory<DefaultMessageListenerContainer> defaultlistenerContainerFactoryConsumidor2() {
//		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//		factory.setConnectionFactory(cachingConnectionFactoryConsumidor2());
//		factory.setDestinationResolver(envPropertyDestinationResolver());
//		factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
//		return factory;
//	}
//
//	@Bean(name = "defaultlistenerContainerFactoryConsumidor3")
//	public JmsListenerContainerFactory<DefaultMessageListenerContainer> defaultlistenerContainerFactoryConsumidor3() {
//		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//		factory.setConnectionFactory(cachingConnectionFactoryConsumidor3());
//		factory.setDestinationResolver(envPropertyDestinationResolver());
//		factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
//		return factory;
//	}

	@Bean(name = "envPropertyDestinationResolver")
	public DestinationResolver envPropertyDestinationResolver() {
		return new DestinationResolver() {

			@Override
			public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain)
					throws JMSException {

				if (session == null) {
					throw new NullPointerException("FilasJMSEnum não deve ser nulo!");
				}

				if (destinationName == null || destinationName.isEmpty()) {
					throw new NullPointerException("destinationName não deve ser nulo!");
				}

				logger.info(" ################ Consumidores : " + destinationName);

				if (pubSubDomain) {
					return session.createTopic(destinationName);
				} else {
					return session.createQueue(destinationName);
				}
			}
		};
	}
}
