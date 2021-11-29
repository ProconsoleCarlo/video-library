package it.proconsole.videodb.config;

import it.proconsole.videodb.db.repository.FilmDao;
import it.proconsole.videodb.db.repository.FilmGenreDao;
import it.proconsole.videodb.db.repository.GenreDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfig {
  @Bean
  public FilmDao filmDao(DataSource videoDbDataSource) {
    return new FilmDao(videoDbDataSource);
  }

  @Bean
  public GenreDao genreDao(DataSource videoDbDataSource) {
    return new GenreDao(videoDbDataSource);
  }

  @Bean
  public FilmGenreDao filmGenreDao(DataSource videoDbDataSource) {
    return new FilmGenreDao(videoDbDataSource);
  }
}
