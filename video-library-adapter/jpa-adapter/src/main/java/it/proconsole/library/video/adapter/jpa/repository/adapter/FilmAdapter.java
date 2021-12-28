package it.proconsole.library.video.adapter.jpa.repository.adapter;

import it.proconsole.library.video.adapter.jpa.model.FilmEntity;
import it.proconsole.library.video.core.model.Film;

import java.util.List;
import java.util.Optional;

public class FilmAdapter {
  private final GenreAdapter genreAdapter;
  private final FilmReviewAdapter filmReviewAdapter;

  public FilmAdapter(GenreAdapter genreAdapter, FilmReviewAdapter filmReviewAdapter) {
    this.genreAdapter = genreAdapter;
    this.filmReviewAdapter = filmReviewAdapter;
  }

  public List<FilmEntity> fromDomain(List<Film> films) {
    return films.stream().map(this::fromDomain).toList();
  }

  public List<Film> toDomain(List<FilmEntity> filmRows) {
    return filmRows.stream().map(this::toDomain).toList();
  }

  private FilmEntity fromDomain(Film film) {
    var entity = new FilmEntity();
    Optional.ofNullable(film.id()).ifPresent(entity::setId);
    entity.setTitle(film.title());
    entity.setYear(film.year());
    entity.setGenres(genreAdapter.fromDomain(film.genres()));
    entity.setReviews(filmReviewAdapter.fromDomain(film.reviews(), entity));
    return entity;
  }

  private Film toDomain(FilmEntity film) {
    return new Film(
            film.getId(),
            film.getTitle(),
            film.getYear(),
            genreAdapter.toDomain(film.getGenres()),
            filmReviewAdapter.toDomain(film.getReviews())
    );
  }
}
