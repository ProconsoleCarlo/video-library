package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmReviewEntity;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;

public class JdbcFilmReviewRepository implements FilmReviewRepository {
  private final FilmReviewDao filmReviewDao;

  public JdbcFilmReviewRepository(FilmReviewDao filmReviewDao) {
    this.filmReviewDao = filmReviewDao;
  }

  @Override
  public FilmReview save(FilmReview review) {
    return filmReviewDao.save(FilmReviewEntity.fromDomain(review)).toDomain();
  }
}