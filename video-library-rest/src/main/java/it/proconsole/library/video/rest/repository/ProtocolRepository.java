package it.proconsole.library.video.rest.repository;

import it.proconsole.library.video.core.repository.Protocol;

public interface ProtocolRepository<R> {
  R getBy(Protocol protocol);
}
