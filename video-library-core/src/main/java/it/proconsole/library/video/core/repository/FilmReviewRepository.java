package it.proconsole.library.video.core.repository;

import it.proconsole.library.video.core.model.FilmReview;

public interface FilmReviewRepository extends ByProtocolRepository {
  FilmReview save(FilmReview review);
}
