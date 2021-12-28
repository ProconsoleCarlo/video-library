package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import it.proconsole.library.video.core.repository.Protocol;

public class JdbcFilmReviewRepository implements FilmReviewRepository {
  private final FilmReviewDao filmReviewDao;
  private final FilmReviewAdapter filmReviewAdapter;

  public JdbcFilmReviewRepository(FilmReviewDao filmReviewDao, FilmReviewAdapter filmReviewAdapter) {
    this.filmReviewDao = filmReviewDao;
    this.filmReviewAdapter = filmReviewAdapter;
  }

  @Override
  public Protocol protocol() {
    return Protocol.JDBC;
  }

  @Override
  public FilmReview save(FilmReview review) {
    var entity = filmReviewAdapter.fromDomain(review, review.filmId());
    var saved = filmReviewDao.save(entity);
    return filmReviewAdapter.toDomain(saved);
  }
}
