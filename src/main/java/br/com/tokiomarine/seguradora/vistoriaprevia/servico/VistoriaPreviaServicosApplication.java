package br.com.tokiomarine.seguradora.vistoriaprevia.servico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = JndiDataSourceAutoConfiguration.class, scanBasePackages = {
	"br.com.tokiomarine.seguradora.vistoriaprevia.servico.regra.agendamento",
	"br.com.tokiomarine.seguradora.vistoriaprevia.servico", 
	"br.com.tokiomarine.seguradora.ext.act.service",
	"br.com.tokiomarine.seguradora.ext.rest.cta",
	"br.com.tokiomarine.seguradora.ext.ssv.service",
	"br.com.tokiomarine.seguradora.aceitacao.rest.client",
	"br.com.tokiomarine.aceitacao.comuns"
})

@EnableJpaRepositories(basePackages = {
        "br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository",
		"br.com.tokiomarine.seguradora.aceitacao.rest.client.repository",
		"br.com.tokiomarine.seguradora.ext.act.repository", 
		"br.com.tokiomarine.seguradora.ext.ssv.repository"})

@EnableFeignClients
public class VistoriaPreviaServicosApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(VistoriaPreviaServicosApplication.class, args);
	}

}