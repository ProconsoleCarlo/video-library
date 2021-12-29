package it.proconsole.library.video.rest.controller;

import it.proconsole.library.video.core.Fixtures;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Protocol;
import it.proconsole.library.video.rest.repository.ProtocolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {
  private static final String EXISTENT_FILM_JSON = "/it/proconsole/library/video/rest/controller/existentFilms.json";
  private static final String INSERT_FILM_JSON = "/it/proconsole/library/video/rest/controller/insertFilms.json";

  @Mock
  private ProtocolRepository<FilmRepository> filmProtocolRepository;
  @Mock
  private FilmRepository filmRepository;

  private MockMvc mvc;

  @BeforeEach
  void setUp() {
    mvc = standaloneSetup(new FilmController(filmProtocolRepository))
            .setMessageConverters(new MappingJackson2HttpMessageConverter(Fixtures.springObjectMapper()))
            .build();
  }

  @Nested
  class WhenGetFilms {
    @ParameterizedTest
    @EnumSource(Protocol.class)
    void getFilms(Protocol protocol) throws Exception {
      when(filmProtocolRepository.getBy(protocol)).thenReturn(filmRepository);
      when(filmRepository.findAll()).thenReturn(Fixtures.readListFromClasspath(EXISTENT_FILM_JSON, Film.class));

      mvc.perform(get("/" + protocol.name().toLowerCase() + "/films"))
              .andExpect(status().isOk())
              .andExpect(content().json(Fixtures.readFromClasspath(EXISTENT_FILM_JSON)));
    }

    @Test
    void notFoundIfInvalidProtocol() throws Exception {
      notFoundIfInvalidProtocolFor(get("/invalidProtocol/films"));
    }
  }

  @Nested
  class WhenUpdateFilms {
    @ParameterizedTest
    @EnumSource(Protocol.class)
    void updateFilms(Protocol protocol) throws Exception {
      var films = Fixtures.readListFromClasspath(EXISTENT_FILM_JSON, Film.class);

      when(filmProtocolRepository.getBy(protocol)).thenReturn(filmRepository);
      when(filmRepository.saveAll(films)).thenReturn(films);

      mvc.perform(post("/" + protocol.name().toLowerCase() + "/films")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(Fixtures.readFromClasspath(EXISTENT_FILM_JSON)))
              .andExpect(status().isOk())
              .andExpect(content().json(Fixtures.readFromClasspath(EXISTENT_FILM_JSON)));
    }

    @Test
    void notFoundIfInvalidProtocol() throws Exception {
      notFoundIfInvalidProtocolFor(post("/invalidProtocol/films")
              .contentType(MediaType.APPLICATION_JSON)
              .content(Fixtures.readFromClasspath(EXISTENT_FILM_JSON)));
    }
  }

  @Nested
  class WhenInsertFilms {
    @ParameterizedTest
    @EnumSource(Protocol.class)
    void insertFilms(Protocol protocol) throws Exception {
      var filmsToInsert = Fixtures.readListFromClasspath(INSERT_FILM_JSON, Film.class);
      var filmsInserted = Fixtures.readListFromClasspath(EXISTENT_FILM_JSON, Film.class);

      when(filmProtocolRepository.getBy(protocol)).thenReturn(filmRepository);
      when(filmRepository.saveAll(filmsToInsert)).thenReturn(filmsInserted);

      mvc.perform(put("/" + protocol.name().toLowerCase() + "/films")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(Fixtures.readFromClasspath(INSERT_FILM_JSON)))
              .andExpect(status().isOk())
              .andExpect(content().json(Fixtures.readFromClasspath(EXISTENT_FILM_JSON)));
    }

    @Test
    void notFoundIfInvalidProtocol() throws Exception {
      notFoundIfInvalidProtocolFor(put("/invalidProtocol/films")
              .contentType(MediaType.APPLICATION_JSON)
              .content(Fixtures.readFromClasspath(EXISTENT_FILM_JSON)));
    }
  }

  private void notFoundIfInvalidProtocolFor(MockHttpServletRequestBuilder mockedRequest) throws Exception {
    mvc.perform(mockedRequest).andExpect(status().isNotFound());

    verifyNoInteractions(filmProtocolRepository);
    verifyNoInteractions(filmRepository);
  }
}