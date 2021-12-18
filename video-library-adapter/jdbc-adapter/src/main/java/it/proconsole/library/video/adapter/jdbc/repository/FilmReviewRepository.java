package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmEntity;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmReviewEntity;
import it.proconsole.library.video.core.model.FilmReview;

import java.util.List;

public class FilmReviewRepository {
  private final FilmReviewDao filmReviewDao;

  public FilmReviewRepository(FilmReviewDao filmReviewDao) {
    this.filmReviewDao = filmReviewDao;
  }

  public FilmReview save(FilmReview review) {
    var filmReviewEntity = FilmReviewEntity.fromDomain(review);
    return filmReviewDao.save(filmReviewEntity).toDomain();
  }

  private List<FilmReview> retrieveReviewsFor(FilmEntity film) {
    return filmReviewDao.findByFilmId(film.id())
            .stream().map(FilmReviewEntity::toDomain)
            .toList();
  }
}
