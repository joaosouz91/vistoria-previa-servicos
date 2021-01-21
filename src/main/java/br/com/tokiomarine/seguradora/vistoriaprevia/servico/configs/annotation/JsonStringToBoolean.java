package br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.serialization.BooleanToStringSerializer;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.serialization.StringToBooleanSerializer;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonInclude(Include.NON_NULL)
@JsonSerialize(using = StringToBooleanSerializer.class)
@JsonDeserialize(using = BooleanToStringSerializer.class)
public @interface JsonStringToBoolean {

	public String strTrue();
	public String strFalse();
}
