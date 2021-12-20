package it.proconsole.library.video.rest.controller.jpa;

import it.proconsole.library.video.Application;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@AutoConfigureDataJpa
@Sql({"/schema-test.sql", "/data-test.sql"})
class FilmControllerIT {
  private static final String FILMS_JSON = "/it/proconsole/library/video/core/model/films.json";
  private static final String UPDATED_FILMS_JSON = "/it/proconsole/library/video/rest/controller/update-films.json";

  @Autowired
  private MockMvc mvc;

  @Test
  void getFilms() throws Exception {
    mvc.perform(get("/jpa/films"))
            .andExpect(status().isOk())
            .andExpect(content().json(Fixtures.readFromClasspath(FILMS_JSON)));
  }

  @Test
  void putFilms() throws Exception {
    mvc.perform(put("/jpa/films")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(Fixtures.readFromClasspath(UPDATED_FILMS_JSON)))
            .andExpect(status().isOk())
            .andExpect(content().json(Fixtures.readFromClasspath(UPDATED_FILMS_JSON)));
  }
}