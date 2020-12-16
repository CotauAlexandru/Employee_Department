package com.example.demo;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestDtoUtility {

    public static void assertSerialization(String file, Class serializationClass) throws IOException {
        String eventJson = new String(Files.readAllBytes(Paths.get("src/test/resources/"+file)));
        ObjectMapper objectMapper = new ObjectMapper();
        Object object = objectMapper.readValue(eventJson, serializationClass);
        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        assertNotNull(jsonStr);
    }

}








