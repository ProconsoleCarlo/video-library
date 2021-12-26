package it.proconsole.library.video.adapter.xlsx.repository.adapter;

import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import it.proconsole.library.video.core.model.Film;

public class FilmAdapter {
  private final GenreAdapter genreAdapter;
  private final FilmReviewAdapter filmReviewAdapter;

  public FilmAdapter(GenreAdapter genreAdapter, FilmReviewAdapter filmReviewAdapter) {
    this.genreAdapter = genreAdapter;
    this.filmReviewAdapter = filmReviewAdapter;
  }

  public FilmRow fromDomain(Film film) {
    return new FilmRow(
            film.id(),
            film.title(),
            film.year(),
            genreAdapter.fromDomain(film.genres()),
            filmReviewAdapter.fromDomain(film.reviews())
    );
  }

  public Film toDomain(FilmRow filmRow) {
    return new Film(
            filmRow.id(),
            filmRow.title(),
            filmRow.year(),
            genreAdapter.toDomain(filmRow.genres()),
            filmReviewAdapter.toDomain(filmRow.reviews(), filmRow.id())
    );
  }
}
