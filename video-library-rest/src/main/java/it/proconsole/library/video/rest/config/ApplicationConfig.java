package it.proconsole.library.video.rest.config;

import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import it.proconsole.library.video.rest.repository.FilmProtocolRepository;
import it.proconsole.library.video.rest.repository.FilmReviewProtocolRepository;
import it.proconsole.library.video.rest.repository.ProtocolRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
  @Bean
  public ProtocolRepository<FilmRepository> filmProtocolRepository(
          FilmRepository jdbcFilmRepository,
          FilmRepository jpaFilmRepository,
          FilmRepository xlsxFilmRepository
  ) {
    return new FilmProtocolRepository(jdbcFilmRepository, jpaFilmRepository, xlsxFilmRepository);
  }

  @Bean
  public ProtocolRepository<FilmReviewRepository> filmReviewProtocolRepository(
          FilmReviewRepository jdbcFilmReviewRepository,
          FilmReviewRepository jpaFilmReviewRepository
  ) {
    return new FilmReviewProtocolRepository(jdbcFilmReviewRepository, jpaFilmReviewRepository);
  }
}
