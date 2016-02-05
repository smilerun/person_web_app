package com.bnaqica.person.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import static java.text.MessageFormat.format;

public class ObjectMapperProvider {
	private static ObjectMapperProvider instance = new ObjectMapperProvider();

    private ObjectMapperProvider() {}

    public static ObjectMapper getObjectMapper() {
        return instance.buildObjectMapper();
    }

    private ObjectMapper buildObjectMapper() {
        SimpleModule module = new SimpleModule();

        module.addDeserializer(LocalDate.class, newDeserializer(
            LocalDate::parse,
            "Error while parsing date at \"{0}\", expected format \"YYYY-MM-dd\" got \"{1}\""
        ));

        module.addDeserializer(LocalDateTime.class, newDeserializer(
            text -> LocalDateTime.parse(text.length() > 19? text.substring(0,19) : text),
            "Error while parsing date time at \"{0}\", expected format \"YYYY-MM-dd''T''HH:mm:ss''Z''\" got \"{1}\""
        ));

        module.addSerializer(LocalDateTime.class, newSerializer(
            (jGen, v) -> jGen.writeString(DateTimeFormatter.ISO_INSTANT.format(v.atZone(ZoneId.of("UTC")))),
            "Error serializing local date time with value: \"{0}\""
        ));

        module.addSerializer(LocalDate.class, newSerializer(
            (jGen, v) -> jGen.writeString(v.toString()),
            "Error serializing local date with value: \"{0}\""
        ));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        objectMapper.getSerializationConfig().without(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return objectMapper;
    }

    private <T> JsonDeserializer<T> newDeserializer(ParsingFunction<T> parsingFunction, String messageFormat) {
        return new JsonDeserializer<T>() {
            @Override public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
                try {
                    return parsingFunction.parse(jp.getValueAsString());
                } catch (Exception e) {
                    throw new IOException(format(messageFormat, jp.getCurrentName(), jp.getValueAsString()));
                }
            }
        };
    }

    private <T> JsonSerializer<T> newSerializer(SerializingFunction<T> serializingFunction, String messageFormat) {
        return new JsonSerializer<T>() {
            @Override public void serialize(T value, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                try {
                    if (value == null) {
                        jGen.writeNull();
                    }
                    serializingFunction.serialize(jGen, value);
                } catch (Exception e) {
                    throw new IOException(format(messageFormat, value));
                }
            }
        };
    }

    private interface ParsingFunction<T> {
        T parse(String value) throws Exception;
    }

    private interface SerializingFunction<T> {
        void serialize(JsonGenerator jsonGenerator, T value) throws Exception;
    }

}
