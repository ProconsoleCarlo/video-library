package it.proconsole.library.video.adapter.jpa.repository.adapter;

import it.proconsole.library.video.adapter.jpa.model.GenreEntity;
import it.proconsole.library.video.core.model.Genre;

import java.util.List;

public class GenreAdapter {
  public List<GenreEntity> fromDomain(List<Genre> genres) {
    return genres.stream().map(this::fromDomain).toList();
  }

  public List<Genre> toDomain(List<GenreEntity> genres) {
    return genres.stream().map(this::toDomain).toList();
  }

  private GenreEntity fromDomain(Genre genre) {
    return new GenreEntity(genre.id(), genre.value());
  }

  private Genre toDomain(GenreEntity genre) {
    return new Genre(genre.getId(), genre.getValue());
  }
}
