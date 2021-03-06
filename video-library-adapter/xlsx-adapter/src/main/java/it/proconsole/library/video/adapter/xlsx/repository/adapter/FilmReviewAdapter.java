package it.proconsole.library.video.adapter.xlsx.repository.adapter;

import it.proconsole.library.video.adapter.xlsx.model.FilmReviewRow;
import it.proconsole.library.video.core.model.FilmReview;

import java.util.List;

public class FilmReviewAdapter {
  public List<FilmReviewRow> fromDomain(List<FilmReview> filmReviews) {
    return filmReviews.stream().map(this::fromDomain).toList();
  }

  public List<FilmReview> toDomain(List<FilmReviewRow> filmReviewRows) {
    return filmReviewRows.stream().map(this::toDomain).toList();
  }

  private FilmReviewRow fromDomain(FilmReview filmReview) {
    return new FilmReviewRow(filmReview.id(), filmReview.date(), filmReview.rating(), filmReview.detail());
  }

  private FilmReview toDomain(FilmReviewRow filmReviewRow) {
    return new FilmReview(filmReviewRow.id(), filmReviewRow.date(), filmReviewRow.rating(), filmReviewRow.detail());
  }
}
