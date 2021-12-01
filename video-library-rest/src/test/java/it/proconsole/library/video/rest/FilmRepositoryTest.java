package it.proconsole.library.video.rest;

import it.proconsole.library.video.ApplicationConfig;
import it.proconsole.library.video.adapter.jpa.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationConfig.class)
@ActiveProfiles("test")
class FilmRepositoryTest {

  @Autowired
  private FilmRepository filmRepository;

  @Test
  void name() {
    filmRepository.findAll();
  }
}
