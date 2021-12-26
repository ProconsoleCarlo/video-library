package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import it.proconsole.library.video.core.repository.Protocol;

public class JpaFilmReviewRepository implements FilmReviewRepository {
  private final FilmReviewCrudRepository filmReviewCrudRepository;

  public JpaFilmReviewRepository(FilmReviewCrudRepository filmReviewCrudRepository) {
    this.filmReviewCrudRepository = filmReviewCrudRepository;
  }

  @Override
  public Protocol protocol() {
    return Protocol.JPA;
  }

  @Override
  public FilmReview save(FilmReview review) {
    return filmReviewCrudRepository.save(FilmReviewEntity.fromDomain(review)).toDomain();
  }
}
