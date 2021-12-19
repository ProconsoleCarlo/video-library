package it.proconsole.library.video.rest.controller.jpa;

import it.proconsole.library.video.adapter.jpa.model.Film;
import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewRepository;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = FilmReviewController.class)
class FilmReviewControllerIT {
  private static final String FILMS_JSON = "/it/proconsole/library/video/core/model/films.json";

  @MockBean
  private FilmReviewRepository filmReviewRepository;

  @Autowired
  private MockMvc mvc;

  @Test
  void addReview() throws Exception {
    var film = Fixtures.readListFromClasspath(FILMS_JSON, Film.class).get(0);
    var review = new FilmReviewEntity(LocalDateTime.now(), 8, "This is a review", film.getId());

    when(filmReviewRepository.save(review)).thenReturn(review);

    mvc.perform(post("/jpa/review"))
            .andExpect(status().isOk());

    verify(filmReviewRepository).save(review);
  }
}