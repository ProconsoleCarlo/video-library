package it.proconsole.library.video.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import it.proconsole.library.video.adapter.jdbc.repository.FilmRepository;
import it.proconsole.library.video.rest.Fixtures;
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
@WebMvcTest(controllers = JdbcController.class)
class JdbcControllerIT {
  private static final String FILMS_JSON = "/it/proconsole/library/video/rest/controller/films.json";

  @MockBean
  private FilmRepository filmRepository;

  @Autowired
  private MockMvc mvc;

  @Test
  void getFilms() throws Exception {
    when(filmRepository.findAll()).thenReturn(Fixtures.readFromClasspath(FILMS_JSON, new TypeReference<>() {
    }));

    mvc.perform(get("/jdbc/films"))
            .andExpect(status().isOk())
            .andExpect(content().json(Fixtures.readFromClasspath(FILMS_JSON)));
  }
}