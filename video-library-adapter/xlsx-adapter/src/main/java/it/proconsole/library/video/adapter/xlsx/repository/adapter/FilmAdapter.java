package it.proconsole.library.video.adapter.xlsx.repository.adapter;

import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import it.proconsole.library.video.adapter.xlsx.model.ReviewRow;
import it.proconsole.library.video.core.model.Film;

public class FilmAdapter {
  private final GenreAdapter genreAdapter;

  public FilmAdapter(GenreAdapter genreAdapter) {
    this.genreAdapter = genreAdapter;
  }

  public FilmRow fromDomain(Film film) {
    return new FilmRow(
            film.id(),
            film.title(),
            film.year(),
            genreAdapter.fromDomain(film.genres()),
            film.reviews().stream().map(ReviewRow::fromDomain).toList()
    );
  }

  public Film toDomain(FilmRow filmRow) {
    return new Film(
            filmRow.id(),
            filmRow.title(),
            filmRow.year(),
            genreAdapter.toDomain(filmRow.genres()),
            filmRow.reviews().stream().map(it -> it.toDomain(filmRow.id())).toList()
    );
  }
}
