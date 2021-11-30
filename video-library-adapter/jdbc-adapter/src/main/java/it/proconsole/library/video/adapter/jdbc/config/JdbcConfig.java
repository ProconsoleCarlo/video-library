package it.proconsole.library.video.adapter.jdbc.config;

import it.proconsole.library.video.adapter.jdbc.repository.FilmRepository;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmGenreDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.GenreDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
  @Bean
  public FilmRepository jdbcFilmRepository(
          FilmDao filmDao,
          FilmGenreDao filmGenreDao,
          GenreDao genreDao,
          FilmReviewDao filmReviewDao
  ) {
    return new FilmRepository(filmDao, filmGenreDao, genreDao, filmReviewDao);
  }

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

  @Bean
  public FilmReviewDao filmReviewDao(DataSource videoLibraryDataSource) {
    return new FilmReviewDao(videoLibraryDataSource);
  }
}
