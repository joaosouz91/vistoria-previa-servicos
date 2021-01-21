package br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation.JsonStringToBoolean;

public class StringToBooleanSerializer extends StdSerializer<String> implements ContextualSerializer {

	private static final long serialVersionUID = 4489440552337342507L;

	private String strTrue = null;

	public StringToBooleanSerializer() {
		super(String.class);
	}

	public StringToBooleanSerializer(String strTrue) {
		this();
		this.strTrue = strTrue;
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
			throws JsonMappingException {
		JsonStringToBoolean annotation = property.getAnnotation(JsonStringToBoolean.class);
		return new StringToBooleanSerializer(annotation.strTrue());
	}

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeBoolean(value.equals(strTrue) ? Boolean.TRUE : Boolean.FALSE);
	}

}
