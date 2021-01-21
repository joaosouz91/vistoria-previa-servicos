package br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonStringToBoolean;

public class BooleanToStringSerializer extends StdDeserializer<String> implements ContextualDeserializer {

	private static final long serialVersionUID = -4603391564300685840L;

	private String strTrue;
	private String strFalse;

	public BooleanToStringSerializer() {
		super(String.class);
	}

	public BooleanToStringSerializer(String strTrue, String strFalse) {
		this();
		this.strTrue = strTrue;
		this.strFalse = strFalse;
	}

	@Override
	public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
			throws JsonMappingException {
		JsonStringToBoolean annotation = property.getAnnotation(JsonStringToBoolean.class);
		return new BooleanToStringSerializer(annotation.strTrue(), annotation.strFalse());
	}

	@Override
	public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		return p.getBooleanValue() ? strTrue : strFalse;
	}

}
