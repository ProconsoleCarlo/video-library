package it.proconsole.library.video.core.model;

public record Genre(Long id, GenreEnum value) {
  public Genre(GenreEnum value) {
    this(value.id(), value);
  }
}
