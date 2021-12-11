package it.proconsole.library.video.rest.controller;

import it.proconsole.library.video.adapter.jpa.model.Film;
import it.proconsole.library.video.adapter.jpa.model.FilmReview;
import it.proconsole.library.video.adapter.jpa.repository.FilmRepository;
import it.proconsole.library.video.adapter.jpa.repository.FilmReviewRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jpa")
public class JpaController {
  private final FilmRepository filmRepository;
  private final FilmReviewRepository filmReviewRepository;

  public JpaController(FilmRepository filmRepository, FilmReviewRepository filmReviewRepository) {
    this.filmRepository = filmRepository;
    this.filmReviewRepository = filmReviewRepository;
  }

  @GetMapping("/films")
  public List<Film> getFilms() {
    return filmRepository.findAll();
  }

  @PostMapping("/review")
  public void addReview(FilmReview review) {
    filmReviewRepository.save(review);
  }
}
