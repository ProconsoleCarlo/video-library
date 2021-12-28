package it.proconsole.library.video.adapter.jpa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.Hibernate;
import org.springframework.lang.Nullable;

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
public class FilmReviewEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  @Column(name = "date", nullable = false)
  private LocalDateTime date;
  @Column(name = "rating", nullable = false)
  private Integer rating;
  @Lob
  @Column(name = "detail")
  @Nullable
  private String detail;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "film_id", nullable = false)
  private FilmEntity film;

  public FilmReviewEntity() {
  }

  @Deprecated
  public FilmReviewEntity(Long id, LocalDateTime date, Integer rating, String detail) {
    this.id = id;
    this.date = date;
    this.rating = rating;
    this.detail = detail;
    this.film = null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  @Nullable
  public String getDetail() {
    return detail;
  }

  public void setDetail(@Nullable String detail) {
    this.detail = detail;
  }

  public FilmEntity getFilm() {
    return film;
  }

  public void setFilm(FilmEntity film) {
    this.film = film;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    FilmReviewEntity that = (FilmReviewEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" +
            "id = " + id + ", " +
            "date = " + date + ", " +
            "rating = " + rating + ", " +
            "detail = " + detail + ")";
  }
}