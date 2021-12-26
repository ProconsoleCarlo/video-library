package it.proconsole.library.video.rest.repository;

import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Protocol;
import it.proconsole.library.video.rest.exception.UnknownProtocolException;

import java.util.Arrays;
import java.util.List;

public class FilmProtocolRepository implements ProtocolRepository<FilmRepository> {
  private final List<FilmRepository> filmRepositories;

  public FilmProtocolRepository(FilmRepository... filmRepositories) {
    this.filmRepositories = Arrays.asList(filmRepositories);
  }

  @Override
  public FilmRepository getBy(Protocol protocol) {
    return filmRepositories.stream()
            .filter(it -> it.protocol() == protocol)
            .findFirst()
            .orElseThrow(() -> new UnknownProtocolException(protocol));
  }
}
