package it.proconsole.library.video.rest.controller;

import it.proconsole.library.video.adapter.jpa.model.Film;
import it.proconsole.library.video.adapter.jpa.model.FilmReview;
import it.proconsole.library.video.adapter.jpa.repository.FilmRepository;
import it.proconsole.library.video.adapter.jpa.repository.FilmReviewRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class JpaControllerTest {
  private static final String FILMS_JSON = "/it/proconsole/library/video/core/model/films.json";

  @Mock
  private FilmRepository filmRepository;
  @Mock
  private FilmReviewRepository filmReviewRepository;

  private MockMvc mvc;

  @BeforeEach
  void setUp() {
    mvc = standaloneSetup(new JpaController(filmRepository, filmReviewRepository))
        .setMessageConverters(new MappingJackson2HttpMessageConverter(Fixtures.springObjectMapper()))
        .build();
  }

  @Test
  void getFilms() throws Exception {
    when(filmRepository.findAll()).thenReturn(Fixtures.readListFromClasspath(FILMS_JSON, Film.class));

    mvc.perform(get("/jpa/films"))
        .andExpect(status().isOk())
        .andExpect(content().json(Fixtures.readFromClasspath(FILMS_JSON)));
  }

  @Test
  void addReview() throws Exception {
    var film = Fixtures.readListFromClasspath(FILMS_JSON, Film.class).get(0);
    var review = new FilmReview(LocalDateTime.now(), 8, "This is a review", film);

    when(filmReviewRepository.save(review)).thenReturn(review);

    mvc.perform(post("/jpa/review"))
        .andExpect(status().isOk());

    verify(filmReviewRepository).save(review);
  }
}