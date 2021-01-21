package br.com.tokiomarine.seguradora.vistoriaprevia.servico.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

@Getter
public class CreatedResourceEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private String id;

    public CreatedResourceEvent(Object source, HttpServletResponse response, String id) {
        super(source);
        this.response = response;
        this.id = id;
    }
}
