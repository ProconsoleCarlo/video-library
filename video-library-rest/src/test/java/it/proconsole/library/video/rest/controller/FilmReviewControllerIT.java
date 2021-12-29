package it.proconsole.library.video.rest.controller;

import it.proconsole.library.video.core.Fixtures;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import it.proconsole.library.video.core.repository.Protocol;
import it.proconsole.library.video.rest.repository.ProtocolRepository;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = FilmReviewController.class)
class FilmReviewControllerIT {
  private static final String EXISTENT_FILM_REVIEW_JSON = "/it/proconsole/library/video/rest/controller/existentFilmReview.json";
  private static final String INSERT_FILM_REVIEW_JSON = "/it/proconsole/library/video/rest/controller/insertFilmReview.json";

  @MockBean
  private ProtocolRepository<FilmReviewRepository> filmReviewProtocolRepository;
  @Mock
  private FilmReviewRepository filmReviewRepository;

  @Autowired
  private MockMvc mvc;

  @Nested
  class WhenUpdateReview {
    @ParameterizedTest
    @EnumSource(Protocol.class)
    void updateReview(Protocol protocol) throws Exception {
      var filmReview = Fixtures.readFromClasspath(EXISTENT_FILM_REVIEW_JSON, FilmReview.class);

      when(filmReviewProtocolRepository.getBy(protocol)).thenReturn(filmReviewRepository);
      when(filmReviewRepository.save(filmReview)).thenReturn(filmReview);

      mvc.perform(post("/" + protocol.name().toLowerCase() + "/review")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(Fixtures.readFromClasspath(EXISTENT_FILM_REVIEW_JSON)))
              .andExpect(status().isOk())
              .andExpect(content().json(Fixtures.readFromClasspath(EXISTENT_FILM_REVIEW_JSON)));
    }

    @Test
    void notFoundIfInvalidProtocol() throws Exception {
      notFoundIfInvalidProtocolFor(post("/invalidProtocol/review")
              .contentType(MediaType.APPLICATION_JSON)
              .content(Fixtures.readFromClasspath(EXISTENT_FILM_REVIEW_JSON)));
    }
  }

  @Nested
  class WhenInsertReview {
    @ParameterizedTest
    @EnumSource(Protocol.class)
    void insertReview(Protocol protocol) throws Exception {
      var filmReviewToInsert = Fixtures.readFromClasspath(INSERT_FILM_REVIEW_JSON, FilmReview.class);
      var filmReviewInserted = Fixtures.readFromClasspath(EXISTENT_FILM_REVIEW_JSON, FilmReview.class);

      when(filmReviewProtocolRepository.getBy(protocol)).thenReturn(filmReviewRepository);
      when(filmReviewRepository.save(filmReviewToInsert)).thenReturn(filmReviewInserted);

      mvc.perform(put("/" + protocol.name().toLowerCase() + "/review")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(Fixtures.readFromClasspath(INSERT_FILM_REVIEW_JSON)))
              .andExpect(status().isOk())
              .andExpect(content().json(Fixtures.readFromClasspath(EXISTENT_FILM_REVIEW_JSON)));
    }

    @Test
    void notFoundIfInvalidProtocol() throws Exception {
      notFoundIfInvalidProtocolFor(put("/invalidProtocol/review")
              .contentType(MediaType.APPLICATION_JSON)
              .content(Fixtures.readFromClasspath(INSERT_FILM_REVIEW_JSON)));
    }
  }

  private void notFoundIfInvalidProtocolFor(MockHttpServletRequestBuilder mockedRequest) throws Exception {
    mvc.perform(mockedRequest).andExpect(status().isNotFound());

    verifyNoInteractions(filmReviewProtocolRepository);
    verifyNoInteractions(filmReviewRepository);
  }
}