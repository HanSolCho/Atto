package com.example.ato.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.stereotype.Repository;

@Repository
public class JsonUtil {
    private boolean indent;
    private boolean dateAsTimestamp;
    private Map<Class<?>, JsonSerializer<?>> serializerMap;
    private Map<Class<?>, JsonDeserializer<?>> deserializerMap;

    public JsonUtil() {
        this(false, false);
    }

    public JsonUtil(boolean indent, boolean dateAsTimestamp) {
        this.indent = indent;
        this.dateAsTimestamp = dateAsTimestamp;
        this.serializerMap = new HashMap<>();
        this.deserializerMap = new HashMap<>();
    }

    public String getJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = getObjectMapper();

        return mapper.writeValueAsString(obj);
    }

    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());

        Set<Class<?>> serializerKeySet = serializerMap.keySet();
        if((serializerKeySet != null) && (serializerKeySet.size() > 0)) {
            SimpleModule serializeModule = new SimpleModule("SerializeModule");

            for (Class c : serializerKeySet) {
                serializeModule.addSerializer(c, serializerMap.get(c));
            }

            mapper.registerModule(serializeModule);
        }

        Set<Class<?>> deserializerKeySet = deserializerMap.keySet();
        if((deserializerKeySet != null) && (deserializerKeySet.size() > 0)) {
            SimpleModule deserializeModule = new SimpleModule("DeserializerModule");

            for (Class c : deserializerKeySet) {
                deserializeModule.addDeserializer(c, deserializerMap.get(c));
            }

            mapper.registerModule(deserializeModule);
        }

        if(this.indent) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        } else {
            mapper.disable(SerializationFeature.INDENT_OUTPUT);
        }

        if(this.dateAsTimestamp) {
            mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        } else {
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        return mapper;
    }
}