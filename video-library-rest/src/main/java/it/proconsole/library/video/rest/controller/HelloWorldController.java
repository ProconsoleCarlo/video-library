package it.proconsole.library.video.rest.controller;

import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmGenreDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.GenreDao;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmEntity;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmGenreEntity;
import it.proconsole.library.video.adapter.jdbc.repository.entity.GenreEntity;
import it.proconsole.library.video.adapter.jpa.model.Film;
import it.proconsole.library.video.adapter.jpa.repository.FilmRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloWorldController {
  private final FilmDao filmDao;
  private final GenreDao genreDao;
  private final FilmGenreDao filmGenreDao;
  private final FilmRepository filmRepository;
  private final it.proconsole.library.video.adapter.jdbc.repository.FilmRepository jdbcFilmRepository;

  public HelloWorldController(FilmDao filmDao, GenreDao genreDao, FilmGenreDao filmGenreDao, FilmRepository filmRepository, it.proconsole.library.video.adapter.jdbc.repository.FilmRepository jdbcFilmRepository) {
    this.filmDao = filmDao;
    this.genreDao = genreDao;
    this.filmGenreDao = filmGenreDao;
    this.filmRepository = filmRepository;
    this.jdbcFilmRepository = jdbcFilmRepository;
  }

  @GetMapping("/jpa")
  public Iterable<Film> jpa() {
    return filmRepository.findAll();
  }

  @GetMapping("/jdbc")
  public Iterable<it.proconsole.library.video.adapter.jdbc.model.Film> jdbc() {
    return jdbcFilmRepository.findAll();
  }

  @GetMapping("/film")
  public List<FilmEntity> allFilms() {
    return filmDao.findAll();
  }

  @GetMapping("/genre")
  public List<GenreEntity> allGenres() {
    return genreDao.findAll();
  }

  @GetMapping("/film-genre")
  public List<FilmGenreEntity> filmGenres() {
    return filmGenreDao.findAll();
  }
}
