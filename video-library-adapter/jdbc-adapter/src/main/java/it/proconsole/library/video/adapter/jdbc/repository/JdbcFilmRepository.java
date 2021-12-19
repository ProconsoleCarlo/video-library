package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.GenreDao;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmEntity;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmReviewEntity;
import it.proconsole.library.video.adapter.jdbc.repository.entity.GenreEntity;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.model.Genre;
import it.proconsole.library.video.core.repository.FilmRepository;

import java.util.List;
import java.util.Optional;

public class JdbcFilmRepository implements FilmRepository {
  private final FilmDao filmDao;
  private final GenreDao genreDao;
  private final FilmReviewDao filmReviewDao;

  public JdbcFilmRepository(FilmDao filmDao, GenreDao genreDao, FilmReviewDao filmReviewDao) {
    this.filmDao = filmDao;
    this.genreDao = genreDao;
    this.filmReviewDao = filmReviewDao;
  }

  @Override
  public List<Film> findAll() {
    return filmDao.findAll()
            .stream()
            .map(this::enrichFilm)
            .toList();
  }

  private List<Film> findAllById(List<Long> filmIds) {
    return filmDao.findAllById(filmIds).stream()
            .map(this::enrichFilm)
            .toList();
  }

  @Override
  public List<Film> saveAll(List<Film> films) {
    var filmEntities = films.stream().map(FilmEntity::fromDomain).toList();
    filmDao.saveAll(filmEntities);
    var filmReviewEntities = films.stream().flatMap(f -> f.reviews().stream()).map(FilmReviewEntity::fromDomain).toList();
    filmReviewDao.saveAll(filmReviewEntities);
    return findAllById(films.stream().map(Film::id).toList());
  }

  private Film enrichFilm(FilmEntity entity) {
    return Optional.ofNullable(entity.id())
            .map(id -> entity.toDomain(retrieveGenresFor(id), retrieveReviewsFor(id)))
            .orElseGet(entity::toDomain);
  }

  private List<Genre> retrieveGenresFor(Long filmId) {
    return genreDao.findByFilmId(filmId).stream()
            .map(GenreEntity::toDomain)
            .toList();
  }

  private List<FilmReview> retrieveReviewsFor(Long filmId) {
    return filmReviewDao.findByFilmId(filmId).stream()
            .map(FilmReviewEntity::toDomain)
            .toList();
  }
}
