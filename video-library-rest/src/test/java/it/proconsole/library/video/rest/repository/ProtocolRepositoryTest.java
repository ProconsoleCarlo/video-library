package it.proconsole.library.video.rest.repository;

import it.proconsole.library.video.core.repository.Protocol;
import it.proconsole.library.video.rest.exception.UnknownProtocolException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class ProtocolRepositoryTest<R> {
  private static final Protocol INVALID_PROTOCOL = Protocol.XLSX;

  abstract ProtocolRepository<R> repository();

  @Test
  void whenProtocolIsInvalid() {
    assertThrows(UnknownProtocolException.class, () -> repository().getBy(INVALID_PROTOCOL));
  }
}
