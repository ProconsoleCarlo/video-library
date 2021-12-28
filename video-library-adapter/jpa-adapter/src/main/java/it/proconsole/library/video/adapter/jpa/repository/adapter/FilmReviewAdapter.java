package it.proconsole.library.video.adapter.jpa.repository.adapter;

import it.proconsole.library.video.adapter.jpa.model.FilmEntity;
import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import it.proconsole.library.video.core.model.FilmReview;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FilmReviewAdapter {
  public List<FilmReviewEntity> fromDomain(List<FilmReview> filmReviews, @Nullable FilmEntity film) {
    return filmReviews.stream().map(filmReview -> fromDomain(filmReview, film)).collect(Collectors.toList());
  }

  public List<FilmReview> toDomain(List<FilmReviewEntity> filmReviews) {
    return filmReviews.stream().map(this::toDomain).toList();
  }

  private FilmReviewEntity fromDomain(FilmReview filmReview, @Nullable FilmEntity film) {
    var review = new FilmReviewEntity();
    Optional.ofNullable(filmReview.id()).ifPresent(review::setId);
    review.setDate(filmReview.date());
    review.setRating(filmReview.rating());
    review.setDetail(filmReview.detail());
    Optional.ofNullable(film).ifPresent(review::setFilm);
    return review;
  }

  private FilmReview toDomain(FilmReviewEntity filmReview) {
    return new FilmReview(
            filmReview.getId(),
            filmReview.getDate(),
            filmReview.getRating(),
            filmReview.getDetail(),
            filmReview.getFilm().getId()
    );
  }
}
