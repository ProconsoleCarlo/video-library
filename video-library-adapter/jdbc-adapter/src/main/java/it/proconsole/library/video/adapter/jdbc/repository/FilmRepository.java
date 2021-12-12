package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.model.Film;
import it.proconsole.library.video.adapter.jdbc.model.FilmReview;
import it.proconsole.library.video.adapter.jdbc.model.Genre;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.GenreDao;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmEntity;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmReviewEntity;
import it.proconsole.library.video.adapter.jdbc.repository.entity.GenreEntity;

import java.util.List;

public class FilmRepository {
  private final FilmDao filmDao;
  private final GenreDao genreDao;
  private final FilmReviewDao filmReviewDao;

  public FilmRepository(FilmDao filmDao, GenreDao genreDao, FilmReviewDao filmReviewDao) {
    this.filmDao = filmDao;
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
    return genreDao.findByFilmId(film.id()).stream()
            .map(GenreEntity::toDomain)
            .toList();
  }

  private List<FilmReview> retrieveReviewsFor(FilmEntity film) {
    return filmReviewDao.findByFilmId(film.id())
            .stream().map(FilmReviewEntity::toDomain)
            .toList();
  }
}
