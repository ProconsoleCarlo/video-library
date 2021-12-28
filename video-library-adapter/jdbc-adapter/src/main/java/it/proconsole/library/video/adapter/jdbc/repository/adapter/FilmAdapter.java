package it.proconsole.library.video.adapter.jdbc.repository.adapter;

import it.proconsole.library.video.adapter.jdbc.model.FilmEntity;
import it.proconsole.library.video.adapter.jdbc.model.FilmReviewEntity;
import it.proconsole.library.video.adapter.jdbc.model.GenreEntity;
import it.proconsole.library.video.core.model.Film;

import java.util.List;

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

  private FilmEntity fromDomain(Film film) {
    return new FilmEntity(film.id(), film.title(), film.year());
  }

  public Film toDomain(FilmEntity film, List<GenreEntity> genres, List<FilmReviewEntity> reviews) {
    return new Film(
            film.id(),
            film.title(),
            film.year(),
            genreAdapter.toDomain(genres),
            filmReviewAdapter.toDomain(reviews)
    );
  }
}
