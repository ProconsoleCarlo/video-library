package it.proconsole.library.video.adapter.jpa.config;

import it.proconsole.library.video.adapter.jpa.repository.JpaFilmRepository;
import it.proconsole.library.video.adapter.jpa.repository.JpaFilmReviewRepository;
import it.proconsole.library.video.adapter.jpa.repository.adapter.FilmAdapter;
import it.proconsole.library.video.adapter.jpa.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.jpa.repository.adapter.GenreAdapter;
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
  public FilmReviewRepository jpaFilmReviewRepository(
          FilmReviewCrudRepository filmReviewCrudRepository,
          FilmCrudRepository filmCrudRepository,
          FilmReviewAdapter jpaFilmReviewAdapter
  ) {
    return new JpaFilmReviewRepository(filmReviewCrudRepository, filmCrudRepository, jpaFilmReviewAdapter);
  }

  @Bean
  public FilmRepository jpaFilmRepository(
          FilmCrudRepository filmCrudRepository,
          FilmAdapter jpaFilmAdapter
  ) {
    return new JpaFilmRepository(filmCrudRepository, jpaFilmAdapter);
  }

  @Bean
  public FilmAdapter jpaFilmAdapter(FilmReviewAdapter jpaFilmReviewAdapter) {
    return new FilmAdapter(new GenreAdapter(), jpaFilmReviewAdapter);
  }

  @Bean
  public FilmReviewAdapter jpaFilmReviewAdapter() {
    return new FilmReviewAdapter();
  }
}
