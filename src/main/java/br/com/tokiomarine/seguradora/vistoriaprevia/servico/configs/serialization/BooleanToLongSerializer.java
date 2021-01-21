package br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class BooleanToLongSerializer extends StdDeserializer<Long> {

	private static final long serialVersionUID = -4603391564300685840L;
	private Long longOne = 1L;
	private Long longZero = 0L;

	public BooleanToLongSerializer() {
		this(null);
	}

	protected BooleanToLongSerializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		return p.getBooleanValue() ? longOne : longZero;
	}

}
