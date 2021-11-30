package it.proconsole.library.video.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Fixtures {
  private static final ObjectMapper objectMapper = new ObjectMapper()
          .findAndRegisterModules()
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

  @NonNull
  public static String readFromClasspath(@NonNull String path) {
    try {
      return new String(Fixtures.class.getResourceAsStream(path).readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String asJsonString(@NonNull Object path) {
    try {
      return objectMapper.writeValueAsString(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @NonNull
  public static <T> T readFromClasspath(@NonNull String path, @NonNull Class<T> clazz) {
    try {
      return objectMapper
              .readValue(Fixtures.class.getResourceAsStream(path), clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T readFromClasspath(@NonNull String path, @NonNull TypeReference<T> typeReference) {
    try {
      return objectMapper
              .readValue(Fixtures.class.getResourceAsStream(path), typeReference);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
