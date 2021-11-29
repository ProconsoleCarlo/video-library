package it.proconsole.videodb.rest.controller;

import it.proconsole.videodb.model.Film;
import it.proconsole.videodb.db.model.FilmGenreTable;
import it.proconsole.videodb.db.model.FilmTable;
import it.proconsole.videodb.db.model.GenreTable;
import it.proconsole.videodb.db.repository.FilmDao;
import it.proconsole.videodb.db.repository.FilmGenreDao;
import it.proconsole.videodb.database.FilmRepository;
import it.proconsole.videodb.db.repository.GenreDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloWorldController {
  private final FilmDao filmDao;
  private final GenreDao genreDao;
  private final FilmGenreDao filmGenreDao;
  private final FilmRepository filmRepository;

  public HelloWorldController(FilmDao filmDao, GenreDao genreDao, FilmGenreDao filmGenreDao, FilmRepository filmRepository) {
    this.filmDao = filmDao;
    this.genreDao = genreDao;
    this.filmGenreDao = filmGenreDao;
    this.filmRepository = filmRepository;
  }

  @GetMapping("/jpa")
  public Iterable<Film> jpa() {
    return filmRepository.findAll();
  }

  @GetMapping("/film")
  public List<FilmTable> allFilms() {
    return filmDao.findAll();
  }

  @GetMapping("/genre")
  public List<GenreTable> allGenres() {
    return genreDao.findAll();
  }

  @GetMapping("/film-genre")
  public List<FilmGenreTable> filmGenres() {
    return filmGenreDao.findAll();
  }
}
