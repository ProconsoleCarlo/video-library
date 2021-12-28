package it.proconsole.library.video.adapter.jpa.config;

import it.proconsole.library.video.adapter.jpa.repository.JpaFilmRepository;
import it.proconsole.library.video.adapter.jpa.repository.JpaFilmReviewRepository;
import it.proconsole.library.video.adapter.jpa.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmCrudRepository;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("it.proconsole.library.video.adapter.jpa.repository.crud")
public class JpaConfig {
  @Bean
  public FilmReviewRepository jpaFilmReviewRepository(FilmReviewCrudRepository filmReviewCrudRepository) {
    return new JpaFilmReviewRepository(filmReviewCrudRepository, new FilmReviewAdapter());
  }

  @Bean
  public FilmRepository jpaFilmRepository(FilmCrudRepository filmCrudRepository) {
    return new JpaFilmRepository(filmCrudRepository);
  }
}
