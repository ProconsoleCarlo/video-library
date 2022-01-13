package it.proconsole.library.video.adapter.xlsx.repository.adapter;

import it.proconsole.library.video.adapter.xlsx.exception.UnknownGenreException;
import it.proconsole.library.video.core.model.Genre;
import it.proconsole.library.video.core.model.GenreEnum;

import java.util.List;
import java.util.Optional;

public class GenreAdapter {
  public List<String> fromDomain(List<Genre> genres) {
    return genres.stream()
            .map(this::fromDomain)
            .toList();
  }

  public List<Genre> toDomain(List<String> genres) {
    return genres.stream()
            .map(this::genreToDomain)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
  }

  private Optional<Genre> genreToDomain(String genre) {
    var genreEnum = switch (genre) {
      case "avventura" -> GenreEnum.ADVENTURE;
      case "azione" -> GenreEnum.ACTION;
      case "biografico" -> GenreEnum.BIOGRAPHICAL;
      case "catastrofico" -> GenreEnum.DISASTER;
      case "commedia" -> GenreEnum.COMEDY;
      case "crimine" -> GenreEnum.CRIME;
      case "documentario" -> GenreEnum.DOCUMENTARY;
      case "drammatico" -> GenreEnum.DRAMA;
      case "erotico" -> GenreEnum.EROTIC;
      case "fantascienza" -> GenreEnum.SCIENCE_FICTION;
      case "fantastico" -> GenreEnum.FANTASY;
      case "horror" -> GenreEnum.HORROR;
      case "romantico" -> GenreEnum.ROMANTIC;
      case "storico" -> GenreEnum.HISTORICAL;
      case "suspense/thriller" -> GenreEnum.THRILLER;
      case "western" -> GenreEnum.WESTERN;
      case "" -> null;
      default -> throw new UnknownGenreException(genre);
    };
    return Optional.ofNullable(genreEnum)
            .map(it -> new Genre(it.id(), it));
  }

  private String fromDomain(Genre genre) {
    return switch (genre.value()) {
      case ACTION -> "azione";
      case ADVENTURE -> "avventura";
      case BIOGRAPHICAL -> "biografico";
      case COMEDY -> "commedia";
      case CRIME -> "crimine";
      case DISASTER -> "catastrofico";
      case DOCUMENTARY -> "documentario";
      case DRAMA -> "drammatico";
      case EROTIC -> "erotico";
      case FANTASY -> "fantastico";
      case HISTORICAL -> "storico";
      case HORROR -> "horror";
      case ROMANTIC -> "romantico";
      case SCIENCE_FICTION -> "fantascienza";
      case THRILLER -> "suspense/thriller";
      case WESTERN -> "western";
    };
  }
}
