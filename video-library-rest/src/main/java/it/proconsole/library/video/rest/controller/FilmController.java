package it.proconsole.library.video.rest.controller;

import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Protocol;
import it.proconsole.library.video.rest.exception.UnknownProtocolException;
import it.proconsole.library.video.rest.repository.ProtocolRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class FilmController {
  private final ProtocolRepository<FilmRepository> filmProtocolRepository;

  public FilmController(ProtocolRepository<FilmRepository> filmProtocolRepository) {
    this.filmProtocolRepository = filmProtocolRepository;
  }

  @GetMapping("/{protocol}/films")
  public List<Film> getFilms(@PathVariable String protocol) {
    return filmProtocolRepository.getBy(protocolFrom(protocol)).findAll();
  }

  @PutMapping("/{protocol}/films")
  public List<Film> updateFilms(@PathVariable String protocol, @RequestBody List<Film> films) {
    return filmProtocolRepository.getBy(protocolFrom(protocol)).saveAll(films);
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
