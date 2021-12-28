package it.proconsole.library.video.adapter.jdbc.repository.adapter;

import it.proconsole.library.video.adapter.jdbc.model.FilmReviewEntity;
import it.proconsole.library.video.core.model.FilmReview;
import org.springframework.lang.Nullable;

import java.util.List;

public class FilmReviewAdapter {
  public List<FilmReviewEntity> fromDomain(List<FilmReview> filmReviews, @Nullable Long filmId) {
    return filmReviews.stream().map(filmReview -> fromDomain(filmReview, filmId)).toList();
  }

  public List<FilmReview> toDomain(List<FilmReviewEntity> filmReviews) {
    return filmReviews.stream().map(this::toDomain).toList();
  }

  public FilmReviewEntity fromDomain(FilmReview filmReview, @Nullable Long filmId) {
    return new FilmReviewEntity(
            filmReview.id(),
            filmReview.date(),
            filmReview.rating(),
            filmReview.detail(),
            filmId
    );
  }

  public FilmReview toDomain(FilmReviewEntity filmReview) {
    return new FilmReview(
            filmReview.id(),
            filmReview.date(),
            filmReview.rating(),
            filmReview.detail(),
            filmReview.filmId()
    );
  }
}
