package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.model.FilmEntity;
import it.proconsole.library.video.adapter.jpa.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import it.proconsole.library.video.core.repository.Protocol;

public class JpaFilmReviewRepository implements FilmReviewRepository {
  private final FilmReviewCrudRepository filmReviewCrudRepository;
  private final FilmReviewAdapter filmReviewAdapter;

  public JpaFilmReviewRepository(
          FilmReviewCrudRepository filmReviewCrudRepository,
          FilmReviewAdapter filmReviewAdapter
  ) {
    this.filmReviewCrudRepository = filmReviewCrudRepository;
    this.filmReviewAdapter = filmReviewAdapter;
  }

  @Override
  public Protocol protocol() {
    return Protocol.JPA;
  }

  @Override
  public FilmReview save(FilmReview review) {
    var entity = filmReviewAdapter.fromDomain(review, new FilmEntity());
    var saved = filmReviewCrudRepository.save(entity);
    return filmReviewAdapter.toDomain(saved);
  }
}
