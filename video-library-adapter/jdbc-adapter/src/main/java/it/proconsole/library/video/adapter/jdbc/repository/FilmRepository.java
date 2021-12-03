package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.model.Film;
import it.proconsole.library.video.adapter.jdbc.model.FilmReview;
import it.proconsole.library.video.adapter.jdbc.model.Genre;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmGenreDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.GenreDao;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmEntity;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmReviewEntity;
import it.proconsole.library.video.adapter.jdbc.repository.entity.GenreEntity;

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
            .map(film -> {
              var genres = retrieveGenresFor(film);
              var reviews = retrieveReviewsFor(film);
              return film.toDomain(genres, reviews);
            }).toList();
  }

  private List<Genre> retrieveGenresFor(FilmEntity film) {
    return filmGenreDao.findByFilmId(film.id())
            .stream()
            .map(filmGenreEntity -> genreDao.findById(filmGenreEntity.genreId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(GenreEntity::toDomain)
            .toList();
  }

  private List<FilmReview> retrieveReviewsFor(FilmEntity film) {
    return filmReviewDao.findByFilmId(film.id())
            .stream().map(FilmReviewEntity::toDomain)
            .toList();
  }
}
