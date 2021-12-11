package it.proconsole.library.video.rest.controller.jpa;

import it.proconsole.library.video.adapter.jpa.model.FilmReview;
import it.proconsole.library.video.adapter.jpa.repository.FilmReviewRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jpa")
public class FilmReviewController {
  private final FilmReviewRepository filmReviewRepository;

  public FilmReviewController(FilmReviewRepository filmReviewRepository) {
    this.filmReviewRepository = filmReviewRepository;
  }

  @PostMapping("/review")
  public FilmReview addReview(FilmReview review) {
    return filmReviewRepository.save(review);
  }
}
