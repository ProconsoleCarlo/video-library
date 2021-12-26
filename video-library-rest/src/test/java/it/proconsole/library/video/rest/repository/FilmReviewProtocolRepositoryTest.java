package it.proconsole.library.video.rest.repository;

import it.proconsole.library.video.core.repository.FilmReviewRepository;
import it.proconsole.library.video.core.repository.Protocol;
import it.proconsole.library.video.rest.exception.UnknownProtocolException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmReviewProtocolRepositoryTest {
  private static final Protocol A_PROTOCOL = Protocol.JDBC;
  private static final Protocol ANOTHER_PROTOCOL = Protocol.JPA;
  private static final Protocol INVALID_PROTOCOL = Protocol.XLSX;

  @Mock
  private FilmReviewRepository aFilmReviewRepository;
  @Mock
  private FilmReviewRepository anotherFilmReviewRepository;

  private ProtocolRepository<FilmReviewRepository> repository;

  @BeforeEach
  void setUp() {
    repository = new FilmReviewProtocolRepository(aFilmReviewRepository, anotherFilmReviewRepository);
  }

  @Test
  void getByProtocol() {
    when(aFilmReviewRepository.protocol()).thenReturn(A_PROTOCOL);
    when(anotherFilmReviewRepository.protocol()).thenReturn(ANOTHER_PROTOCOL);

    assertEquals(aFilmReviewRepository, repository.getBy(A_PROTOCOL));
    assertEquals(anotherFilmReviewRepository, repository.getBy(ANOTHER_PROTOCOL));
  }

  @Test
  void whenProtocolIsInvalid() {
    assertThrows(UnknownProtocolException.class, () -> repository.getBy(INVALID_PROTOCOL));
  }
}