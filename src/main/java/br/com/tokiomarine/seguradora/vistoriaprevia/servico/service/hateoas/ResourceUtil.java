package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service.hateoas;

import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

public abstract class ResourceUtil<E, D extends ResourceSupport> {

	private final Class<E> typeParameterENTITYClass;
	private final Class<D> typeParameterDTOClass;

	@Autowired
	private ModelMapper mapper;

	@SuppressWarnings("unchecked")
	public ResourceUtil() {
		typeParameterENTITYClass = ((Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0]);

		typeParameterDTOClass = ((Class<D>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[1]);
	}

	public abstract Link linkTo(D dto);

	public D addResourceToEntity(E p) {
		D dto = mapper.map(p, typeParameterDTOClass);
		addResourceToDto(dto);
		return dto;
	}

	public void addResourceToDto(final D d) {
		d.add(linkTo(d));
	}

	public Optional<E> toEntity(D d) {
		return Optional.of(mapper.map(d, typeParameterENTITYClass));
	}

	public Optional<D> toDto(E e) {
		return Optional.of(mapper.map(e, typeParameterDTOClass));
	}

	public Page<D> pageToResource(Page<? extends E> obj) {
		return pageToResource(obj, null);
	}

	public List<D> pageToResource(List<? extends E> obj) {
		return pageToResource(obj, null);
	}

	public List<D> pageToResource(Stream<? extends E> stream, Function<D, D> consumer) {
		return stream.map(e -> {
				D dto = addResourceToEntity(e);
				
				if (consumer != null) {
					dto = consumer.apply(dto);
				}
				
				return dto;
			})
			.collect(Collectors.toList());
	}

	public List<D> pageToResource(List<? extends E> obj, Function<D, D> consumer) {
		return pageToResource(obj.stream(), consumer);
	}

	public Page<D> pageToResource(Page<? extends E> obj, Function<D, D> consumer) {
		List<D> dtos = pageToResource(obj.stream(), consumer);
		
		return new PageImpl<>(dtos, obj.getPageable(), obj.getTotalElements());
	}

	public ResponseEntity<D> created(E obj) throws URISyntaxException {
		D dto = addResourceToEntity(obj);

		return ResponseEntity.created(new URI(dto.getId().getHref())).body(dto);
	}

	public ResponseEntity<D> ok(E obj) {
		return ResponseEntity.ok(addResourceToEntity(obj));
	}

	public ResponseEntity<Page<D>> ok(Page<E> obj) {
		return ResponseEntity.ok(pageToResource(obj));
	}

	public ResponseEntity<List<D>> ok(List<E> obj) {
		return ResponseEntity.ok(pageToResource(obj));
	}
}
