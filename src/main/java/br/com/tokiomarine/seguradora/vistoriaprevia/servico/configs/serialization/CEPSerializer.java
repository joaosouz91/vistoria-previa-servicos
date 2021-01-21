package br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.serialization;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CEPSerializer extends StdSerializer<Object> implements ContextualSerializer {

	private static final long serialVersionUID = -2076057048047009181L;

	public CEPSerializer() {
		super(Object.class);
	}

	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
			throws JsonMappingException {
		return new CEPSerializer();
	}

	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		if (value == null) {
			gen.writeNull();
		}
		
		gen.writeString(StringUtils.leftPad(value != null ? value.toString() : "", 8, "0"));
	}

}
