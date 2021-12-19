package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import it.proconsole.library.video.core.model.FilmReview;

public class FilmReviewRepository {
  private final FilmReviewCrudRepository filmReviewCrudRepository;

  public FilmReviewRepository(FilmReviewCrudRepository filmReviewCrudRepository) {
    this.filmReviewCrudRepository = filmReviewCrudRepository;
  }

  public FilmReview save(FilmReview review) {
    return filmReviewCrudRepository.save(FilmReviewEntity.fromDomain(review)).toDomain();
  }
}
