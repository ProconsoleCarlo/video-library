package it.proconsole.library.video.adapter.jdbc.repository.entity;

public record FilmGenreEntity(long filmId, int genreId) implements EntityWithId {
  @Override
  public Long id() {
    return filmId;
  }
}
