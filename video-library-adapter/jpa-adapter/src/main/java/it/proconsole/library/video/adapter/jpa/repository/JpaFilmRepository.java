package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.model.FilmEntity;
import it.proconsole.library.video.adapter.jpa.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.jpa.repository.adapter.GenreAdapter;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmCrudRepository;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Protocol;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JpaFilmRepository implements FilmRepository {
  private final FilmCrudRepository filmCrudRepository;
  private final FilmReviewCrudRepository filmReviewCrudRepository;

  private final GenreAdapter genreAdapter = new GenreAdapter();
  private final FilmReviewAdapter filmReviewAdapter = new FilmReviewAdapter();

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
            .map(FilmEntity::toDomain)
            .toList();
  }

  @Override
  public List<Film> saveAll(List<Film> films) {
    var filmEntities = films.stream().map(it -> convert(it)).collect(Collectors.toList());

    return filmCrudRepository.saveAll(filmEntities).stream().map(it -> convert(it)).toList();
  }

  private FilmEntity convert(Film film) {
    return Optional.ofNullable(film.id())
            .flatMap(id -> filmCrudRepository.findById(film.id()))
            .map(it -> {
              it.setTitle(film.title());
              it.setYear(film.year());
              it.setGenres(genreAdapter.fromDomain(film.genres()));
              it.setReviews(filmReviewAdapter.fromDomain(film.reviews(), it));
              return it;
            }).orElseGet(() -> {
              var entity = new FilmEntity();
              entity.setTitle(film.title());
              entity.setYear(film.year());
              entity.setGenres(genreAdapter.fromDomain(film.genres()));
              entity.setReviews(filmReviewAdapter.fromDomain(film.reviews(), entity));
              return entity;
            });
  }

  private Film convert(FilmEntity film) {
    return new Film(
            film.getId(),
            film.getTitle(),
            film.getYear(),
            genreAdapter.toDomain(film.getGenres()),
            filmReviewAdapter.toDomain(film.getReviews())
    );
  }
}
