package it.proconsole.library.video.rest.controller.jdbc;

import it.proconsole.library.video.adapter.jdbc.model.FilmReview;
import it.proconsole.library.video.adapter.jdbc.repository.FilmReviewRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("JdbcFilmReviewController")
@RequestMapping("/jdbc")
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
