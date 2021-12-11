package it.proconsole.library.video.adapter.jpa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "film_review", indexes = {
    @Index(name = "fk_film_review_film1_idx", columnList = "film_id")
})
@Entity
public class FilmReview {
  public FilmReview() {
  }

  public FilmReview(LocalDateTime date, Integer rating, String detail, Film film) {
    this.date = date;
    this.rating = rating;
    this.detail = detail;
    this.film = film;
  }

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
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    FilmReview that = (FilmReview) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, date, rating, detail, film);
  }
}