package it.proconsole.library.video.adapter.xlsx.repository.adapter;

import it.proconsole.library.video.adapter.xlsx.model.ReviewRow;
import it.proconsole.library.video.core.model.FilmReview;

import java.util.List;

public class FilmReviewAdapter {
  public List<ReviewRow> fromDomain(List<FilmReview> filmReviews) {
    return filmReviews.stream().map(this::fromDomain).toList();
  }

  public List<FilmReview> toDomain(List<ReviewRow> reviewRows, Long filmId) {
    return reviewRows.stream().map(it -> toDomain(it, filmId)).toList();
  }

  private ReviewRow fromDomain(FilmReview filmReview) {
    return new ReviewRow(filmReview.id(), filmReview.date(), filmReview.rating(), filmReview.detail());
  }

  private FilmReview toDomain(ReviewRow reviewRow, Long filmId) {
    return new FilmReview(reviewRow.id(), reviewRow.date(), reviewRow.rating(), reviewRow.detail(), filmId);
  }
}
