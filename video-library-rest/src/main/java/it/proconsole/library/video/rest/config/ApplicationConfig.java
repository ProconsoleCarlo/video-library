package it.proconsole.library.video.rest.config;

import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.rest.controller.Protocol;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ApplicationConfig {
  @Bean
  public Map<Protocol, FilmRepository> protocolRepositories(
          FilmRepository jdbcFilmRepository,
          FilmRepository jpaFilmRepository,
          FilmRepository xlsxFilmRepository
  ) {
    return Map.of(
            Protocol.JDBC, jdbcFilmRepository,
            Protocol.JPA, jpaFilmRepository,
            Protocol.XLSX, xlsxFilmRepository
    );
  }
}
