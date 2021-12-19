package it.proconsole.library.video.rest.controller.jpa;

import it.proconsole.library.video.adapter.jpa.model.CompleteFilmEntity;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmCrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("JpaFilmController")
@RequestMapping("/jpa")
public class FilmController {
  private final FilmCrudRepository filmRepository;

  public FilmController(FilmCrudRepository filmRepository) {
    this.filmRepository = filmRepository;
  }

  @GetMapping("/films")
  public List<CompleteFilmEntity> getFilms() {
    return filmRepository.findAll();
  }

  @PutMapping("/films")
  public List<CompleteFilmEntity> updateFilms(@RequestBody List<CompleteFilmEntity> films) {
    return filmRepository.saveAll(films);
  }
}
