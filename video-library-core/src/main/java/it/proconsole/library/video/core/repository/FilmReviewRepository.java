package it.proconsole.library.video.core.repository;

import it.proconsole.library.video.core.model.FilmReview;

public interface FilmReviewRepository {
  FilmReview save(FilmReview review);
}
