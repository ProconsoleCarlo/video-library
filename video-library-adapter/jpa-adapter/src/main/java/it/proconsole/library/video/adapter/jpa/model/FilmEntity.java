package it.proconsole.library.video.adapter.jpa.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Table(name = "film")
@Entity
public class FilmEntity {
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
  private List<FilmReviewEntity> reviews;

  public FilmEntity() {
  }

  public FilmEntity(
          Long id,
          String title,
          Integer year,
          List<GenreEntity> genres,
          List<FilmReviewEntity> reviews
  ) {
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

  public List<FilmReviewEntity> getReviews() {
    return reviews;
  }

  public void setReviews(List<FilmReviewEntity> reviews) {
    this.reviews = reviews;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    FilmEntity film = (FilmEntity) o;
    return Objects.equals(id, film.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}