package it.proconsole.library.video.adapter.xlsx.repository.adapter;

import it.proconsole.library.video.core.model.Genre;
import it.proconsole.library.video.core.model.GenreEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GenreAdapter {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public String fromDomain(List<Genre> genres) {
    return StringUtils.capitalize(
            genres.stream()
                    .map(this::xlsxFrom)
                    .collect(Collectors.joining(", "))
    );
  }

  public List<Genre> toDomain(String xlsxGenres) {
    return Arrays.stream(xlsxGenres.toLowerCase().split(", "))
            .map(this::domainFrom)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
  }

  private Optional<Genre> domainFrom(String genre) {
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
      default -> {
        logger.error("Unknown genre " + genre + " found");
        yield null;
      }
    };
    return Optional.ofNullable(genreEnum)
            .map(it -> new Genre(it.id(), it));
  }

  private String xlsxFrom(Genre genre) {
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
