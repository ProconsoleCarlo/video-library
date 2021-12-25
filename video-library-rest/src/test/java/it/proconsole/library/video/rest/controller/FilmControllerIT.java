package it.proconsole.library.video.rest.controller;

import it.proconsole.library.video.core.Fixtures;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = FilmController.class)
class FilmControllerIT {
  private static final String FILMS_JSON = "/it/proconsole/library/video/core/model/films.json";

  @MockBean
  private Map<Protocol, FilmRepository> protocolRepositories;
  @Mock
  private FilmRepository filmRepository;

  @Autowired
  private MockMvc mvc;

  @Nested
  class WhenGetFilms {
    @ParameterizedTest
    @EnumSource(Protocol.class)
    void getFilms(Protocol protocol) throws Exception {
      when(protocolRepositories.get(protocol)).thenReturn(filmRepository);
      when(filmRepository.findAll()).thenReturn(Fixtures.readListFromClasspath(FILMS_JSON, Film.class));

      mvc.perform(get("/" + protocol.name().toLowerCase() + "/films"))
              .andExpect(status().isOk())
              .andExpect(content().json(Fixtures.readFromClasspath(FILMS_JSON)));
    }

    @Test
    void notFoundIfInvalidProtocol() throws Exception {
      mvc.perform(get("/invalidProtocol/films"))
              .andExpect(status().isNotFound());

      verifyNoInteractions(protocolRepositories);
      verifyNoInteractions(filmRepository);
    }
  }
}