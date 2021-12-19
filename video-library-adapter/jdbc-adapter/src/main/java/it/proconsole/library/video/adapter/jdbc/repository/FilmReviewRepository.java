package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmReviewEntity;
import it.proconsole.library.video.core.model.FilmReview;

public class FilmReviewRepository {
  private final FilmReviewDao filmReviewDao;

  public FilmReviewRepository(FilmReviewDao filmReviewDao) {
    this.filmReviewDao = filmReviewDao;
  }

  public FilmReview save(FilmReview review) {
    return filmReviewDao.save(FilmReviewEntity.fromDomain(review)).toDomain();
  }
}
