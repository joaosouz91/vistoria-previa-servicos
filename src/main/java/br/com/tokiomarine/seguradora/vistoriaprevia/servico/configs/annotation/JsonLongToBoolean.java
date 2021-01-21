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

import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.serialization.BooleanToLongSerializer;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.configs.serialization.LongToBooleanSerializer;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonInclude(Include.NON_NULL)
@JsonSerialize(using = LongToBooleanSerializer.class)
@JsonDeserialize(using = BooleanToLongSerializer.class)
public @interface JsonLongToBoolean {

}
