package it.proconsole.library.video.rest.repository;

import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Protocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmProtocolRepositoryTest extends ProtocolRepositoryTest<FilmRepository> {
  private static final Protocol A_PROTOCOL = Protocol.JDBC;
  private static final Protocol ANOTHER_PROTOCOL = Protocol.JPA;

  @Mock
  private FilmRepository aFilmRepository;
  @Mock
  private FilmRepository anotherFilmRepository;

  private ProtocolRepository<FilmRepository> repository;

  @BeforeEach
  void setUp() {
    repository = new FilmProtocolRepository(aFilmRepository, anotherFilmRepository);
  }

  @Override
  ProtocolRepository<FilmRepository> repository() {
    return repository;
  }

  @Test
  void getByProtocol() {
    when(aFilmRepository.protocol()).thenReturn(A_PROTOCOL);
    when(anotherFilmRepository.protocol()).thenReturn(ANOTHER_PROTOCOL);

    assertEquals(aFilmRepository, repository.getBy(A_PROTOCOL));
    assertEquals(anotherFilmRepository, repository.getBy(ANOTHER_PROTOCOL));
  }
}