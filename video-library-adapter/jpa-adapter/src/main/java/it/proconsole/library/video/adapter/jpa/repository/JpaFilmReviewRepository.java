package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmCrudRepository;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import it.proconsole.library.video.core.exception.FilmNotFoundException;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import it.proconsole.library.video.core.repository.Protocol;

public class JpaFilmReviewRepository implements FilmReviewRepository {
  private final FilmReviewCrudRepository filmReviewCrudRepository;
  private final FilmCrudRepository filmCrudRepository;
  private final FilmReviewAdapter filmReviewAdapter;

  public JpaFilmReviewRepository(
          FilmReviewCrudRepository filmReviewCrudRepository,
          FilmCrudRepository filmCrudRepository,
          FilmReviewAdapter filmReviewAdapter
  ) {
    this.filmReviewCrudRepository = filmReviewCrudRepository;
    this.filmCrudRepository = filmCrudRepository;
    this.filmReviewAdapter = filmReviewAdapter;
  }

  @Override
  public Protocol protocol() {
    return Protocol.JPA;
  }

  @Override
  public FilmReview save(FilmReview review, Long filmId) {
    return filmCrudRepository.findById(filmId)
            .map(it -> {
              var entity = filmReviewAdapter.fromDomain(review, it);
              var saved = filmReviewCrudRepository.save(entity);
              return filmReviewAdapter.toDomain(saved);
            }).orElseThrow(() -> new FilmNotFoundException(review, filmId));
  }
}
