package it.proconsole.library.video.adapter.jpa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Table(name = "film")
@Entity
public class CompleteFilmEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "year", nullable = false)
  private Integer year;

  @ManyToMany
  @JoinTable(name = "film_genres",
          joinColumns = @JoinColumn(name = "film_id"),
          inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private List<GenreEntity> genres;

  @JsonManagedReference
  @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FilmReview> reviews;

  public CompleteFilmEntity() {
  }

  public CompleteFilmEntity(Long id, String title, Integer year, List<GenreEntity> genres, List<FilmReview> reviews) {
    this.id = id;
    this.title = title;
    this.year = year;
    this.genres = genres;
    this.reviews = reviews;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public List<GenreEntity> getGenres() {
    return genres;
  }

  public void setGenres(List<GenreEntity> genres) {
    this.genres = genres;
  }

  public List<FilmReview> getReviews() {
    return reviews;
  }

  public void setReviews(List<FilmReview> reviews) {
    this.reviews = reviews;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    CompleteFilmEntity film = (CompleteFilmEntity) o;
    return Objects.equals(id, film.id);
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Table(name = "film_review", indexes = {
          @Index(name = "fk_film_review_film1_idx", columnList = "film_id")
  })
  @Entity
  public static class FilmReview {
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "film_id", nullable = false)
    private CompleteFilmEntity film;

    public FilmReview() {
    }

    public FilmReview(LocalDateTime date, Integer rating, String detail, CompleteFilmEntity film) {
      this.date = date;
      this.rating = rating;
      this.detail = detail;
      this.film = film;
    }

    public CompleteFilmEntity getFilm() {
      return film;
    }

    public void setFilm(CompleteFilmEntity film) {
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
}