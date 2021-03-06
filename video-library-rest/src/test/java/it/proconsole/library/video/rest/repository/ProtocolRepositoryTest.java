package it.proconsole.library.video.rest.repository;

import it.proconsole.library.video.core.repository.ByProtocolRepository;
import it.proconsole.library.video.core.repository.Protocol;
import it.proconsole.library.video.rest.exception.UnknownProtocolException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class ProtocolRepositoryTest<R extends ByProtocolRepository> {
  static final Protocol A_PROTOCOL = Protocol.JDBC;
  static final Protocol ANOTHER_PROTOCOL = Protocol.JPA;
  private static final Protocol INVALID_PROTOCOL = Protocol.XLSX;

  abstract ProtocolRepository<R> repository();

  @Test
  void whenProtocolIsInvalid() {
    var repository = repository();

    assertThrows(UnknownProtocolException.class, () -> repository.getBy(INVALID_PROTOCOL));
  }
}
