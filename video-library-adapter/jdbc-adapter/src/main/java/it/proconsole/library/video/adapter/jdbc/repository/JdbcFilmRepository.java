package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.exception.EntityNotSavedException;
import it.proconsole.library.video.adapter.jdbc.model.FilmEntity;
import it.proconsole.library.video.adapter.jdbc.model.FilmReviewEntity;
import it.proconsole.library.video.adapter.jdbc.model.GenreEntity;
import it.proconsole.library.video.adapter.jdbc.repository.adapter.FilmAdapter;
import it.proconsole.library.video.adapter.jdbc.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.jdbc.repository.adapter.GenreAdapter;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.GenreDao;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Protocol;

import java.util.List;
import java.util.Optional;

public class JdbcFilmRepository implements FilmRepository {
  private final FilmDao filmDao;
  private final GenreDao genreDao;
  private final FilmReviewDao filmReviewDao;
  private final FilmAdapter filmAdapter;
  private final GenreAdapter genreAdapter = new GenreAdapter();
  private final FilmReviewAdapter filmReviewAdapter = new FilmReviewAdapter();

  public JdbcFilmRepository(
          FilmDao filmDao,
          GenreDao genreDao,
          FilmReviewDao filmReviewDao,
          FilmAdapter filmAdapter
  ) {
    this.filmDao = filmDao;
    this.genreDao = genreDao;
    this.filmReviewDao = filmReviewDao;
    this.filmAdapter = filmAdapter;
  }

  @Override
  public Protocol protocol() {
    return Protocol.JDBC;
  }

  @Override
  public List<Film> findAll() {
    return filmDao.findAll()
            .stream()
            .map(this::enrichFilm)
            .toList();
  }

  @Override
  public List<Film> saveAll(List<Film> films) {
    var filmEntities = films.stream().map(it -> {
      var film = filmDao.save(filmAdapter.fromDomain(it));
      Optional.ofNullable(film.id())
              .ifPresentOrElse(id -> {
                var genreEntities = genreAdapter.fromDomain(it.genres());
                genreDao.saveForFilmId(genreEntities, id);

                var filmReviewEntities = filmReviewAdapter.fromDomain(it.reviews(), id);
                filmReviewDao.saveByFilmId(filmReviewEntities, id);
              }, () -> {
                throw new EntityNotSavedException(film);
              });
      return film;
    });

    return findAllById(filmEntities.map(FilmEntity::id).toList());
  }

  private List<Film> findAllById(List<Long> filmIds) {
    return filmDao.findAllById(filmIds).stream()
            .map(this::enrichFilm)
            .toList();
  }

  private Film enrichFilm(FilmEntity entity) {
    return Optional.ofNullable(entity.id())
            .map(id -> filmAdapter.toDomain(entity, retrieveGenresFor(id), retrieveReviewsFor(id)))
            .orElseThrow(() -> new EntityNotSavedException(entity));
  }

  private List<GenreEntity> retrieveGenresFor(Long filmId) {
    return genreDao.findByFilmId(filmId);
  }

  private List<FilmReviewEntity> retrieveReviewsFor(Long filmId) {
    return filmReviewDao.findByFilmId(filmId);
  }
}
