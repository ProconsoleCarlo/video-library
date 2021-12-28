package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.repository.crud.FilmCrudRepository;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.model.GenreEnum;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Protocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql({"/schema.sql", "/data.sql"})
class JpaFilmRepositoryTest {
  @Autowired
  private FilmCrudRepository filmCrudRepository;
  @Autowired
  private FilmReviewCrudRepository filmReviewCrudRepository;

  private FilmRepository repository;

  @BeforeEach
  void setUp() {
    repository = new JpaFilmRepository(filmCrudRepository, filmReviewCrudRepository);
  }

  @Test
  void protocol() {
    assertEquals(Protocol.JPA, repository.protocol());
  }

  @Test
  void findAll() {
    var aFilm = new Film("Title", 2018, List.of(GenreEnum.ROMANTIC), Collections.emptyList());
    var anotherFilm = new Film("Another Title", 2019, List.of(GenreEnum.ACTION), Collections.emptyList());

    var current = repository.saveAll(List.of(aFilm, anotherFilm));

    var expected = List.of(aFilm.copy().withId(1L).build(), anotherFilm.copy().withId(2L).build());
    assertEquals(expected, current);
  }

  @Test
  void saveAll() {
    var aFilmReview = new FilmReview(LocalDateTime.now(), 8, "comment");
    var anotherFilmReview = new FilmReview(LocalDateTime.now(), 8, "comment");
    var aFilm = new Film("Title", 2018, List.of(GenreEnum.ROMANTIC), List.of(aFilmReview, anotherFilmReview));
    var currentSavedFilms = repository.saveAll(List.of(aFilm));

    var savedFilmReview = aFilmReview.copy().withId(1L).withFilmId(1L).build();
    var anotherSavedFilmReview = anotherFilmReview.copy().withId(2L).withFilmId(1L).build();
    var savedFilm = aFilm.copy().withId(1L).withReviews(savedFilmReview, anotherSavedFilmReview).build();
    assertEquals(List.of(savedFilm), currentSavedFilms);

    var filmReviewToUpdate = savedFilmReview.copy().withRating(1).withDetail("Updated comment").build();
    var filmToUpdate = savedFilm.copy()
            .withTitle("New title")
            .withYear(2021)
            .withGenres(GenreEnum.ACTION, GenreEnum.ADVENTURE)
            .withReviews(filmReviewToUpdate)
            .build();

    var currentUpdatedFilms = repository.saveAll(List.of(filmToUpdate));

    assertEquals(List.of(filmToUpdate), currentUpdatedFilms);
  }
}