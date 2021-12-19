package it.proconsole.library.video.rest.controller.jpa;

import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("JpaFilmController")
@RequestMapping("/jpa")
public class FilmController {
  private final FilmRepository filmRepository;

  public FilmController(FilmRepository jpaFilmRepository) {
    this.filmRepository = jpaFilmRepository;
  }

  @GetMapping("/films")
  public List<Film> getFilms() {
    return filmRepository.findAll();
  }

  @PutMapping("/films")
  public List<Film> updateFilms(@RequestBody List<Film> films) {
    return filmRepository.saveAll(films);
  }
}
