package it.proconsole.library.video.adapter.jdbc.config;

import it.proconsole.library.video.adapter.jdbc.repository.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.FilmGenreDao;
import it.proconsole.library.video.adapter.jdbc.repository.GenreDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
  @Bean
  public FilmDao filmDao(DataSource videoLibraryDataSource) {
    return new FilmDao(videoLibraryDataSource);
  }

  @Bean
  public GenreDao genreDao(DataSource videoLibraryDataSource) {
    return new GenreDao(videoLibraryDataSource);
  }

  @Bean
  public FilmGenreDao filmGenreDao(DataSource videoLibraryDataSource) {
    return new FilmGenreDao(videoLibraryDataSource);
  }
}
