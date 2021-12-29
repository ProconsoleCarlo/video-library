package it.proconsole.library.video.rest.controller;

import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import it.proconsole.library.video.core.repository.Protocol;
import it.proconsole.library.video.rest.exception.UnknownProtocolException;
import it.proconsole.library.video.rest.repository.ProtocolRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class FilmReviewController {
  private final ProtocolRepository<FilmReviewRepository> filmReviewProtocolRepository;

  public FilmReviewController(ProtocolRepository<FilmReviewRepository> filmReviewProtocolRepository) {
    this.filmReviewProtocolRepository = filmReviewProtocolRepository;
  }

  @PostMapping("/{protocol}/review/{filmId}")
  public FilmReview updateReview(@PathVariable String protocol, @PathVariable Long filmId, @RequestBody FilmReview review) {
    return filmReviewProtocolRepository.getBy(protocolFrom(protocol)).save(review, filmId);
  }

  @PutMapping("/{protocol}/review/{filmId}")
  public FilmReview insertReview(@PathVariable String protocol, @PathVariable Long filmId, @RequestBody FilmReview review) {
    return filmReviewProtocolRepository.getBy(protocolFrom(protocol)).save(review, filmId);
  }

  private Protocol protocolFrom(String protocol) {
    try {
      return Protocol.valueOf(protocol.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new UnknownProtocolException(protocol);
    }
  }

  @ExceptionHandler(UnknownProtocolException.class)
  public ResponseEntity<String> notFound(UnknownProtocolException e) {
    return ResponseEntity.notFound().build();
  }
}
