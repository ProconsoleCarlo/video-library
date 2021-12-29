package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.repository.adapter.FilmAdapter;
import it.proconsole.library.video.adapter.jdbc.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.jdbc.repository.adapter.GenreAdapter;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.GenreDao;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.model.GenreEnum;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Protocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Sql({"/schema.sql", "/data.sql"})
class JdbcFilmRepositoryTest {
  private final FilmAdapter filmAdapter = new FilmAdapter(new GenreAdapter(), new FilmReviewAdapter());

  @Autowired
  private DataSource dataSource;

  private FilmRepository repository;

  @BeforeEach
  void setUp() {
    repository = new JdbcFilmRepository(new FilmDao(dataSource), new GenreDao(dataSource), new FilmReviewDao(dataSource), filmAdapter);
  }

  @Test
  void protocol() {
    assertEquals(Protocol.JDBC, repository.protocol());
  }

  @Test
  void findAll() {
    var aFilm = new Film("Title", 2018, List.of(GenreEnum.ROMANTIC), Collections.emptyList());
    var anotherFilm = new Film("Another Title", 2019, List.of(GenreEnum.ACTION), Collections.emptyList());
    var savedFilms = repository.saveAll(List.of(aFilm, anotherFilm));

    var current = repository.findAll();

    assertEquals(savedFilms, current);
  }

  @Test
  void saveAll() {
    var date = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    var aFilmReview = new FilmReview(date, 8, "Comment");
    var anotherFilmReview = new FilmReview(date, 8, "Another comment");
    var aFilm = new Film("Title", 2018, List.of(GenreEnum.ACTION, GenreEnum.ROMANTIC), List.of(aFilmReview, anotherFilmReview));
    var anotherFilm = new Film("Another title", 2017, List.of(GenreEnum.COMEDY), Collections.emptyList());
    var currentSavedFilms = repository.saveAll(List.of(aFilm, anotherFilm));

    var savedFilmReview = aFilmReview.copy().withId(1L).build();
    var anotherSavedFilmReview = anotherFilmReview.copy().withId(2L).build();
    var savedFilm = aFilm.copy().withId(1L).withReviews(savedFilmReview, anotherSavedFilmReview).build();
    var anotherSavedFilm = anotherFilm.copy().withId(2L).build();
    assertEquals(List.of(savedFilm, anotherSavedFilm), currentSavedFilms);

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