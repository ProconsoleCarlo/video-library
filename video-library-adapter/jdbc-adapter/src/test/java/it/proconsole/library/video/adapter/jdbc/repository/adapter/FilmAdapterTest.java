package it.proconsole.library.video.adapter.jdbc.repository.adapter;

import it.proconsole.library.video.adapter.jdbc.model.FilmEntity;
import it.proconsole.library.video.adapter.jdbc.model.FilmReviewEntity;
import it.proconsole.library.video.adapter.jdbc.model.GenreEntity;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.model.Genre;
import it.proconsole.library.video.core.model.GenreEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmAdapterTest {
  private static final LocalDateTime DATE = LocalDateTime.now();
  private static final Long FILM_ID = 1L;
  private static final Long ANOTHER_FILM_ID = 2L;
  private static final List<Genre> DOMAIN_GENRE = List.of(new Genre(GenreEnum.ACTION));
  private static final List<GenreEntity> FRONTIER_GENRE = List.of(new GenreEntity(GenreEnum.ACTION.id(), GenreEnum.ACTION));
  private static final List<FilmReview> DOMAIN_FILM_REVIEW = List.of(
          new FilmReview(1L, DATE, 10, "Review", FILM_ID),
          new FilmReview(2L, DATE, 6, null, FILM_ID)
  );
  private static final List<FilmReviewEntity> FRONTIER_FILM_REVIEW = List.of(
          new FilmReviewEntity(1L, DATE, 10, "Review", FILM_ID),
          new FilmReviewEntity(2L, DATE, 6, null, FILM_ID)
  );
  private static final List<Film> DOMAIN = List.of(
          new Film(FILM_ID, "Title", 2021, DOMAIN_GENRE, DOMAIN_FILM_REVIEW),
          new Film(ANOTHER_FILM_ID, "Another title", 2012, Collections.emptyList(), Collections.emptyList())
  );
  private static final List<FilmEntity> FRONTIER = List.of(
          new FilmEntity(FILM_ID, "Title", 2021),
          new FilmEntity(ANOTHER_FILM_ID, "Another title", 2012)
  );
  @Mock
  private GenreAdapter genreAdapter;
  @Mock
  private FilmReviewAdapter filmReviewAdapter;

  private FilmAdapter adapter;

  @BeforeEach
  void setUp() {
    adapter = new FilmAdapter(genreAdapter, filmReviewAdapter);
  }

  @Test
  void fromDomain() {
    var current = adapter.fromDomain(DOMAIN);

    assertEquals(FRONTIER, current);
  }

  @Test
  void toDomain() {
    when(genreAdapter.toDomain(FRONTIER_GENRE)).thenReturn(DOMAIN_GENRE);
    when(genreAdapter.toDomain(Collections.emptyList())).thenReturn(Collections.emptyList());
    when(filmReviewAdapter.toDomain(FRONTIER_FILM_REVIEW)).thenReturn(DOMAIN_FILM_REVIEW);
    when(filmReviewAdapter.toDomain(Collections.emptyList())).thenReturn(Collections.emptyList());

    var aFilm = adapter.toDomain(FRONTIER.get(0), FRONTIER_GENRE, FRONTIER_FILM_REVIEW);
    var anotherFilm = adapter.toDomain(FRONTIER.get(1), Collections.emptyList(), Collections.emptyList());

    assertEquals(DOMAIN, List.of(aFilm, anotherFilm));
  }

  @Test
  void genresFromDomain() {
    when(genreAdapter.fromDomain(DOMAIN_GENRE)).thenReturn(FRONTIER_GENRE);
    var current = adapter.genresFromDomain(DOMAIN_GENRE);

    assertEquals(FRONTIER_GENRE, current);
  }

  @Test
  void reviewsFromDomain() {
    when(filmReviewAdapter.fromDomain(DOMAIN_FILM_REVIEW, FILM_ID)).thenReturn(FRONTIER_FILM_REVIEW);
    var current = adapter.reviewsFromDomain(DOMAIN_FILM_REVIEW, FILM_ID);

    assertEquals(FRONTIER_FILM_REVIEW, current);
  }
}