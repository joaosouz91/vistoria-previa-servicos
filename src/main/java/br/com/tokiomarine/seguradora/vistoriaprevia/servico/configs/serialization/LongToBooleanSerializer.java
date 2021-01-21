package br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LongToBooleanSerializer extends StdSerializer<Long> {

	private static final long serialVersionUID = 4489440552337342507L;

	public LongToBooleanSerializer() {
		this(null);
	}
	
	protected LongToBooleanSerializer(Class<Long> t) {
		super(t);
	}

	@Override
	public void serialize(Long value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeBoolean(value != 0 );
	}

}
