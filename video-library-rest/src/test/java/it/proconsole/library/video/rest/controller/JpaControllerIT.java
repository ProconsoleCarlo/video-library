package it.proconsole.library.video.rest.controller;

import it.proconsole.library.video.adapter.jpa.model.Film;
import it.proconsole.library.video.adapter.jpa.repository.FilmRepository;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(controllers = JpaController.class)
class JpaControllerIT {
  private static final String FILMS_JSON = "/it/proconsole/library/video/core/model/films.json";

  @MockBean
  private FilmRepository filmRepository;

  @Autowired
  private MockMvc mvc;

  @Test
  void getFilms() throws Exception {
    when(filmRepository.findAll()).thenReturn(Fixtures.readListFromClasspath(FILMS_JSON, Film.class));

    mvc.perform(get("/jpa/films"))
            .andExpect(status().isOk())
            .andExpect(content().json(Fixtures.readFromClasspath(FILMS_JSON)));
  }
}