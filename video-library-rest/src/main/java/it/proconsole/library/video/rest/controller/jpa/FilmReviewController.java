package it.proconsole.library.video.rest.controller.jpa;

import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("JpaFilmReviewController")
@RequestMapping("/jpa")
public class FilmReviewController {
  private final FilmReviewCrudRepository filmReviewRepository;

  public FilmReviewController(FilmReviewCrudRepository filmReviewRepository) {
    this.filmReviewRepository = filmReviewRepository;
  }

  @PostMapping("/review")
  public FilmReviewEntity updateReview(@RequestBody FilmReviewEntity review) {
    return filmReviewRepository.save(review);
  }

  @PutMapping("/review")
  public FilmReviewEntity insertReview(@RequestBody FilmReviewEntity review) {
    return filmReviewRepository.save(review);
  }
}
