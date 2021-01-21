package br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.tokiomarine.seguradora.aceitacao.crud.model.AgrupamentoCaracteristicaItemSegurado;
import br.com.tokiomarine.seguradora.aceitacao.crud.model.ParametrosAceitacao;
import br.com.tokiomarine.seguradora.aceitacao.crud.model.Proposta;
import br.com.tokiomarine.seguradora.aceitacao.crud.model.Restricao;
import br.com.tokiomarine.seguradora.ssv.transacional.model.AnexoProposta;
import br.com.tokiomarine.seguradora.ssv.transacional.model.Crivo;
import br.com.tokiomarine.seguradora.ssv.transacional.model.MotivoLiberacao;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;

@Configuration
@EnableTransactionManagement
public class CustomEntityScan {
	
	@Autowired
	private JpaProperties jpaProperties;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
		try {
			LocalContainerEntityManagerFactoryBean local = new LocalContainerEntityManagerFactoryBean();
			local.setDataSource(dataSource);
			local.setPackagesToScan(packageNames());

			local.setPersistenceUnitPostProcessors((MutablePersistenceUnitInfo persistenceUnit) -> {
				addEntity(persistenceUnit, Proposta.class);
				addEntity(persistenceUnit, Restricao.class);
				addEntity(persistenceUnit, ParametrosAceitacao.class);
				addEntity(persistenceUnit, AgrupamentoCaracteristicaItemSegurado.class);
				removeEntity(persistenceUnit, AnexoProposta.class);
				removeEntity(persistenceUnit, Crivo.class);
				removeEntity(persistenceUnit, MotivoLiberacao.class);
			});

			local = configureProvider(local);
			
			return local;
		} catch (Exception ex) {
			throw new InternalServerException(ex);
		}
	}

	private LocalContainerEntityManagerFactoryBean configureProvider(LocalContainerEntityManagerFactoryBean emf) {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		emf.setJpaVendorAdapter(vendorAdapter);

		emf.setJpaPropertyMap(jpaProperties.getProperties());
		emf.afterPropertiesSet();

		return emf;
	}

	private void addEntity(MutablePersistenceUnitInfo mutable, Class<?> clazz) {
		mutable.getManagedClassNames().add(clazz.getName());
	}

	private void removeEntity(MutablePersistenceUnitInfo mutable, Class<?> clazz) {
		mutable.getManagedClassNames().remove(clazz.getName());
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	public String[] packageNames() {
		return new String[] { 
				"br.com.tokiomarine.seguradora.vistoriaprevia.servico.entities",
				"br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto",
				"br.com.tokiomarine.seguradora.ext.ssv.entity",
				"br.com.tokiomarine.seguradora.aceitacao.crud.model",
				"br.com.tokiomarine.seguradora.ssv.corporativa.model",
				"br.com.tokiomarine.seguradora.ssv.vp.crud.model", 
				"br.com.tokiomarine.seguradora.ssv.cadprodvar.model",
				"br.com.tokiomarine.seguradora.ssv.transacional.model",
				"br.com.tokiomarine.seguradora.ssv.controle.alcada.model"
			};
	}
}
