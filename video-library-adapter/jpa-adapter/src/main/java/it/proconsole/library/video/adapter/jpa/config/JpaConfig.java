package it.proconsole.library.video.adapter.jpa.config;

import it.proconsole.library.video.adapter.jpa.repository.FilmReviewRepository;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("it.proconsole.library.video.adapter.jpa.repository.crud")
public class JpaConfig {
  @Bean
  public FilmReviewRepository filmReviewRepository(FilmReviewCrudRepository filmReviewCrudRepository) {
    return new FilmReviewRepository(filmReviewCrudRepository);
  }
}
