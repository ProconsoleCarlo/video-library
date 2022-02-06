package it.proconsole.library.video.core.repository;

import it.proconsole.library.video.core.model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface FilmRepository extends ByProtocolRepository {
  Logger logger = LoggerFactory.getLogger(FilmRepository.class);

  List<Film> findAll();

  List<Film> saveAll(List<Film> films);

  default void logStart(Protocol protocol, Operation operation) {
    logger.info("%s - %s request received".formatted(protocol, operation));
  }

  default void logStart(Protocol protocol, Operation operation, int inputs) {
    logger.info("%s - %s request received with %d values".formatted(protocol, operation, inputs));
  }

  default void logEnd(Protocol protocol, Operation operation, int results) {
    logger.info("%s - %s request completed with %d results".formatted(protocol, operation, results));
  }
}
