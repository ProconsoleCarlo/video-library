package it.proconsole.library.video.rest.repository;

import it.proconsole.library.video.core.repository.FilmReviewRepository;
import it.proconsole.library.video.core.repository.Protocol;
import it.proconsole.library.video.rest.exception.UnknownProtocolException;

import java.util.Arrays;
import java.util.List;

public class FilmReviewProtocolRepository implements ProtocolRepository<FilmReviewRepository> {
  private final List<FilmReviewRepository> filmRepositories;

  public FilmReviewProtocolRepository(FilmReviewRepository... filmReviewRepositories) {
    this.filmRepositories = Arrays.asList(filmReviewRepositories);
  }

  @Override
  public FilmReviewRepository getBy(Protocol protocol) {
    return filmRepositories.stream()
            .filter(it -> it.protocol() == protocol)
            .findFirst()
            .orElseThrow(() -> new UnknownProtocolException(protocol));
  }
}
