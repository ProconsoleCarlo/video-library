package it.proconsole.library.video.adapter.xlsx.repository.adapter;

import it.proconsole.library.video.core.model.Genre;
import it.proconsole.library.video.core.model.GenreEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenreAdapter {
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
            .toList();
  }

  private Genre domainFrom(String xlsxGenre) {
    var genreEnum = switch (xlsxGenre) {
      case "avventura" -> GenreEnum.ADVENTURE;
      case "azione" -> GenreEnum.ACTION;
      case "commedia" -> GenreEnum.COMEDY;
      case "drammatico" -> GenreEnum.DRAMA;
      case "fantascienza" -> GenreEnum.SCIENCE_FICTION;
      case "fantastico" -> GenreEnum.FANTASY;
      case "romantico" -> GenreEnum.ROMANTIC;
      case "suspense/thriller" -> GenreEnum.THRILLER;
      default -> throw new IllegalArgumentException(xlsxGenre);
    };
    return new Genre(genreEnum.id(), genreEnum);
  }

  private String xlsxFrom(Genre genre) {
    return switch (genre.value()) {
      case ACTION -> "azione";
      case ADVENTURE -> "avventura";
      case COMEDY -> "commedia";
      case DRAMA -> "drammatico";
      case FANTASY -> "fantastico";
      case ROMANTIC -> "romantico";
      case SCIENCE_FICTION -> "fantascienza";
      case THRILLER -> "suspense/thriller";
    };
  }
}
