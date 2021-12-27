package it.proconsole.library.video.core.model;

public enum GenreEnum {
  ACTION(1L),
  ADVENTURE(2L),
  BIOGRAPHICAL(3L),
  COMEDY(4L),
  CRIME(5L),
  DISASTER(6L),
  DOCUMENTARY(7L),
  DRAMA(8L),
  EROTIC(9L),
  FANTASY(10L),
  HISTORICAL(11L),
  HORROR(12L),
  ROMANTIC(13L),
  SCIENCE_FICTION(14L),
  THRILLER(15L),
  WESTERN(16L);

  private final Long id;

  GenreEnum(Long id) {
    this.id = id;
  }

  public Long id() {
    return id;
  }
}
