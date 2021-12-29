package it.proconsole.library.video.adapter.jdbc.config;

import it.proconsole.library.video.adapter.jdbc.repository.JdbcFilmRepository;
import it.proconsole.library.video.adapter.jdbc.repository.JdbcFilmReviewRepository;
import it.proconsole.library.video.adapter.jdbc.repository.adapter.FilmAdapter;
import it.proconsole.library.video.adapter.jdbc.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.jdbc.repository.adapter.GenreAdapter;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.GenreDao;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
  @Bean
  public FilmRepository jdbcFilmRepository(
          FilmDao filmDao,
          GenreDao genreDao,
          FilmReviewDao filmReviewDao
  ) {
    return new JdbcFilmRepository(filmDao, genreDao, filmReviewDao, new FilmAdapter(new GenreAdapter(), new FilmReviewAdapter()));
  }

  @Bean
  public FilmReviewRepository jdbcFilmReviewRepository(FilmReviewDao filmReviewDao, FilmDao filmDao) {
    return new JdbcFilmReviewRepository(filmReviewDao, filmDao, new FilmReviewAdapter());
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
  public FilmReviewDao filmReviewDao(DataSource videoLibraryDataSource) {
    return new FilmReviewDao(videoLibraryDataSource);
  }
}
