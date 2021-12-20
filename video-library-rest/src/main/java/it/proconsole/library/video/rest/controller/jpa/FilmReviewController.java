package it.proconsole.library.video.rest.controller.jpa;

import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("JpaFilmReviewController")
@RequestMapping("/jpa")
public class FilmReviewController {
  private final FilmReviewRepository filmReviewRepository;

  public FilmReviewController(FilmReviewRepository jpaFilmReviewRepository) {
    this.filmReviewRepository = jpaFilmReviewRepository;
  }

  @PostMapping("/review")
  public FilmReview updateReview(@RequestBody FilmReview review) {
    return filmReviewRepository.save(review);
  }

  @PutMapping("/review")
  public FilmReview insertReview(@RequestBody FilmReview review) {
    return filmReviewRepository.save(review);
  }
}
