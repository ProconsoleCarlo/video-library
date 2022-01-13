package it.proconsole.library.video.adapter.xlsx.repository.adapter;

import it.proconsole.library.video.adapter.xlsx.model.FilmReviewRow;
import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
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
  private static final List<Genre> DOMAIN_GENRE = List.of(new Genre(GenreEnum.ACTION.id(), GenreEnum.ACTION));
  private static final List<String> FRONTIER_GENRE = List.of("azione");
  private static final List<FilmReview> DOMAIN_FILM_REVIEW = List.of(
          new FilmReview(1L, DATE, 10, "Review"),
          new FilmReview(2L, DATE, 6, null)
  );
  private static final List<FilmReviewRow> FRONTIER_FILM_REVIEW = List.of(
          new FilmReviewRow(1L, DATE, 10, "Review"),
          new FilmReviewRow(2L, DATE, 6, null)
  );
  private static final List<Film> DOMAIN = List.of(
          new Film(1L, "Title", 2021, DOMAIN_GENRE, DOMAIN_FILM_REVIEW),
          new Film(2L, "Another title", 2012, Collections.emptyList(), Collections.emptyList())
  );
  private static final List<FilmRow> FRONTIER = List.of(
          new FilmRow(1L, "Title", 2021, FRONTIER_GENRE, FRONTIER_FILM_REVIEW),
          new FilmRow(2L, "Another title", 2012, Collections.emptyList(), Collections.emptyList())
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
    when(genreAdapter.fromDomain(DOMAIN_GENRE)).thenReturn(FRONTIER_GENRE);
    when(genreAdapter.fromDomain(Collections.emptyList())).thenReturn(Collections.emptyList());
    when(filmReviewAdapter.fromDomain(DOMAIN_FILM_REVIEW)).thenReturn(FRONTIER_FILM_REVIEW);
    when(filmReviewAdapter.fromDomain(Collections.emptyList())).thenReturn(Collections.emptyList());

    var current = adapter.fromDomain(DOMAIN);

    assertEquals(FRONTIER, current);
  }

  @Test
  void toDomain() {
    when(genreAdapter.toDomain(FRONTIER_GENRE)).thenReturn(DOMAIN_GENRE);
    when(genreAdapter.toDomain(Collections.emptyList())).thenReturn(Collections.emptyList());
    when(filmReviewAdapter.toDomain(FRONTIER_FILM_REVIEW)).thenReturn(DOMAIN_FILM_REVIEW);
    when(filmReviewAdapter.toDomain(Collections.emptyList())).thenReturn(Collections.emptyList());

    var current = adapter.toDomain(FRONTIER);

    assertEquals(DOMAIN, current);
  }
}