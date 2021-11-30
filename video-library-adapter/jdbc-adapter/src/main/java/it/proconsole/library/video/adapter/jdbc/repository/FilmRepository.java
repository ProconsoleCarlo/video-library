package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.model.Film;
import it.proconsole.library.video.adapter.jdbc.model.Genre;
import it.proconsole.library.video.adapter.jdbc.model.Review;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmGenreDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.GenreDao;

import java.util.List;
import java.util.Optional;

public class FilmRepository {
  private final FilmDao filmDao;
  private final FilmGenreDao filmGenreDao;
  private final GenreDao genreDao;
  private final FilmReviewDao filmReviewDao;

  public FilmRepository(FilmDao filmDao, FilmGenreDao filmGenreDao, GenreDao genreDao, FilmReviewDao filmReviewDao) {
    this.filmDao = filmDao;
    this.filmGenreDao = filmGenreDao;
    this.genreDao = genreDao;
    this.filmReviewDao = filmReviewDao;
  }

  public List<Film> findAll() {
    return filmDao.findAll()
            .stream()
            .map(filmEntity -> {
              var filmGenres = filmGenreDao.findBy(filmEntity.id());
              var genres = filmGenres.stream()
                      .map(filmGenreEntity -> genreDao.findById(filmGenreEntity.genreId()))
                      .filter(Optional::isPresent)
                      .map(Optional::get)
                      .map(genreEntity -> new Genre(genreEntity.id(), genreEntity.value()))
                      .toList();
              var reviews = filmReviewDao.findById(filmEntity.id())
                      .stream().map(filmReviewEntity -> new Review(filmReviewEntity.id(), filmReviewEntity.date(), filmReviewEntity.rating(), filmReviewEntity.detail()))
                      .toList();
              return new Film(
                      filmEntity.id(),
                      filmEntity.title(),
                      filmEntity.year(),
                      genres,
                      reviews
              );
            })
            .toList();
  }
}
