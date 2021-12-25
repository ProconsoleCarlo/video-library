package it.proconsole.library.video.adapter.xlsx.repository.adapter;

import it.proconsole.library.video.core.model.GenreEnum;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenreAdapterTest {
  private static final List<GenreEnum> DOMAIN = Arrays.stream(GenreEnum.values()).toList();
  private static final String FRONTIER = "Azione, avventura, commedia, drammatico, fantastico, romantico, fantascienza, suspense/thriller";

  private final GenreAdapter adapter = new GenreAdapter();

  @Test
  void fromDomain() {
    var current = adapter.fromDomain(DOMAIN);

    assertEquals(FRONTIER, current);
  }

  @Test
  void toDomain() {
    var current = adapter.toDomain(FRONTIER);

    assertEquals(DOMAIN, current);
  }
}