package com.votacao.sistemavotacao.infra.Utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JSON {

    public static final ObjectMapper mapper;
    public static final ObjectMapper snakeMapper;
    private static final JsonNodeFactory nodeFactory;

    static {
        JsonFactory fac = new JsonFactory();
        // to avoid the OutputStream auto-close on a 'writeValue'
        fac.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);

        mapper = new ObjectMapper(fac);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.CLOSE_CLOSEABLE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        nodeFactory = mapper.getNodeFactory();

        snakeMapper = new ObjectMapper(fac);
        snakeMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE); //Camel Case resolution
        snakeMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        snakeMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        snakeMapper.disable(SerializationFeature.CLOSE_CLOSEABLE);
        snakeMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        snakeMapper.registerModule(new JavaTimeModule());
    }

    public static JsonNode valueToTree(Object data) throws Exception {
        try {
            return mapper.valueToTree(data);
        } catch (IllegalArgumentException e) {
            throw new Exception("Failed to parse object data", e);
        }
    }

    public static JsonNodeFactory getNodeFactory() {
        return nodeFactory;
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }
}


