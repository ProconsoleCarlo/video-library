package it.proconsole.library.video.adapter.xlsx.config;

import it.proconsole.library.video.adapter.xlsx.repository.XlsxFilmRepository;
import it.proconsole.library.video.adapter.xlsx.repository.adapter.FilmAdapter;
import it.proconsole.library.video.adapter.xlsx.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.xlsx.repository.adapter.GenreAdapter;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.FilmWorkbookRepository;
import it.proconsole.library.video.core.repository.FilmRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XlsxConfig {
  @Bean
  public FilmWorkbookRepository filmWorkbookRepository() {
    return new FilmWorkbookRepository("D:/OneDrive/Documents/Catalogo film.xlsx");
  }

  @Bean
  public FilmRepository xlsxFilmRepository(FilmWorkbookRepository filmWorkbookRepository) {
    return new XlsxFilmRepository(filmWorkbookRepository, new FilmAdapter(new GenreAdapter(), new FilmReviewAdapter()));
  }
}
