package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.GenreDao;
import it.proconsole.library.video.core.Fixtures;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.model.Genre;
import it.proconsole.library.video.core.model.GenreEnum;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Protocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Sql({"/schema.sql"})
class JdbcFilmRepositoryTest {
  private static final String FILMS_JSON = "/it/proconsole/library/video/core/model/films-in.json";

  @Autowired
  private DataSource dataSource;

  private FilmRepository repository;

  @BeforeEach
  void setUp() {
    repository = new JdbcFilmRepository(new FilmDao(dataSource), new GenreDao(dataSource), new FilmReviewDao(dataSource));
  }

  @Test
  void protocol() {
    assertEquals(Protocol.JDBC, repository.protocol());
  }

  @Test
  void findAll() {
    var current = repository.findAll();
    var expected = Fixtures.readListFromClasspath(FILMS_JSON, Film.class);

    assertEquals(expected, current);
  }

  /*@Test
  void saveAll() {
    var aFilmReview = new FilmReview(1L, LocalDateTime.now(), 7, "Comment", 1L);
    var anotherFilmReview = new FilmReview(2L, LocalDateTime.now(), 9, "Comment", 1L);
    var aFilm = new FilmBuilder().withId(1L).withTitle("Title").withYear(2018).withGenres(genres(GenreEnum.ROMANTIC)).withReviews(reviews(aFilmReview, anotherFilmReview)).createFilm();
    assertEquals(List.of(aFilm), repository.saveAll(List.of(aFilm)));

    var updatedFilmReview = new FilmReview(2L, LocalDateTime.now(), 8, "Updated comment", 1L);
    var newFilmReview = new FilmReview(LocalDateTime.now(), 8, "Updated comment", 1L);
    var filmToUpdate = new FilmBuilder().withId(1L).withTitle("Title to update").withYear(2021).withGenres(genres(GenreEnum.COMEDY, GenreEnum.ACTION)).withReviews(reviews(aFilmReview, updatedFilmReview, newFilmReview)).createFilm();
    var filmToInsert = new FilmBuilder().withTitle("Title to insert").withYear(2018).withGenres(Collections.emptyList()).withReviews(Collections.emptyList()).createFilm();
    var filmInserted = new FilmBuilder().withId(2L).withTitle("Title to insert").withYear(2018).withGenres(Collections.emptyList()).withReviews(Collections.emptyList()).createFilm();

    var current = repository.saveAll(List.of(filmToUpdate, filmToInsert));
    assertEquals(List.of(filmToUpdate, filmInserted), current);
  }*/

  private List<Genre> genres(GenreEnum... genreEnum) {
    return Arrays.stream(genreEnum).map(it -> new Genre(it)).toList();
  }

  private List<FilmReview> reviews(FilmReview... filmReview) {
    return Arrays.stream(filmReview).toList();
  }
}