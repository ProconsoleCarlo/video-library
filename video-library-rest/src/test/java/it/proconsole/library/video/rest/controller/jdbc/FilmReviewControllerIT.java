package it.proconsole.library.video.rest.controller.jdbc;

import it.proconsole.library.video.core.Fixtures;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = FilmReviewController.class)
class FilmReviewControllerIT {
  private static final String EXISTENT_FILM_REVIEW_JSON = "/it/proconsole/library/video/core/model/existentFilmReview.json";
  private static final String INSERT_FILM_REVIEW_JSON = "/it/proconsole/library/video/core/model/insertFilmReview.json";

  @MockBean
  private FilmReviewRepository filmReviewRepository;

  @Autowired
  private MockMvc mvc;

  @Test
  void updateReview() throws Exception {
    var filmReview = Fixtures.readFromClasspath(EXISTENT_FILM_REVIEW_JSON, FilmReview.class);

    when(filmReviewRepository.save(filmReview)).thenReturn(filmReview);

    mvc.perform(post("/jdbc/review")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(Fixtures.readFromClasspath(EXISTENT_FILM_REVIEW_JSON)))
            .andExpect(status().isOk())
            .andExpect(content().json(Fixtures.readFromClasspath(EXISTENT_FILM_REVIEW_JSON)));

    verify(filmReviewRepository).save(filmReview);
  }

  @Test
  void insertReview() throws Exception {
    var filmReviewToInsert = Fixtures.readFromClasspath(INSERT_FILM_REVIEW_JSON, FilmReview.class);
    var filmReviewInserted = Fixtures.readFromClasspath(EXISTENT_FILM_REVIEW_JSON, FilmReview.class);

    when(filmReviewRepository.save(filmReviewToInsert)).thenReturn(filmReviewInserted);

    mvc.perform(put("/jdbc/review")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(Fixtures.readFromClasspath(INSERT_FILM_REVIEW_JSON)))
            .andExpect(status().isOk())
            .andExpect(content().json(Fixtures.readFromClasspath(EXISTENT_FILM_REVIEW_JSON)));

    verify(filmReviewRepository).save(filmReviewToInsert);
  }
}