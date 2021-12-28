package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.core.exception.FilmNotFoundException;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import it.proconsole.library.video.core.repository.Protocol;

import java.util.Optional;

public class JdbcFilmReviewRepository implements FilmReviewRepository {
  private final FilmReviewDao filmReviewDao;
  private final FilmDao filmDao;
  private final FilmReviewAdapter filmReviewAdapter;

  public JdbcFilmReviewRepository(FilmReviewDao filmReviewDao, FilmDao filmDao, FilmReviewAdapter filmReviewAdapter) {
    this.filmReviewDao = filmReviewDao;
    this.filmDao = filmDao;
    this.filmReviewAdapter = filmReviewAdapter;
  }

  @Override
  public Protocol protocol() {
    return Protocol.JDBC;
  }

  @Override
  public FilmReview save(FilmReview review) {
    return Optional.ofNullable(review.filmId())
            .flatMap(filmDao::findById)
            .map(it -> {
              var entity = filmReviewAdapter.fromDomain(review, it.id());
              var saved = filmReviewDao.save(entity);
              return filmReviewAdapter.toDomain(saved);
            }).orElseThrow(() -> new FilmNotFoundException(review));
  }
}
