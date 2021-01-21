package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.VistoriaPreviaObrigatoria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.AgendamentoController;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.controller.VistoriaPreviaObrigatoriaController;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.VistoriaObrigatoriaDTO;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.exceptions.InternalServerException;

@Component
public class VistoriaPreviaObrigatoriaResourceUtil extends ResourceUtil<VistoriaPreviaObrigatoria, VistoriaObrigatoriaDTO> {

	@Override
	public Link linkTo(VistoriaObrigatoriaDTO dto) {
		return ControllerLinkBuilder
				.linkTo(methodOn(VistoriaPreviaObrigatoriaController.class).buscarVistoria(dto.getIdVspreObgta()))
				.withSelfRel();
	}
	
	public void linkToVisualizar(final VistoriaObrigatoriaDTO dto) {
		Link link = ControllerLinkBuilder
				.linkTo(methodOn(AgendamentoController.class).visualizar(dto.getIdVspreObgta(), dto.getAgendamento().getCdVouch()))
				.withSelfRel()
				.withTitle("visualizar")
				.withType(GET.name());
		dto.add(link);
	}

	public void linkToAgendar(final VistoriaObrigatoriaDTO dto) {
		try {
			Link link = ControllerLinkBuilder
					.linkTo(methodOn(AgendamentoController.class).agendar(null))
					.withSelfRel()
					.withTitle("agendar")
					.withType(POST.name());
			dto.add(link);
		} catch (BindException e) {
			throw new InternalServerException(e);
		}
	}

	public void linkToCancelar(final VistoriaObrigatoriaDTO dto) {
		try {
			Link link = ControllerLinkBuilder
					.linkTo(methodOn(AgendamentoController.class).cancelar(dto.getAgendamento().getCdVouch(), dto.getAgendamento().getStatus(), null))
					.withSelfRel()
					.withTitle("cancelar")
					.withType(DELETE.name());
			dto.add(link);
		} catch (BindException e) {
			throw new InternalServerException(e);
		}
	}
}