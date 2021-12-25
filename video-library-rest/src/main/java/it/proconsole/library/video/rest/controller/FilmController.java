package it.proconsole.library.video.rest.controller;

import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.rest.exception.UnknownProtocolException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class FilmController {
  private final Map<Protocol, FilmRepository> protocolRepositories;

  public FilmController(Map<Protocol, FilmRepository> protocolRepositories) {
    this.protocolRepositories = protocolRepositories;
  }

  @GetMapping("/{protocol}/films")
  public List<Film> getFilms(@PathVariable String protocol) {
    try {
      return protocolRepositories.get(Protocol.valueOf(protocol.toUpperCase())).findAll();
    } catch (IllegalArgumentException e) {
      throw new UnknownProtocolException(protocol);
    }
  }

  @PutMapping("/{protocol}/films")
  public List<Film> updateFilms(@PathVariable String protocol, @RequestBody List<Film> films) {
    try {
      return protocolRepositories.get(Protocol.valueOf(protocol.toUpperCase())).saveAll(films);
    } catch (IllegalArgumentException e) {
      throw new UnknownProtocolException(protocol);
    }
  }

  @ExceptionHandler(UnknownProtocolException.class)
  public ResponseEntity<String> notFound(UnknownProtocolException e) {
    return ResponseEntity.notFound().build();
  }
}
