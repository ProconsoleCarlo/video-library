package it.proconsole.library.video.rest.controller;

import it.proconsole.library.video.adapter.jpa.model.Film;
import it.proconsole.library.video.adapter.jpa.repository.FilmRepository;
import it.proconsole.library.video.rest.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class JpaControllerTest {
  private static final String FILMS_JSON = "/it/proconsole/library/video/rest/controller/films.json";

  @Mock
  private FilmRepository filmRepository;

  private MockMvc mvc;

  @BeforeEach
  void setUp() {
    mvc = standaloneSetup(new JpaController(filmRepository))
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
}