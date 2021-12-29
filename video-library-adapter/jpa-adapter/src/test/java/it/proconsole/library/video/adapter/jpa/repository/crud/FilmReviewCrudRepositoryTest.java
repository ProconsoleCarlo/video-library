package it.proconsole.library.video.adapter.jpa.repository.crud;

import it.proconsole.library.video.adapter.jpa.model.FilmEntity;
import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql("/schema.sql")
class FilmReviewCrudRepositoryTest {
  @Autowired
  private FilmReviewCrudRepository filmReviewRepository;
  @Autowired
  private FilmCrudRepository filmCrudRepository;

  @Test
  void findByFilmId() {
    var films = filmCrudRepository.saveAll(List.of(aFilm(), aFilm()));
    var aFilm = films.get(0);
    var anotherFilm = films.get(1);
    var review = aFilmReview(aFilm);
    var anotherReview = aFilmReview(anotherFilm);
    var savedReview = filmReviewRepository.saveAll(List.of(review, anotherReview));

    var aReviewFound = filmReviewRepository.findByFilmId(aFilm.getId());
    var anotherReviewFound = filmReviewRepository.findByFilmId(anotherFilm.getId());

    assertEquals(List.of(savedReview.get(0)), aReviewFound);
    assertEquals(List.of(savedReview.get(1)), anotherReviewFound);
  }

  private FilmEntity aFilm() {
    var film = new FilmEntity();
    film.setTitle("Film title");
    film.setYear(2018);
    return film;
  }

  private FilmReviewEntity aFilmReview(FilmEntity film) {
    var filmReview = new FilmReviewEntity();
    filmReview.setDate(LocalDateTime.now());
    filmReview.setRating(7);
    filmReview.setDetail("Review");
    filmReview.setFilm(film);
    return filmReview;
  }
}