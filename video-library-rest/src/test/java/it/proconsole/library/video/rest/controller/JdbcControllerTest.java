package it.proconsole.library.video.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import it.proconsole.library.video.adapter.jdbc.repository.FilmRepository;
import it.proconsole.library.video.rest.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class JdbcControllerTest {
  private static final String FILMS_JSON = "/it/proconsole/library/video/rest/controller/films.json";

  @Mock
  private FilmRepository filmRepository;

  private MockMvc mvc;

  @BeforeEach
  void setUp() {
    mvc = standaloneSetup(new JdbcController(filmRepository)).build();
  }

  @Test
  void getFilms() throws Exception {
    when(filmRepository.findAll()).thenReturn(Fixtures.readFromClasspath(FILMS_JSON, new TypeReference<>() {
    }));

    mvc.perform(get("/jdbc/films"))
            .andExpect(status().isOk())
            .andExpect(content().json(Fixtures.readFromClasspath(FILMS_JSON)));
  }
}