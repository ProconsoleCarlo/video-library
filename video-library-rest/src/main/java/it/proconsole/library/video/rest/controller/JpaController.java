package it.proconsole.library.video.rest.controller;

import it.proconsole.library.video.adapter.jpa.model.Film;
import it.proconsole.library.video.adapter.jpa.repository.FilmRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jpa")
public class JpaController {
  private final FilmRepository filmRepository;

  public JpaController(FilmRepository filmRepository) {
    this.filmRepository = filmRepository;
  }

  @GetMapping("/films")
  public Iterable<Film> getFilms() {
    return filmRepository.findAll();
  }
}
