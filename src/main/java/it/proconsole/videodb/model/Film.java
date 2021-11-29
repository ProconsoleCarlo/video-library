package it.proconsole.videodb.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name = "film")
@Entity
public class Film {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "year", nullable = false)
  private Integer year;

  @ManyToMany
  @JoinTable(name = "film_genres",
          joinColumns = @JoinColumn(name = "film_id"),
          inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private List<Genre> genres;

  @JsonManagedReference
  @OneToMany(mappedBy = "film", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<FilmReview> reviews;

  public List<Genre> getGenres() {
    return genres;
  }

  public void setGenres(List<Genre> genres) {
    this.genres = genres;
  }

  public List<FilmReview> getReviews() {
    return reviews;
  }

  public void setReviews(List<FilmReview> reviews) {
    this.reviews = reviews;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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
    Film film = (Film) o;
    return Objects.equals(id, film.id);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}