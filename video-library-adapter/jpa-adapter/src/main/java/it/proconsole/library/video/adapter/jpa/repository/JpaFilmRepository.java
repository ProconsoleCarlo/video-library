package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.model.FilmEntity;
import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmCrudRepository;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Protocol;

import java.util.List;
import java.util.Optional;

public class JpaFilmRepository implements FilmRepository {
  private final FilmCrudRepository filmCrudRepository;
  private final FilmReviewCrudRepository filmReviewCrudRepository;

  public JpaFilmRepository(FilmCrudRepository filmCrudRepository, FilmReviewCrudRepository filmReviewCrudRepository) {
    this.filmCrudRepository = filmCrudRepository;
    this.filmReviewCrudRepository = filmReviewCrudRepository;
  }

  @Override
  public Protocol protocol() {
    return Protocol.JPA;
  }

  @Override
  public List<Film> findAll() {
    return filmCrudRepository.findAll().stream()
            .map(f -> f.toDomain(filmReviewCrudRepository.findByFilmId(f.getId()).stream()
                    .map(FilmReviewEntity::toDomain)
                    .toList())
            ).toList();
  }

  @Override
  public List<Film> saveAll(List<Film> films) {
    var filmEntities = films.stream().map(FilmEntity::fromDomain).toList();
    var ids = filmCrudRepository.saveAll(filmEntities).stream().map(FilmEntity::getId).toList();
    var filmReviews = films.stream()
            .flatMap(f -> f.reviews().stream())
            .map(FilmReviewEntity::fromDomain)
            .toList();
    filmReviewCrudRepository.saveAll(filmReviews);
    return findAllById(ids);
  }

  private List<Film> findAllById(List<Long> filmIds) {
    return filmCrudRepository.findAllById(filmIds).stream()
            .map(this::enrichFilm)
            .toList();
  }

  private Film enrichFilm(FilmEntity entity) {
    return Optional.ofNullable(entity.getId())
            .map(id -> entity.toDomain(retrieveReviewsFor(id)))
            .orElseGet(entity::toDomain);
  }

  private List<FilmReview> retrieveReviewsFor(Long filmId) {
    return filmReviewCrudRepository.findByFilmId(filmId).stream()
            .map(FilmReviewEntity::toDomain)
            .toList();
  }
}
