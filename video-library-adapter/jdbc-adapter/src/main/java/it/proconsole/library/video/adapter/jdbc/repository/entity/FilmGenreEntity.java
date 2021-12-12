package it.proconsole.library.video.adapter.jdbc.repository.entity;

public record FilmGenreEntity(Long filmId, Long genreId) implements EntityWithId {
  @Override
  public Long id() {
    return filmId;
  }
}
