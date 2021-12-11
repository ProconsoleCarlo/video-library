package it.proconsole.library.video.rest.controller.jdbc;

import it.proconsole.library.video.adapter.jdbc.repository.FilmRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("JdbcFilmController")
@RequestMapping("/jdbc")
public class FilmController {
  private final FilmRepository jdbcFilmRepository;

  public FilmController(FilmRepository jdbcFilmRepository) {
    this.jdbcFilmRepository = jdbcFilmRepository;
  }

  @GetMapping("/films")
  public Iterable<it.proconsole.library.video.adapter.jdbc.model.Film> getFilms() {
    return jdbcFilmRepository.findAll();
  }
}
