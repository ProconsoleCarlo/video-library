package it.proconsole.library.video.adapter.jpa.model;

import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.model.FilmReview;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collections;
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

  public FilmEntity() {
  }

  public FilmEntity(Long id, String title, Integer year, List<GenreEntity> genres) {
    this.id = id;
    this.title = title;
    this.year = year;
    this.genres = genres;
  }

  public static FilmEntity fromDomain(Film film) {
    return new FilmEntity(
            film.id(),
            film.title(),
            film.year(),
            film.genres().stream().map(GenreEntity::fromDomain).toList()
    );
  }

  public Film toDomain() {
    return new Film(
            id,
            title,
            year,
            genres.stream().map(GenreEntity::toDomain).toList(),
            Collections.emptyList()
    );
  }

  public Film toDomain(List<FilmReview> reviews) {
    return new Film(
            id,
            title,
            year,
            genres.stream().map(GenreEntity::toDomain).toList(),
            reviews
    );
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