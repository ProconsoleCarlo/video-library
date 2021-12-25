package it.proconsole.library.video.core.model;

public enum GenreEnum {
  ACTION(1L),
  ADVENTURE(2L),
  COMEDY(3L),
  DRAMA(4L),
  FANTASY(5L),
  ROMANTIC(6L),
  SCIENCE_FICTION(7L),
  THRILLER(8L);

  private final Long id;

  GenreEnum(Long id) {
    this.id = id;
  }

  public Long id() {
    return id;
  }
}
