package it.proconsole.library.video.rest.controller.jpa;

import it.proconsole.library.video.adapter.jpa.model.Film;
import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class FilmReviewControllerTest {
  private static final String FILMS_JSON = "/it/proconsole/library/video/core/model/films.json";

  @Mock
  private FilmReviewCrudRepository filmReviewRepository;

  private MockMvc mvc;

  @BeforeEach
  void setUp() {
    mvc = standaloneSetup(new FilmReviewController(filmReviewRepository))
            .setMessageConverters(new MappingJackson2HttpMessageConverter(Fixtures.springObjectMapper()))
            .build();
  }

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