package com.springsockets.playground.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {
    public static JsonNode parseJson(String jsonString) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonString);
    }

    public static String toJson(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
