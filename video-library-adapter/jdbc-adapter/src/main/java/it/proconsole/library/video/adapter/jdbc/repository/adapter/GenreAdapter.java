package it.proconsole.library.video.adapter.jdbc.repository.adapter;

import it.proconsole.library.video.adapter.jdbc.model.GenreEntity;
import it.proconsole.library.video.core.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

public class GenreAdapter {
  public List<GenreEntity> fromDomain(List<Genre> genres) {
    return genres.stream().map(this::fromDomain).collect(Collectors.toList());
  }

  public List<Genre> toDomain(List<GenreEntity> genres) {
    return genres.stream().map(this::toDomain).collect(Collectors.toList());
  }

  private GenreEntity fromDomain(Genre genre) {
    return new GenreEntity(genre.id(), genre.value());
  }

  private Genre toDomain(GenreEntity genre) {
    return new Genre(genre.value());
  }
}
