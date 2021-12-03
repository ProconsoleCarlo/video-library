package it.proconsole.library.video.adapter.jpa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "film_review", indexes = {
        @Index(name = "fk_film_review_film1_idx", columnList = "film_id")
})
@Entity
public class FilmReview {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "date", nullable = false)
  private LocalDateTime date;

  @Column(name = "rating", nullable = false)
  private Integer rating;

  @Lob
  @Column(name = "detail")
  private String detail;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "film_id")
  private Film film;

  public Film getFilm() {
    return film;
  }

  public void setFilm(Film film) {
    this.film = film;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FilmReview that = (FilmReview) o;
    return id.equals(that.id) && date.equals(that.date) && rating.equals(that.rating) && Objects.equals(detail, that.detail) && film.equals(that.film);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, date, rating, detail, film);
  }
}